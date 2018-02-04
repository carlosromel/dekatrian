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
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author Carlos Romel Pereira da Silva <carlos.romel@gmail.com>
 */
public class AritimeticaBasicaTest {
    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void mesAnterior() {
        DekatrianCalendar atual = new DekatrianCalendar(2018, 0, 1);
        DekatrianCalendar anterior = new DekatrianCalendar(2017, 12, 1);

        assertEquals("Próximo mês inválido.",
                     anterior.toString(),
                     atual.previousMonth().toString());
    }

    @Test
    public void proximoMes() {
        DekatrianCalendar atual = new DekatrianCalendar(2018, 1, 1);
        DekatrianCalendar proximo = new DekatrianCalendar(2018, 2, 1);

        assertEquals("Próximo mês inválido.",
                     proximo.toString(),
                     atual.nextMonth().toString());
    }
}
