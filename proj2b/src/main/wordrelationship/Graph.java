package main.wordrelationship;

import java.util.*;


public class Graph {
    private HashMap<Integer, ArrayList<Integer>> connectedTo;
    private int size;
    public Graph(int size) {
        this.connectedTo = new HashMap<>();
        this.size = size;
    }

//    private ArrayList<Integer> createBuckets() {
//        return new ArrayList<>();
//    }

    public void addEdge(int parent, int child) {
        if (!connectedTo.containsKey(parent)) {
            ArrayList<Integer> bucket = new ArrayList<>();
            bucket.add(child);
            connectedTo.put(parent, bucket);
        } else {
            connectedTo.get(parent).add(child);
        }
    }

    public List<Integer> directChildren(int parent) {
        return connectedTo.get(parent);
    }

    //possible optimization
    public List<Integer> allChildrenList(List<Integer> occurences) {
        Set<Integer> visited = new HashSet<>();
        List<Integer> totalIds = new ArrayList<>();
        if (occurences == null) {
            return totalIds;
        }
        for (int occurence : occurences) {
            if (!visited.contains(occurence)) {
                totalIds.add(occurence);
                allChildrenHelper(occurence, totalIds, visited);
            }
        }
        return totalIds;
    }

    private void allChildrenHelper(int parent, List<Integer> childrenList, Set<Integer> visited) {
        visited.add(parent);
        if (directChildren(parent) != null) {
            for (int parentId : directChildren(parent)) {
                if (!visited.contains(parentId)) {
                    childrenList.add(parentId);
                    allChildrenHelper(parentId, childrenList, visited);
                }
            }
        }
    }
}






