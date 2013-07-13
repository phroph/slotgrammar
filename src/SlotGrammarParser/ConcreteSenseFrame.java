	package SlotGrammarParser;

//import SlotGrammarTree.SlotGrammarTreeNode;

import SlotGrammarParser.abstractions.Feature;
import SlotGrammarParser.abstractions.SenseFrame;
import SlotGrammarParser.abstractions.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class ConcreteSenseFrame implements SenseFrame {

    Feature pos;

    public Slot[] slots;
    public List<Feature> features;
    int id;

    private IndexWord indexWord;

    public String toString() {
        // assert(indexWord!=null);
        return String.format("(%s) {%s}", Arrays.toString(slots),features);
    }
   /* public boolean equals(Object sf)
    {
        return indexWord.toString().equals(((ConcreteSenseFrame) sf).getIndexWord().toString()) && pos.equals(((ConcreteSenseFrame)sf).getPOS());
    }*/

    public void fillSlot(Phrase p)
    {
        for(Slot s : slots)
        {
            if(s.accepts(p))
            {

            }
        }
    }

    public List<Slot> getAdjunctSlots() {
        List<Slot> retval = new ArrayList<Slot>();
        for(Slot slot : slots)
        {
            if(slot.isAdjunctSlot())
                retval.add(slot);
        }
        return retval;
    }

    public void setID(int i)
    {
        id = i;
    }

    public ConcreteSenseFrame(Feature partOfSpeech, List<Feature> features, Slot... slots)
    {
        this.pos = partOfSpeech;
        this.slots = slots;
        this.features = features;
    }
   
    public void setIndexWord(IndexWord iw)
    {
        indexWord = iw;
    }

    public Slot[] getSlots() {
        return slots;
    }

    public List<Feature> getFeatures() {
        return features;
    }


    public String getSenseName() {
        return getSenseNameHelper(this);
    }

    private String getSenseNameHelper(ConcreteSenseFrame frame)
    {
        return frame.getIndexWord().toString().replace(' ','_')+id;
    }

    /*public void fillSlot(ConcreteSenseFrame sf)
    {
        fillSlotHelper(sf, sf.slots);
    }
    private void fillSlotHelper(ConcreteSenseFrame sf, List<Slot> slots)
    {
        if(slots.get(0).canAccept(sf))
        {
            slots.get(0).filledFrames.add(sf);
        }
        else
        {
            fillSlotHelper(sf, slots.subList(1, slots.size()-1));
        }
    }
    public void fillSlot(List<ConcreteSenseFrame> senseFrames)
    {
        try{
            fillSlot(senseFrames.get(0));
        }
        catch(Exception e)
        {
            fillSlot(senseFrames.subList(1, senseFrames.size()-1));
        }
    }*/


    /*public void addDet(SlotGrammarTreeNode node) {
        try{
            Slot[] newSlots = new Slot[slots.length+1];
            for(int i=0; i<slots.length;i++)
                newSlots[i] = slots[i];
            newSlots[slots.length] = new Slot(SlotType.DET, SlotOption.det);
            slots = newSlots;
            slots[slots.length-1].accept(node);
            int worked;
        }
        catch(Exception e)
        {

        }
    }     */

    public IndexWord getIndexWord() {
        return indexWord;
    }

     @Override
     public Feature getPartOfSpeech() {
            return pos;
     }

     @Override
     public List<Slot> getComplementSlots() {
         List<Slot> retval = new ArrayList<Slot>();
         for(Slot slot : slots)
         {
             if(slot.isComplementSlot())
                 retval.add(slot);
         }
         return retval;
     }

     @Override
     public int getID() {
          return this.id;
     }
    

}
