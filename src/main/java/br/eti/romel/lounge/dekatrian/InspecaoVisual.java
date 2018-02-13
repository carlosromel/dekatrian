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
package br.eti.romel.lounge.dekatrian;

import java.text.*;
import java.util.*;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class InspecaoVisual {

    public static void main(String[] args) {
        showMonths(Arrays.asList(new DekatrianCalendar(2016, 0, 1),
                                 new DekatrianCalendar(2016, 1, 1),
                                 new DekatrianCalendar(2018, 0, 1),
                                 new DekatrianCalendar(2018, 1, 1),
                                 new DekatrianCalendar(2018, 2, 1)));
    }

    private static void showMonths(List<DekatrianCalendar> dekatrians) {
        String line = "";

        for (DekatrianCalendar dekatrian : dekatrians) {
            line += header(dekatrian) + " ";
        }

        System.out.println(line);

        line = "";
        for (DekatrianCalendar dekatrian : dekatrians) {
            line += weekDays(dekatrian) + " ";
        }

        System.out.println(line);

        for (int w = 0; w < 5; ++w) {
            line = "";
            for (DekatrianCalendar dekatrian : dekatrians) {
                line += days(dekatrian, w);
                line += " ";
            }

            System.out.println(line);
        }
    }

    private static String header(DekatrianCalendar dekatrian) {

        return String.format("[%30s]", dekatrian.toHuman());
    }

    private static String weekDays(DekatrianCalendar dekatrian) {
        String line = " #";

        for (String shortWeekDay : Week.shortWeekDays(dekatrian)) {
            line += String.format(" %s", shortWeekDay);
        }

        return String.format("[%30s]", line);
    }

    private static String days(DekatrianCalendar dekatrian, Integer week) {
        final List<Week> weeks = dekatrian.getDekatrianWeeks();
        String line = "";

        if (weeks.size() > week) {
            Week weekDays = weeks.get(week);
            line += String.format("%02d", weekDays.getNumeroSemana());
            for (Integer day : weekDays.getDays()) {
                line += String.format(" %3s", day == null ? "" : day);
            }
        }

        return String.format("[%30s]", line);
    }

    private static void sync() {
        String formato = "MM.dd";
        Integer anoInicial = 2018;
        Integer dias = 0;
        Integer agrupador = 7;
        Calendar dataInicial = new GregorianCalendar(anoInicial, 0, 1);

        while (dataInicial.get(Calendar.YEAR) == anoInicial) {
            DekatrianCalendar dekatrian = new DekatrianCalendar(dataInicial);
            String grg = new SimpleDateFormat(formato).format(dataInicial.getTime());
            String dkt = String.format("%02d.%02d",
                                       dekatrian.getMonth(),
                                       dekatrian.getDay());

            System.out.printf("\t(%s, %s)%s", grg, dkt, (++dias % agrupador == 0 ? "\n" : ""));

            dataInicial.add(Calendar.DAY_OF_MONTH, 1);

            if (dataInicial.get(Calendar.MONTH) == 2) {
                break;
            }
        }
    }
}
