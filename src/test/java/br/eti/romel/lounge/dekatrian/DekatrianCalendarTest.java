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
import static org.junit.Assert.assertEquals;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Carlos Romel Pereira da Silva <carlos.romel@gmail.com>
 */
@RunWith(Parameterized.class)
public class DekatrianCalendarTest {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    private static final int ANO_BISSEXTO = 2016;
    private static final int ANO_REFERENCIA = 2018;
    private final String mensagem;
    private final DekatrianCalendar informado;
    private final Calendar esperado;

    @Parameters
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
            {new DekatrianCalendar(ANO_BISSEXTO, 0, 1), new GregorianCalendar(ANO_BISSEXTO, 0, 1), "Anachronian (dia fora do tempo)."},
            {new DekatrianCalendar(ANO_BISSEXTO, 0, 2), new GregorianCalendar(ANO_BISSEXTO, 0, 2), "Sinchronian (para anos bissextos)."},
            {new DekatrianCalendar(ANO_BISSEXTO, 1, 1), new GregorianCalendar(ANO_BISSEXTO, 0, 3), ""},
            {new DekatrianCalendar(ANO_BISSEXTO, 1, 28), new GregorianCalendar(ANO_BISSEXTO, 0, 30), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 0, 1), new GregorianCalendar(ANO_REFERENCIA, 0, 1), "Anachronian (dia fora do tempo)."},
            {new DekatrianCalendar(ANO_REFERENCIA, 1, 1), new GregorianCalendar(ANO_REFERENCIA, 0, 2), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 1, 2), new GregorianCalendar(ANO_REFERENCIA, 0, 3), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 1, 28), new GregorianCalendar(ANO_REFERENCIA, 0, 29), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 2, 2), new GregorianCalendar(ANO_REFERENCIA, 0, 31), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 2, 3), new GregorianCalendar(ANO_REFERENCIA, 1, 1), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 3, 2), new GregorianCalendar(ANO_REFERENCIA, 1, 28), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 3, 3), new GregorianCalendar(ANO_REFERENCIA, 2, 1), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 4, 5), new GregorianCalendar(ANO_REFERENCIA, 2, 31), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 4, 6), new GregorianCalendar(ANO_REFERENCIA, 3, 1), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 5, 7), new GregorianCalendar(ANO_REFERENCIA, 3, 30), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 5, 8), new GregorianCalendar(ANO_REFERENCIA, 4, 1), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 6, 10), new GregorianCalendar(ANO_REFERENCIA, 4, 31), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 6, 11), new GregorianCalendar(ANO_REFERENCIA, 5, 1), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 7, 12), new GregorianCalendar(ANO_REFERENCIA, 5, 30), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 7, 13), new GregorianCalendar(ANO_REFERENCIA, 6, 1), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 8, 15), new GregorianCalendar(ANO_REFERENCIA, 6, 31), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 8, 16), new GregorianCalendar(ANO_REFERENCIA, 7, 1), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 9, 18), new GregorianCalendar(ANO_REFERENCIA, 7, 31), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 9, 19), new GregorianCalendar(ANO_REFERENCIA, 8, 1), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 10, 20), new GregorianCalendar(ANO_REFERENCIA, 8, 30), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 10, 21), new GregorianCalendar(ANO_REFERENCIA, 9, 1), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 11, 23), new GregorianCalendar(ANO_REFERENCIA, 9, 31), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 11, 24), new GregorianCalendar(ANO_REFERENCIA, 10, 1), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 12, 25), new GregorianCalendar(ANO_REFERENCIA, 10, 30), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 12, 26), new GregorianCalendar(ANO_REFERENCIA, 11, 1), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 13, 28), new GregorianCalendar(ANO_REFERENCIA, 11, 31), ""}
        });
    }

    public DekatrianCalendarTest(DekatrianCalendar informado, Calendar esperado, String mensagem) {
        this.informado = informado;
        this.esperado = esperado;
        this.mensagem = mensagem;
    }

    @Test
    public void equivalencia() {

        assertEquals(this.mensagem,
                     SDF.format(this.esperado.getTime()),
                     SDF.format(this.informado.getTime()));
    }
}
