// Failed case #6/9: Wrong answer .. don't know why..

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.lang.Math;

public class DistWithCoords {
    private static class Impl {
        // Number of nodes
        int n;
        // Coordinates of nodes
        int[] x;
        int[] y;
        // See description of these fields in the starters for friend_suggestion
        ArrayList<Integer>[] adj;
        ArrayList<Integer>[] cost;
        Long[] distance;
        PriorityQueue<Entry> queue;
        boolean[] visited;
        ArrayList<Integer> workset;
		HashMap<Integer, Long> p;
        final Long INFINITY = Long.MAX_VALUE / 4;

        Impl(int n) {
            this.n = n;
            visited = new boolean[n];
            x = new int[n];
            y = new int[n];
            Arrays.fill(visited, false);
            workset = new ArrayList<Integer>();
			p = new HashMap<Integer, Long>();
            distance = new Long[n];
            for (int i = 0; i < n; ++i) {
                distance[i] = INFINITY;
            }
            queue = new PriorityQueue<Entry>();
        }

        // See the description of this method in the starters for friend_suggestion
        void clear() {
            for (int v : workset) {
                distance[v] = INFINITY;
                visited[v] = false;
            }
            workset.clear();
            queue.clear();
			p.clear();
        }

        // See the description of this method in the starters for friend_suggestion
        void visit(int v, Long dist, Long measure) {
            // Implement this method yourself
			if (distance[v] > dist) {
				distance[v] = dist;
				queue.add(new Entry(distance[v] + measure, v));
				workset.add(v);
			}
        }
		
		Long potential(int u, int t) {
			if (!p.containsKey(u)) {
				p.put(u, (long) Math.sqrt((x[u]-x[t])*(x[u]-x[t])+(y[u]-y[t])*(y[u]-y[t])));
			}
			return p.get(u);
		}
		
		int extractMin() {
			Entry e = queue.poll();
			return e.node;
		}
		
		void process(int u, int t, ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
			for (int i = 0; i < adj[u].size(); ++i) {
				int v = adj[u].get(i);
				if (visited[v] != true) {
					Long w = (long) cost[u].get(i);
					visit(v, distance[u] + w, potential(v, t));
				}
			}
		}

        // Returns the distance from s to t in the graph.
        Long query(int s, int t) {
            clear();
            visit(s, 0L, potential(s, t));
            // Implement the rest of the algorithm yourself
			while (!queue.isEmpty()) {
				int v = extractMin();
				if (v == t) {
					if(distance[t] != INFINITY)
						return distance[t];
					else
						return -1L;
				}
				if (visited[v] != true) {
					process(v, t, adj, cost);
					visited[v] = true;
				}
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
        Impl DistWithCoords = new Impl(n);
        DistWithCoords.adj = (ArrayList<Integer>[])new ArrayList[n];
        DistWithCoords.cost = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            DistWithCoords.adj[i] = new ArrayList<Integer>();
            DistWithCoords.cost[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < n; i++) { 
            int x, y;
            x = in.nextInt();
            y = in.nextInt();
            DistWithCoords.x[i] = x;
            DistWithCoords.y[i] = y;
        }

        for (int i = 0; i < m; i++) {
            int x, y, c;
            x = in.nextInt();
            y = in.nextInt();
            c = in.nextInt();
            DistWithCoords.adj[x - 1].add(y - 1);
            DistWithCoords.cost[x - 1].add(c);
        }

        int t = in.nextInt();

        for (int i = 0; i < t; i++) {
            int u, v;
            u = in.nextInt();
            v = in.nextInt();
            System.out.println(DistWithCoords.query(u-1, v-1));
        }
    }
}
