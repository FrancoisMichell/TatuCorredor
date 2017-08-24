package com.tatu.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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

class MenuScreen implements Screen {

    private Skin skin;
    private Stage stage;
    private TatuBola game;
    private Sound selecionado;

    private Button jogarButton;
    private Button sairButton;
    private Button lojaButton;

    MenuScreen(TatuBola game) {

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

        skin = new Skin(Gdx.files.internal("menu/menu.json"),new TextureAtlas("menu/menu.pack"));

        Image background = new Image(skin, "menuscreen");
        background.setPosition(0,0);

        jogarButton = new Button(skin, "jogarButton");
        //jogarButton.setPosition( 650 , 10+116+10+116+10) ;
        jogarButton.setPosition(120, 0);
        jogarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selecionado.play();
                ((Game)Gdx.app.getApplicationListener()).setScreen(new LevelScreen(game));
                dispose();
            }
        });

        lojaButton = new Button(skin, "lojaButton");
        //lojaButton.setPosition( 650 , 10+116+10+116+10+116+10) ;
        lojaButton.setPosition(350, 0);

        lojaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selecionado.play();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LojaScreen(game));
                dispose();
            }
        });

        sairButton = new Button(skin, "sairButton");
        //sairButton.setPosition( 650 , 10) ;
        sairButton.setPosition(580, 0);
        sairButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selecionado.play();
                Gdx.app.exit();
            }

        });

        stage.addActor(background);
        stage.addActor(jogarButton);
        stage.addActor(sairButton);
        stage.addActor(lojaButton);
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
