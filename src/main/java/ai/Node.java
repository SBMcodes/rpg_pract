package ai;

public class Node {
    Node parent;
    public int row,col;
    int gCost,hCost,fCost;
    NodeState nodeState=NodeState.OPEN;

    public Node(int row,int col){
        this.row=row;
        this.col=col;
    }
}
