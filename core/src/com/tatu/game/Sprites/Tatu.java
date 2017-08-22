package com.tatu.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tatu.game.Scenes.Hud;
import com.tatu.game.Screens.PlayScreen;
import com.tatu.game.TatuBola;
import com.tatu.game.Util.Session;

public class Tatu extends Sprite {
    public enum State {FALLING, JUMPING, RUNNING, IDLE, DEAD}

    private State currentState;
    private State previousState;
    private PlayScreen screen;

    private World world;
    public Body b2body;
    private TextureRegion tatuStand;
    private Animation<TextureRegion> tatuRun;
    private Animation<TextureRegion> tatuIdle;
    private Animation<TextureRegion> tatuJump;

    private float pulo = 4f;
    private float velocidade = 0.1f;

    private boolean powerUpCarreira = false;
    private float tempoCarrera;
    private boolean powerUpPulo = false;
    private float tempoPulo;
    private boolean powerUpFreio = false ;
    private float tempoFreio;

    private boolean runningRight;
    private float stateTimer;
    private boolean tatuIsDead;

    private float dt, tempoHit;

    public Tatu(PlayScreen screen) {
        super(screen.getAtlas().findRegion("tatu"));
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.IDLE;
        previousState = State.IDLE;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> framesIdle = new Array<TextureRegion>();
        Array<TextureRegion> frames = new Array<TextureRegion>();
        int imgSize = 64;
        for (int i = 1; i < 8; i++)
            frames.add(new TextureRegion(getTexture(), i * imgSize, 0, imgSize, imgSize));
        tatuRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 8; i < 14; i++)
            framesIdle.add(new TextureRegion(getTexture(), i * imgSize, 0, imgSize, imgSize));
        tatuIdle = new Animation<TextureRegion>(0.1f, framesIdle);
        framesIdle.clear();

        for (int i = 16; i < 27; i++)
            frames.add(new TextureRegion(getTexture(), i * imgSize, 0, imgSize, imgSize));
        tatuJump = new Animation<TextureRegion>(0.1f, frames);

        tatuStand = new TextureRegion(getTexture(), 0, 0, imgSize, imgSize);

        defineTatu();
        setBounds(0, 0, imgSize / TatuBola.PPM, imgSize / TatuBola.PPM);
        setRegion(tatuStand);

    }

    public void update(float dt) {
        this.dt = dt;
        setPosition(b2body.getPosition().x - getHeight() / 2, b2body.getPosition().y - getHeight() / 3);
        setRegion(getFrame(dt));
        checkDeath();
        checkUpgrade(dt);
    }

    private void checkUpgrade(float dt) {
        tempoCarrera += dt;
        tempoPulo += dt;
        tempoFreio += dt;

        if (isPowerUpCarreira()) {
            if (tempoCarrera >= 15) {
                setPowerUpCarreira(false);
                setVelocidade(-getVelocidade() + 0.1f);
                tempoCarrera = 0;
            }
        } else if (isPowerUpPulo()) {
            if (tempoPulo >= 15) {
                setPowerUpPulo(false);
                setPulo(-getPulo() + 6f);
                tempoPulo = 0;
            }
        }
    }

    private void checkDeath(){
        if (b2body.getPosition().y < -50) {
            tatuIsDead = true;
        }
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = tatuJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = tatuRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case IDLE:
                region = tatuIdle.getKeyFrame(stateTimer, true);
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
        if (tatuIsDead)
            return State.DEAD;
        else if (b2body.getLinearVelocity().y > 0)
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
        bdef.position.set(1064 / TatuBola.PPM, 256 / TatuBola.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / TatuBola.PPM);
        // CATEGORIA DO OBJETO
        fdef.filter.categoryBits = TatuBola.TATU_BIT;
        // COM QUAIS CATEGORIAS ELE PODE COLIDIR?
        fdef.filter.maskBits = TatuBola.DEFAULT_BIT | TatuBola.CARRERA_BIT | TatuBola.PULO_BIT | TatuBola.ONCA_BIT | TatuBola.JAGUATIRICA_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef);

        CircleShape head = new CircleShape();
        head.setRadius(20 / TatuBola.PPM);
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef);

    }

    public State getCurrentState() {
        return currentState;
    }

    public boolean isDead() {
        return tatuIsDead;
    }

    public void hit(short inimigo) {
        tempoHit += dt;
        if (tempoHit > 0.03) {
            if (powerUpCarreira || powerUpPulo) {
                resetPowerUp();
                tempoHit = 0;
                voaTatu();
            } else {
                if (inimigo == TatuBola.JAGUATIRICA_BIT) {
                    if (screen.getHud().getAguaCarreraScoreValue() > 0) {
                        screen.getHud().setAguaCarreraScoreValue(screen.getHud().getAguaCarreraScoreValue() - 1);
                        voaTatu();
                        tempoHit = 0;
                    } else if (!tatuIsDead) {
                        tatuIsDead = true;
                    }
                } else if (inimigo == TatuBola.ONCA_BIT) {
                    if (screen.getHud().getAguaPuloScoreValue() > 0) {
                        screen.getHud().setAguaPuloScoreValue(screen.getHud().getAguaPuloScoreValue() - 1);
                        voaTatu();
                        tempoHit = 0;
                    } else if (!tatuIsDead) {
                        tatuIsDead = true;
                    }
                }
            }
        }
    }


    private void resetPowerUp() {
        setVelocidade(-getVelocidade() + 0.1f);
        setPowerUpCarreira(false);
        setPulo(-getPulo() + 6f);
        setPowerUpPulo(false);
        tempoCarrera = 0;
        tempoPulo = 0;
    }

    private void voaTatu() {
        if (runningRight) {
            b2body.applyLinearImpulse(new Vector2(0.5f, 5f), b2body.getWorldCenter(), true);
        } else {
            b2body.applyLinearImpulse(new Vector2(-0.5f, 5f), b2body.getWorldCenter(), true);
        }
    }

    public float getPulo() {
        if (this.isPowerUpPulo()){
            return pulo + Session.getUsuarioLogado().getAguaPuloPower();
        }else{
            return pulo;
        }
    }

    public void setPulo(float pulo) {
        this.pulo += pulo;
    }

    public boolean isPowerUpPulo() {
        return powerUpPulo;
    }

    public void setPowerUpPulo(boolean powerUpPulo) {
        this.powerUpPulo = powerUpPulo;
        //timeCount = 0;
    }

    public float getVelocidade() {
        if (this.isPowerUpCarreira()){
            return velocidade + Session.getUsuarioLogado().getAguaCarreraPower();
        }else{
            return velocidade;
        }
    }

    public void setVelocidade(float velocidade) {
        this.velocidade += velocidade;
    }

    private boolean isPowerUpCarreira() {
        return powerUpCarreira;
    }

    public void setPowerUpCarreira(boolean powerUpCarreira) {
        this.powerUpCarreira = powerUpCarreira;
        tempoCarrera = 0;
    }



    public boolean isPowerUpFreio() {
        return powerUpFreio;
    }

    public void setPowerUpFreio(boolean powerUpFreio) {
        this.powerUpFreio = powerUpFreio;
        //timeCount = 0;
    }

}
