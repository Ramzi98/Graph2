
import m1graf2020.Edge;
import m1graf2020.Exceptiongraf;
import m1graf2020.Graf;
import m1graf2020.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class FlowNetwork extends Graf {

    protected Boolean LR;
    protected String graph_name;
    protected Map<Edge, List<Integer>> EdgesWeights = new HashMap();
    protected int label;
    protected String induced_graph;
    private int[] parent; // Holds parent of a node when a path is found (filled by BFS)
    private Queue<Integer> queue; // Queue of nodes to explore (BFS to FIFO queue)
    private int noOfNodes; // The number of nodes of the given array
    private boolean[] visited; // Keeps track of the nodes that has been visited
    public int labels = 2;
    List<ArrayList<Integer>> finalPath = new ArrayList<ArrayList<Integer>>();
    // residualGraph[i][j] tells you if there's an edge between vertex i & j.
    // 0 = no edge, positive number = capacity of that edge
    public int[][] residualGraph;
    public FlowNetwork() throws Exceptiongraf {
        super(true);
        setWeighted(true);

    }
    public FlowNetwork(String path) throws Exceptiongraf {
        setWeighted(true);

        int id_max = 2;
        List<String> list = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int nbr_line = list.size();
        for (int i = 0; i < nbr_line; i++) {
            if (list.get(i).equals("\n")) {
                continue;
            }
            if (i == 0) {
                String[] line1 = list.get(i).split(" ");
                if (line1[0].equals("digraph")) {
                    graph_name = line1[1];
                }
            }else if (i == 1) {
                String[] line1 = list.get(i).split("=\"");
                line1 = line1[1].split("\"");
                if (line1[0].equals("LR")) {
                    LR = true;
                }
                else {
                    LR = false;
                }
            }else if (i == 2) {
                String[] line1 = list.get(i).split(" ");
                induced_graph = line1[3];

                String[] l = line1[1].split("\\(");
                l = l[1].split("\\)");
                label = Integer.parseInt(l[0]);
            }
            String[] Str = list.get(i).split(" ");
            int id ;
            Node n1,n2;
            int weight;
            if (i > 2 && i != nbr_line-1) {
                if (Str.length >= 3) {
                    if(Str[0].equals("s"))
                    {
                        n1 = new Node(1,"s");
                    }
                    else {
                        id = Integer.parseInt(Str[0])+1;
                        n1 = new Node(id);
                        if(id_max < id){
                            id_max = id;
                        }
                    }
                    if(Str[2].equals("t"))
                    {
                        n2 = new Node(id_max+1,"t");
                    }
                    else {
                        id = Integer.parseInt(Str[2])+1;
                        n2 = new Node(id);
                        if(id_max < id){
                            id_max = id;
                        }
                    }
                    String[] s1 = Str[5].split("]");
                    String[] s2 = s1[0].split("=");
                    weight = Integer.parseInt(s2[1]);
                    addNode(n1);
                    addNode(n2);
                    Edge e = new Edge(n1,n2,weight);
                    addEdge(n1, n2,weight);
                    List<Integer>  list_weight = new ArrayList<>();
                    list_weight.add(weight);
                    list_weight.add(0);
                    EdgesWeights.put(new Edge(n1,n2),list_weight);
                }
            }
        }
    }

    public boolean bfs(int source, int sink, int[][] graph) {
        // Mark all nodes as not visited
        for (int vertex = 1; vertex < noOfNodes; vertex++) {
            visited[vertex] = false;
        }

        // Add the source node and marks it visited
        queue.add(source);
        visited[source] = true;
        parent[source] = -1; // Source has no parent

        // Standard Breadth First Search (BFS) loop
        while (!queue.isEmpty()) {
            // Return and remove the vertex from the front of the queue
            int element = queue.remove();

            // Visit all the adjacent nodes
            for (int destination = 0; destination < noOfNodes; destination++) {
                // Check if the u-v edge capacity > 0 and if a node is not already visited
                if (graph[element][destination] > 0 && !visited[destination]) {
                    parent[destination] = element;
                    queue.add(destination);
                    visited[destination] = true;
                }
            }
        }
        return (visited[sink]); // Return true if the sink node has been reached
    }

    /**
     * Runs the algorithm and calculates the maximum possible flow of the
     * given graph from source to the sink.
     *
     * @param graph  The given flow graph graph.
     * @return int Returns the maximum possible flow of the given graph.
     */
    public int fordFulkerson(int[][] graph) throws IOException {
        int u, v;
        int source = this.getStartNodeFlow().getId()-1;
        int sink = this.getEndNodeFlow().getId()-1;
        int maximumFlow = 0; // Initialize the maximum possible flow to zero
        this.noOfNodes = graph.length;
        this.queue = new LinkedList<>();
        parent = new int[noOfNodes];
        visited = new boolean[noOfNodes];
        residualGraph = new int[noOfNodes][noOfNodes];
        // Initialize residual graph to be same as the original graph
        for (u = 0; u < noOfNodes; u++) {
            for (v = 0; v < noOfNodes; v++) {
                residualGraph[u][v] = graph[u][v];
            }
        }

        // Augment the flow while there is path from source to sink
        while (bfs(source, sink, residualGraph)) {

            // Find bottleneck (minimum) by looping over path from BFS using parent[]
            // array, so initially set it to the largest number possible.
            int pathFlow = Integer.MAX_VALUE;
            ArrayList<Integer> list = new ArrayList<>();
            // Find the maximum flow through the path found
            // Loop backward through the path using parent[] array
            for (v = sink; v != source; v = parent[v]) {
                u = parent[v]; // Holds the previous node in the path
                // Minimum out of previous bottleneck & the capacity of the new edge
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
                list.add(u);
            }
            Collections.reverse(list);
            list.add(sink);
            finalPath.add(list);
            // Update the residual graph capacities & reverse edges along the path
            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] -= pathFlow; // Back edge
                residualGraph[v][u] += pathFlow; // Forward edge
            }
            for (int[] row : residualGraph)

                // converting each row as string
                // and then printing in a separate line
                System.out.println(Arrays.toString(row));
            // Add path flow to overall maximum flow
            maximumFlow += pathFlow;
            String str = this.ResidualGraphtoDot(residualGraph,pathFlow,list);
            System.out.println(str);
        }
        // Return the overall maximum flow
        return maximumFlow;
    }

    /**
     * This method validates the residual graph generated.
     *
     * @param sourceNode The source node of the given graph.
     * @param sinkNode   The sink node of the given graph.
     * @param matrix     The matrix of the given graph with the edges.
     * @return boolean Returns true if the generated residual graph is valid.
     */
    public boolean validateEdges(int sourceNode, int sinkNode, int[][] matrix) {
        int noOfCols = matrix[0].length; // Number of columns of the graph
        int sumFromSource = 0;
        int sumToSink = 0;
        boolean valid = true; // Returns true if the given graph is valid

        for (int i = 0; i < noOfCols; i++) {
            sumFromSource += matrix[sourceNode][i]; // Sum of the edges from the source
        }
        for (int[] row : matrix) {
            sumToSink += row[sinkNode]; // Sum of the edges to the sink
        }

        // If the sum of the edges from the source and to the sink are equal,
        // then check if the edges from and to the other nodes are equal
        if (sumFromSource == sumToSink) {
            // Iterating through the number of nodes
            for (int node = 0; node < noOfCols; node++) {
                int sumFromNode = 0;
                int sumToNode = 0;

                // If the node is source or sink,
                // then don't check if the edges to and from the node is equal
                if (node != sourceNode && node != sinkNode) {
                    for (int i = 0; i < noOfCols; i++) {
                        sumFromNode += matrix[node][i];
                    }
                    for (int[] row : matrix) {
                        sumToNode += row[node];
                    }

                    // If the edges from and to a given node is not equal,
                    // then the graph is not valid
                    if (!(sumFromNode == sumToNode)) {
                        valid = false;
                    }
                }
            }
        } else {
            valid = false;
        }
        return valid; // Returns true if the given graph is valid
    }

    /**
     * This method converts the flow network into residual graph and prints the nodes
     * and their respective capacities of the flow network.
     *
     * @param graph The flow network of the user given graph.
     * @return int[][] Returns the residual graph of the user given graph.
     */
    public int[][] printResidual(int[][] graph) {
        // Initialize a graph to hold the converted residual graph
        int[][] finalResidual = new int[noOfNodes][noOfNodes];

        // Iterating through the rows and columns of the flow network
        for (int u = 0; u < noOfNodes; u++) {
            for (int v = 0; v < noOfNodes; v++) {
                if (graph[u][v] > residualGraph[u][v]) {
                    // Capacities of the residual graph is the difference between the
                    // capacities of the user given graph and the flow network
                    finalResidual[u][v] = graph[u][v] - residualGraph[u][v];
                } else
                    finalResidual[u][v] = 0;
            }
        }
        return finalResidual;
    }

    public Node getStartNodeFlow()
    {
        for (Node n : getAllNodes())
        {
            if(n.getName() == null)
            {
                continue;
            }
            else if(n.getName().equals("s"))
            {
                return n;
            }
        }
        return null;
    }

    public int[][] toMatrix(){
        ArrayList<Node> l;
        l = (ArrayList<Node>) this.getAllNodes();
        int x = 0;
        for(Node n : l) {
            if(x<n.getId()) x=n.getId();
        }
        int[][] matrix=new int[x][x];

        // initsialize the matrix

        for (int i=0;i<x;i++) {
            for(int j=0;j<x;j++) {
                matrix[i][j]=0;
            }
        }

        //  implimants the Adjacancy Matrix

        for (Node n: l) {
            for (Node node:this.adjList.get(n)) {
                Edge e1 = null;
                List<Edge> le = getAllEdges();
                for(Edge e : le)
                {
                    if(e.getEndnode().equals(node) && e.getStartnode().equals(n))
                    {
                        e1 = e;
                    }
                }

                matrix[n.getId()-1][node.getId()-1]= e1.getWeight();
            }

        }
        return matrix;
    }

    public Node getEndNodeFlow()
    {
        for (Node n : getAllNodes())
        {
            if(n.getName() == null)
            {
                continue;
            }
            else if(n.getName().equals("t"))
            {
                return n;
            }
        }
        return null;
    }


    public void DottoFlowGraph(Graf g)
    {

    }

    public void DottoResidualGraph(Graf g)
    {

    }

    public void FlowGraftoDot(Graf g)
    {

    }

    public String ResidualGraphtoDot(int[][] mat,int capacity,ArrayList<Integer> list)
    {
        Map<Node, List<Node>> adjList = new HashMap<>();
        adjList = AdjmatrixtoAdjlist(mat);
        String dotStringGraph = "digraph g {\n";
        dotStringGraph += "rankdir=\"LR\";\n";
        dotStringGraph += "label=\"("+ labels++ +") residual graph.\"\n";
        dotStringGraph += "Augementing path : "+list+".\n";
        dotStringGraph += "Residual capacity : "+capacity+"\";\n";

        TreeMap<Node, List<Node>> sorted = new TreeMap<>(adjList);

        int i =0,j=1;
        for (Map.Entry<Node, List<Node>> entry : sorted.entrySet()) {
            int nodeFrom = entry.getKey().getId();
                Collections.sort(entry.getValue());
                for (Node nod : entry.getValue()) {
                    int nodeto = nod.getId();
                            dotStringGraph += " " + (nodeFrom-1) + " -> " + (nodeto-1) + "[label=\""+mat[nodeFrom-1][nodeto-1]+"\"]; \n";
                        }
        }

        dotStringGraph += "}";
        return dotStringGraph;
    }

//,int label,int[] path,int capacity
    public Map<Node, List<Node>> AdjmatrixtoAdjlist(int[][] mat)
    {
        Map<Node, List<Node>> adjList = new HashMap<>();

        for (int i = 0 ; i < mat.length; i++) {
            Node n = new Node(i+1);
            List<Node> nodeList = new ArrayList<>();
            for(int j= 0; j< mat.length; j++) {
                if(mat[i][j] > 0) {
                    Node n2 = new Node(j+1);
                    if(!nodeList.contains(n2))
                        nodeList.add(n2);
                }
                adjList.put(n,nodeList);
            }
        }
        return adjList;
    }

}
