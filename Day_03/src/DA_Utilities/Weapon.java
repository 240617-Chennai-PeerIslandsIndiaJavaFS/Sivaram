package DA_Utilities;

public class Weapon extends Item{

    @Override
    void use(Player p) {
        p.haveWeapon = true;
        System.out.println("Hurray you got the Weapon!");
    }


    @Override
    public void interact(Interactable p) {
        if(p instanceof Player){
            this.use((Player)p);
        }
    }
}
