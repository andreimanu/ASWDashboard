package model;

public class User {

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", gender=" + (gender ? "Male" : "Female") + "]";
	}

	private String name;
	private String LName;
	private int id;
	private String email;
	private String DOB;
	private boolean gender;
	private String password;
	private String Address;
	
	public User(String name, String LName, int id, String email, String DOB, boolean gender, String password, String Address) {
		this.name = name;
		this.LName = LName;
		this.id = id;
		this.email = email;
		this.DOB = DOB;
		this.gender = gender;
		this.password = password;
		this.setAddress(Address);
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getLName() {
		return LName;
	}
	
	public String getFullName() {
		return getName() + " " + getLName();
	}

	public String getEmail() {
		return email;
	}

	/**
	 * @return true for men, false for women
	 */
	public boolean isGender() {
		return gender;
	}
	
	public void setGender(boolean gender) {
		this.gender = gender;
	}

//	public int getDNI() {
//		return DNI;
//	}
//
//	public void setDNI(int dNI) {
//		DNI = dNI;
//	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}
}
