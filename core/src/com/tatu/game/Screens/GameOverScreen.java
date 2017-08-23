package com.tatu.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tatu.game.TatuBola;

public class GameOverScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private TatuBola game;
    private Sound selecionado;
    Skin skin;


    public GameOverScreen(final TatuBola game) {
        this.game = game;

        OrthographicCamera cam = new OrthographicCamera();
        Viewport viewport = new FitViewport(800, 480, cam);
        //Viewport viewport = new FitViewport(V_WIDTH, V_HEIGHT, cam);
        stage = new Stage(viewport, TatuBola.batch);
        Gdx.input.setInputProcessor(stage);
        selecionado = Gdx.audio.newSound(Gdx.files.internal("efeitos/selecionado.wav"));

    }

    @Override
    public void show() {
        // ADICIONAR SOM DE FALHA
        //Sound falhaLevel = Gdx.audio.newSound(Gdx.files.internal("efeitos/"));

        skin = new Skin(Gdx.files.internal("menu/menu.json"),new TextureAtlas("menu/menu.pack"));

        Image background = new Image(skin, "menuscreen");
        background.setPosition(0,0);
        stage.addActor(background);

        Image gameOver = new Image(skin,"gameOver");
        gameOver.setPosition( ( 800 - 484)/2 , (480 - 345)/2 ) ;
        stage.addActor(gameOver);

        Button gameOverSim = new Button(skin,"gameOverSim");
        gameOverSim.setPosition(265,100);
        stage.addActor(gameOverSim);

        Button gameOverNao = new Button(skin,"gameOverNao");
        gameOverNao.setPosition(425,100);
        stage.addActor(gameOverNao);

        gameOverSim.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TO DO passar o level como parametro no construtor
                selecionado.play();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game, 1));
                dispose();
            }
        });

        gameOverNao.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selecionado.play();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen(game));
                dispose();
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        stage.dispose();
    }
}