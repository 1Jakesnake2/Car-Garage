package finalProject;
//Jake Sencenbaugh
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;


import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class GarageDatabase{
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	//connects to database
	private void connect()throws SQLException, ClassNotFoundException{
	Class.forName("com.mysql.cj.jdbc.Driver");
	
	connection = DriverManager.getConnection("jdbc:mysql://localhost/ParkingGarage", "scott", "tiger");
	statement = connection.createStatement();
	}
	
	//closes database
	private void close() throws SQLException, ClassNotFoundException
	{
		connection.close();
	}
	
	//add customer to database
	public boolean addCustomer(Customer cust)throws SQLException, ClassNotFoundException
	{
		//Arraylist used to simplify the addition process.
		// Used to prevent duplicate parking spots from happening
		ArrayList<Integer> list = new ArrayList<Integer>();
		connect();
		ResultSet select = statement.executeQuery("select * from Customers");
		int spaceCounter = 0;
		String plate = cust.getID();
		String pwd = cust.getPwd();
		Double weight = cust.getVehicleWeight();
		String time = cust.getStartTime();
		
		//inserts customer info into database
		if (select.next() == false) {
			statement.executeUpdate("insert into customers(space, plate, pwd, weight, time_in) values('"+1+"','"+plate+"',sha1("+pwd+"),"+weight+",'"+time+"')");
			close();
			return true;
			} else {
				do { 
					spaceCounter++;
					if(spaceCounter > 200)
					{
						return false;
					}
					
					list.add(select.getInt(1));
					} while (select.next());
				}

		for(int i = 1; i <= list.size()+1; i++)
		{
			if(list.indexOf(i) < 0)
			{
				statement.executeUpdate("insert into customers(space, plate, pwd, weight, time_in) values('"+i+"','"+plate+"',sha1("+pwd+"),"+weight+",'"+time+"')");
				break;
			}
		}
		close();
		return true;
	}
	
	//checks to see if customer is in database
	public boolean hasCustomer(String plate, String pwd) throws SQLException, ClassNotFoundException
	{
		connect();
		// checks with plate # and password provided
		resultSet = statement.executeQuery("select * from Customers where plate = '"+plate +"' AND pwd = sha1('" +pwd +"')");
		if(resultSet.next())
		{
			close();
			return true;
		}
		close();
		return false;
	}
	
	//removes customer with plate # and password provided
	public void removeCustomer(String plate, String pwd) throws SQLException, ClassNotFoundException
	{
		connect();
		statement.executeUpdate("delete from Customers where plate = '" +plate +"'");
		close();
	
	}
	
	//Uses Generics to calculate fees for checkout process
	//Multiple Objects are stored into an ArrayList
	public ArrayList<Object> getInfo(String plate, String pwd) throws ClassNotFoundException, SQLException
	{
		Date date = new Date();
		long milli = date.getTime();
		
		ArrayList<Object> list = new ArrayList<Object>();
		connect();
		resultSet = statement.executeQuery("select * from Customers where plate = '"+plate +"' AND pwd = sha1('" +pwd +"')");
		while(resultSet.next())
		{
			//all stored in arraylist
			list.add(resultSet.getDouble(4)); // gets weight
			list.add((milli - Long.parseLong(resultSet.getString(5)))/3.6e+6); // gets time
			list.add(Math.round((Double)list.get(1)*3 + (Double)list.get(0)/750 * 100.0) / 100.0);	 // calculates fee
		}
		
		
		close();
		return list;
	}
	
	//adds locations to help table
	public void addHelp(int location) throws ClassNotFoundException, SQLException
	{
		Date date = new Date();
		double hours = date.getTime()/3.6e+6;
		String sHours = Double.toString(hours);
		
		connect();
		statement.executeUpdate("insert into help(location, time) values('" +location +"','" + sHours + "')");
		close();
	}
	
	//creates view table to be viewed to employees
	public GridPane viewHelp() throws ClassNotFoundException, SQLException
	{
		GridPane pane = new GridPane();
		int i = 1;
		connect();
		resultSet = statement.executeQuery("select * from help");
		pane.add(new Text("Location"), 0, 0);
		pane.add(new Text("Time Millis"), 1, 0);
		while(resultSet.next())
		{
			pane.add(new Text(resultSet.getString(1)), 0, i);
			pane.add(new Text(resultSet.getString(2)), 1, i);
			i++;
		}
		pane.setPadding(new Insets(3, 5, 3, 5));
		pane.setHgap(6);
		pane.setVgap(3);
		close();
		return pane;
	}
	
	//creates customer table to be viewed by employees
	public GridPane viewCust() throws ClassNotFoundException, SQLException
	{
		GridPane pane = new GridPane();
		int i = 1;
		connect();
		pane.add(new Text("P-Spot"), 0, 0);
		pane.add(new Text("Plate #"), 1, 0);
		pane.add(new Text("Vehicle Weight"), 2, 0);
		pane.add(new Text("Time In Millis"), 3, 0);
		resultSet = statement.executeQuery("select * from customers");
		while(resultSet.next())
		{
			pane.add(new Text(resultSet.getString(1)), 0, i);
			pane.add(new Text(resultSet.getString(2)), 1, i);
			pane.add(new Text(resultSet.getString(4)), 2, i);
			pane.add(new Text(resultSet.getString(5)), 3, i);
			i++;
		}
		
		close();
		pane.setHgap(6);
		pane.setVgap(3);
		return pane;
		
	}
	
	//returns true if employee is in database
	public boolean hasEmployee(String id, String pwd) throws SQLException, ClassNotFoundException
	{
		connect();
		resultSet = statement.executeQuery("select * from employees where id = '"+id +"' AND pwd = '" +pwd +"'");
		if(resultSet.next())
		{
			close();
			return true;
		}
		close();
		return false;
	}
	
	
	

}
