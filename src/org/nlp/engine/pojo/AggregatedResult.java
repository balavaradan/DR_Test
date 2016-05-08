package org.nlp.engine.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "aggregator")

public class AggregatedResult {

	private String filePath;
	
	private List<SearchResult> listOfSearchResults;

	@XmlElementWrapper(name = "search_results")
	@XmlElement(name = "search_result", type=SearchResult.class)
	public List<SearchResult> getListOfSearchResults() {
		return listOfSearchResults;
	}

	public void setListOfSearchResults(List<SearchResult> listOfSearchResults) {
		this.listOfSearchResults = listOfSearchResults;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
