package spell;

public class Node implements INode{
    private int count;
    private Node[] nodes;

    public Node() {
        this.count = 0;
        this.nodes = new Node[26];
    }

    @Override
    public int getValue() {
        return this.count;
    }
    @Override
    public void incrementValue() {
        this.count++;
    }
    @Override
    public INode[] getChildren() {
        return this.nodes;
    }
}
