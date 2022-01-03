import java.io.FileNotFoundException;

public class UsingNodesPaths {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Graph2 graph = Graph2.loadGraph("graph2.dat");

		Graph2 tree = graph.getMiniSpanningTreeKruskal();

		System.out.println(tree);
	}

}
