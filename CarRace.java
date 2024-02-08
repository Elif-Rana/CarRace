import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.locks.Lock;

public class CarRace extends JFrame {
    RaceTrack background;
    Player[] players;
    Bot[] bots;
    Chronometer chronometer;

    boolean gameContinue;
    int time;

    //for popup
    String winner;

    double centerX;
    double centerY;
    double outerCircleRadius;
    double innerCircleRadius;

    Lock lock;


    public CarRace() {
        gameContinue=true;
        players = new Player[2];
        for (int i = 0; i < 2; i++) {
            Player player = new Player(this, i);
            players[i] = player;
        }
        bots = new Bot[5];
        for (int i = 0; i < 5; i++) {
            Bot bot = new Bot(this, i);
            bots[i] = bot;
        }
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); ////

        background = new RaceTrack();
        add(background);
        //background.setBackground(Color.lightGray);

        chronometer=new Chronometer();
        Thread chorono = new Thread(chronometer);
        chorono.start();
        background.add(chronometer);

        //addWindowListener(new GameOver());
        addKeyListener(new ForKey());

        centerX = 370;
        centerY=390;
        outerCircleRadius=350;
        innerCircleRadius=200;

        setResizable(false);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < 2; i++) {
            g.setColor(this.players[i].getColor());
            g.fillRect(this.players[i].getX(), this.players[i].getY(), 10, 10);
        }
        for (int i = 0; i < 5; i++) {
            g.setColor(this.bots[i].getColor());
            g.fillRect(this.bots[i].getX(), this.bots[i].getY(), 10, 10);

        }
    }

    public void game(int n) {
        time=1000/n;
        for (int i=0;i<2;i++){
            players[i].start();
        }
        for (int i=0;i<5;i++){
            bots[i].start();
            // repaint();
        }
        while(gameContinue){
            for (int i=0;i<2;i++){
                if(players[i].won){
                    winner=players[i].getStatus();
                    new PopUp();
                    gameContinue=false;
                }
            }
            for (int i=0;i<5;i++){
                if(bots[i].won){
                    winner=bots[i].getStatus();
                    new PopUp();
                    gameContinue=false;
                }
            }
        }
    }

    //FOR THE CIRCLES AND START LINE
    class RaceTrack extends JLabel{
        public RaceTrack(){
            setSize(800, 800);
            setBackground(Color.lightGray);
            setVisible(true);
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.drawOval(20, 10, 700, 700);
            g.drawOval(170, 160, 400, 400);
            g.drawLine(20, 360, 170, 360);

        }
    }

    //POPUP
    class GameOver implements WindowListener{

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            new PopUp();
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }
    class PopUp extends JFrame implements WindowListener{
        JLabel labelOfPop;
        public PopUp() {
            setSize(230, 170);
            setLocation(290,300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            addWindowListener(this);

            JPanel panelForLabel=new JPanel();
            labelOfPop = new JLabel();
            labelOfPop.setText(winner + " Kazandi! Suresi " + chronometer.getText());
            panelForLabel.add(labelOfPop);
            add(panelForLabel);

            setVisible(true);
        }

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            dispose();
            System.exit(0);
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }

    //Chronometer
    class Chronometer extends JLabel implements Runnable{
        int splitSec;
        int sec;
        int min;
        public Chronometer() {
            setBounds(20,20,80,20);
            splitSec = 0;
            sec = 0;
            min = 0;
            setText("00:00:00");
            setVisible(true);

        }

        @Override
        public void run() {
            String s="";
            String minute="";
            String second="";
            String splitSecond="";
            while (gameContinue) {
                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (splitSec != 59) {
                    splitSec++;
                } else {
                    splitSec = 0;
                    if (sec != 59) sec++;
                    else {
                        sec = 0;
                        min++;
                    }
                }

                if (min < 10){
                    minute="0"+min;
                }
                else minute=""+min;
                if (sec < 10){
                    second="0"+sec;
                }
                else second=""+sec;
                if (splitSec < 10){
                    splitSecond="0"+splitSec;
                }
                else splitSecond=""+splitSec;

                setText(minute+":"+second+":"+splitSec);
            }
            if(!gameContinue){
                if(splitSec-2>=0){
                    setText(minute+":"+second+":"+(splitSec-2));
                }
                else{
                    ////////////////////////////////////////////////////////7
                }
            }
        }
    }

    //TO TAKE INPUT FROM KEYBOARD
    class ForKey implements KeyListener {
        private long time1=-1;
        private long time2=-1;
        private long time3=-1;
        private long time4=-1;

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

            switch (e.getKeyCode()) {
                //left
                case 65://A,a
                    /*
                    if(players[0].crashed){
                        lock.lock();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        players[0].crashed=false;
                        lock.unlock();
                    }

                    */
                    if(players[0].ableToMove) {
                        if (time1 < 0) {
                            time1 = System.currentTimeMillis();
                        } else if (time2 < 0) {
                            time2 = System.currentTimeMillis();
                            if (time2 - time1 <= time/30) {
                                time2 = -1;
                                break;
                                ////////////////////
                            }
                        } else {
                            players[0].pressed = true;
                            players[0].setX(players[0].getX() - 2);
                            time1 = time2;
                            time2 = -1;

                        }
                    }
                    break;
                //right
                case 68://D,d
                    if(players[0].ableToMove) {
                        if (time1 < 0) {
                            time1 = System.currentTimeMillis();
                        } else if (time2 < 0) {
                            time2 = System.currentTimeMillis();
                            if (time2 - time1 <= time/30) {
                                time2 = -1;
                                break;
                                ////////////////////
                            }
                        } else {
                            players[0].pressed = true;
                            players[0].setX(players[0].getX() + 2);
                            time1 = time2;
                            time2 = -1;
                        }
                    }
                    break;
                //up
                case 87://W,w
                    if(players[0].ableToMove) {
                        if (time1 < 0) {
                            time1 = System.currentTimeMillis();
                        } else if (time2 < 0) {
                            time2 = System.currentTimeMillis();
                            if (time2 - time1 <= time/30) {
                                time2 = -1;
                                break;
                                ////////////////////
                            }
                        } else {
                            players[0].pressed = true;
                            players[0].setY(players[0].getY() - 2);
                            time1 = time2;
                            time2 = -1;
                        }
                    }
                    break;
                //down
                case 83://S,s
                    if(players[0].ableToMove) {
                        if (time1 < 0) {
                            time1 = System.currentTimeMillis();
                        } else if (time2 < 0) {
                            time2 = System.currentTimeMillis();
                            if (time2 - time1 <= time/30) {
                                time2 = -1;
                                break;
                                ////////////////////
                            }
                        } else {
                            players[0].pressed = true;
                            players[0].setY(players[0].getY() + 2);
                            time1 = time2;
                            time2 = -1;
                        }
                    }
                    break;

                //left
                case 37://<-
                    if(players[1].ableToMove) {
                        if (time3 < 0) {
                            time3 = System.currentTimeMillis();
                        } else if (time4 < 0) {
                            time4 = System.currentTimeMillis();
                            if (time4 - time3 <= time/30) {
                                time4 = -1;
                                break;
                                ////////////////////
                            }
                        } else {
                            players[1].pressed = true;
                            players[1].setX(players[1].getX() - 2);
                            time3 = time4;
                            time4 = -1;
                        }
                    }
                    break;
                //right
                case 39://->
                    if(players[1].ableToMove) {
                        if (time3 < 0) {
                            time3 = System.currentTimeMillis();
                        } else if (time4 < 0) {
                            time4 = System.currentTimeMillis();
                            if (time4 - time3 <= time/30) {
                                time4 = -1;
                                break;
                                ////////////////////
                            }
                        } else {
                            players[1].pressed = true;
                            players[1].setX(players[1].getX() + 2);
                            time3 = time4;
                            time4 = -1;
                        }
                    }
                    break;
                //up
                case 38:
                    if(players[1].ableToMove) {
                        if (time3 < 0) {
                            time3 = System.currentTimeMillis();
                        } else if (time4 < 0) {
                            time4 = System.currentTimeMillis();
                            if (time4 - time3 <= time/30) {
                                time4 = -1;
                                break;
                                ////////////////////
                            }
                        } else {
                            players[1].pressed = true;
                            players[1].setY(players[1].getY() - 2);
                            time3 = time4;
                            time4 = -1;
                        }
                    }
                    break;
                //down
                case 40:
                    if(players[1].ableToMove) {
                        if (time3 < 0) {
                            time3 = System.currentTimeMillis();
                        } else if (time4 < 0) {
                            time4 = System.currentTimeMillis();
                            if (time4 - time3 <= time/30) {
                                time4 = -1;
                                break;
                                ////////////////////
                            }
                        } else {
                            players[1].pressed = true;
                            players[1].setY(players[1].getY() + 2);
                            time3 = time4;
                            time4 = -1;

                        }
                    }
                    break;
            }
            repaint();
/*
            for(int i=0;i<5;i++){
                if((players[0].getX()== bots[i].getX()+10 && ((players[0].getY()>=bots[i].getY()) ||(players[0].getY()<=bots[i].getY()+10)))
                || (players[0].getX()== bots[i].getX()+10 && ((players[0].getY()+10>=bots[i].getY()) ||(players[0].getY()+10<=bots[i].getY()+10)))
                ||(players[0].getX()+10== bots[i].getX() && ((players[0].getY()>=bots[i].getY()) ||(players[0].getY()<=bots[i].getY()+10)))
                ||(players[0].getX()+10== bots[i].getX() && ((players[0].getY()+10>=bots[i].getY()) ||(players[0].getY()+10<=bots[i].getY()+10)))
                ||(players[0].getY()==bots[i].getY()+10 &&(players[0].getX()>=bots[i].getX()||players[0].getX()<=bots[i].getX()+10))
                ||(players[0].getY()==bots[i].getY()+10 &&(players[0].getX()+10>=bots[i].getX()||players[0].getX()+10<=bots[i].getX()+10))
                ||(players[0].getY()+10==bots[i].getY() &&(players[0].getX()>=bots[i].getX()||players[0].getX()<=bots[i].getX()+10))
                ||(players[0].getY()+10==bots[i].getY() &&(players[0].getX()+10>=bots[i].getX()||players[0].getX()+10<=bots[i].getX()+10))){
                    players[0].crashed=true;
                    bots[i].crashed=true;

                }
                if((players[1].getX()== bots[i].getX()+10 && ((players[1].getY()>=bots[i].getY()) ||(players[1].getY()<=bots[i].getY()+10)))
                        ||(players[1].getX()== bots[i].getX()+10 && ((players[1].getY()+10>=bots[i].getY()) ||(players[1].getY()+10<=bots[i].getY()+10)))
                        ||(players[1].getX()+10== bots[i].getX() && ((players[1].getY()>=bots[i].getY()) ||(players[1].getY()<=bots[i].getY()+10)))
                        ||(players[1].getX()+10== bots[i].getX() && ((players[1].getY()+10>=bots[i].getY()) ||(players[1].getY()+10<=bots[i].getY()+10)))
                        ||(players[1].getY()==bots[i].getY()+10 &&(players[1].getX()>=bots[i].getX()||players[1].getX()<=bots[i].getX()+10))
                        ||(players[1].getY()==bots[i].getY()+10 &&(players[1].getX()+10>=bots[i].getX()||players[1].getX()+10<=bots[i].getX()+10))
                        ||(players[1].getY()+10==bots[i].getY() &&(players[1].getX()>=bots[i].getX()||players[1].getX()<=bots[i].getX()+10))
                        ||(players[1].getY()+10==bots[i].getY() &&(players[1].getX()+10>=bots[i].getX()||players[1].getX()+10<=bots[i].getX()+10))){
                    players[1].crashed=true;
                    bots[i].crashed=true;

                }
            }
            if((players[1].getX()== players[0].getX()+10 && ((players[1].getY()>=players[0].getY()) ||(players[1].getY()<=players[0].getY()+10)))
                    ||(players[1].getX()== players[0].getX()+10 && ((players[1].getY()+10>=players[0].getY()) ||(players[1].getY()+10<=players[0].getY()+10)))
                    ||(players[1].getX()+10== players[0].getX() && ((players[1].getY()>=players[0].getY()) ||(players[1].getY()<=players[0].getY()+10)))
                    ||(players[1].getX()+10== players[0].getX() && ((players[1].getY()+10>=players[0].getY()) ||(players[1].getY()+10<=players[0].getY()+10)))
                    ||(players[0].getY()==players[1].getY()+10 &&(players[0].getX()>=players[1].getX()||players[0].getX()<=players[1].getX()+10))
                    ||(players[0].getY()==players[1].getY()+10 &&(players[0].getX()+10>=players[1].getX()||players[0].getX()+10<=players[1].getX()+10))
                    ||(players[0].getY()+10==players[1].getY() &&(players[0].getX()>=players[1].getX()||players[0].getX()<=players[1].getX()+10))
                    ||(players[0].getY()+10==players[1].getY() &&(players[0].getX()+10>=players[1].getX()||players[0].getX()+10<=players[1].getX()+10))){
                players[1].crashed=true;
                players[0].crashed=true;


            }

 */




            for(int i=0;i<2;i++){
                /*
                if(players[i].getY()>=389 && players[i].getY()<=391 && players[i].getX()>=28 && players[i].getX()<=168){
                    if(!players[i].lastQuarter){
                        players[i].startLine= !players[i].startLine;
                        System.out.println("starto");
                    }
                }

                 */
                if(players[i].getY()>=40 && players[i].getY()<=180 && players[i].getX()>=387 && players[i].getX()<=389){
                    if(players[i].startLine){
                        players[i].firstQuarter=true;
                    }
                }
                if(players[i].getY()>=590 && players[i].getY()<=730 && players[i].getX()>=387 && players[i].getX()<=389){
                    if(players[i].firstQuarter){
                        players[i].lastQuarter=true;
                    }
                }
                if(players[i].getY()>=390 && players[i].getY()<=392 && players[i].getX()>=28 && players[i].getX()<=168){
                    if(players[i].lastQuarter){
                        players[i].finishLine=true;
                    }
                }
                if(players[i].finishLine){
                    players[i].won=true;
                    //new PopUp(); gamein icinde, calisiyor mu kontrol et
                }
            }
        }

    }

}
