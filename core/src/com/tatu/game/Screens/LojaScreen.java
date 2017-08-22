package com.tatu.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
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
import com.tatu.game.Util.BdManager;
import com.tatu.game.Util.Session;

/**
 * Created by Matheus Uehara on 16/08/2017.
 */

public class LojaScreen implements Screen {

    private Skin skin;
    private Stage stage;
    private TatuBola game;
    private Session session = Session.getInstance();

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

        aguaCarreraSaldoValue = new Label(session.getUsuarioLogado().getAguaCarreraMoney()+"", font);
        aguaCarreraSaldoValue.setPosition(aguaCarreraSaldo.getX()+45 , aguaCarreraSaldo.getY()+15);

        aguaPuloSaldoValue = new Label(session.getUsuarioLogado().getAguaPuloMoney()+"", font);
        aguaPuloSaldoValue.setPosition(aguaPuloSaldo.getX()+45 , aguaPuloSaldo.getY()+15);

        stage.addActor(aguaCarreraSaldoValue);
        stage.addActor(aguaPuloSaldoValue);

        getLevelPowerCarrera();
        getLevelPowerPulo();
    }

    public void getLevelPowerCarrera() {
        float carreraPowerValue = session.getUsuarioLogado().getAguaCarreraPower();
        String carreraPowerSkin = "lojaAguaCarrera0";
        String btnCarreraUpgrade = "comprarAguaCarrera1";
        int precoPowerUpCarrera = 5;
        if (carreraPowerValue == 0) {
            carreraPowerSkin = "lojaAguaCarrera0";
            btnCarreraUpgrade = "comprarAguaCarrera1";
            precoPowerUpCarrera = 5;
        } else if (carreraPowerValue == 0.2f) {
            carreraPowerSkin = "lojaAguaCarrera1";
            btnCarreraUpgrade = "comprarAguaCarrera2";
            precoPowerUpCarrera = 15;
        } else if (carreraPowerValue == 0.4f) {
            carreraPowerSkin = "lojaAguaCarrera2";
            btnCarreraUpgrade = "comprarAguaCarrera3";
            precoPowerUpCarrera = 25;
        } else if (carreraPowerValue == 0.6f) {
            carreraPowerSkin = "lojaAguaCarrera3";
            //String btnUpgrade = "";
        }

        final Image powerLevelCarrera = new Image(skin, carreraPowerSkin);
        powerLevelCarrera.setPosition(aguaCarreraSaldo.getX(), aguaCarreraSaldo.getY() - 100);
        stage.addActor(powerLevelCarrera);

        final Button btnMelhorarCarrera = new Button(skin, btnCarreraUpgrade);
        btnMelhorarCarrera.setPosition(aguaPuloSaldo.getX() + 50, aguaPuloSaldo.getY() - 100);
        stage.addActor(btnMelhorarCarrera);
        final int finalPrecoPowerUpCarrera = precoPowerUpCarrera;
        btnMelhorarCarrera.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (session.getUsuarioLogado().getAguaCarreraMoney() >= finalPrecoPowerUpCarrera) {
                    session.getUsuarioLogado().setAguaCarreraMoney(session.getUsuarioLogado().getAguaCarreraMoney() - finalPrecoPowerUpCarrera);
                    session.getUsuarioLogado().setAguaCarreraPower(session.getUsuarioLogado().getAguaCarreraPower() + 0.2f);
                    BdManager.getInstance().saveUserInSharedPref(session.getUsuarioLogado());
                    aguaCarreraSaldoValue.setText(session.getUsuarioLogado().getAguaCarreraMoney()+"");
                    btnMelhorarCarrera.remove();
                    powerLevelCarrera.remove();
                    getLevelPowerCarrera();
                }
            }
        });
    }

    public void getLevelPowerPulo() {

        float powerValue = session.getUsuarioLogado().getAguaPuloPower();
        String puloPowerSkin = "lojaAguaPulo0";
        String btnPuloUpgrade = "comprarAguaPulo1";
        int precoPowerUpPulo = 5;
        if (powerValue == 0){
            puloPowerSkin = "lojaAguaPulo0";
            btnPuloUpgrade = "comprarAguaPulo1";
            precoPowerUpPulo = 5;
        }else if ( powerValue == 0.2f ){
            puloPowerSkin = "lojaAguaPulo1";
            btnPuloUpgrade = "comprarAguaPulo2";
            precoPowerUpPulo = 15;
        }else if ( powerValue == 0.4f ){
            puloPowerSkin = "lojaAguaPulo2";
            btnPuloUpgrade = "comprarAguaPulo3";
            precoPowerUpPulo = 25;
        }else if ( powerValue == 0.6f){
            puloPowerSkin = "lojaAguaPulo3";
            //String btnUpgrade = "comprarAguaPulo1";
        }

        final Image powerLevelPulo = new Image(skin,puloPowerSkin);
        powerLevelPulo.setPosition(aguaCarreraSaldo.getX(), aguaCarreraSaldo.getY()-200);
        stage.addActor(powerLevelPulo);

        final Button btnMelhorarPulo = new Button(skin,btnPuloUpgrade);
        btnMelhorarPulo.setPosition(aguaPuloSaldo.getX()+50, aguaPuloSaldo.getY()-200);
        stage.addActor(btnMelhorarPulo);

        final int finalPrecoPowerUpPulo = precoPowerUpPulo;
        btnMelhorarPulo.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(session.getUsuarioLogado().getAguaPuloMoney() >= finalPrecoPowerUpPulo){
                    session.getUsuarioLogado().setAguaPuloMoney(session.getUsuarioLogado().getAguaPuloMoney() - finalPrecoPowerUpPulo);
                    session.getUsuarioLogado().setAguaPuloPower(session.getUsuarioLogado().getAguaPuloPower()+0.2f);
                    BdManager.getInstance().saveUserInSharedPref(session.getUsuarioLogado());
                    aguaPuloSaldoValue.setText(session.getUsuarioLogado().getAguaPuloMoney()+"");
                    btnMelhorarPulo.remove();
                    powerLevelPulo.remove();
                    getLevelPowerPulo();
                }
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
