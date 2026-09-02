package CSPC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class cspc1p3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int lineNumber = 1;

        while (!(line = br.readLine().trim()).equals("No More Words!")) {
            String result = checkSpelling(line, lineNumber);
            System.out.println(result);
            lineNumber++;
        }
    }

    // Function to check if a word follows the "I before E, except after C" rule
    private static String checkSpelling(String word, int lineNumber) {
        if (word.contains("cie") || word.contains("ei") && !word.contains("cei")) {
            // Fix the spelling according to the rule
            if (word.contains("cie")) {
                word = word.replace("cie", "cei");
            } else if (word.contains("ei") && !word.contains("cei")) {
                word = word.replace("ei", "ie");
            }
            return word;
        } else {
            // The word is correct according to the rule
            return "Word " + lineNumber + " is correct.";
        }
    }
}