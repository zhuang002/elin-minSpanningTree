import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class Graph2 {
	List<Path2> paths = new ArrayList<>();
	Set<Set<Integer>> subsets = new HashSet<>();

	public static Graph2 loadGraph(String fname) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		Graph2 graph = new Graph2();
		
		Scanner sc = new Scanner(new File(fname));
		
		int noOfNodes = sc.nextInt();
		int noOfPaths = sc.nextInt();
		
		for (int i=0;i<noOfPaths;i++) {
			graph.paths.add(new Path2(sc.nextInt(), sc.nextInt(), sc.nextInt()));
		}
		
		
		return graph;
	}

	public Graph2 getMiniSpanningTreeKruskal() {
		// TODO Auto-generated method stub
		Collections.sort(this.paths, (x, y) -> x.length-y.length);
		this.subsets.clear();
		
		Graph2 retGraph = new Graph2();
		for (Path2 path:this.paths) {
			// step 1: check the nodes are included or not
			// step 2: check the nodes are in same subset or not
			// step 3: possibly add path and merge sbusets.
			Set<Integer> subset1 = getSubset(path.node1);
			Set<Integer> subset2 = getSubset(path.node2);
			
			if (subset1 == null) {
				if (subset2 == null) {
					// if both nodes are not included, we need to create a subset for the 2 nodes and add the path.
					Set<Integer> subset = new HashSet<>();
					subset.add(path.node1);
					subset.add(path.node2);
					this.subsets.add(subset);
					
					retGraph.paths.add(path);
				} else {
					// the node1 is not included but node2 is included. So we need to add node1 to the sebset of node2, and add the path.
					subset2.add(path.node1);
					retGraph.paths.add(path);				
				}
			} else {
				if (subset2 == null) {
					// the node1 is included but node2 is not include. We need to add node2 to the subset of node1, and add the path.
					subset1.add(path.node2);
					retGraph.paths.add(path);
				}
				else {
					// both node1 and node 2 are included. we need to check if they are in the same subset.
					if (subset1 != subset2) {
						// they are not in the same subset. We should add the path and merge the 2 subsets.
						retGraph.paths.add(path);
						subset1.addAll(subset2);
						this.subsets.remove(subset2);
					}
				}
			}
		}
		
		
		return retGraph;
	}

	private Set<Integer> getSubset(int node) {
		// TODO Auto-generated method stub
		for (Set<Integer> subset: subsets) {
			if (subset.contains(node))
				return subset;
		}
		return null;
	}
	
	
	@Override
	public String toString() {
		String printString = "";
		Set<Integer> nodes = new HashSet<>();
		for (Path2 path: this.paths) {
			nodes.add(path.node1);
			nodes.add(path.node2);
		}
		printString+=nodes.size()+"\n"+this.paths.size()+"\n";
		for (Path2 path:this.paths) {
			printString+=path.node1+" "+path.node2+" "+path.length+"\n";
		}
		
		
		return printString;
	}
	
}

class Path2 {
	int node1,node2;
	int length;
	
	public Path2(int n1, int n2, int len) {
		this.node1 = n1;
		this.node2 = n2;
		this.length = len;
	}
}

