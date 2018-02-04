/**
 * Copyright 2017 Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
package br.eti.romel.lounge.dekatrian.heroku;

import br.eti.romel.lounge.dekatrian.*;
import java.text.*;
import java.util.*;
import java.util.logging.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
@SpringBootApplication
public class Main {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    private static final String SHORT_REF = "%04d%02d";
    private static final List<String> ESTADOS = Arrays.asList("Janeiro", "Fevereiro",
                                                              "Março", "Abril",
                                                              "Maio", "Junho",
                                                              "Julho", "Agosto",
                                                              "Setembro", "Outubro",
                                                              "Novembro", "Dezembro");

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

    @RequestMapping("/")
    String index(Model model) {
        Calendar gregorian = Calendar.getInstance();
        final DekatrianCalendar dekatrian = new DekatrianCalendar().gregToDeka(gregorian);

        return calendar(ref(dekatrian), ref(gregorian), model);
    }

    @RequestMapping("/cal/{refDekatrian}/{refGregorian}")
    String calendar(@PathVariable String refDekatrian, @PathVariable String refGregorian, Model model) {

        if (refDekatrian.length() == 6) {
            final int yd = Integer.parseInt(refDekatrian.substring(0, 4));
            final int md = Integer.parseInt(refDekatrian.substring(4, 6));
            final int yg = Integer.parseInt(refGregorian.substring(0, 4));
            final int mg = Integer.parseInt(refGregorian.substring(4, 6));

            final DekatrianCalendar dekatrian = new DekatrianCalendar(yd, md, 1);
            final Calendar gregorian = new GregorianCalendar(yg, mg, 1);
            final Calendar anteriorGregorian = new GregorianCalendar(yg, mg, 1);
            final Calendar proximoGregorian = new GregorianCalendar(yg, mg, 1);

            final int yearDekatrian = dekatrian.getYear();
            final int yearGregorian = gregorian.get(Calendar.YEAR);
            final String monthDekatrian = DekatrianEnum.getMonthName(dekatrian.getMonth());
            final String monthGregorian = ESTADOS.get(gregorian.get(Calendar.MONTH));
            final DekatrianCalendar anteriorDekatrian = dekatrian.previousMonth();
            final DekatrianCalendar proximoDekatrian = dekatrian.nextMonth();

            anteriorGregorian.add(Calendar.MONTH, -1);
            proximoGregorian.add(Calendar.MONTH, 1);

            model.addAttribute("dekatrian", refDekatrian);
            model.addAttribute("gregorian", refGregorian);

            model.addAttribute("refDekatrian", String.format("%s %04d", monthDekatrian, yearDekatrian));
            model.addAttribute("refGregorian", String.format("%s %04d", monthGregorian, yearGregorian));

            model.addAttribute("anteriorDekatrian", ref(anteriorDekatrian));
            model.addAttribute("proximoDekatrian", ref(proximoDekatrian));
            model.addAttribute("anteriorGregorian", ref(anteriorGregorian));
            model.addAttribute("proximoGregorian", ref(proximoGregorian));

            model.addAttribute("semanaInicialDekatrian", dekatrian.getWeek());
            model.addAttribute("semanaInicialGregorian", gregorian.get(Calendar.WEEK_OF_YEAR));
            model.addAttribute("bean", new Bean(dekatrian));

            model.addAttribute("semanas", obterSemanas(gregorian));

            return "index";
        } else {
            return index(model);
        }
    }

    @PostMapping("/")
    public String converter(@ModelAttribute Bean bean) {

        return "index";
    }

    @ResponseBody()
    @RequestMapping(path = "/v1/dekatrian/{gregorian}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    Bean toDekatrean(@PathVariable String gregorian) {
        Bean result = new Bean();
        try {
            Calendar greg = new GregorianCalendar();
            greg.setTime(SDF.parse(gregorian));
            result.setGregorian(greg);
        } catch (ParseException ex) {
            result.setMensagem(String.format("%s não é uma data válida (%s).", gregorian, ex.getLocalizedMessage()));
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @ResponseBody()
    @RequestMapping(path = "/v1/gregorian/{dekatrian}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    Bean toGregorian(@PathVariable String dekatrian) {
        Bean result = new Bean();
        try {
            result.setDekatrian(new DekatrianCalendar(SDF.parse(dekatrian)));
        } catch (ParseException ex) {
            result.setMensagem(String.format("%s não é uma data válida (%s).", dekatrian, ex.getLocalizedMessage()));
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    private String ref(Calendar date) {

        return String.format(SHORT_REF, date.get(Calendar.YEAR), date.get(Calendar.MONTH));
    }

    private String ref(DekatrianCalendar date) {

        return String.format(SHORT_REF, date.getYear(), date.getMonth());
    }

    public List<Semana> obterSemanas(Calendar gregorian) {
        List<Semana> semanas = new ArrayList<>();
        int mesAtual = gregorian.get(Calendar.MONTH);
        gregorian.set(Calendar.DAY_OF_MONTH, 1);

        while (gregorian.get(Calendar.MONTH) == mesAtual) {
            Integer numeroSemana = gregorian.get(Calendar.WEEK_OF_YEAR);
            List<Integer> dias = Arrays.asList(null, null, null, null, null, null, null);

            while (gregorian.get(Calendar.MONTH) == mesAtual
                   && gregorian.get(Calendar.WEEK_OF_YEAR) == numeroSemana) {
                dias.set(gregorian.get(Calendar.DAY_OF_WEEK) - 1, gregorian.get(Calendar.DAY_OF_MONTH));
                gregorian.add(Calendar.DAY_OF_MONTH, 1);
            }

            semanas.add(new Semana(numeroSemana, dias));
        }

        return semanas;
    }
}
