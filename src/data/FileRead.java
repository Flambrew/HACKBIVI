package src.data;
import java.io.*;

public class FileRead {

    private String fName = "map.txt";
    private String directory = System.getProperty("user.home");
    private String absolutePath = directory + File.separator + fName;

    public void transReads() {

        try {

            FileReader fr = new FileReader(absolutePath);
            int ch = fr.read();
            while(ch != -1) {
                System.out.print((char)ch);
                fr.close();
            }
        } 
        catch (FileNotFoundException e) {} 
        catch (IOException e) {}
    }

    public void setRead(String newRead) {
        transReads() = newRead;
    }

}
