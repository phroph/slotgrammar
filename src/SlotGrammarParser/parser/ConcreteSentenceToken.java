package SlotGrammarParser.parser;

import SlotGrammarParser.SlotGrammarParser.LexicalEntry;
import SlotGrammarParser.abstractions.SentenceToken;
import SlotGrammarParser.exceptions.TokenHasNoWordNumberException;

/**
 * Created with IntelliJ IDEA.
 * User: Anthony
 * Date: 8/17/12
 * Time: 1:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConcreteSentenceToken implements SentenceToken {

    private Object token;
    boolean isPunctuation;
    int leftBound, rightBound;
    private String originalText;
    final int wordNumber;

    public ConcreteSentenceToken(String originalText, int wordNumber, Object obj, boolean  isPunctuation, int leftBound, int rightBound) {
        this.originalText = originalText;
        this.token = obj;
        this.isPunctuation = isPunctuation;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.wordNumber = wordNumber;

    }

    public String getOriginalText() {
        return originalText;
    }

    public String toString() {

        StringBuilder result = new StringBuilder();
        if(token.getClass()==LexicalEntry.class) {
            result.append (((LexicalEntry)token).getIndexWord().toString());
        } else {
            result.append((String)token);

        }

        result.append(" [" +leftBound + ", " + rightBound + "]");
        return  result.toString();

    }


    public boolean isLexicalEntry() {
        return !isPunctuation;
    }

    @Override
    public boolean isWord() {
      // return false;  //To change body of implemented methods use File | Settings | File Templates.
        return !isPunctuation;
    }

    @Override
    public boolean isPunctuation() {
        return isPunctuation;
    }

    @Override
    public int getLeftBoundary() {
        return leftBound;
    }

    @Override
    public int getRightBoundary() {
        return rightBound;
    }


    public LexicalEntry getLexicalEntry() {
        if(isLexicalEntry()) {
            return (LexicalEntry)token;
        } else {
            return null;
        }
    }

    @Override
    public int getWordNumber() throws TokenHasNoWordNumberException {
        if(!isWord()) {
            throw new TokenHasNoWordNumberException(this);
        }
        else {
            return wordNumber;
        }
    }


}
