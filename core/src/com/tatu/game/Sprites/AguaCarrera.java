package com.tatu.game.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.tatu.game.Scenes.Hud;
import com.tatu.game.Screens.PlayScreen;
import com.tatu.game.TatuBola;

public class AguaCarrera extends InteractiveTileObject {

    PlayScreen screen;

    public AguaCarrera(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);
        this.screen = screen;
        fixture.setUserData(this);

        setCategoryFilter(TatuBola.CARRERA_BIT);
    }

    @Override
    public void onHeadHit() {
        setCategoryFilter(TatuBola.DESTRUIDO_BIT);
        screen.getHud().setAguaCarreraScoreValue(screen.getHud().getAguaCarreraScoreValue()+1);
        getCell().setTile(null);

    }
}
