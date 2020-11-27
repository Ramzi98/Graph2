
import m1graf2020.Edge;
import m1graf2020.Exceptiongraf;
import m1graf2020.Graf;
import m1graf2020.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class FlowNetwork extends Graf {

    protected Boolean LR;
    protected String graph_name;
    protected Map<Edge, List<Integer>> EdgesWeights = new HashMap();
    protected int label;
    protected String induced_graph;

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

    boolean bfs(int rGraph[][], int s, int t, int parent[])
    {
        int V = rGraph.length;
        // Create a visited array and mark all vertices as not
        // visited
        boolean visited[] = new boolean[V];
        for(int i=0; i<V; ++i)
            visited[i]=false;

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s]=-1;
        ArrayList<Integer> path = new ArrayList<>();

        // Standard BFS Loop
        while (queue.size()!=0)
        {
            int u = queue.poll();
            path.add(u);
            for (int v=0; v<V; v++)
            {
                if (visited[v]==false && rGraph[u][v] > 0)
                {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        for (int i = 0 ; i< visited.length;i++)
        {
            System.out.println(visited[i]);
        }

        // If we reached sink in BFS starting from source, then
        // return true, else false
        return (visited[t] == true);
    }

    // Returns tne maximum flow from s to t in the given graph
    int fordFulkerson(int graph[][])
    {
        int u, v;
        int V = graph.length;
        int s = this.getStartNodeFlow().getId()-1;
        int t = this.getEndNodeFlow().getId()-1;
        // Create a residual graph and fill the residual graph
        // with given capacities in the original graph as
        // residual capacities in residual graph

        // Residual graph where rGraph[i][j] indicates
        // residual capacity of edge from i to j (if there
        // is an edge. If rGraph[i][j] is 0, then there is
        // not)
        int rGraph[][] = new int[V][V];

        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                rGraph[u][v] = graph[u][v];

        // This array is filled by BFS and to store path
        int parent[] = new int[V];

        int max_flow = 0;  // There is no flow initially

        // Augment the flow while tere is path from source
        // to sink
        while (bfs(rGraph, s, t, parent))
        {
            // Find minimum residual capacity of the edhes
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v])
            {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }
            for(int i = 0;i< parent.length;i++) {
                //System.out.println(parent[i]);
            }
            // update residual capacities of the edges and
            // reverse edges along the path
            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
                for (int[] row : rGraph) {
                    System.out.println(Arrays.toString(row));
                }
                System.out.println(path_flow);

                System.out.println("\n");
            }

            // Add path flow to overall flow
            max_flow += path_flow;
        }

        // Return the overall flow
        return max_flow;
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

    public void ResidualGraphtoDot(Graf g)
    {

    }

}
