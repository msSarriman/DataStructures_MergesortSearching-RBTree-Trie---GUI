/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructuresproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileWriter;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author root
 */
public class DataStructuresProjectMergesort {    
    public static List<Integer> myList = new ArrayList<>();
    static String filename = null;
    
    DataStructuresProjectMergesort(String filename){
        this.filename=filename;
    }
    
    public static void readFileFun(String fileName){
        try(BufferedReader myBr = new BufferedReader(new FileReader(fileName))){
            String line;
            int x=0;
            while((line=myBr.readLine())!=null){
                myList.add(Integer.valueOf(line));
                System.out.println(line);
            }
            myBr.close();
        }catch(Exception e){
            System.out.println("Something went wrong reading the file.\n");
        }
    }
    
    public static void mySort(int[] temp,int leftStartIndex,int rightEndIndex){
        int leftArrayEnd=(leftStartIndex+rightEndIndex)/2;
        int rightArrayStart=leftArrayEnd+1;
        int ArraySize=rightEndIndex-leftStartIndex+1;
        
        int indexForLeft = leftStartIndex;
        int indexForRight = rightArrayStart;
        int indexForTemp = leftStartIndex;
        
        while (indexForLeft <= leftArrayEnd && indexForRight <= rightEndIndex){
            if(myList.get(indexForLeft) <= myList.get(indexForRight)){
                temp[indexForTemp]=myList.get(indexForLeft);
                indexForTemp++; 
                indexForLeft++;
            }else{
                temp[indexForTemp]=myList.get(indexForRight);
                indexForTemp++;
                indexForRight++;
            }
        }
        if(indexForLeft<=leftArrayEnd){
            while(indexForLeft<=leftArrayEnd){
                temp[indexForTemp++]=myList.get(indexForLeft++);
            }
        }else if(indexForRight<=rightEndIndex){
            while(indexForRight<=rightEndIndex){
                int debugTemp=myList.get(indexForRight);
                temp[indexForTemp++]=myList.get(indexForRight);
                indexForRight++;
            }
        }
        for(int ii=leftStartIndex; ii<ArraySize+leftStartIndex; ii++){
            myList.set(ii, temp[ii]);
        }
    }
    
    public static void myMergesort(int[] temp, int leftStartIndex, int rightEndIndex){
        if(leftStartIndex>=rightEndIndex) return;
        int middleIndex=(leftStartIndex+rightEndIndex)/2;
        myMergesort(temp,leftStartIndex,middleIndex);
        myMergesort(temp,middleIndex+1,rightEndIndex);
        mySort(temp,leftStartIndex,rightEndIndex);
    }
    
    public static void dspRun(){
        readFileFun(filename);
        int[] temp = new int[myList.size()];
        myMergesort(temp, 0, myList.size()-1);
    }
    
    public static void dspRun(String file) throws IOException{
        readFileFun(filename);
        int[] temp = new int[myList.size()];
        myMergesort(temp, 0, myList.size()-1);
        
        
        File newFile = new File("sortedOutput.txt");
        if (newFile.exists()) {
            newFile.delete();
        }
        newFile.createNewFile();

        FileWriter writer = new FileWriter(newFile);
        BufferedWriter buffW = new BufferedWriter(writer);
        for(int data: myList) {
            writer.write(Integer.toString(data));
            writer.write("\n");
        }        
        writer.close();
        buffW.close();
    }
    
    public static void main() throws FileNotFoundException, IOException {        
               
    }
    
}
