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
package br.eti.romel.lounge.dekatrian.chatbot;

import br.eti.romel.lounge.dekatrian.chatbot.servico.*;
import java.math.*;
import org.telegram.telegrambots.api.objects.*;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class DekatrianBotEngine {

    private static final String CHALLENGE = "/challenge";
    private static final String MONEY = "/money";
    private static final String TAGLINE = "/tagline";
    private static final String TODAY = "/today";

    public static String getResultMessage(Message mensagem) {
        String resposta = "";
        String[] tokens = mensagem.getText().split(" ");

        if (tokens.length > 0) {
            switch (tokens[0].toLowerCase()) {
                case CHALLENGE:
                    if (tokens.length > 1) {
                        resposta = String.format("%s?, _hold my beer_.", tokens[1]);
                    } else {
                        resposta = "_You win_!";
                    }
                    break;
                case MONEY:
                    if (tokens.length > 1) {
                        BigDecimal valor = new BigDecimal(tokens[1]);
                        resposta = new ServicoExtenso().getExtenso(valor);
                    }
                    break;
                case TAGLINE:
                    resposta = new ServicoTagLines().getRandomTagLines();
                    break;
                case TODAY:
                    resposta = new ServicoDekatrian().getDekatrian();
                    break;
            }
        }

        return resposta;
    }
}
