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
import java.math.*;
import lombok.*;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public class ServicoExtenso extends ServicoBase {

    private final String defaultURL = "http://extenso.herokuapp.com/v1/extenso";

    public String getExtenso(BigDecimal valor) {

        return getExtenso(this.defaultURL, valor);
    }

    public String getExtenso(String url, BigDecimal valor) {
        String conteudo = getContent(String.format("%s/%s", url, valor));
        Extenso json = new Gson().fromJson(conteudo, Extenso.class);

        return json.getExtenso();
    }
}

@Getter
class Extenso {

    String extenso;
}
