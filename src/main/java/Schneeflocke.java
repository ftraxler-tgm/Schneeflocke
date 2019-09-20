import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Fabian Traxler {@literal <ftraxler@student.tgm.ac.at>}
 * @version 2019-09-18
 * @project untitled
 */
public class Schneeflocke extends Thread {
    int x = 0;
    int y = 10;
    String name = "";
    private static Object lock = new Object();
    private static int counter;
    public Schneeflocke(int x, String name){
        this.x = x;
        this.name = name;
    }

    public void run(){
        while (y>0){
            try {
                Random r = new Random();
                int gravity= r.nextInt(4);
                int velocity= r.nextInt(1);
                sleep(1000);
                //System.out.println(this.name+" X:Position: "+x+ " Y:Position: "+y);
                y-=gravity;
                x-=velocity;
                if(y<0){
                    y=0;
                }
                if(x<0){
                    x=0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        //System.out.println(name+" Reached the ground");
        increment();
    }
     public void increment(){
        synchronized (lock){
            counter++;
        }
    }
    public static int read(){
        synchronized (lock){
            return counter;
        }
    }

    public static void main(String[] args){
        try {
            Thread.sleep(1000);
            Schneeflocke s = new Schneeflocke(10, "S1");
            Schneeflocke s2 = new Schneeflocke(5,"S2");
            s.start();
            s2.start();
            do{
                String[][] map = new String[11][11];
                for(int i = 0; i < 11; i++)
                {
                    for(int j = 0; j < 11; j++)
                    {
                        map[i][j]=" ";
                    }
                }

                Thread.sleep(500);
                System.out.println("--------------------------------------");
                if(s.x<0){
                    s.x=0;
                }
                if(s.y<0){
                    s.y=0;
                }

                if(s2.x<0){
                    s2.x=0;
                }
                if(s2.y<0){
                    s2.y=0;
                }
                map[s.y][s.x] = "X";
                map[s2.y][s2.x] = "X";

                for(int i = 10; i >=0;i--){
                    for(int j = 10; j >=0; j--){
                        System.out.print(map[i][j]);
                    }
                    System.out.println();
                }
                System.out.println("--------------------------------------");
            }while(read()!=2);
            s.interrupt();
            s2.interrupt();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
