package spell;

public class Trie implements ITrie{

    Node [] hashTable;
    @Override
    public void add(String word) {
        int index = (word.hashCode() % hashTable.length);
        hashTable[index] = word;

    }

    @Override
    public INode find(String word) {
        int index = (word.hashCode() % hashTable.length);
        return hashTable[index];
    }

    @Override
    public int getWordCount() {
        return 0;
    }

    @Override
    public int getNodeCount() {
        return 0;
    }
    //word then newline
    @Override
    public String toString() {
        return "Implement toString";
    }
    //what index to place it at
    @Override
    public int hashCode() {
        return 5;
    }
    //must be recursive
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Trie check = (Trie)obj;
        //now check all the variables and return accordingly on check created
        return false;
    }
}
