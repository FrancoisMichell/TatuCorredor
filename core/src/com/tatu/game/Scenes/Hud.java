package com.tatu.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tatu.game.TatuBola;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    //private Integer worldTimer;
    private float timeCount;
    private Integer score;
    private Skin skin;
    //private static Integer score;

    private static Integer aguaCarreraScoreValueGlobal = 0;
    private static Integer aguaCarreraScoreValue = 0;

    private static Integer aguaPuloScoreValueGlobal = 0;
    private static Integer aguaPuloScoreValue = 0;

    private Integer worldTimer;
    private Label tempoLabel;
    private Label tempoValue;

    private Label aguaCarreraLabel;
    private Label aguaCarreraValue;

    private Label aguaPuloLabel;
    private Label aguaPuloValue;

    /*
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label tatuLabel;
*/
    public Hud(SpriteBatch sb) {

        worldTimer = 0;
        timeCount = 0;
        score = 0;
        viewport = new FitViewport(TatuBola.V_WIDTH, TatuBola.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        skin = new Skin(Gdx.files.internal("menu/menu.json"),new TextureAtlas("menu/menu.pack"));

        Image aguaCarreraScore = new Image (skin,"aguaCarreraScore");
        aguaCarreraScore.setPosition( 40+170+10+142+20 , 500) ;
        stage.addActor(aguaCarreraScore);

        Image aguaPuloScore = new Image (skin,"aguaPuloScore");
        aguaPuloScore.setPosition( 40+170+20 , 500) ;
        stage.addActor(aguaPuloScore);

        Image tempoScore = new Image (skin,"tempoScore");
        tempoScore.setPosition( 40 , 500) ;
        stage.addActor(tempoScore );

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        /*countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        tatuLabel = new Label("TATU", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(tatuLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();
*/
        tempoLabel = new Label("Tempo", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        aguaCarreraLabel = new Label("Agua Carrera", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        aguaPuloLabel = new Label("Agua Pulo", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        tempoValue = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        tempoValue.setFontScale(2);

        aguaCarreraValue = new Label(aguaCarreraScoreValue+"", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        aguaCarreraValue.setFontScale(2);

        aguaPuloValue= new Label(aguaPuloScoreValue+"", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        aguaPuloValue.setFontScale(2);

        /*
        table.add(tempoLabel).expandX().padTop(10);
        table.add(aguaCarreraLabel).expandX().padTop(10);
        table.add(aguaPuloLabel).expandX().padTop(10);
        */
        table.add().expandX();
        table.add().expandX();
        table.add().expandX();


        table.row();
        table.add(tempoValue).expandX().padTop(45);
        table.add(aguaCarreraValue).expandX().padTop(45);
        table.add().expandX();

        stage.addActor(table);

    }

    public void update(float dt) {

        timeCount += dt;
        if (timeCount >= 1) {
            worldTimer++;
            tempoValue.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }

        aguaCarreraValue.setText(aguaCarreraScoreValueGlobal +"");

    }

    public static Integer getAguaCarreraScoreValueGlobal() {
        return aguaCarreraScoreValueGlobal;
    }

    public static void setAguaCarreraScoreValueGlobal(Integer aguaCarreraScoreValueGlobal) {
        Hud.aguaCarreraScoreValueGlobal = aguaCarreraScoreValueGlobal;
    }

    public static Integer getAguaPuloScoreValueGlobal() {
        return aguaPuloScoreValueGlobal;
    }

    public static void setAguaPuloScoreValueGlobal(Integer aguaPuloScoreValueGlobal) {
        Hud.aguaPuloScoreValueGlobal = aguaPuloScoreValueGlobal;
    }

    public static Integer getAguaCarreraScoreValue() {
        return aguaCarreraScoreValue;
    }

    public static void setAguaCarreraScoreValue(Integer aguaCarreraScoreValue) {
        Hud.aguaCarreraScoreValue = aguaCarreraScoreValue;
    }

    public static Integer getAguaPuloScoreValue() {
        return aguaPuloScoreValue;
    }

    public static void setAguaPuloScoreValue(Integer aguaPuloScoreValue) {
        Hud.aguaPuloScoreValue = aguaPuloScoreValue;
    }

    /*    public void update(float dt){
            timeCount += dt;
            if(timeCount >= 1){
                worldTimer--;
                countdownLabel.setText(String.format("%03d", worldTimer));
                timeCount = 0;
            }

        }

        public static void addScore(int value){
            score += value;
            scoreLabel.setText(String.format("%06d", score));
        }
    */
    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
