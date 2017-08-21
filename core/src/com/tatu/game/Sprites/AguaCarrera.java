package com.tatu.game.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.tatu.game.Scenes.Hud;
import com.tatu.game.Screens.PlayScreen;
import com.tatu.game.TatuBola;

public class AguaCarrera extends InteractiveTileObject {

    public AguaCarrera(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);

        fixture.setUserData(this);

        setCategoryFilter(TatuBola.CARRERA_BIT);
    }

    @Override
    public void onHeadHit() {
        setCategoryFilter(TatuBola.DESTRUIDO_BIT);
        Hud.setAguaCarreraScoreValueGlobal(Hud.getAguaCarreraScoreValueGlobal()+1);
        getCell().setTile(null);

    }
}
