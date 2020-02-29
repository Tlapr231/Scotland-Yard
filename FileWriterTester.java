import java.io.FileWriter;
import java.io.BufferedWriter;

public class FileWriterTester {

  public static void main(String[] args) throws Exception {
    FileWriter writer = new FileWriter("GameLogs\\GameData.txt", true);
    BufferedWriter buffer = new BufferedWriter(writer);
    buffer.write("Writing the first thing.");
    buffer.write("Tried to reopen.");
    buffer.close();
    System.out.println("Success");
    }

}
