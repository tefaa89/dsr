package com.dces.test;

import com.dces.evaluation.LoadDirectoryInstances;
import com.dces.evaluation.featureExtraction.FeatureSpaceBuilder;
import com.dces.util.enumu.FeaturesModelEnum;
import com.dces.util.enumu.LanguageEnum;

public class FeatureSpaceBuilderTest {
	public static void main(String[] args) throws Exception {
		LoadDirectoryInstances ldi = new LoadDirectoryInstances("c:\\test2",false);
		ldi.load();
		FeatureSpaceBuilder fsb = new FeatureSpaceBuilder(ldi.getRowDataInstances());
		fsb.setStemmerStopListLanguage(LanguageEnum.English);
		fsb.setFeatureSpaceModel(FeaturesModelEnum.WORD_UNIGRAM_BIGRAM);
		fsb.setUseStemmer(true);
		fsb.setUseStopWordList(true);
		fsb.setTFTransform(true);
		fsb.setIDFTransform(true);
		fsb.build();
		// 0.693147
		// 0.174198
		/*TextDirectoryLoader loader = new TextDirectoryLoader();
		System.out.println("Starting ....");
		loader.setDirectory(new File("C:\\test"));
		Instances dataRaw = loader.getDataSet();
		// System.out.println("\n\nImported data:\n\n" + dataRaw);

		// apply the StringToWordVector
		// (see the source code of setOptions(String[]) method of the filter
		// if you want to know which command-line option corresponds to which
		// bean property)
		StringToWordVector filter = new StringToWordVector();
		SnowballStemmer stemmer = new SnowballStemmer();
		stemmer.setStemmer("english");

		filter.setStemmer(stemmer);
		filter.setInputFormat(dataRaw);

		NGramTokenizer tokenizer = new NGramTokenizer();
		tokenizer.setNGramMaxSize(3);
		tokenizer.setNGramMinSize(1);
		filter.setTokenizer(tokenizer);
		filter.setTFTransform(true);
		// filter.setOptions(new String[]{"-S"});
		Instances dataFiltered = Filter.useFilter(dataRaw, filter);
		*/System.out.println("\n\nFiltered data:\n\n" + fsb.getFilteredDataSet().getInstances().toSummaryString());

		/*// train J48 and output model
		J48 classifier = new J48();
		classifier.buildClassifier(fsb.getFilteredDataSet().getInstances());
		System.out.println("\n\nClassifier model:\n\n" + classifier);*/
	}
}
