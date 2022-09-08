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
                INode node = this.trie.find(s);
                if (node != null) {
                    words.put(s , node.getValue());
                }
            }
            if (words.isEmpty()) {
                Set<String> distTwoWords = this.distanceTwo(distOneWords);
                for (String s : distTwoWords) {
                    INode node = this.trie.find(s);
                    if (node != null) {
                        words.put(s , node.getValue());
                    }
                }
            }
            if (words.isEmpty()) {
                return null;
            }
            else {
                AtomicReference<String> suggestion = new AtomicReference<>();
                AtomicInteger start = new AtomicInteger(0);
                words.forEach((key, value) -> {
                    if (start.get() < value) {
                        start.set(value);
                        suggestion.set(key);

                    } else if (start.get() == value) {
                        if (suggestion.toString().compareToIgnoreCase(key) > 0) {
                            start.set(value);
                            suggestion.set(key);
                        }
                    }
                });
                return suggestion.toString();
            }
        }

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
            stringBuilder.replace(i,i+1,"");
            distOneWords.add(stringBuilder.toString());
            stringBuilder = new StringBuilder(input);
        }
        //add char
        for (int i = 0; i <= input.length(); ++i) {
            for (int j = 0; j < 26; j++) {
                char newChar = (char)('a' + j);
                stringBuilder.insert(i,newChar);
                distOneWords.add(stringBuilder.toString());
                stringBuilder = new StringBuilder(input);
            }
        }
        //transpose char
        for (int i = 0; i < input.length(); ++i) {
            if (i < input.length() - 1) {
                char charOne = input.charAt(i);
                char charTwo = input.charAt(i+1);
                stringBuilder.setCharAt(i,charTwo);
                stringBuilder.setCharAt(i+1,charOne);
                distOneWords.add(stringBuilder.toString());
                stringBuilder = new StringBuilder(input);
            }
        }
        return distOneWords;
    }

    public Set<String> distanceTwo(Set<String> distOneWords) {
        Set<String> distTwoWords = new HashSet<>();
        for (String s : distOneWords) {
            Set<String> temp = this.distanceOne(s);
            distTwoWords.addAll(temp);
        }
        return distTwoWords;
    }

}
