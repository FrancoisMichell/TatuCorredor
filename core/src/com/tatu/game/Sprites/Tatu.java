package com.tatu.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tatu.game.Screens.PlayScreen;
import com.tatu.game.TatuBola;

public class Tatu extends Sprite {

    public enum State {FALLING, JUMPING, RUNNING, IDLE}

    public State getCurrentState() {
        return currentState;
    }

    public State currentState;
    private State previousState;

    private World world;
    public Body b2body;
    private TextureRegion tatuStand;
    private Animation tatuRun;
    private Animation tatuIdle;
    private Animation<TextureRegion> tatuJump;

    private float pulo = 6f;
    private float velocidade = 0.1f;

    private boolean runningRight;
    private float stateTimer;

    private int imgSize = 64;

    public Tatu(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("tatu"));
        this.world = world;
        currentState = State.IDLE;
        previousState = State.IDLE;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> framesIdle = new Array<TextureRegion>();
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 8; i++)
            frames.add(new TextureRegion(getTexture(), i * imgSize, 0, imgSize, imgSize));
        tatuRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 8; i < 14; i++)
            framesIdle.add(new TextureRegion(getTexture(), i * imgSize, 0, imgSize, imgSize));
        tatuIdle = new Animation(0.1f,framesIdle);
        framesIdle.clear();

        for (int i = 16; i < 27; i++)
            frames.add(new TextureRegion(getTexture(), i * imgSize, 0, imgSize, imgSize));
        tatuJump = new Animation(0.1f, frames);

        tatuStand = new TextureRegion(getTexture(), 0, 0, imgSize, imgSize);

        defineTatu();
        setBounds(0, 0, imgSize / TatuBola.PPM, imgSize / TatuBola.PPM);
        setRegion(tatuStand);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getHeight() / 2, b2body.getPosition().y - getHeight() / 3);
        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = tatuJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) tatuRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case IDLE:
                region = (TextureRegion) tatuIdle.getKeyFrame(stateTimer, true);
                break;
            default:
                region = tatuStand;
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    private State getState() {
        if (b2body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0 || b2body.getLinearVelocity().y > 4)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else if (b2body.getLinearVelocity().y == 0)
            return State.IDLE;
        else
            return State.JUMPING;
    }

    private void defineTatu() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(384 / TatuBola.PPM, 256 / TatuBola.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / TatuBola.PPM);
        // CATEGORIA DO OBJETO
        fdef.filter.categoryBits = TatuBola.TATU_BIT;
        // COM QUAIS CATEGORIAS ELE PODE COLIDIR?
        fdef.filter.maskBits = TatuBola.DEFAULT_BIT | TatuBola.AGUA_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef);

        //EdgeShape head = new EdgeShape();
        //head.set(new Vector2(-2 / TatuBola.PPM, 31 / TatuBola.PPM), new Vector2(2 / TatuBola.PPM, 31 / TatuBola.PPM));
        //fdef.shape = head;
        CircleShape head = new CircleShape();
        head.setRadius(20 / TatuBola.PPM);
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");

    }

    public float getPulo() {
        return pulo;
    }

    public void setPulo(float pulo) {
        this.pulo = pulo;
    }

    public float getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(float velocidade) {
        this.velocidade += velocidade;
    }

}