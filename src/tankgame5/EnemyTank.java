package tankgame5;

import java.util.Vector;
@SuppressWarnings({"all"})
public class EnemyTank extends Tank implements Runnable{
    boolean isLive = true;
    Vector<Shot> shots = new Vector<Shot>();
    public EnemyTank(int x, int y) {
        super(x, y);
    }
    Shot shot = null;
    @Override
    public void run() {
        while(true){
            if(isLive && shots.size() < 3){
                switch(getDirect()){
                    case 0:
                        shot = new Shot(getX() + 20, getY(),0);
                        break;
                    case 1:
                        shot = new Shot(getX() + 60, getY() + 20,1);
                        break;
                    case 2:
                        shot = new Shot(getX() + 20, getY() + 60,2);
                        break;
                    case 3:
                        shot = new Shot(getX(),getY() + 20,3);
                        break;
                }
                shots.add(shot);
                new Thread(shot).start();
            }
            switch(getDirect()){
                case 0:
                    for (int i = 0; i < 30; i++) {
                        moveUp();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        moveRight();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        moveDown();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        moveLeft();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            setDirect((int)(Math.random() * 4));
            if(!isLive){
                break;
            }
        }
    }
}
