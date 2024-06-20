import DA_Utilities.*;

import java.util.Scanner;

public class DungeonAdventure {

    public static boolean validChoices(int choice){
        if(choice >= 1 && choice <=4)
            return true;
        return false;
    }


    public static  void main(String[] args){
        String player_name;
        int choice;
        int i = 0;


        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Dungeon Adventure: ");

        // Create player
        System.out.print("Enter you name: ");
        player_name=sc.next();
        Player p1= new Player(player_name);

        //Display Instructions
        Instructions.mainInstructions();

        //create playground
        Potion po1 = new Potion();
        Weapon w1 = new Weapon();
        Goblin g1 = new Goblin();
        Dragon d1 = new Dragon();

        Interactable[][] playground = { {po1,w1,g1,d1},
                                        {w1,g1,d1,po1},
                                        {g1,d1,po1,w1},
                                        {d1,po1,w1,g1},
                                        {po1,w1,g1,d1} };

       for(i = 0; i < 5;i++){

           System.out.println("-----------------------------------------------------------------------------------------");
           System.out.println();

           System.out.println("\nNumber of rooms Visited : "+i);

           while(true){
               System.out.print("Enter your choices from 1 to 4: ");
               choice = sc.nextInt();
               if(validChoices(choice))
                   break;
               else
                   System.out.println("Invalid choice ");
           }

           p1.interact(playground[i][choice-1]);

           System.out.println();
       }

        System.out.println();
        if(i == 5)
            System.out.println("Congrats you won the game !");

    }
}
