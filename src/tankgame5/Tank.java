package tankgame5;

public class Tank {
    private int x;//坦克的横坐标
    private int y;//坦克的竖坐标
    private int direct; //0上 1右 2下 3左;
    private int speed = 1;
    boolean isLive = true;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //上又下移左移动方法
    public void moveUp(){
        if(y > 0) {
            y -= speed;
        }
    }
    public void moveDown(){
        if(y + 60 < 750){
        y += speed;
        }
    }
    public void moveLeft() {
        if(x > 0)
        x -= speed;
    }
    public void moveRight(){
        if(x + 60 < 1000) {
            x += speed;
        }
    }
    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
