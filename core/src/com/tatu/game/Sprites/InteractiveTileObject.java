package com.tatu.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tatu.game.Screens.PlayScreen;
import com.tatu.game.TatuBola;

abstract class InteractiveTileObject {
    private TiledMap map;
    private Body body;
    Fixture fixture;

    InteractiveTileObject(PlayScreen screen, Rectangle bounds, boolean isSensor) {
        World world = screen.getWorld();
        this.map = screen.getMap();

        BodyDef bDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set((bounds.getX() + bounds.getWidth() / 2) / TatuBola.PPM, (bounds.getY() + bounds.getHeight() / 2) / TatuBola.PPM);

        body = world.createBody(bDef);

        shape.setAsBox((bounds.getWidth() / 2) / TatuBola.PPM, (bounds.getHeight() / 2) / TatuBola.PPM);
        fDef.shape = shape;

        fDef.isSensor = isSensor;
        fixture = body.createFixture(fDef);

    }

    public abstract void onHeadHit();

    void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("objects");
        return layer.getCell((int) (body.getPosition().x * TatuBola.PPM / 64),
                (int) (body.getPosition().y * TatuBola.PPM / 64));
    }
}
