package com.tatu.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tatu.game.Screens.PlayScreen;
import com.tatu.game.Sprites.AguaCarrera;
import com.tatu.game.Sprites.AguaPulo;
import com.tatu.game.Sprites.Bala;
import com.tatu.game.Sprites.Homem;
import com.tatu.game.Sprites.Jaguatirica;
import com.tatu.game.Sprites.Onca;
import com.tatu.game.TatuBola;

public class B2WorldCreator {
    private Array<Jaguatirica> jaguatiricas;
    private Array<Onca> oncas;
    private Array<Homem> cangaceiros;
    private Array<Bala> balas;

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / TatuBola.PPM, (rect.getY() + rect.getHeight() / 2) / TatuBola.PPM);

            body = world.createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2) / TatuBola.PPM, (rect.getHeight() / 2) / TatuBola.PPM);
            fDef.shape = shape;

            body.createFixture(fDef);
        }

        for (MapObject object : map.getLayers().get("Paredes").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / TatuBola.PPM, (rect.getY() + rect.getHeight() / 2) / TatuBola.PPM);

            body = world.createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2) / TatuBola.PPM, (rect.getHeight() / 2) / TatuBola.PPM);
            fDef.filter.categoryBits = TatuBola.PAREDE_BIT;
            fDef.shape = shape;

            body.createFixture(fDef);
        }

        for (MapObject object : map.getLayers().get("FinalLevel").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / TatuBola.PPM, (rect.getY() + rect.getHeight() / 2) / TatuBola.PPM);

            body = world.createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2) / TatuBola.PPM, (rect.getHeight() / 2) / TatuBola.PPM);
            fDef.filter.categoryBits = TatuBola.FINAL_BIT;
            fDef.shape = shape;

            body.createFixture(fDef);
        }

        for (MapObject object : map.getLayers().get("gotaCarrera").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new AguaCarrera(screen, rect);
        }

        for (MapObject object : map.getLayers().get("gotaPulo").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new AguaPulo(screen, rect);
        }

        // Criar jaguatiricas
        jaguatiricas = new Array<Jaguatirica>();
        for (MapObject object : map.getLayers().get("Jaguatirica").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            jaguatiricas.add(new Jaguatirica(screen, rect.getX() / TatuBola.PPM, rect.getY() / TatuBola.PPM));
        }

        // Criar onças
        oncas = new Array<Onca>();
        for (MapObject object : map.getLayers().get("Onca").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            oncas.add(new Onca(screen, rect.getX() / TatuBola.PPM, rect.getY() / TatuBola.PPM));
        }

        // Criar onças
        cangaceiros = new Array<Homem>();
        for (MapObject object : map.getLayers().get("Cangaceiro").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            cangaceiros.add(new Homem(screen, rect.getX() / TatuBola.PPM, rect.getY() / TatuBola.PPM));
        }

        criaBala(screen, map);
    }

    public void criaBala(PlayScreen screen, TiledMap map) {
        balas = new Array<Bala>();
        for (MapObject object : map.getLayers().get("Cangaceiro").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            balas.add(new Bala(screen, rect.getX() / TatuBola.PPM, rect.getY() / TatuBola.PPM));
        }
    }


    public Array<Jaguatirica> getJaguatiricas() {
        return jaguatiricas;
    }

    public Array<Onca> getOncas() {
        return oncas;
    }

    public Array<Homem> getCangaceiros() {
        return cangaceiros;
    }

    public Array<Bala> getBalas() {
        return balas;
    }
}