public class Path {

  //Variables
  private Node self, destination;   //Nodes of the curent location of the path and its destination
  private int type;     //the type of transportation in integer
  //type : 1 = taxi , 2 = bus , 3 = train


  /**

    All Methods :

    Path : Constructor
    getSelf : Getter
    getDestination : Getter
    getType : Getter
    equals : Equals(self, destination)
    toString : String representation

  **/


  /**
      Constructor

      @param Node self
      @param Node destination
        Node representation of where the player is right now and where he could go
      @param type
        the type of transportation the path needs
  **/
  public Path(Node self, Node destination, int type) {
    this.self = self;
    this.destination = destination;
    this.type = type;
  }


  /**
      Getter

      @return int
        Integer of the name of the current node
  **/
  public int getSelf(){
    return self.getName();
  }


  /**
    Getter

    @return int
      Integer of the name of the Destination
  **/
  public int getDestination() {
    return destination.getName();
  }


  /**
      Getter

      @return int
        Integer of the type of transportation
  **/
  public int getType() {
    return type;
  }


  /**
      Equals

      @param Path other
        the other path

      @return boolean
        a boolean value of the equality of the paths
        two paths can be equal but with different transportation values
  **/
  public boolean equals(Path other){
    return (this.self.getName() == other.getSelf() && this.destination.getName() == other.getDestination());
  }


  /**
    string representation

    @return String
      string representation of the path
  **/
  public String toString() {
    String str;
    str = (self.getName() + " to " + destination.getName() + " using a ");
    if (type == 1) {
      str = str + "taxi.";
    } else if (type == 2) {
      str = str + "bus.";
    } else {
      str = str + "train.";
    }
    return str;
  }
}
