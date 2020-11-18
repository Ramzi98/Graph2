import m1graf2020.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    addEdge(n1, n2,weight);
                    List<Integer>  list_weight = new ArrayList<>();
                    list_weight.add(weight);
                    list_weight.add(0);
                    EdgesWeights.put(new Edge(n1,n2),list_weight);
                }
            }
        }
    }

    public Node getStartNodeFlow()
    {
        for (Node n : getAllNodes())
        {
            if(n.getName().equals("s"))
            {
                return n;
            }
        }
        return null;
    }

    public Node getEndNodeFlow()
    {
        for (Node n : getAllNodes())
        {
            if(n.getName().equals("t"))
            {
                return n;
            }
        }
        return null;
    }

    public void FlowGraph(ResidualNetwork f)
    {

    }

}
