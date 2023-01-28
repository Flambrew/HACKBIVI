package src.data;

import java.io.*;

public class FileRW {

    private String fName = "map.txt";
    private String directory = System.getProperty("user.home");
    private String absolutePath = directory + File.separator + fName;

    public String transWrites() {

        try {
            FileWriter fw = new FileWriter(absolutePath);

            String fContent = "guh";
            fw.write(fContent);
            fw.close();
            return fContent;
        } 
        catch (IOException e) {
            return null;
        }
    }

    public void setWrite(String newWrite) {
    }

}
