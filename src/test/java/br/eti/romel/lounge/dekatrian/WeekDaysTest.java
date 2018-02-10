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
public class WeekDaysTest {

    private final String informado;
    private final String esperado;

    public WeekDaysTest(String informado, String esperado) {
        this.informado = informado;
        this.esperado = esperado;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {

        return Arrays.asList(new Object[][]{
            {Week.shortWeekDays().get(0), "Dom"},
            {Week.shortWeekDays().get(1), "Seg"},
            {Week.shortWeekDays().get(2), "Ter"},
            {Week.shortWeekDays().get(3), "Qua"},
            {Week.shortWeekDays().get(4), "Qui"},
            {Week.shortWeekDays().get(5), "Sex"},
            {Week.shortWeekDays().get(6), "Sáb"},
            {Week.weekDays().get(0), "Domingo"},
            {Week.weekDays().get(1), "Segunda"},
            {Week.weekDays().get(2), "Terça"},
            {Week.weekDays().get(3), "Quarta"},
            {Week.weekDays().get(4), "Quinta"},
            {Week.weekDays().get(5), "Sexta"},
            {Week.weekDays().get(6), "Sábado"}
        });
    }

    @Test
    public void equivalencia() {

        assertEquals(this.esperado, this.informado);
    }
}
