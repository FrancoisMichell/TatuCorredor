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

public class Homem extends Enemy {

    private int count = 0;

    public enum State {IDLE, SHOOTING}

    private State currentState;

    private float stateTime;
    private Animation<TextureRegion> shootingAnimation;
    private Animation<TextureRegion> standingAnimation;
    private Array<TextureRegion> standing;
    private Array<TextureRegion> shooting;
    private FixtureDef fdef;
    private float time;

    private Sound tiro;

    public Homem(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        tiro = Gdx.audio.newSound(Gdx.files.internal("efeitos/tiro2.mp3"));

        standing = new Array<TextureRegion>();
        shooting = new Array<TextureRegion>();

        for (int i = 0; i < 9; i++) {
            standing.add(new TextureRegion(screen.getAtlas().findRegion("cangaceiro"), i * 123, 0, 123, 128));
        }
        for (int i = 9; i < 10; i++) {
            shooting.add(new TextureRegion(screen.getAtlas().findRegion("cangaceiro"), i * 123, 0, 123, 128));
        }

        standingAnimation = new Animation<TextureRegion>(0.2f, standing);
        shootingAnimation = new Animation<TextureRegion>(0.2f, shooting);
        stateTime = 0;
        setBounds(getX(), getY(), 123 / TatuBola.PPM, 128 / TatuBola.PPM);
    }

    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y + 0.6f - getHeight() / 2);
        changePosition(dt);

    }

    private void changePosition(float dt) {
        time += dt;

        if (time < 4) {
            count = 0;
            setCurrentState(State.IDLE);
            setRegion(standingAnimation.getKeyFrame(stateTime, true));
        } else if (time > 4 && time < 5) {
            if (count == 0 && b2body.isActive()) {
                tiro.play();
            }
            count++;
            setCurrentState(State.SHOOTING);
            setRegion(shootingAnimation.getKeyFrame(stateTime, true));

        } else {
            b2body.setActive(false);
            time = 0;
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
        vertice[2] = new Vector2(-60, 40).scl(1 / TatuBola.PPM);
        vertice[3] = new Vector2(-60, 70).scl(1 / TatuBola.PPM);
        vertice[4] = new Vector2(30, 60).scl(1 / TatuBola.PPM);
        vertice[5] = new Vector2(30, 3).scl(1 / TatuBola.PPM);
        shape.set(vertice);

        // CATEGORIA DO OBJETO
        fdef.filter.categoryBits = TatuBola.HOMEM_BIT;
        // COM QUAIS CATEGORIAS ELE PODE COLIDIR?
        fdef.filter.maskBits = TatuBola.DEFAULT_BIT | TatuBola.TATU_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);
        b2body.setActive(false);
    }

    public State getCurrentState() {
        return currentState;
    }

    private void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}