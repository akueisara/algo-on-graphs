def acyclic(adj):
    # Mark all the vertices as not visited and not part of recursion stack
    visited = [0 for _ in range(len(adj))]
    rec_stack = [0 for _ in range(len(adj))]
    # Call the recursive helper function to detect cycle in different DFS trees
    for i in range(len(adj)):
        if not visited[i]:
            if dfs(adj, i, visited, rec_stack):
                return 1
    return 0

def dfs(adj, x, visited, rec_stack):
    # Mark the current node as visited and part of recursion stack
    visited[x] = 1
    rec_stack[x] = 1
    # Recur for all the vertices adjacent to this vertex
    for i in range(len(adj[x])):
        if not visited[adj[x][i]] and dfs(adj, adj[x][i], visited, rec_stack):
            return 1
        elif rec_stack[adj[x][i]]:
            return 1
    rec_stack[x] = 0 # remove the vertex from recursion stack
    return 0

if __name__ == '__main__':
    n, m = map(int, raw_input().split())
    adj = [[] for _ in range(n)]
    for i in range(m):
        a, b = map(int, raw_input().split())
        # adjacency list		
        adj[a - 1].append(b - 1)
    print(acyclic(adj))
