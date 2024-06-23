package org.Instructions;

public class Instructions {

    public static void mainInstructions(){
        System.out.println("Main Instructions: ");
        System.out.println("------------------\n");

        System.out.println("Room Details");
        System.out.println("There are 4 rooms are available");
        System.out.println("Room1 , Room2 , Room3, Room4");
        System.out.println("Each room contains Enemies or Items");
        System.out.println("Enemies => Dragon and Goblin");
        System.out.println("Items   => Weapon and lotion");
        System.out.println("You can Select the room by using choices [1,2,3,4]");
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("What happen when visit Each room? ");
        System.out.println();
        System.out.println("Scenario 1: (see enemies)");
        System.out.println("if got Weapon in previous room You will be save when you see Enemies on the next option");
        System.out.println("if not have Weapon Your health will reduce by 1");
        System.out.println("You can use Weapon only once");
        System.out.println();
        System.out.println("Scenario 2: (see Items)");
        System.out.println("If you got Weapon, use that in next Option");
        System.out.println("If you got portion, you life will increase by 1");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("If you successfully visit 5 rooms without health reduced to 0, you will win the game!");
        System.out.println();
        System.out.println("let's start the game !!!");
    }
}
