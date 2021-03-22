package service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileService {
    private static FileWriter writer;

    public static void setOutputFile(String out){
        try{
            new File(out);
            writer = new FileWriter(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String str){
        try{
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeFile(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}