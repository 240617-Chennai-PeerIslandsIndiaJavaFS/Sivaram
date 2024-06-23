package org.DA_Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Player extends Creature implements Interactable{
    private static final Logger logger = LoggerFactory.getLogger(Player.class);
    boolean haveWeapon = false;

    public Player(String name) {
        this.name=name;
        this.health=3;
        logger.info("Player created: {}", name);
    }

    @Override
    void attack(Creature target) {
        if(haveWeapon){
            target.health--;
            this.haveWeapon=false;
        }

    }

    @Override
    void takeDamage() {
        this.health--;
    }


    public void interact(Interactable interactable) {
        interactable.interact(this);
    }

}
