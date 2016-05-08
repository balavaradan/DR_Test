package org.nlp.engine.main;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nlp.engine.pojo.NounWithResults;
import org.nlp.engine.pojo.Paragraph;
import org.nlp.engine.pojo.SearchResult;
import org.nlp.engine.service.FileProcessAndSearchService;
import org.nlp.engine.service.impl.FileProcessAndSearchServiceImpl;
import org.nlp.engine.utils.Utils;


/*
 * 
 * Controller to orchestrate the activities accordingly
 * 
 */
public class NLPController {
	
	FileProcessAndSearchService searchObj = new FileProcessAndSearchServiceImpl();
	
	public Map<String,List<SearchResult>> controllerBlock(File inputFile, List<String> listOfNERWords)
	{
		Paragraph paragraph = searchObj.processActualFile(inputFile);

		Map<String,List<SearchResult>> mapOfSentencesMatchingNER = searchObj.getSentencesWithNouns(listOfNERWords, paragraph, inputFile.getAbsolutePath());

		return mapOfSentencesMatchingNER;
	}

	public List<String> processNERFile(File file) {
		
		return searchObj.processNERFile(file);
	}

	public void returnSearchWordWithAggrResult(List<String> listOfNERWords,
			List<Map<String, List<SearchResult>>> listOfMapOfResults) {
		
		Utils util = new Utils();
		
		Map<String, List<List<SearchResult>>> wordMatchResults = searchObj.returnSearchWordWithAggrResult(listOfNERWords, listOfMapOfResults);
	
		Map<String,NounWithResults> mapOfNounsFound = searchObj.returnSearchResultsCombined(wordMatchResults);
		
		for(Entry<String,NounWithResults> entry : mapOfNounsFound.entrySet())
		{
			String xmlOutput = util.convertToXml(entry.getValue(), NounWithResults.class);
			
			System.out.println("Search Results for "+ entry.getKey());
			
			System.out.println(xmlOutput);
			
			System.out.println("----------------------------------------------------------");

		}
	
	}
	
	
}
