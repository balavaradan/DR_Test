package org.nlp.engine.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "final_results")
public class NounWithResults {

	private List<AggregatedResult> aggrResult;
	
	private String searchWord;

	

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public List<AggregatedResult> getAggrResult() {
		return aggrResult;
	}

	public void setAggrResult(List<AggregatedResult> aggrResult) {
		this.aggrResult = aggrResult;
	}
}
