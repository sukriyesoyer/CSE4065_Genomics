import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class RandomMotifSearch {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		 // Accept a string 
		String file="input.txt";   
		String[] linearray= new String[10];
		int k=11;
		int i=0;
	try (BufferedReader br = new BufferedReader(new FileReader(file))) { // Reading line by line input.txt
		    String line;
		    while ((line = br.readLine()).length()!=0) {
		       // process the line.
		    	linearray[i]=line;
		    	i++;
		    }

		 
String[] firstmotifs = new String[i];


for(int a=0;a<linearray.length;a++) {  // Select random motifs
	int start=(int) (Math.random()*(linearray[a].length()-k));
	  
	String x= linearray[a];
	firstmotifs[a]=x.substring(start,start+k);
	
}


double [][] a=new double[4][k]; // Create Profil matrix
int bestscore =score(firstmotifs,a);
String[] newfirstmotifs = new String[i];
String [] bestmotif= new String[i];
bestmotif=firstmotifs;
System.arraycopy(firstmotifs, 0, bestmotif, 0, firstmotifs.length);
ArrayList<Integer>scores=new ArrayList<>();
scores.add(bestscore);
newfirstmotifs=motifs(a,linearray);
int sumscore=bestscore;
while(scores.size()<50) { // Iteration for 50 cases
	int score=score(newfirstmotifs,a);
	scores.add(score);
	sumscore+=score;
	
	if(score<bestscore) {
		bestscore=score;
		bestmotif=newfirstmotifs;
		System.arraycopy(newfirstmotifs, 0, bestmotif, 0, newfirstmotifs.length);
	}
	
	newfirstmotifs=motifs(a,linearray);	
}

System.out.println();
for(int t=0;t<newfirstmotifs.length;t++) {
	System.out.println(bestmotif[t]);
}
System.out.println("\n BestScore= "+bestscore+"\n Average Score= "+ (double)sumscore/scores.size()+"\n Max score= "+Collections.max(scores)+" ");
System.out.println(consesusstring(bestmotif));
}
	}
	
	
public static int score(String [] ýnput,double[][]profile) { //Calculate score, Create profil matrix
	int counta=0;
	int countc=0;
	int countt=0;
	int countg=0;
	int score=0;
	for(int i=0;i<ýnput[0].length();i++) {  // Read motifs column by column and calculate score
		for(int t=0;t<ýnput.length;t++) {
		if(ýnput[t].charAt(i)=='A') {
			counta++;
		}
		if(ýnput[t].charAt(i)=='T') {
			countt++;
		}
		if(ýnput[t].charAt(i)=='G') {
			countg++;
		}
		if(ýnput[t].charAt(i)=='C') {
			countc++;
		}
		}
		ArrayList<Integer> intValues = new ArrayList<>();
		intValues.add(counta);
		intValues.add(countt);
		intValues.add(countg);
		intValues.add(countc);
		int max=Collections.max(intValues);
		score+=ýnput.length-max;
		profile[0][i]=(double)counta/ýnput.length;
		profile[1][i]=(double)countc/ýnput.length;
		profile[2][i]=(double)countg/ýnput.length;
		profile[3][i]=(double)countt/ýnput.length;
		
		
		
		 counta=0;
		 countc=0;
	     countt=0;
		  countg=0;
}
	return  score;
	
}

public static String[] motifs(double[][]profile,String [] lines){ // Create new motifs select max probability
	int k=profile[0].length;
	String [] motifs=new String[lines.length];
	for (int i=0;i<lines.length;i++) {
		ArrayList<Double> proba=new ArrayList<>();
		
		for(int c=0;c<lines[i].length()-k;c++) {
			String substring=lines[i].substring(c, k+c);
			double prob=1;
			
			for(int f=0;f<substring.length();f++) {
				if(substring.charAt(f)=='A') {
					prob*=profile[0][f];
				}
				
				if(substring.charAt(f)=='C') {
					prob*=profile[1][f];
				}
				if(substring.charAt(f)=='G') {
					prob*=profile[2][f];
				}
				if(substring.charAt(f)=='T') {
					prob*=profile[3][f];
				}
			
			
			}
			
			proba.add(prob);
		}	
	
		double maxproba=Collections.max(proba);
		int maxýndex=proba.indexOf(maxproba);
		motifs[i]=lines[i].substring(maxýndex, maxýndex+k);
		
	}

	return motifs;
	
	
}

public static String consesusstring(String [] ýnput) { // Create consesus string from best motif 
	int counta=0;
	int countc=0;
	int countt=0;
	int countg=0;
	
	String consesus="";
	for(int i=0;i<ýnput[0].length();i++) {
		for(int t=0;t<ýnput.length;t++) {
		if(ýnput[t].charAt(i)=='A') {
			counta++;
		}
		if(ýnput[t].charAt(i)=='T') {
			countt++;
		}
		if(ýnput[t].charAt(i)=='G') {
			countg++;
		}
		if(ýnput[t].charAt(i)=='C') {
			countc++;
		}
		}
		ArrayList<Integer> intValues = new ArrayList<>();
		intValues.add(counta);
		intValues.add(countt);
		intValues.add(countg);
		intValues.add(countc);
		int max=Collections.max(intValues);
		int index=intValues.indexOf(max);
		if(index==0) {
			consesus+='A';
		}
		if(index==1) {
			consesus+='T';
		}
		
		if(index==2) {
			consesus+='G';
		}
		if(index==3) {
			consesus+='C';
		}
		counta=0;
	    countc=0;
		countt=0;
		countg=0;
		
		
}
	return consesus;
	}}