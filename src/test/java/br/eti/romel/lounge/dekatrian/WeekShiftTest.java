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
public class WeekShiftTest {

    private final String informado;
    private final String esperado;

    public WeekShiftTest(String informado, String esperado) {
        this.informado = informado;
        this.esperado = esperado;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
            {Week.shortWeekShift(0).get(0), "Dom"},
            {Week.shortWeekShift(1).get(0), "Seg"},
            {Week.shortWeekShift(2).get(0), "Ter"},
            {Week.shortWeekShift(3).get(0), "Qua"},
            {Week.shortWeekShift(4).get(0), "Qui"},
            {Week.shortWeekShift(5).get(0), "Sex"},
            {Week.shortWeekShift(6).get(0), "Sáb"},
            {Week.weekShift(0).get(0), "Domingo"},
            {Week.weekShift(1).get(0), "Segunda"},
            {Week.weekShift(2).get(0), "Terça"},
            {Week.weekShift(3).get(0), "Quarta"},
            {Week.weekShift(4).get(0), "Quinta"},
            {Week.weekShift(5).get(0), "Sexta"},
            {Week.weekShift(6).get(0), "Sábado"}
        });
    }

    @Test
    public void equivalencia() {

        assertEquals(this.esperado, this.informado);
    }
}
