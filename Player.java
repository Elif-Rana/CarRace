import java.awt.*;
import java.sql.SQLOutput;
import java.util.concurrent.locks.ReentrantLock;

public class Player extends Thread {
    private int x;
    private int y;
    private Color color;
    int order;
    private String status;
    CarRace carRace;
    boolean crashed;
    boolean pressed;
    boolean ableToMove;
    int region;
    ReentrantLock lock;

    //checking whether the player finished the race
    boolean startLine;
    boolean firstQuarter;
    boolean lastQuarter;
    boolean finishLine;

    boolean won;

    long wait;


    public Player(){}
    public Player(CarRace cr, int ord){
        lock=new ReentrantLock();
        wait=-1;
        won=false;
        startLine=true;
        firstQuarter=false;
        lastQuarter=false;
        finishLine=false;
        ableToMove=true;
        region=1;


        pressed=false;
        crashed =false;
        carRace=cr;
        order=ord;
        status  = (order+1)+". Oyuncu";
        if (order==0){
            x=50;
            y=385;
            color=Color.RED;
        }
        else if(order==1){
            x=32;
            y=385;
            color=Color.GREEN;
        }
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getX() {
        return x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getY() {
        return y;
    }
    public Color getColor(){
        return color;
    }
    public String getStatus() {
        return status ;
    }

    @Override
    public void run() {
        while(carRace.gameContinue) {
            for(int i=0;i<2;i++){
                if(i==order) continue;
                if((x>=carRace.players[i].getX() && x<=carRace.players[i].getX()+10 &&
                        y>=carRace.players[i].getY() && y<=carRace.players[i].getY()+10)
                        || (x+10>=carRace.players[i].getX() && x+10<=carRace.players[i].getX()+10 &&
                        y>=carRace.players[i].getY() && y<=carRace.players[i].getY()+10)
                        ||(x>=carRace.players[i].getX() && x<=carRace.players[i].getX()+10 &&
                        y+10>=carRace.players[i].getY() && y+10<=carRace.players[i].getY()+10)
                        ||(x+10>=carRace.players[i].getX() && x+10<=carRace.players[i].getX()+10 &&
                        y+10>=carRace.players[i].getY() && y+10<=carRace.players[i].getY()+10)){
                    crashed=true;
                    carRace.players[i].crashed=true;
                    ableToMove=false;
                    carRace.players[i].ableToMove=false;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    ableToMove=true;
                    carRace.players[i].ableToMove=true;

                    if(x>=carRace.players[i].getX()){
                        x+=3;
                        carRace.players[i].setX(carRace.players[i].getX()-3);
                        if(y>=carRace.players[i].getY()){
                            y+=3;
                            carRace.players[i].setY(carRace.players[i].getY()-3);
                        }
                        else{
                            y-=3;
                            carRace.players[i].setY(carRace.players[i].getY()+3);
                        }
                    }
                    else {
                        x-=3;
                        carRace.players[i].setX(carRace.players[i].getX()+3);
                        if(y>=carRace.players[i].getY()){
                            y+=3;
                            carRace.players[i].setY(carRace.players[i].getY()-3);
                        }
                        else{
                            y-=3;
                            carRace.players[i].setY(carRace.players[i].getY()+3);
                        }
                    }
                }
            }
            region=checkRegion();
            double distance;
            double distance2;
            switch (region){
                case 1:
                    distance=Math.sqrt(((carRace.centerX-x)*(carRace.centerX-x))+((carRace.centerY-y)*(carRace.centerY-y)));
                    distance2=Math.sqrt(((carRace.centerX-(x+10))*(carRace.centerX-(x+10)))+((carRace.centerY-(y+10))*(carRace.centerY-(y+10))));
                    if(distance>=343){
                        crashed = true;
                        x+=5;
                        y+=5;
                    }
                    if(distance2<=193){
                        crashed = true;
                        x-=5;
                        y-=5;
                    }
                    break;
                case 2:
                    distance=Math.sqrt(((carRace.centerX-(x+10))*(carRace.centerX-(x+10)))+((carRace.centerY-y)*(carRace.centerY-y)));
                    distance2=Math.sqrt(((carRace.centerX-x)*(carRace.centerX-x))+((carRace.centerY-(y+10))*(carRace.centerY-(y+10))));
                    if(distance>=354){
                        crashed = true;
                        x-=5;
                        y+=5;
                    }
                    if(distance2<=205){
                        crashed = true;
                        x+=5;
                        y-=5;
                    }
                    break;
                case 3:
                    distance=Math.sqrt(((carRace.centerX-(x+10))*(carRace.centerX-(x+10)))+((carRace.centerY-(y+10))*(carRace.centerY-(y+10))));
                    distance2=Math.sqrt(((carRace.centerX-x)*(carRace.centerX-x))+((carRace.centerY-y)*(carRace.centerY-y)));
                    if(distance>=356){
                        crashed = true;
                        x-=5;
                        y-=5;
                    }
                    if(distance2<=206){
                        crashed = true;
                        x+=5;
                        y+=5;
                    }
                    break;
                case 4:
                    distance=Math.sqrt(((carRace.centerX-x)*(carRace.centerX-x))+((carRace.centerY-(y+10))*(carRace.centerY-(y+10))));
                    distance2=Math.sqrt(((carRace.centerX-(x+10))*(carRace.centerX-(x+10)))+((carRace.centerY-y)*(carRace.centerY-y)));
                    if(distance>=348){
                        crashed = true;
                        x+=5;
                        y-=5;
                    }
                    if(distance2<=198){
                        crashed = true;
                        x-=5;
                        y+=5;
                    }
                    break;
            }

            if (crashed) {
                System.out.println(crashed);
                ableToMove=false;
                //lock.lock();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                finally {
                    //lock.unlock();

                }
                ableToMove=true;
                crashed=false;
                System.out.println(crashed);
            }



        }
        //carRace.repaint();
    }
    //TO CHECK WHETHER IN THE RACE TRACK
    public int checkRegion(){
        if(x<=370 && y>390){
            return 4;
        }
        else if(x>370 && y<=390){
            return 2;
        }
        else if(x>370){
            return 3;
        }
        else return 1;
    }
}
