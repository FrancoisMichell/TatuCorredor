package com.tatu.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tatu.game.Screens.PlayScreen;

public abstract class Enemy extends Sprite {
    protected PlayScreen screen;
    World world;
    public Body b2body;
    Vector2 velocity;

    Enemy(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(-3, -3);
        b2body.setActive(false);
    }

    public abstract void update(float dt);

    protected abstract void defineEnemy();
}
