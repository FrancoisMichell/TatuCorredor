package com.tatu.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tatu.game.TatuBola;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    //private Integer worldTimer;
    private float timeCount;
    private Integer score;
    private Skin skin;
    //private static Integer score;

    private Integer aguaCarreraScoreValue;

    private Integer aguaPuloScoreValue;

    private Integer worldTimer;

    private Label.LabelStyle font;

    private Label tempoLabel;
    private Label tempoValue;

    private Label aguaCarreraLabel;
    private Label aguaCarreraValue;

    private Label aguaPuloLabel;
    private Label aguaPuloValue;

    public Hud(SpriteBatch sb) {
        aguaPuloScoreValue = 0;
        aguaCarreraScoreValue = 0;
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

        font = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("fonts/cartwhel.fnt")), Color.WHITE);

        tempoLabel = new Label("Tempo", font);
        aguaCarreraLabel = new Label("Agua Carrera", font);
        aguaPuloLabel = new Label("Agua Pulo", font);

        tempoValue = new Label(worldTimer+"", font);

        aguaCarreraValue = new Label(aguaCarreraScoreValue+"", font);

        aguaPuloValue= new Label(aguaPuloScoreValue+"", font);

        table.add().expandX();
        table.add().expandX();


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
        if (timeCount >= 2) {
            worldTimer++;
            String dateAsText = new SimpleDateFormat("mm:ss").format(new Date(worldTimer * 1000L));
            tempoValue.setText(dateAsText);
            timeCount = 0;
        }

        aguaCarreraValue.setText(String.format("%03d", getAguaCarreraScoreValue()));
        aguaPuloValue.setText(String.format("%03d", getAguaPuloScoreValue()));

    }

    public Integer getAguaCarreraScoreValue() {
        return this.aguaCarreraScoreValue;
    }

    public void setAguaCarreraScoreValue(Integer aguaCarreraScoreValue) {
        this.aguaCarreraScoreValue = aguaCarreraScoreValue;
    }

    public Integer getAguaPuloScoreValue() {
        return this.aguaPuloScoreValue;
    }

    public void setAguaPuloScoreValue(Integer aguaPuloScoreValue) {
        this.aguaPuloScoreValue = aguaPuloScoreValue;
    }

    public Integer getWorldTimer() {
        return worldTimer;
    }

    public void setWorldTimer(Integer worldTimer) {
        this.worldTimer = worldTimer;
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
