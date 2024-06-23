package org.DA_Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Potion extends Item {
    private static final Logger logger = LoggerFactory.getLogger(Potion.class);
    @Override
    void use(Player p) {
        p.health++;
        if(p.health >= 4)
            p.health = 3 ;

        logger.info("{}, you got a potion. Your Health: {}", p.name, p.health);
    }

    @Override
    public void interact(Interactable p) {
        if(p instanceof Player){
            this.use((Player)p);
        }
    }

}