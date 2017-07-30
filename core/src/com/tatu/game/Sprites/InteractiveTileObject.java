package com.tatu.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tatu.game.TatuBola;

public abstract class InteractiveTileObject {
    private World world;
    private TiledMap map;
    protected TiledMapTile tile;
    private Rectangle bounds;
    private Body body;
    Fixture fixture;

    InteractiveTileObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set((bounds.getX() + bounds.getWidth() / 2) / TatuBola.PPM, (bounds.getY() + bounds.getHeight() / 2) / TatuBola.PPM);

        body = world.createBody(bDef);

        shape.setAsBox((bounds.getWidth() / 2) / TatuBola.PPM, (bounds.getHeight() / 2) / TatuBola.PPM);
        fDef.shape = shape;
        fixture = body.createFixture(fDef);
    }

    public abstract void onHeadHit();
}
