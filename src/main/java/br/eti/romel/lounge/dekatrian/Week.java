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
 * Semana fluída, necessária para o calendário Dekatrian.
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
@AllArgsConstructor
@Data
public class Week {

    private Integer numeroSemana;
    private Integer domingo;
    private Integer segunda;
    private Integer terca;
    private Integer quarta;
    private Integer quinta;
    private Integer sexta;
    private Integer sabado;

    /**
     * Construção de uma semana do calendário Dekatrian.
     *
     * @param numeroSemana Informa o número da semana Dekatrian.
     * @param data         Data Dekatrian.
     */
    public Week(Integer numeroSemana, List<Integer> data) {
        this(numeroSemana, data.get(0), data.get(1), data.get(2), data.get(3), data.get(4), data.get(5), data.get(6));
    }

    /**
     * Retorna os dias da semana, de domingo a sábado (conforme a ordem
     * prescrita na classe GregorianCalendar), do ano atual.
     *
     * @return Lista de dias da semana.
     */
    public static List<String> weekDays() {

        return weekShift(0);
    }

    /**
     * Retorna os dias da semana, de domingo a sábado (conforme a ordem
     * prescrita na classe GregorianCalendar).
     *
     * @param dekatrian Data dekatrian. Somente o ano é necessário.
     *
     * @return Lista de dias da semana, variável conforme o primeiro dia do ano.
     */
    public static List<String> weekDays(DekatrianCalendar dekatrian) {
        Calendar firstDay = new GregorianCalendar(dekatrian.getYear(), 0, 1);
        int positions = firstDay.get(Calendar.DAY_OF_WEEK);

        return weekShift(positions - 1);
    }

    /**
     * Retorna os dias da semana, fluidos pela quantidade de dias do ano, apenas
     * com as iniciais do ano atual.
     *
     * @return Lista de dias da semana, variável conforme o primeiro dia do ano.
     */
    public static List<String> shortWeekDays() {

        return shortWeekShift(0);
    }

    /**
     * Retorna os dias da semana, fluidos pela quantidade de dias do ano, apenas
     * com as iniciais.
     *
     * @param dekatrian Data dekatrian. Somente o ano é necessário.
     *
     * @return Lista de dias da semana, variável conforme o primeiro dia do ano.
     */
    public static List<String> shortWeekDays(DekatrianCalendar dekatrian) {
        final int year = dekatrian.getYear();
        final int day = dekatrian.isLeap() ? 3 : 2;
        final GregorianCalendar ref = new GregorianCalendar(year, 0, day);
        final int position = ref.get(Calendar.DAY_OF_WEEK);

        return shortWeekShift(position - 1);
    }

    /**
     * Retorna os dias da semana, fluidos pela quantidade de dias do ano atual,
     * em nomes longos.
     *
     * @param positions Posições para a movimentação dos dias da semana.
     *
     * @return Lista de dias da semana, variável conforme o primeiro dia do ano.
     */
    public static List<String> weekShift(int positions) {
        List<String> days = Arrays.asList("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado");

        return weekShift(positions, days);
    }

    /**
     * Retorna os dias da semana, fluidos pela quantidade de dias do ano atual,
     * apenas com as iniciais.
     *
     * @param positions Posições para a movimentação dos dias da semana.
     *
     * @return Lista de dias da semana, variável conforme o primeiro dia do ano.
     */
    public static List<String> shortWeekShift(int positions) {
        List<String> days = Arrays.asList("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb");

        return weekShift(positions, days);
    }

    /**
     * Retorna o primeiro dia da semana para o calendário Dekatrian. O retorno
     * segue a convenção Calendar.DAY_OF_WEEK, para o ano atual.
     *
     * @return Referência ao dia da semana.
     */
    public int firstDay() {

        return firstDay(new DekatrianCalendar());
    }

    /**
     * Retorna o primeiro dia da semana para o calendário Dekatrian. O retorno
     * segue a convenção Calendar.DAY_OF_WEEK.
     *
     * @param dekatrian
     *
     * @return Referência ao dia da semana.
     */
    public static int firstDay(DekatrianCalendar dekatrian) {
        int year = dekatrian.getYear();
        int day = dekatrian.isLeap() ? 3 : 2;
        Calendar firstDay = new GregorianCalendar(year, 0, day);

        return firstDay.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Retorna os dias da semana, fluídos pela quantidade de dias informado.
     *
     * @param positions Posições para a movimentação dos dias da semana.
     *
     * @return Lista de dias da semana.
     */
    private static List<String> weekShift(int positions, List<String> days) {

        if (positions != 0) {
            if (positions > 0) {
                for (int j = 0; j < positions; ++j) {
                    String position = days.get(0);
                    int i;
                    for (i = 0; i < days.size() - 1; ++i) {
                        days.set(i, days.get(i + 1));
                    }
                    days.set(i, position);
                }
            } else if (positions < 0) {
                for (int j = 0; j < Math.abs(positions); ++j) {
                    String position = days.get(days.size() - 1);
                    int i;
                    for (i = days.size() - 1; i > 0; --i) {
                        days.set(i, days.get(i - 1));
                    }
                    days.set(i, position);
                }
            }
        }

        return days;
    }

    public long getMillis(int year, int month, int day) {

        return new GregorianCalendar(year, month, day).getTimeInMillis();
    }

    public List<Integer> getDays() {

        return Arrays.asList(this.domingo, this.segunda, this.terca, this.quarta, this.quinta, this.sexta, this.sabado);
    }
}
