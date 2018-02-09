/**
 * Copyright 2018 Carlos Romel Pereira da Silva, carlos.romel@gmail.com
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

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

    @RequestMapping("/")
    String index(Model model) {
        Calendar gregorian = Calendar.getInstance();
        final DekatrianCalendar dekatrian = new DekatrianCalendar().gregToDeka(gregorian);

        return calendar(ref(dekatrian), ref(gregorian), model);
    }

    @PostMapping("/")
    public String converter(@ModelAttribute Bean bean) {

        return "index";
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

            final boolean isLeap = new GregorianCalendar().isLeapYear(yg);
            final int yearDekatrian = dekatrian.getYear();
            final int yearGregorian = gregorian.get(Calendar.YEAR);
            final String monthDekatrian = DekatrianEnum.getMonthName(dekatrian.getMonth() + 1);
            final String monthGregorian = new SimpleDateFormat("MMMM", new Locale("pt", "BR")).format(gregorian.getTime());
            final DekatrianCalendar anteriorDekatrian = dekatrian.previousMonth();
            final DekatrianCalendar proximoDekatrian = dekatrian.nextMonth();

            anteriorGregorian.add(Calendar.MONTH, -1);
            proximoGregorian.add(Calendar.MONTH, 1);

            model.addAttribute("dekatrian", refDekatrian);
            model.addAttribute("gregorian", refGregorian);

            model.addAttribute("baseDekatrian", String.format("%04d-%02d", yd, md + 1));
            model.addAttribute("baseGregorian", String.format("%04d-%02d", yg, mg + 1));

            model.addAttribute("refDekatrian", String.format("%s %04d", monthDekatrian, yearDekatrian));
            model.addAttribute("refGregorian", String.format("%s %04d", monthGregorian, yearGregorian));

            model.addAttribute("anteriorDekatrian", ref(anteriorDekatrian));
            model.addAttribute("proximoDekatrian", ref(proximoDekatrian));
            model.addAttribute("anteriorGregorian", ref(anteriorGregorian));
            model.addAttribute("proximoGregorian", ref(proximoGregorian));

            model.addAttribute("dekatrianWeekDays", Week.shortWeekDays(dekatrian));
            model.addAttribute("semanaInicialDekatrian", dekatrian.getWeek());
            model.addAttribute("semanaInicialGregorian", gregorian.get(Calendar.WEEK_OF_YEAR));
            model.addAttribute("bean", new Bean(dekatrian));

            if (md == 0) {
                model.addAttribute("anachronian", 1);
                System.out.println(isLeap);
                List<String> weekDays = Arrays.asList("segunda", "terca", "quarta", "quinta", "sexta", "sabado", "domingo");
                switch (Week.firstDay(dekatrian) - 1) {
                    case Calendar.SUNDAY:
                        model.addAttribute("anachronian.segunda", 1);
                        if (isLeap) {
                            model.addAttribute("sinchronian.terca", 2);
                        }
                        break;
                    case Calendar.MONDAY:
                        model.addAttribute("anachronian.terca", 1);
                        if (isLeap) {
                            model.addAttribute("sinchronian.quarta", 2);
                        }
                        break;
                    case Calendar.TUESDAY:
                        model.addAttribute("anachronian.quarta", 1);
                        if (isLeap) {
                            model.addAttribute("sinchronian.quinta", 2);
                        }
                        break;
                    case Calendar.WEDNESDAY:
                        model.addAttribute("anachronian.quinta", 1);
                        if (isLeap) {
                            model.addAttribute("sinchronian.sexta", 2);
                        }
                        break;
                    case Calendar.THURSDAY:
                        model.addAttribute("anachronian.sexta", 1);
                        if (isLeap) {
                            model.addAttribute("sinchronian.sabado", 2);
                        }
                        break;
                    case Calendar.FRIDAY:
                        model.addAttribute("anachronian.sabado", 1);
                        if (isLeap) {
                            model.addAttribute("sinchronian.domingo", 2);
                        }
                        break;
                    case Calendar.SATURDAY:
                        model.addAttribute("anachronian.domingo", 1);
                        if (isLeap) {
                            model.addAttribute("sinchronian.segunda", 2);
                        }
                        break;
                }
            }
            model.addAttribute("semanasDekatrianas", dekatrian.getDekatrianWeeks());

            model.addAttribute("semanasGregorianas", DekatrianCalendar.getGregorianWeeks(proximoGregorian));

            return "index";
        } else {
            return index(model);
        }
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
            String[] parts = dekatrian.split("-");
            if (parts.length == 3) {
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);

//                if (month == 1 && (day == 1 || (isLeap && day == 2))) {
//                    --month;
//                }
                if (!result.setDekatrian(new DekatrianCalendar(year, month, day))) {
                    result.setMensagem(String.format("%s não é uma data válida.", dekatrian));
                }
            }
        } catch (NumberFormatException ex) {
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

}
