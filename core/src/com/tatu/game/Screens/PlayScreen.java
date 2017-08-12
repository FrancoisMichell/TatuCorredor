package com.tatu.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tatu.game.Controller;
import com.tatu.game.Scenes.Hud;
import com.tatu.game.Sprites.Tatu;
import com.tatu.game.TatuBola;
import com.tatu.game.Tools.B2WorldCreator;
import com.tatu.game.Tools.WorldContactListener;

import static com.tatu.game.TatuBola.PPM;
import static com.tatu.game.TatuBola.V_HEIGHT;
import static com.tatu.game.TatuBola.V_WIDTH;
import static com.tatu.game.TatuBola.batch;

public class PlayScreen implements Screen {
    private TatuBola game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private Hud hud;

    private TextureAtlas atlas;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    private Tatu player;
    private Controller controller;


    public PlayScreen(TatuBola game) {

        atlas = new TextureAtlas("sprites/Tatu64.atlas");
        this.game = game;
        gameCam = new OrthographicCamera(V_WIDTH, V_HEIGHT);
        gamePort = new StretchViewport(V_WIDTH / PPM, V_HEIGHT / PPM, gameCam);
        hud = new Hud(batch);

        mapLoader = new TmxMapLoader();
        //map = mapLoader.load("mapas/map128.tmx");
        map = mapLoader.load("mapas/map64.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        player = new Tatu(world, this);

        world.setContactListener(new WorldContactListener(player));

        controller = Controller.getInstance();

    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    private void update(float dt) {
        handleInput();

        world.step(1 / 60f, 6, 2);

        player.update(dt);

        gameCam.position.x = player.b2body.getPosition().x;

        gameCam.update();
        renderer.setView(gameCam);
    }

    private void handleInput() {
        //Manipulando o pulo
        if (controller.isUpPressed() && (player.getCurrentState() == Tatu.State.IDLE || player.getCurrentState() == Tatu.State.RUNNING)) {
            //if (player.getPowerUpAgua() == true){
            //    player.b2body.applyLinearImpulse(new Vector2(0, player.getPulo() * forca do power up ), player.b2body.getWorldCenter(), true);
            //}else{
                player.b2body.applyLinearImpulse(new Vector2(0, player.getPulo()), player.b2body.getWorldCenter(), true);
            //}
        }

        //Manipulando a corrida pra direita
        if (controller.isRightPressed() && (player.b2body.getLinearVelocity().x <= 3)) {

            player.b2body.applyLinearImpulse(new Vector2(player.getVelocidade(), 0), player.b2body.getWorldCenter(), true);
        }

        //Manipulando a corrida pra esquerda
        if (controller.isLeftPressed() && (player.b2body.getLinearVelocity().x >= -3)) {
            player.b2body.applyLinearImpulse(new Vector2(-player.getVelocidade(), 0), player.b2body.getWorldCenter(), true);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();

        b2dr.render(world, gameCam.combined);

        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();
        player.draw(batch);
        batch.end();

        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        controller.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);

        controller.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        controller.dispose();

    }
}