#include <iostream>
#include <vector>
#include <queue>

using std::vector;
using std::queue;

int bipartite(vector<vector<int> > &adj) {
  //write your code here
  // Create a color array to store colors assigned to all veritces.
  // Vertex number is used as index in this array. The value '-1'
  // of  colorArr[i] is used to indicate that no color is assigned
  // to vertex 'i'.  The value 1 is used to indicate first color
  // is assigned and value 0 indicates second color is assigned.
  int colorArr[adj.size()];
  for (int i = 0; i < adj.size(); ++i)
    colorArr[i] = -1;
  
  // Assign first color to the source vertex
  colorArr[0] = 1;
  
  // Create a queue (FIFO) of vertex numbers and enqueue
  // source vertex for BFS traversal
  queue<int> queue;
  queue.push(0);
  
  // Run while there are vertices in queue (Similar to BFS)
  while(!queue.empty()) {
	// Dequeue a vertex from queue
    int u = queue.front();
	queue.pop();
	   
    // Find all non-colored adjacent vertices
    for(int i=0; i < adj[u].size(); ++i) {
      int v = adj[u][i];
	  // Destination v is not colored
      if (colorArr[v] == -1) {
        // Assign alternate color to this adjacent v of u
        colorArr[v] = 1- colorArr[u];
		queue.push(v);
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

int main() {
  int n, m;
  std::cin >> n >> m;
  vector<vector<int> > adj(n, vector<int>());
  for (int i = 0; i < m; i++) {
    int x, y;
    std::cin >> x >> y;
    adj[x - 1].push_back(y - 1);
    adj[y - 1].push_back(x - 1);
  }
  std::cout << bipartite(adj);
}
