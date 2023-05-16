package GuessingGameShow;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//import javafx.application.Application; 

public class CLIENT3 
{
    public static void main(String[]args)
    {    
        int max=1000000;
        int numberOfPlayers = 3;
        int playersActualNumber;        
        int playersStartingBank = 100;
        Thread t;
        GuessingGameShow_PlayerThread_withBank p;  
        ExecutorService executor = Executors.newFixedThreadPool(numberOfPlayers);
        GuessingGameShow_PlayerThread_withBank threadArray[] = new GuessingGameShow_PlayerThread_withBank[numberOfPlayers];

        for (int i=1; i<=numberOfPlayers; i++)
        {
            playersActualNumber = (int) (Math.random()*max+1);
            p = new GuessingGameShow_PlayerThread_withBank("P"+i,playersActualNumber,max,playersStartingBank);   
            t = new Thread(p);  // ... so 'p' can be put into a Thread.            
            threadArray[i-1] = p; 
            executor.execute(t);            
        }  // i   
        
        // Stop all threads
        executor.shutdown();   
        while(!executor.isTerminated()) {}   // stall / wait for all threads to stop
              
              
        System.out.println("\n\n\t Sort and Print threadArray: ");       
        Arrays.sort(threadArray, new Comparator<GuessingGameShow_PlayerThread_withBank>()
        {
            public int compare(GuessingGameShow_PlayerThread_withBank thread1, GuessingGameShow_PlayerThread_withBank thread2)
            {
                return thread1.getPlayersBank() - thread2.getPlayersBank();
            }
        });
                                
        for (GuessingGameShow_PlayerThread_withBank gpt : threadArray)
        {
            System.out.println("Name = " + gpt.getName() + ": " + 
                               "ActualNumber = " + gpt.getActualNumber() + "   " + 
                               "GuessNumber = " + gpt.getGuessNumber() + "   " +
                               "GuessCount = " + gpt.getGuessCount() + "   " +   
                               "PlayersBank = " + gpt.getPlayersBank() + "   " + 
                               "gpt/this = " + gpt);
        }      
        System.exit(0);
    }

    public static class GuessingGameShow_PlayerThread_withBank implements Runnable
    {
        private String name;
        private int actualNumber;
        private int guessNumber;
        private int guessCount;
        private int low;
        private int high;
        private int playersBank;
        private int playersBet;
        private static Bank bigBank;

        public GuessingGameShow_PlayerThread_withBank(String name, int actualNumber, int high, int playersBank)
        {
            this.name = name;
            this.actualNumber = actualNumber;
            this.high = high;
            this.guessNumber = (low+high)/2;
            this.playersBank = playersBank;
            GuessingGameShow_PlayerThread_withBank.bigBank = new Bank (1000);
        }

        public String getName()
        {
            return this.name;
        }

        public int getGuessNumber()
        {
            return this.guessNumber;
        }

        public int getGuessCount()
        {
            return this.guessCount;
        }

        public int getActualNumber()
        {
            return this.actualNumber;
        }

        public int getPlayersBet()
        {
            return this.playersBet;
        }

        public int getPlayersBank()
        {
            return this.playersBank;
        }

        public void addToPlayersBank(int amount)
        {
            this.playersBank += amount;
        }

        public void subtractFromPlayersBank(int amount)
        {
            this.playersBank -= amount;
        }
        public void run()
        {
            String resultingString = "";
    
            while (guessNumber!=actualNumber && playersBank!=0 && bigBank.getBankBalance()!=0)
            {
                guessCount++;
                guessNumber = (low+high)/2;
                
                // NEW: figure playersBet and call bank's bet.
                playersBet = (int) ((Math.random()*10+1)/100.0 * playersBank);
                if(playersBet==0 || playersBank ==1)
                    playersBet = 1;
                if(bigBank.getBankBalance() != 0)
                    resultingString = name + ": guessNumber = " + guessNumber + "\t guessCount = " + guessCount +
                                          "\t playersBet = " + playersBet;
                bigBank.bet(this);
                if (guessNumber<actualNumber) 
                {
                    low=guessNumber;
                    resultingString += " Guess too LOW! You lose " + playersBet +
                                           " playersBank = " + playersBank;
                } 
                else if (guessNumber>actualNumber) 
                {
                    high=guessNumber;
                    resultingString += " Guess too HIGH! You lose " + playersBet + 
                                           " playersBank = " + playersBank;                
                } else {  // (guessNumber == actualNumber)
                    resultingString += " CORRECT! You win!";               
                }
    
               System.out.println(resultingString + " Thread: " + this + "  bigBank balance: " + bigBank.getBankBalance());
               System.out.println();
            } 
    
            if (bigBank.getBankBalance()==0) 
            {
                System.out.println(">>>>> bigBank.getBankBalance()==0!!!  Game OVER!!!" + " Thread: " + this);
            }
            System.out.println(">>>>> " + name + ": Your guessCount = " + guessCount +
                                       " actualNumber = " + actualNumber +
                                       " playersBank = " + playersBank);         
            System.out.println();                                
        } 
    }

    private static class Bank
    {
        private int bankBalance;
        private static Lock lock = new ReentrantLock();
        public Bank(int bankBalance)
        {
            this.bankBalance = bankBalance;
        }

        public int getBankBalance()
        {
            return this.bankBalance;
        }
        
        public void bet(GuessingGameShow_PlayerThread_withBank player)
        {        
            lock.lock();
            try
            {
                if(this.bankBalance == 0)
                    throw new IllegalStateException("bigBank runs out of money!");
                if(player.getGuessNumber() == player.getActualNumber()) //if they win they we take money from bank and put into their bank
                {
                    player.addToPlayersBank(player.getPlayersBet());
                    this.bankBalance -= player.getPlayersBet();
                }
                else
                {
                    player.subtractFromPlayersBank(player.getPlayersBet());
                    this.bankBalance += player.getPlayersBet();
                }
                Thread.sleep(100);
            } catch(InterruptedException ex) {}
            finally
            {
                lock.unlock();
            }
        } 
    }
}
