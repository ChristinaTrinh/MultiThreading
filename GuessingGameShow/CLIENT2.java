package GuessingGameShow;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CLIENT2 
{
    public static void main(String[]args)
    {
        final int max = 10000000; //highest number
        final int numberOfPlayers = 10; 
        GuessingGameShow[] threadArray = new GuessingGameShow[numberOfPlayers]; //threadArray that holds the players

        ExecutorService executor = Executors.newFixedThreadPool(numberOfPlayers); //create the thread pool
        for(int i = 0; i < numberOfPlayers; i++) 
        {
            GuessingGameShow thread = new GuessingGameShow("player"+i, (int) (Math.random()*max+1), max); //create the task
            threadArray[i] = thread; //put the task into the array
            executor.execute(threadArray[i]); //execute the thread
        }
        executor.shutdown(); //shutdown the thread
        while(!executor.isTerminated()){} //check if all the tasks are completed
        Arrays.sort(threadArray, new threadGuessCount_Comparator()); //sort the threadArray 
        System.out.println("\n\n\n");
        for(int i = 0; i < numberOfPlayers; i++) //print the sorted threadArray
        {
            GuessingGameShow thread = (GuessingGameShow) threadArray[i];
            System.out.println("Name = " + thread.getName() + ": ActualNumber = " + thread.getActualNumber() + 
                               "  GuessNumber = " + thread.getGuessNumber() + "  GuessCount = " + thread.getGuessCount() + 
                               "  ThreadName: " + thread.getThreadName()); 
        }
    }
}
