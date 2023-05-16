package Demonstration_PrintChar2;
public class CLIENT 
{
    public static void main(String[]args)
    {
        final int N = 100;

        Runnable printA = new PrintChar2("a1", N);
        Runnable printB = new PrintChar2("b1", N);
        Runnable printC = new PrintChar2("c1", N);

        Thread thread1 = new Thread(printA);
        Thread thread2 = new Thread(printB);
        Thread thread3 = new Thread(printC);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
