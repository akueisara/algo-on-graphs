def number_of_components(adj):
    result = 0
    #write your code here
    visited = [0] * len(adj)
    for i in xrange(len(adj)):
        if not visited[i]:
            explore(adj, i, visited)
            result += 1
    return result

def explore(adj, x, visited):
    visited[x] = 1
    for i in xrange(len(adj[x])):
        if not visited[adj[x][i]]:
            explore(adj, adj[x][i], visited)

if __name__ == '__main__':
    n, m = map(int, raw_input().split())
    adj = [[] for _ in range(n)]
    for i in range(m):
        a, b = map(int, raw_input().split())
        # adjacency list		
        adj[a - 1].append(b - 1)
        adj[b - 1].append(a - 1)
    print(number_of_components(adj))
