package SlotGrammarParser.parser;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Anthony
 */
import SlotGrammarParser.abstractions.Phrase;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import java.util.ArrayList;
public class SlotGrammarPhraseChart {
     
     List<Phrase> chart;
     
     public SlotGrammarPhraseChart() {
          chart = new ArrayList<Phrase>();
     }
    public List<Phrase> getPhrases()
    {
        return chart;
    }
     
     @Test
     public void MaryGaveJohnABookTest() {
          SlotGrammarParser parser;
          try {
               parser = new SlotGrammarParser();
               SlotGrammarPhraseChart chart = new SlotGrammarPhraseChart();
               List<Phrase> starterPhrases = parser.extractSentenceStarterPhrases("Mary gave John a ball.");
               assert(starterPhrases.size()==5);
               System.out.println("STARTER PHRASE TEST:" );
               System.out.println(starterPhrases.get(0));
               
          } catch (Exception ex) {
               Logger.getLogger(SlotGrammarPhraseChart.class.getName()).log(Level.SEVERE, null, ex);
               assert(false);
          }
        
     }
    public void addPhrase(Phrase p)
    {
        addPhrase(p, chart.size(), 0);
    }
     public void addPhrase(Phrase p, int takeSize, int shift) {

         List<Phrase> toAdd = new ArrayList();
         chart.add(p);
         if(p.getOriginalText().toLowerCase().equals("ball"))
         {
             int test = 5;
         }
         //for(Phrase chartPhrase : chart)
         //{
         //    toAdd.addAll(fill(p, chartPhrase));
         //    toAdd.addAll(fill(chartPhrase, p));
         //}
          for(int it=shift+2; it<=takeSize+1; it++) {
              toAdd.addAll(fill(p,chart.get(chart.size()-it)));
              toAdd.addAll(fill(chart.get(chart.size()-it),p));
          }
         for(Phrase add : toAdd)
         {
             //chart.add(add);
             shift++;
             addPhrase(add, takeSize, shift);
         }

     }
     
     public Phrase get(int index) {
          return chart.get(index);
     }
     public int size() {
          return chart.size();
     }

     // Attempt to fill each of the parent's slots with the childs slot
     // Create a new phrase for each
     private List<Phrase> fill(Phrase parent, Phrase child) {
         
          List<Phrase> newPhrases = new ArrayList<Phrase>();
         ConcretePhrase par = (ConcretePhrase)parent;
          
          // Check the boundaries are touching, if not just return an empty
          // list
          //if(!(par.fixedLeftBoundary==child.getRightBoundary()) || (par.fixedRightBoundary==child.getLeftBoundary())) {
          //     return newPhrases;
          //}
         for(Phrase p : parent.getFilledPhrases())
         {
             if(((ConcretePhrase)p).fixedLeftBoundary == ((ConcretePhrase)child).fixedLeftBoundary && ((ConcretePhrase)p).fixedRightBoundary == ((ConcretePhrase)child).fixedRightBoundary)
                 return newPhrases;
         //    if(parent.getLeftBoundary() == )
         }
          
         
          // For each slot in the parent, attempt to fill with child
         for(int sIndex = 0;sIndex<parent.getSlots().size();sIndex++) {
               if(parent.getSlots().get(sIndex).accepts(child)) {
                    Phrase parentClone = parent.clone();
                    int score = parentClone.getSlots().get(sIndex).put(child.clone()) + child.getParseScore();
                    parentClone.addScore(score);
                   if(child.getLeftBoundary() < parent.getLeftBoundary())
                       parentClone.setLeftBoundary(child.getLeftBoundary());
                   if(child.getRightBoundary() > parent.getRightBoundary())
                       parentClone.setRightBoundary(child.getRightBoundary());
                    newPhrases.add(parentClone);
                   break; //We've added it, so cut the shit.
               }
          }
         
         return newPhrases;
     }
}
