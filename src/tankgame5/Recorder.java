package tankgame5;

import java.io.*;
import java.util.Vector;

public class Recorder {
    private static int allEnemyTank = 0;
    private static FileWriter fw = null;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "src\\myRecord.txt";
    private static Vector<Node> nd = new Vector();
    private static Vector<EnemyTank> enemyTanks = null;

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }
 public static Vector<Node> readRecord(){
     try {
         br = new BufferedReader(new FileReader(recordFile));
        String date = br.readLine();
        allEnemyTank = Integer.parseInt(date);
         while(date != null){
              date = br.readLine();
             String[] s = date.split(" ");
             Node node = new Node(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
             nd.add(node);
         }
     } catch (IOException e) {
         e.printStackTrace();
     }finally{
         if(br != null){
             try {
                 br.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         return nd;

     }
 }
    public static void keepRecord(){
        try {
           bw = new BufferedWriter(new FileWriter(recordFile));
           bw.write(allEnemyTank + "\n");
           for(int i = 0; i < enemyTanks.size(); i++){
               EnemyTank enemyTank = enemyTanks.get(i);
               if(enemyTank != null){
                   bw.write(enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect() + "\n");
               }
           }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void setAllEnemyTank(int allEnemyTank) {
        Recorder.allEnemyTank = allEnemyTank;
    }

    public static int getAllEnemyTank() {
        return allEnemyTank;
    }
    public static void addAllEnemyTank() {
        allEnemyTank++;
    }
}
