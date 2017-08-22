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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tatu.game.TatuBola;
import com.tatu.game.Util.Session;

/**
 * Created by Matheus Uehara on 16/08/2017.
 */

public class LojaScreen implements Screen {

    private Skin skin;
    private Stage stage;
    private TatuBola game;

    private Label.LabelStyle font;

    private Label aguaPuloSaldoValue;
    private Label aguaCarreraSaldoValue;

    private Image aguaCarreraSaldo;
    private Image aguaPuloSaldo;

    public LojaScreen(TatuBola game){
        this.game = game;

        OrthographicCamera cam = new OrthographicCamera();
        Viewport viewport = new FitViewport(800, 480, cam);
        //Viewport viewport = new FitViewport(V_WIDTH, V_HEIGHT, cam);
        stage = new Stage(viewport, TatuBola.batch);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("loja/loja.json"),new TextureAtlas("loja/loja.pack"));

        Image background = new Image(skin, "menuscreen");
        background.setPosition(0,0);
        stage.addActor(background);

        Image lojascreen = new Image(skin,"lojascreen");
        lojascreen.setPosition( ( 800 - 475)/2 , (480 - 420)/2 ) ;
        stage.addActor(lojascreen);

        aguaCarreraSaldo = new Image(skin,"aguaCarreraSaldo");
        aguaCarreraSaldo.setPosition( 210 , 310 ) ;
        stage.addActor(aguaCarreraSaldo);

        aguaPuloSaldo = new Image(skin,"aguaPuloSaldo");
        aguaPuloSaldo.setPosition( 400 , 310 ) ;
        stage.addActor(aguaPuloSaldo);

        Button closeButton = new Button(skin,"closeButton");
        closeButton.setPosition( 600,385 );
        stage.addActor(closeButton);

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new LevelScreen(game));
                dispose();
            }
        });

        Table table = new Table();
        table.top();
        table.setFillParent(true);


        font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("fonts/cartwhel.fnt")), Color.WHITE);

        aguaCarreraSaldoValue = new Label(Session.getUsuarioLogado().getAguaCarreraMoney()+"", font);
        aguaCarreraSaldoValue.setPosition(aguaCarreraSaldo.getX()+45 , aguaCarreraSaldo.getY()+15);

        aguaPuloSaldoValue = new Label(Session.getUsuarioLogado().getAguaPuloMoney()+"", font);
        aguaPuloSaldoValue.setPosition(aguaPuloSaldo.getX()+45 , aguaPuloSaldo.getY()+15);

        stage.addActor(aguaCarreraSaldoValue);
        stage.addActor(aguaPuloSaldoValue);

        getLevelPower("carrera");
        getLevelPower("pulo");

    }

    public void getLevelPower(String power){
        if (power == "carrera"){
            float powerValue = Session.getUsuarioLogado().getAguaCarreraPower();
            String powerSkin = "lojaAguaCarrera0";
            String btnUpgrade = "comprarAguaCarrera1";
            if (powerValue == 0){
                powerSkin = "lojaAguaCarrera0";
                btnUpgrade = "comprarAguaCarrera1";
            }else if ( powerValue == 0.1f ){
                powerSkin = "lojaAguaCarrera1";
                btnUpgrade = "comprarAguaCarrera2";
            }else if ( powerValue == 0.2f ){
                powerSkin = "lojaAguaCarrera2";
                btnUpgrade = "comprarAguaCarrera3";
            }else if ( powerValue == 0.3f){
                powerSkin = "lojaAguaCarrera3";
                //String btnUpgrade = "";
            }

            Image powerLevelCarrera = new Image(skin,powerSkin);
            powerLevelCarrera.setPosition(aguaCarreraSaldo.getX(), aguaCarreraSaldo.getY()-100);
            stage.addActor(powerLevelCarrera);

            //TODO trocar tipo de Image para Button e Adicionar onClickListener de acordo com o nível que vier para realizar cobrança das aguas
            Image btnMelhorarCarrera = new Image(skin,btnUpgrade);
            btnMelhorarCarrera.setPosition(aguaPuloSaldo.getX()+50, aguaPuloSaldo.getY()-100);
            stage.addActor(btnMelhorarCarrera);

        }else if ( power == "pulo"){
            float powerValue = Session.getUsuarioLogado().getAguaPuloPower();
            String powerSkin = "lojaAguaPulo0";
            String btnUpgrade = "comprarAguaPulo1";
            if (powerValue == 0){
                powerSkin = "lojaAguaPulo0";
                btnUpgrade = "comprarAguaPulo1";
            }else if ( powerValue == 0.1f ){
                powerSkin = "lojaAguaPulo1";
                btnUpgrade = "comprarAguaPulo2";
            }else if ( powerValue == 0.2f ){
                powerSkin = "lojaAguaPulo2";
                btnUpgrade = "comprarAguaPulo3";
            }else if ( powerValue == 0.3f){
                powerSkin = "lojaAguaPulo3";
                //String btnUpgrade = "comprarAguaPulo1";
            }

            Image powerLevelPulo = new Image(skin,powerSkin);
            powerLevelPulo.setPosition(aguaCarreraSaldo.getX(), aguaCarreraSaldo.getY()-200);
            stage.addActor(powerLevelPulo);

            //TODO trocar tipo de Image para Button e Adicionar onClickListener de acordo com o nível que vier para realizar cobrança das aguas
            Image btnMelhorarPulo = new Image(skin,btnUpgrade);
            btnMelhorarPulo.setPosition(aguaPuloSaldo.getX()+50, aguaPuloSaldo.getY()-200);
            stage.addActor(btnMelhorarPulo);
        }

    }

    //TODO Implementar método de cobrança de águas

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
