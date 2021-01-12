package finalProject;
//Jake Sencenbaugh
import java.sql.SQLException;
import java.util.*;
public class GenInfo{
	private Random random = new Random();
	
	//Returns a random sequence of 6 characters to represent a license plate
	public String getPlate()
	{	
		String plateInfo = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890" ;
		String plate = "";
		for(int i = 0; i < 6; i++)
		{
			plate = plate.substring(0,i) + plateInfo.charAt(random.nextInt(36));
		}
		return plate;
	}
	
	//Generates a random car weight
	//between 2000 and 4000 pounds
	public double getWeight()
	{
		double weight;
		weight = random.nextDouble() * 1000 + 2000 + random.nextInt(1000); 
		return weight;
	}
	
	//Generates a random location where a customer may be located.
	public int getLocation()
	{
		return random.nextInt(100);
	}
	
	//Returns true if car is safe to be taken away.
	//10% chance of car not being safe.
	public boolean isSafe()
	{
		int num = random.nextInt(10);
		if(num%10 == 0)
			return false;
		return true;
	}
	
	//returns the generated fees by the GarageDatabase Class
	public ArrayList<Object> getFees(String aLicense, String aPwd, GarageDatabase garage) throws ClassNotFoundException, SQLException
	{
			return garage.getInfo(aLicense, aPwd);

	}
	
}
