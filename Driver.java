import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Driver {
	
	private static final int NEIGHBORS = 6;

	public static void main(String[] args) throws IOException
	{
		
		//Scanner object 
		Scanner input = new Scanner(System.in);
		
		//get the training data file 
		System.out.println("Enter the name of Training data file : ");
		String inputTrainingFile = input.nextLine();
		//get the test data file
		System.out.println("Enter the name of Test data file : ");
		String inputTestFile = input.nextLine();
		//get the classified data file
		System.out.println("Enter the name of classified file :");
		String classifiedFile = input.nextLine();
		
		//convert training file to numerical format
		convertTrainingFile(inputTrainingFile,"numericalTraining.txt");
		
		//convert test file to numerical format
		convertTestFile(inputTestFile,"numericalTest.txt");
		
		//create a classifier object
		Classifier classifier = new Classifier(NEIGHBORS);
		
		//load training file to classifier
		classifier.loadTrainingFile("numericalTraining.txt");
		
		//classify test data
		classifier.classifyData("numericalTest.txt", "result.txt");
		
		//convert classified file to text format
		convertClassFile("result.txt",classifiedFile,classifier);
		
		
	}
	
	//method converts training file to numerical format 
	public static void convertTrainingFile(String inputFile,String outputFile)throws IOException
	{
		//input and output files
        Scanner inFile = new Scanner(new File(inputFile)); 
        PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));
        
        //read number of records, attributes, classes
        int numberRecords = inFile.nextInt();    
        int numberAttributes = inFile.nextInt();    
        int numberClasses = inFile.nextInt();
        
        //write number of records, attributes, classes
        outFile.println(numberRecords + " " + numberAttributes + " " + numberClasses);
        
        //for each record
        for(int x = 0 ; x < numberRecords ; x++)
        {
        	//convert credit score
        	int creditScore = inFile.nextInt();
        	outFile.print(convertCreditScore(creditScore)+" ");
        	
        	//convert income 
        	int income =  inFile.nextInt();
        	outFile.print(convertIncome(income)+" ");
        	
        	//convert age
        	int age = inFile.nextInt();
        	outFile.print(convertAge(age)+" ");
        	
        	//convert sex
        	String sex = inFile.next();
        	outFile.print(convertSex(sex)+" ");
        	
        	//convert marital status
        	String maritalStatus = inFile.next();
        	outFile.print(convertMaritalStatus(maritalStatus)+" ");
        	
        	//convert class
        	String strClass  = inFile.next();
        	outFile.print(convertClassToNumber(strClass));
        	
        	//new line
        	outFile.println();
        	
        }
        
        
        inFile.close();
        outFile.close();
	}
	
	//method converts test file to numerical format 
	public static void convertTestFile(String inputFile,String outputFile) throws IOException
	{
		//input and output files
        Scanner inFile = new Scanner(new File(inputFile)); 
        PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));
        
        //read number of records, attributes, classes
        int numberRecords = inFile.nextInt();    
        
        //write number of records, attributes, classes
        outFile.println(numberRecords);
        
        //for each record
        for(int x = 0 ; x < numberRecords ; x++)
        {
        	//convert credit score
        	int creditScore = inFile.nextInt();
        	outFile.print(convertCreditScore(creditScore)+" ");
        	
        	//convert income 
        	int income =  inFile.nextInt();
        	outFile.print(convertIncome(income)+" ");
        	
        	//convert age
        	int age = inFile.nextInt();
        	outFile.print(convertAge(age)+" ");
        	
        	//convert sex
        	String sex = inFile.next();
        	outFile.print(convertSex(sex)+" ");
        	
        	//convert marital status
        	String maritalStatus = inFile.next();
        	outFile.print(convertMaritalStatus(maritalStatus)+" ");
        	
        	//new line
        	outFile.println();
        }
        
        inFile.close();
        outFile.close();
	}
	
	//method converts classified file to text format
	public static void convertClassFile(String inputFile, String outputFile,Classifier classifier)throws IOException
	{
		//input and output files
        Scanner inFile = new Scanner(new File(inputFile));
        PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));
        
        //read number of records
        int numberRecords = inFile.nextInt();    

        //write number of records
        outFile.println(numberRecords);
        
        //for each record
        for(int x=0; x<numberRecords ; x++)
        {
        	int number = inFile.nextInt();
        	outFile.println(convertNumberToClass(number));
        }
        
        outFile.println("Validation error : %"+classifier.validate()); //write error rate to
        outFile.println("Number of nearest neighbor : "+NEIGHBORS);	   //write nearest neighbor
        
        inFile.close();
        outFile.close();
	}
	
	//method normalizes credit score
	public static double convertCreditScore(double score)
	{
		return (score-500)/(900-500);
	}
	
	//method normalizes income
	public static double convertIncome(double income)
	{
		return (income - 30)/(90 - 30);
	}
	
	//method normalizes age
	public static double convertAge(double age)
	{
		return (age-30)/(80-30);
	}
	
	//method normalizes sex
	public static double convertSex(String sex)
	{
		if(sex.equals("male")) 
			return 1;	// 1 for male
		else
			return 0;	//0 for female
	}
	
	//method normalizes marital status
	public static double convertMaritalStatus(String status)
	{
		if(status.equals("single"))
			return 0;						//0 for single
		else if(status.equals("married"))
			return 0.5;						//0.5 for married
		else
			return 1;						//1.0 for divorced
	}
	
	//method normalizes class
	public static int convertClassToNumber(String risk)
	{
		if(risk.equals("low"))
			return 1;				//0.25 for low 
		else if(risk.equals("medium"))  
			return 2;				//0.50 for medium
		else if(risk.equals("high"))
			return 3;				//0.75 for high
		else
			return 4;					//1.0 for undetermined
	}
	
	//method converts number to class
	public static String convertNumberToClass(int number)
	{
		if(number == 1)
			return "low";
		else if(number == 2)
			return "medium";
		else if(number == 3)
			return "high";
		else
			return "undetermined";
				
	}

}
