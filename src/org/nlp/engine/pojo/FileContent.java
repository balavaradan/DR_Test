/**
 * 
 */
package org.nlp.engine.pojo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author bvaradarajan
 *
 */
@XmlRootElement
@XmlType(propOrder = {"paragraph"})
public class FileContent implements Serializable{

	/**
	 * 
	 */

	private static final long serialVersionUID = 1712162927544485126L;

	private Paragraph paragraph;
	
	private String filePath;

    @XmlElement
	public Paragraph getParagraph() {
		return paragraph;
	}

	public void setParagraph(Paragraph paragraph) {
		this.paragraph = paragraph;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
