package Main;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import javax.imageio.ImageIO;

/**
 *
 * @author DavidPC
 */
public class GameObj
{
  private int xRow;
  private int yCol;
    /**
     *
     */
    protected BufferedImage sprite;
  
    /**
     *
     * @param xRow
     * @param yCol
     * @param spriteName
     */
    public GameObj(int xRow, int yCol, String spriteName)
  {
    this.xRow = xRow;
    this.yCol = yCol;
    GamePanel.world[yCol][xRow] = this;
    try
    {
      this.sprite = ImageIO.read(new FileInputStream("Art/" + spriteName));
    }
    catch (IOException e)
    {
      System.out.println("Error, please reinstall files." + spriteName);
    }
  }
  
    /**
     *
     * @return
     */
    public int getRow()
  {
    return this.xRow;
  }
  
    /**
     *
     * @return
     */
    public int getCol()
  {
    return this.yCol;
  }
  
    /**
     *
     * @param xRow
     */
    public void setRow(int xRow)
  {
    this.xRow = xRow;
  }
  
    /**
     *
     * @param yCol
     */
    public void setCol(int yCol)
  {
    this.yCol = yCol;
  }
  
    /**
     *
     * @param g
     */
    public void draw(Graphics2D g)
  {
    g.drawImage(this.sprite, 24 * getCol(), 24 * getRow(), null);
  }
  
    /**
     *
     */
    public void remove()
  {
    GamePanel.world[getCol()][getRow()] = null;
  }
}
