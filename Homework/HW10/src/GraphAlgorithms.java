import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Zhixiang Yan
 * @version 1.0
 * @userid zyan319
 * @GTID 903810954
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("the starting vertex and the graph cannot be null!");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        HashSet<Vertex<T>> vs = new HashSet<Vertex<T>>();
        Queue<Vertex<T>> que = new LinkedList<Vertex<T>>();
        List<Vertex<T>> list = new ArrayList<Vertex<T>>();
        que.add(start);
        while (!que.isEmpty()) {
            Vertex<T> vertex = que.remove();
            list.add(vertex);
            vs.add(vertex);
            for (VertexDistance<T> vertexDistance: adjList.get(vertex)) {
                if (!vs.contains(vertexDistance.getVertex())) {
                    que.add(vertexDistance.getVertex());
                }
            }
        }
        return list;

    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("the starting vertex and the graph cannot be null!");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Starting vertex does not exist in the graph!");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        HashSet<Vertex<T>> vs = new HashSet<Vertex<T>>();
        List<Vertex<T>> list = new ArrayList<Vertex<T>>();
        dfsHelper(start, vs, list, adjList);
        return list;
    }
    /**
     * Helper method for performing a dfs on a graph,
     * starting at the specified vertex.The visited
     * vertices are added to the list and visit set to avoid revisiting
     *
     * @param <T>      the generic typing of the data
     * @param start    the vertex to begin the DFS
     * @param vs  a HashSet to track visited vertices
     * @param list   the list to store the vertices in the order of adjacency list
     * @param adjList  the adjacency list representing the graph structure
     */
    private static <T> void dfsHelper(Vertex<T> start, HashSet<Vertex<T>> vs,
                                      List<Vertex<T>> list, Map<Vertex<T>, List<VertexDistance<T>>> adjList) {
        list.add(start);
        vs.add(start);
        for (VertexDistance<T> vertexDistance: adjList.get(start)) {
            if (!vs.contains(vertexDistance.getVertex())) {
                dfsHelper(vertexDistance.getVertex(), vs, list, adjList);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The starting vertex and the graph cannot be null!");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Starting vertex does not exist in the graph!");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        HashSet<Vertex<T>> vs = new HashSet<Vertex<T>>();
        Map<Vertex<T>, Integer> dm = new HashMap<Vertex<T>, Integer>();
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<VertexDistance<T>>();

        for (Vertex<T> vertex : adjList.keySet()) {
            dm.put(vertex, Integer.MAX_VALUE);
        }

        pq.add(new VertexDistance<>(start, 0));
        dm.put(start, 0);

        while (!pq.isEmpty() && vs.size() < adjList.size()) {
            VertexDistance<T> currVertexDistance = pq.remove();

            if (!vs.contains(currVertexDistance.getVertex())) {
                vs.add(currVertexDistance.getVertex());

                for (VertexDistance<T> vertexDistance: adjList.get(currVertexDistance.getVertex())) {
                    Integer currentDistance = dm.get(vertexDistance.getVertex());
                    if (currentDistance != null
                            &&
                            currentDistance.
                                    compareTo(currVertexDistance.getDistance() + vertexDistance.getDistance()) > 0) {
                        dm.put(vertexDistance.getVertex(),
                                currVertexDistance.getDistance() + vertexDistance.getDistance());
                    }
                    pq.add(new VertexDistance<T>(vertexDistance.getVertex(),
                            currVertexDistance.getDistance() + vertexDistance.getDistance()));
                }

            }

        }
        return dm;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * An MST should NOT have self-loops or parallel edges.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        DisjointSet<T> ds = new DisjointSet<>();
        for (Vertex<T> vertex: graph.getVertices()) {
            ds.find(vertex);
        }
        Set<Edge<T>> mst = new HashSet<Edge<T>>();
        PriorityQueue<Edge<T>> priorityQueue = new PriorityQueue<Edge<T>>(graph.getEdges());
        while (!priorityQueue.isEmpty() && mst.size() < graph.getEdges().size() - 1) {
            Edge<T> edge = priorityQueue.remove();
            Vertex<T> u = edge.getU();
            Vertex<T> v = edge.getV();
            if (ds.find(edge.getU().getData()) != ds.find(v.getData())) {
                mst.add(edge);
                ds.union(u.getData(), v.getData());
                Edge<T> reverseEdge = new Edge<>(v, u, edge.getWeight());
                mst.add(reverseEdge);
                ds.union(v.getData(), u.getData());
            }
        }
        return mst;
    }
}
