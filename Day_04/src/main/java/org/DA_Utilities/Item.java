package org.DA_Utilities;

abstract class Item implements Interactable{
    String name;

    abstract void use(Player p);
}
