package com.company;

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar; 

public class SavingOfResults{ 
    static FileWriter fileWriter;
    static File file;
    static Date date = Calendar.getInstance().getTime();
    static DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    static String strDate = dateFormat.format(date);

    protected static void createFile(String fileName){
        file = new File(fileName);
        if(file.exists()) file.delete();
    }
    
    protected static void savingResults(List<NodeGraph> nodes, Integer amountOfNodes, Integer nodeForCalculation){

        try {
            fileWriter = new FileWriter(file, true);
            fileWriter.write("\nCalculation for Node: " + nodes.get(nodeForCalculation).getNodeId() + "\n");
            for (int i = 0; i < amountOfNodes; i++) {
                fileWriter.write("The shortest path from Node " + nodes.get(nodeForCalculation).getNodeId() + " to Node " + 
                nodes.get(i).getNodeId() + " has the weight of: " + nodes.get(i).getWeight() + "\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("An Error Occurred");
        }
    }

    protected static void savingResult(List<NodeGraph> nodes, Integer firstNode, Integer secondNode){

        try {
            fileWriter = new FileWriter(file, true);

            fileWriter.write("The shortest path from Node " + nodes.get(firstNode).getNodeId() + " to Node " +
                        nodes.get(secondNode).getNodeId() + " has the weight of: " + nodes.get(secondNode).getWeight() + "\n");

            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("An Error Occurred SEC");
            e.printStackTrace();
        }
    }

}