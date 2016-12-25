def dfs(adj, x, visited, stack):
    # Mark the current node as visited
    visited[x] = 1
    
    # Recur for all the vertices adjacent to this vertex
    for i in range(len(adj[x])):
        if not visited[adj[x][i]]:
            visited[adj[x][i]] = 1
            dfs(adj, adj[x][i], visited, stack)

    # All vertices reachable from x are processed by now, push x to Stack
    stack.append(x)

def reverseEdges(adj):
    r_adj = [[] for _ in range(len(adj))]
    for i in range(len(adj)):
        for j in range(len(adj[i])):
            r_adj[adj[i][j]].append(i)
    return r_adj

def number_of_strongly_connected_components(adj):
    result = 0
    stack = []

    # Mark all the vertices as not visited (For first DFS)
    visited = [0] * len(adj)

    # Fill vertices in stack according to their finishing times
    for i in range(len(adj)):
        if not visited[i]:
            dfs(adj, i, visited, stack)

    # get the reversed adj list
    r_adj = reverseEdges(adj)

    # Mark all the vertices as not visited (For second DFS)
    visited = [0] * len(adj)

    # Now process all vertices in order defined by Stack
    while stack:
        x = stack.pop()
        if not visited[x]:
            dfs(r_adj, x, visited, [])
            result+=1
    return result

if __name__ == '__main__':
    n, m = map(int, raw_input().split())
    adj = [[] for _ in range(n)]
    for i in range(m):
        a, b = map(int, raw_input().split())
        # adjacency list        
        adj[a - 1].append(b - 1)
    print(number_of_strongly_connected_components(adj))
