package Main;


/**
 * Creates a Door on the grid given the x and y coordinates 
 * @author Alex
 */
public class Door extends GameObj
{
  private int phrase;
  
    /**
     * Constructor - creates a door at position x,y 
     * @param xRow the x coordinate of the door 
     * @param yCol the y coordinate of the door
     * @param phrase the phrase that the user must say at this door
     */
    public Door(int xRow, int yCol, int phrase)
  {
    super(xRow, yCol, "Door.png");
    this.phrase = phrase;
  }
  
    /**
     * Accessor method that returns the phrase of the door
     * @return the phrase of the door
     */
    public int getPhrase()
  {
    return this.phrase;
  }
}
