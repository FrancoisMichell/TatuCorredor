package com.tatu.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.tatu.game.Scenes.Hud;
import com.tatu.game.Screens.PlayScreen;
import com.tatu.game.TatuBola;

public class Agua extends InteractiveTileObject {

    public Agua(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);

        fixture.setUserData(this);

        setCategoryFilter(TatuBola.AGUA_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Agua", "collision");
        setCategoryFilter(TatuBola.DESTRUIDO_BIT);
        Hud.setAguaCarreraScoreValueGlobal(Hud.getAguaCarreraScoreValueGlobal()+1);
        // Hud.addScore(200);
        getCell().setTile(null);

    }
}
