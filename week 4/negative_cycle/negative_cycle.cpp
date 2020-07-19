#include <iostream>
#include <vector>
#include <limits>

using std::vector;
using std::numeric_limits;

const int inf = numeric_limits<int>::max();

int negative_cycle(vector<vector<int> > &adj, vector<vector<int> > &cost) {
  //write your code here
  vector<long> dist(adj.size(), inf);
  dist[0] = 0;
  for (int i = 0; i < adj.size(); i++) {
    for(int u = 0; u < adj.size(); u++){
	  for (int k = 0; k < adj[u].size(); k++) {
	    int v = adj[u][k];
	    if(dist[u] != inf && dist[v] > dist[u] + cost[u][k]) {
		    dist[v] = dist[u] + cost[u][k];
			if(i == adj.size() - 1) 
			  return 1;
		}
	  }
	}
  }
  return 0;
}

int main() {
  int n, m;
  std::cin >> n >> m;
  vector<vector<int> > adj(n, vector<int>());
  vector<vector<int> > cost(n, vector<int>());
  for (int i = 0; i < m; i++) {
    int x, y, w;
    std::cin >> x >> y >> w;
    adj[x - 1].push_back(y - 1);
    cost[x - 1].push_back(w);
  }
  std::cout << negative_cycle(adj, cost);
}
