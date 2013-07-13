package SlotGrammarParser.abstractions;

import SlotGrammarParser.exceptions.TokenHasNoWordNumberException;

/**
 * Created with IntelliJ IDEA.
 * User: adeluca
 * Date: 8/20/12
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SentenceToken {

    // Returns true if the token is a word, not a punctuation
    public boolean isWord();

    public boolean isPunctuation();

    public int getLeftBoundary();

    public int getRightBoundary();

    // Gets the original text of the token as it appeared in the sentence.
    public String getOriginalText();

    // Returns null if it is not a lexical entry.
    public LexicalEntry getLexicalEntry();

    public int getWordNumber() throws TokenHasNoWordNumberException;

}
