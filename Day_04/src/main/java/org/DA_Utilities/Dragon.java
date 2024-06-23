package org.DA_Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dragon extends  Creature implements Interactable{
    private static final Logger logger = LoggerFactory.getLogger(Dragon.class);
    int health = 5;

    @Override
    void attack(Creature target) {
        logger.info("You face the Dragon");

        if(target instanceof Player){
            if(!(((Player) target).haveWeapon)){
                ((Player) target).takeDamage();

                logger.warn("You doesn't have Weapon! Player health: {}", target.health);
                System.out.println();

                if(target.health <=0){
                    logger.error("You lost the game");
                    System.exit(0);
                }
            }
            else{

                logger.info("You have weapon , so retain its health");
                System.out.println();
            }
        };

    }

    @Override
    void takeDamage() {
        this.health--;
    }

    @Override
    public void interact(Interactable p) {
        if(p instanceof Player){
            this.attack((Player)p);
        }
    }
}