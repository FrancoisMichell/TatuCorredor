package com.tatu.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.tatu.game.Screens.PlayScreen;
import com.tatu.game.TatuBola;

public class Onca extends Enemy {

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private Sound pulo;

    public Onca(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("onca"), i * 160, 0, 160, 128));
        }
        walkAnimation = new Animation<TextureRegion>(0.2f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 180 / TatuBola.PPM, 128 / TatuBola.PPM);
        pulo = Gdx.audio.newSound(Gdx.files.internal("efeitos/onca.mp3"));
    }

    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y + 0.6f - getHeight() / 2);
        b2body.setLinearVelocity(velocity);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
        if (stateTime % 0.6 == 0) {
            pulo.play();
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2[] vertice = new Vector2[6];
        vertice[0] = new Vector2(-3, 3).scl(1 / TatuBola.PPM);
        vertice[1] = new Vector2(-3, 40).scl(1 / TatuBola.PPM);
        vertice[2] = new Vector2(-70, 40).scl(1 / TatuBola.PPM);
        vertice[3] = new Vector2(-70, 70).scl(1 / TatuBola.PPM);
        vertice[4] = new Vector2(60, 60).scl(1 / TatuBola.PPM);
        vertice[5] = new Vector2(60, 3).scl(1 / TatuBola.PPM);
        shape.set(vertice);

        // CATEGORIA DO OBJETO
        fdef.filter.categoryBits = TatuBola.ONCA_BIT;
        // COM QUAIS CATEGORIAS ELE PODE COLIDIR?
        fdef.filter.maskBits = TatuBola.DEFAULT_BIT | TatuBola.TATU_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);
    }
}