package VM7WC;
import java.util.Scanner;

public class vmss7wc16c1p2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: number of statements
        int n = scanner.nextInt();
        int[] statements = new int[n];

        // Input: the array of statements
        for (int i = 0; i < n; i++) {
            statements[i] = scanner.nextInt();
        }

        int result = -1;

        // Loop through all possible numbers of true statements
        for (int t = 0; t <= n; t++) {
            int trueCount = 0;

            // Count the number of statements claiming t is true
            for (int i = 0; i < n; i++) {
                if (statements[i] == t) {
                    trueCount++;
                }
            }

            // If the number of true statements matches t, it's a valid answer
            if (trueCount == t) {
                result = Math.max(result, t);
            }
        }

        // Output the result
        if (result == -1) {
            System.out.println("Paradox!");
        } else {
            System.out.println(result);
        }

        scanner.close();
    }
}
