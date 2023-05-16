package GuessingGameShow;
public class CLIENT1 
{
    public static void main(String[]args)
    {
        final int max = 10000000;
        int n1 = (int) (Math.random()*max+1);
        int n2 = (int) (Math.random()*max+1);
        int n3 = (int) (Math.random()*max+1);

        Runnable player1 = new GuessingGameShow("p1", n1, max);
        Runnable player2 = new GuessingGameShow("p2", n2, max);
        Runnable player3 = new GuessingGameShow("p3", n3, max);
         
        Thread thread1 = new Thread(player1);
        Thread thread2 = new Thread(player2);
        Thread thread3 = new Thread(player3);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
