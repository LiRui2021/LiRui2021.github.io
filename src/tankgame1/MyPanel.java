package tankgame1;

import javax.swing.*;
import java.awt.*;

//坦克大战的绘图区域
public class MyPanel extends JPanel {
    //定义我的坦克
    Hero hero = null;
    public MyPanel() {
        hero = new Hero(100, 100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);
        drawTank(hero.getX(), hero.getY(), g,0,0);
    }
    //编写方法，画出坦克

    /**
     *
     * @param x 坦克的左上角x坐标
     * @param y  左上角y
     * @param g  画笔
     * @param direct  坦克方向
     * @param type   坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type){
      switch(type){
          case 0: //我们的坦克
              g.setColor(Color.cyan);//青色
              break;
          case 1:
              g.setColor(Color.yellow);
              break;
      }
      switch(direct){
          case 0:
              g.fill3DRect(x, y,10,60,false);
              g.fill3DRect(x + 30, y,10,60,false);
              g.fill3DRect(x + 10, y + 10, 20 ,40, false);
              g.fillOval(x+10,y+20,20,20);//画出圆形盖子
              g.drawLine(x + 20,y+30,x+20,y);
              break;
          default:
              System.out.println("暂时没有处理");
      }
    }
}
