package Main;



import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author DavidPC
 */
public class MainMenu
  extends JPanel
  implements ActionListener, KeyListener
{
  private GameFrame container;
  JButton playGame;
  JButton instructionsButton;
  JButton hScoresButton;
  JButton quitButton;
  Image background;
  
    /**
     *
     * @param container
     */
    public MainMenu(GameFrame container)
  {
    this.container = container;
    FlowLayout fl = new FlowLayout();
    setPreferredSize(new Dimension(700, 500));
    setLayout(fl);
    this.background = new ImageIcon("Art/MenuBG.png").getImage();
    this.playGame = new JButton("Play Game");
    this.playGame.addActionListener(this);
    this.instructionsButton = new JButton("Instructions");
    this.instructionsButton.addActionListener(this);
    this.hScoresButton = new JButton("High Scores");
    this.hScoresButton.addActionListener(this);
    this.quitButton = new JButton("Quit");
    this.quitButton.addActionListener(this);
    fl.setHgap(500);
    fl.setVgap(70);
    add(this.playGame);
    add(this.instructionsButton);
    add(this.hScoresButton);
    add(this.quitButton);
  }
  
    /**
     *
     */
    public void addNotify()
  {
    super.addNotify();
    requestFocus();
    addKeyListener(this);
  }
  
    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e)
  {
    this.container.setJMenuBar(null);
    this.container.repaint();
    if (e.getActionCommand().equals("Play Game"))
    {
      removeAll();
      this.container.levelSelection();
    }
    else if (e.getActionCommand().equals("Instructions"))
    {
      removeAll();
      this.container.display("Instructions");
    }
    else if (e.getActionCommand().equals("High Scores"))
    {
      removeAll();
      this.container.display("HighScores");
    }
    else if (e.getActionCommand().equals("Quit"))
    {
      System.exit(0);
    }
  }
  
    /**
     *
     * @param ke
     */
    public void keyTyped(KeyEvent ke) {}
  
    /**
     *
     * @param ke
     */
    public void keyPressed(KeyEvent ke)
  {
    int keyCode = ke.getKeyCode();
    switch (keyCode)
    {
    case 112: 
      GameFrame.help();
      break;
    case 113: 
      GameFrame.about();
      break;
    case 114: 
      GameFrame.quit();
    }
  }
  
    /**
     *
     * @param ke
     */
    public void keyReleased(KeyEvent ke) {}
  
    /**
     *
     * @param g
     */
    public void paint(Graphics g)
  {
    super.paint(g);
    Graphics2D g2d = (Graphics2D)g;
    g2d.drawImage(this.background, 0, 0, null);
  }
}
