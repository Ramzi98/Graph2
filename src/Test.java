import m1graf2020.Exceptiongraf;

import java.io.IOException;


public class Test {
    public static void main(String[] args) throws Exceptiongraf, IOException {
        FlowNetwork g = new FlowNetwork("DOT\\dg1.dot");
        int[][] mat = g.toMatrix();
        g.ford_fulkerson_execute(mat);
    }
}
