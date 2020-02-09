package com.company;

import java.io.IOException;
import java.util.List;
import java.io.File;
import java.io.FileWriter;

public class SavingOfResults extends Thread{

    private static FileWriter fileWriter;
    private static File file;
    private static List<NodeGraph> nodes;
    private static int firstValueForSaving;
    private static int secondValueForSaving;
    private static boolean saveAll;


    public static void setData(List<NodeGraph> listOfNodes, int firstValue, int secondValue, boolean bollValue){
        nodes = listOfNodes;
        firstValueForSaving = firstValue;
        secondValueForSaving = secondValue;
        saveAll = bollValue;
    }

    @Override
    public void run() {
        try{
            if(saveAll) savingResults(nodes, firstValueForSaving, secondValueForSaving);
            else savingResult(nodes, firstValueForSaving, secondValueForSaving);
        }catch (Exception e){
            System.out.println("An Error Occurred");
        }
    }

    public static Character askForFileCreation(){
        String fileName;
        Character answerForFileCreation, answerForFileOverwriting;

        System.out.println("Do you also want to save your Results? Y = Yes; Any Other Input = No");
        answerForFileCreation = ReadXml.scanner.next().charAt(0);

        if(answerForFileCreation.equals('y') || answerForFileCreation.equals('Y')){
            System.out.println("Please Enter a name for your file: ");
            fileName = ReadXml.scanner.next();

            //File creation
            file = new File(fileName + ".txt");
            int x = 0;
            if(file.exists()){
                System.out.println("The File already Exists. Do you want to overwrite it? Y = Yes; Any other Input = No");
                answerForFileOverwriting = ReadXml.scanner.next().charAt(0);
                if(answerForFileOverwriting.equals('y') || answerForFileOverwriting.equals('Y')){
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
        return answerForFileCreation;
    }
    
    protected static void savingResults(List<NodeGraph> nodes, Integer firstValue, int secondValue){

        //File Writing
        try {
            fileWriter = new FileWriter(file, true);
            fileWriter.write("\nCalculation for Node: " + nodes.get(secondValue).getNodeId() + "\n");
                for (int i = 0; i < firstValue; i++) {
                    fileWriter.write("The shortest path from Node " + nodes.get(secondValue).getNodeId() + " to Node " +
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