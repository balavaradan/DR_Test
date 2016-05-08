package org.nlp.engine.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nlp.engine.pojo.AggregatedResult;
import org.nlp.engine.pojo.FileContent;
import org.nlp.engine.pojo.NounWithResults;
import org.nlp.engine.pojo.Paragraph;
import org.nlp.engine.pojo.SearchResult;
import org.nlp.engine.pojo.Sentence;
import org.nlp.engine.service.FileProcessAndSearchService;
import org.nlp.engine.utils.Utils;

public class FileProcessAndSearchServiceImpl implements FileProcessAndSearchService{

	@Override
	public Paragraph processActualFile(File inputFile) {

		Paragraph paragraph = new Paragraph();

		FileContent fc = new FileContent();
		try
		{
			Utils util = new Utils();
						
			String fileContent = util.readAndGetContentOfFile(inputFile);
			
			Pattern pattern = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
		    Matcher reMatcher = pattern.matcher(fileContent.trim());
		    while (reMatcher.find()) {
		    	Sentence sentence = new Sentence();
		    	
		    	String sentString = reMatcher.group();
		    	
		    	String[] arrayOfWords = sentString.split("\\s");
	    	
		    	sentence.setRawSentence(sentString);

		    	
		    	if(sentence.getListOfWords() == null)
		    	{
		    		sentence.setListOfWords(new ArrayList<String>());
		    	}
		    
		    	sentence.setListOfWords(new ArrayList<String>( Arrays.asList(arrayOfWords)));
		    	
		    	
		    	if(paragraph.getListOfSentences() == null)
		    	{
		    		paragraph.setListOfSentences(new ArrayList<Sentence>());
		    	}
		    	
		    	paragraph.getListOfSentences().add(sentence);
		    	
		    }
		
		    fc.setParagraph(paragraph);
		    
		    fc.setFilePath(inputFile.getAbsolutePath());
		    
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return paragraph;
	}

	@Override
	public List<String> processNERFile(File nerFile) {
		List<String> listOfNERs = new ArrayList<String>();
		BufferedReader in = null;
		try
		{
			in = new BufferedReader(new FileReader(nerFile));
			
			String line = "";
						
			while((line = in.readLine()) != null)
			{
				if(line.length() >= 1)
					listOfNERs.add(line);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(in != null)
				{
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		}
		
		return listOfNERs;
	}

	@Override
	public Map<String,List<SearchResult>> getSentencesWithNouns(List<String> namedEntityList,
			Paragraph paragraph, String filePath) {
	
		
		Map<String,List<SearchResult>> mapOfSearchWithKeyWord = new HashMap<String,List<SearchResult>>();
		
		for(String ner:namedEntityList)
		{
			SearchResult searchResult = new SearchResult();

			List<SearchResult> listOfSearchResults = new ArrayList<SearchResult>();

			for(Sentence indvSent : paragraph.getListOfSentences())
			{
				boolean contains = indvSent.getRawSentence().matches(".*\\b"+ner+"\\b.*");
				
				if(contains)
				{
					
					if(searchResult.getActualSentenceMatched() == null)
					{
						searchResult.setActualSentenceMatched(new ArrayList<Sentence>());
					}
					
					if(searchResult.getSentenceMatched() == null)
					{
						searchResult.setSentenceMatched(new ArrayList<String>());
					}
					
					searchResult.setWordSearchedFor(ner);
					
					searchResult.setFilePath(filePath);
					
					searchResult.getActualSentenceMatched().add(indvSent);
					
					searchResult.getSentenceMatched().add(indvSent.getRawSentence());

					indvSent.setWordSearchedFor(ner);					

				}

			}
			if(searchResult.getActualSentenceMatched() != null)
				listOfSearchResults.add(searchResult);
			mapOfSearchWithKeyWord.put(ner, listOfSearchResults);
		}

		return mapOfSearchWithKeyWord;
	}

	@Override
	public Map<String, List<List<SearchResult>>> returnSearchWordWithAggrResult(List<String> namedEntityList,
			List<Map<String, List<SearchResult>>> listOfMapOfResults) {
		
		Map<String, List<List<SearchResult>>> wordMatchResults = new HashMap<String, List<List<SearchResult>>>();
		
		for(String word : namedEntityList) 
		{
			for(Map<String, List<SearchResult>> resultMap : listOfMapOfResults)
			{
				if(resultMap.containsKey(word))
				{
					if(!wordMatchResults.containsKey(word))
					{
						wordMatchResults.put(word,new ArrayList<List<SearchResult>>());
					}
					wordMatchResults.get(word).add(resultMap.get(word));
				}
			}
		}
		
		return wordMatchResults;
	}

	@Override
	public Map<String, NounWithResults> returnSearchResultsCombined(Map<String, List<List<SearchResult>>> wordMatchResults) {
		
		Map<String,NounWithResults> MapOfNounsFound = new HashMap<String,NounWithResults>();
		
		for(Entry<String,List<List<SearchResult>>> entry : wordMatchResults.entrySet())
		{
			NounWithResults indvObj = new NounWithResults();
			
			
			
			List<AggregatedResult> aggrResultList = new ArrayList<AggregatedResult>();
			
			for(List<SearchResult> value: entry.getValue())
			{
				AggregatedResult indvAggr = new AggregatedResult();
				
				indvAggr.setListOfSearchResults(value);
				
				aggrResultList.add(indvAggr);
			}
			
			indvObj.setAggrResult(aggrResultList);
			
			indvObj.setSearchWord(entry.getKey());
			
			MapOfNounsFound.put(entry.getKey(),indvObj);
		}
		
		return MapOfNounsFound;
	}
}
