import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class StronglyConnected {
    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj) {
		int result = 0;
		Stack<Integer> stack = new Stack<>();
		
		// Mark all the vertices as not visited (For first DFS)
        int visited[] = new int[adj.length];
		
		// Fill vertices in stack according to their finishing times
		for(int i = 0; i < adj.length; i++){
			if(visited[i] == 0){
				dfs(adj, i, visited, stack);
			}
		}
		
		// get the reversed adj list
		ArrayList<Integer>[] rAdj = reverseEdges(adj);
		
		// Mark all the vertices as not visited (For second DFS)
		visited = new int[adj.length];
		
	    // Now process all vertices in order defined by Stack
		while (! stack.isEmpty()) {
			// Pop a vertex from stack
            int x = stack.pop();
			
			// get one Strongly connected component of the popped vertex
			if(visited[x] == 0){
				dfs(rAdj, x, visited, new Stack<Integer>());
				result++;
			}
		}
        return result;
    }
	
	private static void dfs(ArrayList<Integer>[] adj, int x, int visited[], Stack<Integer> stack){
		// Mark the current node as visited
		visited[x] = 1;
		
		// Recur for all the vertices adjacent to this vertex
		for(int i = 0; i < adj[x].size(); i++){
			if(visited[adj[x].get(i)] == 0){
				visited[adj[x].get(i)] = 1;
				dfs(adj, adj[x].get(i), visited, stack);
			}	
		}
		
		// All vertices reachable from x are processed by now, push x to Stack
		stack.push(x);
	}
	
	private static ArrayList<Integer>[] reverseEdges(ArrayList<Integer>[] adj){
		ArrayList<Integer>[] rAdj = new ArrayList[adj.length];
		for(int i = 0; i < adj.length; i++){
			rAdj[i] = new ArrayList<Integer>();
		}	
		for(int i = 0; i < adj.length; i++){
			// Recur for all the vertices adjacent to this vertex
			for(int j = 0; j < adj[i].size(); j++){
				rAdj[adj[i].get(j)].add(i);
			}
		}
		return rAdj;
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
        System.out.println(numberOfStronglyConnectedComponents(adj));
    }
}

