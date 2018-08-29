import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.*;
import static java.lang.Math.pow;
import java.awt.*;
import javax.swing.*;
import java.applet.*;

/*classe la plus importante : elle génère une nouvelle population,  
 * regroupe les résultats des différentes étapes (sélections...) et 
 * renvoie le résultat grâce à factory sous forme d'une matrice composé de 
 * tableaux de ce type : 
 * [nbitérations, meilleur résulat obtenu lors des itérations, l'indice de celui-ci, le dernier résultat, le temps d'éxécution].

La fonction resulataspourgraphe renvoie la même chose mais en string pour
 l'obtention d'un graphe.*/

public class Main {
public static List<Integer> Scores = new ArrayList<Integer>();

public static Random r;
public static String content;
public static int nbchemins=40; //nombre de chemin dans la population P
public static int nbVilles=6;

public static int nbchoisis=20;//chemins choisis pour S

public static int [][] matricechemin={{0,780,320,580,480,660},{780,0,700,460,300,200},
	{320,700,0,380,820,630},{580,460,380,0,750,310},{480,300,820,750,0,500},
	{660,200,630,310,500,0}};
public static List<Integer[]> BestPath = new ArrayList<Integer[]>();

        /*   */


public static int[][] newpopulation (){ //génère nouvelle population
	int [] [] pop= new int [nbchemins][nbVilles];
	int liste[]= new int[nbVilles];
	for (int j=0; j<nbVilles; j++){
		liste[j]= j;
	}
	r=new Random();
	for (int i=0; i<nbchemins; i++){
		int []liste2= new int[nbVilles];
		liste2= annexe.copietableau(shuffle.shuffler(liste),liste2);
		//copietableau permet de ne pas pointer vers la référence liste
		pop[i]= liste2;
		
	  	}
	//System.out.println(Arrays.deepToString(pop));
	return pop;
   }

public static int longchemin(int []liste){ //calcul longueur du chemin
	//calcul la longueur d'un chemin
	int n= liste.length;
	int t=0;
	for (int i=0;i<n-1;i++){
		t=t+matricechemin[liste[i]][liste[i+1]];}
	return (   t+ matricechemin[liste[0]][liste[n-1]]  );
		
		
	}

public static Integer[] IntToInteger(int[] l){
	//transforme un int[]en Integer[]
	int n= l.length;
	Integer[] res= new Integer[n];
	
	for (int i =0; i<n;i++){
		res[i]=(Integer)l[i];
		
	}
	return res;
}


public static int[][] selectiondeterministe(int [][]T){
	//séléctionne les survivants d'une population T en prenant les nbchoisis
	//meilleurs par trifusion(cf annexe)
	
	int [][]aux=new int [nbchemins][2];
	int n= T.length;
	int l;
	
	for (int i=0; i<n; i++){
		l= longchemin(T[i]);
		aux[i][0]=i;
		aux[i][1]=l;
		} //On passe par cet auxiliaire pour ne pas recalculer
		//les longueurs de chemin dans le tri
		
	//System.out.print(Arrays.deepToString(aux));
	
	tris.trifusion(aux);//on trie suivant les longueurs, cf trifusion
	int [][]S= new int [nbchoisis][nbVilles];
	for (int j=0; j<nbchoisis;j++){ //on forme S
		annexe.copietableau(T[aux[j][0]],S[j]);}
	//On prend les nbchoisis premiers du tableau trié
	Scores.add(longchemin(S[0]));
	BestPath.add(IntToInteger(S[0]));//ajoute le meilleur chemin dans la liste
	//System.out.println(longchemin(S[0]));
	return S;
		}
		
		
	
	
		
	
	
public static int [][] selectionroulette(int [][]T){
	//sélection survivants d'une population par la technique de la roulette
	float []aux= new float [nbchemins]; //float car sinon pose problème
	//pour la division
	
	Integer m=Integer.MAX_VALUE;//on conserve le min pour calculer les scores
	
	aux[0]=longchemin(T[0]);// Il faut que je m'en débarasse
	for (int i=0; i<nbchemins-1;i++){
		int v=longchemin(T[i+1]);
		aux[i+1]=aux[i]+v; 
		//System.out.println(""+aux[i+1]);
		//On calcule les sommes partielles
		//une bonne fois pour toute
		m=Math.min(m, v);
	}
	Scores.add(m);
	//System.out.println(m);
	float SF=aux[nbchemins-1];
	//System.out.println(""+SF);
	int n=0;
	int j;
	int [][]S= new int [nbchoisis][nbVilles];
	r=new Random();
	while(n<nbchoisis){
		
		float y=r.nextFloat();
		//System.out.println(""+y);
	    j=0;
		while(((aux[j]/SF)<y)&&(j<nbchemins-1)){
			//System.out.println(""+aux[j]/SF);
			j=j+1;
			//System.out.println(""+j);
			}
		//System.out.println(""+j);
		S[n]=T[j];
		
	    n=n+1;
	    }
	return S;
		}

public static int [][] selection(String selection,int[][]P){ //effectue la sélection que l'on veut
	//appelle la bonne selection
	if (selection=="selectiondeterministe"){
		return selectiondeterministe(P);}
	else{
		return selectionroulette(P);}
}
public static void recombnatur(int[][]S, float Pcross, String croisment){
	//appelle le bon croisement avec une probabilité Pcross
	//r=new Random();
	int i=0;
	while(i<nbchoisis-1){
		
		int conjoint = i+ 1+r.nextInt (nbchoisis-i-1);
		
		float p=r.nextFloat();
		if (p<Pcross){
		   if (croisment=="PMX"){
		     croisement.PMX(S[i],S[conjoint]);}
		   if (croisment=="OX"){
			   croisement.OX(S[i],S[conjoint]);}
		   if (croisment=="LOX"){
			   croisement.LOX(S[i],S[conjoint]);}
		   
		   }
		shuffle.permut2(conjoint,i+1,S);// on les place à côté		
		i=i+2;}//on avance de 2 en 2
	    
}

public static void mutfinal(int[][]T,float Pmut, String mutatio){  //effectue la mutation que l'on veut
	//appelle la bonne mutation avec la probabilité Pmut
	r= new Random();
    float p;
    for (int i=0; i<nbchoisis; i++){
    	p=r.nextFloat();
    	//System.out.println(p);
    	if (p<Pmut){
    		if (mutatio=="transposition"){
    		 mutation.transposition (T[i]);}
    		if (mutatio=="transpositionalea"){
       		 mutation.transpositionalea (T[i]);}
       		if (mutatio=="inversion"){
       		 mutation.inversion (T[i]);}}
    		}
    		
    	}
    
    

		
	

	
	

/*public static void recombroulette(int[][]T){  //inutilisable
	float []aux= new float [nbchemins]; //float car sinon pose problème
	//pour la division
	aux[0]=longchemin(T[0]);
	for (int i=0; i<nbchemins-1;i++){
		aux[i+1]=aux[i]+longchemin(T[i+1]); 
		//On calcule les sommes partielles
		//une bonne fois pour toute
	}
	float SF=aux[nbchemins-1];
	int n=0;
	int j;
	int
	r=new Random();
	Set<Integer> Choisis= new TreeSet<Integer>();
    while(n<nbchoisis){
		
		float y=r.nextFloat();
		//System.out.println(""+y);
	    j=0;
		while(((aux[j]/SF)<y)&&(j<nbchemins-1)){
			//System.out.println(""+aux[j]/SF);
			j=j+1;
			//System.out.println(""+j);
			}
		//System.out.println(""+j);
		if(!Choisis.contains(j)){
			r=(r+1)%2
			if (r=0){
				croisement.OX(T[i], T2, a, b);
			};
			
			Choisis.add(j);}
		
		while(!Choisis.isEmpty());
		
	    n=n+1;
	    }}  */
	

private static float calculp(float tho, int gradsize,float gradregul){ //calcule la probabilité
	//calcule la probabilité de mutation/croisement suivant le paramètre
	//tho, le nombre de termes considérés pour obtenir le gradient moyen
	//et un facteur de régulation
	if(Scores.size()<gradsize+1){
		return (float) 0.5;}
	else{
		int n=Scores.size();
		float grad=0;
		for(int i=n-1;i>(n-gradsize);i--){
			float a= Scores.get(i);
			float b= Scores.get(i-1);
			grad= grad + (float) (1-Math.exp(-(Math.abs((a-b)) +gradregul*(a-b)  )/(b*tho)));		
		}
		return (grad/(float)(gradsize));
		
		}
}


 public static void factory (String mutation, String croisement,  //effectue suivant paramètres
		String Selection,float thocrois, float thomut, int nbiterations,
		float infgrad, int gradsize, float gradregul){
	
	int [][]P= newpopulation();
	float grad=100000;
	int i=0;
	while ((i<nbiterations)&&(grad>infgrad)){
		
		float Pcross=calculp(thocrois, gradsize,gradregul);
		
		float Pmut=calculp(thomut, gradsize,gradregul);
		
		int[][]S= selection(Selection, P);
		
		recombnatur(S, Pcross,croisement);
		
		mutfinal(S,Pmut,mutation);
		
		P=newpopulation();
		for (int j=0;j<nbchoisis;j++){
			P[j]=S[j];}
		i++;
		int n=Scores.size();
		if(n>=(gradsize+1)){
			
		for(int k=n-1;k>(n-gradsize);k--){
			float a= Scores.get(k);
			float b= Scores.get(k-1);
			grad= grad + Math.abs((float) ((a-b)/b));		
		}
		grad= (grad/(float)(gradsize));
		}
		
	
	}		
		}
		
public static int indexBest(List<Integer> l){ //renvoie l'indice du meilleur score des chemins obtenus pendant les itérations
	int n=l.size();
	int min=l.get(0);
	int indexmin=0;
	for (int k=0; k<n;k++ ){
		if (l.get(k)<min){min=l.get(k);
		indexmin=k;
		}
	}
	return indexmin;
}

 
public static float[][] resultats(int n)throws IOException{ //renvoie le tableau de résultats, cf CR
	//on va calculer les résultats pour 2, 4, 8..... 2^n itérations
	//le résultat renvoie le nbitérations,le meilleur score et 
	//l'apparition du meilleur score,le résultat final et le temps d'éxécution
	//matricechemin =matricedistance.genermat();// à cocher ou décocher si l'on veut tester 
	//sur les 250 villes françaises ou garder la valeur instancié dans la classe (petite matrice du sujet)
	float[][]result= new float[n][5];
	for (int i=0;i<n;i++){
		System.out.println(""+i);
		long debut = System.currentTimeMillis();
		double j=(double)i;
		factory("transposition", "LOX", "selectiondeterministe",1f,1f,(int)Math.pow(2, j),
				0, 2, 0);//on peut changer à sa guise
		int indexbest= indexBest(Scores);
		
		result[i][0]=(float)Math.pow(2,j);
		result[i][1]=Scores.get(indexbest);
		result[i][2]=indexbest;
		result[i][3]=Scores.get(Scores.size()-1);
		result[i][4]=(System.currentTimeMillis()-debut);
		Scores.clear();
		BestPath.clear();
		
	}
	return result;
	//System.out.print(Arrays.deepToString(result));
}


public static String resultatspourgraphe(int n)throws IOException{ //renvoie résultats pour le graphe
	//on va calculer les résultats pour 2, 4, 8..... 2^n itérations
	//le résultat renvoie le nbitérations,le meilleur score et 
	//l'apparition du meilleur score,le résultat final et le temps d'éxécution
	//matricechemin =matricedistance.genermat();
	String R=null;
	for (int i=1;i<(int)(n);i++){
		long debut = System.currentTimeMillis();
		double j=(double)i;
		//(int)Math.pow(2,i)
		factory("transposition", "PMX", "selectiondeterministe",0.01f,0.01f,i,
				0, 4, 0.5f);
		int indexbest= indexBest(Scores);
		
		R=R+ i;
		R=R+"  ";
		R=R+ Scores.get(indexbest);
		R=R+"  ";
		R=R+ indexbest;
		R=R+"  ";
		R=R+Scores.get(Scores.size()-1);
		R=R+"  ";
		R=R+(System.currentTimeMillis()-debut);
		R=R+"\n";
		Scores.clear();
		BestPath.clear();
		System.out.println(i);
		
	}
	
	/*for (int i=n/4;i<(int)(n/2);i++){
		long debut = System.currentTimeMillis();
		double j=(double)i;
		factory("transposition", "LOX", "selectiondeterministe",1f,1f,(int)50*i,
				0, 10, 0);
		int indexbest= indexBest(Scores);
		
		R=R+ 50*i;
		R=R+"  ";
		R=R+ Scores.get(indexbest);
		R=R+"  ";
		R=R+ indexbest;
		R=R+"  ";
		R=R+Scores.get(Scores.size()-1);
		R=R+"  ";
		R=R+(System.currentTimeMillis()-debut);
		R=R+"\n";
		Scores.clear();
		BestPath.clear();
		System.out.println(i);
	}
	
	for (int i=(int)(n/2);i<(int)(3*n/4);i++){
		long debut = System.currentTimeMillis();
		double j=(double)i;
		factory("transposition", "LOX", "selectiondeterministe",1f,1f,(int)50*i,
				0, 10, 0);
		int indexbest= indexBest(Scores);
		
		R=R+ 50*i;
		R=R+"  ";
		R=R+ Scores.get(indexbest);
		R=R+"  ";
		R=R+ indexbest;
		R=R+"  ";
		R=R+Scores.get(Scores.size()-1);
		R=R+"  ";
		R=R+(System.currentTimeMillis()-debut);
		R=R+"\n";
		Scores.clear();
		BestPath.clear();
		System.out.println(i);
	}
	
	for (int i=(int)(3*n/4);i<n;i++){
		long debut = System.currentTimeMillis();
		double j=(double)i;
		factory("transposition", "LOX", "selectiondeterministe",1f,1f,(int)50*i,
				0, 10, 0);
		int indexbest= indexBest(Scores);
		
		R=R+ 50*i;
		R=R+"  ";
		R=R+ Scores.get(indexbest);
		R=R+"  ";
		R=R+ indexbest;
		R=R+"  ";
		R=R+Scores.get(Scores.size()-1);
		R=R+"  ";
		R=R+(System.currentTimeMillis()-debut);
		R=R+"\n";
		Scores.clear();
		BestPath.clear();
		System.out.println(i);
	}*/
	return R;}
	//System.out.print(Arrays.deepToString(result));



public static void main (String[] args) throws IOException { //je me servais de celui-là comme d'une fonction test, ne pas la considérer
	
	 //for (int i =240;i<250;i++){
	 //System.out.println(Arrays.toString(villes[i]));}
	 //matricechemin =matricedistance.genermat();
	 //int cheminm=0;
	 //for (int i=0;i<250;i++){
		 //cheminm+=matricechemin[i][(i+1)%250];
	// }
	 //System.out.println(""+cheminm);
	 float[][]A=resultats(8);
	 System.out.print(Arrays.deepToString(A));
	 List<Integer> oldList = Scores;
			  
		List<String> newList = new ArrayList<String>(oldList.size());
			 for (Integer myInt : oldList) { 
			   newList.add(String.valueOf(myInt)); 
			 }
	
	 //System.out.println(newList);
}
 


}
