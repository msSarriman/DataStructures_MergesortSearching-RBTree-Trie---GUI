package datastructuresproject;

import static datastructuresproject.DataStructuresProjectMergesort.myList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *  This class implements the desired search mechanisms (linear, binary, interpolation)
 *  into the 'sortedOutput' file. 
 */
public class MySearchClass {
    static String typeOfSearch = null;    
    static int dataToSearch;
    public static List<Integer> myList = new ArrayList<>();
    
    static long[] returnStuff = new long[2];
    public static long[] returnTheStuff(){
        return returnStuff;
    }
    
    MySearchClass(String typeOfSearch,int dataToSearch){
        this.typeOfSearch=typeOfSearch;
        this.dataToSearch=dataToSearch;
        scRun();
    }
    
    public static void scRun(){
        readFileFun();
        if(typeOfSearch=="Binary")
            binarySearching();
        else if(typeOfSearch=="Linear")
            linearSearching();
        else if(typeOfSearch=="Interpolation")
            interpolationSearching();
    }
    
    public static void readFileFun(){
        if(!myList.isEmpty()) return;
        try(BufferedReader myBr = new BufferedReader(new FileReader("sortedOutput.txt"))){
            String line=null;
            while((line=myBr.readLine())!=null){
                myList.add(Integer.valueOf(line));
                System.out.println(line);
            }
            myBr.close();
        }catch(Exception e){
            System.out.println("Something went wrong reading the file.\n");
        }
    }
    
    /**
     * Linear searching method
     * returnStuff[a,b]
     * a is the index. -1 if not found
     * b is the time passed to find the dataToSearch
     */
    public static void linearSearching(){
        long startTime=0;
        long endTime=0;
        returnStuff[0]=-1;        
        startTime = System.nanoTime();
        
        for(int data: myList){
            if(data==dataToSearch)
                returnStuff[0]=myList.indexOf(data);
        }
        
        endTime = System.nanoTime() - startTime;
        returnStuff[1]=endTime;
    }
    
    /**
     * Binary searching method
     * returnStuff[a,b]
     * a is the index. -1 if not found
     * b is the time passed to find the dataToSearch
     */
    public static void binarySearching(){
        long startTime=0;
        long endTime=0;
        returnStuff[0]=-1;
        
        int myIndex = myList.size() / 2;
        int leftIndex=0;
        int rightIndex=myList.size()-1;
        
        startTime = System.nanoTime();        
        while(/*myList.get(myIndex)!=dataToSearch && */(leftIndex-rightIndex!=0)){
            int currentValue = myList.get(myIndex);
            if(currentValue==dataToSearch){
                returnStuff[0]=myIndex;
                break;
            }else{
                if(currentValue<dataToSearch){
                    leftIndex=myIndex;
                    myIndex=(leftIndex+rightIndex)/2;
                }else if(currentValue>dataToSearch){
                    rightIndex=myIndex;
                    myIndex=(leftIndex+rightIndex)/2;
                }
            }
        }
        endTime = System.nanoTime() - startTime;
        returnStuff[1]=endTime;
    }
    
    
    /**
     * Interpolation searching method
     * returnStuff[a,b]
     * a is the index. -1 if not found
     * b is the time passed to find the dataToSearch
     */
    public static void interpolationSearching(){
        long startTime=0;
        long endTime=0;
        returnStuff[0]=-1;
        
        
        int leftIndex=0;
        int rightIndex=myList.size()-1;
        int midIndex=(leftIndex+rightIndex)/2;
        int currIndex=(((dataToSearch-myList.get(leftIndex))/(myList.get(rightIndex)-myList.get(leftIndex)))*(rightIndex-leftIndex))+leftIndex;
        
        boolean flag=false;
        
        startTime = System.nanoTime();
        if(myList.get(currIndex)==dataToSearch) 
            returnStuff[0]=currIndex;
        else{
            while(myList.get(currIndex)!=dataToSearch){
                if(myList.get(currIndex)<dataToSearch) 
                    leftIndex=currIndex+1;
                else 
                    rightIndex=currIndex-1;
                currIndex=(((dataToSearch-myList.get(leftIndex))/(myList.get(rightIndex)-myList.get(leftIndex)))*(rightIndex-leftIndex))+leftIndex;
            }
            returnStuff[0]=currIndex;
        }
        endTime = System.nanoTime() - startTime;
        returnStuff[1]=endTime;        
    }
}
