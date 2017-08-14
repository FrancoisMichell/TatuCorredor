package com.tatu.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tatu.game.Sprites.Agua;
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

        /*if ((fixA.getUserData() == "head") || (fixB.getUserData() == "head")) {
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture obj = head == fixA ? fixB : fixA;

            if (obj.getUserData() instanceof InteractiveTileObject) {
                ((InteractiveTileObject) obj.getUserData()).onHeadHit();
            }

            if (obj.getUserData() instanceof Agua) {
                tatu.setVelocidade(0.1f);
            }
        }*/

        switch (cDef) {
            case TatuBola.AGUA_BIT | TatuBola.TATU_BIT:
                if (fixA.getFilterData().categoryBits == TatuBola.AGUA_BIT) {
                    ((Agua) fixA.getUserData()).onHeadHit();
                    tatu.setVelocidade(0.1f);
                } else {
                    ((Agua) fixB.getUserData()).onHeadHit();
                    tatu.setVelocidade(0.1f);
                }
                break;
            case TatuBola.ENEMY_BIT | TatuBola.TATU_BIT:
                if (fixA.getFilterData().categoryBits == TatuBola.TATU_BIT) {
                    tatu.hit();
                } else {
                    tatu.hit();
                }
                break;
        }

        //Gdx.app.log("Begin Contact", "MOPA");
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