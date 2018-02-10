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
