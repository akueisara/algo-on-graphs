def distance(adj, s, t):
    #write your code here
    dist = [len(adj)] * len(adj)
    dist[s] = 0
    queue = []
    queue.append(s)
    while queue:
        u = queue.pop(0)
        for v in adj[u]:
            if dist[v] == len(adj):
                queue.append(v)
                dist[v] = dist[u] + 1
    if dist[t] != len(adj):
        return dist[t]
    return -1

if __name__ == '__main__':
    n, m = map(int, raw_input().split())
    adj = [[] for _ in range(n)]
    for i in range(m):
        a, b = map(int, raw_input().split())
        # adjacency list		
        adj[a - 1].append(b - 1)
        adj[b - 1].append(a - 1)
    s, t = map(int, raw_input().split())
    s = s - 1
    t = t - 1
    print(distance(adj, s, t))
