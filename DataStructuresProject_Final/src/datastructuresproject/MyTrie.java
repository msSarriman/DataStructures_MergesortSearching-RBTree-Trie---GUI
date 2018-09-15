package datastructuresproject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Trie data structure.
 * @author root
 */
public class MyTrie {
    List<MyTrie> myNodes = new ArrayList<MyTrie>();
    public static MyTrie rootNode;
    public MyTrie myFather;
    public char myChar;
    public boolean wordStatus = false;
    
    public MyTrie(){
        
    }
    
    /**
     * Constructor to create the root node.
     * To be used only once!
     * @param father == null
     * @param isRoot == root
     */
    public MyTrie(MyTrie father, String isRoot){
        myFather=father;
        if(isRoot=="root"){
            rootNode=this;
        }
    }
    
    public MyTrie(MyTrie father,char inputChar){
        myFather=father;
        myChar = inputChar;
    }
    
    public MyTrie myCreateNode(MyTrie father,char inputChar){
        MyTrie node = new MyTrie();
        myFather=father;
        myChar = inputChar;
        return node;
    }
    
    /**
     * Method that insert words inside the Trie.
     * @param inputWord 
     */
    public void myInsertWord(String inputWord){
        MyTrie index = rootNode;
        char[] myWord = new char[inputWord.length()];
        myWord = inputWord.toCharArray();
        
        for(int i=0; i<myWord.length; i++){
            boolean found=false;
            for(MyTrie j : index.myNodes){
                if(j.myChar==myWord[i]){
                    //i++; // why did i have this here?
                    index=j;
                    found = true;
                    break;
                }
            }
            if(!found){
                index.myNodes.add(new MyTrie(this,myWord[i]));
                index=myNodeWithChar(myWord[i],index);
            }
        }
        index.wordStatus=true;
    }
    
    /**
     * Method that given the params, returns an index at the node with the desired @myChar
     * which is a child of node @startingNode
     * @param myChar
     * @param startingNode
     * @return index
     */
    public MyTrie myNodeWithChar(char myChar,MyTrie startingNode){
        MyTrie index = startingNode;
        for(MyTrie i:index.myNodes){
            if(i.myChar==myChar){
                index=i;
                break;
            }
        }
        return index;
    }
    
    /**
     * Method that searches of a word inside the Trie.
     * @param inputWord
     * @return returnData = [word found trueFalse]
     */
    public String myWordSearch(String inputWord){
        String returnData = "Word did not found!";
        
        char[] myWord = inputWord.toCharArray();
        MyTrie currentNode = rootNode;
        boolean flag=false;
        for(char i : myWord){
            flag=false;
            for(MyTrie j: currentNode.myNodes){
                if(j.myChar==i){
                    currentNode=j;
                    flag=true;
                    break;
                }
            }
            if(flag)
                continue;
            else    // letter and therefore word did not found
                break;
        }
        if(currentNode.wordStatus && flag==true)
            returnData = "Word exists inside the Trie Data Structure!";
        
        return returnData;
    }
    
    
    /**
     * Method the deletes a word from the trie.
     * @param inputWord=word to delete
     * @return 1 if operation runs successfully
     * @return 0 if operation runs un-successfully
     */
    public int myWordDelete(String inputWord){
        char[] myWord = inputWord.toCharArray();
        MyTrie currentNode = rootNode;
        
        boolean flag;
        for(char i : myWord){
            flag=false;
            for(MyTrie j: currentNode.myNodes){
                if(j.myChar==i){
                    currentNode=j;
                    flag=true;
                    break;
                }
            }
            if(flag)
                continue;
            else    // letter and therefore word did not found
                break;
        }
        try{
            if(currentNode.wordStatus){
                currentNode.wordStatus=false;
                if(!currentNode.myNodes.isEmpty()) // if word is a preamble, just delete the word status at the current node and return...
                    return 1;
                while(!currentNode.wordStatus){     // if word is not a preamble, but the preamle is a another word, delete the excess characters. (e.g. lightsaber, light, delete 'saber' from lightsaber to keep the word light. 
                    MyTrie childToRemove=currentNode;
                    currentNode=currentNode.myFather;
                    currentNode.myNodes.remove(childToRemove);
                }
            }
            return 1;
        }catch(Exception e){
            return 0; // 0 equals error
        }        
    }
}
