package com.tatu.game.entity;

/**
 * Created by uehara on 17/08/17.
 */

import java.util.ArrayList;

public class Usuario {

    private String nome;
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
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }

}
