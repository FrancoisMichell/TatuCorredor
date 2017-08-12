package com.tatu.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tatu.game.Sprites.Tatu;
import com.tatu.game.TatuBola;

import static com.tatu.game.TatuBola.PPM;
import static com.tatu.game.TatuBola.V_HEIGHT;
import static com.tatu.game.TatuBola.V_WIDTH;


/**
 * Created by Matheus Uehara on 11/08/2017.
 */

public class MenuScreen implements Screen {

    private Skin skin;
    private Stage stage;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    //private LevelScreen levelScreen = new LevelScreen();
    //private OptionsScreen optionsScreen = new OptionsScreen();
    //private CreditsScreen creditsScreen = new CreditsScreen();
    private Image bgMenu;
    private Image background;
    private Button jogarButton;
    private Button configuracoesButton;
    private Button sairButton;

    public MenuScreen(){
        //TODO corrigir problema com viewPort no menu
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

        skin = new Skin(Gdx.files.internal("menu/menu.json"),new TextureAtlas("menu/menu.pack"));

        background= new Image(skin,"menuscreen");
        background.setPosition(Gdx.graphics.getWidth()/2 - 800/2f , Gdx.graphics.getHeight()/2 - 300f);
        stage.addActor(background);

        bgMenu = new Image(skin,"menu_painel");
        bgMenu.setPosition(Gdx.graphics.getWidth()/2 - 637/2f, Gdx.graphics.getHeight()/2 - 225f );
        stage.addActor(bgMenu);

        jogarButton = new Button(skin,"jogarButton");
        jogarButton.setPosition(Gdx.graphics.getWidth()/2- 165/2f, Gdx.graphics.getHeight()/2 + 89f);
        stage.addActor(jogarButton);

        configuracoesButton= new Button(skin,"configuracoesButton");
        configuracoesButton.setPosition(Gdx.graphics.getWidth()/2- 219/2f, Gdx.graphics.getHeight()/2 - 0f);
        stage.addActor(configuracoesButton);

        sairButton= new Button(skin,"sairButton");
        sairButton.setPosition(Gdx.graphics.getWidth()/2- 144/2f, Gdx.graphics.getHeight()/2 - 89f);
        stage.addActor(sairButton);

        jogarButton.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new PlayScreen(new TatuBola()));
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
