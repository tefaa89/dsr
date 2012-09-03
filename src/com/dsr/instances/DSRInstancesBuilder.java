package com.dsr.instances;

import java.util.Random;
import java.util.Vector;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import com.dsr.database.DBConnection;
import com.dsr.database.DBQuery;
import com.dsr.util.DSRWekaUtil;
import com.dsr.util.enumu.FeatureValuesEnum;
import com.dsr.util.enumu.NGramEnum;

public class DSRInstancesBuilder extends DocumentInstancesBuilder {
	public DSRInstancesBuilder(Vector<DocumentInfo> docNGramVec, NGramEnum ngramType) {
		super(docNGramVec, ngramType);
	}

	public DSRInstancesBuilder(NGramEnum ngramType) {
		super(null,ngramType);
		buildDocInfos();
	}

	private void buildDocInfos() {
		Vector<DocumentInfo> docInfoVec = new Vector<DocumentInfo>();
		DocumentInfo[] docInfoArr = DBQuery.getAllDocuments(DBConnection.connect());
		for(DocumentInfo docInfo:docInfoArr)
			docInfoVec.add(docInfo);
		setDocVec(docInfoVec);
	}
	
	public static void main(String[] args) {
		DSRInstancesBuilder dsr = new DSRInstancesBuilder(NGramEnum.WORD_UNIGRAM);
		dsr.buildInstances();
		
		IBk nb = new IBk();
		
		Instances trainData = DSRWekaUtil.convertDocInstancesToWekaInstances(dsr.getDocumentInstances(), FeatureValuesEnum.TFIDF_VALUES);
		try {
			Random rand = new Random(1);
			Instances randData = new Instances(trainData);
			randData.randomize(rand);
			
			Evaluation eval = new Evaluation(randData);
			eval.crossValidateModel(nb, randData, 10, new Random(1));
			System.out.println("Correct: " + eval.correct() + "\nIncorrect: " + eval.incorrect());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
}
