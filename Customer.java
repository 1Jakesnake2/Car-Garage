package finalProject;

public class Customer extends User{
	public double vehicleWeight;
	public String startTime;
	public boolean needsHelp;
	public int location;
	private GenInfo fakeAI = new GenInfo();

	
	public Customer(String aID, String aPwd, String aType) {
		super(aID, aPwd, aType);
	}
	
	public Customer(String aID, String aPwd, String aType, double weight, String aStartTime, int aLocation, boolean help)
	{
		super(aID, aPwd, aType);
		vehicleWeight = weight;
		startTime = aStartTime; 
		needsHelp = help;
		location = aLocation;

	}
	
	public void setInfo()
	{
		super.ID = fakeAI.getPlate();
		vehicleWeight = fakeAI.getWeight();
		location = fakeAI.getLocation();
	}
	
	public String getID()
	{
		return super.ID;
	}
	
	public String getPwd()
	{
		return super.pwd;
	}
	
	public String getType()
	{
		return super.type;
	}
	
	public double getVehicleWeight() {
		return vehicleWeight;
	}

	public void setVehicleWeight(double vehicleWeight) {
		this.vehicleWeight = vehicleWeight;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public boolean isNeedsHelp() {
		return needsHelp;
	}

	public void setNeedsHelp(boolean needsHelp) {
		this.needsHelp = needsHelp;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}
	
	

}
