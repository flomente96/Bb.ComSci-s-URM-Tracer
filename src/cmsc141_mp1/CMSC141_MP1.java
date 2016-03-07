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
        // TODO code application logic here
//        Scanner scan = new Scanner(System.in);
        String fileName = "mp1.in";
        String[] lines = open_file(fileName);
        int numberOfLines = readLines();
        File file = create_file();
        FileWriter writer = new FileWriter(file);
        ArrayList data = new ArrayList();
        
        String[] temp_list = lines[0].split("\\s+");
        for(int i = 0; i < temp_list.length; i++){
            data.add(Integer.parseInt(temp_list[i]));
        }
        writer.write(to_string(data));
        writer.write("\n");
        
//        System.out.println("-------------------------");
        while (current_command < numberOfLines){
            ArrayList parsed = parse_line(lines[current_command]);
            data = execute(data, parsed);
            writer.write(to_string(data));
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }
    public static File create_file() throws IOException{
        File file = new File("mp1.out");
        file.createNewFile();
        
        return file;
    }
    
    public static String[] open_file(String fileName) throws FileNotFoundException, IOException{
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
//        System.out.println("Parsed: " + parsed.toString());
        return parsed;
    }
    public static String to_string(ArrayList data){
        StringBuilder str_builder = new StringBuilder();
        
        for(int i = 0; i < data.size(); i++){
            str_builder.append(data.get(i));
            str_builder.append(' ');
        }
        
        return str_builder.toString();
    }
    
    public static ArrayList execute(ArrayList current_state, ArrayList command){
        if(command.get(0).toString().equals("J")){
            if(current_state.get((int)command.get(1)).equals(current_state.get((int)command.get(2)))){
                current_command = (int) command.get(3);
            }
            else{
                current_command++;
            }
        }
        else if(command.get(0).toString().equals("S")){
            int temp_i = (int)command.get(1);
            int temp = (int)current_state.get(temp_i);
            current_state.set(temp_i, temp + 1);
            current_command++;
        }
        else if(command.get(0).toString().equals("C")){
            int from = (int)command.get(1);
            int to = (int)command.get(2);
            int temp = (int)current_state.get(from);
            current_state.set(to, temp);
            current_command++;
        }
        else if(command.get(0).toString().equals("Z")){
            int temp_i = (int)command.get(1);
            current_state.set(temp_i, 0);
            current_command++;
        }
       return current_state;
    }
}