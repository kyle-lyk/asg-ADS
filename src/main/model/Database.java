package main.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Database Class has the methods to manipulate with Database File.
 * The methods are Write Data, Read Data and Update Data.
 */ 
public class Database {

    /**
     * Write new data into the database file.
     * @param filename Database Filename
     * @param Data Data to be added into Database
     */
    public static void writeData(String filename, List<String> Data){
        String filepath = "src/main/db/" + filename + ".csv";
        File file = new File(filepath);
        if (file.exists()) { // If particular file exist in the directory, use it
            try {
                FileWriter writer = new FileWriter(filepath, true);
                for (int i=0; i < Data.size(); i++) {
                    writer.append(Data.get(i));
                    if(i == Data.size()-1){
                        writer.append("\n");
                    }
                    else{
                        writer.append(",");
                    }
                }
                writer.flush();
                writer.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{ // If particular file does not exist in the directory, create one
            try {
                FileWriter writer = new FileWriter(filepath);
                for (int i=0; i < Data.size(); i++) {
                    writer.append(Data.get(i));
                    if(i == Data.size()-1){
                        writer.append("\n");
                    }
                    else{
                        writer.append(",");
                    }
                }
                writer.flush();
                writer.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read data from the database file.
     * @param filename Database Filename
     * @return Data List from the database file
     */
    public static List<List<String>> readData(String filename){
        String filepath = "src/main/db/" + filename + ".csv";
        File file = new File(filepath);
        List<List<String>> dataList = new ArrayList<List<String>>();

        if (file.exists()) {
            try{
                String row = null;
                BufferedReader reader = new BufferedReader(new FileReader(filepath));
                while ((row = reader.readLine()) != null) {
                    String[] data = row.split(",");
                    List<String> temp = Arrays.asList(data);
                    dataList.add(temp);
                }
                reader.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    /**
     * Update new from the database file.
     * @param filename Database Filename
     * @param Datalist Data List to be added into Database
     */
    public static void updateData(String filename, List<List<String>> Datalist){
        String filepath = "src/main/db/" + filename + ".csv";
        File file = new File(filepath);
        if (file.exists()) {
            try {
                FileWriter writer = new FileWriter(filepath);
                for (int i=0; i < Datalist.size(); i++) {
                    for (int j=0; j < Datalist.get(i).size(); j++) {
                        writer.append(Datalist.get(i).get(j));
                        if(j == Datalist.get(i).size()-1){
                            writer.append("\n");
                        }
                        else{
                            writer.append(",");
                        }
                    }
                }
                writer.flush();
                writer.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
