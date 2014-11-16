package Main;


import java.util.logging.Level;
import java.util.logging.Logger;
import src.edu.cmu.sphinx.demo.helloworld.VoiceRecognition;
import src.edu.cmu.sphinx.demo.helloworld.WordRecognizer;

/**
 *
 * @author DavidPC
 */
public class Player
  extends GameObj
{
  static boolean firstTime = true;
    /**
     *
     */
    public boolean up;
    /**
     *
     */
    public boolean down;
    /**
     *
     */
    public boolean right;
    /**
     *
     */
    public boolean left;
 
    /**
     *
     * @param xRow
     * @param yCol
     */
    public Player(int xRow, int yCol)
  {
    super(xRow, yCol, "Player.png");
  }
  
    /**
     *
     * @param directional
     */
    public void altarHandle(GameObj directional) 
  {
    if ((directional instanceof Door))
    {
      GamePanel.running = false;
      
      VoiceRecognition.teachPhrase(((Door)directional).getPhrase(), firstTime);
      firstTime = false;

     while (!WordRecognizer.correctInput){
          try {
              Thread.sleep(10);
          } catch (InterruptedException ex) {  
          }
     }
      WordRecognizer.correctInput = false;

      directional.remove();
      GamePanel.running = true;
   
    }
  }
  
    /**
     *
     */
    public void update()
  {
    if ((GamePanel.world[getCol()][getRow()] instanceof EndPoint))
    {
        System.out.println(GamePanel.level);
      GamePanel.finished = true;
      Speakeasy.game.startGame(GamePanel.level + 1);
    }
    if (this.up)
    {
      this.up = false;
      GameObj directional = GamePanel.world[getCol()][(getRow() - 1)];
      if (!(directional instanceof Wall))
      {
        altarHandle(directional);
        
        setRow(getRow() - 1);
      }
    }
    if (this.down)
    {
      this.down = false;
      GameObj directional = GamePanel.world[getCol()][(getRow() + 1)];
      if (!(directional instanceof Wall))
      {
        altarHandle(directional);
        
        setRow(getRow() + 1);
      }
    }
    if (this.right)
    {
      this.right = false;
      GameObj directional = GamePanel.world[(getCol() + 1)][getRow()];
      if (!(directional instanceof Wall))
      {
        altarHandle(directional);
        
        setCol(getCol() + 1);
      }
    }
    if (this.left)
    {
      this.left = false;
      GameObj directional = GamePanel.world[(getCol() - 1)][getRow()];
      if (!(directional instanceof Wall))
      {
        altarHandle(directional);
        
        setCol(getCol() - 1);
      }
    }
  }
}
