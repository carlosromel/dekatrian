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
import lombok.*;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class ServicoTagLines extends ServicoBase {

    private final String defaultURL = "http://taglines.herokuapp.com/v1/tagline";

    public String getRandomTagLines() {

        return getRandomTagLines(this.defaultURL);
    }

    public String getRandomTagLines(String url) {
        String conteudo = getContent(url);
        TagLine json = new Gson().fromJson(conteudo, TagLine.class);

        return json.getTagline();
    }
}

@Getter
class TagLine {

    String tagline;
}
