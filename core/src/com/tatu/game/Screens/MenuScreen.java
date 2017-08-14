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

class MenuScreen implements Screen {

    private Skin skin;
    private Stage stage;
    private TatuBola game;

    //private LevelScreen levelScreen = new LevelScreen();
    //private OptionsScreen optionsScreen = new OptionsScreen();
    //private CreditsScreen creditsScreen = new CreditsScreen();

    MenuScreen(TatuBola game) {

        this.game = game;

        //TODO corrigir problema com viewPort no menu
        OrthographicCamera cam = new OrthographicCamera();
        Viewport viewport = new FitViewport(800, 480, cam);
        stage = new Stage(viewport, TatuBola.batch);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

        skin = new Skin(Gdx.files.internal("menu/menu.json"),new TextureAtlas("menu/menu.pack"));

        Image background = new Image(skin, "menuscreen");
        background.setPosition(0,0);
        //background.setPosition(Gdx.graphics.getWidth()/2 - 800/2f , Gdx.graphics.getHeight()/2 - 300f);
        stage.addActor(background);

        //bgMenu = new Image(skin,"menu_painel");
        //bgMenu.setPosition( (800 - 637)/2 , (480 - 539)/2 ) ;
        //stage.addActor(bgMenu);

        Button jogarButton = new Button(skin, "jogarButton");
        jogarButton.setPosition( 650 , 10+116+10+116+10) ;
        stage.addActor(jogarButton);

        Button configuracoesButton = new Button(skin, "configuracoesButton");
        configuracoesButton.setPosition( 650 , 10+116+10) ;
        stage.addActor(configuracoesButton);

        Button sairButton = new Button(skin, "sairButton");
        sairButton.setPosition( 650 , 10) ;
        stage.addActor(sairButton);

        jogarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game));
                dispose();
            }
        });

        sairButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
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
