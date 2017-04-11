import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileTextIn {

	public static void main(String[] args) 
	{
		String firstName;
		String lastName;
		String phoneNr;
		Person aPerson = null;
		try 
		{
			Scanner input = new Scanner(new File("Person.txt"));
			firstName = input.nextLine();
			lastName = input.nextLine();
			phoneNr = input.nextLine();
			input.close();
			aPerson =  new Person(firstName, lastName, phoneNr);
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}

		System.out.println(aPerson.toString());
	}

}
