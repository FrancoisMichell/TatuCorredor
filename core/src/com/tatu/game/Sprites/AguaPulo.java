package com.tatu.game.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.tatu.game.Scenes.Hud;
import com.tatu.game.Screens.PlayScreen;
import com.tatu.game.TatuBola;

public class AguaPulo extends InteractiveTileObject {

    PlayScreen screen;

    public AguaPulo(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds, true);
        this.screen = screen;
        fixture.setUserData(this);

        setCategoryFilter(TatuBola.PULO_BIT);
    }

    @Override
    public void onHeadHit() {
        setCategoryFilter(TatuBola.DESTRUIDO_BIT);
        screen.getHud().setAguaPuloScoreValue(screen.getHud().getAguaPuloScoreValue()+1);
        getCell().setTile(null);

    }
}
