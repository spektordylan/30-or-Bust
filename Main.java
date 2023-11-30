import java.util.Scanner;

class Main {
    public static void main(String[] args) throws InterruptedException { // "throws error" included so Thread.sleep() method can be run
        // ascii art greeting
        asciiGreeting();

        // prompt for each player's name and construct each player object.
        // set each player object's name using setter based on what user inputted
        System.out.println("What is the first player's name?");
        Scanner playerNames = new Scanner(System.in);
        String player1Name = playerNames.nextLine();
        Player player1 = new Player();
        player1.setName(player1Name);
        System.out.println(); // empty line for readability

        // same thing but for second player
        System.out.println("What is the second player's name?");
        String player2Name = playerNames.nextLine();
        Player player2 = new Player();
        player2.setName(player2Name);
        System.out.println();

        // print greeting referring to each player object's set name using getter
        System.out.println("Alright " + player1.getName() + " and " + player2.getName() + ", let's start the game!");
        // print game rules
        System.out.println("Your goal is to reach a score of exactly 30. Each turn, you roll two dice.");
        System.out.println("You may select the value on either die or their sum to be added to your score.");
        System.out.println("If your score goes over 30, it is reset to 0 and the game continues.");
        System.out.println("Good luck!\n");
        Thread.sleep(2500); // 2.5 second delay for smoothness and readability

        // boolean that controls whose turn it is - starts on player 1's turn
        boolean player1Turn = true;

        // run this loop that alternates each player's turns until one player reaches 30 and wins
        // main loop that the game takes place in
        while(true) { // break statements in loop
            if(player1Turn) {
                // separate rollDice method for each "turn" so alternating player objects can be passed
                // better than having two large identical code chunks in the same loop
                rollDice(player1);
                player1Turn = false; // once player1 has their turn, it is no longer player 1's turn

                // check if player has reached a score of thirty, if so they are the winner
                if(player1.getScore() == 30) {
                    // if player wins, run final victory message
                    asciiVictory(player1.getName());
                    break; // end the game loop
                }
            } else {
                // same code but for player 2
                rollDice(player2);
                player1Turn = true; // after player 2's turn, it is once again player 1's turn

                if(player2.getScore() == 30) {
                    asciiVictory(player2.getName());
                    break;
                }
            }
        }
    }



    // this method displays the Ascii art for each die face side-by-side using a 2D array
    public static void displayDice(int roll1, int roll2) {
        /* 2D matrix of six possible die faces
        die faces are broken up into arrays of their individual rows */
        String[][] dice = {
                {
                        "+-------+",
                        "|       |",
                        "|   o   |",
                        "|       |",
                        "+-------+"
                },
                {
                        "+-------+",
                        "| o     |",
                        "|       |",
                        "|     o |",
                        "+-------+"
                },
                {
                        "+-------+",
                        "| o     |",
                        "|   o   |",
                        "|     o |",
                        "+-------+"
                },
                {
                        "+-------+",
                        "| o   o |",
                        "|       |",
                        "| o   o |",
                        "+-------+"
                },
                {
                        "+-------+",
                        "| o   o |",
                        "|   o   |",
                        "| o   o |",
                        "+-------+"
                },
                {
                        "+-------+",
                        "| o   o |",
                        "| o   o |",
                        "| o   o |",
                        "+-------+"
                }
        };

        // variables for the for loop that select the correct die from the matrix based on zero-based index numbering
        int die1 = roll1 - 1;
        int die2 = roll2 - 1;

        // loop that iterates through the row of each die to print the correct die faces side by side using
        for(int i = 0; i < 5; i++) { // 5 rows per die
            // the use of print over println allows the dice rows to be printed on the same line
            System.out.print("    "); // have the output be somewhat centered
            System.out.print(dice[die1][i]); // row of first die
            System.out.print("     "); // have space between each die
            System.out.print(dice[die2][i]); // row of second die
            System.out.println(); // start new line after every loop
        }
    }



    public static void rollDice(Player player) throws InterruptedException { // exception included so Thread.sleep() function can be used
        // display whose turn it is and their score using player object getters
        System.out.println(player.getName() + "'s turn!" + " You have " + player.getScore() + " points.");

        // roll dice
        int roll1 = (int)(Math.random() * 6 + 1);
        int roll2 = (int)(Math.random() * 6 + 1);

        // calculate total
        int total = roll1 + roll2;

        // display the die faces
        displayDice(roll1, roll2);

        // display roll information
        System.out.println("You rolled a " + roll1 + " and a " + roll2 + " for a total of " + total + ".\n");

        // ask player which roll they want to keep and validate input using a nested loop
        Scanner numberSelection = new Scanner(System.in);
        int choice; // initialize choice variable before loop
        System.out.println("Would you like to keep the value of the first die (1), the second die (2), or their total (3)?");
        do {
            System.out.println("Please enter 1, 2, or 3."); // keep prompting for valid input until it is given
            while(!(numberSelection.hasNextInt())) { // if input is not an integer, specify that a number must be entered
                System.out.println("Please enter a number 1, 2, or 3.");
                numberSelection.next(); // get rid of the invalid input to avoid infinite loop
            }
            choice = numberSelection.nextInt();
        } while(choice < 1 || choice > 3); // if user enters a number other than 1, 2, or 3, keep prompting for valid input

        // add corresponding amount to score using player object addPoints(int points) method
        if(choice == 1)
            player.addPoints(roll1);
        else if(choice == 2)
            player.addPoints(roll2);
        else
            player.addPoints(total);

        // in normal cases where score is not above 30, notify of new score
        if(player.getScore() <= 30) {
            System.out.println(); // empty line
            // print player information using object getters
            System.out.println(player.getName() + "'s new score is " + player.getScore() + ".\n");
        }
        // if the score is above 30, reset to 0 and notify user
        else {
            System.out.println();
            // print player information using object getters
            System.out.println(player.getName() + "'s new score is " + player.getScore() + ".");
            System.out.println("Because this score is larger than 30, your score is reset to 0!\n");
            player.resetScore(); // reset to 0 using Player object resetScore() method
        }

        Thread.sleep(1000); // one second delay after each turn
    }



    // method that prints ascii art for the greeting
    public static void asciiGreeting() {
        System.out.println("Welcome to...");
        System.out.println("  ____   ___                 ____            _   _ ");
        System.out.println(" |___ \\ / _ \\               |  _ \\          | | | |");
        System.out.println("   __) | | | |   ___  _ __  | |_) |_   _ ___| |_| |");
        System.out.println("  |__ <| | | |  / _ \\| '__| |  _ <| | | / __| __| |");
        System.out.println("  ___) | |_| | | (_) | |    | |_) | |_| \\__ \\ |_|_|");
        System.out.println(" |____/ \\___/   \\___/|_|    |____/ \\__,_|___/\\__(_)");
        System.out.println();
    }



    // prints ascii art victory message to notify that user has won
    public static void asciiVictory(String name) {
        System.out.println("Congratulations, " + name + ", you are the Winner!");
        System.out.println("   _____          __  __ ______    ______      ________ _____  ");
        System.out.println("  / ____|   /\\   |  \\/  |  ____|  / __ \\ \\    / /  ____|  __ \\ ");
        System.out.println(" | |  __   /  \\  | \\  / | |__    | |  | \\ \\  / /| |__  | |__) |");
        System.out.println(" | | |_ | / /\\ \\ | |\\/| |  __|   | |  | |\\ \\/ / |  __| |  _  / ");
        System.out.println(" | |__| |/ ____ \\| |  | | |____  | |__| | \\  /  | |____| | \\ \\ ");
        System.out.println("  \\_____/_/    \\_\\_|  |_|______|  \\____/   \\/   |______|_|  \\_\\");
    }
}