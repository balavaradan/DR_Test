package org.nlp.engine.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sentence")
public class Paragraph implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 367178960478441920L;
	private ArrayList<Sentence> listOfSentences;

	@XmlElementWrapper(name = "sentenceList")
	@XmlElement(name = "sentence", type=Sentence.class)
	public ArrayList<Sentence> getListOfSentences() {
		return listOfSentences;
	}

	public void setListOfSentences(ArrayList<Sentence> listOfSentences) {
		this.listOfSentences = listOfSentences;
	}
	
	
}
