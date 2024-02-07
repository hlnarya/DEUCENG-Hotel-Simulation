
public class Staff {

	//Attributes
	private int id;
	private String name;
	private String surname;
	private String birthdate;
	private String gender;
	private String address;
	private String phonenumber;
	private String job;
	private float salary;
	
	private static int autoincrement = 1;
		
	//Constructor
	Staff(String inputName,String inputSurname,String inputBirthdate,String inputGender,String inputAddress,String inputPhonenumber,String inputJob,float inputSalary)
				{
				  name = inputName;
				  surname = inputSurname;
				  birthdate = inputBirthdate;
				  gender = inputGender;
				  address = inputAddress;
				  phonenumber = inputPhonenumber;
				  job = inputJob;
				  salary = inputSalary;
				  this.id = autoincrement++;
				  
				}
	public int getStaffId(){
	    return this.id;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}
	
		
		
		
		
		
		
}