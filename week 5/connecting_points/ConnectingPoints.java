import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Comparator;

public class ConnectingPoints {
	private static class Node {
		int x;
		int y;
		int parent;
		int rank;
		
		public Node(int a, int b, int c) {
			x = a;
			y = b;
			parent = c;
			rank = 0;
		}
	}
	
	private static class Edge {
		int u;
		int v;
		double weight;
		
		public Edge(int a, int b, double c) {
			u = a;
			v = b;
			weight = c;
		}
	}	
	
	private static void MakeSet(int i, Node[] nodes, int[] x, int[] y) {
		nodes[i] = new Node(x[i], y[i], i);
	}
	
	private static double Weight(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
	
	private static int Find(int i, Node[] nodes) {
		if (i != nodes[i].parent) {
			nodes[i].parent = Find(nodes[i].parent, nodes);
		}
		return nodes[i].parent;
	}
	
	private static void Union(int u, int v, Node[] nodes) {
		int r1 = Find(u, nodes);
		int r2 = Find(v, nodes);
		if (r1 != r2) {
			if (nodes[r1].rank > nodes[r2].rank) {
			nodes[r2].parent = r1;
			} else {
				nodes[r1].parent = r2;
				if (nodes[r1].rank == nodes[r2].rank) {
					nodes[r2].rank++;
				}
			}
		}
	}
	
    private static double minimumDistance(int[] x, int[] y) {
        double result = 0.;
        //write your code here
		int n = x.length;
		Node[] nodes = new Node[n];
		for (int i = 0; i < n; i++) {
			MakeSet(i, nodes, x, y);
		}
		PriorityQueue<Edge> edges = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.weight < e2.weight ? -1 : 1;
            }
        });
		for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                edges.offer(new Edge(i, j, Weight(x[i], y[i], x[j], y[j])));
            }
        }
		while (!edges.isEmpty()) {
			Edge currentEdge = edges.poll();
			int u = currentEdge.u;
			int v = currentEdge.v;
			if (Find(u, nodes) != Find(v, nodes)) {
                result += currentEdge.weight;
				Union(u, v, nodes);
			}
		}
		
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        System.out.println(minimumDistance(x, y));
    }
}

