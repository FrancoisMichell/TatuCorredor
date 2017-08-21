package com.tatu.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tatu.game.Sprites.AguaCarrera;
import com.tatu.game.Sprites.AguaPulo;
import com.tatu.game.Sprites.Tatu;
import com.tatu.game.TatuBola;

public class WorldContactListener implements ContactListener {

    private Tatu tatu;

    public WorldContactListener(Tatu tatu) {
        this.tatu = tatu;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case TatuBola.CARRERA_BIT | TatuBola.TATU_BIT:
                if (fixA.getFilterData().categoryBits == TatuBola.CARRERA_BIT) {
                    ((AguaCarrera) fixA.getUserData()).onHeadHit();
                    tatu.setVelocidade(0.2f);
                    tatu.setPowerUpCarreira(true);
                } else {
                    ((AguaCarrera) fixB.getUserData()).onHeadHit();
                    tatu.setVelocidade(0.2f);
                    tatu.setPowerUpCarreira(true);
                }
                break;

            case TatuBola.PULO_BIT | TatuBola.TATU_BIT:
                if (fixA.getFilterData().categoryBits == TatuBola.PULO_BIT) {
                    ((AguaPulo) fixA.getUserData()).onHeadHit();
                    tatu.setPulo(1f);
                    tatu.setPowerUpPulo(true);
                } else {
                    ((AguaPulo) fixA.getUserData()).onHeadHit();
                    tatu.setPulo(1f);
                    tatu.setPowerUpPulo(true);
                }
                break;

            case TatuBola.JAGUATIRICA_BIT | TatuBola.TATU_BIT:
                tatu.hit(TatuBola.JAGUATIRICA_BIT);
                break;

            case TatuBola.ONCA_BIT | TatuBola.TATU_BIT:
                tatu.hit(TatuBola.ONCA_BIT);
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
        //Gdx.app.log("END Contact", "MOPA");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}