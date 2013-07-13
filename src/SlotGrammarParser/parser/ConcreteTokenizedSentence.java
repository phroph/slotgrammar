package SlotGrammarParser.parser;

import SlotGrammarParser.abstractions.Dictionary;
import SlotGrammarParser.abstractions.SentenceToken;
import SlotGrammarParser.abstractions.TokenizedSentence;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ADeLuca
 * Date: 8/16/12
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConcreteTokenizedSentence implements TokenizedSentence {

    List<SentenceToken> tokens;
    public ConcreteTokenizedSentence(List<SentenceToken> tokens) {

        assert(tokens.size()>0);
        this.tokens = tokens;

    }

    @Override
    public List<SentenceToken> getTokenList() {
        return tokens;

    }
}
