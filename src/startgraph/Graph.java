package startgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private List<Vertex> vertices = new ArrayList<>();
    //  find en måde at gemme Edge objekter
    private Map<Vertex, List<Edge>> edgeMap = new HashMap<>();

    private List<Integer> getNeighbors(int vertex){
        List<Integer> list = new ArrayList<>();
        List<Edge> edgeList = edgeMap.get(getVertex(vertex));
        for (Edge edge : edgeList) {
            list.add(edge.endVertex);
        }
        return list;
    }

    public boolean addVertex(Vertex vertex){
        if(!vertices.contains(vertex)){
            vertices.add(vertex);
            edgeMap.put(vertex, new ArrayList<>());
            return true;
        }
        return false;
    }

    public Vertex getVertex(int index){
        if(index >= 0 && index < vertices.size()) {
            return vertices.get(index);
        }
        return null;
    }

    public void addEdge(Edge edge){
        //1. find en måde at gemme Edge objekter. DONE
        //2. Lav et NYT Edge objekt i denne metode, og gem
        Edge edge2 = new Edge(edge.startVertex, edge.endVertex);
        Vertex startVertex = getVertex(edge.startVertex);
        edgeMap.get(startVertex).add(edge2);
    }

    public void printGraph(){
        for (Map.Entry<Vertex, List<Edge>> entry : edgeMap.entrySet()) {
            System.out.println("Vertex: " + entry.getKey());
            for (Edge edge : entry.getValue()) {
                System.out.println(getVertex(edge.startVertex) + " to " + getVertex(edge.endVertex));
            }
        }
    }

    public Tree dfs(int root){
        // 1. besøg alle Vertices i grafen
        // kan godt anvende en privat rekursiv hjælper-metode
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = -1;
        }
        boolean[] isVisited = new boolean[vertices.size()];
        List<Integer> searchOrder = new ArrayList<>();
        dfs(root, parent, isVisited, searchOrder);
        return new Tree(root, searchOrder, parent);
    }

    private void dfs(int vertex, int[] parent, boolean[] isVisited, List<Integer> searchOrder){
            isVisited[vertex] = true;
            searchOrder.add(vertex);
        for (Integer neighbor : getNeighbors(vertex)) {
            if(! isVisited[neighbor]){
                parent[neighbor] = vertex;
                dfs(neighbor, parent, isVisited, searchOrder);
            }
        }
    }

    public class Tree{
        int root;
        List<Integer> searchOrder;
        int[] parent;

        public Tree(int root, List<Integer> searchOrder, int[] parent) {
            this.root = root;
            this.searchOrder = searchOrder;
            this.parent = parent;
        }
    }
}
