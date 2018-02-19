/**
 * Copyright (C) 2018 Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package br.eti.romel.lounge.dekatrian.heroku;

import br.eti.romel.lounge.dekatrian.*;
import java.text.*;
import java.util.*;
import java.util.logging.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
@Controller
@SpringBootApplication
public class DekatrianWebApp {
    private static final String SHORT_REF = "%04d%02d";

    @RequestMapping("/")
    String index(Model model) {
        final Calendar gregorian = Calendar.getInstance();
        final DekatrianCalendar dekatrian = new DekatrianCalendar();

        return calendar(ref(dekatrian.previousMonth()), ref(gregorian), model);
    }

    @PostMapping("/")
    public String converter(@ModelAttribute Bean bean) {

        return "index";
    }

    @RequestMapping("/cal/{refDekatrian}/{refGregorian}")
    String calendar(@PathVariable String refDekatrian, @PathVariable String refGregorian, Model model) {
        final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-M-d");
        final SimpleDateFormat SDFM = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));

        if (refDekatrian.length() == 6) {
            final int yd = Integer.parseInt(refDekatrian.substring(0, 4));
            final int md = Integer.parseInt(refDekatrian.substring(4, 6));
            final int yg = Integer.parseInt(refGregorian.substring(0, 4));
            final int mg = Integer.parseInt(refGregorian.substring(4, 6));
            final DekatrianCalendar dekatrian = new DekatrianCalendar(yd, md, 1);
            final Calendar gregorian = new GregorianCalendar(yg, mg, 1);
            final Calendar anteriorGregorian = new GregorianCalendar(yg, mg, 1);
            final Calendar proximoGregorian = new GregorianCalendar(yg, mg, 1);
            final String monthDekatrian = DekatrianEnum.getMonthName(md + 1);
            final String monthGregorian = SDFM.format(gregorian.getTime());
            final DekatrianCalendar anteriorDekatrian = dekatrian.previousMonth();
            final DekatrianCalendar proximoDekatrian = dekatrian.nextMonth();

            anteriorGregorian.add(Calendar.MONTH, -1);
            proximoGregorian.add(Calendar.MONTH, 1);

            model.addAttribute("todayDekatrian", new DekatrianCalendar().getSimpleFormat());
            model.addAttribute("yearDekatrian", yd);
            model.addAttribute("monthDekatrian", md + 1);
            model.addAttribute("todayGregorian", SDF.format(new GregorianCalendar().getTime()));
            model.addAttribute("yearGregorian", yg);
            model.addAttribute("monthGregorian", mg + 1);

            model.addAttribute("refDekatrian", refDekatrian);
            model.addAttribute("refGregorian", refGregorian);

            model.addAttribute("baseDekatrian", String.format("%04d-%02d", yd, md + 1));
            model.addAttribute("baseGregorian", String.format("%04d-%02d", yg, mg + 1));

            model.addAttribute("titleDekatrian", String.format("%s %04d", monthDekatrian, yd));
            model.addAttribute("titleGregorian", String.format("%s %04d", monthGregorian, yg));

            model.addAttribute("anteriorDekatrian", ref(anteriorDekatrian));
            model.addAttribute("proximoDekatrian", ref(proximoDekatrian));
            model.addAttribute("anteriorGregorian", ref(anteriorGregorian));
            model.addAttribute("proximoGregorian", ref(proximoGregorian));

            model.addAttribute("dekatrianWeekDays", Week.shortWeekDays(dekatrian));
            model.addAttribute("semanasDekatrianas", dekatrian.getDekatrianWeeks());
            model.addAttribute("semanasGregorianas", DekatrianCalendar.getGregorianWeeks(gregorian));

            model.addAttribute("bean", new Bean(dekatrian));

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
            greg.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(gregorian));
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
