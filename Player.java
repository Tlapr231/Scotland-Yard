import java.util.ArrayList;
import java.util.Scanner;

public class Player {

  //Variables
  private Scanner input = new Scanner(System.in);   //for inputs
  private Node location ;   //the current location of the player
  private String name;      //the name of the player
  private boolean mrX;      //boolean value of if someone is MrX
  private int[] tickets = new int[5];   // 0 = double | 1 = taxi | 2 = bus | 3 = train | 4 = black


  /**

    All Methods :

    Player : Constructor
    getLocation : Getter
    getName : Getter
    getTicketName : Getter
    getDoubleTickets : Getter
    getBlackTickets : Getter
    getMrX : Getter
    isMrX : Setter, boolean of if Player is MrX
    ticketsToString : String representation of the tickets
    playDouble : removes a double ticket
    askTransport : ask methode of transportation
    move : moves a player
    toString : String representation

  **/

    /**
      Constructor

      @param Node location
        Node where the player spawns on
      @param String name
        String of the players name
    **/
  public Player(Node location, String name) {

    this.location = location;
    this.name = name;
    this.tickets[0] = 0;    //double
    this.tickets[1] = 10;   //taxi
    this.tickets[2] = 8;    //bus
    this.tickets[3] = 4;    //train
    this.tickets[4] = 0;    //black
    this.mrX = false;

  }


  /**
    Getter

    @return Node
      the current Node the player is standing on
  **/
  public Node getLocation(){
    return this.location;
  }


  /**
    Getter

    @return String
      String of the players name
  **/
  public String getName(){
    return this.name;
  }


  /**
    Getter

    @param int n
      demanded ticket

    @return String
      the String of the demanded value
  **/
  public String getTicketName(int n){

    switch (n){
      case 0 :
        return "Double Ticket";
      case 1 :
        return "Taxi Ticket";
      case 2 :
        return "Bus Ticket";
      case 3 :
        return "Train Ticket";
      case 4 :
        return "Black Ticket";
    }

    return "Invalid input";
  }


  /**
    Getter

    @return int
      the value of Double tickets
  **/
  public int getDoubleTickets(){
    return this.tickets[0];
  }


  /**
    Getter

    @return int
      the value of Black tickets
  **/
  public int getBlackTickets(){
    return this.tickets[4];
  }


  /**
    Getter

    @return boolean
      boolean of if a player is Mr.X
  **/
  public boolean getMrX(){
    return this.mrX;
  }


  /**
    Setter

    Transforms a player into Mr.X

    @param int playerCount
      gives the tickets to Mr.X depending on the player count
  **/
  public void isMrX(int playerCount) {

    this.tickets[0] = 2;     //double
    this.tickets[1] = 24;    //taxi
    this.tickets[2] = 24;    //bus
    this.tickets[3] = 24;    //train

    if (playerCount > 5) {
      this.tickets[4] = 5;    //black
    } else {
      this.tickets[4] = 4;    //black
    }

    this.mrX = true;

  }


  /**
    Representation of the tickets

    @return String
      string value of the tickets
  **/
  public String ticketsToString() {
    String str ;

    str = "\n" + name + "'s tickets : \n";

    //runs all the basic players cards
    for (int i = 1; i < 4; i++){
      if (tickets[i] > 1){
        if (i == 3){
          str = str + getTicketName(i) + " : " + Integer.toString(tickets[i]) + " tickets.";
        } else {
          str = str + getTicketName(i) + " : " + Integer.toString(tickets[i]) + " tickets.\n";
        }
      } else {
        if (i == 3){
          str = str + getTicketName(i) + " : " + Integer.toString(tickets[i]) + " ticket.";
        } else {
          str = str + getTicketName(i) + " : " + Integer.toString(tickets[i]) + " ticket.\n";
        }
      }
    }

    //runs all the Mr.X cards
    if (mrX){
      if (tickets[4] > 1){
        str = str + "\n" + getTicketName(4) + " : " + Integer.toString(tickets[4]) + " tickets.\n";
      } else {
        str = str + "\n" + getTicketName(4) + " : " + Integer.toString(tickets[4]) + " ticket.\n";
      }

      if (tickets[0] > 1){
        str = str + getTicketName(0) + " : " + Integer.toString(tickets[0]) + " tickets.";
      } else {
        str = str + getTicketName(0) + " : " + Integer.toString(tickets[0]) + " ticket.";
      }
    }

    return str;
  }


  /**
    removes a ticket of the double pile
  **/
  public void playDouble(){
    tickets[0] = tickets[0] - 1;
  }


  /**
    ask the mothode of transportation (this only occurs if there are 2 paths between the same nodes)
    works has binary values ex : 3 = 011 so bus and taxi are available
    Train | Bus | Taxi

    @param int n
      binary value of which route is available

    @return int
      Integer of which ticket wants to be used
  **/
  public int askTransport(int n){

    int anwser = 0;

    switch (n) {

      case 1 :
        //taxi

        ScotlandYard.write("Using a taxi,");
        return 1;

      case 2 :
        //bus

        ScotlandYard.write("Using a bus,");
        return 2;

      case 3 :
        //taxi + bus

        while (anwser != 1 && anwser != 2) {

          System.out.print(name + ", do you want to use a taxi (1) or a bus (2) : ");
          anwser = Integer.parseInt(input.nextLine());

          if (anwser != 1 && anwser != 2)  {
            System.out.println("Incorect input.");
          }
        }

        if (anwser == 1) {
          ScotlandYard.write("Using a taxi,");
        }

        if (anwser == 2) {
          ScotlandYard.write("Using a bus,");
        }

        return anwser;

      case 4 :
        //train

        ScotlandYard.write("Using a train,");
        return 3;

      case 5 :
        //train + taxi

        while (anwser != 1 && anwser != 2) {

          System.out.print(name + ", do you want to use a taxi (1) or a train (3) : ");
          anwser = Integer.parseInt(input.nextLine());

          if (anwser != 1 && anwser != 3)  {
            System.out.println("Incorect input.");
          }
        }

        if (anwser == 1) {
          ScotlandYard.write("Using a taxi,");
        }

        if (anwser == 3) {
          ScotlandYard.write("Using a bus,");
        }

        return anwser;

      case 6 :
        //train + bus

        while (anwser != 2 && anwser != 3) {

          System.out.print(name + ", do you want to use a bus (2) or a train (3) : ");
          anwser = Integer.parseInt(input.nextLine());

          if (anwser != 2 && anwser != 3)  {
            System.out.println("Incorect input.");
          }
        }

        if (anwser == 2) {
          ScotlandYard.write("Using a bus,");
        }

        if (anwser == 3) {
          ScotlandYard.write("Using a train,");
        }

        return anwser;
      }

      return 0;
  }


  /**
    ask the mothode of transportation for MrX (this only occurs if there are 2 paths between the same nodes)
    works has binary values ex : 3 = 011 so bus and taxi are available
    Train | Bus | Taxi

    @param int n
      binary value of which route is available

    @return int
      Integer of which ticket wants to be used
  **/
  public int askTransportMrX(int n){

    int anwser = 0;

    switch (n) {

      case 1 :
        //taxi + black tickets

        while (anwser != 1 && anwser != 4) {

          System.out.print(name + ", do you want to use a taxi (1) or a black ticket (4) : ");
          anwser = Integer.parseInt(input.nextLine());

          if (anwser != 1 && anwser != 4)  {
            System.out.println("Incorect input.");
          }
        }

        if (anwser == 1) {
          ScotlandYard.write("Using a taxi,");
        }

        if (anwser == 4) {
          ScotlandYard.write("Using a black ticket,");
        }

        return anwser;

      case 2 :
        //bus + black tickets

        while (anwser != 2 && anwser != 4) {

          System.out.print(name + ", do you want to use a bus (2) or a black ticket (4) : ");
          anwser = Integer.parseInt(input.nextLine());

          if (anwser != 2 && anwser != 4)  {
            System.out.println("Incorect input.");
          }
        }

        if (anwser == 2) {
          ScotlandYard.write("Using a taxi,");
        }

        if (anwser == 4) {
          ScotlandYard.write("Using a black ticket,");
        }

        return anwser;

      case 3 :
        //taxi + bus + black tickets

        while (anwser != 1 && anwser != 2 && anwser != 4) {

          System.out.print(name + ", do you want to use a taxi (1), a bus (2) or a black ticket (4) : ");
          anwser = Integer.parseInt(input.nextLine());

          if (anwser != 1 && anwser != 2 && anwser != 4)  {
            System.out.println("Incorect input.");
          }
        }

        if (anwser == 1) {
          ScotlandYard.write("Using a taxi,");
        }

        if (anwser == 2) {
          ScotlandYard.write("Using a bus,");
        }

        if (anwser == 4) {
          ScotlandYard.write("Using a black ticket,");
        }

        return anwser;

      case 4 :
        //train + black tickets

        while (anwser != 3 && anwser != 4) {

          System.out.print(name + ", do you want to use a train (3) or a black ticket (4) : ");
          anwser = Integer.parseInt(input.nextLine());

          if (anwser != 3 && anwser != 4)  {
            System.out.println("Incorect input.");
          }
        }

        if (anwser == 3) {
          ScotlandYard.write("Using a train,");
        }

        if (anwser == 4) {
          ScotlandYard.write("Using a black ticket,");
        }

        return anwser;

      case 5 :
        // taxi + train + black tickets

        while (anwser != 1 && anwser != 3 && anwser != 4) {

          System.out.print(name + ", do you want to use a taxi (1), a train (3) or a black ticket (4) : ");
          anwser = Integer.parseInt(input.nextLine());

          if (anwser != 1 && anwser != 3 && anwser != 4)  {
            System.out.println("Incorect input.");
          }
        }

        if (anwser == 1) {
          ScotlandYard.write("Using a taxi,");
        }

        if (anwser == 3) {
          ScotlandYard.write("Using a train,");
        }

        if (anwser == 4) {
          ScotlandYard.write("Using a black ticket,");
        }

        return anwser;

      case 6 :
        //bus + train + black tickets

        while (anwser != 2 && anwser != 3 && anwser != 4) {

          System.out.print(name + ", do you want to use a bus (2), a train (3) or a black ticket (4) : ");
          anwser = Integer.parseInt(input.nextLine());

          if (anwser != 2 && anwser != 3 && anwser != 4)  {
            System.out.println("Incorect input.");
          }
        }

        if (anwser == 2) {
          ScotlandYard.write("Using a bus,");
        }

        if (anwser == 3) {
          ScotlandYard.write("Using a train,");
        }

        if (anwser == 4) {
          ScotlandYard.write("Using a black ticket,");
        }

        return anwser;
      }

      return 0;
  }


  /**
    Moves the players

    @param Node node
      is where the player wants to move
    @param Player[] otherPlayers
      the list of other players

    @return boolean
      true if the move was successfull, false if not
  **/
  public boolean move(Node node, Player[] otherPlayers) {

    //check if other players on that node
    for (int i = 0; i < otherPlayers.length; i++) {
      if (node.getName() == otherPlayers[i].getLocation().getName()){
        System.out.println(otherPlayers[i].getName() + " is already on node " + node.getName());
        return false;
      }
    }

    //gather all possible paths from current node to the requested node
    Path[] allPaths = location.getPaths();
    ArrayList<Path> goodPaths = new ArrayList<Path>();

    for (int i = 0; i < allPaths.length; i ++){
      if (allPaths[i].getDestination() ==  node.getName()){
        goodPaths.add(allPaths[i]);
      }
    }

    //if there are 2 possible paths in the possible paths ask what ticket the player wants to use
    int sumOfTransportation = 0;
    int x = 0;

    for (int i = 0; i < goodPaths.size(); i++){
      x = goodPaths.get(i).getType();

      if (x == 3){
        sumOfTransportation += 4;
      } else {
        sumOfTransportation += x;
      }
    }

    if (sumOfTransportation == 0){
      return false;
    }

    int transportation = 0;

    if (mrX){
      transportation = askTransportMrX(sumOfTransportation);
    } else {
      transportation = askTransport(sumOfTransportation);
    }


    //check if the given tickets has enough stock
    if (tickets[transportation] < 1) {
      System.out.println(name  + " does not have the reqired " + getTicketName(transportation));
      return false;
    }

    tickets[transportation] += -1;
    this.location = node;
    return true;

  }


  /**
    string representation

    @return String
      string representation of the player
  **/
  public String toString(){
    return "\n" + name + " is on the node " + Integer.toString(location.getName()) + ".";
  }

}
