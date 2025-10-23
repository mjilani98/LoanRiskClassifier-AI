import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) throws IOException
	{
		
		//Scanner object 
		Scanner input = new Scanner(System.in);
		
		//get the training data file 
		System.out.println("Enter the name of Training data file : ");
		String trainingFile = input.nextLine();
		//get the test data file
		System.out.println("Enter the name of Test data file : ");
		String testFile = input.nextLine();
		//get the classified data file
		System.out.println("Enter the name of classified file :");
		String classifiedFile = input.nextLine();
		
		//convert test file
		convertTestFile(testFile,classifiedFile);
		
	}
	
	//method converts test file to numerical format 
	public static void convertTestFile(String testFile,String outputFile) throws IOException
	{
		//input and output files
        Scanner inFile = new Scanner(new File(testFile));
        PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));
        
        
        
        while(inFile.hasNext())
        {
        	//credit score
        	double creditScore = inFile.nextDouble();
        	outFile.print(convertCreditScore(creditScore)+" ");
        	//income
        	double income = inFile.nextDouble();
        	outFile.print(convertIncome(income) + " ");
        	//age
        	double age = inFile.nextDouble();
        	outFile.print(convertAge(age)+" ");
        	//sex
        	String sex = inFile.next();
        	outFile.print(convertSex(sex)+" ");
        	//status
        	String status = inFile.next();
        	outFile.print(convertMaritalStatus(status)+" ");
        	outFile.println();
        }
        
        
        
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
	public static int convertSex(String sex)
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
			return 0;
		else if(status.equals("married"))
			return 0.5;
		else
			return 1;
	}
	
	//method normalizes class
	
	public static double convertClassToNumber(String risk)
	{
		if(risk.equals("low"))
			return 0.25;				//0.25 for low 
		else if(risk.equals("medium"))  
			return 0.50;				//0.50 for medium
		else if(risk.equals("high"))
			return 0.75;				//0.75 for high
		else
			return 1.0;					//1.0 for undetermined
	}
	
	

}
