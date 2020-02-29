import java.util.ArrayList;

public class Node {

  //Variables
  private int x, y ;  //positions on the GUI board
  private int name ;  //The name of the Node in Integer
  private ArrayList<Path> paths = new ArrayList<Path>() ; //an arraylist of all paths available from this node

  /**

    All Methods :

    Node : Constructor
    getPaths : Getter
    getName : Getter
    getConnectedNodes : Getter
    getX : Getter
    getY : Getter
    addPath : adds a path
    toString : String representation

  **/

  /**
    Constructor

    @param int x
    @param int y
        paramaters for the location of the node on the board
    @param int name
        the name of the node in Integer value
  **/
  public Node(int x, int y,  int name) {
    this.x = x;
    this.y = y;
    this.name = name;
  }



  /**
      Getter

      @return Path[]
        array of paths of all possible paths available from this node
  **/
  public Path[] getPaths(){
    Path[] newPaths = new Path[paths.size()];

    for (int i = 0; i < paths.size(); i++){
      newPaths[i] = paths.get(i);
    }

    return newPaths;
  }


  /**
      Getter

      @return int
        Integer representing the name of the node
  **/
  public int getName() {
    return name;
  }

  
  public int[] getConnectedNodes() {
	  int[] nodes =  new int[paths.size()];
	  
	  for (int i = 0 ; i < paths.size(); i ++) {
		  nodes[i] = paths.get(i).getDestination(); 
	  }
	  
	  return nodes ;
  }
  
  
  /**
   * Getter
   * 
   * @return String
   * 	returns a string representing the x and y values of the node
   */
  public String getCoordinates() {
	  String str = "Node " + Integer.toString(name) +"\n\tX : ";
	  
	  str = str + Integer.toString(x) + " \n\tY : " + Integer.toString(y);
  
	  return str;
  }

  
  /**
   * Getter
   * 
   * @return int x
   * 	returns an integer with the value of x
   */
  public int getX() {
	  return x;
  }


  /**
   * Getter
   * 
   * @return int y
   * 	returns an integer with the value of y
   */
  public int getY() {
	  return y;
  }


/**
    adds a path to the arraylist paths

    @param Path path
      The path to be added
  **/
  public void addPath(Path path) {
    paths.add(path);
  }


  /**
    String representation

    @return String
      string representation of the Node
  **/
  public String toString() {
    String str = "" ;

    str = str + ("Node " + name + " has " + paths.size() + " paths");

    if (paths.size() > 1) {
      for (int i = 0; i < paths.size(); i++){
        str = str + "\nCan go from " + paths.get(i).toString();
      }
    }

    return str;
  }

}
