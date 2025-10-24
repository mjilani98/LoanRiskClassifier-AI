import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Classifier 
{
   /*************************************************************************/
	
	//Record class (inner class)
    private class Record 
    {
    	
        private double[] attributes;         //attributes of record      
        private int className;               //class of record

        //Constructor of Record
        private Record(double[] attributes, int className)
        {
            this.attributes = attributes;    //set attributes 
            this.className = className;      //set class
        }
    }
    
    /*************************************************************************/
    
    private int numberRecords;               //number of training records   
    private int numberAttributes;            //number of attributes   
    private int numberClasses;               //number of classes
    private int numberNeighbors;             //number of nearest neighbors
    private ArrayList<Record> records;       //list of training records

    /*************************************************************************/

    //Constructor of NearestNeighbor
    public Classifier(int neighbors)
    {         
        //initial data is empty           
        numberRecords = 0;      
        numberAttributes = 0;
        numberClasses = 0;
        numberNeighbors = neighbors; 
        records = null;                        
    }

    /*************************************************************************/
    
    //method loads data from training file
    public void loadTrainingFile(String trainingFile)throws IOException
    {
        Scanner inFile = new Scanner(new File(trainingFile));
       
        //read number of records, attributes, classes
        numberRecords = inFile.nextInt();
        numberAttributes = inFile.nextInt();
        numberClasses = inFile.nextInt();
        
        //create empty list of records
        records = new ArrayList<Record>(); 
        
        //for each record
        for(int x=0; x<numberRecords ; x++)
        {
        	//create an attribute array
        	double[] attributesArray = new double[numberAttributes];
        	
        	//read attributes into the array
        	for(int y=0; y<numberAttributes ; y++)
        		attributesArray[y] = inFile.nextDouble();
        	
        	//read the class
        	int className = inFile.nextInt();
        	
        	//create a record
        	Record record = new Record(attributesArray,className);
        	
        	//add record to a record list 
        	records.add(record);
        }
        
        inFile.close();
    }
    
    /*************************************************************************/
    
    //method reads records from test file and determine their classes
    //and writes classes to a classified file
    public void classifyData(String testFile, String classifiedFile)throws IOException
    {
    	Scanner inFile = new Scanner(new File(testFile));
        PrintWriter outFile = new PrintWriter(new FileWriter(classifiedFile));
        
        //get number of records from the file 
        int numberRecords = inFile.nextInt();
        
        //write number of records to the classified file
        outFile.println(numberRecords);
        
        //for each record
        for(int x=0 ; x<numberRecords ; x++)
        {
        	//create attribute array
            double[] attributeArray = new double[numberAttributes];
            
            //read attributes values
            for(int y=0; y<numberAttributes ; y++)
            {
            	attributeArray[y] = inFile.nextDouble();	
            }
            
            //find class of attributes
            int className = classify(attributeArray);
            
            //write class name
            outFile.println(className);
        }
        
        inFile.close();
        outFile.close();
    }
    
    /*************************************************************************/
    
    //method determines the class of a given attributes
    private int classify(double[] attributes)
    {
    	double[] distance = new double[numberRecords];
        int[] id = new int[numberRecords];
        
        //find distance between attributes and all records
        for(int x=0; x<numberRecords ; x++)
        {
        	distance[x] = distance(attributes,records.get(x).attributes);
        	id[x] = x;
        }
        
        //find nearest neighbors
        nearestNeighbor(distance, id);
        
        //find majority class of nearest neighbor
        int className = majority(id);
    	
    	return className;
    }
    
    /*************************************************************************/
    
    //method calculate distance between two attributes using Euclidean distance
    private double distance(double[] u, double[]v)
    {
    	 double distance = 0;         

         for (int i = 0; i < u.length; i++)
             distance = distance + (u[i] - v[i])*(u[i] - v[i]);

         distance = Math.sqrt(distance); 
  
         return distance;   
    }
    
    /*************************************************************************/
    
    //Method finds the nearest neighbors
    private void nearestNeighbor(double[] distance, int[] id)
    {
    	//sort distances and choose nearest neighbors
        for (int i = 0; i < numberNeighbors; i++)
        {
        	for (int j = i; j < numberRecords; j++)
	            if (distance[i] > distance[j])
	            {
	                double tempDistance = distance[i];
	                distance[i] = distance[j];
	                distance[j] = tempDistance;
	
	                int tempId = id[i];
	                id[i] = id[j];
	                id[j] = tempId;
	            }
        }
        	
    }
    
    /*************************************************************************/
    
    //method finds the majority of class of nearest neighbors
    private int majority(int[] id)
    {
	  double[] frequency = new double[numberClasses];

      //class frequencies are zero initially
      for (int i = 0; i < numberClasses; i++)
          frequency[i] = 0;

      //each neighbor contributes 1 to its class
      for (int i = 0; i < numberNeighbors; i++)
          frequency[records.get(id[i]).className - 1] += 1;

      //find majority class
      int maxIndex = 0;
      for (int i = 0; i < numberClasses; i++)
          if (frequency[i] > frequency[maxIndex])
              maxIndex = i;

      return maxIndex + 1;
    }
    
    //method validates classifier using leave one out method 
    //returns the error rate
    public double validate()
    {
    	double errorRate=0;		//error rate
    	int numberErrors=0;		//number of errors
    	
    	for(int x=0; x<numberRecords; x++)
    	{
    		//use one record for validation
    		//remove one record
    		Record tempRecord = records.remove(x);
    		numberRecords-=1;
    		
    		double[] attributesArray = tempRecord.attributes;	//attribute of the record
    		int recordClass = tempRecord.className;				//class of the record
    		
    		//classify temp record
    		int prediction = classify(attributesArray);
    		
    		//check if prediction matches actual class
    		if(prediction != recordClass)
    			numberErrors+=1;		//increment number of errors
    		
    		//add the record back to the list 
    		records.add(x, tempRecord);	
    		numberRecords+=1;
    	}
    	
    	//calculate error rate 
    	errorRate = 100.0*numberErrors/numberRecords;
    	
    	return errorRate;
    }
    

}
