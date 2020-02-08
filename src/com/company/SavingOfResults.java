package com.company;

import java.io.IOException;
import java.util.List;
import java.io.File;
import java.io.FileWriter;

public class SavingOfResults{ 
    private static FileWriter fileWriter;
    private static File file;

    public static Character askForFileCreation(){
        String fileName;
        Character answer, answer2;

        System.out.println("Do you also want to save your Results? Y = Yes; Any Other Input = No");
        answer = ReadXml.scanner.next().charAt(0);
        if(answer.equals('y') || answer.equals('Y')){
            System.out.println("Please Enter a name for your file: ");
            fileName = ReadXml.scanner.next();

            //File creation
            file = new File(fileName + ".txt");
            int x = 0;
            if(file.exists()){
                System.out.println("The File already Exists. Do you want to overwrite it? Y = Yes; Any other Input = No");
                answer2 = ReadXml.scanner.next().charAt(0);
                if(answer2.equals('y') || answer2.equals('Y')){
                    file.delete();
                } else{
                    //looking for a name
                    do{
                        x++;
                        file = new File(fileName + "(" + x + ").txt");
                        if(!file.exists()) break;
                    }while(true);
                }
            }
        }
        return answer;
    }
    
    protected static void savingResults(List<NodeGraph> nodes, Integer amountOfNodes, int nodeToSave){

        //File Writing
        try {
            fileWriter = new FileWriter(file, true);
            fileWriter.write("\nCalculation for Node: " + nodes.get(nodeToSave).getNodeId() + "\n");
                for (int i = 0; i < amountOfNodes; i++) {
                    fileWriter.write("The shortest path from Node " + nodes.get(nodeToSave).getNodeId() + " to Node " +
                            nodes.get(i).getNodeId() + " has the weight of: " + nodes.get(i).getWeight() + "\n");
                }
            fileWriter.flush();
        } catch (Exception e) {
            System.out.println("An Error Occurred. File won't be saved");
        }
    }

    protected static void savingResult(List<NodeGraph> nodes, Integer firstNode, Integer secondNode){

        try {
            fileWriter = new FileWriter(file, true);

            fileWriter.write("The shortest path from Node " + nodes.get(firstNode).getNodeId() + " to Node " +
                        nodes.get(secondNode).getNodeId() + " has the weight of: " + nodes.get(secondNode).getWeight() + "\n");
            fileWriter.flush();
        } catch (Exception e) {
            System.out.println("An Error Occurred SEC");
            e.printStackTrace();
        }
    }

    public static void endOfWriting(){
        try {

            fileWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}