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

import static org.junit.Assert.assertTrue;
import org.junit.*;

/**
 *
 * @author Carlos Romel Pereira da Silva <carlos.romel@gmail.com>
 */
public class VerificacoesBasicasTest {

    @Test
    public void dataValida() {
        DekatrianCalendar data = new DekatrianCalendar(2018, 1, 1);

        assertTrue("Uma data inválida.",
                   data.getYear() == 2018
                   && data.getMonth() == 1
                   && data.getDay() == 1);
    }

    @Test
    public void dataValidaLimite() {
        DekatrianCalendar data = new DekatrianCalendar(2018, 1, 28);

        assertTrue("Uma data inválida.",
                   data.getYear() == 2018
                   && data.getMonth() == 1
                   && data.getDay() == 28);
    }

    @Test
    public void dataInvalida() {
        DekatrianCalendar data = new DekatrianCalendar(2018, 1, 29);

        assertTrue("O calendário Dekatrian possui meses com 28 dias.",
                   data.getYear() == 0
                   && data.getMonth() == 0
                   && data.getDay() == 0);
    }

    @Test
    public void dataValidaLimiteMeses() {
        DekatrianCalendar data = new DekatrianCalendar(2018, 12, 1);

        assertTrue("O calendário Dekatrian possui treze meses.",
                   data.getYear() == 2018
                   && data.getMonth() == 12
                   && data.getDay() == 1);
    }

    @Test
    public void dataInvalidaLimiteMeses() {
        DekatrianCalendar data = new DekatrianCalendar(2018, 14, 1);

        assertTrue("O calendário Dekatrian possui treze meses.",
                   data.getYear() == 0
                   && data.getMonth() == 0
                   && data.getDay() == 0);
    }
}
