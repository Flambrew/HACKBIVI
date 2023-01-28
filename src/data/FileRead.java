package src.data;
import java.io.*;

public class FileRead {

    public File

    FileWriter(File file);

    public static void main(String args[]) throws IOException {
        File file = new File("Map.txt");

        file.createNewFile();

        FileWriter writer = new FileWriter(file);

        writer.write("hwevfwqlfvjqwkfviwgfwiqgfi");
        writer.flush();
        writer.close();

        FileReader fr = new FileReader(file);
        char[] a = new char[50];
        fr.read(a);

        for (char c : a)
                System.out.print(c);
        fr.close();

    }
}
