package datastructuresproject;

/**
 * This class is implementing the Red Black Tree.
 * Main methods are 'insertData' and 'mySearch'.
 * @author root
 */
public class MyRedBlackTree {
    char myColor;
    Integer myData;
    private int nodeCounter=0;
    MyRedBlackTree myFather;
    MyRedBlackTree mySibling;
    
    MyRedBlackTree myLeftNode;
    MyRedBlackTree myRightNode;
    
    static MyRedBlackTree root;

    public MyRedBlackTree() {
        myData=null;
        myColor='b';
    }
    
    public MyRedBlackTree(MyRedBlackTree father) {
        myData=null;
        myColor='b';
        myFather=father;
    }
    
    public MyRedBlackTree(int data) {
        insertData(data);
    }
    
    public void myInsert(int data){
        insertData(data);
    }
    
    /**
     * Method to create a Null node inside the structure, that a parent node
     * can refer to it as a child.
     * @param father
     * @return 
     */
    public static MyRedBlackTree createNullNode(MyRedBlackTree father){
        MyRedBlackTree mrbt = new MyRedBlackTree(father);
        return mrbt;
    }
    
     /**
     * Method to find the desired height that an element will be stored.
     * @param data
     * @return MyRedBlackTree[node,father,sibling]
     */
    public MyRedBlackTree[] myNodes(int data){
        MyRedBlackTree[] array = new MyRedBlackTree[3];
        MyRedBlackTree nodePointer=root;
        while(!(nodePointer.myData==null)){
            if(data>nodePointer.myData){
                array[1]=nodePointer;
                array[2]=nodePointer.myLeftNode;
                nodePointer=nodePointer.myRightNode;
            }else{
                array[1]=nodePointer;
                array[2]=nodePointer.myRightNode;
                nodePointer=nodePointer.myLeftNode;                
            }                    
        }
        array[0]=nodePointer;
        return array;
    }
    
    public boolean isRoot(MyRedBlackTree node){
        if(node.equals(root))
            return true;
        return false;
    }
    
    private void setCounter(){
        nodeCounter++;
    }
    
    private int getCounter(){
        return nodeCounter;
    }
    
    /**
     * Method that is used the insert data inside the RB Tree
     * @param data 
     */
    public void insertData(int data){
        if(getCounter()==0){ //root case
            this.myColor='b';
            this.myData=data;
            this.myFather=null;
            this.mySibling=null;
            this.myLeftNode=createNullNode(this);
            this.myRightNode=createNullNode(this);            
            setCounter();
            this.root=this;
        }else{  // insert red leaf node
            MyRedBlackTree[] tempNode=new MyRedBlackTree[3];
            tempNode=myNodes(data);
            tempNode[0].myColor='r';
            tempNode[0].myData=data;
            tempNode[0].myFather=tempNode[1];
            tempNode[0].mySibling=tempNode[2];
            tempNode[0].myLeftNode=createNullNode(this);
            tempNode[0].myRightNode=createNullNode(this);
            
            // Here starts the tree balancing for the newly inserted element.
            if(tempNode[0].myFather.myColor=='b'){
                return;
            }else{ // father is red
                if(tempNode[0].myFather.mySibling.myColor=='r'){ // uncle is red
                    tempNode[0].myFather.mySibling.myColor='b';
                    tempNode[0].myFather.myColor='b';
                    if(tempNode[0].myFather.myFather!=null){
                        if(!(tempNode[0].myFather.myFather==root))
                            tempNode[0].myFather.myFather.myColor='r';
                    }                    
                }else{ //uncle is black
                    if(checkLL(tempNode))
                        return;
                    if(checkLR(tempNode))
                        return;
                    if(checkRR(tempNode))
                        return;
                    if(checkRL(tempNode))
                        return;
                }                
            }
        }        
    }
    
    // left left case
    public boolean checkLL(MyRedBlackTree[] tempNode){
        if(tempNode[0].equals(tempNode[0].myFather.myLeftNode) && tempNode[0].myFather.equals(tempNode[0].myFather.myFather.myLeftNode)){ //left left case
            doLLCaseRotation(tempNode[0],tempNode[1],tempNode[1].myFather,tempNode[1].myFather.myFather);
            return true;
        }
        return false;
    }
    
    // left right case
    public boolean checkLR(MyRedBlackTree[] tempNode){
        if(tempNode[0].equals(tempNode[0].myFather.myRightNode) && tempNode[0].myFather.equals(tempNode[0].myFather.myFather.myLeftNode)){ //left left case
            doLRCaseRotation(tempNode[0],tempNode[1],tempNode[1].myFather,tempNode[1].myFather.myFather);
            return true;
        }
        return false;
    }
    
    // right right case
    public boolean checkRR(MyRedBlackTree[] tempNode){
        if(tempNode[0].equals(tempNode[0].myFather.myRightNode) && tempNode[0].myFather.equals(tempNode[0].myFather.myFather.myRightNode)){ //left left case
            doRRCaseRotation(tempNode[0],tempNode[1],tempNode[1].myFather,tempNode[1].myFather.myFather);
            return true;
        }
        return false;
    }
    
    // right left case
    public boolean checkRL(MyRedBlackTree[] tempNode){
        if(tempNode[0].equals(tempNode[0].myFather.myLeftNode) && tempNode[0].myFather.equals(tempNode[0].myFather.myFather.myRightNode)){ //left left case
            doRLCaseRotation(tempNode[0],tempNode[1],tempNode[1].myFather,tempNode[1].myFather.myFather);
            return true;
        }
        return false;
    }
    
    // left left case rotation algorithm
    public void doLLCaseRotation(MyRedBlackTree child, MyRedBlackTree father, MyRedBlackTree grandfather, MyRedBlackTree steadyNode){
        boolean rootStatus=isRoot(grandfather);

        // right rotation at grandfather
        if(steadyNode!=null){
            if(steadyNode.myLeftNode.equals(grandfather))
                steadyNode.myLeftNode=father;
            else
                steadyNode.myRightNode=father;
        }
        father.myLeftNode=child;
        father.myRightNode=grandfather;
        
        // pointers corrections
        if(steadyNode!=null){
            father.myFather=steadyNode;
            if(father.myFather.myRightNode==father){
                father.mySibling=father.myFather.myLeftNode;
                steadyNode.myLeftNode.mySibling=father;
            }else{
                father.mySibling=father.myFather.myRightNode;
                steadyNode.myRightNode.mySibling=father;                
            }
        }
        
        child.myFather=father;
        child.mySibling=grandfather;
        
        grandfather.myFather=father;
        grandfather.mySibling=child;
        grandfather.myLeftNode=createNullNode(grandfather);
        
        if(rootStatus)
            root=father;
        
        //recoloring
        father.myColor='b';
        child.myColor='r';
        grandfather.myColor='r';
        
    }
    
    // left right case rotation algorithm
    public void doLRCaseRotation(MyRedBlackTree child, MyRedBlackTree father, MyRedBlackTree grandfather, MyRedBlackTree steadyNode){
        boolean rootStatus=isRoot(grandfather);

        //left rotation at father
        grandfather.myLeftNode=child;
        child.myLeftNode=father;
        
        //right rotation at grandfather
        if(steadyNode!=null){
            if(steadyNode.myLeftNode.equals(grandfather))
                steadyNode.myLeftNode=child;
            else
                steadyNode.myRightNode=child;
        }
        father.myLeftNode=father;
        father.myRightNode=grandfather;
        
        //pointer correction
        father.myFather=child;
        father.mySibling=grandfather;
        father.myLeftNode=createNullNode(father);
        father.myRightNode=createNullNode(father);
        
        if(steadyNode!=null){
            child.myFather=steadyNode;
            if(child.myFather.myRightNode.equals(child)){
                child.mySibling=child.myFather.myLeftNode;
                steadyNode.myLeftNode.mySibling=child;
            }else{
                child.mySibling=child.myFather.myRightNode;                
                steadyNode.myRightNode.mySibling=child;
            }
        }
        
        grandfather.myFather=father;
        grandfather.mySibling=child;
        grandfather.myLeftNode=createNullNode(grandfather);
        
        
        
        if(rootStatus)
            root=child;
        
        //recoloring
        father.myColor='r';
        child.myColor='b';
        grandfather.myColor='r';
    }
    
    // right right case rotation algorithm
    public void doRRCaseRotation(MyRedBlackTree child, MyRedBlackTree father, MyRedBlackTree grandfather, MyRedBlackTree steadyNode){
        boolean rootStatus=isRoot(grandfather);

        // left rotation on grandfather
        if(steadyNode!=null){
            if(steadyNode.myLeftNode.equals(grandfather))
                steadyNode.myLeftNode=father;
            else
                steadyNode.myRightNode=father; 
        }        
        father.myLeftNode=grandfather;
        father.myRightNode=child;
        
        //pointers correction
        if(steadyNode!=null){
            father.myFather=steadyNode;
            if(father.myFather.myRightNode==father){
                father.mySibling=father.myFather.myLeftNode;
                steadyNode.myLeftNode.mySibling=father;
            }else{
                father.mySibling=father.myFather.myRightNode;
                steadyNode.myRightNode.mySibling=father;                
            }
        }
        
        child.myFather=father;
        child.mySibling=grandfather;
        
        grandfather.myFather=father;
        grandfather.mySibling=child;
        grandfather.myRightNode=createNullNode(grandfather);
        
        if(rootStatus)
            root=father;
        
        //recoloring
        father.myColor='b';
        child.myColor='r';
        grandfather.myColor='r';        
    }
    
    // right left case rotation algorithm
    public void doRLCaseRotation(MyRedBlackTree child, MyRedBlackTree father, MyRedBlackTree grandfather, MyRedBlackTree steadyNode){
        boolean rootStatus=isRoot(grandfather);

        // right rotation at father
        grandfather.myRightNode=child;
        child.myRightNode=father;
        
        // left rotation on grandfather
        if(steadyNode!=null){
            if(steadyNode.myLeftNode.equals(grandfather))
                steadyNode.myLeftNode=child;
            else
                steadyNode.myRightNode=child;
        }        
        child.myLeftNode=grandfather;
        child.myRightNode=father;
        
        //pointers correction
        father.myFather=child;
        father.mySibling=grandfather;
        father.myLeftNode=createNullNode(father);
        father.myRightNode=createNullNode(father);
        
        if(steadyNode!=null){
            child.myFather=steadyNode;
            if(steadyNode.myLeftNode.equals(child)){
                child.mySibling=steadyNode.myRightNode;
                steadyNode.myRightNode.mySibling=child;
            }else{
                child.mySibling=steadyNode.myLeftNode;                
                steadyNode.myLeftNode.mySibling=child;
            }
        }
        
        grandfather.myFather=child;
        grandfather.mySibling=father;
        grandfather.myRightNode=createNullNode(grandfather);
        
        if(rootStatus)
            root=child;
        
        //recoloring
        father.myColor='r';
        child.myColor='b';
        grandfather.myColor='r';
    }
    
    
    /**
     * Method that is searching inside the RB Tree structure, a given element
     * @param data               0       1         2          3
     * @return returnData[4] = [data, height, true/false, timeTaken] 
     * true/false is for 
     * true(1) == element found
     * false(0) == element not found
     */
    public static int[] mySearch(int data){
        Long startTime;
        Long endTime;
        startTime = System.nanoTime(); 
        
        int[] returnData = new int[4];
        returnData[0]=0;
        returnData[1]=0;
        returnData[2]=0; //false
        returnData[3]=0;
        
        MyRedBlackTree pointer = root;
        if(pointer==null){
            return returnData;
        }else if(pointer.myData==data){
            returnData[0]=root.myData;
        }else{
            while(pointer.myData!=null){
                if(pointer.myData>data){
                    pointer=pointer.myLeftNode;
                    returnData[1]++;
                }else if(pointer.myData<data){
                    pointer=pointer.myRightNode;
                    returnData[1]++;
                }else{
                    returnData[0]=pointer.myData;
                    returnData[2]=1;
                    break;
                }
            }
        }
        endTime = System.nanoTime() - startTime;
        returnData[3]=Integer.valueOf(endTime.intValue());;
        return returnData;        
    }      
}
