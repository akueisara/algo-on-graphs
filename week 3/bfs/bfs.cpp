#include <iostream>
#include <vector>
#include <queue>
#include <limits>
#include <queue>

using std::vector;
using std::queue;
using std::numeric_limits;
using std::queue;

int distance(vector<vector<int> > &adj, int s, int t) {
  //write your code here
  if (s == t) {
	return 0;
  }
  
  vector<int> dist(adj.size());
  for(int i=0; i<dist.size();i++){
    dist[i] = numeric_limits<int>::max();
  }
  
  dist[s] = 0;
  
  // Create a queue for BFS
  queue<int> queue;
  
  // Enqueue the current node
  queue.push(s);
  
  while(!queue.empty()) {
	// Dequeue a vertex from queue
    int u = queue.front();
	queue.pop();
	   
	// Get all adjacent vertices of the dequeued vertex u
    // If a adjacent has not been discovered, then enqueue it
    for(int i=0; i < adj[u].size(); ++i) {
      int v = adj[u][i];
	  if(dist[v] == numeric_limits<int>::max()) {
		queue.push(v);
		dist[v] = dist[u] + 1;
	  }
	}
  }
  
  if(dist[t] != numeric_limits<int>::max()) {
    return dist[t];
  }
  
  return -1;
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
  int s, t;
  std::cin >> s >> t;
  s--, t--;
  std::cout << distance(adj, s, t);
}
