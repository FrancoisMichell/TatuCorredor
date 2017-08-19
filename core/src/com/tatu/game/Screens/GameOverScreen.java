package com.tatu.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tatu.game.TatuBola;

public class GameOverScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private TatuBola game;

    public GameOverScreen(final TatuBola game) {
        this.game = game;

        OrthographicCamera cam = new OrthographicCamera();
        Viewport viewport = new FitViewport(800, 480, cam);
        stage = new Stage(viewport, TatuBola.batch);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", font);
        gameOverLabel.scaleBy(5);

        Label playAgainLabel = new Label("Click to Play Again", font);
        playAgainLabel.scaleBy(3);

        Label exitLabel = new Label("Click to Back Main Menu", font);
        exitLabel.scaleBy(3);

        table.add(gameOverLabel).expandX();
        table.row();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10);
        table.row();
        table.row();
        table.add(exitLabel).expandX().padTop(10);

        stage.addActor(table);

        playAgainLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new PlayScreen(game));
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game, 1));

                dispose();
            }
        });

        exitLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new MenuScreen( game));
                TatuBola game2 = new TatuBola();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen(game2));
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