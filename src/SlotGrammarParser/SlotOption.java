package SlotGrammarParser;

import SlotGrammarParser.abstractions.Feature;
import SlotGrammarParser.abstractions.Phrase;

/**
 * Created with IntelliJ IDEA.
 * User: Anthony
 * Date: 8/13/12
 * Time: 12:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class SlotOption implements SlotGrammarParser.abstractions.SlotOption {
  //  n, v, p, det;
    Test[] tests;

    public SlotOption(Feature feat)
    {
        tests = new Test[1];
        tests[0] = new Test(feat);
    }
    public boolean accepts(Phrase p) {
        for(Feature feature : p.getFeatures())
        {
            for(Test t : tests)
            {
                if(t.evaluate(feature))
                    return true;
            }
        }
        return false;
    }
  /*  public boolean accepts(Feature f) {
      switch(this) {
          case n:

              if(f.equals(Feature.np)
                      ||f.equals((Feature.noun))) {
                  return true;
              }

              break;

          case v:
              break;
          case p:
              break;
          case det:

              if(f.equals(Feature.noun) || f.equals(Feature.vp)) {
                  return true;
              }
              break;
      }

        return false;
    }*/
     
     
}
