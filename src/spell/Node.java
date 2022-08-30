package spell;

public class Node implements INode{
    private int count = 0;
    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public INode[] getChildren() {
        return new INode[0];
    }
}
