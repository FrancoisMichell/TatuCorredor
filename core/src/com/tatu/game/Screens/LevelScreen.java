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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tatu.game.TatuBola;
import com.tatu.game.Util.Session;

/**
 * Created by Matheus Uehara on 16/08/2017.
 */

public class LevelScreen extends ClickListener implements Screen {

    private final Sound selecionado;
    private Skin skin;
    private Stage stage;
    private TatuBola game;
    private Session session = Session.getInstance();

    public LevelScreen(TatuBola game){
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
        skin = new Skin(Gdx.files.internal("levelsMenu/levelsMenu.json"),new TextureAtlas("levelsMenu/levelsMenu.pack"));

        Image background = new Image(skin, "menuscreen");
        background.setPosition(0,0);
        stage.addActor(background);

        Image levelsMenu = new Image(skin,"levels_menu");
        levelsMenu.setPosition( ( 800 - 475)/2 , (480 - 420)/2 ) ;
        stage.addActor(levelsMenu);

        TextButton level1 = new TextButton("1",skin,getEnableDisabledButton(0));
        level1.setPosition( 210,290 ) ;
        stage.addActor(level1);

        TextButton level2 = new TextButton("2",skin,getEnableDisabledButton(1));
        level2.setPosition( 310,290 ) ;
        stage.addActor(level2);

        TextButton level3 = new TextButton("3",skin,getEnableDisabledButton(2));
        level3.setPosition( 415 , 290 ) ;
        stage.addActor(level3);

        TextButton level4 = new TextButton("4",skin,getEnableDisabledButton(3));
        level4.setPosition( 515 ,  290 );
        stage.addActor(level4);

        TextButton level5 = new TextButton("5",skin,getEnableDisabledButton(4));
        level5.setPosition( 220, 180 ) ;
        stage.addActor(level5);

        TextButton level6 = new TextButton("6",skin,getEnableDisabledButton(5));
        level6.setPosition( 318 , 180 );
        stage.addActor(level6);

        TextButton level7 = new TextButton("7",skin,getEnableDisabledButton(6));
        level7.setPosition( 410 , 180) ;
        stage.addActor(level7);

        TextButton level8 = new TextButton("8",skin,getEnableDisabledButton(7));
        level8.setPosition( 505 , 180 ) ;
        stage.addActor(level8);

        Button closeButton = new Button(skin,"closeButton");
        closeButton.setPosition( 600,385 );
        stage.addActor(closeButton);

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen(game));
                selecionado.play();
                dispose();
            }
        });

        completeResources(level1,0);
        completeResources(level2,1);
        completeResources(level3,2);
        completeResources(level4,3);
        completeResources(level5,4);
        completeResources(level6,5);
        completeResources(level7,6);
        completeResources(level8,7);

    }

    public String getEnableDisabledButton(int level){
        String retorno = "btnLevelDisabled";
        if (! session.getUsuarioLogado().getLevels().get(level).getLocked()) {
            retorno = "btnLevelEnable";
        }
        return retorno;
    }

    public void completeResources(Button button , final int level){
        if (! session.getUsuarioLogado().getLevels().get(level).getLocked()){
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selecionado.play();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game,level));
                    dispose();
                }
            });

            String spriteStar = "0star";
            switch (session.getUsuarioLogado().getLevels().get(level).getScore()){
                case 0:
                    spriteStar = "0star";
                    break;
                case 1:
                    spriteStar = "1star";
                    break;
                case 2:
                    spriteStar = "2star";
                    break;
                case 3:
                    spriteStar = "3star";
                    break;
            }

            Image score = new Image(skin,spriteStar);
            score.setPosition( button.getX(),button.getY()-20 ) ;
            stage.addActor(score);

        }
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
