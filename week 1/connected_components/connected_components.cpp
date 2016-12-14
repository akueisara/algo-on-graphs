#include <iostream>
#include <vector>

using std::vector;
using std::pair;

void explore(vector<vector<int> > &adj, int x, vector<int> &visited) {
	visited[x] = 1;
	for (int i = 0; i < adj[x].size(); i++) {
		if (!visited[adj[x][i]])
			explore(adj, adj[x][i], visited);
	}
}

int number_of_components(vector<vector<int> > &adj) {
  int res = 0;
  //write your code here
  vector<int> visited(adj.size());
  for (int i = 0; i < adj.size(); i++)
  {
    if (!visited[i])
    {
      explore(adj, i, visited);
      res++;
    }
  }
  return res;
}

int main() {
  size_t n, m;
  std::cin >> n >> m;
  vector<vector<int> > adj(n, vector<int>());
  for (size_t i = 0; i < m; i++) {
    int x, y;
    std::cin >> x >> y;
    adj[x - 1].push_back(y - 1);
    adj[y - 1].push_back(x - 1);
  }
  std::cout << number_of_components(adj);
}
