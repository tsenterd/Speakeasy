package Main;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import src.edu.cmu.sphinx.demo.helloworld.WordRecognizer;

/**
 *  The main JFrame used for the game. All the menu items are located here, and everything comes back to this screen.
 *
 * @author David, Alex
 */
public class GameFrame extends JFrame implements ActionListener
{
    /**
     *  The menu instance variable
     */
    
    public MainMenu mainMenu;
    /**
     *  The game panel instance variable
     */
    public GamePanel gamePanel;
    static JMenuBar myMenus;
    /**
     * The window that pops up due to error messages
     */
    public JWindow g;
    /**
     *  
     */
    public JTextField inputField;
  String userName;
  boolean gotName;
  static JDialog d;
  
    /**
     *
     */
    public GameFrame()
  {
    super("SpeakEasy - An English teaching maze game");
    this.mainMenu = new MainMenu(this);
    JMenuItem quitItem = new JMenuItem("Quit (F3)");
    JMenuItem helpItem = new JMenuItem("Help (F1)");
    JMenuItem aboutItem = new JMenuItem("About (F2)");
    
    JMenu fileMenu = new JMenu("File");
    JMenu helpMenu = new JMenu("Help");
    
    fileMenu.add(quitItem);
    helpMenu.add(helpItem);
    helpMenu.add(aboutItem);
    
    myMenus = new JMenuBar();
    
    myMenus.add(fileMenu);
    myMenus.add(helpMenu);
    
    setJMenuBar(myMenus);
    
    quitItem.addActionListener(this);
    helpItem.addActionListener(this);
    aboutItem.addActionListener(this);
    
    setDefaultCloseOperation(3);
    setContentPane(this.mainMenu);
    
    setResizable(false);
    pack();
    setVisible(true);
  }
  
    /**
     *
     */
    public void getUserName()
  {
    this.g = new JWindow(this);
    
    JLabel label = new JLabel("Please enter your username");
    this.inputField = new JTextField(24);
    JButton submit = new JButton("Submit");
    

    this.g.add(label);
    this.g.add(this.inputField);
    this.g.add(submit);
    

    submit.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GameFrame.this.userName = GameFrame.this.inputField.getText();
        GameFrame.this.gotName = true;
        GameFrame.this.g.dispose();
      }
    });
    this.g.setLayout(new FlowLayout());
    this.g.pack();
    this.g.setVisible(true);
    this.g.repaint();
  }
  
    /**
     *
     */
    public void highScore()
  {
    ArrayList<String> scores = new ArrayList();
    try
    {
      BufferedReader in = new BufferedReader(new FileReader("Highscores.txt"));
      if (!in.readLine().equals("Speakeasy High Score File")) {
        createFile();
      }
      for (int i = 0; i < 10; i++)
      {
        String nextScore = in.readLine();
        if (nextScore != null) {
          scores.add(nextScore);
        }
      }
      getUserName();
      while (!this.gotName) {
        try
        {
          Thread.sleep(10);
        }
        catch (Exception e) {}
      }
      this.gotName = false;
      
      scores.add(30 - WordRecognizer.numFails + " " + this.userName);
      WordRecognizer.numFails = 0;
      scores = sortScores(scores);
      writeHighScores(scores);
      for (int i = 0; i < scores.size(); i++) {
        System.out.println((String)scores.get(i));
      }
    }
    catch (IOException e)
    {
      createFile();
      highScore();
    }
    catch (NullPointerException e)
    {
      createFile();
      highScore();
    }
  }
  
    /**
     *
     * @param scores
     * @return
     */
    public ArrayList<String> sortScores(ArrayList<String> scores)
  {
    if (scores.size() == 1) {
      return scores;
    }
    for (int j = 1; j < scores.size(); j++)
    {
      String key = (String)scores.get(j);
      for (int i = j - 1; (i >= 0) && (Integer.parseInt(new StringTokenizer((String)scores.get(i)).nextToken()) < Integer.parseInt(new StringTokenizer(key).nextToken())); i--) {
        scores.set(i + 1, scores.get(i));
      }
      scores.set(j + 1, key);
    }
    return scores;
  }
  
    /**
     *
     * @param scores
     */
    public void writeHighScores(ArrayList<String> scores)
  {
    PrintWriter out = null;
    try
    {
      out = new PrintWriter(new FileWriter("Highscores.txt"));
      out.println("Speakeasy High Score File");
      for (int x = 0; x < 10; x++) {
        out.println((String)scores.get(x));
      }
      out.close();
    }
    catch (IOException ex) {}catch (IndexOutOfBoundsException e)
    {
      out.close();
    }
  }
  
    /**
     *
     * @param num
     */
    public void startGame(int num)
  {
    if (num > 3)
    {
      highScore();
      backToMenu(this.gamePanel);
    }
    else
    {
 
      
      this.gamePanel = new GamePanel(num);
      
      setLocation(0, 0);
      setResizable(false);
      
      setSize(485, 505);
      

      setContentPane(this.gamePanel);
      this.gamePanel.updateUI();
      repaint();
    }
  }
  
    /**
     *
     */
    public void levelSelection()
  {
    LevelSelectionPanel lsp = new LevelSelectionPanel(this);
    setContentPane(lsp);
    lsp.updateUI();
  }
  
    /**
     *
     */
    public void createFile()
  {
    try
    {
      PrintWriter out = new PrintWriter(new FileWriter("Highscores.txt"));
      out.write("Speakeasy High Score File");
      out.close();
    }
    catch (IOException e) {}
  }
  
    /**
     *
     * @param name
     */
    public void display(String name)
  {
    JPanel panel;
    if (name.equals("Instructions")) {
      panel = new InstructionPanel(this);
    } else {
      panel = new HighScorePanel(this);
    }
    remove(this.mainMenu);
    this.mainMenu.removeAll();
    setContentPane(panel);
    panel.updateUI();
    repaint();
  }
  
    /**
     *
     * @param panelName
     */
    public void backToMenu(JPanel panelName)
  {
    MainMenu menu = new MainMenu(this);
    remove(panelName);
    setContentPane(menu);
    
    setSize(706, 551);
    repaint();
    menu.updateUI();
    setJMenuBar(myMenus);
  }
  
    /**
     *
     * @param title
     * @param buttonText
     * @param windowSize
     */
    public static void dialog(String title, String buttonText, int windowSize)
  {
    JFrame tmp = new JFrame();
    d = new JDialog(tmp, title);
    d.setSize(windowSize, 100);
    d.setResizable(false);
    d.setLayout(new FlowLayout());
    JLabel label = new JLabel(buttonText);
    label.setFont(new Font("Serif", 0, 16));
    JButton button = new JButton("Close");
    button.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        GameFrame.d.dispose();
      }
    });
    d.add(label);
    d.setLocationRelativeTo(tmp);
    tmp.dispose();
    d.add(button);
    d.setVisible(true);
  }
  
    /**
     *
     */
    public static void quit()
  {
    System.exit(0);
  }
  
    /**
     *
     */
    public static void help()
  {
    try
    {
      Runtime.getRuntime().exec("hh.exe CHM/Speakeasy.chm");
    }
    catch (IOException e)
    {
      System.out.println("nop");
    }
  }
  
    /**
     *
     */
    public static void about()
  {
    dialog("About", "Copyright SSG (SouthSeaGames) inc.", 300);
  }
  
    /**
     *
     * @param ae
     */
    public void actionPerformed(ActionEvent ae)
  {
    if (ae.getActionCommand().equals("Quit (F3)")) {
      quit();
    } else if (ae.getActionCommand().equals("About (F2)")) {
      about();
    } else if (ae.getActionCommand().equals("Help (F1)")) {
      help();
    }
  }
}
