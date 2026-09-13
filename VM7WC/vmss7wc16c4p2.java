package VM7WC;

import java.io.*;
import java.util.*;

public class vmss7wc16c4p2 {
    // Helper class to store the current word and the index (0-indexed) of the restriction set used for its last letter
    static class State {
        String word;
        int lastSet;

        State(String word, int lastSet) {
            this.word = word;
            this.lastSet = lastSet;
        }
    }

    public static void main(String[] args) throws IOException {
        // Set up the BufferedReader for input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // First line: number of restriction sets and the maximum word length.
        StringTokenizer st = new StringTokenizer(br.readLine());
        int numSets = Integer.parseInt(st.nextToken());
        int maxLength = Integer.parseInt(st.nextToken());

        // Read in all the restriction sets. Each set is stored as a sorted List<Character>.
        List<List<Character>> restrictSets = new ArrayList<>();
        for (int i = 0; i < numSets; i++) {
            st = new StringTokenizer(br.readLine());
            int count = Integer.parseInt(st.nextToken());
            List<Character> letters = new ArrayList<>();
            for (int j = 0; j < count; j++) {
                letters.add(st.nextToken().charAt(0));
            }
            Collections.sort(letters);
            restrictSets.add(letters);
        }

        // List to hold all valid words.
        ArrayList<String> results = new ArrayList<>();
        // Queue to iteratively build valid words.
        Queue<State> queue = new LinkedList<>();

        // The first letter of every word must come from the first restriction set.
        for (char c : restrictSets.get(0)) {
            String s = String.valueOf(c);
            results.add(s);
            // Enqueue for further expansion only if the word's current length is less than maxLength.
            if (s.length() < maxLength) {
                queue.offer(new State(s, 0));
            }
        }

        // Iteratively expand words. For a word whose last letter is from set 'lastSet',
        // append each letter from any restriction sets with indices greater than lastSet.
        while (!queue.isEmpty()) {
            State current = queue.poll();
            String curWord = current.word;
            int lastSet = current.lastSet;
            for (int nextSet = lastSet + 1; nextSet < numSets; nextSet++) {
                for (char ch : restrictSets.get(nextSet)) {
                    String newWord = curWord + ch;
                    results.add(newWord);
                    if (newWord.length() < maxLength) {
                        queue.offer(new State(newWord, nextSet));
                    }
                }
            }
        }

        // Sort the results alphabetically before printing them.
        Collections.sort(results);
        for (String word : results) {
            System.out.println(word);
        }
    }
}