package org.nlp.engine.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.nlp.engine.pojo.NounWithResults;
import org.nlp.engine.pojo.Paragraph;
import org.nlp.engine.pojo.SearchResult;

public interface FileProcessAndSearchService {

	public Paragraph processActualFile(File inputFile);
	
	public List<String> processNERFile(File nerFile);
	
	public Map<String, List<SearchResult>> getSentencesWithNouns(List<String> namedEntityList, Paragraph paragraph, String filePath);
	
	public Map<String, List<List<SearchResult>>> returnSearchWordWithAggrResult(List<String> namedEntityList, List<Map<String, List<SearchResult>>> listOfMapOfResults);
	
	public Map<String,NounWithResults> returnSearchResultsCombined(Map<String, List<List<SearchResult>>> wordMatchResults);
}
