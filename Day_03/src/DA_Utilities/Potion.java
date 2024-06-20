package DA_Utilities;

public class Potion extends Item {

    @Override
    void use(Player p) {
        p.health++;
        if(p.health >= 4)
            p.health = 3 ;
        System.out.println(p.name+" , you got potion ");
        System.out.println("Your Health: "+p.health);
    }

    @Override
    public void interact(Interactable p) {
        if(p instanceof Player){
            this.use((Player)p);
        }
    }

}