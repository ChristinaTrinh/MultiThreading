package GuessingGameShow;

import java.util.Comparator;

public class threadGuessCount_Comparator implements Comparator<GuessingGameShow>
{
   public int compare(GuessingGameShow thread1, GuessingGameShow thread2)
   {
      return thread1.getGuessCount()- thread2.getGuessCount();
   } 
}
