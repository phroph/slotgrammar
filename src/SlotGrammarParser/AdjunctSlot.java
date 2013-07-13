package SlotGrammarParser;

import SlotGrammarParser.abstractions.SlotOption;
import SlotGrammarParser.abstractions.*;
import SlotGrammarParser.parser.ConcretePhrase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Phillip
 * Date: 8/20/12
 * Time: 8:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdjunctSlot implements Slot {
    private SlotOption[] options;
    private SlotType type;
    private List<Phrase> contents;
    private Phrase parent;

    public AdjunctSlot(SlotType type, Phrase parent, SlotOption... options)
    {
        this.options = options;
        this.parent = parent;
        this.type = type;
        contents = new ArrayList<Phrase>();
    }

    @Override
    public Phrase getParent() {
        return parent;
    }

    @Override
    public String getSlotType() {
        return this.type.toString().toLowerCase();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isComplementSlot() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isAdjunctSlot() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<SlotGrammarParser.abstractions.SlotOption> getSlotOptions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

     @Override
     public boolean accepts(Phrase child) {
         ConcretePhrase par = (ConcretePhrase)parent;
         for(Phrase p : parent.getFilledPhrases())
         {
             if(p.getLeftBoundary() == child.getLeftBoundary() && p.getRightBoundary() == child.getRightBoundary())
                 return false;
         }
         if(par.fixedLeftBoundary == child.getRightBoundary() || par.fixedLeftBoundary == child.getRightBoundary())
         {
            for(SlotOption opt : options)
            {
                 if(opt.accepts(child))
                 {
                     return true;
                 }
            }
         }
         return false;
     }

     @Override
     public int put(Phrase child) {
          if(accepts(child))
          {
              contents.add(child);
              return 1;
          }
         return 0;
     }
    public String toString() {
        String retVal = "A[";
        for(Phrase p : contents)
        {
            retVal += p.toString();
        }
        retVal+="]";
        return retVal;
    }

    @Override
    public Slot clone() {
        AdjunctSlot retVal = new AdjunctSlot(type, parent, options);
        for(Phrase p : contents)
        {
            retVal.contents.add(p);
        }
        return retVal;
    }
    public List<Phrase> getContent()
    {
        return contents;
    }

    @Override
    public int getFillCount() {
        return contents.size();
    }

    @Override
    public void setParent(Phrase newPhrase) {
        parent = newPhrase;
    }

    @Override
    public Phrase getContents() {
        return contents.get(0);
    }
}
