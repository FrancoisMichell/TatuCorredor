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

public class Jaguatirica extends Enemy {

    private float stateTime;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> standAnimation;
    private Array<TextureRegion> frames;
    private Array<TextureRegion> stand;
    private FixtureDef fdef;
    private Sound movimento;
    private int count = 0;

    public Jaguatirica(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        frames = new Array<TextureRegion>();
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("jaguatirica"), i * 180, 0, 180, 128));
        }
        attackAnimation = new Animation<TextureRegion>(0.2f, frames);

        stand = new Array<TextureRegion>();
        for (int i = 0; i < 1; i++) {
            stand.add(new TextureRegion(screen.getAtlas().findRegion("jaguatirica"), i * 180, 0, 180, 128));
        }
        standAnimation = new Animation<TextureRegion>(1f, stand);


        stateTime = 0;
        setBounds(getX(), getY(), 180 / TatuBola.PPM, 128 / TatuBola.PPM);
        movimento = Gdx.audio.newSound(Gdx.files.internal("efeitos/jaguatirica.mp3"));
        setRegion(standAnimation.getKeyFrame(stateTime, true));
    }

    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y + 0.6f - getHeight() / 2);
        animaJaguatirica(dt);
    }


    private float time;

    private void animaJaguatirica(float dt) {
        time += dt;
        if (time > 2) {
            setRegion(attackAnimation.getKeyFrame(stateTime, true));
            if (count == 0 && b2body.isActive()) {
                count++;
                movimento.play();
            }
            if (time >= 2 + attackAnimation.getAnimationDuration() && attackAnimation.isAnimationFinished(stateTime))
                time = 0;
        } else {
            setRegion(standAnimation.getKeyFrame(stateTime, true));
            count = 0;
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        this.fdef = new FixtureDef();

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
        fdef.filter.categoryBits = TatuBola.JAGUATIRICA_BIT;
        // COM QUAIS CATEGORIAS ELE PODE COLIDIR?
        fdef.filter.maskBits = TatuBola.DEFAULT_BIT | TatuBola.TATU_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);
        b2body.setActive(false);
    }
}