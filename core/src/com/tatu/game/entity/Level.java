package com.tatu.game.entity;

/**
 * Created by uehara on 17/08/17.
 */

public class Level {

    private Integer level;
    private Integer score;
    private Boolean locked;

    public Level(){}

    public Level(Integer level, Integer score, boolean locked){
        this.level = level;
        this.score = score;
        this.locked = locked;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
