package src.data;

import java.io.*;

public class FileRW {

    private String fName = "Map.txt";
    private String directory = System.getProperty("user.home");
    private String absolutePath = directory + File.separator + fName;

    public static void transWrites(String name) {

        try {
            FileWriter fw = new FileWriter(name + ".txt");
            fw.close();
        } catch (IOException e) {
        }
    }

    public static void transReads() {
        
        try {
            
            FileReader fr = new FileReader("Map.txt");
            int ch = fr.read();
            if(ch != -1) {
                System.out.print((char)ch);
            }
            fr.close();
        } 
        catch (FileNotFoundException e) {} 
        catch (IOException e) {} 
    }

    public static void transAdds() {
        try {
            FileWriter fw = new FileWriter("Map.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw); {

                pw.println("check this out");
            }
            pw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void transBGones() throws IOException {

        PrintWriter pw = new PrintWriter("newMap.txt");
        BufferedReader br1 = new BufferedReader(new FileReader("Map.txt")); //i used two bufferedreaders for this
          
        String l1 = br1.readLine(); //the l's mean lines
          
        while(l1 != null)
        {
            boolean flag = false;

            BufferedReader br2 = new BufferedReader(new FileReader("delete.txt"));
              
            String l2 = br2.readLine();

            while(l2 != null) {

                if(l1.equals(l2)) {
                    flag = true;
                    break;
                } 
                l2 = br2.readLine();
                br2.close();
            }

            if(!flag) 
                pw.println(l1); 
            l1 = br1.readLine();      
        }
          
        pw.flush();

        br1.close();
        pw.close();
          
        System.out.println("File operation performed successfully");
    }

    public static void transSummaries() {
        String l;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Map.txt"));
            while ((l = br.readLine()) != null) {
                System.out.println(l);
            }
            br.close();
        } catch (IOException e) {}
     }
}