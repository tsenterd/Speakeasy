package Main;


import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author DavidPC
 */
public class InstructionPanel
  extends JPanel
  implements ActionListener
{
  private GameFrame container;
  JButton print;
  JButton menu;
  Image instructions;
  
    /**
     *
     * @param container
     */
    public InstructionPanel(GameFrame container)
  {
    FlowLayout fl = new FlowLayout();
    this.container = container;
    setLayout(fl);
    
    this.instructions = new ImageIcon("Art/Instructions.png").getImage();
    

    this.print = new JButton("Print");
    this.print.addActionListener(this);
    this.menu = new JButton("Menu");
    this.menu.addActionListener(this);
    fl.setHgap(200);
    fl.setVgap(450);
    add(this.print);
    add(this.menu);
    
    updateUI();
    setVisible(true);
  }
  
    /**
     *
     * @param g
     */
    public void paint(Graphics g)
  {
    super.paint(g);
    Graphics2D g2d = (Graphics2D)g;
    g2d.drawImage(this.instructions, 10, 10, null);
  }
  
  private void printToPrinter()
  {
    PrinterJob job = PrinterJob.getPrinterJob();
    job.setPrintable(new Printable()
    {
      public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
        throws PrinterException
      {
        if (pageIndex != 0) {
          return 1;
        }
        graphics.drawImage(InstructionPanel.this.instructions, 100, 100, null);
        return 0;
      }
    });
    boolean doPrint = job.printDialog();
    if (doPrint) {
      try
      {
        job.print();
      }
      catch (PrinterException e) {}
    }
  }
  
    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("Menu")) {
      this.container.backToMenu(this);
    } else if (e.getActionCommand().equals("Print")) {
      printToPrinter();
    }
  }
}
