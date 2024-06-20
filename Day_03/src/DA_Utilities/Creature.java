package DA_Utilities;

abstract class Creature{
    String name;
    int health;

    abstract void attack(Creature target);
    abstract void takeDamage();
}

