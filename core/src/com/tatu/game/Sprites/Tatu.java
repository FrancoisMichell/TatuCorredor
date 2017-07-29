package com.tatu.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.tatu.game.TatuBola;

public class Tatu extends Sprite {
    private World world;
    public Body b2body;

    public Tatu(World world) {
        this.world = world;
        defineTatu();
    }

    private void defineTatu() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / TatuBola.PPM, 32 / TatuBola.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / TatuBola.PPM);
        fdef.shape = shape;

        b2body.createFixture(fdef);
    }

}
