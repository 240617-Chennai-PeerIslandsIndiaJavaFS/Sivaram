package org.DA_Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Weapon extends Item{
    private static final Logger logger = LoggerFactory.getLogger(Weapon.class);

    @Override
    void use(Player p) {
        p.haveWeapon = true;
        logger.info("Hurray, you got the Weapon!");
    }


    @Override
    public void interact(Interactable p) {
        if(p instanceof Player){
            this.use((Player)p);
        }
    }
}
