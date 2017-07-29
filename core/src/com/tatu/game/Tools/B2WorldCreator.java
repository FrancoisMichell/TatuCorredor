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
import com.tatu.game.TatuBola;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map) {

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / TatuBola.PPM, (rect.getY() + rect.getHeight() / 2) / TatuBola.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / TatuBola.PPM, (rect.getHeight() / 2) / TatuBola.PPM);
            fDef.shape = shape;
            body.createFixture(fDef);
        }

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / TatuBola.PPM, (rect.getY() + rect.getHeight() / 2) / TatuBola.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / TatuBola.PPM, (rect.getHeight() / 2) / TatuBola.PPM);
            fDef.shape = shape;
            body.createFixture(fDef);
        }

        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / TatuBola.PPM, (rect.getY() + rect.getHeight() / 2) / TatuBola.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / TatuBola.PPM, (rect.getHeight() / 2) / TatuBola.PPM);
            fDef.shape = shape;
            body.createFixture(fDef);
        }
    }
}
