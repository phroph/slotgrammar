package SlotGrammarParser;


import SlotGrammarParser.abstractions.Feature;
import SlotGrammarParser.abstractions.Phrase;
import SlotGrammarParser.abstractions.Slot;
import SlotGrammarParser.abstractions.SlotOption;
import SlotGrammarParser.parser.ConcretePhrase;

import java.util.List;

/**
 * Slots are used during parsing. They come from the lexical entries and are 
 * filled with matching words.
 * @author Anthony
 *
 */

public class ComplementSlot implements Slot {

    private SlotOption[] options;
    private SlotType type;
    private Phrase contents;
    private Phrase parent;


   // public List<Object> filledFrames;

    /*public String toString() {
       // return String.format("(%s %s %s)",type,filledFrames, Arrays.toString(options));
    }   */

    public SlotOption[] getOptions()
    {
        return options;
    }
    private ComplementSlot(SlotType type, Phrase content, Phrase parent, SlotOption... options)
    {
        this.type = type;
        this.contents = content;
        this.parent = parent;
        this.options = options;
    }

    public boolean equals(Object obj)
    {
        boolean equals = true;
        for(SlotOption option : options)
        {
            boolean contains = false;
            for(SlotOption opt : ((ComplementSlot)obj).getOptions())
            {
                if(opt.equals(option))
                    contains = true;
            }
            if(contains == false)
                equals = false;
        }
        return ((ComplementSlot)obj).type.equals(type) && equals;
    }

    public ComplementSlot(SlotType type, SlotOption... slotOptions) {
        this.type = type;
     //   filledFrames = new ArrayList<Object>();
        options = slotOptions;
    }

    // check whether the slot can accept the given feature
   /* public boolean canAcceptFeature(Feature f) {

        for(SlotOption s: options) {
             if(s.accepts(f))  {
                 return true;
             }
        }

        return false;
    }  */

    // Checks whether the slot can be filled by the list of features
  /*  public boolean canAccept(List<Feature> features) {

        assert (features!=null);

    //  sstem.out.println("\t\tCan " + this + " accept " + features + "?");
       if(isFull())
       {
      //     System.out.println("No, Slot is full.");
           return false;
       }

        // Go through the list of options for the slot, check feach of the features for a match
        for(SlotOption option : options) {

         //   System.out.println("\t\t\tUsing option: " + option);
            for(Feature f: features) {
           //     System.out.printf("\t\t\t\tDoes option: %s accept feature %s?\n", option, f);
                if(option.accepts(f)) {
                //    System.out.println("\t\t\t\t\tYes.");
                    return true;
                }
            }
        }

       /*
        if (isFull())
            return false;
        //PartOfSpeech framePOS = sf.getPOS();
        List<SlotOption> mappedFeatures = new ArrayList<SlotOption>();
        for(Feature feature : features)
        {
            mappedFeatures.add(Dictionary.getFeatureMapping(feature));
        }
        /*if(type == SlotType.DET) {
             if(framePOS == PartOfSpeech.DET) { return true; }
             return false;
        } */

        /*
        for(SlotOption option : mappedFeatures)
        {
            for(SlotOption slotsOption : options)
            {
                if(option.equals(slotsOption))
                    return true;
            }
        }

        return false; */

       /* return false;

    }    */
       public boolean accepts(Feature f) {
       /*    for(Test test : tests)
           {
               if(test.evaluate(f))
               {
                   return true;
               }
           }             */
           return false;
       }

    public Phrase fill(Phrase phrase) throws Exception {

       throw new Exception();

    }


    // Adds sf to the slot, returns true if the slot can accept more frames.
    /*public void accept(Phrase phrase) throws Exception {

      // System.out.println("filling");
        if(canAccept(phrase.getFeatures()))
        {
            filledFrames.add(phrase);
        } else {
            throw new Exception("Slot is full or cannot accept this node");
        }

    }   */


    // Returns whether or not the current slot is full
  /*  public boolean isFull() {
        return filledFrames.size() > 0;
    }   */

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
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isAdjunctSlot() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<SlotOption> getSlotOptions() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

     @Override
     public boolean accepts(Phrase child) {
         if(contents!=null)
            return false;
         ConcretePhrase par = (ConcretePhrase)parent;
         if(type == SlotType.NID || (type == SlotType.OBJ && parent.getPartOfSpeech().isSubset(Feature.getFeatureFromString("noun"))))
        { //The child must be on the direct left boundary
            return false;
        }
         //if(par.fixedLeftBoundary == child.getRightBoundary() || par.fixedRightBoundary == child.getLeftBoundary())
         if(parent.getLeftBoundary() == child.getRightBoundary() || par.getRightBoundary() == child.getLeftBoundary())
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
              contents = child;
              return 1;
          }
         return 0;
     }

    @Override
    public int getFillCount() {
        if(contents != null)
            return 1;
        return 0;
    }

    @Override
    public void setParent(Phrase newPhrase) {
        parent = newPhrase;
    }

    @Override
    public Phrase getContents() {
        return contents;
    }

    public String toString()
    {
        if(contents!=null)
            return contents.toString();
        else
            return type.toString();
    }
    public Slot clone()
    {
        if(contents!=null)
            return new ComplementSlot(type, contents.clone(), parent, options);
        return new ComplementSlot(type, options);
    }
}
