package com.tatu.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.tatu.game.TatuBola;

public class Agua extends InteractiveTileObject {

    public Agua(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(TatuBola.AGUA_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Agua", "collision");
        setCategoryFilter(TatuBola.DESTRUIDO_BIT);
        getCell().setTile(null);
    }
}
