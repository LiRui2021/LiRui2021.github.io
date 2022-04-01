package tankgame5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
@SuppressWarnings({"all"})
//坦克大战的绘图区域
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    Hero hero = null;
    int enemyTankSize = 3;
    //定义敌人坦克
    Vector<EnemyTank> enemyTanks = new Vector<EnemyTank>();
    Vector<Node> nd = null;
    //定义一个Vector,存放炸弹
    //当子弹击中坦克，出现坦克效果
    Vector<Bomb> bombs = new Vector<Bomb>();
    //定义三张炸弹图片，用于显示炸弹效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    public void showInfo(Graphics g){
      g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("您累积击败坦克数量",1020,30);
        drawTank(1020,60,g,0,0);
        g.setColor(Color.black);
        g.drawString(Recorder.getAllEnemyTank() + "",1080,100);

    }
    public MyPanel(String key) {

        //获取坦克数量
        Recorder.setEnemyTanks(enemyTanks);
        //创建自身坦克对象
        hero = new Hero(500, 500);
        hero.setSpeed(4);
        switch(key){
            case "1":
                for(int i = 0; i < enemyTankSize; i++){
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 100);
                    enemyTank.setDirect(2);
                    //启动地方坦克线程
                    new Thread(enemyTank).start();
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, 2);
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2":
                //读取上局游戏数据
                nd = Recorder.readRecord();
                for(int i = 0; i < nd.size(); i++){
                    EnemyTank enemyTank = new EnemyTank(nd.get(i).getX(), nd.get(i).getY());
                    enemyTank.setDirect(nd.get(i).getDirection());
                    //启动地方坦克线程
                    new Thread(enemyTank).start();
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, 2);
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                break;
        }
        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);
        showInfo(g);
        if(hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }
        //画单颗子弹
//        if(hero.shot != null && hero.shot.isLive() == true ){
//            g.draw3DRect(hero.shot.getX(),hero.shot.getY(),1,1,false);
//        }
        //画多颗子弹
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive() == true) {
                g.draw3DRect(shot.getX(), shot.getY(), 1, 1, false);
            }else{
                hero.shots.remove(shot);
            }
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            //根据当前这个bomb对象的life画出图像
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(bomb.life > 6){
                g.drawImage(image1, bomb.x, bomb.y, 100,100, this);
            }else if(bomb.life > 3){
                g.drawImage(image2, bomb.x, bomb.y, 100,100, this);
            }else{
                g.drawImage(image3, bomb.x, bomb.y, 100,100, this);
            }
                bomb.lifeDown();
            //如果bomb life 为0，就从bombs的集合中删除
            if(bomb.life == 0){
                bombs.remove(bomb);
            }
        }
        for(int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive == true) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.isLive()) {
                        g.draw3DRect(shot.getX(), shot.getY(), 1, 1, false);
                    }else{
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }

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
          case 0: //敌人的坦克
              g.setColor(Color.cyan);//青色
              break;
          case 1://我的坦克
              g.setColor(Color.yellow);
              break;
      }
      //根据坦克的方向绘制不同的形状
      switch(direct){
          case 0:
              g.fill3DRect(x, y,10,60,false);//画出坦克左边轮子
              g.fill3DRect(x+30, y,10,60,false);//画出右边轮子
              g.fill3DRect(x + 10, y + 10, 20 ,40, false);//画出坦克盖子
              g.fillOval(x+10,y+20,20,20);//画出圆形盖子
              g.drawLine(x + 20,y+30,x + 20,y);
              break;
              //direct表示方向：0: 向上 1 向右 2 向下  3 向左

          //向右
           case 1:
              g.fill3DRect(x, y,60,10,false);//画出坦克左边轮子
              g.fill3DRect(x , y+30,60,10,false);//画出右边轮子
              g.fill3DRect(x + 10, y + 10, 40 ,20, false);//画出坦克盖子
              g.fillOval(x+20,y+10,20,20);//画出圆形盖子
              g.drawLine(x + 30,y+20,x+60,y+20);//画出炮筒
              break;
              //向下
          case 2:
              g.fill3DRect(x, y,10,60,false);//画出坦克左边轮子
              g.fill3DRect(x+30, y,10,60,false);//画出右边轮子
              g.fill3DRect(x + 10, y + 10, 20 ,40, false);//画出坦克盖子
              g.fillOval(x+10,y+20,20,20);//画出圆形盖子
              g.drawLine(x + 20,y+30,x + 20,y + 60);
              break;
              //向左
          case 3:
              g.fill3DRect(x, y,60,10,false);//画出坦克左边轮子
              g.fill3DRect(x , y+30,60,10,false);//画出右边轮子
              g.fill3DRect(x + 10, y + 10, 40 ,20, false);//画出坦克盖子
              g.fillOval(x+20,y+10,20,20);//画出圆形盖子
              g.drawLine(x + 30,y+20,x,y+20);//画出炮筒
              break;
          default:
              System.out.println("暂时没有处理");
      }
    }
    public void shotEnemy(){
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if(shot != null && shot.isLive()){
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot,enemyTank);
                }
            }
        }
    }
    public void shotHero(){
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                if(enemyTank.isLive && shot.isLive()){
                    hitTank(shot, hero);
                }
            }

        }
    }
    //写一个方法，判断是否击中敌方坦克
      public void hitTank(Shot s, Tank enemyTank){
            switch (enemyTank.getDirect()){
                case 0:
                case 2:
                    if(s.getX() > enemyTank.getX() && s.getX() < enemyTank.getX() + 40
                    && s.getY() > enemyTank.getY() && s.getY() < enemyTank.getY() + 60){
                        s.setLive(false);
                        enemyTank.isLive = false;
                        //当坦克被击中后，从容器中删除坦克
                        enemyTanks.remove(enemyTank);
                        if(enemyTank instanceof EnemyTank){
                            Recorder.addAllEnemyTank();
                        }
                        //创建Bomb对象，加入到bombs集合
                        Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                        bombs.add(bomb);
                    }
                    break;
                case 1:
                case 3:
                    if(s.getX() > enemyTank.getX() && s.getX() < enemyTank.getX() + 60
                            && s.getY() > enemyTank.getY() && s.getY() < enemyTank.getY() + 40){
                        s.setLive(false);
                        enemyTank.isLive = false;
                        //当坦克被击中后，从容器中删除坦克
                        enemyTanks.remove(enemyTank);
                        if(enemyTank instanceof EnemyTank){
                            Recorder.addAllEnemyTank();
                        }
                        Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                        bombs.add(bomb);
                    }
                    break;
                default:
                    break;
            }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
   //处理wdsa  键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
   if(e.getKeyCode() == KeyEvent.VK_W) {
       hero.setDirect(0);
       hero.moveUp();
   } else if(e.getKeyCode() == KeyEvent.VK_D) {
       hero.setDirect(1);
       hero.moveRight();
   } else if(e.getKeyCode() == KeyEvent.VK_S) {
       hero.setDirect(2);
       hero.moveDown();
   } else if(e.getKeyCode() == KeyEvent.VK_A) {
       hero.setDirect(3);
       hero.moveLeft();
   }
   if(e.getKeyCode() == KeyEvent.VK_J){
//       if(hero.shot == null || !hero.shot.isLive()){
//           hero.shotEnemyTank();
//       }
       hero.shotEnemyTank();

   }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            shotEnemy();
            shotHero();
            this.repaint();
        }
    }
}
