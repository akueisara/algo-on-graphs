// Failed case #9/9: unknown signal 11... don't know how to slove it QQ

#include <cstdio>
#include <cassert>
#include <vector>
#include <queue>
#include <limits>
#include <utility>
#include <cmath>
#include <map>

using namespace std;

// See the explanations of these typedefs and constants in the starter for friend_suggestion
typedef vector<vector<int>> Adj;
typedef long long Len;
typedef priority_queue<pair<Len, int>,vector<pair<Len,int>>,greater<pair<Len,int>>> Queue;

const Len INFINITY_ = numeric_limits<Len>::max() / 4;

class AStar {
    // See the descriptions of these fields in the starter for friend_suggestion
    int n_;
    Adj adj_;
    Adj cost_;
    vector<Len> distance_;
    vector<bool> visited_;
	vector<int> workset_;
	vector<Len> p_;
    // Coordinates of the nodes
    vector<pair<Len,Len>> xy_;

public:
    AStar(int n, Adj adj, Adj cost, vector<pair<Len,Len>> xy)
        : n_(n), adj_(adj), cost_(cost), distance_(vector<Len>(n_, INFINITY_)), visited_(n), xy_(xy), p_(vector<Len>(n_, -1))
    {workset_.reserve(n);}

    // See the description of this method in the starter for friend_suggestion
    void clear() {
        for (int i = 0; i < workset_.size(); ++i) {
			int v = workset_[i];
            distance_[v] = INFINITY_;
            visited_[v] = false;
			p_[v] = -1;
        }
		workset_.clear();
    }

    // See the description of this method in the starter for friend_suggestion
    void visit(Queue& q, int v, Len dist, Len measure) {
        // Implement this method yourself
		if (distance_[v] > dist) {
			distance_[v] = dist;
			q.push({distance_[v] + measure, v});
			workset_.push_back(v);
		}
    }
	
	Len Potential(int u, int t) {
		if(p_[u] == -1) {
			pair<Len, Len> p_u = xy_[u];
			pair<Len, Len> p_t = xy_[t];
			p_[u] = sqrt((p_u.first - p_t.first)*(p_u.first - p_t.first) + (p_u.second - p_t.second)*(p_u.second - p_t.second));
		}
		return p_[u];
	}
	
	int extractMin(Queue& q) {
		pair<Len,int> p = q.top();
		q.pop();
		return p.second;
	}
	
	void Process(Queue& q, int u, int t, vector<vector<int>> &adj, const vector<vector<int>> &cost) {
		for (int i = 0; i < adj[u].size(); ++i) {
			int v = adj[u][i];
			if (visited_[v] != true) {
				int w = cost[u][i];
				visit(q, v, distance_[u] + w, Potential(v, t));
			}
		}	
	}

    // Returns the distance from s to t in the graph
    Len query(int s, int t) {
        clear();
        Queue q;
        visit(q, s, 0, Potential(s, t));
        // Implement the rest of the algorithm yourself
		while (!q.empty()) {
			int v = extractMin(q);
			if (v == t) {
				if(distance_[t] != INFINITY_)
					return distance_[t];
				else
					return -1;
			}
			if (visited_[v] != true) {
				Process(q, v, t, adj_, cost_);
				visited_[v] = true;
			}	
		}
        return -1;
    }
};

int main() {
    int n, m;
    scanf("%d%d", &n, &m);
    vector<pair<Len,Len>> xy(n);
    for (int i=0;i<n;++i){
        int a, b;
        scanf("%d%d", &a, &b);
        xy[i] = make_pair(a,b);
    }
    Adj adj(n);
    Adj cost(n);
    for (int i=0; i<m; ++i) {
        int u, v, c;
        scanf("%d%d%d", &u, &v, &c);
        adj[u-1].push_back(v-1);
        cost[u-1].push_back(c);
    }

    AStar astar(n, adj, cost, xy);

    int t;
    scanf("%d", &t);
    for (int i=0; i<t; ++i) {
        int u, v;
        scanf("%d%d", &u, &v);
        printf("%lld\n", astar.query(u-1, v-1));
    }
}
