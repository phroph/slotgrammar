package SlotGrammarParser.exceptions;

import SlotGrammarParser.abstractions.SentenceToken;

/**
 * Created with IntelliJ IDEA.
 * User: Anthony
 * Date: 8/20/12
 * Time: 7:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class TokenHasNoWordNumberException extends Exception {

    public TokenHasNoWordNumberException(SentenceToken token) {
        super("Token: " + token.getOriginalText() + " has no word number");
    }
}
