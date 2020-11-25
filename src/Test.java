import m1graf2020.Edge;
import m1graf2020.Exceptiongraf;
import m1graf2020.Graf;

public class Test {
    public static void main(String[] args) throws Exceptiongraf {
        FlowNetwork g = new FlowNetwork("DOT\\dg.dot");

        System.out.println("Nodes : "+g.getAllNodes());
        System.out.println("Edges : "+g.getAllEdges());
        System.out.println("startnode : "+g.getStartNodeFlow());
        System.out.println("stopnode : "+g.getEndNodeFlow());
        System.out.println("Max Flow: " + g.fordFulkerson(g.getStartNodeFlow().getId()-1,g.getEndNodeFlow().getId()-1));
    }
}
