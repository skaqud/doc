package com.test.temp;

public class TestPermutation {

	static int N=10;
	static int K=3;
	static int arr [] = {1,3,5,7,9,11,13,15,17,19};
	static String [] resultList = new String[999];
	static int taken[]= new int[N];
	static int cnt;	
	
	public static void main(String args[]) {
		cnt =0;
		
		perm(0,"");
		
		for(int i=0;i<cnt;i++) {
			System.out.println("result["+i+"]:"+resultList[i]);
		}
		System.out.println("cnt="+cnt);
	}
	
	static void perm(int pickCnt,String num) {
		if(pickCnt ==K) {
			resultList[cnt++] = num;
			return;
		}
		
		for(int i=0;i<N;i++) {
			if(taken[i]==1) continue;
			
			taken[i] = 1;
			perm(pickCnt+1,num+""+arr[i]);
			taken[i] = 0;
		}
	}
	
	
}
