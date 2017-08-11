package com.tatu.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Homem extends InteractiveTileObject {

    public Homem(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds, false);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Homem", "collision");
    }
}
