package com.tatu.game.Screens;

import com.badlogic.gdx.Game;
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
import com.tatu.game.Sprites.Enemy;
import com.tatu.game.Sprites.Homem;
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

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    private Tatu player;

    private Controller controller;

    private boolean block;

    public PlayScreen(TatuBola game, int level) {
        this.game = game;

        atlas = new TextureAtlas("sprites/Tatu64 - Copia - Copia.atlas");
        gameCam = new OrthographicCamera(V_WIDTH, V_HEIGHT);
        gamePort = new StretchViewport(V_WIDTH / PPM, V_HEIGHT / PPM, gameCam);
        hud = new Hud(batch);

        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load(loadMap(level));
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        player = new Tatu(this);

        world.setContactListener(new WorldContactListener(player));

        //controller = Controller.getInstance();
        controller = new Controller();
    }

    private String loadMap(int level) {
        String mapa;

        switch (level) {
            case 1:
                mapa = "mapas/map64.tmx";
                break;
            case 2:
                mapa = "mapas/map64.tmx";
                break;
            case 3:
                mapa = "mapas/map64.tmx";
                break;
            case 4:
                mapa = "mapas/map64.tmx";
                break;
            case 5:
                mapa = "mapas/map64.tmx";
                break;
            case 6:
                mapa = "mapas/map64.tmx";
                break;
            case 7:
                mapa = "mapas/map64.tmx";
                break;
            case 8:
                mapa = "mapas/map64.tmx";
                break;
            case 9:
                mapa = "mapas/map64.tmx";
                break;
            case 10:
                mapa = "mapas/map64.tmx";
                break;
            default:
                mapa = "mapas/map64.tmx";
                break;
        }
        return mapa;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    private void update(float dt) {
        handleInput();

        hud.update(dt);

        world.step(1 / 60f, 6, 2);

        player.update(dt);

        for (Homem enemy : creator.getCangaceiros()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 512 / TatuBola.PPM) {
                enemy.b2body.setActive(true);
            } else if (enemy.getX() > player.getX() - 512 / TatuBola.PPM) {
                enemy.b2body.setActive(false);
            }
            if (enemy.getCurrentState() == Homem.State.SHOOTING) {
                criaBala();
                block = true;
            } else {
                block = false;
            }
        }

        for (Enemy enemy : creator.getJaguatiricas()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 512 / TatuBola.PPM) {
                enemy.b2body.setActive(true);
            } else if (enemy.getX() > player.getX() - 512 / TatuBola.PPM) {
                enemy.b2body.setActive(false);
            }
        }

        for (Enemy enemy : creator.getBalas()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 512 / TatuBola.PPM) {
                enemy.b2body.setActive(true);
            } else if (enemy.getX() > player.getX() - 512 / TatuBola.PPM) {
                enemy.b2body.setActive(false);
            }
        }

        for (Enemy enemy : creator.getOncas()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 512 / TatuBola.PPM) {
                enemy.b2body.setActive(true);
            } else if (enemy.getX() > player.getX() - 512 / TatuBola.PPM) {
                enemy.b2body.setActive(false);
            }
        }

        hud.update(dt);

        if (!player.isDead()) {
            gameCam.position.x = player.b2body.getPosition().x;
        } else {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreen(game));
        }

        gameCam.update();
        renderer.setView(gameCam);
    }

    private void criaBala() {
        if (!block)
            creator.criaBala(this, map);
    }

    private void handleInput() {

        if (!player.isDead()) {
            //Manipulando o pulo
            if (controller.isUpPressed() && (player.getCurrentState() == Tatu.State.IDLE || player.getCurrentState() == Tatu.State.RUNNING)) {
                    player.b2body.applyLinearImpulse(new Vector2(0, player.getPulo()), player.b2body.getWorldCenter(), true);

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
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();

        //TODO Retirar o render das linhas de debug quando for apresentar
        b2dr.render(world, gameCam.combined);

        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();
        player.draw(batch);

        for (Enemy enemy : creator.getBalas()) {
            enemy.draw(batch);
        }

        for (Enemy enemy : creator.getJaguatiricas()) {
            enemy.draw(batch);
        }
        for (Enemy enemy : creator.getOncas()) {
            enemy.draw(batch);
        }
        for (Enemy enemy : creator.getCangaceiros()) {
            enemy.draw(batch);
        }

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

    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        //dispose();
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

    public Hud getHud() {
        return hud;
    }

    public void setHud(Hud hud) {
        this.hud = hud;
    }
}