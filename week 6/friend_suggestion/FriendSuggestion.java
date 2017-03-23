import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class FriendSuggestion {
    private static class Impl {
        // Number of nodes
        int n;
        // adj[0] and cost[0] store the initial graph, adj[1] and cost[1] store the reversed graph.
        // Each graph is stored as array of adjacency lists for each node. adj stores the edges,
        // and cost stores their costs.
        ArrayList<Integer>[][] adj;
        ArrayList<Integer>[][] cost;
        // distance[0] and distance[1] correspond to distance estimates in the forward and backward searches.
        Long[][] distance;
        // Two priority queues, one for forward and one for backward search.
        ArrayList<PriorityQueue<Entry>> queue;
        // visited[v] == true iff v was visited either by forward or backward search.
        boolean[] visited;
        // List of all the nodes which were visited either by forward or backward search.
        ArrayList<Integer> workset;
        final Long INFINITY = Long.MAX_VALUE / 4;

        Impl(int n) {
            this.n = n;
            visited = new boolean[n];
            Arrays.fill(visited, false);
            workset = new ArrayList<Integer>();
            distance = new Long[][] {new Long[n], new Long[n]};
            for (int i = 0; i < n; ++i) {
                distance[0][i] = distance[1][i] = INFINITY;
            }
            queue = new ArrayList<PriorityQueue<Entry>>();
            queue.add(new PriorityQueue<Entry>(n));
            queue.add(new PriorityQueue<Entry>(n));
        }

        // Reinitialize the data structures before new query after the previous query
        void clear() {
            for (int v : workset) {
                distance[0][v] = distance[1][v] = INFINITY;
                visited[v] = false;
            }
            workset.clear();
            queue.get(0).clear();
            queue.get(1).clear();
        }

        // Try to relax the distance from direction side to node v using value dist.
        void visit(int side, int v, Long dist) {
			// Implement this method yourself
			if ( distance[side][v] > dist) {
				 distance[side][v] = dist;
				 queue.get(side).add(new Entry(distance[side][v], v));
				 workset.add(v);
			}
        }
		
		int extractMin(int side) {
			Entry e = queue.get(side).poll();
			return e.node;
		}
		
		void Process(int side, int u, ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
			for (int i = 0; i < adj[u].size(); ++i) {
				int v = adj[u].get(i);
				int w = cost[u].get(i);
				visit(side, v, distance[side][u] + w);
			}
			
		}
		
		Long ShortestPath(int v) {
			Long dist = INFINITY;
			for(int u: workset) {
				if (distance[0][u] + distance[1][u] < dist) {
					dist = distance[0][u] + distance[1][u];
				}
			}
			if (dist == INFINITY)
				return -1L;
			return dist;
		}

        // Returns the distance from s to t in the graph.
        Long query(int s, int t) {
            clear();
            visit(0, s, 0L);
            visit(1, t, 0L);
            // Implement the rest of the algorithm yourself
			while (!queue.get(0).isEmpty() && !queue.get(1).isEmpty()) {
				int v = extractMin(0);
				Process(0, v, adj[0], cost[0]);
				if (visited[v] == true)
                    return ShortestPath(v);
				visited[v] = true;
				
				int v_r = extractMin(1);
				Process(1, v_r, adj[1], cost[1]);
				if (visited[v_r] == true)
                    return ShortestPath(v_r);
				visited[v_r] = true;
			}
			return -1L;
        }

        class Entry implements Comparable<Entry>
        {
            Long cost;
            int node;
          
            public Entry(Long cost, int node)
            {
                this.cost = cost;
                this.node = node;
            }
         
            public int compareTo(Entry other)
            {
                return cost < other.cost ? -1 : cost > other.cost ? 1 : 0;
            }
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        Impl bidij = new Impl(n);
        bidij.adj = (ArrayList<Integer>[][])new ArrayList[2][];
        bidij.cost = (ArrayList<Integer>[][])new ArrayList[2][];
        for (int side = 0; side < 2; ++side) {
            bidij.adj[side] = (ArrayList<Integer>[])new ArrayList[n];
            bidij.cost[side] = (ArrayList<Integer>[])new ArrayList[n];
            for (int i = 0; i < n; i++) {
                bidij.adj[side][i] = new ArrayList<Integer>();
                bidij.cost[side][i] = new ArrayList<Integer>();
            }
        }

        for (int i = 0; i < m; i++) {
            int x, y, c;
            x = in.nextInt();
            y = in.nextInt();
            c = in.nextInt();
            bidij.adj[0][x - 1].add(y - 1);
            bidij.cost[0][x - 1].add(c);
            bidij.adj[1][y - 1].add(x - 1);
            bidij.cost[1][y - 1].add(c);
        }

        int t = in.nextInt();

        for (int i = 0; i < t; i++) {
            int u, v;
            u = in.nextInt();
            v = in.nextInt();
            System.out.println(bidij.query(u-1, v-1));
        }
    }
}
