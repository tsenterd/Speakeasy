package Main;



/**
 * This method creates the final green block at position x,y
 * @author Alex
 */
public class EndPoint
  extends GameObj
{
    /**
     * Creates end point at location x,y
     * @param xRow the x coordinate
     * @param yCol the y coordinate
     */
    public EndPoint(int xRow, int yCol)
  {
    super(xRow, yCol, "EndPoint.png");
  }
}
