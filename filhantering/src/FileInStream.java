import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FileInStream {

	public static void main(String[] args) throws ClassNotFoundException, IOException 
	{
		Person aPerson;
		ObjectInputStream inStream = new ObjectInputStream(new FileInputStream("Person.dat"));
		aPerson = (Person) inStream.readObject();
		inStream.close();
		
		System.out.println(aPerson.toString());
	}

}
