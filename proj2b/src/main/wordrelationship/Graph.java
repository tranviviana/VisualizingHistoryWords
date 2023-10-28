package main.wordrelationship;

import java.util.*;


public class Graph {
    private List<List<Integer>> connectedTo;
    public Graph(int size) {
        this.connectedTo = new ArrayList<>();
        createBuckets(size);
    }
    public void createBuckets(int size) {
        int i = 0;
        while (i < size) {
            connectedTo.add(new ArrayList<>());
            i++;
        }
    }
    public void addEdge(int parent, int child) {
        List<Integer> parentsChildren = new ArrayList<>(connectedTo.get(parent));
        parentsChildren.add(child);
        connectedTo.set(parent, parentsChildren);
    }
    public List<Integer> directChildren(int parent) {
        return connectedTo.get(parent);
    }
    public List<Integer> allChildrenList(List<Integer> occurences) {
        List<Integer> totalIds = new ArrayList<>();
        if (occurences == null) {
            return totalIds;
        }
        for (int occurence : occurences) {
            totalIds.add(occurence);
            allChildrenHelper(occurence, totalIds);
        }
        return totalIds;
    }
    private List<Integer> allChildrenHelper(int parent, List<Integer> childrenList) {
        for (int parentId : directChildren(parent)) {
            childrenList.add(parentId);
            allChildrenHelper(parentId, childrenList);
        }
        return childrenList;
    }
}






