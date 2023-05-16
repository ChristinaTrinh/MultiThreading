package GuessingGameShow;
public class GuessingGameShow implements Runnable
{
    private String name;
    private int actualNumber;
    private int guessNumber;
    private int guessCount;
    private int low;
    private int high;
    private String threadName;

    public GuessingGameShow(String name, int actualNumber, int high)
    {
        this.name = name;
        this.actualNumber = actualNumber;
        this.high = high;
        this.guessNumber = (low+high)/2;
    }

    public String getName()
    {
        return this.name;
    }

    public int getActualNumber()
    {
        return this.actualNumber;
    }

    public int getGuessNumber()
    {
        return this.guessNumber;
    }

    public int getGuessCount()
    {
        return this.guessCount;
    }

    public String getThreadName()
    {
        return this.threadName;
    }

    public void run()
    {
        while(guessNumber != actualNumber) //sorting via binary approach
        {
            guessNumber = (low+high)/2;
            guessCount++;
            if(guessNumber<actualNumber)
            {
                low = guessNumber;
                System.out.println(name + ":  guessNumber = "+guessNumber+"  guessCount = " + guessCount + 
                                   "  TOO LOW!" + "Thread: " + Thread.currentThread().getName());
            }
            else // guessNumber > actualNumber
            {
                high = guessNumber;
                System.out.println(name + ":  guessNumber = "+guessNumber+"  guessCount = " + guessCount + 
                                   "  TOO HIGH!" + "Thread: " + Thread.currentThread().getName());
            }
            threadName = Thread.currentThread().getName();
            try 
            {
                Thread.currentThread().sleep(1000); //put the thread to sleep before another guess
            } catch (InterruptedException e) {}
        }
        System.out.println(name +":  guessCount = " + guessCount + "  actualNumber = " + actualNumber);
    }
    
}
