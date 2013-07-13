package SlotGrammarParser;

import SlotGrammarParser.abstractions.Feature;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Phillip
 * Date: 8/19/12
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    //
    Feature toMatch;

    public Test(Feature matcher)
    {
        toMatch = matcher;
    }

    public boolean evaluate(Feature features)
    {
        return features.isSubset(toMatch);
    }
}
