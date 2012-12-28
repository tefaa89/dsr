package com.dces.instances;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.stemmers.SnowballStemmer;
import weka.core.tokenizers.NGramTokenizer;
import weka.core.tokenizers.Tokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import com.dces.util.enumu.FeaturesModelEnum;
import com.dces.util.enumu.FeaturesParametersEnum;
import com.dces.util.enumu.LanguageEnum;

public class FeatureSpaceBuilder {
	private FeaturesModelEnum featuresModel;
	private Instances rowDataSet;
	private DEInstances filteredDEDataSet;

	private Tokenizer tokenizer;
	private SnowballStemmer stemmer;
	private boolean useStemmer;
	private boolean useStopWordList;
	private boolean trasformTFBool;
	private boolean trasformIDFBool;

	public FeatureSpaceBuilder(Instances rowDataSet) {
		this.rowDataSet = rowDataSet;
		filteredDEDataSet = new DEInstances();
	}

	public void setFeatureSpaceModel(FeaturesModelEnum featuresModel) {
		this.featuresModel = featuresModel;
	}

	public void setStemmerStopListLanguage(LanguageEnum lang) {
		stemmer = new SnowballStemmer();
		stemmer.setStemmer(lang.toString());
	}

	public void setUseStopWordList(boolean bool) {
		useStopWordList = bool;
	}

	public void setTFTransform(boolean bool) {
		trasformTFBool = bool;
	}

	public void setIDFTransform(boolean bool) {
		trasformIDFBool = bool;
	}

	public void setUseStemmer(boolean useStemmer) {
		this.useStemmer = useStemmer;
	}

	public Instances getRowDataSet() {
		return rowDataSet;
	}

	public DEInstances getFilteredDataSet() {
		return filteredDEDataSet;
	}

	public void setFeatureSpaceParamater(FeaturesParametersEnum featureParam) {
		switch (featureParam) {
		case TOGGLESTEMMING_STOPWORDS_OFF:
			useStemmer = false;
			useStopWordList = false;
			break;
		case TOGGLESTEMMING_STOPWORDS_ON:
			useStemmer = true;
			useStopWordList = true;
			break;
		default:
			break;
		}
	}

	public void build() {
		switch (featuresModel) {
		case WORD_UNIGRAM:
			buildNgramTokenizer(1, 1);
			break;
		case WORD_BIGRAM:
			buildNgramTokenizer(2, 2);
			break;
		case WORD_TRIGRAM:
			buildNgramTokenizer(3, 3);
			break;
		case WORD_UNIGRAM_BIGRAM:
			buildNgramTokenizer(1, 2);
			break;
		case WORD_BIGRAM_TRIGRAM:
			buildNgramTokenizer(2, 3);
			break;
		case WORD_UNIGRAM_BIGRAM_TRIGRAM:
			buildNgramTokenizer(1, 3);
			break;
		default:
			buildNgramTokenizer(1, 1);
			break;
		}
		buildStringToWordVector();
	}

	private void buildNgramTokenizer(int minGram, int maxGram) {
		tokenizer = new NGramTokenizer();
		((NGramTokenizer) tokenizer).setNGramMinSize(minGram);
		((NGramTokenizer) tokenizer).setNGramMaxSize(maxGram);
	}

	private void buildStringToWordVector() {
		try {
			StringToWordVector filter = new StringToWordVector();
			filter.setTokenizer(tokenizer);
			filter.setInputFormat(rowDataSet);
			filter.setUseStoplist(useStopWordList);
			filter.setStemmer(useStemmer ? stemmer : null);
			filter.setIDFTransform(trasformIDFBool);
			filter.setTFTransform(trasformTFBool);
			filter.setLowerCaseTokens(true);
			filteredDEDataSet.setInstances(Filter.useFilter(rowDataSet, filter));
			filteredDEDataSet.getEvaluationParameters().setUseStopList(useStopWordList);
			filteredDEDataSet.getEvaluationParameters().setFeatureModelEnum(featuresModel);
			filteredDEDataSet.getEvaluationParameters().setIdfBool(trasformIDFBool);
			filteredDEDataSet.getEvaluationParameters().setTfBool(trasformTFBool);
			filteredDEDataSet.getEvaluationParameters().setUseStemming(useStemmer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		LoadDirectoryInstances ldi = new LoadDirectoryInstances("c:\\test");
		ldi.load();
		FeatureSpaceBuilder fsb = new FeatureSpaceBuilder(ldi.getRowDataInstances());
		fsb.setStemmerStopListLanguage(LanguageEnum.English);
		fsb.setFeatureSpaceModel(FeaturesModelEnum.WORD_UNIGRAM);
		fsb.setUseStopWordList(false);
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
		*/System.out.println("\n\nFiltered data:\n\n" + fsb.getFilteredDataSet().getInstances());

		// train J48 and output model
		J48 classifier = new J48();
		classifier.buildClassifier(fsb.getFilteredDataSet().getInstances());
		System.out.println("\n\nClassifier model:\n\n" + classifier);
	}
}
