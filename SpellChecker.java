
public class SpellChecker {


    public static void main(String[] args) {
        String word = args[0];
        int threshold = Integer.parseInt(args[1]);
        String[] dictionary = readDictionary("dictionary.txt");
        String correction = spellChecker(word, threshold, dictionary);
        System.out.println(correction);
    }

    public static String tail(String str) {
        if (str.length() == 1) {
            return "";
        }
        return str.substring(1);
    }

    public static int levenshtein(String word1, String word2) {
        if (word1.isEmpty()) return word2.length();//terminal case
        if (word2.isEmpty()) return word1.length();//terminal case

        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();

        if (word2.charAt(0) == word1.charAt(0)) {
            return levenshtein(word1.substring(1), word2.substring(1));
        }
        //finds minimum distance from substrings of word1 and word2
        int minChanges = Math.min(levenshtein(tail(word1), tail(word2)),
                Math.min(levenshtein(word1, tail(word2)),
                        levenshtein(tail(word1), word2)));
        return 1 + minChanges;
    }

    public static String[] readDictionary(String fileName) {
        String[] dictionary = new String[3000];

        In in = new In(fileName);
        for (int i = 0; i < 3000; i++) {
            dictionary[i] = in.readLine();
        }

        return dictionary;
    }

    public static String spellChecker(String word, int threshold, String[] dictionary) {
        String closestWord = "";
        int minDistance = threshold + 1;
        for (int i = 0; i < dictionary.length; i++) {
            int d = levenshtein(word, dictionary[i]);//current distance
            //if current distance is the new minimum, replace distance and minimum distance
            if (d < minDistance) {
                minDistance = d;
                closestWord = dictionary[i];
            }
        }

        if (minDistance <= threshold) {//no matching word within threshold
            return closestWord;
        }
        return word;
    }

}
