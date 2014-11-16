package Main;



import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author DavidPC
 */
public class LevelSelectionPanel
  extends JPanel
  implements ActionListener
{
  private GameFrame container;
  JButton level1;
  JButton level2;
  JButton level3;
  JButton menu;
  
    /**
     *
     * @param container
     */
  @SuppressWarnings("LeakingThisInConstructor")
    public LevelSelectionPanel(GameFrame container)
  {
    FlowLayout fl = new FlowLayout();
    this.container = container;
    setLayout(fl);
    this.level1 = new JButton("Easy");
    this.level1.addActionListener(this);
    this.level2 = new JButton("Medium");
    this.level2.addActionListener(this);
    this.level3 = new JButton("Hard");
    this.level3.addActionListener(this);
    this.menu = new JButton("Menu");
    this.menu.addActionListener(this);
    fl.setHgap(300);
    fl.setVgap(80);
    add(this.level1);
    add(this.level2);
    add(this.level3);
    add(this.menu);
    updateUI();
    setVisible(true);
  }
  
    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("Easy")) {
      this.container.startGame(1);
    } else if (e.getActionCommand().equals("Medium")) {
      this.container.startGame(2);
    } else if (e.getActionCommand().equals("Hard")) {
      this.container.startGame(3);
    } else if (e.getActionCommand().equals("Menu")) {
      this.container.backToMenu(this);
    }
  }
}
