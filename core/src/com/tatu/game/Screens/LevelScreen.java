package com.tatu.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tatu.game.TatuBola;
import com.tatu.game.Util.LevelStatus;

/**
 * Created by Matheus Uehara on 16/08/2017.
 */

public class LevelScreen implements Screen {

    private Skin skin;
    private Stage stage;
    private TatuBola game;
    private LevelStatus status;


    public LevelScreen(TatuBola game){
        this.game = game;

        //TODO corrigir problema com viewPort no menu
        OrthographicCamera cam = new OrthographicCamera();
        Viewport viewport = new FitViewport(800, 480, cam);
        stage = new Stage(viewport, TatuBola.batch);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("levelsMenu/levelsMenu.json"),new TextureAtlas("levelsMenu/levelsMenu.pack"));

        Image background = new Image(skin, "menuscreen");
        background.setPosition(0,0);
        //background.setPosition(Gdx.graphics.getWidth()/2 - 800/2f , Gdx.graphics.getHeight()/2 - 300f);
        stage.addActor(background);

        Image levelsMenu = new Image(skin,"levels_menu");
        levelsMenu.setPosition( ( 800 - 475)/2 , (480 - 420)/2 ) ;
        stage.addActor(levelsMenu);
//65x69
        Image level1 = new Image(skin,"btnLevelEnable");
        level1.setPosition( (800 - (475 - 475/2)) , (480 - 420)/2 ) ;
        stage.addActor(level1);

        Image level2 = new Image(skin,"btnLevelEnable");
        level2.setPosition( (800 - (475 - 475/2)) , (480 - 420)/2 ) ;
        stage.addActor(level2);

        Image level3 = new Image(skin,"btnLevelEnable");
        level3.setPosition( (800 - (475 + 475/4)) , (480 - 420)/2 ) ;
        stage.addActor(level3);

        Image level4 = new Image(skin,"btnLevelEnable");
        level4.setPosition( (800 - (475 + 475/2)) , (480 - 420)/2 ) ;
        stage.addActor(level4);
/*
        Image level5 = new Image(skin,"btnLevelEnable");
        level5.setPosition( (800 - 475/4)/2 , (480 - 420)/2 ) ;
        stage.addActor(level5);

        Image level6 = new Image(skin,"btnLevelEnable");
        level6.setPosition( (800 - 475/3)/2 , (480 - 420)/2 ) ;
        stage.addActor(level6);

        Image level7 = new Image(skin,"btnLevelEnable");
        level7.setPosition( (800 - 475/2)/2 , (480 - 420)/2 ) ;
        stage.addActor(level7);

        Image level8 = new Image(skin,"btnLevelEnable");
        level8.setPosition( (800 - 475/1)/2 , (480 - 420)/2 ) ;
        stage.addActor(level8);
*/
        /*
        for ( int i = 0 ; i < 9 ; i ++ ){


            status.get();
            switch (status.levels[i]){
                case 0:
                    Image
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }

        }
        */
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
        skin.dispose();
        stage.dispose();
    }
}
