package com.study;

public class TestDfs {

	static int n=7;	//정점의 총 개
	static int [][] map = new int[n][n];	//인접 행렬의 개
	static int [] visit = new int[n];		//방문여부 
	static int start=3;	//시작 정점
	
	
	public static void main(String [] args) {
		
		map[1][5] = map[5][1] = 1;
		map[1][4] = map[4][1] = 1;
		map[3][4] = map[4][3] = 1;
		map[4][2] = map[2][4] = 1;
		map[4][6] = map[6][4] = 1;
		
		dfs(start);
	}
	
	
	static void dfs(int v) {
		//v를 방문했다고 표시 
		visit[v] = 1;
		
		for(int i=0;i<n;i++) {
			if (map[v][i] == 1 && (visit[i]==0) ) {
				System.out.println(v+"에서 "+i+"로 이동 ");
				dfs(i);
			}
		}
	}
}
