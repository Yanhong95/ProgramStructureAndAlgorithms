package com.main;

import java.util.Random;
import java.util.Scanner;

public class UF_CLIENT {

    private static int[] randomPairGenerator(int n){
        Random r = new Random();
        int a = r.nextInt(n);
        int b = r.nextInt(n);
        return new int[]{a,b};
    }

    private static int count(int n){
        int countOfConnections = 0;
        for(int i = 0; i<100; i++){
            UF_HWQUPC uf = new UF_HWQUPC(n, true);
            int count = 0;
            while(uf.components() > 1){
                int[] randomPairs =  randomPairGenerator(n);
                if(!uf.connected(randomPairs[0], randomPairs[1])){
                    uf.union(randomPairs[0], randomPairs[1]);
                }
                count++;
            }
            countOfConnections += count;
        }
        return (int)(countOfConnections/100);
    }



    public static void main(String[] args) {
        System.out.print("Please enter 10 positive numbers ( greater than 100 )for the UF testing!");
        System.out.print("\n");
        for(int i = 1; i <= 10; i++) {
            int number;
            Scanner input = new Scanner(System.in);
            do {
                System.out.print("Please enter NO:" + i + " positive number for the UF testing: ");
                while (!input.hasNextInt()) {
                    System.out.println("That's not a number!");
                    input.next(); // this is important!
                }
                number = input.nextInt();
            } while (number <= 0);
            int pairs = count(number);
            System.out.println("Initial sites: " + number + ", Pairs generated: " + pairs);
        }
    }
}
