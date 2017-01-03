import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Bipartite {
	// This function returns 1 if the graph is Bipartite, else 0
    private static int bipartite(ArrayList<Integer>[] adj) {
        //write your code here
		// Create a color array to store colors assigned to all veritces.
        // Vertex number is used as index in this array. The value '-1'
        // of  colorArr[i] is used to indicate that no color is assigned
        // to vertex 'i'.  The value 1 is used to indicate first color
        // is assigned and value 0 indicates second color is assigned.
		int[] colorArr = new int[adj.length];
		for(int i=0; i<colorArr.length;i++){
			colorArr[i] = -1;
		}

		// Assign first color to the source vertex
		colorArr[0] = 1;
		
		// Create a queue (FIFO) of vertex numbers and enqueue
        // source vertex for BFS traversal
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(0);
		
		// Run while there are vertices in queue (Similar to BFS)
		while (!queue.isEmpty()) {
			// Dequeue a vertex from queue
			int u = queue.poll();
			
			// Find all non-colored adjacent vertices
			for (int v: adj[u]) {
				// Destination v is not colored
				if (colorArr[v] == -1) {
					// Assign alternate color to this adjacent v of u
                    colorArr[v] = 1- colorArr[u];
					queue.add(v);
				}
				
				// Destination v is colored with same color as u
                else if (colorArr[v] == colorArr[u])
					return 0;
			}
		}
		// If we reach here, then all adjacent vertices can
        //  be colored with alternate color
		return 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        System.out.println(bipartite(adj));
    }
}

