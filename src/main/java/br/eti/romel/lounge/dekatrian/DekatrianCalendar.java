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

/**
 * Representação do calendário Dekatrian.
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 *
 * @see <a href="http://dekatrian.com/">Calendário Dekatrian</a>
 *
 * Conversão livre do projeto dekaph
 * @see <a href="https://github.com/vitorteccom/dekaph">Dekatrian to
 * Gregorian</a>
 * @see <a href="https://apps.vitortec.com/calendar-dekatrian/">Exemplo do
 * Calendário Dekatrian</a>
 */
public final class DekatrianCalendar {

    private int year = -1;
    private int month = -1;
    private int day = -1;

    /**
     * Cria uma data Dekatrian baseada em sua equivalente Gregorian.
     *
     * @param date Em formato Gregorian.
     */
    public DekatrianCalendar(Calendar date) {
        DekatrianCalendar dekatrian = gregToDeka(date);
        this.year = dekatrian.getYear();
        this.month = dekatrian.getMonth();
        this.day = dekatrian.getDay();
    }

    /**
     * Cria uma data Dekatrian baseada no dia corrente.
     */
    public DekatrianCalendar() {
        this(Calendar.getInstance());
    }

    /**
     * Cria uma data Dekatrian pura.
     *
     * @param year  Ano.
     * @param month Mês.
     * @param day   Dia.
     */
    public DekatrianCalendar(int year, int month, int day) {
        if ((year > 0)
            && (month >= 0 && month <= 14)
            && (day > 0 && day <= 28)) {
            this.year = year;
            this.month = month;
            this.day = day;
        }
    }

    public int getYear() {

        return this.year;
    }

    public int getMonth() {

        return this.month;
    }

    public int getDay() {

        return this.day;
    }

    /**
     * Converte uma data DekatrianCalendar para o equivalente Gregoriano.
     *
     * @return Data gregoriana.
     */
    public Calendar toGregorian() {
        int daysInYear = (this.month * 28) - 28 + this.day;
        Calendar greg = new GregorianCalendar(this.year, 0, 1);

        if (new GregorianCalendar().isLeapYear(this.year)) {
            ++daysInYear;
        }

        // Synchronian day || Achronian day
        if (this.month == 0 && (this.day == 1 || this.day == 2)) {
            greg.set(Calendar.DAY_OF_MONTH, this.day);
        } else {
            greg.add(Calendar.DAY_OF_YEAR, daysInYear);
        }

        return greg;
    }

    /**
     * Converte uma data Gregoriana no equivalente DekatrianCalendar
     *
     * @param date Data gregoriana.
     *
     * @return Uma data DekatrianCalendar.
     */
    public DekatrianCalendar gregToDeka(Calendar date) {
        int daysInYear = date.get(Calendar.DAY_OF_YEAR);
        int year = date.get(Calendar.YEAR);
        int month;
        int day = 1;

        if (new GregorianCalendar().isLeapYear(year)
            && date.get(Calendar.MONTH) == 0
            && (date.get(Calendar.DAY_OF_MONTH) == 1
                || date.get(Calendar.DAY_OF_MONTH) == 2)) {
            month = 0;
            day = date.get(Calendar.DAY_OF_MONTH);
        } else if (date.get(Calendar.MONTH) == 0
                   && date.get(Calendar.DAY_OF_MONTH) == 1) {
            month = 0;
        } else if (date.get(Calendar.MONTH) == 0
                   && date.get(Calendar.DAY_OF_MONTH) == 2) {
            month = 1;
        } else {
            month = (int) Math.floor(daysInYear / 28);
            day = daysInYear - (month * 28);
            ++month;

            if (day == 1) {
                day = 28;
                --month;
            }
        }

        return new DekatrianCalendar(year, month, day);
    }

    public final Date getTime() {

        return toGregorian().getTime();
    }

    @Override
    public String toString() {

        return String.format("%04d\\%02d\\%02d",
                             this.year,
                             this.month,
                             this.day);
    }

    public String toHuman() {
        String monthName = DekatrianEnum.getMonthName(this.month);
        Calendar gregorian = toGregorian();

        if (gregorian.get(Calendar.MONTH) == 0
            && gregorian.get(Calendar.DAY_OF_MONTH) == 1) {
            monthName = "Anachronian";
        } else if (new GregorianCalendar().isLeapYear(this.year)
                   && gregorian.get(Calendar.MONTH) == 0
                   && gregorian.get(Calendar.DAY_OF_MONTH) == 2) {
            monthName = "Sincronian";
        }

        return String.format("%02d %s %04d", this.day, monthName, this.year);
    }

    private DekatrianCalendar moveMonth(int months) {
        int year = this.year;
        int month = this.month;
        int day = this.day;

        month += months;
        if (month > 14) {
            month = 0;
            ++year;
        } else if (month < 0) {
            month = 14;
            --year;
        }

        return new DekatrianCalendar(year, month, day);
    }

    public DekatrianCalendar nextMonth() {

        return moveMonth(1);
    }

    public DekatrianCalendar previousMonth() {

        return moveMonth(-1);
    }

    public int getWeek() {

        return toGregorian().get(Calendar.WEEK_OF_YEAR);
    }

    public boolean isValid() {

        return this.year > 0 && this.month >= 0 && this.day > 0;
    }
}
