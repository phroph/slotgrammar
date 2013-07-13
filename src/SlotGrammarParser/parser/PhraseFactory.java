package SlotGrammarParser.parser;

import SlotGrammarParser.abstractions.*;
import SlotGrammarParser.exceptions.TokenHasNoWordNumberException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Anthony
 * Date: 8/20/12
 * Time: 6:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhraseFactory {

    // Create a Phrase with a token and a frame
    public static Phrase createPhrase(SentenceToken token, SenseFrame frame) throws TokenHasNoWordNumberException {

        //ConcretePhrase(String originalString, String headwordLemma, List<Feature> features, int wordNumber, int leftBoundary, int rightBoundary, List<Slot> complementSlots, List<Slot> adjunctSlots)
        Phrase newPhrase = new ConcretePhrase(token.getOriginalText(), frame.getSenseName(), frame.getFeatures(), token.getWordNumber(), token.getLeftBoundary(), token.getRightBoundary(),
                frame.getComplementSlots(), frame.getAdjunctSlots(), frame.getPartOfSpeech());
        for(Slot s : newPhrase.getSlots())
        {
            s.setParent(newPhrase);
        }

        return newPhrase;


    }

    // Create a list of Phrases for each senseframe contained in the lexical entry for the token
    public static List<Phrase> createPhrase(SentenceToken token) {

        List<Phrase> phrases = new ArrayList<Phrase>();

        for(SenseFrame frame : token.getLexicalEntry().getSenseFrames()) {
            try {
                phrases.add(PhraseFactory.createPhrase(token, frame));
            } catch (TokenHasNoWordNumberException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return phrases;
    }

    public static List<Phrase> createPhrases(TokenizedSentence sentence) {
        List<Phrase> phrases = new ArrayList<Phrase>();

        for(SentenceToken token : sentence.getTokenList()) {
            phrases.addAll(PhraseFactory.createPhrase(token));
        }

        return phrases;
    }
}
