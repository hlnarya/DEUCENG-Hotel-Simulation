import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class HotelManagement {
	//Attributes
	    private String name;
	    private String foundationDate;
	    private String address;
	    private String phoneNumber;
	    private int starRating;
	    private Room rooms[];
	    private Customer customers[];
		private Phone phones[];
		private Staff staffs[];
	    private static int roomIndex =0;
	    private static int customerIndex =0;
	    private static int staffIndex =0;
	    private static double counth =0;
		private static int counta =0;
		private static int countr =0;
		private static int dells =0;
		private int DeleteID;
	    private Object[] command;
	    private Reservation resRecord;
	    public HotelManagement() {
	    	// Initialize the attributes
	    	this.resRecord = new Reservation();
	        this.rooms = new Room[30];
	        this.customers = new Customer[9999];
			this.phones = new Phone[9999];
			this.staffs = new Staff[50];
	        //getting commands from commands.txt and putting them into a object array
	        Path path= Path.of("src/resources/commands.txt");
	    	try {
	    		command = Files.lines(path).toArray();
	    	} catch (IOException e1) {
	    		System.out.println("Error!");
	    		e1.printStackTrace();
	    	}
	    	String commandline[][]= new String[command.length][];
	    	for(int i=0;i<command.length;i++)
	    	{
	    		commandline[i]=command[i].toString().split(";");
	    	}
	    	for(int i=0;i<command.length;i++)
	    	{
	    		switch(commandline[i][0])
    	    	{
    	    	case "addRoom":
    	    		for(int j=0;j<Integer.parseInt(commandline[i][1]);j++)
    	    		{
    	    			Room r=new Room(commandline[i][2],Boolean.parseBoolean(commandline[i][3]),Boolean.parseBoolean(commandline[i][4]),Integer.parseInt(commandline[i][5]));
    	    			addRoom(r);
    	    		}
    	    		break;
    	    	case "addCustomer":
					String phone_parts = commandline[i][8];
					Phone p = new Phone(phone_parts.substring(0,3),phone_parts.substring(3,6),phone_parts.substring(6,phone_parts.length()));
					Customer c = new Customer(commandline[i][1],commandline[i][2],commandline[i][3],commandline[i][4],commandline[i][7],commandline[i][8]);
					addCustomer(c);
					addPhone(p);
					break;
    	    	case "listRooms":
    	    		listRooms();
					break;
    	    	case "listCustomer":
    	    		listCustomer();
					break;
    	    	case "addReservation":
    	    		String[] startDate= commandline[i][3].split("\\.");
    	    		String[] endDate= commandline[i][4].split("\\.");
    	    		Date sDate = new Date(Integer.parseInt(startDate[0]),Integer.parseInt(startDate[1]),Integer.parseInt(startDate[2]));
    	    		Date eDate = new Date(Integer.parseInt(endDate[0]),Integer.parseInt(endDate[1]),Integer.parseInt(endDate[2]));
    	    		resRecord.addReservation(Integer.parseInt(commandline[i][1]),Integer.parseInt(commandline[i][2]),sDate,eDate);
					break;
    	    	case "listReservations":
    	    		listReservations();
    	    		break;
    	    	case "addEmployee":
					Staff s = new Staff(commandline[i][1],commandline[i][2],commandline[i][3],commandline[i][4],commandline[i][5],commandline[i][8],commandline[i][9],Float.parseFloat(commandline[i][10]));
					addStaff(s);
					break;
    	    	case "listEmployees":
    	    		listStaff();
    	    		break;
				case "deleteEmployee":
					DeleteID = Integer.parseInt(commandline[i][1]) - 1;
					dells++;
					dellStaff();
					break;
				case "searchRoom":
					String[] startDateSearch = commandline[i][1].split("\\.");
    	    		String[] endDateSearch = commandline[i][2].split("\\.");
    	    		Date sDateSearch = new Date(Integer.parseInt(startDateSearch[0]),Integer.parseInt(startDateSearch[1]),Integer.parseInt(startDateSearch[2]));
    	    		Date eDateSearch = new Date(Integer.parseInt(endDateSearch[0]),Integer.parseInt(endDateSearch[1]),Integer.parseInt(endDateSearch[2]));
    	    		System.out.println("SearchRoom: " + sDateSearch.getDateString()+" To "+eDateSearch.getDateString());
    	    		searchRoom(resRecord.getAvailableRoom(sDateSearch,eDateSearch));
    	    		break;
				case "searchCustomer":
					searchCustomer(commandline[i][1]);
					break;
				case "statistics":
					statistics();
					break;
				case "simulation":
					String[] startDateSimulation = commandline[i][1].split("\\.");
    	    		String[] endDateSimulation = commandline[i][2].split("\\.");
    	    		Date sDateSimulation = new Date(Integer.parseInt(startDateSimulation[0]),Integer.parseInt(startDateSimulation[1]),Integer.parseInt(startDateSimulation[2]));
    	    		Date eDateSimulation = new Date(Integer.parseInt(endDateSimulation[0]),Integer.parseInt(endDateSimulation[1]),Integer.parseInt(endDateSimulation[2]));
    	    		simulation(sDateSimulation,eDateSimulation);
					break;
    	    	}
	    	}
	    }
	    
	    private void simulation(Date sDateSimulation, Date eDateSimulation) {
	    	System.out.println("simulation"+";"+sDateSimulation.getDateString()+";"+eDateSimulation.getDateString());
	    	int sDate = dateToInteger(sDateSimulation);
	    	int eDate = dateToInteger(eDateSimulation);
	    	int[] customerCount = new int[eDate-sDate+1];
	    	float avgSatisfaction = 0;
			for(int i=0;i<resRecord.getResCount();i++) {
				for(int j=0;j<(eDate-sDate+1);j++) {
					if((sDate+j)<resRecord.getReservation()[3][i]&&(sDate+j)>=resRecord.getReservation()[2][i]) {
						customerCount[j]++;
					}
					if(sDate==eDate) {
						customerCount[j]++;
					}
				}
			}
			String strLine1 = "Day         :";
			String strLine2 = "Customer    :";
			String strLine3 = "Satisfaction:";
			for(int j=0;j<(eDate-sDate+1);j++) {
				strLine1 += String.format("%-10s",intToDate(sDate+j).getDay());
				strLine2 += String.format("%-10s",customerCount[j]);
				if(customerCount[j]==0)
				{
					strLine3 += String.format("%-10s","100%");
					avgSatisfaction+=100;
				}
					
				else if((counth*300)/customerCount[j]>100)
				{
					strLine3 += String.format("%-10s","100%");
					avgSatisfaction+=100;
				}
				else
				{
					strLine3 += String.format("%-10s", (counth*300)/customerCount[j] + "%");
					avgSatisfaction+=(counth*300)/customerCount[j];
				}
					
			}
			avgSatisfaction=avgSatisfaction/customerCount.length;
			System.out.println(strLine1+"\n"+strLine2+"\n"+strLine3+"\n"+"Average Satisfaction = " +String.format("%.02f", avgSatisfaction) +"%" );
			
		}

		private void searchRoom(int[] availableRoom) {
	    	for(int i=0; i<roomIndex+1;i++) {
				if(availableRoom[i]==1)
				{
					System.out.println(String.format("%-15s %-10s %-25s %-15s %-8s", "	Room # " + rooms[i].getRoomId(), rooms[i].getType(),
                            (rooms[i].isAircondition() ? "airconditioning" : "no-airconditioning") ,
                            (rooms[i].isBalcony() ? "balcony" : "no-balcony"),rooms[i].getPrice()+"TL")) ;
					 try {
				        	// append is true in Filewriter causing it not erase last screen
				    	      FileWriter myWriter = new FileWriter("src/resources/Screen.txt",true);
				    	      myWriter.write("Search Room Results:"+System.getProperty( "line.separator" ));
				    	      myWriter.write(String.format("%-15s %-10s %-25s %-15s %-8s", "	Room # " + rooms[i].getRoomId(), rooms[i].getType(),
			                            (rooms[i].isAircondition() ? "airconditioning" : "no-airconditioning") ,
			                            (rooms[i].isBalcony() ? "balcony" : "no-balcony"),rooms[i].getPrice()+"TL"));
				    	      myWriter.close();
				    	      
			    	    } catch (IOException e) {
			    	      System.out.println("An error occurred.");
			    	      e.printStackTrace();
			    	    }
				}
			}
			
		}

		public int getRoomsCount() {
			return roomIndex;
		}
	    public int getCustomerCount() {
	    	return customerIndex;
	    }
	    public void addRoom(Room room) {
	    	this.roomIndex = room.getRoomId()-1;
	    	
	    	if(roomIndex<30)
	        rooms[roomIndex] = room;
	    	else
	    		System.out.println("You Have Reached Max Room Number!");
	        
	    }
	    public void addCustomer(Customer customer){
			this.customerIndex = customer.getCustomerId()-1;
			customers[customerIndex] = customer;
		}
		public void addPhone(Phone phone){
			phones[customerIndex] = phone;
		}	
		public void addStaff(Staff staff){
			this.staffIndex = staff.getStaffId()-1;

			if (staffIndex<50){
				staffs[staffIndex] = staff;
				if (staff.getJob().equalsIgnoreCase("housekeeper"))
					counth++;
				else if (staff.getJob().equalsIgnoreCase("receptionist"))
					countr++;
				else if (staff.getJob().equalsIgnoreCase("admin"))
					counta++;
			}
			else
				System.out.println("You Have Reached Max Staff!");
		}
		public void dellStaff(){
			if (staffs[DeleteID].getJob().equalsIgnoreCase("housekeeper"))
				counth--;
			else if (staffs[DeleteID].getJob().equalsIgnoreCase("receptionist"))
				countr--;
			else if(staffs[DeleteID].getJob().equalsIgnoreCase("admin"))
				counta--;
			staffs[DeleteID].setName("");
			staffs[DeleteID].setSurname("");
			staffs[DeleteID].setGender("");
			staffs[DeleteID].setBirthdate("");
			staffs[DeleteID].setSurname("");
			staffs[DeleteID].setJob("");
			staffIndex--;
		}
		
	    public void listRooms() {
	    	
	        System.out.println("List of rooms:");
	       
	        try {
	        	// append is true in Filewriter causing it not erase last screen
	    	      FileWriter myWriter = new FileWriter("src/resources/Screen.txt",true);
	    	      myWriter.write("List of rooms:"+System.getProperty( "line.separator" ));
	    	      //if there are no commands for adding rooms and no room is being stored the program will crash
	    	      if(roomIndex!=0)
	    	      for (int j = 0; roomIndex>=j ; j++) {
	    	    	  myWriter.write(String.format("%-15s %-10s %-25s %-15s %-8s", "	Room # " + rooms[j].getRoomId(), rooms[j].getType(),
	                               (rooms[j].isAircondition() ? "airconditioning" : "no-airconditioning") ,
	                               (rooms[j].isBalcony() ? "balcony" : "no-balcony"),rooms[j].getPrice()+"TL")+System.getProperty( "line.separator" ));
	  	            System.out.println(String.format("%-15s %-10s %-25s %-15s %-8s", "	Room # " + rooms[j].getRoomId(), rooms[j].getType(),
	  	                               (rooms[j].isAircondition() ? "airconditioning" : "no-airconditioning") ,
	  	                               (rooms[j].isBalcony() ? "balcony" : "no-balcony"),rooms[j].getPrice()+"TL")) ;
	  	        }
	    	      
	    	      myWriter.close();
	    	      
	    	    } catch (IOException e) {
	    	      System.out.println("An error occurred.");
	    	      e.printStackTrace();
	    	    }
	        
	    }
	    public void listCustomer(){
	    				
			try {
				// append is true in Filewriter causing it not erase last screen
				FileWriter myWriter = new FileWriter("src/resources/Screen.txt",true);
				System.out.println("List of Customer:");
				myWriter.write("List of Customer:"+System.getProperty( "line.separator" ));
				//if there are no commands for adding customer and no customer is being stored the program will crash
				if(customerIndex!=0)
				for (int j = 0; customerIndex>=j ; j++){
					myWriter.write(String.format("%-15s %-10s %-20s %-15s %-12s %-12s %-4s %-1s %-1s %-2s %-8s", "	Customer # " + customers[j].getCustomerId(), customers[j].getName(), customers[j].getSurname(),
									customers[j].getGender(), customers[j].getBirthdate(), customers[j].getAddress(), phones[j].getCountrycode(),"(", phones[j].getCitycode(),")", phones[j].getNumber())+System.getProperty( "line.separator" ));
					System.out.println(String.format("%-15s %-10s %-20s %-15s %-12s %-12s %-4s %-1s %-1s %-2s %-8s", "	Customer # " + customers[j].getCustomerId(), customers[j].getName(), customers[j].getSurname(),
							customers[j].getGender(), customers[j].getBirthdate(), customers[j].getAddress(), phones[j].getCountrycode(),"(", phones[j].getCitycode(),")", phones[j].getNumber()));
				}
				
				myWriter.close();
	    	    

			}catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
			
		}
	    public void listStaff(){
			try {
				if (counta >= 1 && countr >= 1 && counth >= 1.0){
					// append is true in Filewriter causing it not erase last screen
					FileWriter myWriter = new FileWriter("src/resources/Screen.txt",true);
					System.out.println("List of Employee:");
					myWriter.write("List of Employee:"+System.getProperty( "line.separator" ));
					//if there are no commands for adding employee and no employee is being stored the program will crash
					if(staffIndex !=0)
						for (int j = 0; staffIndex + dells>=j ; j++){
							if(staffs[j].getName()!="") {
								myWriter.write(String.format("%-15s %-10s %-11s %-13s %-15s %-12s", "	Employee # " + staffs[j].getStaffId(), staffs[j].getName(), staffs[j].getSurname(),
										staffs[j].getGender(), staffs[j].getBirthdate(), staffs[j].getJob() +System.getProperty( "line.separator" )));
								System.out.println(String.format("%-15s %-10s %-11s %-13s %-15s %-12s", "	Employee # " + staffs[j].getStaffId(), staffs[j].getName(), staffs[j].getSurname(),
										staffs[j].getGender(), staffs[j].getBirthdate(), staffs[j].getJob()));
							}
							
						}

					myWriter.close();
					
				}
				else
					System.out.println("There must be at least one employee for each type to run the hotel.");


			}catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
	    public void listReservations() {
	    	try {
			// append is true in Filewriter causing it not erase last screen
			FileWriter myWriter = new FileWriter("src/resources/Screen.txt",true);
			System.out.println("List of Reservations:");
			myWriter.write("List of Reservations:"+System.getProperty( "line.separator" ));
			//if there are no commands for adding customer and no customer is being stored the program will crash
			for (int j = 0; resRecord.getResCount()>j ; j++){
				Date startDate = intToDate(resRecord.getReservation()[2][j]);
				Date endDate = intToDate(resRecord.getReservation()[3][j]);
				myWriter.write(String.format("%-15s %-20s %-20s %-20s ", "	Room # " +resRecord.getReservation()[1][j] , customers[resRecord.getReservation()[0][j]-1].getName()+" "+ customers[resRecord.getReservation()[0][j]-1].getSurname(),
						startDate.getDateString(),endDate.getDateString())+System.getProperty( "line.separator" ));
						
								
				System.out.println(String.format(String.format("%-15s %-20s %-20s %-20s ", "	Room # " +resRecord.getReservation()[1][j] , customers[resRecord.getReservation()[0][j]-1].getName()+" "+ customers[resRecord.getReservation()[0][j]-1].getSurname(),
						startDate.getDateString(),endDate.getDateString())));
				}
			
			myWriter.close();
    	    

		}catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		}
	    
	    public void searchCustomer(String pattern) {
	    	System.out.println("Search results:");
	    	try {
	    		FileWriter myWriter = new FileWriter("src/resources/Screen.txt",true);
	    		myWriter.write("Search results:"+System.getProperty( "line.separator" ));
	    		 myWriter.close();
	    	}catch (IOException e) {
	    	      System.out.println("An error occurred.");
	    	      e.printStackTrace();
	    	    }
	        for (int i=0;i<=customerIndex;i++) {
	            if (matchPattern(customers[i].getName().toLowerCase(), pattern.toLowerCase())) {
	            	try {
	    	        	// append is true in Filewriter causing it not erase last screen
	    	    	      FileWriter myWriter = new FileWriter("src/resources/Screen.txt",true);
	    	    	      //if there are no commands for adding rooms and no room is being stored the program will crash
	    	    	      myWriter.write(String.format("%-15s %-10s %-20s %-15s %-12s %-12s %-4s %-1s %-1s %-2s %-8s", "	Customer # " + customers[i].getCustomerId(), customers[i].getName(), customers[i].getSurname(),
	    	    	    		  customers[i].getGender(), customers[i].getBirthdate(), customers[i].getAddress(), phones[i].getCountrycode(),"(", phones[i].getCitycode(),")", phones[i].getNumber())+System.getProperty( "line.separator" ));
	    	    	      System.out.println(String.format("%-15s %-10s %-20s %-15s %-12s %-12s %-4s %-1s %-1s %-2s %-8s", "	Customer # " + customers[i].getCustomerId(), customers[i].getName(), customers[i].getSurname(),
	    	    	    		  customers[i].getGender(), customers[i].getBirthdate(), customers[i].getAddress(), phones[i].getCountrycode(),"(", phones[i].getCitycode(),")", phones[i].getNumber()));
	  					  myWriter.close();
	    	    	    } catch (IOException e) {
	    	    	      System.out.println("An error occurred.");
	    	    	      e.printStackTrace();
	    	    	    }
	            }
	        }
	    }

	    private boolean matchPattern(String str, String pattern) {
	        if (pattern.contains("*")) {
	            int idx = pattern.indexOf("*");
	            if (idx == 0) {
	                return str.endsWith(pattern.substring(1));
	            } else if (idx == pattern.length() - 1) {
	                return str.startsWith(pattern.substring(0, idx));
	            } else {
	                String prefix = pattern.substring(0, idx);
	                String suffix = pattern.substring(idx + 1);
	                return str.startsWith(prefix) && str.endsWith(suffix);
	            }
	        } else if (pattern.contains("?")) {
	            if (str.length() != pattern.length()) {
	                return false;
	            }
	            for (int i = 0; i < str.length(); i++) {
	                char c1 = str.charAt(i);
	                char c2 = pattern.charAt(i);
	                if (c2 != '?' && c1 != c2) {
	                    return false;
	                }
	            }
	            return true;
	        } else {
	            return str.equals(pattern);
	        }
	    }

	    public Date intToDate(int d) {
			Date dateOfInt=new Date(1,1,2024);
			if(d<32) {
				dateOfInt=new Date(d,1,2024);
			}
			else if(31<d&&d<61) {
				dateOfInt=new Date(d-31,2,2024);
			}
			else if(60<d&&d<92) {
				dateOfInt=new Date(d-60,3,2024);
			}
			else if(91<d&&d<122) {
				dateOfInt=new Date(d-91,4,2024);
			}
			else if(121<d&&d<153) {
				dateOfInt=new Date(d-121,5,2024);
			}
			else if(152<d&&d<183) {
				dateOfInt=new Date(d-152,6,2024);
			}
			else if(182<d&&d<214) {
				dateOfInt=new Date(d-182,7,2024);
			}
			else if(213<d&&d<245) {
				dateOfInt=new Date(d-213,8,2024);
			}
			else if(244<d&&d<275) {
				dateOfInt=new Date(d-244,9,2024);
			}
			else if(274<d&&d<306) {
				dateOfInt=new Date(d-274,10,2024);
			}
			else if(305<d&&d<336) {
				dateOfInt=new Date(d-305,11,2024);
			}
			else {
				dateOfInt=new Date(d-335,12,2024);
			}
			return dateOfInt;
		}
	    public int dateToInteger(Date d) {
			int daysInDate = 0;
			switch(d.getMonth()){
			case 1:
				daysInDate=d.getDay();
				break;
			case 2:
				daysInDate=31+d.getDay();
				break;
			case 3:
				daysInDate=60+d.getDay();
				break;
			case 4:
				daysInDate=91+d.getDay();
				break;
			case 5:
				daysInDate=121+d.getDay();
				break;
			case 6:
				daysInDate=152+d.getDay();
				break;
			case 7:
				daysInDate=182+d.getDay();
				break;
			case 8:
				daysInDate=213+d.getDay();
				break;
			case 9:
				daysInDate=244+d.getDay();
				break;
			case 10:
				daysInDate=274+d.getDay();
				break;
			case 11:
				daysInDate=305+d.getDay();
				break;
			case 12:
				daysInDate=335+d.getDay();
				break;
			}
			return daysInDate;
		}
	    /* HELEN
	    public void statistics() {
	    	System.out.println("Statistics");
		       
	        try {
	        	// append is true in Filewriter causing it not erase last screen
	    	      FileWriter myWriter = new FileWriter("src/resources/Screen.txt",true);
	    	      myWriter.write("Statistics"+System.getProperty( "line.separator" ));
	    	      //if there are no commands for adding rooms and no room is being stored the program will crash
	    	    	  System.out.println(
		  	            		  "   1.The most reserved room = Room #"+ resRecord.getMostResRoomID() + "\n" +
		  	            		  "   2.The best customer = "+customers[resRecord.getBestCustomer()[0]].getName()+" "+customers[resRecord.getBestCustomer()[0]].getSurname()+"	"+resRecord.getBestCustomer()[1]+" days"+ "\n" +
		  	            	      //"   2.The best customer = "+customers[getBestCustomer()[0]].getName()+" "+customers[getBestCustomer()[0]].getSurname()+"	"+getBestCustomer()[1]+" days"+ "\n" +
		    	    			  "   3.Income = "+ getIncome()+ "\n"+
		    	    			  "     Salary = "+ getSalary()+ "\n"+
		    	    			  "     Constant expenses = "+ getConstantExpenses()+ "\n"+
		    	    			  "     Profit = " + getProfit() + "\n"+
		    	    			  "   4.Monhtly occupancy rate"+ "\n"+
		    	    			  getMonhtlyOccupancyRate());
	    	    	  myWriter.write(
	    	    			  "   1.The most reserved room = Room #"+resRecord.getMostResRoomID()+System.getProperty( "line.separator" ) +
	    	    			  "   2.The best customer = "+customers[resRecord.getBestCustomer()[0]].getName()+" "+customers[resRecord.getBestCustomer()[0]].getSurname()+"	"+resRecord.getBestCustomer()[1]+" days"+System.getProperty( "line.separator" ) +
	    	    			  //"   2.The best customer = "+customers[getBestCustomer()[0]].getName()+" "+customers[getBestCustomer()[0]].getSurname()+"	"+getBestCustomer()[1]+" days"+System.getProperty( "line.separator" ) +
	    	    			  "   3.Income = "+ getIncome()+System.getProperty( "line.separator")+
	    	    			  "     Salary = "+ getSalary()+System.getProperty( "line.separator")+
	    	    			  "     Constant expenses = "+ getConstantExpenses()+System.getProperty( "line.separator")+
	    	    			  "     Profit = " + getProfit() +System.getProperty( "line.separator")+
	    	    			  "   4.Monhtly occupancy rate"+System.getProperty( "line.separator")+
	    	    			  getMonhtlyOccupancyRate());
	    	      myWriter.close();
	    	      
	    	    } catch (IOException e) {
	    	      System.out.println("An error occurred.");
	    	      e.printStackTrace();
	    	    }
	    }
	    
		private String getProfit() {
			int income = 0;
			for(int i=0;i<resRecord.getResCount();i++) {
				int resincome = (int) ((resRecord.getReservation()[3][i]-resRecord.getReservation()[2][i])*rooms[resRecord.getReservation()[1][i]-1].getPrice());
				income+= resincome;
			}
			int salary=0;
			for(int i=0;i<staffIndex+1;i++) {
				salary+= ((int)staffs[i].getSalary())*12;
			}
			int constant=12*10000;
				
			int profit= income-salary-constant;
			return String.format("%,d", income)+" - "+getSalary()+" - "+getConstantExpenses()+" = "+String.format("%,d", profit);
		}

		private String getSalary() {
			int salary=0;
			for(int i=0;i<staffIndex+1;i++) {
				salary+= ((int)staffs[i].getSalary())*12;
			}
			return String.format("%,d", salary);
		}

		private String getConstantExpenses() {
			int constant=12*10000;
			return String.format("%,d", constant);
		}
		
		private String getMonhtlyOccupancyRate() {
			int[] daysInMonths = new int[]{31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};
			float[] occupancy = new float[12];
			int jin =0;
			for(int i=0;i<12;i++)
			{
				occupancy[i]=0;
				for(int k=0;k<roomIndex+1;k++)
				{
					for(int j=jin;j<daysInMonths[i];j++)
					{
						if(resRecord.getResTable()[k][j]>0)
							occupancy[i]++;
					}
				}
				occupancy[i]=(occupancy[i]*100)/((roomIndex+1)*(daysInMonths[i]-jin));
				jin=daysInMonths[i];
				
			}
			return "     " +String.format("%-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s",1,2,3,4,5,6,7,8,9,10,11,12)+"\n     "+
					String.format("%-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s %-7s",Math.round(occupancy[0])+"%",Math.round(occupancy[1])+"%",Math.round(occupancy[2])+"%",Math.round(occupancy[3])+"%",Math.round(occupancy[4])+"%",Math.round(occupancy[5])+"%",Math.round(occupancy[6])+"%",Math.round(occupancy[7])+"%",Math.round(occupancy[8])+"%",Math.round(occupancy[9])+"%",Math.round(occupancy[10])+"%",Math.round(occupancy[11])+"%");
		}

		private String getIncome() {
			int income = 0;
			String str="";
			for(int i=0;i<resRecord.getResCount();i++)
			{
				int resincome = (int) ((resRecord.getReservation()[3][i]-resRecord.getReservation()[2][i])*rooms[resRecord.getReservation()[1][i]-1].getPrice());
				income+= resincome;
				str+=String.format("%,d", resincome);
				if (i<resRecord.getResCount()-1)
					str+=" + ";
			}
			str+=" = "+String.format("%,d", income);
			return str;
		}
*/
	    /*BAHADIR  */
	   public void statistics()
	    {
	    	try 
	    	{
	    		FileWriter myWriter = new FileWriter("src/resources/Screen.txt",true);
		    	System.out.println("\nStatistics\n");
		    	myWriter.write("\nStatistics\n");
		    	myWriter.close();
	    	}
	    	catch(IOException e)
	    	{
	    		System.out.println("An error occured.");
	    	}
  	
	    	mostReservedRoom();
	    	bestCustomer();
	    	moneySituation();
	    	occupancyRate();
	    }
	    public void mostReservedRoom()
	    {
	    	try
	    	{
	    	int theRoom = 0;
	    	int theTime = 0;
	    	
	    	int[] reserveTime = new int[30];
	    	
	    	int[][] rr = new int[4][resRecord.getResCount()];//reservation record
	    	rr=resRecord.getReservation();
	    	
	    	// finding all reserve times and adding it to room number index.
	    	for(int i = 0;i<resRecord.getResCount();i++) 
	    	{
	    		reserveTime[rr[1][i]-1] += rr[3][i]-rr[2][i];
	    	}
	    	
	    	// finding biggest number
	    	for(int i = 0;i<30;i++)
	    	{
	    		if(reserveTime[i]>theTime)
	    		{
	    			theTime=reserveTime[i];
	    			theRoom = i+1;
	    		}
	    	}
	    	
	    	FileWriter myWriter = new FileWriter("src/resources/Screen.txt",true);
	    	myWriter.write("  1. The most reserved room = Room #"+ theRoom + " with "+theTime+" days\n");
	    	myWriter.close();
	    	
	    	System.out.println("  1. The most reserved room = Room #"+ theRoom +" with "+theTime+" days");
	    	
	    	}
	    	catch (IOException e) 
	    	{
				System.out.println("1. An error occurred.");
				e.printStackTrace();
	    	}
	    	
	    }
	    public void bestCustomer()
	    {
	    	try 
	    	{
	    		FileWriter myWriter = new FileWriter("src/resources/Screen.txt",true);
	    		
	    		int theCustomerID = 0;
	    		int theTime = 0;
	    		
	    		int[] reserveTime = new int [customerIndex+1];
	    		
	    		int[][] rr = new int[4][resRecord.getResCount()];//reservation record
		    	rr=resRecord.getReservation();
		    	
		    	// finding all reserve times and adding it to customer number index.
		    	for(int i = 0; i< resRecord.getResCount();i++)
		    	{
		    		reserveTime[rr[0][i]-1] += rr[3][i]-rr[2][i];
		    	}
	    		
		    	// finding biggest number
		    	for(int i = 0; i<customerIndex+1;i++)
		    	{
		    		if(reserveTime[i]>theTime)
		    		{
		    			theTime = reserveTime[i];
		    			theCustomerID = i+1;
		    		}
		    	}
		    	System.out.printf("\n  2. The best customer = %s %s with %d days",customers[theCustomerID-1].getName(), customers[theCustomerID-1].getSurname(),theTime);
		    	myWriter.write(String.format("\n  2. The best customer = %s %s with %d days",customers[theCustomerID-1].getName(), customers[theCustomerID-1].getSurname(),theTime));
		    	
	    		myWriter.close();
	    	}
	    	catch(IOException e)
	    	{
	    		System.out.println("2. An error occurred.");
				e.printStackTrace();
	    		
	    	}
	    }
	    public void moneySituation()
	    {
	    	try
	    	{
	    		FileWriter myWriter = new FileWriter("src/resources/Screen.txt",true);
	    		
	    		// INCOME
	    		
	    		int sumIncome = 0;
	    		
	    		System.out.printf("\n\n  3. Income = ");
	    		myWriter.write("\n\n  3. Income = ");
	    		
	    		int[][] rr = new int[4][resRecord.getResCount()];//reservation record
		    	rr=resRecord.getReservation();
	    		
		    	int roomCounter = roomIndex+1;
		    	
		    	int[] incomeByRoom = new int[roomCounter];
		    	
	    		for(int i = 0;i <resRecord.getResCount();i++)
	    		{
	    			int days = rr[3][i]-rr[2][i];
	    			incomeByRoom[rr[1][i]-1] += days*rooms[rr[1][i]-1].getPrice();
	    		}
	    		for(int i = 0;i< roomCounter;i++)
	    		{
	    			sumIncome += incomeByRoom[i];
	    			System.out.printf(" %,d ", incomeByRoom[i]);
	    			myWriter.write(String.format(" %,d ", incomeByRoom[i]));
	    			if(roomCounter!=i+1)
	    			{
	    				System.out.printf("+");
	    				myWriter.write("+");
	    			}
	    		}
	    		
	    		System.out.printf("= %,d\n",sumIncome);
	    		myWriter.write(String.format("= %,d\n",sumIncome));
	    		
	    		// SALARY
	    		
	    		System.out.printf("     Salary = ");
	    		myWriter.write("     Salary = ");
	    		float sumSalary = 0;
	    		
	    		for(int i = 0;i<staffIndex+1;i++)
	    		{
	    			sumSalary += staffs[i].getSalary();
	    		}
	    		sumSalary *=12;
	    		
	    		System.out.printf("%,.0f",sumSalary);
	    		myWriter.write(String.format("%,.0f",sumSalary));
	    		
	    		// CONSTANT EXPENSE
	    		
	    		float constantExpense = 120000; // 12 month * 10.000
	    		
	    		System.out.printf("\n     Constant Expense = %,.0f", constantExpense);
	    		myWriter.write(String.format("\n     Constant Expense = %,.0f", constantExpense));
	    		
	    		// PROFIT
	    		
	    		float profit = sumIncome-(sumSalary+constantExpense);
	    		
	    		System.out.printf("\n     Profit = %,.0f\n", profit);
	    		myWriter.write(String.format("\n     Profit = %,.0f\n", profit));
	    		
	    		myWriter.close();
	    	}
	    	catch (IOException e) 
	    	{
				System.out.println("3. An error occurred.");
	    	}
	    }
	    public void occupancyRate()
	    {
	    	try 
	    	{
	    		FileWriter myWriter = new FileWriter("src/resources/Screen.txt",true);
	    		
	    		int[] daysInMonths = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	    		float[] monthsPercent = new float[12];
	    		float PERCENT_OF_A_ROOM = 100/(roomIndex+1);
	    		
	    		int[][] rr = new int[4][resRecord.getResCount()];//reservation record
		    	rr=resRecord.getReservation();
		    	
		    	for(int i = 0;i<resRecord.getResCount();i++)
		    	{
		    		float occupationStartDay = rr[2][i];
		    		float occupationEndDay = rr[3][i]-1;
		    		float occupiedDay = rr[3][i] - rr[2][i];
		    		occupiedDay+=1;
		    		
		    		int k = 0, j = 0; // k = start month index, j = end month index
		    		
		    		while(occupationStartDay-daysInMonths[k]>0)
		    		{
		    			occupationStartDay-=daysInMonths[k];
		    			
		    			k++;
		    		}
		    		
		    		while(occupationEndDay-daysInMonths[j]>0)
		    		{
		    			occupationEndDay-=daysInMonths[j];
		    			
		    			j++;
		    		}
		    		
		    		// if start and end same month
		    		
		    		if(k==j)
		    		{
		    			monthsPercent[k] += ((occupiedDay/daysInMonths[k])*PERCENT_OF_A_ROOM);
		    		}
		    		else
		    		{
		    			// start month
			    		
			    		monthsPercent[k] += (((daysInMonths[k]-occupationStartDay)/daysInMonths[k])*PERCENT_OF_A_ROOM);
			    		
			    		// middle months
			    		
			    		int l = k+1;
			    		
			    		while(l<j)
			    		{
			    			monthsPercent[l] += PERCENT_OF_A_ROOM;
			    			l++;
			    		}
			    		
			    		// end month
			    		
			    		monthsPercent[j] += ((occupationEndDay/daysInMonths[j])*PERCENT_OF_A_ROOM);
		    		}
		    	}
		    	
		    	System.out.printf("\n  4. Monthly occupancy rate:\n");
		    	myWriter.write(String.format("\n  4. Monthly occupancy rate:\n"));
		    	
		    	for(int i = 0;i<12;i++)
		    	{
		    		System.out.printf("%7d", i+1);
		    		myWriter.write(String.format("%7d", i+1));
		    	}
		    	
		    	myWriter.write(String.format("\n    "));
		    	System.out.println();
		    	System.out.print("    ");
		    	
		    	for(int i = 0;i<12;i++)
		    	{
		    		myWriter.write(String.format("%4.1f", monthsPercent[i]));
		    		myWriter.write("%  ");
		    		
		    		System.out.printf("%4.1f", monthsPercent[i]);
		    		System.out.print("%  ");
		    	}
		    	myWriter.write("\n");
		    	System.out.println();
	    		myWriter.close();
	    	}
	    	catch(IOException e) 
	    	{
	    		System.out.println("4. An error occured.");
	    	}
	    }
	  
		public static int getCustomerIndex() {
			return customerIndex;
		}
		public static int getRoomIndex() {
			return roomIndex;
		}
		public static int getStaffIndex() {
			return staffIndex;
		}
}
