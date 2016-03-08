/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsc141_mp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static javafx.scene.input.KeyCode.Z;

/**
 *
 * @author parfait
 */
public class CMSC141_MP1 {

    /**
     * @param args the command line arguments
     */
    static int current_command = 1;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String fileName = "mp1.in";
        String[] textData = open_file(fileName);    //Holds the text data the file contains.
        int numberOfLines = readLines();    //Used as stoping condition 
        File file = create_file();
        FileWriter writer = new FileWriter(file);
        ArrayList data = new ArrayList();   //Holds the state of the URM in an ArrayList
        
        String[] initState = textData[0].split("\\s+"); //Holds the initial state of the URM
        for(int i = 0; i < initState.length; i++){  //Converted the initial state to ArrayList for easy manipulation
            data.add(Integer.parseInt(initState[i]));
        }
        writer.write(to_string(data));
        writer.write("\n");
        
        while (current_command < numberOfLines){//Parses each command, execute them, updates the state of the URM and writes it
            ArrayList parsed = parse_line(textData[current_command]);
            data = execute(data, parsed);
            System.out.println(to_string(data));
            writer.write(to_string(data));
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }
    public static File create_file() throws IOException{
        /*
        *Creates a File
        */
        File file = new File("mp1.out");
        file.createNewFile();
        
        return file;
    }
    
    public static String[] open_file(String fileName) throws FileNotFoundException, IOException{
        /*
        *Opens a file and extract its contents.
        */
        FileReader fr = new FileReader(fileName);
        BufferedReader textReader = new BufferedReader(fr);
        
        int numberOfLines = readLines();
        String[] textData = new String[numberOfLines];
        
        for(int i = 0; i < numberOfLines; i++){
            textData[i] = textReader.readLine();
        }
        textReader.close();
        return textData;
    }
    
    public static int readLines() throws FileNotFoundException, IOException{
        /*
        *Counts the total number of lines the file has.
        */
        FileReader fr = new FileReader("mp1.in");
        BufferedReader bf = new BufferedReader(fr);
        
        String aLine;
        int numberOfLines = 0;
        
        while((aLine = bf.readLine()) != null)
            numberOfLines++;
        bf.close();
        
        return numberOfLines;
    }
    
    public static ArrayList parse_line(String line){
        /*
        *Distinguishes between character and number a line contains. And return
        *it as an ArrayList.
        */
        String[] temp = line.split("\\s+");
        ArrayList parsed = new ArrayList();
        
        for(int i = 0; i < temp.length; i++){
            if(temp[i].matches("[a-zA-Z]")){
                parsed.add(temp[i]);
            }
            else if(temp[i].matches("\\d+")){
                parsed.add(Integer.parseInt(temp[i]));
            }
        }
        System.out.println("Parsed: " + parsed.toString());
        return parsed;
    }
    public static String to_string(ArrayList data){
        /*
        *Prints the contents of the ArrayList as a string.
        */
        StringBuilder str_builder = new StringBuilder();
        
        for(int i = 0; i < data.size(); i++){
            str_builder.append(data.get(i));
            str_builder.append(' ');
        }
        
        return str_builder.toString();
    }
    
    public static ArrayList execute(ArrayList state, ArrayList command){
        /*
        *Executes each command found in the file. It also updates the states and
        *returns it as an ArrayList.
        */
        if(command.get(0).toString().equals("J")){
            if(state.get((int)command.get(1)).equals(state.get((int)command.get(2)))){
                current_command = (int) command.get(3);
            }
            else{
                current_command++;
            }
        }
        else if(command.get(0).toString().equals("S")){
            int temp_i = (int)command.get(1);
            int temp = (int)state.get(temp_i);
            state.set(temp_i, temp + 1);
            current_command++;
        }
        else if(command.get(0).toString().equals("C")){
            int from = (int)command.get(1);
            int to = (int)command.get(2);
            int temp = (int)state.get(from);
            state.set(to, temp);
            current_command++;
        }
        else if(command.get(0).toString().equals("Z")){
            int temp_i = (int)command.get(1);
            state.set(temp_i, 0);
            current_command++;
        }
       return state;
    }
}