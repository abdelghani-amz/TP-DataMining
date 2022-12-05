package TP4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Transaction {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		HashMap<String, HashSet<Integer>> transactions = new HashMap<String, HashSet<Integer>>() ;
		BufferedReader reader = new BufferedReader(new FileReader("Dataset2_ TrendingVideosYoutube_1.csv"));
		String[] lineSplit ;
		String line ;
		HashSet<Integer> items ;
		HashSet<Integer> itemsTotal  = new HashSet<Integer>();

		
		while((line = reader.readLine()) != null) {			
        	lineSplit = line.split(",") ;
        
        	if (transactions.containsKey(lineSplit[0])) {
        		transactions.get(lineSplit[0]).add(Integer.parseInt(lineSplit[1])) ;
        	}
        	else {
        		items = new HashSet<Integer>() ;
        		items.add(Integer.parseInt(lineSplit[1])) ;
        		transactions.put(lineSplit[0], items) ;
        	}
        	
        	itemsTotal.add(Integer.parseInt(lineSplit[1])) ;
        	
        }
		
		reader.close();
		System.out.println("Nombre d'items: " + itemsTotal.size());
		System.out.println("Liste d'items: " + itemsTotal + "\n\n");
		System.out.println("Nombre de transaction: " + transactions.size());
		System.out.println("Liste des transactions: " + transactions.keySet());
		
		File file ;
		try {
		      file = new File("Transactions.csv");
		      file.createNewFile();
		} 
		catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		
		FileWriter myWriter = new FileWriter("Transactions.csv");
		for(Map.Entry<String,HashSet<Integer>> t : transactions.entrySet()) {
			myWriter.write(t.getKey() + ";" + t.getValue() + "\n");
		}
		
		myWriter.close();
		HashSet<HashSet<Integer>> itemset = generateKItemSets(3, itemsTotal);
		HashMap<HashSet<Integer>, Integer> candidates = getCandidatesK(new ArrayList<HashSet<Integer>> (transactions.values()), itemset);
		System.out.println(candidates);
		getListK(candidates, 3);
		System.out.println(candidates);
		
	}
	
	
	static HashSet<HashSet<Integer>> generateKItemSets(int k,HashSet<Integer> items ){
		
		HashSet<HashSet<Integer>> kitemSet = new HashSet<HashSet<Integer>>();
		for(Integer i : items) {
			HashSet<Integer> singleton = new HashSet<Integer>();
			singleton.add(i) ;
			kitemSet.add(singleton) ;
		}
			
		HashSet<HashSet<Integer>> singletons = (HashSet<HashSet<Integer>>) kitemSet.clone() ;
		for (int i =0 ; i < k-1 ; i++) {
			HashSet<HashSet<Integer>> newKitemSet = new HashSet<HashSet<Integer>>(); 
			for (HashSet<Integer> set : kitemSet) {
				for (HashSet<Integer> singleton : singletons) {
					HashSet<Integer> newSet = (HashSet<Integer>) set.clone() ;
					newSet.addAll(singleton) ;
					if(newSet.size() != set.size()) newKitemSet.add(newSet) ;
				}
			}
			kitemSet = newKitemSet ;
		}
		return kitemSet;
	}
	
	static HashMap<HashSet<Integer>, Integer> getCandidatesK(ArrayList<HashSet<Integer>> transactions, HashSet<HashSet<Integer>> kitemSet) {
		
		HashMap<HashSet<Integer>, Integer> supports = new HashMap<HashSet<Integer>, Integer>();
		
		for(HashSet<Integer> set : kitemSet) {
			int support = 0 ;
			for(HashSet<Integer> transaction : transactions) {
				if (transaction.containsAll(set)) 
					support = support + 1 ;
			}
			
			if(support > 0) supports.put(set, support) ;
		}
		return supports;
	}
	
	
	static HashMap<HashSet<Integer>, Integer> getListK(HashMap<HashSet<Integer>, Integer> candidatesK, int minSup) {
		
		ArrayList<HashSet<Integer>> itemSets = new ArrayList<HashSet<Integer>>(candidatesK.keySet()) ;
		for (HashSet<Integer> set : itemSets) {
			if(candidatesK.get(set) < minSup)	candidatesK.remove(set) ;
		}
			
		return candidatesK ;
	}
	

}
