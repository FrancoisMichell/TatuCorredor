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
import com.tatu.game.Util.Session;

import static com.tatu.game.TatuBola.V_HEIGHT;
import static com.tatu.game.TatuBola.V_WIDTH;

/**
 * Created by Matheus Uehara on 16/08/2017.
 */

public class LevelScreen extends ClickListener implements Screen {

    private Skin skin;
    private Stage stage;
    private TatuBola game;

    public LevelScreen(TatuBola game){
        this.game = game;

        OrthographicCamera cam = new OrthographicCamera();
        Viewport viewport = new FitViewport(800, 480, cam);
        //Viewport viewport = new FitViewport(V_WIDTH, V_HEIGHT, cam);
        stage = new Stage(viewport, TatuBola.batch);
        Gdx.input.setInputProcessor(stage);
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

        Button level1 = new Button(skin,getEnableDisabledButton(0));
        level1.setPosition( 210,290 ) ;
        stage.addActor(level1);

        Button level2 = new Button(skin,getEnableDisabledButton(1));
        level2.setPosition( 310,290 ) ;
        stage.addActor(level2);

        Button level3 = new Button(skin,getEnableDisabledButton(2));
        level3.setPosition( 415 , 290 ) ;
        stage.addActor(level3);

        Button level4 = new Button(skin,getEnableDisabledButton(3));
        level4.setPosition( 515 ,  290 );
        stage.addActor(level4);

        Button level5 = new Button(skin,getEnableDisabledButton(4));
        level5.setPosition( 220, 180 ) ;
        stage.addActor(level5);

        Button level6 = new Button(skin,getEnableDisabledButton(5));
        level6.setPosition( 318 , 180 );
        stage.addActor(level6);

        Button level7 = new Button(skin,getEnableDisabledButton(6));
        level7.setPosition( 410 , 180) ;
        stage.addActor(level7);

        Button level8 = new Button(skin,getEnableDisabledButton(7));
        level8.setPosition( 505 , 180 ) ;
        stage.addActor(level8);

        Button lojaMenuButtons = new Button(skin,"lojaMenuButtons");
        lojaMenuButtons.setPosition( 315,75 );
        stage.addActor(lojaMenuButtons);

        lojaMenuButtons.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new LojaScreen(game));
                dispose();
            }
        });

        Button menuMenuButtons = new Button(skin,"menuMenuButtons");
        menuMenuButtons.setPosition( 400,75 );
        stage.addActor(menuMenuButtons);

        menuMenuButtons.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen(game));
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
        if (! Session.getUsuarioLogado().getLevels().get(level).getLocked()) {
            retorno = "btnLevelEnable";
        }
        return retorno;
    }

    public void completeResources(Button button , final int level){
        if (! Session.getUsuarioLogado().getLevels().get(level).getLocked()){
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game, level));
                    dispose();
                }
            });

            String spriteStar = "0star";
            switch (Session.getUsuarioLogado().getLevels().get(level).getScore()){
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

        String numberString = "number1";

        switch (level){
            case 0:
                numberString = "number1";
                break;
            case 1:
                numberString = "number2";
                break;
            case 2:
                numberString = "number3";
                break;
            case 3:
                numberString = "number4";
                break;
            case 4:
                numberString = "number5";
                break;
            case 5:
                numberString = "number6";
                break;
            case 6:
                numberString = "number7";
                break;
            case 7:
                numberString = "number8";
                break;
        }

        Image number = new Image(skin,numberString);
        number.setPosition( button.getX()+25,button.getY()+25 ) ;
        stage.addActor(number);
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
