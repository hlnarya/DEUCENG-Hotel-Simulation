
public class Customer {

	//Attributes
	private int id;
	private String name;
	private String surname;
	private String gender;
	private String birthdate;
	private String address;
	private String phone_number;
	private static int autoincrement = 1;


	//Constructor
	public Customer(String inputName,String inputSurname,String inputgender,String inputbirthdate,String inputAddress,String inputPhonenumber)
	{
		name = inputName;
		surname = inputSurname;
		gender = inputgender;
		birthdate = inputbirthdate;
		address = inputAddress;
		phone_number = inputPhonenumber;
		this.id = autoincrement++;

	}

	public int getCustomerId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() { return surname; }

	public void setSurname(String surname) { this.surname = surname; }

	public String getGender() { return gender; }

	public void setGender(String gender) { this.gender = gender; }

	public String getBirthdate() { return birthdate; }

	public void setBirthdate(String birthdate) { this.birthdate = birthdate; }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phonenumber) {
		this.phone_number = phonenumber;
	}


}







