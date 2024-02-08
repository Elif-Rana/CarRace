import java.awt.*;

public class Bot extends Thread{
    private int x;
    private int y;
    private Color color;
    private String status;
    CarRace carRace;
    boolean crashed;
    String prevLocation;
    int region;
    int region2;
    int order;

    //checking whether the bot finished the race
    boolean startLine;
    boolean firstQuarter;
    boolean lastQuarter;
    boolean finishLine;

    boolean won;

    Bot(CarRace cr, int ord){
        won=false;
        startLine=true;
        firstQuarter=false;
        lastQuarter=false;
        finishLine=false;
        region2=1;
        order=ord;
        region=1;
        prevLocation="";
        crashed=false;
        this.carRace=cr;
        color=Color.BLACK;
        status = (order+1)+". Bot";
        if (order==0){
            x=150;
            y=385;
        }
        else if(order==1){
            x=130;
            y=385;
        }
        else if(order==2){
            x=110;
            y=385;
        }
        else if(order==3){
            x=90;
            y=385;
        }
        else if(order==4){
            x=70;
            y=385;
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
        return status;
    }

    @Override
    public void run() {
        while (carRace.gameContinue) {
            if(y>=389 && y<=391 && x>=28 && x<=168){
                /*
                if(!lastQuarter){
                    startLine= !startLine;
                    System.out.println("starto");
                }

                 */
            }
            if(y>=40 && y<=180 && x>=387 && x<=389){
                if(startLine){
                    firstQuarter=true;
                }
            }
            if(y>=590 && y<=730 && x>=387 && x<=389){
                if(firstQuarter){
                    lastQuarter=true;
                }
            }
            if(y>=390 && y<=392 && x>=28 && x<=168){
                if(lastQuarter){
                    finishLine=true;
                }
            }
            if(finishLine){
                won=true;
            }
            try {
                Thread.sleep(carRace.time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            double direction = Math.random();
            region = checkRegion();
            switch (region) {
                case 1:
                    if (direction < 0.80) {
                        y--;
                    }
                    else if(y<=390) x++;
                    else x--;
                    break;
                case 2:
                    if (direction < 0.80) {
                        x++;
                    }
                    else if(x>=378) y++;
                    else y--;
                    break;
                case 3:
                    if (direction < 0.80) {
                        y++;
                    }
                    else if(y<=390) x++;
                    else x--;
                    break;
                case 4:
                    if (direction < 0.80) {
                        x--;
                    }
                    else if(x>=378) y++;
                    else y--;
                    break;

            }
            carRace.repaint();
            //crashed
            for (int i = 0; i < 5; i++) {
                if (order == carRace.bots[i].order) continue;
                if((x>=carRace.bots[i].getX() && x<=carRace.bots[i].getX()+10 &&
                        y>=carRace.bots[i].getY() && y<=carRace.bots[i].getY()+10)
                        || (x+10>=carRace.bots[i].getX() && x+10<=carRace.bots[i].getX()+10 &&
                        y>=carRace.bots[i].getY() && y<=carRace.bots[i].getY()+10)
                        ||(x>=carRace.bots[i].getX() && x<=carRace.bots[i].getX()+10 &&
                        y+10>=carRace.bots[i].getY() && y+10<=carRace.bots[i].getY()+10)
                        ||(x+10>=carRace.bots[i].getX() && x+10<=carRace.bots[i].getX()+10 &&
                        y+10>=carRace.bots[i].getY() && y+10<=carRace.bots[i].getY()+10)){
                    crashed = true;
                    carRace.bots[i].crashed = true;
                    if(x>=carRace.bots[i].getX()){
                        x+=3;
                        carRace.bots[i].setX(carRace.bots[i].getX()-3);
                        if(y>=carRace.bots[i].getY()){
                            y+=3;
                            carRace.bots[i].setY(carRace.bots[i].getY()-3);
                        }
                        else{
                            y-=3;
                            carRace.bots[i].setY(carRace.bots[i].getY()+3);
                        }
                    }
                    else {
                        x-=3;
                        carRace.bots[i].setX(carRace.bots[i].getX()+3);
                        if(y>=carRace.bots[i].getY()){
                            y+=3;
                            carRace.bots[i].setY(carRace.bots[i].getY()-3);
                        }
                        else{
                            y-=3;
                            carRace.bots[i].setY(carRace.bots[i].getY()+3);
                        }
                    }
                }
                /*
                if ((x == carRace.bots[i].getX() + 10 && ((y >= carRace.bots[i].getY()) || (y <= carRace.bots[i].getY() + 10)))
                        || (x == carRace.bots[i].getX() + 10 && ((y + 10 >= carRace.bots[i].getY()) || (y + 10 <= carRace.bots[i].getY() + 10)))
                        || (x + 10 == carRace.bots[i].getX() && ((y >= carRace.bots[i].getY()) || (y <= carRace.bots[i].getY() + 10)))
                        || (x + 10 == carRace.bots[i].getX() && ((y + 10 >= carRace.bots[i].getY()) || (y + 10 <= carRace.bots[i].getY() + 10)))
                        || (y == carRace.bots[i].getY() + 10 && (x >= carRace.bots[i].getX() || x <= carRace.bots[i].getX() + 10))
                        || (y == carRace.bots[i].getY() + 10 && (x + 10 >= carRace.bots[i].getX() || x + 10 <= carRace.bots[i].getX() + 10))
                        || (y + 10 == carRace.bots[i].getY() && (x >= carRace.bots[i].getX() || x <= carRace.bots[i].getX() + 10))
                        || (y + 10 == carRace.bots[i].getY() && (x + 10 >= carRace.bots[i].getX() || x + 10 <= carRace.bots[i].getX() + 10))) {
                    crashed = true;
                    carRace.bots[i].crashed = true;   //bekleme bunda dahil mi bilmiyorum
                }

                 */
            }
            for (int i = 0; i < 2; i++) {
                if((x>=carRace.players[i].getX() && x<=carRace.players[i].getX()+10 &&
                        y>=carRace.players[i].getY() && y<=carRace.players[i].getY()+10)
                        || (x+10>=carRace.players[i].getX() && x+10<=carRace.players[i].getX()+10 &&
                        y>=carRace.players[i].getY() && y<=carRace.players[i].getY()+10)
                        ||(x>=carRace.players[i].getX() && x<=carRace.players[i].getX()+10 &&
                        y+10>=carRace.players[i].getY() && y+10<=carRace.players[i].getY()+10)
                        ||(x+10>=carRace.players[i].getX() && x+10<=carRace.players[i].getX()+10 &&
                        y+10>=carRace.players[i].getY() && y+10<=carRace.players[i].getY()+10)){
                    crashed = true;
                    carRace.players[i].crashed = true;
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

                /*
                if ((x == carRace.players[i].getX() + 10 && ((y >= carRace.players[i].getY()) || (y <= carRace.players[i].getY() + 10)))
                        || (x == carRace.players[i].getX() + 10 && ((y + 10 >= carRace.players[i].getY()) || (y + 10 <= carRace.players[i].getY() + 10)))
                        || (x + 10 == carRace.players[i].getX() && ((y >= carRace.players[i].getY()) || (y <= carRace.players[i].getY() + 10)))
                        || (x + 10 == carRace.players[i].getX() && ((y + 10 >= carRace.players[i].getY()) || (y + 10 <= carRace.players[i].getY() + 10)))
                        || (y == carRace.players[i].getY() + 10 && (x >= carRace.players[i].getX() || x <= carRace.players[i].getX() + 10))
                        || (y == carRace.players[i].getY() + 10 && (x + 10 >= carRace.players[i].getX() || x + 10 <= carRace.players[i].getX() + 10))
                        || (y + 10 == carRace.players[i].getY() && (x >= carRace.players[i].getX() || x <= carRace.players[i].getX() + 10))
                        || (y + 10 == carRace.players[i].getY() && (x + 10 >= carRace.players[i].getX() || x + 10 <= carRace.players[i].getX() + 10))) {
                    crashed = true;
                    carRace.players[i].crashed = true;   //bekleme bunda dahil mi bilmiyorum
                }
                */
            }
            region2=checkRegion2();
            double distance;
            double distance2;
            switch (region2){
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
                    if(distance>=356){
                        crashed = true;
                        x-=5;
                        y+=5;
                    }
                    if(distance2<=203){
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
                    if(distance2<=198){
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
                    if(distance2<=196){
                        crashed = true;
                        x-=5;
                        y+=5;
                    }
                    break;
            }

            if (crashed == true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                crashed = false;
            }


        }
    }
    //TO PICK A DIRECTION
    public int checkRegion(){
        if(x>=27 && x<=290 && y<=585 && y>=195){
            return 1;
        }
        else if(x>=85 && x<671 && y>=40 && y<195){
            return 2;
        }
        else if(x>=465 && x<=728 && y<=585 && y>=195){
            return 3;
        }
        else {
            return 4;
        }
    }

    //TO CHECK WHETHER IN THE RACE TRACK
    public int checkRegion2(){
        if(x<=370 && y<=390){
            return 1;
        }
        else if(x>370 && y<=390){
            return 2;
        }
        else if(x>370){
            return 3;
        }
        else return 4;
    }

}
