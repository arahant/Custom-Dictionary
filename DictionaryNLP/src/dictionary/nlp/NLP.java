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
