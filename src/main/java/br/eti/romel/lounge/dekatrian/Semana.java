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
import lombok.*;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
@AllArgsConstructor
@Data
public class Semana {
    private Integer numeroSemana;
    private Integer domingo;
    private Integer segunda;
    private Integer terca;
    private Integer quarta;
    private Integer quinta;
    private Integer sexta;
    private Integer sabado;

    public Semana(Integer numeroSemana, List<Integer> d) {
        this(numeroSemana, d.get(0), d.get(1), d.get(2), d.get(3), d.get(4), d.get(5), d.get(6));
    }
}