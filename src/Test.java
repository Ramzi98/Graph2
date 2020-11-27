import m1graf2020.Edge;
import m1graf2020.Exceptiongraf;
import m1graf2020.Graf;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exceptiongraf, IOException {
        FlowNetwork g = new FlowNetwork("DOT\\dg.dot");
        int[][] mat = g.toMatrix();
        System.out.println(g.fordFulkerson(mat));
    }
}
