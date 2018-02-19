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
@Getter
public class DekatrianCalendar {

    private int year = -1;
    private int month = -1;
    private int day = -1;
    private Calendar gregorian;
    private boolean leap;

    /**
     * Cria uma data Dekatrian baseada em sua equivalente Gregorian.
     *
     * @param gregorian Em formato Gregorian.
     */
    public DekatrianCalendar(Calendar gregorian) {
        this.gregorian = gregorian;
        DekatrianCalendar dekatrian = gregToDeka(gregorian);
        this.year = dekatrian.getYear();
        this.month = dekatrian.getMonth();
        this.day = dekatrian.getDay();
        this.leap = new GregorianCalendar().isLeapYear(this.year);
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
        if (isValid(year, month, day)) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.leap = new GregorianCalendar().isLeapYear(this.year);
            this.gregorian = toGregorian();
        }
    }

    /**
     * Converte a data dekatriana instanciada para o equivalente Gregoriano.
     *
     * @return Data gregoriana.
     */
    private Calendar toGregorian() {
        Calendar greg = new GregorianCalendar(this.year, 0, 1);
        int daysInYear = (this.month == 0 ? 0 : (this.month - 1) * 28) + this.day - 1;

        if (this.month > 0) {
            daysInYear += (this.leap) ? 2 : 1;
        }

        greg.add(Calendar.DAY_OF_YEAR, daysInYear);

        return greg;
    }

    /**
     * Converte uma data Gregoriana no equivalente DekatrianCalendar.
     *
     * @param gregorian Data gregoriana.
     *
     * @return Uma data DekatrianCalendar.
     */
    public static DekatrianCalendar gregToDeka(Calendar gregorian) {
        int year = gregorian.get(Calendar.YEAR);
        final boolean leapYear = new GregorianCalendar().isLeapYear(year);
        int daysInYear = gregorian.get(Calendar.DAY_OF_YEAR) - (leapYear ? 2 : 1);
        int month;
        int day = 1;

        if (leapYear
            && gregorian.get(Calendar.MONTH) == 0
            && (gregorian.get(Calendar.DAY_OF_MONTH) == 1
                || gregorian.get(Calendar.DAY_OF_MONTH) == 2)) {
            month = 0;
            day = gregorian.get(Calendar.DAY_OF_MONTH);
        } else if (gregorian.get(Calendar.MONTH) == 0
                   && gregorian.get(Calendar.DAY_OF_MONTH) == 1) {
            month = 0;
        } else if (gregorian.get(Calendar.MONTH) == 0
                   && gregorian.get(Calendar.DAY_OF_MONTH) == 2) {
            month = 1;
        } else {
            month = (int) Math.floor(daysInYear / 28);
            day = daysInYear - (month * 28);
            ++month;

            if (day == 0) {
                day = 28;
                --month;
            }
        }

        return new DekatrianCalendar(year, month, day);
    }

    /**
     * Retorna a representação da data dekatriana em milissegundos.
     *
     * @return A quantidade de milissegundos da data instanciada.
     *
     * @see Date#getTime()
     */
    public Date getTime() {

        return this.gregorian.getTime();
    }

    /**
     * Retorna a data dekatriana no formato yyyy\MM\dd
     *
     * @return Representação da data instanciada.
     */
    @Override
    public String toString() {

        return String.format("%04d\\%02d\\%02d", this.year, this.month, this.day);
    }

    public String getSimpleFormat() {

        return String.format("%d-%d-%d", this.year, this.month, this.day);
    }

    /**
     * Retorna a data dekatriana no formato dd\MMMM\yyyy
     *
     * @return Representação legível da data instanciada.
     */
    public String toHuman() {
        String monthName = DekatrianEnum.getMonthName(this.month);

        if (this.gregorian.get(Calendar.MONTH) == 0
            && this.gregorian.get(Calendar.DAY_OF_MONTH) == 1) {
            monthName = "Achronian";
        } else if (new GregorianCalendar().isLeapYear(this.year)
                   && this.gregorian.get(Calendar.MONTH) == 0
                   && this.gregorian.get(Calendar.DAY_OF_MONTH) == 2) {
            monthName = "Sincronian";
        }

        return String.format("%02d %s %04d", this.day, monthName, this.year);
    }

    /**
     * Retorna o mês posterior ao instanciado.
     *
     * @return mês dekatriano posterior.
     */
    public DekatrianCalendar nextMonth() {

        return moveMonth(1);
    }

    /**
     * Retorna o mês anterior ao instanciado.
     *
     * @return mês dekatriano anterior.
     */
    public DekatrianCalendar previousMonth() {

        return moveMonth(-1);
    }

    /**
     * Retorna o número da semana da data dekatriana instanciada.
     *
     * @return número da semana.
     */
    public int getWeek() {

        return this.gregorian.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Determina a validade de um ano dekatriano instanciado.
     *
     * @return True para parâmetros válidos.
     */
    public boolean isValid() {

        return isValid(this.year, this.month, this.day);
    }

    /**
     * Determina a validade de um ano dekatriano.
     *
     * @param year  ano.
     * @param month mês.
     * @param day   dia.
     *
     * @return True para parâmetros válidos.
     */
    public boolean isValid(int year, int month, int day) {

        return ((year > 0)
                && ((month == 0 && ((isLeap() && day <= 2)
                                    || day < 2)
                     || (month >= 0 && month <= 14)))
                && (day >= 0 && day <= 28));
    }

    /**
     * Retorna as semanas dekatrianas do mês instanciado.
     *
     * @return Semanas do mês atual;
     */
    public List<Week> getDekatrianWeeks() {
        List<Week> weeks = new ArrayList<>();
        int w = this.getWeek();
        int d = 1;
        boolean isLeap = new GregorianCalendar().isLeapYear(this.year);

        if (this.month == 0) {
            weeks.add(new Week(0, null, null, null, null, null,
                               isLeap ? 1 : null,
                               isLeap ? 2 : 1));
        }

        weeks.addAll(Arrays.asList(new Week(w++, d++, d++, d++, d++, d++, d++, d++),
                                   new Week(w++, d++, d++, d++, d++, d++, d++, d++),
                                   new Week(w++, d++, d++, d++, d++, d++, d++, d++),
                                   new Week(w++, d++, d++, d++, d++, d++, d++, d++)));
        return weeks;
    }

    /**
     * Retorna as semanas do mês informado.
     *
     * @param gregorian Data de referência.
     *
     * @return Lista com as semanas do mës informado.
     */
    public static List<Week> getGregorianWeeks(Calendar gregorian) {
        List<Week> weeks = new ArrayList<>();
        int actualMonth = gregorian.get(Calendar.MONTH);

        while (gregorian.get(Calendar.MONTH) == actualMonth) {
            Integer weekNumber = gregorian.get(Calendar.WEEK_OF_YEAR);
            List<Integer> days = Arrays.asList(null, null, null, null, null, null, null);
            while (gregorian.get(Calendar.MONTH) == actualMonth && gregorian.get(Calendar.WEEK_OF_YEAR) == weekNumber) {
                days.set(gregorian.get(Calendar.DAY_OF_WEEK) - 1, gregorian.get(Calendar.DAY_OF_MONTH));
                gregorian.add(Calendar.DAY_OF_MONTH, 1);
            }
            weeks.add(new Week(weekNumber, days));
        }

        return weeks;
    }

    private DekatrianCalendar moveMonth(int months) {
        int year = this.year;
        int month = this.month;
        int day = this.day;

        month += months;
        if (month > 12) {
            month = 0;
            ++year;
        } else if (month < 0) {
            month = 12;
            --year;
        }

        return new DekatrianCalendar(year, month, day);
    }
}
