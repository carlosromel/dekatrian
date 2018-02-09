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
     * prescrita na classe GregorianCalendar.
     *
     * @return Lista de dias da semana.
     */
    public static List<String> weekDays() {

        return weekShift(0);
    }

    /**
     * Retorna os dias da semana, fluída, conforme o primeiro dia do ano.
     *
     * @param gregorian Data gregoriana. Somente o ano é necessário.
     *
     * @return Lista de dias da semana, variável conforme o primeiro dia do ano.
     */
    public static List<String> weekDays(Calendar gregorian) {
        Calendar firstDay = new GregorianCalendar(gregorian.get(Calendar.YEAR), 0, 1);
        int positions = firstDay.get(Calendar.DAY_OF_WEEK);

        return weekShift(positions);
    }

    /**
     * Retorna os dias da semana, fluída, conforme o primeiro dia do ano.
     *
     * @param dekatrian Data dekatrian. Somente o ano é necessário.
     *
     * @return Lista de dias da semana, variável conforme o primeiro dia do ano.
     */
    public static List<String> weekDays(DekatrianCalendar dekatrian) {
        Calendar firstDay = new GregorianCalendar(dekatrian.getYear(), 0, 1);
        int positions = firstDay.get(Calendar.DAY_OF_WEEK);

        return weekShift(positions);
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
        List<String> days = Arrays.asList("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb");
        Calendar firstDay = new GregorianCalendar(dekatrian.getYear(), 0, 1);
        int positions = firstDay.get(Calendar.DAY_OF_WEEK);

        return weekShift(positions, days);
    }

    /**
     * Retorna os dias da semana, fluidos pela quantidade de dias do ano, em
     * nomes longos.
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
     * Retorna os dias da semana, fluídos pela quantidade de dias informado.
     *
     * @param positions Posições para a movimentação dos dias da semana.
     *
     * @return Lista de dias da semana.
     */
    private static List<String> weekShift(int positions, List<String> days) {

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

        return days;
    }
}
