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

import java.util.*;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

/**
 *
 * @author Carlos Romel Pereira da Silva <carlos.romel@gmail.com>
 */
@RunWith(Parameterized.class)
public class WeekFirstDayTest {

    private final String ano;
    private final int informado;
    private final int esperado;

    public WeekFirstDayTest(String ano, int informado, int esperado) {
        this.ano = ano;
        this.informado = informado;
        this.esperado = esperado;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
            {"2016", Week.firstDay(new DekatrianCalendar(2016, 0, 1)), Calendar.SUNDAY}, // ano bissexto
            {"2017", Week.firstDay(new DekatrianCalendar(2017, 0, 1)), Calendar.MONDAY},
            {"2018", Week.firstDay(new DekatrianCalendar(2018, 0, 1)), Calendar.TUESDAY},
            {"2013", Week.firstDay(new DekatrianCalendar(2013, 0, 1)), Calendar.WEDNESDAY},
            {"2014", Week.firstDay(new DekatrianCalendar(2014, 0, 1)), Calendar.THURSDAY},
            {"2015", Week.firstDay(new DekatrianCalendar(2015, 0, 1)), Calendar.FRIDAY},
            {"2010", Week.firstDay(new DekatrianCalendar(2010, 0, 1)), Calendar.SATURDAY}
        });
    }

    @Test
    public void equivalencia() {

        assertEquals(this.ano, this.esperado, this.informado);
    }
}
