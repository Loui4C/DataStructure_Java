package com.louischow.recite;

class UnionFindSet {
    int[] parent;
    int[] size;

    public UnionFindSet(int n) {
        this.parent = new int[n];
        this.size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int x) {
        if (this.parent[x] != x) {
            this.parent[x] = this.find(this.parent[x]); // 路径压缩
        }
        return this.parent[x];
    }

    public void union(int x, int y) {
        int rootX = this.find(x);
        int rootY = this.find(y);

        if (rootX != rootY) {
            // 按秩求合
            if (this.size[rootX] < this.size[rootY]) {
                this.parent[rootX] = rootY;
                this.size[rootY] += this.size[rootX];
            } else {
                this.parent[rootY] = rootX;
                this.size[rootX] += this.size[rootY];
            }
        }
    }
}
