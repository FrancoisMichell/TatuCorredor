package com.tatu.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
import com.tatu.game.Screens.PlayScreen;
import com.tatu.game.TatuBola;
import com.tatu.game.Util.Session;

public class Tatu extends Sprite {
    private final float BASE_PULO = 4.5f;
    private final float BASE_VELOCIDADE = 0.1f;

    private boolean somPulo, somCaindo = false;

    public enum State {FALLING, JUMPING, RUNNING, IDLE, DEAD}

    private State currentState;
    private State previousState;

    private boolean acabouFase = false;
    private PlayScreen screen;
    private Session session = Session.getInstance();

    private World world;
    public Body b2body;
    private TextureRegion tatuStand;
    private Animation<TextureRegion> tatuRun;
    private Animation<TextureRegion> tatuIdle;
    private Animation<TextureRegion> tatuJump;

    private float pulo = BASE_PULO;
    private float velocidade = BASE_VELOCIDADE;

    private boolean powerUpCarreira = false;
    private float tempoCarrera;
    private boolean powerUpPulo = false;
    private float tempoPulo;

    private boolean runningRight;
    private float stateTimer;
    private boolean tatuIsDead;

    private float dt, tempoHit;

    private Sound puloSound;
    private Sound caindoSound;

    public Tatu(PlayScreen screen) {
        super(screen.getAtlas().findRegion("tatu"));
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.IDLE;
        previousState = State.IDLE;
        stateTimer = 0;
        runningRight = true;
        puloSound = Gdx.audio.newSound(Gdx.files.internal("efeitos/puloTatu.wav"));
        caindoSound = Gdx.audio.newSound(Gdx.files.internal("efeitos/caindo.mp3"));

        Array<TextureRegion> framesIdle = new Array<TextureRegion>();
        Array<TextureRegion> frames = new Array<TextureRegion>();
        int imgSize = 64;
        for (int i = 1; i < 8; i++)
            frames.add(new TextureRegion(getTexture(), i * imgSize, 64, imgSize, imgSize));
        tatuRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 8; i < 14; i++)
            framesIdle.add(new TextureRegion(getTexture(), i * imgSize, 64, imgSize, imgSize));
        tatuIdle = new Animation<TextureRegion>(0.1f, framesIdle);
        framesIdle.clear();

        for (int i = 16; i < 27; i++)
            frames.add(new TextureRegion(getTexture(), i * imgSize, 64, imgSize, imgSize));
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

        if (isPowerUpCarreira()) {
            if (tempoCarrera >= 15) {
                setPowerUpCarreira(false);
                setVelocidade(-getVelocidade() + BASE_VELOCIDADE);
                tempoCarrera = 0;
            }
        } else if (isPowerUpPulo()) {
            if (tempoPulo >= 15) {
                setPowerUpPulo(false);
                setPulo(-getPulo() + BASE_PULO);
                tempoPulo = 0;
            }
        }
    }

    private void checkDeath(){
        if (b2body.getPosition().y < 0 && !somCaindo) {
            caindoSound.play();
            somCaindo = true;
        }
        if (b2body.getPosition().y < -10) {
            tatuIsDead = true;
        }
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = tatuJump.getKeyFrame(stateTimer);
                if (somPulo) {
                    puloSound.play();
                    somPulo = false;
                }
                break;
            case RUNNING:
                region = tatuRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case IDLE:
                region = tatuIdle.getKeyFrame(stateTimer, true);
                somPulo = true;
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
        bdef.position.set(640 / TatuBola.PPM, 128 / TatuBola.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / TatuBola.PPM);
        // CATEGORIA DO OBJETO
        fdef.filter.categoryBits = TatuBola.TATU_BIT;
        // COM QUAIS CATEGORIAS ELE PODE COLIDIR?
        fdef.filter.maskBits = (short) (TatuBola.DEFAULT_BIT | TatuBola.CARRERA_BIT | TatuBola.PULO_BIT | TatuBola.ONCA_BIT
                | TatuBola.JAGUATIRICA_BIT | TatuBola.HOMEM_BIT | TatuBola.PAREDE_BIT | TatuBola.FINAL_BIT);

        fdef.shape = shape;

        b2body.createFixture(fdef);

    }

    private void resetPowerUp() {
        setVelocidade(-getVelocidade() + BASE_VELOCIDADE + session.getUsuarioLogado().getAguaCarreraPower());
        setPowerUpCarreira(false);
        setPulo(-getPulo() + BASE_PULO);
        setPowerUpPulo(false);
        tempoCarrera = 0;
        tempoPulo = 0;
    }

    private void voaTatu() {
        if (runningRight) {
            b2body.applyLinearImpulse(new Vector2(-5, 3), b2body.getWorldCenter(), true);
        } else {
            b2body.applyLinearImpulse(new Vector2(5, 3), b2body.getWorldCenter(), true);
        }
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
                } else if (inimigo == TatuBola.HOMEM_BIT) {
                    if ((screen.getHud().getAguaPuloScoreValue() > 0) || (screen.getHud().getAguaCarreraScoreValue() > 0)) {
                        String aux;
                        if (screen.getHud().getAguaPuloScoreValue() >= screen.getHud().getAguaCarreraScoreValue()) {
                            aux = "pulo";
                        } else {
                            aux = "carrera";
                        }
                        tiraAgua(aux);
                        voaTatu();
                        tempoHit = 0;
                    } else if (!tatuIsDead) {
                        tatuIsDead = true;
                    }
                }
            }
        }
    }

    public State getCurrentState() {
        return currentState;
    }

    public boolean isDead() {
        return tatuIsDead;
    }

    private void tiraAgua(String aux) {
        if (aux.equals("pulo"))
            screen.getHud().setAguaPuloScoreValue(screen.getHud().getAguaPuloScoreValue() - 1);
        else
            screen.getHud().setAguaCarreraScoreValue(screen.getHud().getAguaCarreraScoreValue() - 1);
    }

    public float getPulo() {
        if (this.isPowerUpPulo()){
            return pulo + session.getUsuarioLogado().getAguaPuloPower();
        }else{
            return pulo;
        }
    }

    public void setPulo(float pulo) {
        this.pulo += pulo;
    }

    private boolean isPowerUpPulo() {
        return powerUpPulo;
    }

    public void setPowerUpPulo(boolean powerUpPulo) {
        this.powerUpPulo = powerUpPulo;
        //timeCount = 0;
    }

    public float getVelocidade() {
        if (this.isPowerUpCarreira()){
            return velocidade + session.getUsuarioLogado().getAguaCarreraPower();
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

    public boolean acabouFase() {
        return acabouFase;
    }

    public void setAcabouFase(boolean acabouFase) {
        this.acabouFase = acabouFase;
    }
}
