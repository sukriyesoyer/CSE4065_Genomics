import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class GibsSampler {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		String file="input.txt";
		String[] linearray= new String[10];
		int k=11;
		int i=0;
	try (BufferedReader br = new BufferedReader(new FileReader(file))) {  // Reading line by line input.txt
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
		    
		    int j=1;
		    int sumscore=0;
		    String[] bestmotif =new String[firstmotifs.length];
		   System.arraycopy(firstmotifs, 0, bestmotif, 0, firstmotifs.length);
		    int bestscore=Integer.MAX_VALUE;
		    ArrayList<Integer>scores=new ArrayList<>();
		while(j<51) {  // Iteration for 50 cases
			 int score=score(firstmotifs);
			 sumscore+=score;
			 scores.add(score);
			 if(score<bestscore) {
				 bestscore=score;
				 System.arraycopy(firstmotifs, 0, bestmotif, 0, firstmotifs.length);
			 }
		    int assumei=(int) (Math.random()*(firstmotifs.length));
		 
		    firstmotifs[assumei]=null;
            String base=linearray[assumei];
          
            double[][] profil=new double[4][linearray.length-1]; // Create Profil matrix
            profil=profile(firstmotifs,k);
          String newMotif=motif(profil,base);
        
          firstmotifs[assumei]=newMotif;
       
	j++;}
		
		
		for(int t=0;t<bestmotif.length;t++) {
			System.out.println(bestmotif[t]);
		}
		System.out.println("\n BestScore= "+bestscore+"\n Average Score= "+ (double)sumscore/scores.size()+"\n Max score= "+Collections.max(scores)+" ");
        System.out.println(consesusstring(bestmotif));
	}
}
	
	public static int score(String [] ýnput) { //Calculate score
		int counta=0;
		int countc=0;
		int countt=0;
		int countg=0;
		int score=0;
		
		for(int i=0;i<ýnput[0].length();i++) { // Read motifs column by column and calculate score
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
		
			 counta=0;
			 countc=0;
		     countt=0;
			 countg=0;
	}
		
	
		
		
		
		return  score;
		
	}
	
	
	
	
	public static double[][] profile(String [] ýnput,int k) { // Create profil matrix
		int counta=0;
		int countc=0;
		int countt=0;
		int countg=0;
		double[][]profile =new double[4][k];
		boolean existzero=false;
		for(int i=0;i<k;i++) {
			for(int t=0;t<ýnput.length;t++) { 
				if(ýnput[t]!=null) {
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
			}}
			}
	
			profile[0][i]=(double)counta;
			profile[1][i]=(double)countc;
			profile[2][i]=(double)countg;
			profile[3][i]=(double)countt;
			if(counta==0||countc==0|| countg==0||countt==0) {
				existzero=true;
			}
			
			 counta=0;
			 countc=0;
		     countt=0;
			  countg=0;
	}
		
		if(existzero) {
			for(int i=0;i<profile.length;i++) {
				for(int t=0;t<profile[i].length;t++) {
				      profile[i][t]+=1;
				      profile[i][t]=profile[i][t]/(ýnput.length+3);
				}}
				}
		
		
		
		return profile;
		
	}
	
	
	
	

public static String motif(double[][]profile,String  line){ // Create new motifs select max probability
	int k=profile[0].length;
	String motif=new String();
	ArrayList<Double> proba=new ArrayList<>();
	for (int i=0;i<line.length()-k;i++) {
		     
	          String substring=line.substring(i, k+i);
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
		motif=line.substring(maxýndex, maxýndex+k);
		
	

	return motif;

	
	
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
	}

	
}
