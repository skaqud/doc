package com.test.temp;

public class TestCombination {

	static int N=10;
	static int K=3;
	
	static int arr [] = {1,3,5,7,9,11,13,15,17,19};
	static String [] resultList = new String[999];
	static int temp[]= new int[K];
	static int cnt;
	static int ans;
	
	
	public static void main(String args[]) {
		cnt =0;
		ans =0;
		
		combi(0,"");
		
		for(int i=0;i<cnt;i++) {
			System.out.println("aa:"+resultList[i]);
		}
		System.out.println("cnt="+cnt);
	}
	
	static void combi(int pickCnt,String num) {
		if(pickCnt ==K) {
			num = temp[0]+""+temp[1]+""+temp[2];
			resultList[cnt++] = num;
			return;
		}
		
		for(int i=0;i<N;i++) {
			if( (arr[i] == -1 ) ||
			    ((pickCnt > 0) && (temp[pickCnt-1]>arr[i])) ) {
			    	continue;
			    }
			
			
			temp[pickCnt] = arr[i];
			arr[i] = -1;
			combi(pickCnt+1,num+""+arr[i]);
			arr[i] = temp[pickCnt];
		}
	}
	
	
}
