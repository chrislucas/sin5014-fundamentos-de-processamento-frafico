package br.dp;

import java.util.ArrayList;

/**
 * Created by r028367 on 27/02/2017.
 * Shortest Paths
 *
 * BOA
 * http://algo.epfl.ch/_media/en/courses/2011-2012/algorithmique-cycles-2011a.pdf
 * https://www.hackerearth.com/practice/algorithms/graphs/shortest-path-algorithms/tutorial/
 *
 */
public class AlgorithmBellmanFord {

    public static ArrayList<ArrayList<Edge>> graph;
    public static final int INF = Integer.MAX_VALUE;

    public static int [] distances, predecessor;
    public static int V, E;

    public static class Edge {
        int src, dest, weight;
        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    public static void initialize(int source, int vertices, int edges) {
        V = vertices;
        E = edges;
        graph = new ArrayList<ArrayList<Edge>>();
        for(int i=0; i<E; i++) {
            graph.add(new ArrayList<Edge>());
        }
        distances = new int[vertices];
        predecessor = new int[vertices];
        for(int i=0; i<vertices; i++)
            distances[i] = i == source ? 0 : INF;
    }

    public static void add(Edge edge) {
        graph.get(edge.src).add(edge);
    }


    public static boolean run() {
        initialize(0, 5, 8);

        add(new Edge(0, 1, 1));
        add(new Edge(0, 2, 4));
        add(new Edge(1, 2, 3));
        add(new Edge(1, 3, 2));
        add(new Edge(1, 4, 2));
        add(new Edge(3, 1, 1));
        add(new Edge(3, 2, 5));
        add(new Edge(4, 3, -3));

        // Relamento das arestas
        // calculando o menor custo do VERTICE fonte ao VERTICE destino
        for(int v=0; v<V-1; v++) {
            for(Edge edge : graph.get(v)) {
                int src = edge.src;
                int dst = edge.dest;
                int weight = edge.weight;
                int costToDestiny = distances[src] + weight;
                if(distances[src] != INF && costToDestiny < distances[dst]) {
                    distances[dst] = costToDestiny;
                    predecessor[dst] = src;
                }
            }
        }
        boolean answer = false;
        //for(int v=0; v<V; v++) {
            for(Edge edge : graph.get(V-1)) {
                int src = edge.src;
                int dst = edge.dest;
                int weight = edge.weight;
                int costToDestiny = distances[src] + weight;
                if(distances[src] != INF && costToDestiny < distances[dst]) {
                    answer = true;
                    break;
                }
            }
        //}

        for (int i=0; i<V; i++) {
            System.out.printf("%d %d\n", distances[i], predecessor[i]);
        }

        return answer;
    }


    public static void main(String[] args) {
        System.out.println(run());
    }

}
