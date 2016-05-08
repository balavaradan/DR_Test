package org.nlp.engine.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.nlp.engine.pojo.SearchResult;

/*
 * 
 * 
 * This is the main entry point for the program
 * 
 * 
 * 
 * 
 */
public class MainEntry {

	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		// TODO Auto-generated method stub
		
		NLPController nlpCtrlr = new NLPController();
		
		File folder = new File("/Users/bvaradarajan/Documents/DR/nlp_data");

		List<String> listOfNERWords = nlpCtrlr.processNERFile(new File("/Users/bvaradarajan/Documents/DR/NER.txt"));
		
		List<File> listOfInputFiles = new ArrayList<File>();
		
		for (File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	if(fileEntry.getAbsolutePath().endsWith("txt"))
	        		listOfInputFiles.add(fileEntry);
	        }
	    }
		
		ExecutorService executor = Executors.newFixedThreadPool(listOfInputFiles.size());

		List<Map<String,List<SearchResult>>> listOfMapOfResults = new ArrayList<Map<String,List<SearchResult>>>();
				
		for (int i = 0; i < listOfInputFiles.size(); i++) {
			
			Callable<Map<String,List<SearchResult>>> callable = new ParallelExecutor(listOfInputFiles.get(i),listOfNERWords);
		    Future<Map<String,List<SearchResult>>> future = executor.submit(callable);

		    listOfMapOfResults.add(future.get());
          }
		
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
     
        nlpCtrlr.returnSearchWordWithAggrResult(listOfNERWords, listOfMapOfResults);
        
        
	}

}
