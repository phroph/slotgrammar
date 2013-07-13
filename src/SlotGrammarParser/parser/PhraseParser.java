package SlotGrammarParser.parser;


import SlotGrammarParser.ConcreteSenseFrame;
import SlotGrammarParser.Dictionary.WordHasNoSenseFramesException;
import SlotGrammarParser.Dictionary.WordNotFoundException;
import SlotGrammarParser.abstractions.Dictionary;
import SlotGrammarParser.abstractions.Phrase;
import SlotGrammarParser.abstractions.SentenceToken;
import SlotGrammarParser.abstractions.SentenceTokenizer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Anthony
 * Date: 8/15/12
 * Time: 12:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhraseParser {

    private Dictionary dictionary;

    // Original phrase without punctuation
  //  private String strippedText;


  //  String originalInput;
  //  SentenceTokenizer tokenizer;

   // List<Phrase> phrases;

   // PhraseParserChart chart;


    private SentenceTokenizer tokenizer;

    public PhraseParser(Dictionary dictionary) {


        this.tokenizer = new ConcreteSentenceTokenizer(dictionary);
        //this.strippedText = tokenizer.getStrippedText();
      //  this.originalInput = input;
        this.dictionary = dictionary;
       // chart = new PhraseParserChart();

      //  phrases = parse(input);

        // We should have more phrases or equal number of phrases as spaces in the input
        //System.out.println("\n" + Arrays.toString(phrases.toArray())  + " should havee at least " + input.split(" ").length + " entries");
     //   assert(phrases.size()>=input.split(" ").length);

        // Create the chart
      //  chart = parsePhrase();
    }

    /**
     * Return a list,
     * @param input
     * @return
     */
    public List<Phrase> parseText(String input) throws WordHasNoSenseFramesException, WordNotFoundException {

        System.out.println("Phrase Parse Text: " + input);
        List<SentenceToken> sentenceTokens = tokenizer.tokenize(input).getTokenList();
        System.out.println("Phrase Parse Sentence Tokens: " +sentenceTokens);
        PhraseParserChart chart = new PhraseParserChart();
        Phrase newPhrase;

        // For each token, create a phrase with each sense frame.
      /*  for(SentenceToken currentToken : sentenceTokens) {
            System.out.println("\tPhrase Parse Current Token: " + currentToken);
            for(ConcreteSenseFrame frame : currentToken.getLexicalEntry().getSenseFrames()) {
                System.out.println("\t\tPhrase Parse Current Frame: " + frame);

                newPhrase = PhraseFactory.createPhrases()
                System.out.println("\t\tPhrase Parse New Phrase: " + newPhrase);

                // Create a new phrase
                chart.add(new ConcretePhrase(currentToken, frame));
            }
        }    */

        return chart.getPhrases();



    }

  /*  public List<Phrase> getCompleteParsedPhrases() {


        return chart.getCompletePhrases();

    }     */

    /**
     * Builds the phrase chart with the given phrases
     * @return
     */
   /* private PhraseParserChart parsePhrase() {


        // Create the chart by iteratively adding each phrase to the chart.
        chart = new PhraseParserChart();
        for(Phrase newPhrase : phrases) {

            System.out.println("Adding: " + newPhrase + " to chart.");
            chart.add(newPhrase);
            System.out.println("Resulting Chart: \n" + chart);
        }


        return chart;




    }                */

   /* public List<Phrase> getPhrases() {
        return phrases;
    }    */

    /*public List<SentenceToken> getSentenceTokens() {
        return tokenizer.getTokens();
    }

    private List<Phrase> parse(String input) {


      //  SentenceTokenizer tokenizer = new SentenceTokenizer(dictionary, input);
        System.out.println("Token stripped text: "+ tokenizer.getStrippedText());


        List<SentenceToken> tokens = tokenizer.getTokens();

        List<Phrase> phrases = new ArrayList<Phrase>();

        Phrase newPhrase;
        LexicalEntry lexicalEntry;
        int leftBoundary, rightBoundary;

        String originalText;

        // For each token
        for(SentenceToken currentToken : tokens) {


            // Get left and right bound, and lexical entry
            lexicalEntry = currentToken.getLexicalEntry();
            leftBoundary = currentToken.getLeftBound();
            rightBoundary = currentToken.getRightBound();
            originalText = currentToken.getOriginalText();

            for(ConcreteSenseFrame frame : lexicalEntry.getSenseFrames()) {

                // Create the new phrase
                phrases.add( new Phrase(currentToken , frame));


            }


        }

        return phrases;

    }



  /*  private List<Phrase> createStartPhrases(String segment) {

        List<Phrase> startPhrases = new ArrayList<Phrase>();
        TokenizedSentence sentence = new TokenizedSentence(dictionary, segment);

        List<SentenceToken> entires = sentence.getLexicalEntries();
        for(int i = 0;i<entires.size();i++) {

        }


        return null;


    }    */


    // Gets text without punctuation
    /*public String getStrippedText() {

        return strippedText;
    }  */
}
