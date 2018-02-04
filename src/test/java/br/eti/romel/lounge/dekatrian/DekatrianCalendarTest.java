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
    private static final int ANO_REFERENCIA = 2017;
    private final String mensagem;
    private final DekatrianCalendar informado;
    private final Calendar esperado;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {new DekatrianCalendar(ANO_REFERENCIA, 0, 01), new GregorianCalendar(ANO_REFERENCIA, 0, 01), "Dia fora do tempo."},
            {new DekatrianCalendar(ANO_REFERENCIA, 0, 28), new GregorianCalendar(ANO_REFERENCIA, 0, 28), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 1, 01), new GregorianCalendar(ANO_REFERENCIA, 0, 29), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 1, 28), new GregorianCalendar(ANO_REFERENCIA, 1, 25), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 2, 01), new GregorianCalendar(ANO_REFERENCIA, 1, 26), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 2, 28), new GregorianCalendar(ANO_REFERENCIA, 2, 25), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 3, 01), new GregorianCalendar(ANO_REFERENCIA, 2, 26), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 3, 28), new GregorianCalendar(ANO_REFERENCIA, 3, 22), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 4, 01), new GregorianCalendar(ANO_REFERENCIA, 3, 23), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 4, 28), new GregorianCalendar(ANO_REFERENCIA, 4, 20), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 5, 01), new GregorianCalendar(ANO_REFERENCIA, 4, 21), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 5, 28), new GregorianCalendar(ANO_REFERENCIA, 5, 17), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 6, 01), new GregorianCalendar(ANO_REFERENCIA, 5, 18), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 6, 28), new GregorianCalendar(ANO_REFERENCIA, 6, 15), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 7, 01), new GregorianCalendar(ANO_REFERENCIA, 6, 16), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 7, 28), new GregorianCalendar(ANO_REFERENCIA, 7, 12), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 8, 01), new GregorianCalendar(ANO_REFERENCIA, 7, 13), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 8, 28), new GregorianCalendar(ANO_REFERENCIA, 8, 9), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 9, 01), new GregorianCalendar(ANO_REFERENCIA, 8, 10), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 9, 28), new GregorianCalendar(ANO_REFERENCIA, 9, 7), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 10, 01), new GregorianCalendar(ANO_REFERENCIA, 9, 8), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 10, 28), new GregorianCalendar(ANO_REFERENCIA, 10, 4), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 11, 01), new GregorianCalendar(ANO_REFERENCIA, 10, 5), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 11, 28), new GregorianCalendar(ANO_REFERENCIA, 11, 2), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 12, 01), new GregorianCalendar(ANO_REFERENCIA, 11, 3), ""},
            {new DekatrianCalendar(ANO_REFERENCIA, 12, 28), new GregorianCalendar(ANO_REFERENCIA, 11, 30), ""}
        /*,
            {new DekatrianCalendar(ANO_REFERENCIA + 1, 0, 1), new GregorianCalendar(ANO_REFERENCIA, 11, 31), "Dia fora do tempo, cíclico."}
         */
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

    @Test
    public void convergencia() {

        assertEquals(this.mensagem,
                     SDF.format(this.esperado.getTime()),
                     SDF.format(this.informado.toGregorian().getTime()));
    }
}
