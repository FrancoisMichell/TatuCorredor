package com.tatu.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tatu.game.TatuBola;

/**
 * Created by uehara on 21/08/17.
 */

public class FinalLevelScreen implements Screen {
    private Skin skin;
    private Stage stage;
    private TatuBola game;

    private int mortes;
    private int tempo;
    private int aguaCarrera;
    private int aguaPulo;

    //public FinalLevelScreen(TatuBola game, int mortes , int tempo, int aguaCarrera, int aguaPulo) {
    public FinalLevelScreen(TatuBola game) {

        this.game = game;
        /*
        this.mortes = mortes;
        this.tempo = tempo;
        this.aguaCarrera = aguaCarrera;
        this.aguaPulo = aguaPulo;
        */

        OrthographicCamera cam = new OrthographicCamera();
        Viewport viewport = new FitViewport(800, 480, cam);
        //Viewport viewport = new FitViewport(V_WIDTH, V_HEIGHT, cam);
        stage = new Stage(viewport, TatuBola.batch);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

        skin = new Skin(Gdx.files.internal("menu/menu.json"),new TextureAtlas("menu/menu.pack"));

        Image background = new Image(skin, "menuscreen");
        background.setPosition(0,0);
        stage.addActor(background);

        Image gameOver = new Image(skin,"faseConcluida");
        gameOver.setPosition( ( 800 - 372)/2 , (480 - 434)/2 ) ;
        stage.addActor(gameOver);

        Image star = new Image(skin,"1star");
        star.setPosition( 260 , 270 ) ;
        stage.addActor(star);

        Button jogarButton = new Button(skin, "jogarButton");
        jogarButton.setPosition( 200 , 50) ;
        stage.addActor(jogarButton);


        Button sairButton = new Button(skin, "sairButton");
        sairButton.setPosition( 350 , 50) ;
        stage.addActor(sairButton);

        jogarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new LevelScreen(game));
                dispose();
            }
        });

        sairButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen(game));
                dispose();
            }
        });
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
        dispose();
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
