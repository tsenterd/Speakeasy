package Main;


import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author DavidPC
 */
public class HighScorePanel
  extends JPanel
  implements ActionListener
{
  private GameFrame container;
  JButton menu;
  String scores = "<html>";
  
    /**
     *
     * @param container
     */
    public HighScorePanel(GameFrame container)
  {
    FlowLayout fl = new FlowLayout();
    this.container = container;
    setLayout(fl);
    
    this.menu = new JButton("Menu");
    this.menu.addActionListener(this);
    fl.setHgap(100);
    fl.setVgap(150);
    try
    {
      add(drawScores());
    }
    catch (NullPointerException e)
    {
      add(new JLabel("No Scores created"));
    }
    add(this.menu);
    updateUI();
    setVisible(true);
  }
  
    /**
     *
     * @return
     */
    public JLabel drawScores()
  {
    JLabel label = null;
    try
    {
      BufferedReader in = new BufferedReader(new FileReader("Highscores.txt"));
      if (in.readLine().equals("Speakeasy High Score File"))
      {
        this.scores += "Score Username <br>";
        for (int x = 1; x < 10; x++)
        {
          String tmp = in.readLine();
          if (tmp != null) {
            this.scores = (this.scores + tmp + "<br>");
          }
        }
        this.scores += "</html>";
        label = new JLabel(this.scores);
        label.setFont(new Font("Arial", 1, 18));
        return label;
      }
    }
    catch (IOException ex) {}catch (NullPointerException e) {}
    return null;
  }
  
    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("Menu")) {
      this.container.backToMenu(this);
    }
  }
}
