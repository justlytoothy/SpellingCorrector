package spell;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class SpellCorrector implements ISpellCorrector {
    public SpellCorrector() {}


    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);
        Trie trie = new Trie();
        while (scanner.hasNext()) {
            String str = scanner.next();
            trie.add(str);
        }
        System.out.println(trie.toString());
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return null;
    }
}
