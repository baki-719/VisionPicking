//import java.util.Scanner;
//
//public class Simple {
//	public static void main(String[] args) {
//		int[] distance = {6,4,2,6,4,2};
//		int[] contain = {0,0,0,0,0,0};
//		int sum=0;
//		String result="";
//		Scanner sc = new Scanner(System.in);
//		String section = sc.nextLine();
//		char[] cut = section.toCharArray();
//
//		for (int i = 0; i < cut.length; i++) {
//			int num = section.charAt(i);
//			contain[num-65]=1;
//		}
//		for (int i = 0; i < contain.length; i++) {
//			distance[i]=distance[i]*contain[i];
//			sum+=distance[i];
//		}
//		while(sum!=0) {
//			for (int i = 0; i < contain.length; i++) {
//				if(distance[i]==2) {
//					sum-=2;
//					distance[i]=0;
//					result = result.concat(Character.toString((char)(i+65)));
////					System.out.print(Character.toString((char)(i+65)));
//				}
//				else if(distance[i]>0) {
//					distance[i]-=2;
//					sum-=2;
//				}
//			}
//		}
//		System.out.println(result);
//	}
//}
