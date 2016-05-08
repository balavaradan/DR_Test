package org.nlp.engine.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Sentence implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5498901458281598804L;
	private ArrayList<String> listOfWords;
	
	private String wordSearchedFor;
	
	private String rawSentence;

	@XmlElement(name = "word", type=String.class)
	public ArrayList<String> getListOfWords() {
		return listOfWords;
	}

	public void setListOfWords(ArrayList<String> listOfWords) {
		this.listOfWords = listOfWords;
	}

	public String getRawSentence() {
		return rawSentence;
	}

	public void setRawSentence(String rawSentence) {
		this.rawSentence = rawSentence;
	}

	public String getWordSearchedFor() {
		return wordSearchedFor;
	}

	public void setWordSearchedFor(String wordSearchedFor) {
		this.wordSearchedFor = wordSearchedFor;
	}
	
	
}
