import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Using2DArray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Graph graph = Graph.loadGraph("graph2.dat");
			
			Graph tree = graph.getMiniSpanningTreeKruskal();
			
			System.out.println(tree);
			
		}

		

	}

	class Graph {
		int[][] ar = null;

		public static Graph loadGraph(String fname) throws FileNotFoundException {
			// TODO Auto-generated method stub
			
			Graph retGraph = new Graph();
			Scanner sc = new Scanner(new File(fname));
			
			int n = sc.nextInt();
			
			retGraph.ar = new int[n][n];
			
			int paths = sc.nextInt();
			
			for (int i=0;i<paths;i++) {
				int n1 = sc.nextInt();
				int n2 = sc.nextInt();
				int length = sc.nextInt();
				
				retGraph.ar[n1][n2] = length;
				retGraph.ar[n2][n1] = length;
			}
			
			return retGraph;
		}
		
		@Override
		public String toString() {
			String retString = "";
			retString+=this.ar.length+"\n";
			String pathsString = "";
			
			int noOfPaths = 0;
			for (int n1=0;n1<this.ar.length;n1++) {
				for (int n2=n1+1;n2<this.ar.length;n2++) {
					if (ar[n1][n2]!=0) {
						noOfPaths++;
						pathsString += n1+" "+n2+" "+ar[n1][n2]+"\n";
					} 
				}
			}
			
			retString += noOfPaths+"\n" + pathsString;
			return retString;
		}
		

		

		public Graph getMiniSpanningTreeKruskal() {
			// TODO Auto-generated method stub
			
			Graph retGraph = new Graph();
			int noOfNodes = this.ar.length;
			retGraph.ar = new int[noOfNodes][noOfNodes];
			
			Set<Set<Integer>> subsets = new HashSet<>();
			Set<Integer> includedNodes=new HashSet<Integer>();
			
			while (includedNodes.size() < noOfNodes) {
				Path path = findNonIncludedShortestPath(retGraph);
				if (path == null) {
					break;
				}
				Set<Integer> set1 = getSubset(path.n1,subsets );
				Set<Integer> set2 = getSubset(path.n2, subsets);
				
				if (set1==null) {
					if (set2 == null) {
						Set<Integer> newset = new HashSet<>();
						newset.add(path.n1);
						newset.add(path.n2);
						subsets.add(newset);
						retGraph.add(path);
						includedNodes.add(path.n1);
						includedNodes.add(path.n2);
					} else {
						set2.add(path.n1);
						retGraph.add(path);
					}
				} else {
					if (set2 == null) {
						set1.add(path.n2);
						retGraph.add(path);
						includedNodes.add(path.n1);
						includedNodes.add(path.n2);
					}
					else {
						if (set1!=set2) {
							set1.addAll(set2);
							subsets.remove(set2);
							retGraph.add(path);
							includedNodes.add(path.n1);
							includedNodes.add(path.n2);
						}
					}
				}
			}
			
			return retGraph;
		}

		private Path findNonIncludedShortestPath(Graph retGraph) {
			// TODO Auto-generated method stub
			int noOfNodes = this.ar.length;
			int shortestPathLength = Integer.MAX_VALUE;
			Path shortestPath = new Path();
			for (int n1=0;n1<noOfNodes;n1++) {
				for (int n2=n1+1;n2<noOfNodes;n2++) {
					if (this.ar[n1][n2]==0) continue;
					if (retGraph.ar[n1][n2]!=0) continue;
					if (this.ar[n1][2]<shortestPathLength) {
						shortestPathLength = this.ar[n1][n2];
						shortestPath.n1 = n1;
						shortestPath.n2 = n2;
						shortestPath.length = shortestPathLength;
					}
				}
			}
			if (shortestPath.length == 0) return null;
			return shortestPath;
		}

		private Set<Integer> getSubset(int node, Set<Set<Integer>> subsets) {
			// TODO Auto-generated method stub
			for (Set<Integer> subset:subsets) {
				if (subset.contains(node)) 
					return subset;
			}
			
			return null;
		}

		private void add(Path path) {
			// TODO Auto-generated method stub
			this.ar[path.n1][path.n2] = path.length;
			this.ar[path.n2][path.n1] = path.length;
		}
		
		
	}

	class Path {
		int n1,n2,length;
	}

	}

}
