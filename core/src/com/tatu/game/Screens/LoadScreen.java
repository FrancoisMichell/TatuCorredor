package com.tatu.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tatu.game.TatuBola;

/**
 * Created by uehara on 23/08/17.
 */

public class LoadScreen {

    /*
    private int level;
    private TatuBola game;
    private Skin skin;

    public LoadScreen(TatuBola game, int level){
        this.game = game;

        // which assets do we want to be loaded
        game.manager =new AssetManager();

        game.manager.load("ui/myskin.json", Skin.class);
        game.manager.load("graphics/testPack.atlas",TextureAtlas.class);
        game.manager.load("ui/test.fnt",BitmapFont.class);
    }

    @Override
    public void show() {
        font=new BitmapFont();
        batch=new SpriteBatch();
        emptyT=new Texture(Gdx.files.internal("load/empty.png"));
        fullT=new Texture(Gdx.files.internal("load/full.png"));
        empty=new NinePatch(new TextureRegion(emptyT,24,24),8,8,8,8);
        full=new NinePatch(new TextureRegion(fullT,24,24),8,8,8,8);

    }

    @Override
    public void render(float delta) {

        batch.begin();
        empty.draw(batch, 40, 225, 720, 30);
        full.draw(batch, 40, 225, game.assets.getProgress()*720, 30);
        font.drawMultiLine(batch,(int)(game.assets.getProgress()*100)+"% loaded",400,247,0, BitmapFont.HAlignment.CENTER);
        batch.end();


        if(game.manager.update()){
            // all the assets are loaded
            game.setScreen(new PlayScreen(game,level));
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    */
}
