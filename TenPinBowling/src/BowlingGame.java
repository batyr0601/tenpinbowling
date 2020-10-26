import jdk.swing.interop.SwingInterOpUtils;

import java.util.Scanner;

public class BowlingGame {
    // RUNS THE GAME UNTIL TOLD TO STOP
    static boolean playAgain = true;
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Welcome to Ten-pin Bowling");
        do {
            menu();
            again();
        } while (playAgain);
    }

    // LOOPS THE GAME
    static void again(){
        String choice = "";
        boolean loop = true;

        while (loop) {
            System.out.println("Would you like to continue the game? (Yes/No)");
            choice = input.next();

            if (choice.equals("Yes")){
                System.out.println("LETS GOOO");;
                loop = false;
            }
            else if (choice.equals("No")){
                playAgain = false;
                loop = false;
            }
            else {
                System.out.println("Error has occurred");
            }
        }
    }

    // THE GAME ITSELF
    static void menu(){
        int totalScore = 0;
        // Scores for each round
        int[] roundScores = new int[12];
        // Which rounds were a spare/strike
        boolean[] spare = new boolean[12];
        boolean[] strike = new boolean[12];
        // Which rounds need an extra point
        int[] extraPoints = new int[12];
        int[] attemptExtra = new int[2];

        for (int i=0; i<10; i++){
            // attempts for an individual round
            int attemptOne = 0, attemptTwo = 0;
            System.out.println("----------------------------------------------------");
            System.out.println("It is now turn " + (i+1));
            boolean getInput = true;
            while (getInput) {
                System.out.println("Input number of pins knocked with the first ball: ");
                attemptOne = input.nextInt();

                // Checking if attempt 1 had an acceptable value
                if (attemptOne == 10){
                    System.out.println("");
                    System.out.println("*****");
                    System.out.println("Strike");
                    System.out.println("*****");
                    System.out.println("");
                    strike[i] = true;
                    extraPoints[i] = 2;
                    roundScores[i] = 10;
                    getInput = false;
                }
                else if (attemptOne >= 0 && attemptOne < 10) {
                    System.out.println("Input number of pins knocked with the second ball: ");
                    attemptTwo = input.nextInt();
                    roundScores[i] = attemptOne + attemptTwo;
                    // Checking if attempt 2 had an acceptable value
                    if (roundScores[i] == 10) {
                        System.out.println("");
                        System.out.println("*****");
                        System.out.println("Spare");
                        System.out.println("*****");
                        System.out.println("");
                        spare[i] = true;
                        extraPoints[i] = 1;
                        getInput = false;
                    }
                    else if (attemptTwo >= 0 && roundScores[i]<=10) {
                        getInput = false;
                    }
                    else {
                        System.out.println("invalid input");
                    }
                }
                else {
                    System.out.println("invalid input");
                }
            }

            // SORTS OUT THE BONUS POINTS
            if (i>0 && extraPoints[i-1] == 2) {
                roundScores[i-1] += attemptOne;
                extraPoints[i-1] -= 1;
                if (attemptOne != 10) {
                    roundScores[i-1] += attemptTwo;
                    extraPoints[i-1] -= 1;
                }
            } else if (i>0 && extraPoints[i-1] == 1) {
                roundScores[i-1] += attemptOne;
                extraPoints[i-1] -= 1;
            }
            if (i>1 && extraPoints[i-2] == 1) {
                roundScores[i-2] += attemptOne;
                extraPoints[i-2] -= 1;
            }

            // TURKEY
            if (i>1 && strike[i-2] && strike[i-1] && strike[i]){
                System.out.println("****************************************************");
                System.out.println("");
                System.out.println("*********************TURKEY!!!**********************");
                System.out.println("");
                System.out.println("****************************************************");
                System.out.println("");
                strike[i-2] = false;
                strike[i-1] = false;
                strike[i] = false;
            }

            // EXTRA GO'S ON THE LAST ROUND
            if (i == 9 && extraPoints[i] != 0){
                for (int c = 0; c < extraPoints[i]; c++){
                    System.out.println("You get an extra go");
                    attemptExtra[c] = input.nextInt();
                    roundScores[i] += attemptExtra[c];
                    // sorts out the bonus points of round 9
                    while (extraPoints[8] != 0){
                        roundScores[8] += attemptExtra[0];
                        extraPoints[8] -= 1;
                    }
                }
            }
        }

        // OUTPUT
        System.out.println("");
        System.out.println("");
        System.out.println("--------");
        System.out.println("RESULTS:");
        System.out.println("--------");
        for (int i=0; i<10; i++){
            System.out.println("In turn " + (i+1) + " you knocked down " + roundScores[i]);
            totalScore += roundScores[i];
        }
        System.out.println("------------------");
        System.out.println("TOTAL SCORE IS " + totalScore);
        System.out.println("------------------");
        System.out.println("");
    }
}