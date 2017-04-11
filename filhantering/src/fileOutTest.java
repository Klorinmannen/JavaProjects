import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.stream.FileImageOutputStream;

public class fileOutTest {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		Person aPerson = new Person("Nils", "Nilsson", "00101");
		ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("Person.dat"));
		outStream.writeObject(aPerson);
		outStream.close();
		
		
		
	}

}
