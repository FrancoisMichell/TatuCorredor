package com.tatu.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tatu.game.Sprites.Agua;
import com.tatu.game.Sprites.InteractiveTileObject;
import com.tatu.game.Sprites.Tatu;

public class WorldContactListener implements ContactListener {

    private Tatu tatu;

    public WorldContactListener(Tatu tatu) {
        this.tatu = tatu;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if ((fixA.getUserData() == "head") || (fixB.getUserData() == "head")) {
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture obj = head == fixA ? fixB : fixA;

            if (obj.getUserData() instanceof InteractiveTileObject) {
                ((InteractiveTileObject) obj.getUserData()).onHeadHit();
            }

            if (obj.getUserData() instanceof Agua) {
                tatu.setVelocidade(0.1f);
            }
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