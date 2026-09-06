package StanleyProblems.arduino;

import java.util.Scanner;

//This is the solution to problem arduino and floating point answer is tolerated
public class ArduinoSol {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        //Reads input
        double V = Double.parseDouble(s.nextLine());
        double P = Double.parseDouble(s.nextLine()); //Percentage
        //Calculate lower bound and print it;
        double lowerBound = V*(1-(P/100));
        System.out.println(lowerBound);
        //Calculate upper bound and print it;
        double upperBound = V*(1+(P/100));
        System.out.println(upperBound);
        s.close();
    }
}
