package Main;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;

class SplashWindow
  extends JWindow
{
  public SplashWindow(JFrame f)
  {
    super(f);
    
    ImageIcon logo = new ImageIcon("Art/Logo.png");
    JLabel l = new JLabel(logo);
    getContentPane().add(l);
    

    setSize(logo.getIconWidth(), logo.getIconHeight());
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screenSize.width - getSize().width) / 2;
    int y = (screenSize.height - getSize().height) / 2;
    setLocation(x, y);
    setVisible(true);
    try
    {
      Thread.sleep(2000L);
    }
    catch (InterruptedException sd) {}
    dispose();
  }
}
