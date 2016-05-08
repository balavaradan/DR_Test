package org.nlp.engine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.nlp.engine.pojo.AggregatedResult;
import org.xml.sax.SAXException;

public class Utils {

	public String readAndGetContentOfFile(File inputFile) throws IOException
	{
		FileInputStream fis = new FileInputStream(inputFile);
		byte[] data = new byte[(int) inputFile.length()];
		fis.read(data);
		fis.close();

		return new String(data, "UTF-8");
	}
	
    public String convertToXml(Object fc, Class... type) {
        String result = null;
        StringWriter sw = new StringWriter();

        
        try {
            JAXBContext outputContext = JAXBContext.newInstance(type);
            Marshaller outputMarshaller = outputContext.createMarshaller();
            outputMarshaller.marshal(fc, sw);
            result = sw.toString();
            
            if(result.length() > 0)
            {
  
            	result = prettyPrint(result);
            }
            
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

        return result;
    }
    
    public String prettyPrint(String xmlString) throws Exception {
    	Source xmlInput = new StreamSource(new StringReader(xmlString));
        StringWriter stringWriter = new StringWriter();
        StreamResult xmlOutput = new StreamResult(stringWriter);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(xmlInput, xmlOutput);
        return xmlOutput.getWriter().toString();
	}

	public void writeXMLtoFile(AggregatedResult aggregatedResult) throws IOException {
		String xmlOutput = convertToXml(aggregatedResult, AggregatedResult.class);
		
		if(aggregatedResult.getFilePath() != null)
		{
			File file = new File(aggregatedResult.getFilePath().replace(".txt", "_searchresults.xml"));
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(xmlOutput);
			fileWriter.flush();
			fileWriter.close();
		}
		
		
	}
}
