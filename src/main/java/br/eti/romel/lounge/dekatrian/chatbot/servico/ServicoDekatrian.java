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
package br.eti.romel.lounge.dekatrian.chatbot.servico;

import com.google.gson.*;
import java.text.*;
import java.util.*;
import lombok.*;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class ServicoDekatrian extends ServicoBase {

    private final String defaultURL = "http://dekatrian.herokuapp.com/v1/dekatrian";

    public String getDekatrian() {

        return getDekatrian(new GregorianCalendar());
    }

    public String getDekatrian(Calendar gregorian) {

        return getDekatrian(this.defaultURL, gregorian);
    }

    public String getDekatrian(String url, Calendar gregorian) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String hoje = sdf.format(gregorian.getTime());
        String conteudo = getContent(String.format("%s/%s", url, hoje));
        Gregorian json = new Gson().fromJson(conteudo, Gregorian.class);

        return json.getDekatrianHuman();
    }
}

@Getter
class Gregorian {

    String dekatrianHuman;
}
