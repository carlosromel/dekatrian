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

import java.io.*;
import java.util.logging.*;
import org.apache.http.client.fluent.*;

/**
 *
 * @author Carlos Romel Pereira da Silva, carlos.romel@gmail.com
 */
public abstract class ServicoBase {

    public String getContent(String url) {
        String conteudo = "";

        try {
            Request servico = Request.Get(url);
            conteudo = new String(servico.execute().returnContent().asBytes());
        } catch (IOException ex) {
            Logger.getLogger(ServicoTagLines.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conteudo;

    }
}
