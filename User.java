package finalProject;

public abstract class User {
	public String ID;
	public String pwd;
	public String type;

	public User(String aID, String aPwd, String aType) {
		ID = aID;
		pwd = aPwd;
		type = aType;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
