import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileTextOut {

	public static void main(String[] args)
	{
		Person aPerson = new Person("Nils", "Nilsson" , "91919");
		try 
		{
			PrintWriter output = new PrintWriter("Person.txt");
			output.println(aPerson.getFirstName());
			output.println(aPerson.getLastName());
			output.println(aPerson.getPhoneNr());
			output.close();
		} 
		catch (FileNotFoundException e) 
		{
			
			e.printStackTrace();
		}
		
	}

}
