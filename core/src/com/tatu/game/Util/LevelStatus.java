package com.tatu.game.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Matheus Uehara on 16/08/2017.
 */

public class LevelStatus {

    public static int[] levels = new int[8];
    public static Preferences prefs;
    public static Integer levelClear;
    public static final String PREFNAME = "TatuBolaPrefs";

    public static void put(){
        // 0 = LIBERADO , 1 = 1STAR , 2 = 2STAR , 3 = 3STAR, 4 = BLOQUEADO
        prefs = Gdx.app.getPreferences(PREFNAME);
        prefs.putInteger("levelClear",9);
        prefs.putInteger("level1",0);
        prefs.putInteger("level2",4);
        prefs.putInteger("level3",4);
        prefs.putInteger("level4",4);
        prefs.putInteger("level5",4);
        prefs.putInteger("level6",4);
        prefs.putInteger("level7",4);
        prefs.putInteger("level8",4);
        prefs.flush();
    }

    public static void get(){
        prefs = Gdx.app.getPreferences(PREFNAME);
        levelClear = prefs.getInteger("levelClear",0);
        levels[0] = prefs.getInteger("level1",0);
        levels[1] = prefs.getInteger("level2",0);
        levels[2] = prefs.getInteger("level3",0);
        levels[3] = prefs.getInteger("level4",0);
        levels[4] = prefs.getInteger("level5",0);
        levels[5] = prefs.getInteger("level6",0);
        levels[6] = prefs.getInteger("level7",0);
        levels[7] = prefs.getInteger("level8",0);
    }

    public static void updateLevel(int level, int value){
        prefs = Gdx.app.getPreferences(PREFNAME);
        levels[level] = prefs.getInteger("level"+level,value);
    }

}
