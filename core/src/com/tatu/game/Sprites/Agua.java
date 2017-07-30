package com.tatu.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Agua extends InteractiveTileObject {

    public Agua(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Agua", "collision");
    }
}
