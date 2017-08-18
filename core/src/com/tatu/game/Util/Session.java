package com.tatu.game.Util;

import com.tatu.game.entity.Usuario;

/**
 * Created by uehara on 17/08/17.
 */

public class Session {

    private static Usuario user;

    public static Usuario getUsuarioLogado() {
        return user;
    }

    public static void setUsuarioLogado(Usuario usuario) {
        user = usuario;
    }

}
