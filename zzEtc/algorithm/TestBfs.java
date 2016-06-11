package com.study;

import java.util.ArrayDeque;

//BFS - 큐를 이용함.
public class TestBfs {

	static int n=6;	//정점의 총 개
	static int [][]map = new int[n+1][n+1];
	static ArrayDeque<Integer> queue = new ArrayDeque<Integer>(); 
	static int [] visit = new int[n+1];		//발견한 점의
	static int start=3;	//시작 정점
	
	public static void main(String [] args) {
		
		map[1][5] = map[5][1] = 1;
		map[1][4] = map[4][1] = 1;
		map[3][4] = map[4][3] = 1;
		map[4][2] = map[2][4] = 1;
		map[4][6] = map[6][4] = 1;
		
		bfs(start);
	}
	
	
	static void bfs(int v) {
		//v를 방문했다고 표시
		visit[v] = 1;
		System.out.println(v+"에서 시작");
		queue.add(v);
		
		while(queue.size() > 0) {
			v=queue.pollFirst();			
			for (int i=1;i<=n;i++) {
				if(map[v][i]==1 && visit[i]!=1) {
					visit[i]=1;
					System.out.println(v+"에서 "+i+"로 이동");
					queue.add(i);
				}
			}
		}
	}
}
