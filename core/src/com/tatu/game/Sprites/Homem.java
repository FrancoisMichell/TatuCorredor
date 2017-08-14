package com.tatu.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.tatu.game.Screens.PlayScreen;

public class Homem extends InteractiveTileObject {

    public Homem(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);

        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Homem", "collision");
    }
}
