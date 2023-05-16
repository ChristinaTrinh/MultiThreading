package Demonstration_PrintChar2;
public class PrintChar2 implements Runnable
{
    private String charsToPrint;
    private int times;

    public PrintChar2(String charsToPrint, int times)
    {
        this.charsToPrint = charsToPrint;
        this.times = times;
    }

    public void run()
    {
        System.out.println("Thread.getName() = " + Thread.currentThread().getName());
        for(int i = 1; i <= times; i++)
        {
            if((i >= times/2) && (times%2 == 0))
            {
                int r1 = (int) (Math.random()*10+1); // range 1-10
                System.out.print(this.charsToPrint + "    r1 = " + r1 + "    ");
                int currentNum = Integer.valueOf(this.charsToPrint.charAt(charsToPrint.length()-1))+1;
                String currentString = this.charsToPrint.substring(0, this.charsToPrint.length());
                switch(r1)
                {
                    case 1, 2, 3, 4, 5: 
                        int r2 = (int) (Math.random()*3+1);
                        if(r2 == 1)
                            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                        else if(r2 == 2)
                            Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
                        else // if(r2 == 3)
                            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                        System.out.println("setPriority(" + r2 + ");");
                        break;
                    case 6:
                        if(currentNum <= 3)
                        {
                            Runnable task = new PrintChar2(currentString + currentNum, 100);
                            Thread newThread = new Thread(task);
                            newThread.start();
                        }
                        System.out.println("newThread.start();");
                        break;
                    case 7:
                        if(currentNum <= 3)
                        {
                            Runnable task = new PrintChar2(currentString + currentNum, 100);
                            Thread newThread = new Thread(task);
                            try
                            {
                                newThread.join();
                            }catch(InterruptedException ex){}
                        }
                        System.out.println("newThread.join();");
                        break;
                    case 8:
                        System.out.println("yield();");
                        Thread.currentThread().yield();
                        break;
                    case 9: 
                        System.out.println("sleep(500);");
                        try
                        {
                            Thread.currentThread().sleep(500);
                        }catch(InterruptedException ex){}
                        break;
                    case 10:
                        System.out.println("stop()");
                        i = times+1;
                        break;
                    default:
                        break;
                }
            }
        }
    }
}