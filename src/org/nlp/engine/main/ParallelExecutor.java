package org.nlp.engine.main;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.nlp.engine.pojo.SearchResult;

public class ParallelExecutor implements Callable<Map<String,List<SearchResult>>>{

	NLPController nlpCtrl;
	
	File inputFile;
	
	List<String> nerWords;
		
	public ParallelExecutor(File inputFile, List<String> nerWords){
        this.inputFile = inputFile;
        this.nerWords = nerWords;
    }

	@Override
	public Map<String,List<SearchResult>> call() throws Exception {
		
		nlpCtrl = new NLPController();
			
		return nlpCtrl.controllerBlock(inputFile,nerWords);
	}

}
