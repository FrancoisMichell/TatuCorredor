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
        aguaCarreraScore.setPosition(580, 518);
        stage.addActor(aguaCarreraScore);

        Image aguaPuloScore = new Image (skin,"aguaPuloScore");
        aguaPuloScore.setPosition(580, 458);
        stage.addActor(aguaPuloScore);

        Image tempoScore = new Image (skin,"tempoScore");
        tempoScore.setPosition(40, 518);
        stage.addActor(tempoScore );

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        tempoLabel = new Label("Tempo", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        aguaCarreraLabel = new Label("Agua Carrera", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        aguaPuloLabel = new Label("Agua Pulo", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        tempoValue = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        tempoValue.setFontScale(2);

        aguaCarreraValue = new Label(aguaCarreraScoreValue+"", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        aguaCarreraValue.setFontScale(2);

        aguaPuloValue= new Label(aguaPuloScoreValue+"", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        aguaPuloValue.setFontScale(2);

        table.row().padTop(30);
        table.add(tempoValue).expandX();
        table.add().expandX();
        table.add(aguaCarreraValue).expandX();

        table.row().padTop(20);
        table.add().expandX();
        table.add().expandX();
        table.add(aguaPuloValue).expandX();

        stage.addActor(table);

    }

    public void update(float dt) {

        timeCount += dt;
        if (timeCount >= 1) {
            worldTimer++;
            tempoValue.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }

        aguaCarreraValue.setText(String.format("%03d", getAguaCarreraScoreValueGlobal()));
        aguaPuloValue.setText(String.format("%03d", getAguaPuloScoreValueGlobal()));

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

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
