package DA_Utilities;

public class Player extends Creature implements Interactable{
    boolean haveWeapon = false;

    public Player(String name) {
        this.name=name;
        this.health=3;
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
