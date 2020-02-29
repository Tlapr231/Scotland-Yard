import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class ScotlandYard {

  private static Node[] nodes;  //all the nodes
  private static int playerCount = 0; //the count of the players
  private static Player[] players = new Player[6];    //the count of the players (max : 6)
  private static int[] spawns = {13, 26, 29, 34, 50, 53, 91, 94, 103, 112, 117, 132, 138, 141, 155, 174, 197, 198}; //all the potential spawns
  private static Player mrX;  //who is Mr.X
  private static Scanner input = new Scanner(System.in);    //the scanner for inputs


  /**

    All Methods :

    main : Game runs here
    getSpawnPoint : Getter
    getNodes : Getter
    addPlayer : adds a player
    setMrX : Setter
    playTurnPlayer : plays a turn for the players
    playTurnMrX : plays a turn for Mr X
    write : write to the console and backup txt file
    startGame : initialize the game data


  **/


  /**
    main

    Where the game is played
   **/
  public static void main(String[] args) throws Exception {


	  startGame();

	  SYView view = new SYView(nodes);

	  for (int i = 1; i < 200; i++) {
		  System.out.println(nodes[i].getCoordinates());
	  }

	  write("\n=============== New Game ===============\n\n");
	  write("The game has been initialized");

	  addPlayer("Thierry");
	  addPlayer("Timbits");
	  addPlayer("Am");
	  addPlayer("Bam");
	  addPlayer("Clam");
	  addPlayer("Slam");

	  setMrX(0);

	  //This is the accual game

	  String doubleAnswer ;

	  for (int round = 1; round < 25; round ++){

		  write("\n================= Round " + Integer.toString(round) + " =================");

		  //Mr.x's turn
		  if (mrX.getDoubleTickets() > 1) {

			  System.out.println(mrX.toString() + "\n" + mrX.getLocation().toString());

			  System.out.print("Do you want to use a double ticket (yes : y | no : n) : ");
			  doubleAnswer = input.nextLine();

			  if (doubleAnswer.equals("y")){
				  mrX.playDouble();
				  write("Double ticket used. " + mrX.getName() + " is now at " + Integer.toString(mrX.getDoubleTickets()) + " double ticket.");
				  playTurnMrX(mrX);
				  write(mrX.getName() + " moved to node " + Integer.toString(mrX.getLocation().getName()) + ".");
			  }
		  }

		  playTurnMrX(mrX);
		  write(mrX.getName() + " moved to node " + Integer.toString(mrX.getLocation().getName()) + ".");
		  write(mrX.ticketsToString());

		  //All the players turns
		  for (int turn = 0; turn < players.length; turn++){

			  playTurnPlayer(players[turn]);
			  write(players[turn].getName() + " moved to node " + Integer.toString(players[turn].getLocation().getName()) + ".");
			  write(players[turn].ticketsToString());

			  if (players[turn].getLocation().getName() == mrX.getLocation().getName()){

				  String output = "The detectives (";

				  write("\nMr. X was on node " + Integer.toString(mrX.getLocation().getName()) + " and " + players[turn].getName() + " found him on turn " + Integer.toString(round) + ".");

				  for (int i = 0; i < playerCount - 1; i++){
					  if (playerCount - 2 == i){
						  output = output + players[i].getName();
					  } else {
						  output = output + players[i].getName() + ", " ;
					  }
				  }

				  output = output + ") have won the game.";
				  write(output);
				  return;
			  }
		  }
	  }

	  write(mrX.getName() + " (Mr.X) won the game.");
  }


  /**
    Getter

    gets the spawn point for a player

    @return Node
      a node of an available spawn points
  **/
  public static Node getSpawnPoint() {
    Random rand = new Random();
    int spawn = 0;
    int n = 0;

    while (spawn == 0){

      n = rand.nextInt(18);
      spawn = spawns[n];

    }

    spawns[n] = 0;

    return nodes[spawn];
  }


  /**
   * Getter
   *
   * returns all the nodes
   *
   * @param Node[]
   * 	returns an array of all the nodes
   */
  public Node[] getNodes() {
	  return nodes;
  }


  /**
    Adds a player to the player array

    @param String name
      string of the players name
  **/
  public static void addPlayer(String name) {
    if (playerCount >= 6){
      System.out.println("Maximum amount of players reached. " + name + " could not be added.");
      return;
    }
    Node location = getSpawnPoint();
    Player player = new Player(location, name);
    players[playerCount] = player;
    playerCount ++;
    write(name +" Added");
  }


  /**
    Setter

    @param int playerIndex
      the index of the player chosen to be Mr.X
  **/
  public static void setMrX(int playerIndex){
    //add exception
    int len = players.length;
    Player[] newPlayers = new Player[len-1];
    int index = 0;

    players[playerIndex].isMrX(len);
    mrX = players[playerIndex];

    for (int i = 0; i < len; i++){
      if (i != playerIndex){
        newPlayers[index] = players[i];
        index ++;
      }
    }
    players = newPlayers;
    write("\n" + mrX.getName() + " is now Mr.X");
  }


  /**
    methode to play a turn as a player

    @param Player player
      the player playing his turn
  **/
  public static void playTurnPlayer(Player player){
    boolean result = false;
    String nodeAnwser;

    write(player.toString());
    System.out.println("\n" + player.getLocation().toString());

    while(!result){
      System.out.print("Where do you want to move : ");
      nodeAnwser = input.nextLine();

      result = player.move(nodes[Integer.parseInt(nodeAnwser)], players);
    }
  }


  /**
    methode to play a turn has Mr.X

    @param Player player
      the player (Mr.X) to play his turn
  **/
  public static void playTurnMrX(Player player) {
    boolean result = false;
    String nodeAnwser;

    write(player.toString());
    System.out.println("\n" + player.getLocation().toString());

    while(!result){
      System.out.print("Where do you want to move : ");
      nodeAnwser = input.nextLine();

      result = player.move(nodes[Integer.parseInt(nodeAnwser)], players);
    }
  }


  /**
    Writes in the consol and to a txt file has a backup;

    @param String message
      the message to be printed
  **/
  public static void write(String message){

    System.out.println(message);
    try {
      FileWriter writer = new FileWriter("GameLogs\\GameData.txt", true);
      BufferedWriter buffer = new BufferedWriter(writer);

      buffer.write(message + "\n");
      buffer.close();
    } catch (Exception e) {
      System.out.println("Something went wrong in the 'Write()' method ");
    }

  }


  /**
    Setter

    Sets up the game data
  **/
  public static void startGame(){

    int i = 1;

    nodes = new Node[200];
    
    nodes[0] = new Node(0, 0, 0);
    nodes[1] = new Node(200, 31, 1);
    nodes[2] = new Node(511, 15, 2);
    nodes[3] = new Node(710, 19, 3);
    nodes[4] = new Node(829, 10, 4);
    nodes[5] = new Node(1316, 22, 5);
    nodes[6] = new Node(1464, 22, 6);
    nodes[7] = new Node(1618, 28, 7);
    nodes[8] = new Node(139, 80, 8);
    nodes[9] = new Node(259, 86, 9);
    nodes[10] = new Node(610, 80, 10);
    nodes[11] = new Node(702, 93, 11);
    nodes[12] = new Node(778, 81, 12);
    nodes[13] = new Node(916, 76, 13);
    nodes[14] = new Node(1061, 56, 14);
    nodes[15] = new Node(1206, 17, 15);
    nodes[16] = new Node(1345, 82, 16);
    nodes[17] = new Node(1608, 117, 17);
    nodes[18] = new Node(68, 122, 18);
    nodes[19] = new Node(180, 130, 19);
    nodes[20] = new Node(327, 109, 20);
    nodes[21] = new Node(473, 151, 21);
    nodes[22] = new Node(679, 170, 22);
    nodes[23] = new Node(823, 123, 23);
    nodes[24] = new Node(1004, 128, 24);
    nodes[25] = new Node(1078, 144, 25);
    nodes[26] = new Node(1195, 81, 26);
    nodes[27] = new Node(1219, 129, 27);
    nodes[28] = new Node(1271, 109, 28);
    nodes[29] = new Node(1470, 142, 29);
    nodes[30] = new Node(1659, 132, 30);
    nodes[31] = new Node(113, 162, 31);
    nodes[32] = new Node(275, 191, 32);
    nodes[33] = new Node(408, 176, 33);
    nodes[34] = new Node(619, 191, 34);
    nodes[35] = new Node(746, 216, 35);
    nodes[36] = new Node(796, 220, 36);
    nodes[37] = new Node(877, 167, 37);
    nodes[38] = new Node(1041, 176, 38);
    nodes[39] = new Node(1118, 165, 39);
    nodes[40] = new Node(1246, 201, 40);
    nodes[41] = new Node(1302, 186, 41);
    nodes[42] = new Node(1612, 189, 42);
    nodes[43] = new Node(37, 208, 43);
    nodes[44] = new Node(207, 232, 44);
    nodes[45] = new Node(321, 249, 45);
    nodes[46] = new Node(427, 226, 46);
    nodes[47] = new Node(512, 213, 47);
    nodes[48] = new Node(645, 252, 48);
    nodes[49] = new Node(835, 255, 49);
    nodes[50] = new Node(916, 216, 50);
    nodes[51] = new Node(1079, 225, 51);
    nodes[52] = new Node(1165, 210, 52);
    nodes[53] = new Node(1261, 247, 53);
    nodes[54] = new Node(1323, 235, 54);
    nodes[55] = new Node(1471, 235, 55);
    nodes[56] = new Node(1665, 243, 56);
    nodes[57] = new Node(90, 255, 57);
    nodes[58] = new Node(254, 267, 58);
    nodes[59] = new Node(287, 297, 59);
    nodes[60] = new Node(351, 289, 60);
    nodes[61] = new Node(457, 300, 61);
    nodes[62] = new Node(516, 284, 62);
    nodes[63] = new Node(657, 339, 63);
    nodes[64] = new Node(742, 326, 64);
    nodes[65] = new Node(832, 311, 65);
    nodes[66] = new Node(888, 303, 66);
    nodes[67] = new Node(979, 291, 67);
    nodes[68] = new Node(1147, 273, 68);
    nodes[69] = new Node(1204, 268, 69);
    nodes[70] = new Node(1333, 284, 70);
    nodes[71] = new Node(1451, 282, 71);
    nodes[72] = new Node(1573, 291, 72);
    nodes[73] = new Node(90, 302, 73);
    nodes[74] = new Node(147, 348, 74);
    nodes[75] = new Node(227, 334, 75);
    nodes[76] = new Node(329, 330, 76);
    nodes[77] = new Node(400, 368, 77);
    nodes[78] = new Node(483, 363, 78);
    nodes[79] = new Node(541, 351, 79);
    nodes[80] = new Node(684, 372, 80);
    nodes[81] = new Node(801, 387, 81);
    nodes[82] = new Node(842, 363, 82);
    nodes[83] = new Node(960, 352, 83);
    nodes[84] = new Node(1035, 277, 84);
    nodes[85] = new Node(1103, 310, 85);
    nodes[86] = new Node(1221, 325, 86);
    nodes[87] = new Node(1342, 362, 87);
    nodes[88] = new Node(1394, 372, 88);
    nodes[89] = new Node(1441, 345, 89);
    nodes[90] = new Node(1529, 342, 90);
    nodes[91] = new Node(1647, 344, 91);
    nodes[92] = new Node(45, 390, 92);
    nodes[93] = new Node(53, 476, 93);
    nodes[94] = new Node(157, 451, 94);
    nodes[95] = new Node(206, 405, 95);
    nodes[96] = new Node(452, 429, 96);
    nodes[97] = new Node(506, 417, 97);
    nodes[98] = new Node(574, 400, 98);
    nodes[99] = new Node(641, 406, 99);
    nodes[100] = new Node(759, 431, 100);
    nodes[101] = new Node(882, 398, 101);
    nodes[102] = new Node(1024, 325, 102);
    nodes[103] = new Node(1121, 367, 103);
    nodes[104] = new Node(1222, 382, 104);
    nodes[105] = new Node(1492, 396, 105);
    nodes[106] = new Node(1598, 409, 106);
    nodes[107] = new Node(1672, 411, 107);
    nodes[108] = new Node(1492, 510, 108);
    nodes[109] = new Node(534, 490, 109);
    nodes[110] = new Node(613, 463, 110);
    nodes[111] = new Node(633, 520, 111);
    nodes[112] = new Node(696, 463, 112);
    nodes[113] = new Node(809, 460, 113);
    nodes[114] = new Node(894, 444, 114);
    nodes[115] = new Node(1018, 414, 115);
    nodes[116] = new Node(1231, 476, 116);
    nodes[117] = new Node(1358, 502, 117);
    nodes[118] = new Node(1223, 522, 118);
    nodes[119] = new Node(1633, 543, 119);
    nodes[120] = new Node(46, 579, 120);
    nodes[121] = new Node(100, 580, 121);
    nodes[122] = new Node(184, 576, 122);
    nodes[123] = new Node(393, 573, 123);
    nodes[124] = new Node(515, 557, 124);
    nodes[125] = new Node(728, 503, 125);
    nodes[126] = new Node(956, 477, 126);
    nodes[127] = new Node(1105, 503, 127);
    nodes[128] = new Node(1303, 671, 128);
    nodes[129] = new Node(1296, 535, 129);
    nodes[130] = new Node(700, 559, 130);
    nodes[131] = new Node(759, 531, 131);
    nodes[132] = new Node(893, 525, 132);
    nodes[133] = new Node(1043, 574, 133);
    nodes[134] = new Node(1150, 548, 134);
    nodes[135] = new Node(1400, 564, 135);
    nodes[136] = new Node(1599, 622, 136);
    nodes[137] = new Node(303, 605, 137);
    nodes[138] = new Node(552, 591, 138);
    nodes[139] = new Node(656, 596, 139);
    nodes[140] = new Node(889, 585, 140);
    nodes[141] = new Node(1083, 594, 141);
    nodes[142] = new Node(1219, 611, 142);
    nodes[143] = new Node(1350, 600, 143);
    nodes[144] = new Node(56, 684, 144);
    nodes[145] = new Node(121, 678, 145);
    nodes[146] = new Node(203, 618, 146);
    nodes[147] = new Node(263, 666, 147);
    nodes[148] = new Node(348, 654, 148);
    nodes[149] = new Node(417, 649, 149);
    nodes[150] = new Node(500, 630, 150);
    nodes[151] = new Node(540, 658, 151);
    nodes[152] = new Node(593, 627, 152);
    nodes[153] = new Node(668, 653, 153);
    nodes[154] = new Node(766, 639, 154);
    nodes[155] = new Node(814, 678, 155);
    nodes[156] = new Node(880, 672, 156);
    nodes[157] = new Node(1032, 686, 157);
    nodes[158] = new Node(1129, 646, 158);
    nodes[159] = new Node(1135, 780, 159);
    nodes[160] = new Node(1405, 684, 160);
    nodes[161] = new Node(1515, 677, 161);
    nodes[162] = new Node(1672, 673, 162);
    nodes[163] = new Node(193, 708, 163);
    nodes[164] = new Node(276, 708, 164);
    nodes[165] = new Node(437, 729, 165);
    nodes[166] = new Node(605, 698, 166);
    nodes[167] = new Node(733, 720, 167);
    nodes[168] = new Node(779, 740, 168);
    nodes[169] = new Node(874, 730, 169);
    nodes[170] = new Node(1032, 747, 170);
    nodes[171] = new Node(1484, 871, 171);
    nodes[172] = new Node(1261, 731, 172);
    nodes[173] = new Node(1434, 770, 173);
    nodes[174] = new Node(1582, 741, 174);
    nodes[175] = new Node(1662, 781, 175);
    nodes[176] = new Node(34, 770, 176);
    nodes[177] = new Node(103, 755, 177);
    nodes[178] = new Node(229, 745, 178);
    nodes[179] = new Node(374, 762, 179);
    nodes[180] = new Node(467, 775, 180);
    nodes[181] = new Node(560, 759, 181);
    nodes[182] = new Node(607, 764, 182);
    nodes[183] = new Node(667, 735, 183);
    nodes[184] = new Node(850, 783, 184);
    nodes[185] = new Node(1014, 891, 185);
    nodes[186] = new Node(1063, 815, 186);
    nodes[187] = new Node(1221, 790, 187);
    nodes[188] = new Node(1370, 792, 188);
    nodes[189] = new Node(106, 843, 189);
    nodes[190] = new Node(183, 870, 190);
    nodes[191] = new Node(279, 814, 191);
    nodes[192] = new Node(302, 889, 192);
    nodes[193] = new Node(532, 821, 193);
    nodes[194] = new Node(558, 896, 194);
    nodes[195] = new Node(633, 843, 195);
    nodes[196] = new Node(732, 799, 196);
    nodes[197] = new Node(746, 848, 197);
    nodes[198] = new Node(1128, 893, 198);
    nodes[199] = new Node(1387, 888, 199);
    
    

    nodes[1].addPath(new Path(nodes[1], nodes[8], 1));
    nodes[1].addPath(new Path(nodes[1], nodes[9], 1));
    nodes[1].addPath(new Path(nodes[1], nodes[46], 2));
    nodes[1].addPath(new Path(nodes[1], nodes[46], 3));
    nodes[1].addPath(new Path(nodes[1], nodes[58], 2));
    
    nodes[2].addPath(new Path(nodes[2], nodes[10], 1));
    nodes[2].addPath(new Path(nodes[2], nodes[20], 1));
    
    nodes[3].addPath(new Path(nodes[3], nodes[4], 1));
    nodes[3].addPath(new Path(nodes[3], nodes[11], 1));
    nodes[3].addPath(new Path(nodes[3], nodes[12], 1));
    nodes[3].addPath(new Path(nodes[3], nodes[22], 2));
    nodes[3].addPath(new Path(nodes[3], nodes[23], 2));
    
    nodes[4].addPath(new Path(nodes[4], nodes[3], 1));
    nodes[4].addPath(new Path(nodes[4], nodes[13], 1));
    
    nodes[5].addPath(new Path(nodes[5], nodes[15], 1));
    nodes[5].addPath(new Path(nodes[5], nodes[16], 1));
    
    nodes[6].addPath(new Path(nodes[6], nodes[7], 1));
    nodes[6].addPath(new Path(nodes[6], nodes[29], 1));
    
    nodes[7].addPath(new Path(nodes[7], nodes[6], 1));
    nodes[7].addPath(new Path(nodes[7], nodes[17], 1));
    nodes[7].addPath(new Path(nodes[7], nodes[42], 2));
    
    nodes[8].addPath(new Path(nodes[8], nodes[1], 1));
    nodes[8].addPath(new Path(nodes[8], nodes[18], 1));
    nodes[8].addPath(new Path(nodes[8], nodes[19], 1));
    
    nodes[9].addPath(new Path(nodes[9], nodes[1], 1));
    nodes[9].addPath(new Path(nodes[9], nodes[19], 1));
    nodes[9].addPath(new Path(nodes[9], nodes[20], 1));
    
    nodes[10].addPath(new Path(nodes[10], nodes[2], 1));
    nodes[10].addPath(new Path(nodes[10], nodes[11], 1));
    nodes[10].addPath(new Path(nodes[10], nodes[21], 1));
    nodes[10].addPath(new Path(nodes[10], nodes[34], 1));
    
    nodes[11].addPath(new Path(nodes[11], nodes[3], 1));
    nodes[11].addPath(new Path(nodes[11], nodes[10], 1));
    nodes[11].addPath(new Path(nodes[11], nodes[22], 1));
    
    nodes[12].addPath(new Path(nodes[12], nodes[3], 1));
    nodes[12].addPath(new Path(nodes[12], nodes[23], 1));
    
    nodes[13].addPath(new Path(nodes[13], nodes[4], 1));
    nodes[13].addPath(new Path(nodes[13], nodes[14], 2));
    nodes[13].addPath(new Path(nodes[13], nodes[23], 1));
    nodes[13].addPath(new Path(nodes[13], nodes[23], 2));
    nodes[13].addPath(new Path(nodes[13], nodes[24], 1));
    nodes[13].addPath(new Path(nodes[13], nodes[46], 3));
    nodes[13].addPath(new Path(nodes[13], nodes[52], 2));
    nodes[13].addPath(new Path(nodes[13], nodes[67], 3));
    nodes[13].addPath(new Path(nodes[13], nodes[89], 3));
    
    nodes[14].addPath(new Path(nodes[14], nodes[13], 2));
    nodes[14].addPath(new Path(nodes[14], nodes[15], 1));
    nodes[14].addPath(new Path(nodes[14], nodes[15], 2));
    nodes[14].addPath(new Path(nodes[14], nodes[25], 1));
    
    nodes[15].addPath(new Path(nodes[15], nodes[5], 1));
    nodes[15].addPath(new Path(nodes[15], nodes[14], 1));
    nodes[15].addPath(new Path(nodes[15], nodes[14], 2));
    nodes[15].addPath(new Path(nodes[15], nodes[16], 1));
    nodes[15].addPath(new Path(nodes[15], nodes[26], 1));
    nodes[15].addPath(new Path(nodes[15], nodes[28], 1));
    nodes[15].addPath(new Path(nodes[15], nodes[29], 2));
    nodes[15].addPath(new Path(nodes[15], nodes[41], 2));
    
    nodes[16].addPath(new Path(nodes[16], nodes[5], 1));
    nodes[16].addPath(new Path(nodes[16], nodes[15], 1));
    nodes[16].addPath(new Path(nodes[16], nodes[28], 1));
    nodes[16].addPath(new Path(nodes[16], nodes[29], 1));
    
    nodes[17].addPath(new Path(nodes[17], nodes[7], 1));
    nodes[17].addPath(new Path(nodes[17], nodes[29], 1));
    nodes[17].addPath(new Path(nodes[17], nodes[30], 1));
    
    nodes[18].addPath(new Path(nodes[18], nodes[8], 1));
    nodes[18].addPath(new Path(nodes[18], nodes[31], 1));
    nodes[18].addPath(new Path(nodes[18], nodes[43], 1));
    
    nodes[19].addPath(new Path(nodes[19], nodes[8], 1));
    nodes[19].addPath(new Path(nodes[19], nodes[9], 1));
    nodes[19].addPath(new Path(nodes[19], nodes[32], 1));
    
    nodes[20].addPath(new Path(nodes[20], nodes[2], 1));
    nodes[20].addPath(new Path(nodes[20], nodes[9], 1));
    nodes[20].addPath(new Path(nodes[20], nodes[33], 1));
    
    nodes[21].addPath(new Path(nodes[21], nodes[10], 1));
    nodes[21].addPath(new Path(nodes[21], nodes[33], 1));
    
    nodes[22].addPath(new Path(nodes[22], nodes[3], 2));
    nodes[22].addPath(new Path(nodes[22], nodes[11], 1));
    nodes[22].addPath(new Path(nodes[22], nodes[23], 1));
    nodes[22].addPath(new Path(nodes[22], nodes[23], 2));
    nodes[22].addPath(new Path(nodes[22], nodes[34], 1));
    nodes[22].addPath(new Path(nodes[22], nodes[34], 2));
    nodes[22].addPath(new Path(nodes[22], nodes[35], 1));
    nodes[22].addPath(new Path(nodes[22], nodes[65], 2));
    
    nodes[23].addPath(new Path(nodes[23], nodes[3], 2));
    nodes[23].addPath(new Path(nodes[23], nodes[12], 1));
    nodes[23].addPath(new Path(nodes[23], nodes[13], 1));
    nodes[23].addPath(new Path(nodes[23], nodes[13], 2));
    nodes[23].addPath(new Path(nodes[23], nodes[22], 1));
    nodes[23].addPath(new Path(nodes[23], nodes[22], 2));
    nodes[23].addPath(new Path(nodes[23], nodes[37], 1));
    nodes[23].addPath(new Path(nodes[23], nodes[67], 2));
   
    nodes[24].addPath(new Path(nodes[24], nodes[13], 1));
    nodes[24].addPath(new Path(nodes[24], nodes[37], 1));
    nodes[24].addPath(new Path(nodes[24], nodes[38], 1));
    
    nodes[25].addPath(new Path(nodes[25], nodes[14], 1));
    nodes[25].addPath(new Path(nodes[25], nodes[38], 1));
    nodes[25].addPath(new Path(nodes[25], nodes[39], 1));
    
    nodes[26].addPath(new Path(nodes[26], nodes[15], 1));
    nodes[26].addPath(new Path(nodes[26], nodes[27], 1));
    nodes[26].addPath(new Path(nodes[26], nodes[39], 1));
    
    nodes[27].addPath(new Path(nodes[27], nodes[26], 1));
    nodes[27].addPath(new Path(nodes[27], nodes[28], 1));
    nodes[27].addPath(new Path(nodes[27], nodes[40], 1));
    
    nodes[28].addPath(new Path(nodes[28], nodes[15], 1));
    nodes[28].addPath(new Path(nodes[28], nodes[16], 1));
    nodes[28].addPath(new Path(nodes[28], nodes[27], 1));
    nodes[28].addPath(new Path(nodes[28], nodes[41], 1));
    
    nodes[29].addPath(new Path(nodes[29], nodes[6], 1));
    nodes[29].addPath(new Path(nodes[29], nodes[15], 2));
    nodes[29].addPath(new Path(nodes[29], nodes[16], 1));
    nodes[29].addPath(new Path(nodes[29], nodes[17], 1));
    nodes[29].addPath(new Path(nodes[29], nodes[41], 1));
    nodes[29].addPath(new Path(nodes[29], nodes[41], 2));
    nodes[29].addPath(new Path(nodes[29], nodes[42], 1));
    nodes[29].addPath(new Path(nodes[29], nodes[42], 2));
    nodes[29].addPath(new Path(nodes[29], nodes[55], 2));
    
    nodes[30].addPath(new Path(nodes[30], nodes[17], 1));
    nodes[30].addPath(new Path(nodes[30], nodes[42], 1));
    
    nodes[31].addPath(new Path(nodes[31], nodes[18], 1));
    nodes[31].addPath(new Path(nodes[31], nodes[43], 1));
    nodes[31].addPath(new Path(nodes[31], nodes[44], 1));
    
    nodes[32].addPath(new Path(nodes[32], nodes[19], 1));
    nodes[32].addPath(new Path(nodes[32], nodes[33], 1));
    nodes[32].addPath(new Path(nodes[32], nodes[44], 1));
    nodes[32].addPath(new Path(nodes[32], nodes[45], 1));
    
    nodes[33].addPath(new Path(nodes[33], nodes[20], 1));
    nodes[33].addPath(new Path(nodes[33], nodes[21], 1));
    nodes[33].addPath(new Path(nodes[33], nodes[32], 1));
    nodes[33].addPath(new Path(nodes[33], nodes[46], 1));
    
    nodes[34].addPath(new Path(nodes[34], nodes[10], 1));
    nodes[34].addPath(new Path(nodes[34], nodes[22], 1));
    nodes[34].addPath(new Path(nodes[34], nodes[22], 2));
    nodes[34].addPath(new Path(nodes[34], nodes[46], 2));
    nodes[34].addPath(new Path(nodes[34], nodes[47], 1));
    nodes[34].addPath(new Path(nodes[34], nodes[48], 1));
    nodes[34].addPath(new Path(nodes[34], nodes[63], 2));
    
    nodes[35].addPath(new Path(nodes[35], nodes[22], 1));
    nodes[35].addPath(new Path(nodes[35], nodes[36], 1));
    nodes[35].addPath(new Path(nodes[35], nodes[48], 1));
    nodes[35].addPath(new Path(nodes[35], nodes[65], 1));
    
    nodes[36].addPath(new Path(nodes[36], nodes[35], 1));
    nodes[36].addPath(new Path(nodes[36], nodes[37], 1));
    nodes[36].addPath(new Path(nodes[36], nodes[49], 1));
    
    nodes[37].addPath(new Path(nodes[37], nodes[23], 1));
    nodes[37].addPath(new Path(nodes[37], nodes[24], 1));
    nodes[37].addPath(new Path(nodes[37], nodes[36], 1));
    nodes[37].addPath(new Path(nodes[37], nodes[50], 1));
    
    nodes[38].addPath(new Path(nodes[38], nodes[24], 1));
    nodes[38].addPath(new Path(nodes[38], nodes[25], 1));
    nodes[38].addPath(new Path(nodes[38], nodes[50], 1));
    nodes[38].addPath(new Path(nodes[38], nodes[51], 1));
    
    nodes[39].addPath(new Path(nodes[39], nodes[25], 1));
    nodes[39].addPath(new Path(nodes[39], nodes[26], 1));
    nodes[39].addPath(new Path(nodes[39], nodes[51], 1));
    nodes[39].addPath(new Path(nodes[39], nodes[52], 1));
    
    nodes[40].addPath(new Path(nodes[40], nodes[27], 1));
    nodes[40].addPath(new Path(nodes[40], nodes[41], 1));
    nodes[40].addPath(new Path(nodes[40], nodes[52], 1));
    nodes[40].addPath(new Path(nodes[40], nodes[53], 1));
    
    nodes[41].addPath(new Path(nodes[41], nodes[15], 2));
    nodes[41].addPath(new Path(nodes[41], nodes[28], 1));
    nodes[41].addPath(new Path(nodes[41], nodes[29], 1));
    nodes[41].addPath(new Path(nodes[41], nodes[29], 2));
    nodes[41].addPath(new Path(nodes[41], nodes[40], 1));
    nodes[41].addPath(new Path(nodes[41], nodes[52], 2));
    nodes[41].addPath(new Path(nodes[41], nodes[54], 1));
    nodes[41].addPath(new Path(nodes[41], nodes[87], 2));
    
    nodes[42].addPath(new Path(nodes[42], nodes[7], 2));
    nodes[42].addPath(new Path(nodes[42], nodes[29], 1));
    nodes[42].addPath(new Path(nodes[42], nodes[29], 2));
    nodes[42].addPath(new Path(nodes[42], nodes[30], 1));
    nodes[42].addPath(new Path(nodes[42], nodes[56], 1));
    nodes[42].addPath(new Path(nodes[42], nodes[72], 1));
    nodes[42].addPath(new Path(nodes[42], nodes[72], 2));
    
    nodes[43].addPath(new Path(nodes[43], nodes[18], 1));
    nodes[43].addPath(new Path(nodes[43], nodes[31], 1));
    nodes[43].addPath(new Path(nodes[43], nodes[57], 1));
    
    nodes[44].addPath(new Path(nodes[44], nodes[31], 1));
    nodes[44].addPath(new Path(nodes[44], nodes[32], 1));
    nodes[44].addPath(new Path(nodes[44], nodes[58], 1));
    
    nodes[45].addPath(new Path(nodes[45], nodes[32], 1));
    nodes[45].addPath(new Path(nodes[45], nodes[46], 1));
    nodes[45].addPath(new Path(nodes[45], nodes[58], 1));
    nodes[45].addPath(new Path(nodes[45], nodes[59], 1));
    nodes[45].addPath(new Path(nodes[45], nodes[60], 1));
    
    nodes[46].addPath(new Path(nodes[46], nodes[1], 2));
    nodes[46].addPath(new Path(nodes[46], nodes[1], 3));
    nodes[46].addPath(new Path(nodes[46], nodes[13], 3));
    nodes[46].addPath(new Path(nodes[46], nodes[33], 1));
    nodes[46].addPath(new Path(nodes[46], nodes[34], 2));
    nodes[46].addPath(new Path(nodes[46], nodes[45], 1));
    nodes[46].addPath(new Path(nodes[46], nodes[47], 1));
    nodes[46].addPath(new Path(nodes[46], nodes[58], 2));
    nodes[46].addPath(new Path(nodes[46], nodes[61], 1));
    nodes[46].addPath(new Path(nodes[46], nodes[74], 3));
    nodes[46].addPath(new Path(nodes[46], nodes[78], 2));
    nodes[46].addPath(new Path(nodes[46], nodes[79], 3));
    
    nodes[47].addPath(new Path(nodes[47], nodes[34], 1));
    nodes[47].addPath(new Path(nodes[47], nodes[46], 1));
    nodes[47].addPath(new Path(nodes[47], nodes[62], 1));
    
    nodes[48].addPath(new Path(nodes[48], nodes[34], 1));
    nodes[48].addPath(new Path(nodes[48], nodes[35], 1));
    nodes[48].addPath(new Path(nodes[48], nodes[62], 1));
    nodes[48].addPath(new Path(nodes[48], nodes[63], 1));
    
    nodes[49].addPath(new Path(nodes[49], nodes[36], 1));
    nodes[49].addPath(new Path(nodes[49], nodes[50], 1));
    nodes[49].addPath(new Path(nodes[49], nodes[66], 1));
    
    nodes[50].addPath(new Path(nodes[50], nodes[37], 1));
    nodes[50].addPath(new Path(nodes[50], nodes[38], 1));
    nodes[50].addPath(new Path(nodes[50], nodes[49], 1));
    
    nodes[51].addPath(new Path(nodes[51], nodes[38], 1));
    nodes[51].addPath(new Path(nodes[51], nodes[39], 1));
    nodes[51].addPath(new Path(nodes[51], nodes[52], 1));
    nodes[51].addPath(new Path(nodes[51], nodes[67], 1));
    nodes[51].addPath(new Path(nodes[51], nodes[68], 1));
    
    nodes[52].addPath(new Path(nodes[52], nodes[13], 2));
    nodes[52].addPath(new Path(nodes[52], nodes[39], 1));
    nodes[52].addPath(new Path(nodes[52], nodes[40], 1));
    nodes[52].addPath(new Path(nodes[52], nodes[41], 2));
    nodes[52].addPath(new Path(nodes[52], nodes[51], 1));
    nodes[52].addPath(new Path(nodes[52], nodes[67], 2));
    nodes[52].addPath(new Path(nodes[52], nodes[69], 1));
    nodes[52].addPath(new Path(nodes[52], nodes[86], 2));
    
    nodes[53].addPath(new Path(nodes[53], nodes[40], 1));
    nodes[53].addPath(new Path(nodes[53], nodes[54], 1));
    nodes[53].addPath(new Path(nodes[53], nodes[69], 1));
    
    nodes[54].addPath(new Path(nodes[54], nodes[41], 1));
    nodes[54].addPath(new Path(nodes[54], nodes[53], 1));
    nodes[54].addPath(new Path(nodes[54], nodes[55], 1));
    nodes[54].addPath(new Path(nodes[54], nodes[70], 1));
    
    nodes[55].addPath(new Path(nodes[55], nodes[29], 2));
    nodes[55].addPath(new Path(nodes[55], nodes[54], 1));
    nodes[55].addPath(new Path(nodes[55], nodes[71], 1));
    nodes[55].addPath(new Path(nodes[55], nodes[89], 2));
    
    nodes[56].addPath(new Path(nodes[56], nodes[42], 1));
    nodes[56].addPath(new Path(nodes[56], nodes[91], 1));
    
    nodes[57].addPath(new Path(nodes[57], nodes[43], 1));
    nodes[57].addPath(new Path(nodes[57], nodes[58], 1));
    nodes[57].addPath(new Path(nodes[57], nodes[73], 1));
    
    nodes[58].addPath(new Path(nodes[58], nodes[1], 2));
    nodes[58].addPath(new Path(nodes[58], nodes[44], 1));
    nodes[58].addPath(new Path(nodes[58], nodes[45], 1));
    nodes[58].addPath(new Path(nodes[58], nodes[46], 2));
    nodes[58].addPath(new Path(nodes[58], nodes[57], 1));
    nodes[58].addPath(new Path(nodes[58], nodes[59], 1));
    nodes[58].addPath(new Path(nodes[58], nodes[74], 1));
    nodes[58].addPath(new Path(nodes[58], nodes[74], 2));
    nodes[58].addPath(new Path(nodes[58], nodes[75], 1));
    nodes[58].addPath(new Path(nodes[58], nodes[77], 2));
    
    nodes[59].addPath(new Path(nodes[59], nodes[45], 1));
    nodes[59].addPath(new Path(nodes[59], nodes[58], 1));
    nodes[59].addPath(new Path(nodes[59], nodes[75], 1));
    nodes[59].addPath(new Path(nodes[59], nodes[76], 1));
    
    nodes[60].addPath(new Path(nodes[60], nodes[45], 1));
    nodes[60].addPath(new Path(nodes[60], nodes[61], 1));
    nodes[60].addPath(new Path(nodes[60], nodes[76], 1));
    
    nodes[61].addPath(new Path(nodes[61], nodes[46], 1));
    nodes[61].addPath(new Path(nodes[61], nodes[60], 1));
    nodes[61].addPath(new Path(nodes[61], nodes[62], 1));
    nodes[61].addPath(new Path(nodes[61], nodes[76], 1));
    nodes[61].addPath(new Path(nodes[61], nodes[78], 1));
    
    nodes[62].addPath(new Path(nodes[62], nodes[47], 1));
    nodes[62].addPath(new Path(nodes[62], nodes[48], 1));
    nodes[62].addPath(new Path(nodes[62], nodes[61], 1));
    nodes[62].addPath(new Path(nodes[62], nodes[79], 1));
    
    nodes[63].addPath(new Path(nodes[63], nodes[34], 2));
    nodes[63].addPath(new Path(nodes[63], nodes[48], 1));
    nodes[63].addPath(new Path(nodes[63], nodes[64], 1));
    nodes[63].addPath(new Path(nodes[63], nodes[65], 2));
    nodes[63].addPath(new Path(nodes[63], nodes[79], 1));
    nodes[63].addPath(new Path(nodes[63], nodes[79], 2));
    nodes[63].addPath(new Path(nodes[63], nodes[80], 1));
    nodes[63].addPath(new Path(nodes[63], nodes[100], 2));
    
    nodes[64].addPath(new Path(nodes[64], nodes[63], 1));
    nodes[64].addPath(new Path(nodes[64], nodes[65], 1));
    nodes[64].addPath(new Path(nodes[64], nodes[81], 1));
    
    nodes[65].addPath(new Path(nodes[65], nodes[22], 2));
    nodes[65].addPath(new Path(nodes[65], nodes[35], 1));
    nodes[65].addPath(new Path(nodes[65], nodes[63], 2));
    nodes[65].addPath(new Path(nodes[65], nodes[64], 1));
    nodes[65].addPath(new Path(nodes[65], nodes[66], 1));
    nodes[65].addPath(new Path(nodes[65], nodes[67], 2));
    nodes[65].addPath(new Path(nodes[65], nodes[82], 1));
    nodes[65].addPath(new Path(nodes[65], nodes[82], 2));
    
    nodes[66].addPath(new Path(nodes[66], nodes[49], 1));
    nodes[66].addPath(new Path(nodes[66], nodes[65], 1));
    nodes[66].addPath(new Path(nodes[66], nodes[67], 1));
    nodes[66].addPath(new Path(nodes[66], nodes[82], 1));
    
    nodes[67].addPath(new Path(nodes[67], nodes[13], 3));
    nodes[67].addPath(new Path(nodes[67], nodes[23], 2));
    nodes[67].addPath(new Path(nodes[67], nodes[51], 1));
    nodes[67].addPath(new Path(nodes[67], nodes[52], 2));
    nodes[67].addPath(new Path(nodes[67], nodes[65], 2));
    nodes[67].addPath(new Path(nodes[67], nodes[66], 1));
    nodes[67].addPath(new Path(nodes[67], nodes[68], 1));
    nodes[67].addPath(new Path(nodes[67], nodes[79], 3));
    nodes[67].addPath(new Path(nodes[67], nodes[82], 2));
    nodes[67].addPath(new Path(nodes[67], nodes[84], 1));
    nodes[67].addPath(new Path(nodes[67], nodes[89], 3));
    nodes[67].addPath(new Path(nodes[67], nodes[102], 2));
    nodes[67].addPath(new Path(nodes[67], nodes[111], 3));
    
    nodes[68].addPath(new Path(nodes[68], nodes[51], 1));
    nodes[68].addPath(new Path(nodes[68], nodes[67], 1));
    nodes[68].addPath(new Path(nodes[68], nodes[69], 1));
    nodes[68].addPath(new Path(nodes[68], nodes[85], 1));
    
    nodes[69].addPath(new Path(nodes[69], nodes[52], 1));
    nodes[69].addPath(new Path(nodes[69], nodes[53], 1));
    nodes[69].addPath(new Path(nodes[69], nodes[68], 1));
    nodes[69].addPath(new Path(nodes[69], nodes[86], 1));
    
    nodes[70].addPath(new Path(nodes[70], nodes[54], 1));
    nodes[70].addPath(new Path(nodes[70], nodes[71], 1));
    nodes[70].addPath(new Path(nodes[70], nodes[87], 1));
    
    nodes[71].addPath(new Path(nodes[71], nodes[55], 1));
    nodes[71].addPath(new Path(nodes[71], nodes[70], 1));
    nodes[71].addPath(new Path(nodes[71], nodes[72], 1));
    nodes[71].addPath(new Path(nodes[71], nodes[89], 1));
    
    nodes[72].addPath(new Path(nodes[72], nodes[42], 1));
    nodes[72].addPath(new Path(nodes[72], nodes[42], 2));
    nodes[72].addPath(new Path(nodes[72], nodes[71], 1));
    nodes[72].addPath(new Path(nodes[72], nodes[90], 1));
    nodes[72].addPath(new Path(nodes[72], nodes[91], 1));
    nodes[72].addPath(new Path(nodes[72], nodes[105], 2));
    nodes[72].addPath(new Path(nodes[72], nodes[107], 2));
    
    nodes[73].addPath(new Path(nodes[73], nodes[57], 1));
    nodes[73].addPath(new Path(nodes[73], nodes[74], 1));
    nodes[73].addPath(new Path(nodes[73], nodes[92], 1));
    
    nodes[74].addPath(new Path(nodes[74], nodes[46], 3));
    nodes[74].addPath(new Path(nodes[74], nodes[58], 1));
    nodes[74].addPath(new Path(nodes[74], nodes[58], 2));
    nodes[74].addPath(new Path(nodes[74], nodes[73], 1));
    nodes[74].addPath(new Path(nodes[74], nodes[75], 1));
    nodes[74].addPath(new Path(nodes[74], nodes[92], 1));
    nodes[74].addPath(new Path(nodes[74], nodes[94], 2));
    
    nodes[75].addPath(new Path(nodes[75], nodes[58], 1));
    nodes[75].addPath(new Path(nodes[75], nodes[59], 1));
    nodes[75].addPath(new Path(nodes[75], nodes[74], 1));
    nodes[75].addPath(new Path(nodes[75], nodes[94], 1));
    
    nodes[76].addPath(new Path(nodes[76], nodes[59], 1));
    nodes[76].addPath(new Path(nodes[76], nodes[60], 1));
    nodes[76].addPath(new Path(nodes[76], nodes[61], 1));
    nodes[76].addPath(new Path(nodes[76], nodes[77], 1));
    
    nodes[77].addPath(new Path(nodes[77], nodes[58], 2));
    nodes[77].addPath(new Path(nodes[77], nodes[76], 1));
    nodes[77].addPath(new Path(nodes[77], nodes[78], 1));
    nodes[77].addPath(new Path(nodes[77], nodes[78], 2));
    nodes[77].addPath(new Path(nodes[77], nodes[94], 2));
    nodes[77].addPath(new Path(nodes[77], nodes[95], 1));
    nodes[77].addPath(new Path(nodes[77], nodes[96], 1));
    nodes[77].addPath(new Path(nodes[77], nodes[124], 2));
    
    nodes[78].addPath(new Path(nodes[78], nodes[46], 2));
    nodes[78].addPath(new Path(nodes[78], nodes[61], 1));
    nodes[78].addPath(new Path(nodes[78], nodes[77], 1));
    nodes[78].addPath(new Path(nodes[78], nodes[77], 2));
    nodes[78].addPath(new Path(nodes[78], nodes[79], 1));
    nodes[78].addPath(new Path(nodes[78], nodes[79], 2));
    nodes[78].addPath(new Path(nodes[78], nodes[97], 1));
    
    nodes[79].addPath(new Path(nodes[79], nodes[46], 3));
    nodes[79].addPath(new Path(nodes[79], nodes[62], 1));
    nodes[79].addPath(new Path(nodes[79], nodes[63], 1));
    nodes[79].addPath(new Path(nodes[79], nodes[63], 2));
    nodes[79].addPath(new Path(nodes[79], nodes[67], 3));
    nodes[79].addPath(new Path(nodes[79], nodes[78], 1));
    nodes[79].addPath(new Path(nodes[79], nodes[78], 2));
    nodes[79].addPath(new Path(nodes[79], nodes[93], 3));
    nodes[79].addPath(new Path(nodes[79], nodes[98], 1));
    nodes[79].addPath(new Path(nodes[79], nodes[111], 3));
    
    nodes[80].addPath(new Path(nodes[80], nodes[63], 1));
    nodes[80].addPath(new Path(nodes[80], nodes[99], 1));
    nodes[80].addPath(new Path(nodes[80], nodes[100], 1));
    
    nodes[81].addPath(new Path(nodes[81], nodes[64], 1));
    nodes[81].addPath(new Path(nodes[81], nodes[82], 1));
    nodes[81].addPath(new Path(nodes[81], nodes[100], 1));
    
    nodes[82].addPath(new Path(nodes[82], nodes[65], 1));
    nodes[82].addPath(new Path(nodes[82], nodes[65], 2));
    nodes[82].addPath(new Path(nodes[82], nodes[66], 1));
    nodes[82].addPath(new Path(nodes[82], nodes[67], 2));
    nodes[82].addPath(new Path(nodes[82], nodes[81], 1));
    nodes[82].addPath(new Path(nodes[82], nodes[100], 2));
    nodes[82].addPath(new Path(nodes[82], nodes[101], 1));
    nodes[82].addPath(new Path(nodes[82], nodes[140], 2));
    
    nodes[83].addPath(new Path(nodes[83], nodes[101], 1));
    nodes[83].addPath(new Path(nodes[83], nodes[102], 1));
    
    nodes[84].addPath(new Path(nodes[84], nodes[67], 1));
    nodes[84].addPath(new Path(nodes[84], nodes[85], 1));
    
    nodes[85].addPath(new Path(nodes[85], nodes[68], 1));
    nodes[85].addPath(new Path(nodes[85], nodes[84], 1));
    nodes[85].addPath(new Path(nodes[85], nodes[103], 1));
    
    nodes[86].addPath(new Path(nodes[86], nodes[52], 2));
    nodes[86].addPath(new Path(nodes[86], nodes[69], 1));
    nodes[86].addPath(new Path(nodes[86], nodes[87], 2));
    nodes[86].addPath(new Path(nodes[86], nodes[102], 2));
    nodes[86].addPath(new Path(nodes[86], nodes[103], 1));
    nodes[86].addPath(new Path(nodes[86], nodes[104], 1));
    nodes[86].addPath(new Path(nodes[86], nodes[116], 2));
    
    nodes[87].addPath(new Path(nodes[87], nodes[41], 2));
    nodes[87].addPath(new Path(nodes[87], nodes[70], 1));
    nodes[87].addPath(new Path(nodes[87], nodes[86], 2));
    nodes[87].addPath(new Path(nodes[87], nodes[88], 1));
    nodes[87].addPath(new Path(nodes[87], nodes[105], 2));
    
    nodes[88].addPath(new Path(nodes[88], nodes[87], 1));
    nodes[88].addPath(new Path(nodes[88], nodes[89], 1));
    nodes[88].addPath(new Path(nodes[88], nodes[117], 1));
    
    nodes[89].addPath(new Path(nodes[89], nodes[13], 3));
    nodes[89].addPath(new Path(nodes[89], nodes[55], 2));
    nodes[89].addPath(new Path(nodes[89], nodes[67], 3));
    nodes[89].addPath(new Path(nodes[89], nodes[71], 1));
    nodes[89].addPath(new Path(nodes[89], nodes[88], 1));
    nodes[89].addPath(new Path(nodes[89], nodes[105], 1));
    nodes[89].addPath(new Path(nodes[89], nodes[105], 2));
    nodes[89].addPath(new Path(nodes[89], nodes[128], 3));
    nodes[89].addPath(new Path(nodes[89], nodes[140], 3));
    
    nodes[90].addPath(new Path(nodes[90], nodes[72], 1));
    nodes[90].addPath(new Path(nodes[90], nodes[91], 1));
    nodes[90].addPath(new Path(nodes[90], nodes[105], 1));
    
    nodes[91].addPath(new Path(nodes[91], nodes[56], 1));
    nodes[91].addPath(new Path(nodes[91], nodes[72], 1));
    nodes[91].addPath(new Path(nodes[91], nodes[90], 1));
    nodes[91].addPath(new Path(nodes[91], nodes[105], 1));
    nodes[91].addPath(new Path(nodes[91], nodes[107], 1));
    
    nodes[92].addPath(new Path(nodes[92], nodes[73], 1));
    nodes[92].addPath(new Path(nodes[92], nodes[74], 1));
    nodes[92].addPath(new Path(nodes[92], nodes[93], 1));
    nodes[93].addPath(new Path(nodes[93], nodes[79], 3));
    
    nodes[93].addPath(new Path(nodes[93], nodes[92], 1));
    nodes[93].addPath(new Path(nodes[93], nodes[94], 1));
    nodes[93].addPath(new Path(nodes[93], nodes[94], 2));
    
    nodes[94].addPath(new Path(nodes[94], nodes[74], 2));
    nodes[94].addPath(new Path(nodes[94], nodes[75], 1));
    nodes[94].addPath(new Path(nodes[94], nodes[77], 2));
    nodes[94].addPath(new Path(nodes[94], nodes[93], 1));
    nodes[94].addPath(new Path(nodes[94], nodes[93], 2));
    nodes[94].addPath(new Path(nodes[94], nodes[95], 1));
    
    nodes[95].addPath(new Path(nodes[95], nodes[77], 1));
    nodes[95].addPath(new Path(nodes[95], nodes[94], 1));
    nodes[95].addPath(new Path(nodes[95], nodes[122], 1));
    
    nodes[96].addPath(new Path(nodes[96], nodes[77], 1));
    nodes[96].addPath(new Path(nodes[96], nodes[97], 1));
    nodes[96].addPath(new Path(nodes[96], nodes[109], 1));
    
    nodes[97].addPath(new Path(nodes[97], nodes[78], 1));
    nodes[97].addPath(new Path(nodes[97], nodes[96], 1));
    nodes[97].addPath(new Path(nodes[97], nodes[98], 1));
    nodes[97].addPath(new Path(nodes[97], nodes[109], 1));
    
    nodes[98].addPath(new Path(nodes[98], nodes[79], 1));
    nodes[98].addPath(new Path(nodes[98], nodes[97], 1));
    nodes[98].addPath(new Path(nodes[98], nodes[99], 1));
    nodes[98].addPath(new Path(nodes[98], nodes[110], 1));
    
    nodes[99].addPath(new Path(nodes[99], nodes[80], 1));
    nodes[99].addPath(new Path(nodes[99], nodes[98], 1));
    nodes[99].addPath(new Path(nodes[99], nodes[110], 1));
    nodes[99].addPath(new Path(nodes[99], nodes[112], 1));
    
    nodes[100].addPath(new Path(nodes[100], nodes[63], 2));
    nodes[100].addPath(new Path(nodes[100], nodes[80], 1));
    nodes[100].addPath(new Path(nodes[100], nodes[81], 1));
    nodes[100].addPath(new Path(nodes[100], nodes[82], 2));
    nodes[100].addPath(new Path(nodes[100], nodes[101], 1));
    nodes[100].addPath(new Path(nodes[100], nodes[111], 2));
    nodes[100].addPath(new Path(nodes[100], nodes[112], 1));
    
    nodes[100].addPath(new Path(nodes[100], nodes[113], 1));
    nodes[101].addPath(new Path(nodes[101], nodes[82], 1));
    nodes[101].addPath(new Path(nodes[101], nodes[83], 1));
    nodes[101].addPath(new Path(nodes[101], nodes[100], 1));
    nodes[101].addPath(new Path(nodes[101], nodes[114], 1));
    
    nodes[102].addPath(new Path(nodes[102], nodes[67], 2));
    nodes[102].addPath(new Path(nodes[102], nodes[83], 1));
    nodes[102].addPath(new Path(nodes[102], nodes[86], 2));
    nodes[102].addPath(new Path(nodes[102], nodes[103], 1));
    nodes[102].addPath(new Path(nodes[102], nodes[115], 1));
    nodes[102].addPath(new Path(nodes[102], nodes[127], 2));
    
    nodes[103].addPath(new Path(nodes[103], nodes[85], 1));
    nodes[103].addPath(new Path(nodes[103], nodes[86], 1));
    nodes[103].addPath(new Path(nodes[103], nodes[102], 1));
    
    nodes[104].addPath(new Path(nodes[104], nodes[86], 1));
    nodes[104].addPath(new Path(nodes[104], nodes[116], 1));
    
    nodes[105].addPath(new Path(nodes[105], nodes[72], 2));
    nodes[105].addPath(new Path(nodes[105], nodes[87], 2));
    nodes[105].addPath(new Path(nodes[105], nodes[89], 1));
    nodes[105].addPath(new Path(nodes[105], nodes[89], 2));
    nodes[105].addPath(new Path(nodes[105], nodes[90], 1));
    nodes[105].addPath(new Path(nodes[105], nodes[91], 1));
    nodes[105].addPath(new Path(nodes[105], nodes[106], 1));
    nodes[105].addPath(new Path(nodes[105], nodes[107], 2));
    nodes[105].addPath(new Path(nodes[105], nodes[108], 1));
    nodes[105].addPath(new Path(nodes[105], nodes[108], 2));
    
    nodes[106].addPath(new Path(nodes[106], nodes[105], 1));
    nodes[106].addPath(new Path(nodes[106], nodes[107], 1));
    
    nodes[107].addPath(new Path(nodes[107], nodes[72], 2));
    nodes[107].addPath(new Path(nodes[107], nodes[91], 1));
    nodes[107].addPath(new Path(nodes[107], nodes[105], 2));
    nodes[107].addPath(new Path(nodes[107], nodes[106], 1));
    nodes[107].addPath(new Path(nodes[107], nodes[119], 1));
    nodes[107].addPath(new Path(nodes[107], nodes[161], 2));
    
    nodes[108].addPath(new Path(nodes[108], nodes[105], 1));
    nodes[108].addPath(new Path(nodes[108], nodes[105], 2));
    nodes[108].addPath(new Path(nodes[108], nodes[116], 2));
    nodes[108].addPath(new Path(nodes[108], nodes[117], 1));
    nodes[108].addPath(new Path(nodes[108], nodes[119], 1));
    nodes[108].addPath(new Path(nodes[108], nodes[135], 2));
    
    nodes[109].addPath(new Path(nodes[109], nodes[96], 1));
    nodes[109].addPath(new Path(nodes[109], nodes[97], 1));
    nodes[109].addPath(new Path(nodes[109], nodes[110], 1));
    nodes[109].addPath(new Path(nodes[109], nodes[124], 1));
    
    nodes[110].addPath(new Path(nodes[110], nodes[98], 1));
    nodes[110].addPath(new Path(nodes[110], nodes[99], 1));
    nodes[110].addPath(new Path(nodes[110], nodes[109], 1));
    nodes[110].addPath(new Path(nodes[110], nodes[111], 1));
    
    nodes[111].addPath(new Path(nodes[111], nodes[67], 3));
    nodes[111].addPath(new Path(nodes[111], nodes[79], 3));
    nodes[111].addPath(new Path(nodes[111], nodes[100], 2));
    nodes[111].addPath(new Path(nodes[111], nodes[110], 1));
    nodes[111].addPath(new Path(nodes[111], nodes[112], 1));
    nodes[111].addPath(new Path(nodes[111], nodes[124], 1));
    nodes[111].addPath(new Path(nodes[111], nodes[124], 2));
    nodes[111].addPath(new Path(nodes[111], nodes[153], 3));
    nodes[111].addPath(new Path(nodes[111], nodes[163], 3));
    
    nodes[112].addPath(new Path(nodes[112], nodes[99], 1));
    nodes[112].addPath(new Path(nodes[112], nodes[100], 1));
    nodes[112].addPath(new Path(nodes[112], nodes[111], 1));
    nodes[112].addPath(new Path(nodes[112], nodes[125], 1));
    
    nodes[113].addPath(new Path(nodes[113], nodes[100], 1));
    nodes[113].addPath(new Path(nodes[113], nodes[114], 1));
    nodes[113].addPath(new Path(nodes[113], nodes[125], 1));
    
    nodes[114].addPath(new Path(nodes[114], nodes[101], 1));
    nodes[114].addPath(new Path(nodes[114], nodes[113], 1));
    nodes[114].addPath(new Path(nodes[114], nodes[115], 1));
    nodes[114].addPath(new Path(nodes[114], nodes[126], 1));
    nodes[114].addPath(new Path(nodes[114], nodes[131], 1));
    nodes[114].addPath(new Path(nodes[114], nodes[132], 1));
    
    nodes[115].addPath(new Path(nodes[115], nodes[102], 1));
    nodes[115].addPath(new Path(nodes[115], nodes[114], 1));
    nodes[115].addPath(new Path(nodes[115], nodes[126], 1));
    nodes[115].addPath(new Path(nodes[115], nodes[127], 1));
    
    nodes[116].addPath(new Path(nodes[116], nodes[86], 2));
    nodes[116].addPath(new Path(nodes[116], nodes[104], 1));
    nodes[116].addPath(new Path(nodes[116], nodes[108], 2));
    nodes[116].addPath(new Path(nodes[116], nodes[117], 1));
    nodes[116].addPath(new Path(nodes[116], nodes[118], 1));
    nodes[116].addPath(new Path(nodes[116], nodes[127], 1));
    nodes[116].addPath(new Path(nodes[116], nodes[127], 2));
    nodes[116].addPath(new Path(nodes[116], nodes[142], 2));
    
    nodes[117].addPath(new Path(nodes[117], nodes[88], 1));
    nodes[117].addPath(new Path(nodes[117], nodes[108], 1));
    nodes[117].addPath(new Path(nodes[117], nodes[116], 1));
    nodes[117].addPath(new Path(nodes[117], nodes[129], 1));
    
    nodes[118].addPath(new Path(nodes[118], nodes[116], 1));
    nodes[118].addPath(new Path(nodes[118], nodes[129], 1));
    nodes[118].addPath(new Path(nodes[118], nodes[134], 1));
    nodes[118].addPath(new Path(nodes[118], nodes[142], 1));
    
    nodes[119].addPath(new Path(nodes[119], nodes[107], 1));
    nodes[119].addPath(new Path(nodes[119], nodes[108], 1));
    nodes[119].addPath(new Path(nodes[119], nodes[136], 1));
    
    nodes[120].addPath(new Path(nodes[120], nodes[121], 1));
    nodes[120].addPath(new Path(nodes[120], nodes[144], 1));
    
    nodes[121].addPath(new Path(nodes[121], nodes[120], 1));
    nodes[121].addPath(new Path(nodes[121], nodes[122], 1));
    nodes[121].addPath(new Path(nodes[121], nodes[145], 1));
    
    nodes[122].addPath(new Path(nodes[122], nodes[95], 1));
    nodes[122].addPath(new Path(nodes[122], nodes[121], 1));
    nodes[122].addPath(new Path(nodes[122], nodes[123], 1));
    nodes[122].addPath(new Path(nodes[122], nodes[123], 2));
    nodes[122].addPath(new Path(nodes[122], nodes[144], 2));
    nodes[122].addPath(new Path(nodes[122], nodes[146], 1));
    
    nodes[123].addPath(new Path(nodes[123], nodes[122], 1));
    nodes[123].addPath(new Path(nodes[123], nodes[122], 2));
    nodes[123].addPath(new Path(nodes[123], nodes[124], 1));
    nodes[123].addPath(new Path(nodes[123], nodes[124], 2));
    nodes[123].addPath(new Path(nodes[123], nodes[137], 1));
    nodes[123].addPath(new Path(nodes[123], nodes[144], 2));
    nodes[123].addPath(new Path(nodes[123], nodes[148], 1));
    nodes[123].addPath(new Path(nodes[123], nodes[149], 1));
    nodes[123].addPath(new Path(nodes[123], nodes[165], 2));
    
    nodes[124].addPath(new Path(nodes[124], nodes[77], 2));
    nodes[124].addPath(new Path(nodes[124], nodes[109], 1));
    nodes[124].addPath(new Path(nodes[124], nodes[111], 1));
    nodes[124].addPath(new Path(nodes[124], nodes[111], 2));
    nodes[124].addPath(new Path(nodes[124], nodes[123], 1));
    nodes[124].addPath(new Path(nodes[124], nodes[123], 2));
    nodes[124].addPath(new Path(nodes[124], nodes[130], 1));
    nodes[124].addPath(new Path(nodes[124], nodes[138], 1));
    nodes[124].addPath(new Path(nodes[124], nodes[153], 2));
    
    nodes[125].addPath(new Path(nodes[125], nodes[112], 1));
    nodes[125].addPath(new Path(nodes[125], nodes[113], 1));
    nodes[125].addPath(new Path(nodes[125], nodes[131], 1));
    
    nodes[126].addPath(new Path(nodes[126], nodes[114], 1));
    nodes[126].addPath(new Path(nodes[126], nodes[115], 1));
    nodes[126].addPath(new Path(nodes[126], nodes[127], 1));
    nodes[126].addPath(new Path(nodes[126], nodes[140], 1));
    
    nodes[127].addPath(new Path(nodes[127], nodes[102], 2));
    nodes[127].addPath(new Path(nodes[127], nodes[115], 1));
    nodes[127].addPath(new Path(nodes[127], nodes[116], 1));
    nodes[127].addPath(new Path(nodes[127], nodes[116], 2));
    nodes[127].addPath(new Path(nodes[127], nodes[126], 1));
    nodes[127].addPath(new Path(nodes[127], nodes[133], 1));
    nodes[127].addPath(new Path(nodes[127], nodes[133], 2));
    nodes[127].addPath(new Path(nodes[127], nodes[134], 1));
    
    nodes[128].addPath(new Path(nodes[128], nodes[89], 3));
    nodes[128].addPath(new Path(nodes[128], nodes[135], 2));
    nodes[128].addPath(new Path(nodes[128], nodes[140], 3));
    nodes[128].addPath(new Path(nodes[128], nodes[142], 1));
    nodes[128].addPath(new Path(nodes[128], nodes[142], 2));
    nodes[128].addPath(new Path(nodes[128], nodes[143], 1));
    nodes[128].addPath(new Path(nodes[128], nodes[160], 1));
    nodes[128].addPath(new Path(nodes[128], nodes[161], 2));
    nodes[128].addPath(new Path(nodes[128], nodes[172], 1));
    nodes[128].addPath(new Path(nodes[128], nodes[185], 3));
    nodes[128].addPath(new Path(nodes[128], nodes[187], 2));
    nodes[128].addPath(new Path(nodes[128], nodes[188], 1));
    nodes[128].addPath(new Path(nodes[128], nodes[199], 2));
    
    nodes[129].addPath(new Path(nodes[129], nodes[117], 1));
    nodes[129].addPath(new Path(nodes[129], nodes[118], 1));
    nodes[129].addPath(new Path(nodes[129], nodes[135], 1));
    nodes[129].addPath(new Path(nodes[129], nodes[142], 1));
    nodes[129].addPath(new Path(nodes[129], nodes[143], 1));
    
    nodes[130].addPath(new Path(nodes[130], nodes[124], 1));
    nodes[130].addPath(new Path(nodes[130], nodes[131], 1));
    nodes[130].addPath(new Path(nodes[130], nodes[139], 1));
    
    nodes[131].addPath(new Path(nodes[131], nodes[114], 1));
    nodes[131].addPath(new Path(nodes[131], nodes[125], 1));
    nodes[131].addPath(new Path(nodes[131], nodes[130], 1));
    
    nodes[132].addPath(new Path(nodes[132], nodes[114], 1));
    nodes[132].addPath(new Path(nodes[132], nodes[140], 1));
    
    nodes[133].addPath(new Path(nodes[133], nodes[127], 1));
    nodes[133].addPath(new Path(nodes[133], nodes[127], 2));
    nodes[133].addPath(new Path(nodes[133], nodes[140], 1));
    nodes[133].addPath(new Path(nodes[133], nodes[140], 2));
    nodes[133].addPath(new Path(nodes[133], nodes[141], 1));
    nodes[133].addPath(new Path(nodes[133], nodes[157], 2));
    
    nodes[134].addPath(new Path(nodes[134], nodes[118], 1));
    nodes[134].addPath(new Path(nodes[134], nodes[127], 1));
    nodes[134].addPath(new Path(nodes[134], nodes[141], 1));
    nodes[134].addPath(new Path(nodes[134], nodes[142], 1));
    
    nodes[135].addPath(new Path(nodes[135], nodes[108], 2));
    nodes[135].addPath(new Path(nodes[135], nodes[128], 2));
    nodes[135].addPath(new Path(nodes[135], nodes[129], 1));
    nodes[135].addPath(new Path(nodes[135], nodes[136], 1));
    nodes[135].addPath(new Path(nodes[135], nodes[143], 1));
    nodes[135].addPath(new Path(nodes[135], nodes[161], 1));
    nodes[135].addPath(new Path(nodes[135], nodes[161], 2));
    
    nodes[136].addPath(new Path(nodes[136], nodes[119], 1));
    nodes[136].addPath(new Path(nodes[136], nodes[135], 1));
    nodes[136].addPath(new Path(nodes[136], nodes[162], 1));
    
    nodes[137].addPath(new Path(nodes[137], nodes[123], 1));
    nodes[137].addPath(new Path(nodes[137], nodes[147], 1));
    
    nodes[138].addPath(new Path(nodes[138], nodes[124], 1));
    nodes[138].addPath(new Path(nodes[138], nodes[150], 1));
    nodes[138].addPath(new Path(nodes[138], nodes[152], 1));
    
    nodes[139].addPath(new Path(nodes[139], nodes[130], 1));
    nodes[139].addPath(new Path(nodes[139], nodes[140], 1));
    nodes[139].addPath(new Path(nodes[139], nodes[153], 1));
    nodes[139].addPath(new Path(nodes[139], nodes[154], 1));
    
    nodes[140].addPath(new Path(nodes[140], nodes[82], 2));
    nodes[140].addPath(new Path(nodes[140], nodes[89], 3));
    nodes[140].addPath(new Path(nodes[140], nodes[126], 1));
    nodes[140].addPath(new Path(nodes[140], nodes[128], 3));
    nodes[140].addPath(new Path(nodes[140], nodes[132], 1));
    nodes[140].addPath(new Path(nodes[140], nodes[133], 1));
    nodes[140].addPath(new Path(nodes[140], nodes[133], 2));
    nodes[140].addPath(new Path(nodes[140], nodes[139], 1));
    nodes[140].addPath(new Path(nodes[140], nodes[153], 3));
    nodes[140].addPath(new Path(nodes[140], nodes[154], 1));
    nodes[140].addPath(new Path(nodes[140], nodes[154], 2));
    nodes[140].addPath(new Path(nodes[140], nodes[156], 1));
    nodes[140].addPath(new Path(nodes[140], nodes[156], 2));
    
    nodes[141].addPath(new Path(nodes[141], nodes[133], 1));
    nodes[141].addPath(new Path(nodes[141], nodes[134], 1));
    nodes[141].addPath(new Path(nodes[141], nodes[142], 1));
    nodes[141].addPath(new Path(nodes[141], nodes[158], 1));
    
    nodes[142].addPath(new Path(nodes[142], nodes[116], 2));
    nodes[142].addPath(new Path(nodes[142], nodes[118], 1));
    nodes[142].addPath(new Path(nodes[142], nodes[128], 1));
    nodes[142].addPath(new Path(nodes[142], nodes[128], 2));
    nodes[142].addPath(new Path(nodes[142], nodes[129], 1));
    nodes[142].addPath(new Path(nodes[142], nodes[134], 1));
    nodes[142].addPath(new Path(nodes[142], nodes[141], 1));
    nodes[142].addPath(new Path(nodes[142], nodes[143], 1));
    nodes[142].addPath(new Path(nodes[142], nodes[157], 2));
    nodes[142].addPath(new Path(nodes[142], nodes[158], 1));
    
    nodes[143].addPath(new Path(nodes[143], nodes[128], 1));
    nodes[143].addPath(new Path(nodes[143], nodes[129], 1));
    nodes[143].addPath(new Path(nodes[143], nodes[135], 1));
    nodes[143].addPath(new Path(nodes[143], nodes[142], 1));
    nodes[143].addPath(new Path(nodes[143], nodes[160], 1));
    
    nodes[144].addPath(new Path(nodes[144], nodes[120], 1));
    nodes[144].addPath(new Path(nodes[144], nodes[122], 2));
    nodes[144].addPath(new Path(nodes[144], nodes[123], 2));
    nodes[144].addPath(new Path(nodes[144], nodes[145], 1));
    nodes[144].addPath(new Path(nodes[144], nodes[163], 2));
    nodes[144].addPath(new Path(nodes[144], nodes[177], 1));
    
    nodes[145].addPath(new Path(nodes[145], nodes[121], 1));
    nodes[145].addPath(new Path(nodes[145], nodes[144], 1));
    nodes[145].addPath(new Path(nodes[145], nodes[146], 1));
    
    nodes[146].addPath(new Path(nodes[146], nodes[122], 1));
    nodes[146].addPath(new Path(nodes[146], nodes[145], 1));
    nodes[146].addPath(new Path(nodes[146], nodes[147], 1));
    nodes[146].addPath(new Path(nodes[146], nodes[163], 1));
    
    nodes[147].addPath(new Path(nodes[147], nodes[137], 1));
    nodes[147].addPath(new Path(nodes[147], nodes[146], 1));
    nodes[147].addPath(new Path(nodes[147], nodes[164], 1));
    
    nodes[148].addPath(new Path(nodes[148], nodes[123], 1));
    nodes[148].addPath(new Path(nodes[148], nodes[149], 1));
    nodes[148].addPath(new Path(nodes[148], nodes[164], 1));
    
    nodes[149].addPath(new Path(nodes[149], nodes[123], 1));
    nodes[149].addPath(new Path(nodes[149], nodes[148], 1));
    nodes[149].addPath(new Path(nodes[149], nodes[150], 1));
    nodes[149].addPath(new Path(nodes[149], nodes[165], 1));
    
    nodes[150].addPath(new Path(nodes[150], nodes[138], 1));
    nodes[150].addPath(new Path(nodes[150], nodes[149], 1));
    nodes[150].addPath(new Path(nodes[150], nodes[151], 1));
    
    nodes[151].addPath(new Path(nodes[151], nodes[150], 1));
    nodes[151].addPath(new Path(nodes[151], nodes[152], 1));
    nodes[151].addPath(new Path(nodes[151], nodes[165], 1));
    nodes[151].addPath(new Path(nodes[151], nodes[166], 1));
    
    nodes[152].addPath(new Path(nodes[152], nodes[138], 1));
    nodes[152].addPath(new Path(nodes[152], nodes[151], 1));
    nodes[152].addPath(new Path(nodes[152], nodes[153], 1));
    
    nodes[153].addPath(new Path(nodes[153], nodes[111], 3));
    nodes[153].addPath(new Path(nodes[153], nodes[124], 2));
    nodes[153].addPath(new Path(nodes[153], nodes[139], 1));
    nodes[153].addPath(new Path(nodes[153], nodes[140], 3));
    nodes[153].addPath(new Path(nodes[153], nodes[152], 1));
    nodes[153].addPath(new Path(nodes[153], nodes[154], 1));
    nodes[153].addPath(new Path(nodes[153], nodes[154], 2));
    nodes[153].addPath(new Path(nodes[153], nodes[163], 3));
    nodes[153].addPath(new Path(nodes[153], nodes[166], 1));
    nodes[153].addPath(new Path(nodes[153], nodes[167], 1));
    nodes[153].addPath(new Path(nodes[153], nodes[180], 2));
    nodes[153].addPath(new Path(nodes[153], nodes[184], 2));
    nodes[153].addPath(new Path(nodes[153], nodes[185], 3));
    
    nodes[154].addPath(new Path(nodes[154], nodes[139], 1));
    nodes[154].addPath(new Path(nodes[154], nodes[140], 1));
    nodes[154].addPath(new Path(nodes[154], nodes[140], 2));
    nodes[154].addPath(new Path(nodes[154], nodes[153], 1));
    nodes[154].addPath(new Path(nodes[154], nodes[153], 2));
    nodes[154].addPath(new Path(nodes[154], nodes[155], 1));
    nodes[154].addPath(new Path(nodes[154], nodes[156], 2));
    
    nodes[155].addPath(new Path(nodes[155], nodes[154], 1));
    nodes[155].addPath(new Path(nodes[155], nodes[156], 1));
    nodes[155].addPath(new Path(nodes[155], nodes[167], 1));
    nodes[155].addPath(new Path(nodes[155], nodes[168], 1));
    
    nodes[156].addPath(new Path(nodes[156], nodes[140], 1));
    nodes[156].addPath(new Path(nodes[156], nodes[140], 2));
    nodes[156].addPath(new Path(nodes[156], nodes[154], 2));
    nodes[156].addPath(new Path(nodes[156], nodes[155], 1));
    nodes[156].addPath(new Path(nodes[156], nodes[157], 1));
    nodes[156].addPath(new Path(nodes[156], nodes[157], 2));
    nodes[156].addPath(new Path(nodes[156], nodes[169], 1));
    nodes[156].addPath(new Path(nodes[156], nodes[184], 2));
    
    nodes[157].addPath(new Path(nodes[157], nodes[133], 2));
    nodes[157].addPath(new Path(nodes[157], nodes[142], 2));
    nodes[157].addPath(new Path(nodes[157], nodes[156], 1));
    nodes[157].addPath(new Path(nodes[157], nodes[156], 2));
    nodes[157].addPath(new Path(nodes[157], nodes[158], 1));
    nodes[157].addPath(new Path(nodes[157], nodes[170], 1));
    nodes[157].addPath(new Path(nodes[157], nodes[185], 2));
    
    nodes[158].addPath(new Path(nodes[158], nodes[141], 1));
    nodes[158].addPath(new Path(nodes[158], nodes[142], 1));
    nodes[158].addPath(new Path(nodes[158], nodes[157], 1));
    nodes[158].addPath(new Path(nodes[158], nodes[159], 1));
    
    nodes[159].addPath(new Path(nodes[159], nodes[158], 1));
    nodes[159].addPath(new Path(nodes[159], nodes[170], 1));
    nodes[159].addPath(new Path(nodes[159], nodes[172], 1));
    nodes[159].addPath(new Path(nodes[159], nodes[186], 1));
    nodes[159].addPath(new Path(nodes[159], nodes[198], 1));
    
    nodes[160].addPath(new Path(nodes[160], nodes[128], 1));
    nodes[160].addPath(new Path(nodes[160], nodes[143], 1));
    nodes[160].addPath(new Path(nodes[160], nodes[161], 1));
    nodes[160].addPath(new Path(nodes[160], nodes[173], 1));
    
    nodes[161].addPath(new Path(nodes[161], nodes[107], 2));
    nodes[161].addPath(new Path(nodes[161], nodes[128], 2));
    nodes[161].addPath(new Path(nodes[161], nodes[135], 1));
    nodes[161].addPath(new Path(nodes[161], nodes[135], 2));
    nodes[161].addPath(new Path(nodes[161], nodes[160], 1));
    nodes[161].addPath(new Path(nodes[161], nodes[174], 1));
    nodes[161].addPath(new Path(nodes[161], nodes[199], 2));
    
    nodes[162].addPath(new Path(nodes[162], nodes[136], 1));
    nodes[162].addPath(new Path(nodes[162], nodes[175], 1));
    
    nodes[163].addPath(new Path(nodes[163], nodes[111], 3));
    nodes[163].addPath(new Path(nodes[163], nodes[144], 2));
    nodes[163].addPath(new Path(nodes[163], nodes[146], 1));
    nodes[163].addPath(new Path(nodes[163], nodes[153], 3));
    nodes[163].addPath(new Path(nodes[163], nodes[176], 2));
    nodes[163].addPath(new Path(nodes[163], nodes[177], 1));
    nodes[163].addPath(new Path(nodes[163], nodes[191], 2));
    
    nodes[164].addPath(new Path(nodes[164], nodes[147], 1));
    nodes[164].addPath(new Path(nodes[164], nodes[148], 1));
    nodes[164].addPath(new Path(nodes[164], nodes[178], 1));
    nodes[164].addPath(new Path(nodes[164], nodes[179], 1));
    
    nodes[165].addPath(new Path(nodes[165], nodes[123], 2));
    nodes[165].addPath(new Path(nodes[165], nodes[149], 1));
    nodes[165].addPath(new Path(nodes[165], nodes[151], 1));
    nodes[165].addPath(new Path(nodes[165], nodes[179], 1));
    nodes[165].addPath(new Path(nodes[165], nodes[180], 1));
    nodes[165].addPath(new Path(nodes[165], nodes[180], 2));
    nodes[165].addPath(new Path(nodes[165], nodes[191], 2));
    
    nodes[166].addPath(new Path(nodes[166], nodes[151], 1));
    nodes[166].addPath(new Path(nodes[166], nodes[153], 1));
    nodes[166].addPath(new Path(nodes[166], nodes[181], 1));
    nodes[166].addPath(new Path(nodes[166], nodes[183], 1));
    
    nodes[167].addPath(new Path(nodes[167], nodes[153], 1));
    nodes[167].addPath(new Path(nodes[167], nodes[155], 1));
    nodes[167].addPath(new Path(nodes[167], nodes[168], 1));
    nodes[167].addPath(new Path(nodes[167], nodes[183], 1));
    
    nodes[168].addPath(new Path(nodes[168], nodes[155], 1));
    nodes[168].addPath(new Path(nodes[168], nodes[167], 1));
    nodes[168].addPath(new Path(nodes[168], nodes[184], 1));
    
    nodes[169].addPath(new Path(nodes[169], nodes[156], 1));
    nodes[169].addPath(new Path(nodes[169], nodes[184], 1));
    
    nodes[170].addPath(new Path(nodes[170], nodes[157], 1));
    nodes[170].addPath(new Path(nodes[170], nodes[159], 1));
    nodes[170].addPath(new Path(nodes[170], nodes[185], 1));
    
    nodes[171].addPath(new Path(nodes[171], nodes[173], 1));
    nodes[171].addPath(new Path(nodes[171], nodes[175], 1));
    nodes[171].addPath(new Path(nodes[171], nodes[199], 1));
    
    nodes[172].addPath(new Path(nodes[172], nodes[128], 1));
    nodes[172].addPath(new Path(nodes[172], nodes[159], 1));
    nodes[172].addPath(new Path(nodes[172], nodes[187], 1));
    
    nodes[173].addPath(new Path(nodes[173], nodes[160], 1));
    nodes[173].addPath(new Path(nodes[173], nodes[171], 1));
    nodes[173].addPath(new Path(nodes[173], nodes[174], 1));
    nodes[173].addPath(new Path(nodes[173], nodes[188], 1));
    
    nodes[174].addPath(new Path(nodes[174], nodes[161], 1));
    nodes[174].addPath(new Path(nodes[174], nodes[173], 1));
    nodes[174].addPath(new Path(nodes[174], nodes[175], 1));
    nodes[175].addPath(new Path(nodes[175], nodes[162], 1));
    
    nodes[175].addPath(new Path(nodes[175], nodes[171], 1));
    nodes[175].addPath(new Path(nodes[175], nodes[174], 1));
    
    nodes[176].addPath(new Path(nodes[176], nodes[163], 2));
    nodes[176].addPath(new Path(nodes[176], nodes[177], 1));
    nodes[176].addPath(new Path(nodes[176], nodes[189], 1));
    nodes[176].addPath(new Path(nodes[176], nodes[190], 2));
    
    nodes[177].addPath(new Path(nodes[177], nodes[144], 1));
    nodes[177].addPath(new Path(nodes[177], nodes[163], 1));
    nodes[177].addPath(new Path(nodes[177], nodes[176], 1));
    
    nodes[178].addPath(new Path(nodes[178], nodes[164], 1));
    nodes[178].addPath(new Path(nodes[178], nodes[189], 1));
    nodes[178].addPath(new Path(nodes[178], nodes[191], 1));
    
    nodes[179].addPath(new Path(nodes[179], nodes[164], 1));
    nodes[179].addPath(new Path(nodes[179], nodes[165], 1));
    nodes[179].addPath(new Path(nodes[179], nodes[191], 1));
    
    nodes[180].addPath(new Path(nodes[180], nodes[153], 2));
    nodes[180].addPath(new Path(nodes[180], nodes[165], 1));
    nodes[180].addPath(new Path(nodes[180], nodes[165], 2));
    nodes[180].addPath(new Path(nodes[180], nodes[181], 1));
    nodes[180].addPath(new Path(nodes[180], nodes[184], 2));
    nodes[180].addPath(new Path(nodes[180], nodes[190], 2));
    nodes[180].addPath(new Path(nodes[180], nodes[193], 1));
    
    nodes[181].addPath(new Path(nodes[181], nodes[166], 1));
    nodes[181].addPath(new Path(nodes[181], nodes[180], 1));
    nodes[181].addPath(new Path(nodes[181], nodes[182], 1));
    nodes[181].addPath(new Path(nodes[181], nodes[193], 1));
    
    nodes[182].addPath(new Path(nodes[182], nodes[181], 1));
    nodes[182].addPath(new Path(nodes[182], nodes[183], 1));
    nodes[182].addPath(new Path(nodes[182], nodes[195], 1));
    
    nodes[183].addPath(new Path(nodes[183], nodes[166], 1));
    nodes[183].addPath(new Path(nodes[183], nodes[167], 1));
    nodes[183].addPath(new Path(nodes[183], nodes[182], 1));
    nodes[183].addPath(new Path(nodes[183], nodes[196], 1));
    
    nodes[184].addPath(new Path(nodes[184], nodes[153], 2));
    nodes[184].addPath(new Path(nodes[184], nodes[156], 2));
    nodes[184].addPath(new Path(nodes[184], nodes[168], 1));
    nodes[184].addPath(new Path(nodes[184], nodes[169], 1));
    nodes[184].addPath(new Path(nodes[184], nodes[180], 2));
    nodes[184].addPath(new Path(nodes[184], nodes[185], 1));
    nodes[184].addPath(new Path(nodes[184], nodes[185], 2));
    nodes[184].addPath(new Path(nodes[184], nodes[196], 1));
    nodes[184].addPath(new Path(nodes[184], nodes[197], 1));
    
    nodes[185].addPath(new Path(nodes[185], nodes[128], 3));
    nodes[185].addPath(new Path(nodes[185], nodes[153], 3));
    nodes[185].addPath(new Path(nodes[185], nodes[157], 2));
    nodes[185].addPath(new Path(nodes[185], nodes[170], 1));
    nodes[185].addPath(new Path(nodes[185], nodes[184], 1));
    nodes[185].addPath(new Path(nodes[185], nodes[184], 2));
    nodes[185].addPath(new Path(nodes[185], nodes[186], 1));
    nodes[185].addPath(new Path(nodes[185], nodes[187], 2));
    
    nodes[186].addPath(new Path(nodes[186], nodes[159], 1));
    nodes[186].addPath(new Path(nodes[186], nodes[185], 1));
    nodes[186].addPath(new Path(nodes[186], nodes[198], 1));
    
    nodes[187].addPath(new Path(nodes[187], nodes[128], 2));
    nodes[187].addPath(new Path(nodes[187], nodes[172], 1));
    nodes[187].addPath(new Path(nodes[187], nodes[185], 2));
    nodes[187].addPath(new Path(nodes[187], nodes[188], 1));
    nodes[187].addPath(new Path(nodes[187], nodes[198], 1));
    
    nodes[188].addPath(new Path(nodes[188], nodes[128], 1));
    nodes[188].addPath(new Path(nodes[188], nodes[173], 1));
    nodes[188].addPath(new Path(nodes[188], nodes[187], 1));
    nodes[188].addPath(new Path(nodes[188], nodes[199], 1));
    
    nodes[189].addPath(new Path(nodes[189], nodes[176], 1));
    nodes[189].addPath(new Path(nodes[189], nodes[178], 1));
    nodes[189].addPath(new Path(nodes[189], nodes[190], 1));
    
    nodes[190].addPath(new Path(nodes[190], nodes[176], 2));
    nodes[190].addPath(new Path(nodes[190], nodes[180], 2));
    nodes[190].addPath(new Path(nodes[190], nodes[189], 1));
    nodes[190].addPath(new Path(nodes[190], nodes[191], 1));
    nodes[190].addPath(new Path(nodes[190], nodes[191], 2));
    nodes[190].addPath(new Path(nodes[190], nodes[192], 1));
    
    nodes[191].addPath(new Path(nodes[191], nodes[163], 2));
    nodes[191].addPath(new Path(nodes[191], nodes[165], 2));
    nodes[191].addPath(new Path(nodes[191], nodes[178], 1));
    nodes[191].addPath(new Path(nodes[191], nodes[179], 1));
    nodes[191].addPath(new Path(nodes[191], nodes[190], 1));
    nodes[191].addPath(new Path(nodes[191], nodes[190], 2));
    nodes[191].addPath(new Path(nodes[191], nodes[192], 1));
    
    nodes[192].addPath(new Path(nodes[192], nodes[190], 1));
    nodes[192].addPath(new Path(nodes[192], nodes[191], 1));
    nodes[192].addPath(new Path(nodes[192], nodes[194], 1));
    
    nodes[193].addPath(new Path(nodes[193], nodes[180], 1));
    nodes[193].addPath(new Path(nodes[193], nodes[181], 1));
    nodes[193].addPath(new Path(nodes[193], nodes[194], 1));
    
    nodes[194].addPath(new Path(nodes[194], nodes[192], 1));
    nodes[194].addPath(new Path(nodes[194], nodes[193], 1));
    nodes[194].addPath(new Path(nodes[194], nodes[195], 1));
    
    nodes[195].addPath(new Path(nodes[195], nodes[182], 1));
    nodes[195].addPath(new Path(nodes[195], nodes[194], 1));
    nodes[195].addPath(new Path(nodes[195], nodes[197], 1));
    
    nodes[196].addPath(new Path(nodes[196], nodes[183], 1));
    nodes[196].addPath(new Path(nodes[196], nodes[184], 1));
    nodes[196].addPath(new Path(nodes[196], nodes[197], 1));
    
    nodes[197].addPath(new Path(nodes[197], nodes[184], 1));
    nodes[197].addPath(new Path(nodes[197], nodes[195], 1));
    nodes[197].addPath(new Path(nodes[197], nodes[196], 1));
    
    nodes[198].addPath(new Path(nodes[198], nodes[159], 1));
    nodes[198].addPath(new Path(nodes[198], nodes[186], 1));
    nodes[198].addPath(new Path(nodes[198], nodes[187], 1));
    nodes[198].addPath(new Path(nodes[198], nodes[199], 1));
    
    nodes[199].addPath(new Path(nodes[199], nodes[128], 2));
    nodes[199].addPath(new Path(nodes[199], nodes[161], 2));
    nodes[199].addPath(new Path(nodes[199], nodes[171], 1));
    nodes[199].addPath(new Path(nodes[199], nodes[188], 1));
    nodes[199].addPath(new Path(nodes[199], nodes[198], 1));


  }

}
