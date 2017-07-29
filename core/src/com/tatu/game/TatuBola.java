package com.tatu.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tatu.game.Screens.PlayScreen;

public class TatuBola extends Game {
    public static final int V_WIDTH = 360;
    public static final int V_HEIGHT = 224;
    public static final float PPM = 100;

    public static SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new PlayScreen());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
