package main.wordrelationship;
import edu.princeton.cs.algs4.In;

import java.util.*;


public class Graph {
   List<List<Integer>> connectedTo;
    public Graph (int size) {
        this.connectedTo = new ArrayList<>(size);

    }
    public void addEdge(int parent, int child) {
        connectedTo.get(parent).add(child);
    }
    public List<Integer> directChildren(int parent) {
        return connectedTo.get(parent);
    }
    public List<Integer> allChildren(int parent) {
        List<Integer> totalIds = new ArrayList<>();
        totalIds = allChildrenHelper(parent, totalIds);
        return totalIds;
    }
    public List<Integer> allChildrenHelper(int parent, List<Integer> childrenList) {
        childrenList.add(parent);
        for (int parentId : directChildren(parent)) {
            childrenList.add(parentId);
            allChildrenHelper(parentId, childrenList);
        }
        return childrenList;
    }
}






