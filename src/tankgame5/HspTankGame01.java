package tankgame5;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class HspTankGame01 extends JFrame {
    //定义MyPaneldd
    MyPanel mp = null;

    public static void main(String[] args){
        HspTankGame01 hspTankGame01 = new HspTankGame01();
    }
    public HspTankGame01(){
        System.out.println("请输入：1.新游戏 2.继续上次游戏");
        Scanner scanner = new Scanner(System.in);
       String key = scanner.next();
        mp = new MyPanel(key);
        new Thread(mp).start();
        this.add(mp);
        this.setSize(1300,950);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                Object
            }
        });
    }
}
