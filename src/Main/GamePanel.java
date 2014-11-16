package Main;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author DavidPC
 */
public class GamePanel
  extends JPanel
  implements Runnable, KeyListener
{
    /**
     *
     */
    public static GameObj[][] world;
  static boolean finished;
    /**
     *
     */
    public static final int PANEL_WIDTH = 480;
  static int level;
    /**
     *
     */
    public static int PANEL_HEIGHT = 480;
  private Thread thread;
  private BufferedImage image;
  private Graphics2D g;
 
  static boolean running;
    /**
     *
     */
    public static final int GRIDCOEFF = 24;
  private Player player;
  BufferedImage background;
  
    /**
     *
     * @param level
     */
    public GamePanel(int level)
  {
        finished = false;
    world = new GameObj[20][20];
    this.level = level;
    
    readWorld(level);
    try
    {
      this.background = ImageIO.read(new FileInputStream("Art/Floor.png"));
    }
    catch (IOException e)
    {
      System.out.println("Error, please reinstall files.Floor.png");
    }
    setPreferredSize(new Dimension(480, PANEL_HEIGHT));
    setFocusable(true);
    requestFocus();
  }
  
    /**
     *
     */
    public void addNotify()
  {
    super.addNotify();
    running = true;
    if (this.thread == null)
    {
      this.thread = new Thread(this);
      this.thread.start();
    }
    requestFocus();
    addKeyListener(this);
  }
  
    /**
     *
     * @param level
     */
    public void readWorld(int level)
  {
    String s = "";
    int altarCount = 1 + 10 * (level - 1);
    try
    {
      BufferedReader r = new BufferedReader(new FileReader("Levels/level" + level + ".level"));
      for (int row = 0; row < 20; row++)
      {
        s = r.readLine();
        for (int col = 0; col < 20; col++) {
          switch (s.charAt(col))
          {
          case '1': 
            new Wall(row, col);
            break;
          case '3': 
            new Door(row, col, altarCount);
            altarCount++;
            break;
          case '2': 
            this.player = new Player(row, col);
            break;
          case '4': 
            new EndPoint(row, col);
          }
        }
      }
      r.close();
    }
    catch (FileNotFoundException ex)
    {
      System.out.println("Level files not found, please restart");
    }
    catch (IOException ex)
    {
      System.out.println("Error reading level files");
    }
  }
  
    /**
     *
     */
    @Override
    public void run()
  {
    running = true;
   

    this.image = new BufferedImage(480, PANEL_HEIGHT, 1);
    
    this.g = ((Graphics2D)this.image.getGraphics());
    


    int n = 0;
    while (!finished)
    {
      long startTime = System.nanoTime();
      



      gameUpdate();
      
      gameRender();
      
      gameDraw();
      
      long waitTime = 16L - (System.nanoTime() - startTime) / 1000000L;
      if (waitTime >= 0L) {
        try
        {
          Thread.sleep(waitTime);
        }
        catch (InterruptedException e) {}
      }
    }
  }
  
  private void gameUpdate()
  {
    if (running) {
      this.player.update();
    }
  }
  
  private void gameRender()
  {
    this.g.setColor(Color.WHITE);
    
    this.g.drawImage(this.background, 0, 0, null);
    for (int x = 0; x < 20; x++) {
      for (int y = 0; y < 20; y++) {
        if (world[x][y] != null) {
          world[x][y].draw(this.g);
        }
      }
    }
    if (!running)
    {
      this.g.setColor(Color.RED);
      this.g.setFont(new Font("Arial", 0, 48));
      this.g.drawString("Paused", 100, 100);
    }
  }
  
  private void gameDraw()
  {
    try
    {
      Graphics g2 = getGraphics();
      
      g2.drawImage(this.image, 0, 0, null);
      
      g2.dispose();
    }
    catch (NullPointerException e) {}
  }
  
    /**
     *
     * @param e
     */
    public void keyTyped(KeyEvent e) {}
  
    /**
     *
     * @param e
     */
    public void keyPressed(KeyEvent e)
  {
    int keyCode = e.getKeyCode();
    if (running) {
      switch (keyCode)
      {
      case 38: 
        this.player.up = true;
        break;
      case 40: 
        this.player.down = true;
        break;
      case 37: 
        this.player.left = true;
        break;
      case 39: 
        this.player.right = true;
      }
    }
    if (keyCode == 80) {
      if (running) {
        running = false;
      } else {
        running = true;
      }
    }
  }
  
    /**
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {}
}
