import m1graf2020.Exceptiongraf;
import m1graf2020.Graf;

public class Test {
    public static void main(String[] args) throws Exceptiongraf {
        FlowNetwork g = new FlowNetwork("C:\\Users\\ramzi\\IdeaProjects\\Graph2\\DOT\\dg.dot");

        System.out.println("Nodes : "+g.getAllNodes());
        System.out.println("Edges : "+g.getAllEdges());
    }
}
