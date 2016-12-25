import java.util.ArrayList;
import java.util.Scanner;

public class Acyclicity {
    private static int acyclic(ArrayList<Integer>[] adj) {
        // Mark all the vertices as not visited and not part of recursion stack
		int[] visited = new int[adj.length];
		int[] recStack = new int[adj.length];
		// Call the recursive helper function to detect cycle in different DFS trees
		for (int i = 0; i < adj.length; i++) {
			if (visited[i] == 0) {
				if (dfs(adj, i, visited, recStack) == 1) 
					return 1;
			}
		}
        return 0;
    }
	
	private static int dfs(ArrayList<Integer>[] adj, int x, int[] visited, int[] recStack) {
		// Mark the current node as visited and part of recursion stack
		visited[x] = 1;
		recStack[x] = 1;
		// Recur for all the vertices adjacent to this vertex
		for (int i = 0; i < adj[x].size(); i++) {
			if (visited[adj[x].get(i)] == 0 && dfs(adj, adj[x].get(i), visited, recStack) == 1)
				return 1;
			else if(recStack[adj[x].get(i)] == 1)
				return 1;
		}
		recStack[x] = 0;  // remove the vertex from recursion stack
		return 0;
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
        }
        System.out.println(acyclic(adj));
    }
}

