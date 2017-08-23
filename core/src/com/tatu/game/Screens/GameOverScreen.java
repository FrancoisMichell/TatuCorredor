package com.tatu.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    Skin skin;

    public GameOverScreen(final TatuBola game) {
        this.game = game;

        OrthographicCamera cam = new OrthographicCamera();
        Viewport viewport = new FitViewport(800, 480, cam);
        //Viewport viewport = new FitViewport(V_WIDTH, V_HEIGHT, cam);
        stage = new Stage(viewport, TatuBola.batch);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

        //migliaLabel = new Label("label", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("Kalam-Regular.fnt")), Color.MAGENTA));
        skin = new Skin(Gdx.files.internal("menu/menu.json"),new TextureAtlas("menu/menu.pack"));

        Image background = new Image(skin, "menuscreen");
        background.setPosition(0,0);
        stage.addActor(background);

        Image gameOver = new Image(skin,"gameOver");
        gameOver.setPosition( ( 800 - 475)/2 , (480 - 420)/2 ) ;
        stage.addActor(gameOver);

        Button gameOverSim = new Button(skin,"gameOverSim");
        gameOverSim.setPosition(250,250);
        gameOverSim.addListener(new ClickListener(){

        });

        Button gameOverNao = new Button(skin,"gameOverSim");
        gameOverNao.setPosition(250,250);
        gameOverNao.addListener(new ClickListener(){

        });

        gameOverSim.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game, 1));
                dispose();
            }
        });


/*        exitLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new MenuScreen( game));
                TatuBola game2 = new TatuBola();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen(game2));
                dispose();
            }
        });
*/
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        //if (Gdx.input.justTouched()) {
        //    game.setScreen(new PlayScreen((TatuBola) game));
        //    dispose();
        //}
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