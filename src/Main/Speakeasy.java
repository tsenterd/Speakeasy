package Main;


import javax.swing.JFrame;

/**
 *
 * @author DavidPC
 */
public class Speakeasy
{
  static GameFrame game;
  
    /**
     *
     * @param args
     */
    public static void main(String[] args)
  {
    JFrame s = new JFrame();
    new SplashWindow(s);
    s.dispose();
    game = new GameFrame();
  }
}
