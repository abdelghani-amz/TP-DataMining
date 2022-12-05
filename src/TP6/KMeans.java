package TP6;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class KMeans {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<Element> data = loadDataset("Dataset-Exos.txt");
		System.out.println(Kmeans(3, data));
	}
	
	static ArrayList<Element> loadDataset(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        ArrayList<Element> data = new ArrayList<>();
        while (line != null) {
            if (!line.isEmpty())
                data.add(new Element(line));
            line = reader.readLine();
        }
        reader.close();
        return data;
    }
	
	public static double manhattanDist(Element point1, Element point2) {
    	
    	float[] coords1 = point1.attr ;
    	float[] coords2 = point2.attr ;
    	
    	double somme = 0 ;
    	for (int i = 0 ; i < coords1.length ; i++) {
    		somme = somme + Math.abs(coords1[i] - coords2[i]) ;
    	}
    	return somme;
    }
	
	public static Element[] generateKcentroide(int k, ArrayList<Element> dataset){
		Element[] centroides = new Element[k] ;
		
		for(int i = 0 ; i < k ; i++) {
			int j = ThreadLocalRandom.current().nextInt(0, 149) ;
			centroides[i] = dataset.get(j) ; 
			dataset.get(j).cluster = i ;
			System.out.println(j);
		}
		
		return centroides ;
	}
	
	public static ArrayList<Element>  Kmeans(int k, ArrayList<Element> dataset){
		Element[] initCentroides = generateKcentroide(k, dataset) ;
		boolean change ;
		do {
			change = false ; 
			for(int j = 0 ; j<dataset.size() ; j++) {
				double min = Double.MAX_VALUE ; 
				int cluster = 0 ;
				for(int i = 0 ; i < k ; i++) {
					double distance = manhattanDist(dataset.get(j), initCentroides[i]) ;
					if (distance < min) {
						min = distance ;
						cluster = i ;
					}
					
				}
				if(dataset.get(j).cluster != cluster) {
					dataset.get(j).cluster = cluster ;
					change = true ; 
				}
				
			}
			
			float[][] sums = new float[k][4] ;
			int[] clusterSizes = new int[k] ;
			for(int j = 0 ; j<dataset.size() ; j++) {
				Element current = dataset.get(j) ;
				for (int i = 0 ; i < 4 ; i++) {
					sums[current.cluster][i] = sums[current.cluster][i] + current.attr[i] ;
				}
				clusterSizes[current.cluster]++ ;
			}
			
			for(int i = 0 ; i<k ; i++) {
				for (int j = 0 ; j < 4 ; j++) {
					sums[i][j] = sums[i][j] / clusterSizes[i] ;
				}
				
				Element newCentroid = new Element();
				newCentroid.attr=sums[i] ;
				newCentroid.cluster = i ;
				initCentroides[i] = newCentroid ;
			}
		}while(change) ;
			
		return dataset ;
			
	}

}
