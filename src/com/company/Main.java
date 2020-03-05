package com.company;

import java.util.Map;

public class Main {

    static class Graph{
        int vertices;
        int matrix[][];

        public Graph(int vertex) {
            this.vertices = vertex;
            matrix = new int[vertex][vertex];
        }

        public void addEdge(int source, int destination, int weight) {
            //add edge
            matrix[source][destination]=weight;

            //add back edge for undirected graph
            matrix[destination][source] = weight;
        }

        //get the vertex with minimum distance which is not included in SPT
        int getMinimumVertex(boolean [] mst, int [] key){
            int minKey = Integer.MAX_VALUE;
            int vertex = -1;
            for (int i = 0; i <vertices ; i++) {
                if(mst[i]==false && minKey>key[i]){
                    minKey = key[i];
                    vertex = i;
                }
            }
            return vertex;
        }

        public void dijkstra_GetMinDistances(int sourceVertex){
            boolean[] spt = new boolean[vertices];
            int [] distance = new int[vertices];
            int INFINITY = Integer.MAX_VALUE;

            //Initialize all the distance to infinity
            for (int i = 0; i <vertices ; i++) {
                distance[i] = INFINITY;
            }

            //start from the vertex 0
            distance[sourceVertex] = 0;

            //create SPT
            for (int i = 0; i <vertices ; i++) {

                //get the vertex with the minimum distance
                int vertex_U = getMinimumVertex(spt, distance);

                //include this vertex in SPT
                spt[vertex_U] = true;

                //iterate through all the adjacent vertices of above vertex and update the keys
                for (int vertex_V = 0; vertex_V <vertices ; vertex_V++) {
                    //check of the edge between vertex_U and vertex_V
                    if(matrix[vertex_U][vertex_V]>0){
                        //check if this vertex 'vertex_V' already in spt and
                        // if distance[vertex_V]!=Infinity

                        if(spt[vertex_V]==false && matrix[vertex_U][vertex_V]!=INFINITY){
                            //check if distance needs an update or not
                            //means check total weight from source to vertex_V is less than
                            //the current distance value, if yes then update the distance

                            int newKey =  matrix[vertex_U][vertex_V] + distance[vertex_U];
                            if(newKey<distance[vertex_V])
                                distance[vertex_V] = newKey;
                        }
                    }
                }
            }
            //print shortest path tree
            printDijkstra(sourceVertex, distance);
        }

        public void printDijkstra(int sourceVertex, int [] key){
            System.out.println("Dijkstra Algorithm: (Adjacency Matrix)");
            for (int i = 0; i <vertices ; i++) {
                System.out.println("Source Vertex: " + sourceVertex + " to vertex " +   + i +
                        " distance: " + key[i]);
            }
        }
    }

    static Mappa map;
    public static void main(String[] args) {
        if ( args.length <= 0){
            return;
        }
        String path = args[0];
        map = new Utils().readMapFile(path);
        System.out.println(move(49,49, Spostamento.UP));
        System.out.println(move(49,49, Spostamento.DOWN));
        System.out.println(move(49,49, Spostamento.RIGHT));
        System.out.println(move(49,49, Spostamento.LEFT));


    }

    public static int move(int x, int y, Spostamento mossa){
        int xx = x; int yy = y;
        switch (mossa){
            case UP:
                xx--;
                break;
            case DOWN:
                xx++;
                break;
            case LEFT:
                yy--;
                break;
            case RIGHT:
                yy++;
                break;
        }

        if (xx < 0 || yy < 0 || xx >= map.height || yy >= map.width) return -1;
        return getCostOfMove(map.infoMap[xx][yy]);
    }

    public static int getCostOfMove(char cell){
        System.out.printf("Valore cella: %c, ", cell);
        int costo = -1;
        switch (cell){
            case '#':
                costo = 0;
                break;
            case '~':
                costo = 800;
                break;
            case '*':
                costo = 200;
                break;
            case '+':
                costo = 150;
                break;
            case 'X':
                costo = 120;
                break;
            case '_':
                costo = 100;
                break;
            case 'H':
                costo = 70;
                break;
            case 'T':
                costo = 50;
                break;
        }
        return costo;
    }
}
