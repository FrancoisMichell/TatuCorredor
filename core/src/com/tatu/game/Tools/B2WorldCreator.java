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
import com.tatu.game.Sprites.Agua;
import com.tatu.game.Sprites.Onca;
import com.tatu.game.TatuBola;

public class B2WorldCreator {
    private Array<Onca> oncas;

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

        for (MapObject object : map.getLayers().get("gotaCarrera").getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Agua(screen, rect);

        }

        // Criar onças
        oncas = new Array<Onca>();
        for (MapObject object : map.getLayers().get("Onca").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            oncas.add(new Onca(screen, rect.getX() / TatuBola.PPM, rect.getY() / TatuBola.PPM));

        }
        /*
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Enemy(world, map, rect);
        }
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Onca(world, map, rect);
        }

        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Homem(world, map, rect);
        }

        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Agua(world, map, rect);
        }
        */
    }

    public Array<Onca> getOncas() {
        return oncas;
    }

}