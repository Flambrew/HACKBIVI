package src.data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileRW {

    // private String fName = "Map.txt";
    // private String directory = System.getProperty("user.home");
    // private String absolutePath = directory + File.separator + fName;

    private static String activeFile;
    File dir = new File("C:\\Users\\camde\\OneDrive\\Desktop\\Hack BI\\HACKBIVI\\maps");

    public static String getActiveFile() {
        return activeFile;
    }

    public static void setActiveFile(String activeFile) {
        FileRW.activeFile = activeFile;
    }

    public static void transWrites(String name) {

        try {
            FileWriter fw = new FileWriter(name + ".txt");
            fw.close();
        } catch (IOException e) {
        }
    }

    public static String transReads() {
        try {
            return Files.readString(Paths.get(activeFile));
        } catch (IOException e) {
            return null;
        }
    }

    public static void transAdds(String in, Object... args) {
        in = String.format(in, args);
        try {
            FileWriter fw = new FileWriter(activeFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            {
                pw.println(in);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void transBGones(String in) {
        String content = transReads();

        String firstHalf = content.substring(0, content.indexOf(in));

        String secondHalf = content.substring(content.indexOf(in));

        String out = firstHalf + secondHalf.substring(secondHalf.indexOf("\n")) ;

        transWrites(out);
        
    }

    public static void transSummaries() {
        String l;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Map.txt"));
            while ((l = br.readLine()) != null) {
                System.out.println(l);
            }
            br.close();
        } catch (IOException e) {
        }
    }
}