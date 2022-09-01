package spell;
import java.util.ArrayList;
public class Trie implements ITrie{

    private Node root;
    private int wordCount;
    private int nodeCount;

    public Trie() {
        this.root = new Node();
        this.wordCount = 0;
        this.nodeCount = 1;
    }

    @Override
    public void add(String word) {

        String lowerWord = word.toLowerCase();
        int index = lowerWord.charAt(0) - 'a';
        INode currNode = this.root;
        for (int i = 0; i < lowerWord.length(); ++i) {
            index = lowerWord.charAt(i) - 'a';
            INode[] nodes = currNode.getChildren();
            if (nodes[index] == null) {
                nodes[index] = new Node();
                this.nodeCount++;
            }
            currNode = currNode.getChildren()[index];
            if (i == lowerWord.length() - 1) {
                if (currNode.getValue() == 0) {
                    this.wordCount++;
                }
                currNode.incrementValue();
            }
        }


    }

    @Override
    public INode find(String word) {
        String checkWord = word.toLowerCase();
        INode currNode = this.root;
        for (int i = 0; i < checkWord.length(); ++i) {
            if (currNode.getChildren()[checkWord.charAt(i) - 'a'] == null) {
                return null;
            }
            else {
                currNode = currNode.getChildren()[checkWord.charAt(i) - 'a'];
            }
        }
        if (currNode.getValue() != 0) {
            return currNode;
        }
        else {
            return null;
        }
    }

    @Override
    public int getWordCount() {
        return this.wordCount;
    }

    @Override
    public int getNodeCount() {
        return this.nodeCount;
    }
    //word then newline
    @Override
    public String toString() {
        StringBuilder currWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toStringHelper(this.root, currWord, output);
        return output.toString();
    }
    private void toStringHelper(INode node, StringBuilder currWord, StringBuilder output) {
        if (node.getValue() > 0) {
            output.append(currWord.toString());
            output.append("\n");
        }
        for (int i = 0; i < node.getChildren().length; ++i) {
            INode child = node.getChildren()[i];
            if (child != null) {
                char childLetter = (char)('a' + i);
                currWord.append(childLetter);
                toStringHelper(child, currWord, output);
                currWord.deleteCharAt(currWord.length() - 1);
            }
        }
    }
    //what index to place it at
    @Override
    public int hashCode() {
        int hash = this.wordCount;
        hash *= this.nodeCount;
        for (int i = 0; i < this.root.getChildren().length; ++i) {
            if (this.root.getChildren()[i] != null) {
                hash *= i;
            }
        }
        return hash;
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
        if (this.getNodeCount() != check.getNodeCount() || this.getWordCount() != check.getWordCount()) {
            return false;
        }
        return equalsHelper(this.root,check.root);
        //now check all the variables and return accordingly on check created
    }
    private boolean equalsHelper(INode n1, INode n2) {
        if (n1 == null || n2 == null) {
            return true;
        }
        if (n1.getValue() != n2.getValue()) {
            return false;
        }
        for (int i = 0; i < n1.getChildren().length; ++i) {
            if (n1.getChildren()[i] == null && n2.getChildren()[i] != null || n2.getChildren()[i] == null && n1.getChildren()[i] != null) {
                return false;
            }
        }
        for (int i = 0; i < n1.getChildren().length; ++i) {
            if (!equalsHelper(n1.getChildren()[i], n2.getChildren()[i])) {
                return false;
            }
        }
        return true;
    }
}
