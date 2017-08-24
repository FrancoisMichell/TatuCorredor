package com.tatu.game.entity;

/**
 * Created by uehara on 17/08/17.
 */

import java.util.ArrayList;

public class Usuario {

    private int aguaCarreraMoney;
    private int aguaPuloMoney;
    private int aguaFreioMoney;

    private float aguaCarreraPower;
    private float aguaPuloPower;
    private float aguaFreioPower;

    private boolean viuTutorial;

    private ArrayList<Level> levels;

    public Usuario(){
        this.levels = new ArrayList<Level>();

        Level level1 = new Level(1,0,false);
        Level level2 = new Level(2,0,true);
        Level level3 = new Level(3,0,true);
        Level level4 = new Level(4,0,true);
        Level level5 = new Level(5,0,true);
        Level level6 = new Level(6,0,true);
        Level level7 = new Level(7,0,true);
        Level level8 = new Level(8,0,true);

        levels.add(level1);
        levels.add(level2);
        levels.add(level3);
        levels.add(level4);
        levels.add(level5);
        levels.add(level6);
        levels.add(level7);
        levels.add(level8);

        this.aguaCarreraMoney = 10;
        this.aguaPuloMoney = 10;
        this.aguaFreioMoney = 0;

        //DEFINIR
        this.aguaCarreraPower = 0f;
        this.aguaPuloPower = 0f;
        this.aguaFreioPower = 0f;

        this.viuTutorial = false;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }

    public int getAguaCarreraMoney() {
        return aguaCarreraMoney;
    }

    public void setAguaCarreraMoney(int aguaCarreraMoney) {
        this.aguaCarreraMoney = aguaCarreraMoney;
    }

    public int getAguaPuloMoney() {
        return aguaPuloMoney;
    }

    public void setAguaPuloMoney(int aguaPuloMoney) {
        this.aguaPuloMoney = aguaPuloMoney;
    }

    public int getAguaFreioMoney() {
        return aguaFreioMoney;
    }

    public void setAguaFreioMoney(int aguaFreioMoney) {
        this.aguaFreioMoney = aguaFreioMoney;
    }

    public float getAguaCarreraPower() {
        return aguaCarreraPower;
    }

    public void setAguaCarreraPower(float aguaCarreraPower) {
        this.aguaCarreraPower = aguaCarreraPower;
    }

    public float getAguaPuloPower() {
        return aguaPuloPower;
    }

    public void setAguaPuloPower(float aguaPuloPower) {
        this.aguaPuloPower = aguaPuloPower;
    }

    public float getAguaFreioPower() {
        return aguaFreioPower;
    }

    public void setAguaFreioPower(float aguaFreioPower) {
        this.aguaFreioPower = aguaFreioPower;
    }

    public boolean isViuTutorial() {
        return viuTutorial;
    }

    public void setViuTutorial(boolean viuTutorial) {
        this.viuTutorial = viuTutorial;
    }
}
