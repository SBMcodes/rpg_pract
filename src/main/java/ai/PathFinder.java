package ai;

import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {
    GamePanel gp;
    Node[][] nodeMatrix;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    public int step=0;
    Node startNode,goalNode,currentNode;
    boolean goalReached=false;
    public int maxSteps=25;

    public PathFinder(GamePanel gp){
        this.gp=gp;
        instantiateNodes();
    }

    public void instantiateNodes(){
        nodeMatrix = new Node[gp.maxWorldRow][gp.maxWorldCol];
        for (int row = 0; row <gp.maxWorldRow ; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                nodeMatrix[row][col] = new Node(row,col);
            }
        }
        setSolidNodes();
    }

    public void resetNodes(){
        for (int row = 0; row <gp.maxWorldRow ; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                if(nodeMatrix[row][col].nodeState!=NodeState.SOLID){
                    nodeMatrix[row][col].nodeState=NodeState.OPEN;
                }
            }
        }
        openList.clear();
        pathList.clear();
        goalReached=false;
        step=0;
    }

    public void setSolidNodes(){
        for (int row = 0; row <gp.maxWorldRow ; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                if(gp.tileManager.tile[gp.tileManager.mapTileNum[gp.currentMap][row][col]].collision){
                    nodeMatrix[row][col].nodeState=NodeState.SOLID;
                }
            }
        }

        for (int i = 0; i < gp.iTile[gp.currentMap].length; i++) {
            if(gp.iTile[gp.currentMap][i]!=null && gp.iTile[gp.currentMap][i].destructible){
                int iCol = gp.iTile[gp.currentMap][i].worldX/gp.tileSize;
                int iRow = gp.iTile[gp.currentMap][i].worldY/gp.tileSize;
                nodeMatrix[iRow][iCol].nodeState=NodeState.SOLID;
            }
        }
    }

    public void setNodeCost(Node node){
        int xDist = Math.abs(node.col- startNode.col),yDist = Math.abs(node.row- startNode.row);

        node.gCost = xDist+yDist;

        xDist = Math.abs(node.col- goalNode.col);
        yDist = Math.abs(node.row- goalNode.row);

        node.hCost = xDist+yDist;
        node.fCost = node.gCost+node.hCost;
    }

    public void setNodes(int startRow,int startCol,int goalRow,int goalCol){
        resetNodes();
        setSolidNodes();

        startNode=nodeMatrix[startRow][startCol];
        goalNode=nodeMatrix[goalRow][goalCol];
        currentNode=startNode;
        openList.add(currentNode);

        // Set Cost
        for (int row = 0; row <gp.maxWorldRow ; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                setNodeCost(nodeMatrix[row][col]);
            }
        }
    }


    public boolean search(){
        while (!goalReached && step<this.maxSteps){
            step++;
            int curRow= currentNode.row,curCol= currentNode.col;

            currentNode.nodeState=NodeState.CHECKED;
            openList.remove(currentNode);

            if(curRow-1>=0 ){
                if(nodeMatrix[curRow-1][curCol].nodeState==NodeState.OPEN){
                    nodeMatrix[curRow-1][curCol].nodeState=NodeState.CHECKED;
                    nodeMatrix[curRow-1][curCol].parent=currentNode;
                    openList.add(nodeMatrix[curRow-1][curCol]);
                }
            }

            if(curCol-1>=0 ){
                if(nodeMatrix[curRow][curCol-1].nodeState==NodeState.OPEN){
                    nodeMatrix[curRow][curCol-1].nodeState=NodeState.CHECKED;
                    nodeMatrix[curRow][curCol-1].parent=currentNode;
                    openList.add(nodeMatrix[curRow][curCol-1]);
                }
            }

            if(curRow+1<gp.maxWorldRow ){
                if(nodeMatrix[curRow+1][curCol].nodeState==NodeState.OPEN){
                    nodeMatrix[curRow+1][curCol].nodeState=NodeState.CHECKED;
                    nodeMatrix[curRow+1][curCol].parent=currentNode;
                    openList.add(nodeMatrix[curRow+1][curCol]);
                }
            }
            if(curCol+1<gp.maxWorldCol ){
                if(nodeMatrix[curRow][curCol+1].nodeState==NodeState.OPEN){
                    nodeMatrix[curRow][curCol+1].nodeState=NodeState.CHECKED;
                    nodeMatrix[curRow][curCol+1].parent=currentNode;
                    openList.add(nodeMatrix[curRow][curCol+1]);
                }
            }

            int bestNodeIdx=-1,bestNodeFcost=9999;

            for (int i = 0; i < openList.size(); i++) {
                if(openList.get(i).fCost<bestNodeFcost){
                    bestNodeIdx=i;
                    bestNodeFcost=openList.get(i).fCost;

                } else if (openList.get(i).fCost==bestNodeFcost) {
                    if(openList.get(i).gCost<openList.get(bestNodeIdx).gCost){
                        bestNodeIdx=i;
                    }
                }
            }

            if(openList.isEmpty()){
                break;
            }

            currentNode=openList.get(bestNodeIdx);
            if(currentNode==goalNode){
                goalReached=true;
                trackPath();
            }
        }

        return goalReached;
    }

    private void trackPath() {
        Node currentNode =goalNode;
        while(currentNode!=startNode){
            pathList.add(0,currentNode);
            currentNode=currentNode.parent;
        }
    }
}
