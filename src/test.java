import java.util.*;








// C'EST UNE CLASSE SUR LAQUELLE JE FAIS DES TESTS NE PAS LA CONSIDERER









public class test{


private static int [] b= {1,2,3,4,5,6};
private static int [] e= {2,5,1,4,6,3};
private static int [] f= {6,5,4,3,2,1};
private static int [] c= {1,1,1,1,1,1,1,1,0,0};
private static int[][] d={{4, 2, 5, 1, 3, 0}, {0, 4, 2, 5, 3, 1}, {1, 2, 5, 3, 4, 0}, 
 {2, 0, 4, 1, 3, 5}, {5, 2, 4, 1, 3, 0},{2, 0, 4, 1, 3, 5}, 
 {5, 2, 4, 1, 3, 0}, {1, 3, 5, 2, 0, 4}, {4, 3, 5, 0, 2, 1},
{3, 0, 2, 4, 1, 5}};
public static void PMXdeterministe(int[]T1,int[]T2,int a,int b){

	int aux1=0;
	int aux=a;
	HashMap<Integer,Integer> H1= new HashMap<Integer,Integer>();
	//on les utilise pour O(ln(n))
	HashMap<Integer,Integer> H2= new HashMap<Integer,Integer>();	
	while (aux<b+1){
	 aux1= T2[aux];
	 H1.put(T2[aux],T1[aux]); //Je garde les paires en mémoire
	 H2.put(T1[aux],T2[aux]);
	 T2[aux]=T1[aux];
	 T1[aux]=aux1;
	 aux++;}
	aux=aux%6;
	//A la fin aux vaut b+1, on part de là
	while ( (aux<a) || (aux>b) ){
		
		if (H1.containsKey(T1[aux])){
			int toSwitch=H1.get(T1[aux]);
			while(H1.containsKey(toSwitch)){
				toSwitch=H1.get(toSwitch);
			}
			T1[aux]=toSwitch;}
		if (H2.containsKey(T2[aux])){
			int toSwitch=H2.get(T2[aux]);
			while(H2.containsKey(toSwitch)){
				toSwitch=H2.get(toSwitch);
			}
			T2[aux]=toSwitch;}
		aux= (aux+1)%6;}
			
			
			
		}	    

public static void main (String[] args) {
	
    PMXdeterministe(e, b,4,5);
    System.out.print(Arrays.toString(b));
    System.out.print(Arrays.toString(e));
	
}


 
  
 
}







