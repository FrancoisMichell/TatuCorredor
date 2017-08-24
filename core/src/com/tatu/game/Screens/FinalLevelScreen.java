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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tatu.game.TatuBola;
import com.tatu.game.Util.BdManager;
import com.tatu.game.Util.Session;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by uehara on 21/08/17.
 */

public class FinalLevelScreen implements Screen {
    private Skin skin;
    private Stage stage;
    private TatuBola game;

    private int level;
    private int tempo;
    private int aguaCarrera;
    private int aguaPulo;
    private Sound selecionado;
    private Session session = Session.getInstance();

    public FinalLevelScreen(TatuBola game, int level, int tempo, int aguaCarrera, int aguaPulo) {

        this.game = game;
        this.level = level;
        this.tempo = tempo;
        this.aguaCarrera = aguaCarrera;
        this.aguaPulo = aguaPulo;


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
        stage.addActor(background);

        Image gameOver = new Image(skin,"faseConcluida");
        gameOver.setPosition( ( 800 - 372)/2 , (480 - 434)/2 ) ;
        stage.addActor(gameOver);

        String strTempo = new SimpleDateFormat("mm:ss").format(new Date(this.tempo * 1000L));

        Double desempenhoTemp = Double.valueOf((getDesempenhoAguaCarrera() + getDesempenhoAguaPulo() + getDesempenhoTempo() ) / 3);
        int desempenho = (int) Math.floor(desempenhoTemp);

        String starResource = "1star";
        switch (desempenho){
            case 1:
                starResource = "1star";
                break;
            case 2:
                starResource = "2star";
                break;
            case 3:
                starResource = "3star";
                break;
        }

        Image star = new Image(skin,starResource);
        star.setPosition( 260 , 270 ) ;
        stage.addActor(star);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("fonts/cartwhel.fnt")), Color.WHITE);

        Label tempoLabel = new Label(strTempo, font);
        tempoLabel.setPosition(410, 230);
        stage.addActor(tempoLabel);

        Label aguaCarreraLabel = new Label(this.aguaCarrera+"", font);
        aguaCarreraLabel.setPosition(410, 180);
        stage.addActor(aguaCarreraLabel);

        Label aguaPuloLabel = new Label(this.aguaPulo+"", font);
        aguaPuloLabel.setPosition(410, 130);
        stage.addActor(aguaPuloLabel);

        session.getUsuarioLogado().setAguaCarreraMoney(session.getUsuarioLogado().getAguaCarreraMoney() + this.aguaCarrera);
        session.getUsuarioLogado().setAguaPuloMoney(session.getUsuarioLogado().getAguaPuloMoney() + this.aguaPulo);
        session.getUsuarioLogado().getLevels().get(this.level).setScore(desempenho);
        session.getUsuarioLogado().getLevels().get(this.level+1).setLocked(false);
        BdManager.getInstance().saveUserInSharedPref(session.getUsuarioLogado());

        int negativeScale = 25;

        Button playAgainButton = new Button(skin, "playAgainButton");
        playAgainButton.setPosition( 270 , 50) ;
        playAgainButton.setWidth(playAgainButton.getWidth()-negativeScale);
        playAgainButton.setHeight(playAgainButton.getHeight()-negativeScale);
        stage.addActor(playAgainButton);

        Button jogarButton = new Button(skin, "jogarButton");
        jogarButton.setPosition( 370 , 50) ;
        jogarButton.setWidth(jogarButton.getWidth()-negativeScale);
        jogarButton.setHeight(jogarButton.getHeight()-negativeScale);
        stage.addActor(jogarButton);

        Button lojaButton = new Button(skin, "lojaButton");
        lojaButton.setPosition(470, 50);
        lojaButton.setWidth(lojaButton.getWidth()-negativeScale);
        lojaButton.setHeight(lojaButton.getHeight()-negativeScale);
        stage.addActor(lojaButton);

        lojaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selecionado.play();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LojaScreen(game));
                dispose();
            }
        });

        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selecionado.play();
                ((Game)Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game,level));
                dispose();
            }
        });

        jogarButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selecionado.play();
                ((Game)Gdx.app.getApplicationListener()).setScreen(new LevelScreen(game));
                dispose();
            }
        });
    }

    public int getDesempenhoAguaCarrera(){
        // Inicializando
        int levelCarrera = 1;

        if (this.level == 1 ) {
            //Quantidade de aguas que existem na fase 1
            levelCarrera = 2;
        }else if (this.level == 2 ){
            levelCarrera = 8;
        }else if (this.level == 3 ){
            levelCarrera = 8;
        }
        int desempenhoParcial = this.aguaCarrera/levelCarrera;
        if (desempenhoParcial >= 0.75) {
            return 3;
        }else if (desempenhoParcial >= 0.5 && desempenhoParcial < 0.75 ){
            return 2;
        }else if(desempenhoParcial < 0.5){
            return 1;
        }
        // Retorno padrão em caso de algum erro.
        return 1;
    }

    public int getDesempenhoAguaPulo(){
        // Inicializando
        int levelPulo = 1;

        if (this.level == 1 ) {
            //Quantidade de aguas que existem na fase 1
             levelPulo = 2;
        }else if (this.level == 2 ){
            levelPulo = 8;
        }else if (this.level == 3 ){
            levelPulo = 8;
        }
        int desempenhoParcial = this.aguaPulo/levelPulo;
        if (desempenhoParcial >= 0.75) {
            return 3;
        }else if (desempenhoParcial >= 0.5 && desempenhoParcial < 0.75 ){
            return 2;
        }else if(desempenhoParcial < 0.5){
            return 1;
        }
        // Retorno padrão em caso de algum erro.
        return 1;
    }

    public int getDesempenhoTempo(){
        int tempoMinimo = 60;
        if (this.level == 1 ) {
            //Tempo minimo para completar a fase 1
            tempoMinimo = 80;
        }else if (this.level == 2 ){
            tempoMinimo = 80;
        }else if (this.level == 3 ){
            tempoMinimo = 80;
        }
        int desempenhoParcial = this.tempo/tempoMinimo;
        if (desempenhoParcial <= 1.5) {
            return 3;
        }else if (desempenhoParcial > 1.5 && desempenhoParcial <= 2){
            return 2;
        }else if(desempenhoParcial > 2){
            return 1;
        }
        // Retorno padrão em caso de algum erro.
        return 1;

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
