package DA_Utilities;

public class Dragon extends  Creature implements Interactable{

    int health = 5;

    @Override
    void attack(Creature target) {
        System.out.println("You face the Dragon ");
        if(target instanceof Player){
            if(!(((Player) target).haveWeapon)){
                //target.health--;
                ((Player) target).takeDamage();

                System.out.println("You don't have Weapon !");
                System.out.println("Your health is : "+ target.health);
                System.out.println();

                if(target.health <=0){
                    System.out.println(" Sorry You lose the Game ");
                    System.exit(0);
                }
            }
            else{

                System.out.println("You had the Weapon, so health not get reduced");
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