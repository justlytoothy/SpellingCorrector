package spell;

import java.io.IOException;
import java.util.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SpellCorrector implements ISpellCorrector {
    public SpellCorrector() {}
    private Trie trie;


    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);
        this.trie = new Trie();
        while (scanner.hasNext()) {
            String str = scanner.next();
            this.trie.add(str);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        Map<String,Integer> words = new HashMap<>();
        String checkWord = inputWord.toLowerCase();
        if (this.trie.find(checkWord) != null) {
            return checkWord;
        }
        else {
            Set<String> distOneWords = this.distanceOne(checkWord);
            for (String s : distOneWords) {
                if (this.trie.find(s) != null) {
                    if (words.containsKey(s)) {
                        int curr = words.get(s);
                        words.replace(s,curr + 1);
                    }
                    else {
                        words.put(s ,1);
                    }
                }
            }
            AtomicReference<String> suggestion = new AtomicReference<>();
            AtomicInteger start = new AtomicInteger(0);
            words.forEach((key, value) -> {
                if (start.get() < value) {
                    start.set(value);
                    suggestion.set(key);
                }
                else if (start.get() == value) {
                    //working here
                }
            });
            System.out.println(suggestion.toString());
            System.out.println(start.toString());
        }

        return null;
    }
    public Set<String> distanceOne(String input) {
        Set<String> distOneWords = new HashSet<>();
        StringBuilder stringBuilder = new StringBuilder(input);
        //replace each char with all others from alphabet
        for (int i = 0; i < input.length(); ++i) {
            char replaceChar = input.charAt(i);
            int skipIndex = replaceChar - 'a';
            for (int j = 0; j < 26; j++) {
                if (j != skipIndex) {
                    stringBuilder.setCharAt(i,((char)('a' + j)));
                    distOneWords.add(stringBuilder.toString());
                }
            }
            stringBuilder.setCharAt(i,replaceChar);
        }
        //remove one char
        for (int i = 0; i < input.length(); ++i) {
            char replaceChar = input.charAt(i);
            stringBuilder.replace(i,i+1,"");
            distOneWords.add(stringBuilder.toString());
            stringBuilder = new StringBuilder(input);
        }
        return distOneWords;
    }

}
