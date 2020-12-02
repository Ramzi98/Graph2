
import m1graf2020.Edge;
import m1graf2020.Exceptiongraf;
import m1graf2020.Graf;
import m1graf2020.Node;

import java.io.*;
import java.util.*;


public class FlowNetwork extends Graf {

    protected Boolean LR;
    protected String graph_name;
    protected Map<Edge, ArrayList<Integer>> EdgesWeights = new HashMap();
    protected int label;
    protected String induced_graph;
    private int[] parent; // Holds parent of a node when a path is found (filled by BFS)
    private Queue<Integer> queue; // Queue of nodes to explore (BFS to FIFO queue)
    private int noOfNodes; // The number of nodes of the given array
    private boolean[] visited; // Keeps track of the nodes that has been visited
    public int labels = 1;
    List<ArrayList<Integer>> finalPath = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
    List<int[][]> rs_graphs = new ArrayList<>();
    ArrayList<Integer> path_flow = new ArrayList<>();
    int flow_id = 1;
    Map<ArrayList<Integer>,Integer> paths_cap = new HashMap<>();

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
                    String s1 = list.get(i).replaceAll(" ","");
                    s1 = s1.replaceAll("\"","");
                    String s2[] = s1.split("=");
                    String we = s2[1].replaceAll(";","");
                    we = we.replace(";","");
                    we = we.replace("\t","");
                    we = we.replace("]","");
                    weight = Integer.parseInt(we);
                    addNode(n1);
                    addNode(n2);
                    Edge e = new Edge(n1,n2,weight);
                    addEdge(n1, n2,weight);
                    ArrayList<Integer>  list_weight = new ArrayList<>();
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
                list.add(u+1);
            }
            Collections.reverse(list);
            list.add(sink+1);
            finalPath.add(list);
            // Update the residual graph capacities & reverse edges along the path
            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] -= pathFlow; // Back edge
                residualGraph[v][u] += pathFlow; // Forward edge
            }
            maximumFlow += pathFlow;
            path_flow.add(pathFlow);
            paths.add(list);
            paths_cap.put(list,pathFlow);

            int [][] g = new int[residualGraph.length][residualGraph.length];
            for(int i=0;i<residualGraph.length;i++)
            {
                for(int j=0;j<residualGraph.length;j++)
                {
                    g[i][j]= residualGraph[i][j];
                }
            }

            rs_graphs.add(g);
        }
        // Return the overall maximum flow
        return maximumFlow;
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

    public String ResidualGraphtoDot(int[][] mat,int capacity,ArrayList<Integer> list)
    {
        Map<Node, List<Node>> adjList = new HashMap<>();
        adjList = AdjmatrixtoAdjlist(mat);
        int sink = this.getEndNodeFlow().getId();
        boolean trouve = false;

        String dotStringGraph = "digraph g {\n";
        dotStringGraph += "rankdir=\"LR\";\n";
        dotStringGraph += "label=\"("+ labels++ +") residual graph.\n";
        dotStringGraph += "Augementing path: [";

        for (int l : list)
        {
            if(l == 1)
            {
                dotStringGraph +="s, ";
            }
            else if (l == sink)
            {
                dotStringGraph +="t].\n";
            }
            else
            {
                dotStringGraph += (l-1)+", ";
            }
        }


        dotStringGraph += "Residual capacity : "+capacity+"\";\n";

        TreeMap<Node, List<Node>> sorted = new TreeMap<>(adjList);

        List<Edge> edgeList = new ArrayList<>();

        for(int i=0;i<list.size()-1;i++)
        {
            edgeList.add(new Edge(list.get(i),list.get(i+1)));
        }

        for (Map.Entry<Node, List<Node>> entry : sorted.entrySet()) {
            int nodeFrom = entry.getKey().getId();
                Collections.sort(entry.getValue());
                for (Node nod : entry.getValue()) {
                    int nodeto = nod.getId();
                    String node_to ;
                    String node_from ;

                    if(nodeFrom == sink) { node_from = "t"; }
                    else if(nodeFrom == 1) { node_from = "s"; }
                    else {
                        int nf = nodeFrom - 1;
                        node_from = String.valueOf(nf);
                    }

                    if(nodeto == 1) { node_to = "s"; }
                    else if(nodeto == sink) { node_to = "t"; }
                    else {
                        int nt = nodeto - 1;
                        node_to = String.valueOf(nt);
                    }



                    for(Edge es : edgeList)
                    {
                        if((es.getStartnode().getId() == nodeFrom) && (es.getEndnode().getId() == nodeto))
                        {
                            trouve = true;
                            break;
                        }
                    }

                    if(trouve)
                    {
                        dotStringGraph += " " + node_from + " -> " + node_to + " [label=\""+mat[nodeFrom-1][nodeto-1]+"\", penwidth=3, color=\"bleu\"]; \n";
                        trouve = false;
                    }
                    else
                    {
                        dotStringGraph += " " + node_from + " -> " + node_to + " [label=\""+mat[nodeFrom-1][nodeto-1]+"\"]; \n";
                    }

                }
        }

        dotStringGraph += "}";
        return dotStringGraph;
    }


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

    public void ford_fulkerson_execute(int[][] mat) throws IOException {


        int max = fordFulkerson(mat);
        String residual_graf;


        System.out.println("First InitFlow graph");
        String First_Flow = printfirstflow();
        System.out.println(First_Flow);



        File flow = new File("DOT/flow"+(flow_id-1)+".dot");
        FileWriter fw1 = new FileWriter(flow);
        PrintWriter pw1 = new PrintWriter(fw1);
        pw1.print(First_Flow);
        pw1.close();

        System.out.println("First resudual graph");

        residual_graf = this.ResidualGraphtoDot(toMatrix(), path_flow.get(0), paths.get(0));
        System.out.println(residual_graf);
        File resudual1 = new File("DOT/residGraph"+(labels-1)+".dot");
        FileWriter fw = new FileWriter(resudual1);
        PrintWriter pw = new PrintWriter(fw);
        pw.print(residual_graf);
        pw.close();



        for (int i =0;i<rs_graphs.size()-1;i++)
        {
            printResidualgrafInDot(rs_graphs.get(i),paths.get(i+1),path_flow.get(i+1));
        }
        for (int i =0;i<rs_graphs.size();i++)
        {
            printFlowgrafInDot(rs_graphs.get(i),paths.get(i),path_flow.get(i));
        }

        //Print last residual
        String last_residual = printLastResidual(rs_graphs.get(rs_graphs.size()-1));
        System.out.println(last_residual);
        File resudual2 = new File("DOT/residGraph"+(labels-1)+".dot");
        FileWriter fw2 = new FileWriter(resudual2);
        PrintWriter pw2 = new PrintWriter(fw2);
        pw2.print(last_residual);
        pw2.close();


        System.out.println("Max flow = "+max);

    }

    public void printResidualgrafInDot(int[][] mat,ArrayList<Integer> path,int pathFlow) throws IOException {
        String residual_graf;
        residual_graf = this.ResidualGraphtoDot(mat,pathFlow,path);
        System.out.println(residual_graf);

        File resudual = new File("DOT/residGraph"+(labels-1)+".dot");
        FileWriter fw2 = new FileWriter(resudual);
        PrintWriter pw2 = new PrintWriter(fw2);
        pw2.print(residual_graf);
        pw2.close();
    }
    public void printFlowgrafInDot(int[][] mat,ArrayList<Integer> path,int pathFlow) throws IOException {
        String Flow;
        int v = 0;

        v += pathFlow;
        addvaluetoEdges(v,path);
        Flow = printflow(v,path);
        System.out.println(Flow);

        File flow = new File("DOT/flow"+(flow_id-1)+".dot");
        FileWriter fw1 = new FileWriter(flow);
        PrintWriter pw1 = new PrintWriter(fw1);
        pw1.print(Flow);
        pw1.close();

    }

    public String printflow(int value,ArrayList<Integer> path)
    {
        int sink = this.getEndNodeFlow().getId();
        List<Edge> edgeList = new ArrayList<>();

        String dotStringGraph = "digraph flow"+(labels)+" {\n";
        dotStringGraph += "rankdir=\"LR\";\n";
        dotStringGraph += "label=\"("+ flow_id++ +") Flow induced from residual graph"+(labels-2)+". Value : "+value+".\"\n";

        TreeMap<Node, List<Node>> sorted_adjlist = new TreeMap<>(adjList);

        for(int i=0;i<path.size()-1;i++)
        {
            edgeList.add(new Edge(path.get(i),path.get(i+1)));
        }
        for (Map.Entry<Node, List<Node>> entry : sorted_adjlist.entrySet()) {
            int nodeFrom = entry.getKey().getId();
            Collections.sort(entry.getValue());
            for (Node nod : entry.getValue()) {
                int nodeto = nod.getId();
                String node_to;
                String node_from;

                if (nodeFrom == sink) {
                    node_from = "t";
                } else if (nodeFrom == 1) {
                    node_from = "s";
                } else {
                    int nf = nodeFrom - 1;
                    node_from = String.valueOf(nf);
                }

                if (nodeto == 1) {
                    node_to = "s";
                } else if (nodeto == sink) {
                    node_to = "t";
                } else {
                    int nt = nodeto - 1;
                    node_to = String.valueOf(nt);
                }

                for (Edge e : getAllEdges()) {

                }

                int w = 0;
                int v = 0;
                for (Map.Entry<Edge, ArrayList<Integer>> el : EdgesWeights.entrySet()) {
                    Edge e = el.getKey();
                    ArrayList<Integer> value_weight = el.getValue();
                    if ((e.getStartnode().getId() == nodeFrom) && (e.getEndnode().getId() == nodeto)) {
                        v = value_weight.get(1);
                        w = value_weight.get(0);
                    }
                }

                if (v != 0) {
                    dotStringGraph += " " + node_from + " -> " + node_to + " [label=\"" + v + "/" + w + "\"]; \n";
                } else {
                    dotStringGraph += " " + node_from + " -> " + node_to + " [label=\"" + w + "\"]; \n";
                }

            }

        }

        dotStringGraph += "}";
        return dotStringGraph;

    }

    public void addvaluetoEdges(int v,ArrayList<Integer> path)
    {
        ArrayList<Edge> edgeList = new ArrayList<>();

        for(int i=0;i<path.size()-1;i++)
        {
            edgeList.add(new Edge(path.get(i),path.get(i+1)));
        }

        for (Map.Entry<Edge, ArrayList<Integer>> entry : EdgesWeights.entrySet()) {
            Edge e = entry.getKey();
            ArrayList<Integer> value_weight = entry.getValue();

            for (Edge es : edgeList) {
                if ((es.getStartnode().getId() == e.getStartnode().getId()) && (es.getEndnode().getId() == e.getEndnode().getId())) {
                    value_weight.set(1, value_weight.get(1)+v);
                }
            }
        }
    }


    public String printfirstflow()
    {
        int sink = this.getEndNodeFlow().getId();

        String dotStringGraph = "digraph flow1_init {\n";
        dotStringGraph += "rankdir=\"LR\";\n";
        dotStringGraph += "label=\" Flow initial. Value : 0.\"\n";
        flow_id++;

        TreeMap<Node, List<Node>> sorted_adjlist = new TreeMap<>(adjList);

        for (Map.Entry<Node, List<Node>> entry : sorted_adjlist.entrySet()) {
            int nodeFrom = entry.getKey().getId();
            Collections.sort(entry.getValue());
            for (Node nod : entry.getValue()) {
                int nodeto = nod.getId();
                String node_to;
                String node_from;

                if (nodeFrom == sink) {
                    node_from = "t";
                } else if (nodeFrom == 1) {
                    node_from = "s";
                } else {
                    int nf = nodeFrom - 1;
                    node_from = String.valueOf(nf);
                }

                if (nodeto == 1) {
                    node_to = "s";
                } else if (nodeto == sink) {
                    node_to = "t";
                } else {
                    int nt = nodeto - 1;
                    node_to = String.valueOf(nt);
                }


                int w = 0;
                for (Map.Entry<Edge, ArrayList<Integer>> el : EdgesWeights.entrySet()) {
                    Edge e = el.getKey();
                    ArrayList<Integer> value_weight = el.getValue();
                    if ((e.getStartnode().getId() == nodeFrom) && (e.getEndnode().getId() == nodeto)) {
                        w = value_weight.get(0);
                    }
                }

                dotStringGraph += " " + node_from + " -> " + node_to + " [label=\"" + w + "\"]; \n";


            }

        }

        dotStringGraph += "}";
        return dotStringGraph;
    }

    public String printLastResidual(int[][] mat)
    {
        Map<Node, List<Node>> adjList;
        adjList = AdjmatrixtoAdjlist(mat);
        int sink = this.getEndNodeFlow().getId();

        String dotStringGraph = "digraph g {\n";
        dotStringGraph += "rankdir=\"LR\";\n";
        dotStringGraph += "label=\"("+ labels++ +") residual graph.\n";
        dotStringGraph += "Augementing path: none.\n";
        dotStringGraph += "Previous flow was maximum .\"";

        TreeMap<Node, List<Node>> sorted = new TreeMap<>(adjList);

        for (Map.Entry<Node, List<Node>> entry : sorted.entrySet()) {
            int nodeFrom = entry.getKey().getId();
            Collections.sort(entry.getValue());
            for (Node nod : entry.getValue()) {
                int nodeto = nod.getId();
                String node_to ;
                String node_from ;

                if(nodeFrom == sink) { node_from = "t"; }
                else if(nodeFrom == 1) { node_from = "s"; }
                else {
                    int nf = nodeFrom - 1;
                    node_from = String.valueOf(nf);
                }

                if(nodeto == 1) { node_to = "s"; }
                else if(nodeto == sink) { node_to = "t"; }
                else {
                    int nt = nodeto - 1;
                    node_to = String.valueOf(nt);
                }

                dotStringGraph += " " + node_from + " -> " + node_to + " [label=\""+mat[nodeFrom-1][nodeto-1]+"\"]; \n";

            }
        }

        dotStringGraph += "}";
        return dotStringGraph;
    }

}
