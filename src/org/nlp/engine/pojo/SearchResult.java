package org.nlp.engine.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="search")
@XmlType(propOrder = {"wordSearchedFor","sentenceMatched","filePath"})
public class SearchResult {
	
	private List<String> sentenceMatched;
	
	private String wordSearchedFor;
	
	private String filePath;
	
	private List<Sentence> actualSentenceMatched;


	@XmlElement(name = "search_word", type=String.class)
	public String getWordSearchedFor() {
		return wordSearchedFor;
	}

	public void setWordSearchedFor(String wordSearchedFor) {
		this.wordSearchedFor = wordSearchedFor;
	}

	
	@XmlElementWrapper(name = "sentence_list")
	@XmlElement(name = "sentence", type=String.class)
	public List<String> getSentenceMatched() {
		return sentenceMatched;
	}

	public void setSentenceMatched(List<String> sentenceMatched) {
		this.sentenceMatched = sentenceMatched;
	}

	@XmlTransient
	public List<Sentence> getActualSentenceMatched() {
		return actualSentenceMatched;
	}

	public void setActualSentenceMatched(List<Sentence> actualSentenceMatched) {
		this.actualSentenceMatched = actualSentenceMatched;
	}

	@XmlElement
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
}
