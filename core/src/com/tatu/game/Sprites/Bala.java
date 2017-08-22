package com.tatu.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.tatu.game.Screens.PlayScreen;
import com.tatu.game.TatuBola;

public class Bala extends Enemy {

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;

    public Bala(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();
        for (int i = 10; i < 11; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("cangaceiro"), i * 123, 0, 123, 128));
        }
        walkAnimation = new Animation<TextureRegion>(0.18f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 123 / TatuBola.PPM, 128 / TatuBola.PPM);
        b2body.setGravityScale(0);

    }

    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y + 0.6f - getHeight() / 2);
        b2body.setLinearVelocity(-3, 0);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 60).scl(1 / TatuBola.PPM);
        vertice[1] = new Vector2(5, 60).scl(1 / TatuBola.PPM);
        vertice[2] = new Vector2(-5, 50).scl(1 / TatuBola.PPM);
        vertice[3] = new Vector2(5, 50).scl(1 / TatuBola.PPM);
        shape.set(vertice);

        // CATEGORIA DO OBJETO
        fdef.filter.categoryBits = TatuBola.HOMEM_BIT;
        // COM QUAIS CATEGORIAS ELE PODE COLIDIR?
        fdef.filter.maskBits = TatuBola.DEFAULT_BIT | TatuBola.TATU_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);
    }
}