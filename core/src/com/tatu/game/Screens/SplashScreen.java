package com.tatu.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tatu.game.TatuBola;
import com.tatu.game.Util.BdManager;
import com.tatu.game.Util.Session;
import com.tatu.game.entity.Usuario;

import static com.tatu.game.TatuBola.V_HEIGHT;
import static com.tatu.game.TatuBola.V_WIDTH;

public class SplashScreen implements Screen {

    private Texture texture = new Texture(Gdx.files.internal("screen/splashscreen.png"));
    private Image splashImage = new Image(texture);
    private Stage stage = new Stage();

    private MenuScreen menuScreen = new MenuScreen(new TatuBola());
    private TatuBola game;

    public SplashScreen(TatuBola game){
        this.game = game;
        stage = new Stage(new StretchViewport(V_WIDTH, V_HEIGHT));

        Usuario user = BdManager.getInstance().getUserFromSharedPref();
        if ( user != null ) {
            Session.setUsuarioLogado(user);
        }else{
            Usuario novoUsuario = new Usuario();
            BdManager.getInstance().saveUserInSharedPref(novoUsuario);
            Session.setUsuarioLogado(user);
        }

    }

    @Override
    public void show() {
        stage.addActor(splashImage);

        splashImage.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.fadeIn(1.0f),
                Actions.delay(1),
                Actions.fadeOut(1.0f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(menuScreen);
                    }
                })));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // use true here to center the camera
        // that's what you probably want in case of a UI
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
        texture.dispose();
        stage.dispose();
    }
}
