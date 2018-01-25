package dictionary.extract;

import java.io.*;
import java.util.*;

import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;

public class NLP {

	private static void start(String word) {

		//text = "An Annotation is a Map and you can get and use the various analyzes individually.";
		PrintWriter out = new PrintWriter(System.out);
		Annotation annotation = new Annotation(word);
		StanfordCoreNLP pipeline = new StanfordCoreNLP();
		pipeline.annotate(annotation);
		System.out.println("Printing StanfordCoreNLP annotations...");
		pipeline.prettyPrint(annotation, out);

		POSTaggerAnnotator postagger = new POSTaggerAnnotator();
		postagger.annotate(annotation);
		System.out.println("Printing POSTaggerAnnotator annotations...");
		System.out.println(postagger.toString());

		getPostype(word);

		/*//An Annotation is a Map and you can get and use the various analyzes individually.
		//For instance, this gets the parse tree of the first sentence in the text.
		//out.println();

		//The toString() method on an Annotation just prints the text of the Annotation
		//But you can see what is in it with other methods like toShorterString()
		//System.out.println("The top level annotation");
		//out.println(annotation.toShorterString()); */

		/*List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		if (sentences != null && sentences.size() > 0) {

			ArrayCoreMap sentence = (ArrayCoreMap) sentences.get(0);
			out.println("The first sentence is:");
			out.println(sentence.toShorterString());
			Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
			out.println();
			out.println("The first sentence tokens are:");
			for (CoreMap token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				ArrayCoreMap aToken = (ArrayCoreMap) token;
				out.println(aToken.toShorterString());
			}
			out.println("The first sentence parse tree is:");
			tree.pennPrint(out);
			out.println("The first sentence basic dependencies are:"); 
			System.out.println(sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class).toString(SemanticGraph.OutputFormat.LIST));
			out.println("The first sentence collapsed, CC-processed dependencies are:");
			SemanticGraph graph = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
			System.out.println(graph.toString(SemanticGraph.OutputFormat.LIST));
		}*/
	}

	public static String getPostype(String word) {
		List<CoreLabel> rawWords = Sentence.toCoreLabelList(word);
		LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
		Tree parse = lp.apply(rawWords);
		String pos = parse.taggedYield().toString().split("/")[1];
		pos=pos.substring(0, pos.length()-1);
		//System.out.println(pos);
		return pos;
	}

	public static ArrayList<TaggedWord> getPostypeSentence(String text) {
		System.out.println(text);
		ArrayList<TaggedWord> posList = new ArrayList<TaggedWord>();
		for(String word:text.split("[,.!?\\s]")) {
			List<CoreLabel> rawWords = Sentence.toCoreLabelList(word);
			LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
			Tree parse = lp.apply(rawWords);
			posList.add(parse.taggedYield().get(0));
			//System.out.println(parse.taggedYield());
		}
		return posList;
	}
}
