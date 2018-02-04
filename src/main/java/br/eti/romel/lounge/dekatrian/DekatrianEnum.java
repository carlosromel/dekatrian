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

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public enum DekatrianEnum {

    Auroran(0),
    Borean(1),
    Coronian(2),
    Driadan(3),
    Electran(4),
    Faian(5),
    Gaian(6),
    Hermetian(7),
    Irisian(8),
    Kaosian(9),
    Lunan(10),
    Maian(11),
    Nixian(12);

    private final int id;
    private static final String[] MONTH_NAME = {
        "Auroran",
        "Borean",
        "Coronian",
        "Driadan",
        "Electran",
        "Faian",
        "Gaian",
        "Hermetian",
        "Irisian",
        "Kaosian",
        "Lunan",
        "Maian",
        "Nixian"
    };

    DekatrianEnum(int id) {
        this.id = id;
    }

    public int getMonth() {

        return this.id;
    }

    public String getMonthName() {

        return MONTH_NAME[this.id];
    }

    public static String getMonthName(int id) {

        return MONTH_NAME[id];
    }
}
