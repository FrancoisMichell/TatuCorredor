package com.tatu.game.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;
import com.tatu.game.entity.Level;
import com.tatu.game.entity.Usuario;


/**
 * Created by Matheus Uehara on 16/08/2017.
 */

public class BdManager {

    private static final String SHARED_PREF_NAME = "TatuBolaPrefs";
    private static final String FULL_USER = "full_user";
    public static Preferences prefs;
    private static BdManager mInstance;

    private BdManager() {}

    public static synchronized BdManager getInstance() {
        if (mInstance == null) {
            mInstance = new BdManager();
        }
        return mInstance;
    }

    public void saveUserInSharedPref(Usuario usuario){
        Gson gson = new Gson();
        String json = gson.toJson(usuario);
        prefs = Gdx.app.getPreferences(SHARED_PREF_NAME);
        prefs.putString(FULL_USER, json);
        prefs.flush();
    }

    public Usuario getUserFromSharedPref(){
        Gson gson = new Gson();
        prefs = Gdx.app.getPreferences(SHARED_PREF_NAME);
        String json = prefs.getString(FULL_USER,"");
        Usuario usuario = gson.fromJson(json, Usuario.class);
        if ( json != "" ){
            return usuario;
        }else{
            return null;
        }
    }

}
