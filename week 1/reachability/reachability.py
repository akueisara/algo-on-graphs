def reach(adj, x, y):
    #write your code here
    visited = [0] * len(adj)
    return explore(adj, x, y, visited)
	
def explore(adj, x, y, visited):
    if (x == y):
        return 1
    visited[x] = 1
    for i in xrange(len(adj[x])):
        if (not visited[adj[x][i]]):
            if(explore(adj, adj[x][i], y, visited)):
                return 1
    return 0

if __name__ == '__main__':
    n, m = map(int, raw_input().split())
    adj = [[] for _ in range(n)]
    for i in range(m):
        a, b = map(int, raw_input().split())
        # adjacency list		
        adj[a - 1].append(b - 1)
        adj[b - 1].append(a - 1)
    x, y = map(int, raw_input().split())
    print(reach(adj, x-1, y-1))