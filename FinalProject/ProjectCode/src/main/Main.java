package main;

import rank.CSVReader;
import rank.Calculator;
import rank.Match;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    static private String filePath;


    public static void main(String[] args) {

        String filePath = "data/season-1920.csv";
        CSVReader cr = new CSVReader();
        ArrayList<Match> matches = cr.getData(filePath);

        for(Match match : matches){
            Calculator.predictNormalDist(match);
        }

//        Calculator.teamsWithNormalDist.forEach((k, v) ->
//                System.out.println("Teams: " + k + " Mean: " + v[0] + " variance: " + v[1]));


        System.out.println("\n" + "Press 1 to view current ranking." + "\n"
                + "Press 2 to type in a new match." + "\n"
                + "Press 3 to make predictions." + "\n"
                + "Press 4 to exit.");
        Scanner input = new Scanner(System.in);
        int number = input.nextInt();
        while (true) {
            try {
                switch (number) {
                    case 1:
                        Calculator.calculateRank();
                        System.exit(0);
                    case 2:
                        newMatch();
                        System.exit(0);
                    case 3:
                        makePrediction();
                        System.exit(0);
                    case 4:
                        System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("Input is abnormal, please re-enter");
            }
        }
    }

    private static void newMatch(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the name of home team");
        String homeTeam = input.nextLine();

        System.out.println("Please enter the score of home team");
        String scoreOfHomeTeam = input.nextLine();

        System.out.println("Please enter the name of away team");
        String awayTeam = input.nextLine();

        System.out.println("Please enter the score of away team");
        String scoreOfAwayTeam = input.nextLine();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        Match match = new Match(dateFormat.format(date),homeTeam,awayTeam,scoreOfHomeTeam,scoreOfAwayTeam);
        Calculator.updateNewMatch(match);
    }

    private static void makePrediction(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the name of home team");
        String homeTeam = input.nextLine();

        System.out.println("Please enter the name of away team");
        String awayTeam = input.nextLine();

        System.out.println("Please enter the score difference you wish to predict");
        int expectedGoal = input.nextInt();

        Calculator.calculatePDF(homeTeam,awayTeam,expectedGoal);
    }
}
