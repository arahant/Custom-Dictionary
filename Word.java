package dictionary.word;

import java.util.LinkedList;
import java.util.List;

public class Word {
/*
CC 	Coordinating conjunction
CD 	Cardinal number
DT 	Determiner
EX 	Existential there
FW 	Foreign word
IN 	Preposition or subordinating conjunction
JJ 	Adjective
JJR Adjective, comparative
JJS Adjective, superlative
LS 	List item marker
MD 	Modal
NN 	Noun, singular or mass
NNS Noun, plural
NNP Proper noun, singular
NNPS Proper noun, plural
PDT Predeterminer
POS Possessive ending
PRP Personal pronoun
PRP$ Possessive pronoun
RB 	Adverb
RBR Adverb, comparative
RBS Adverb, superlative
RP 	Particle
SYM Symbol
TO 	to
UH 	Interjection
VB 	Verb, base form
VBD Verb, past tense
VBG Verb, gerund or present participle
VBN Verb, past participle
VBP Verb, non-3rd person singular present
VBZ Verb, 3rd person singular present
WDT Wh-determiner
WP 	Wh-pronoun
WP$ Possessive wh-pronoun
WRB Wh-adverb 
*/
	private String word;
	private String postype;
	private String definition;
	private List<String> synonyms,antonyms;
	
	public Word(String text) {
		word = text;
	}
	
	public void setPostype(String postype) { this.postype = postype; }
	public void setDefinition(String meaning) { this.definition = meaning; }
	
	public void addSynonym(String... args) {
		if(synonyms==null)
			synonyms = new LinkedList<String>();
		for(int i=0;i<args.length;i++)
			synonyms.add(args[i]);
	}
	
	public void addAntonym(String... args) {
		if(antonyms==null)
			antonyms = new LinkedList<String>();
		for(int i=0;i<args.length;i++)
			antonyms.add(args[i]);
	}
	
	public String getWord() { return word; }
	public String getDefinition() { return definition; }
	public String getPostype() { return postype; }
	public List<String> getAntonyms() { return antonyms; }
	public List<String> getSynonyms() { return synonyms; }

}
