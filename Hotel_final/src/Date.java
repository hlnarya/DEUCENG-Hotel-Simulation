public class Date{

  private int day;
  private int month;
  private int year;
  private int[] daysOfMonths;

  
//Constructor of Date class. It creates a new date if given values are valid.
  public Date(int day, int month, int year) throws IllegalArgumentException {
	  
	  daysOfMonths = new int[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	  if(year != 2024)
	      throw new IllegalArgumentException("Invalid date: " + day + "/" + month + "/" + year );
	  else if(month < 1 || month > 12)
	      throw new IllegalArgumentException("Invalid date: " + day + "/" + month + "/" + year);
	  else if(day > daysOfMonths[month - 1]) {
		  throw new IllegalArgumentException("Invalid date: " + day + "/" + month + "/" + year);
	  }
	  else {
	  this.day = day;
	  this.month = month;
	  this.year = year;
	  }
	  
  }
  	  public int getDay(){
	    return this.day;
	  }
	  public int getMonth(){
	    return this.month;
	  }
	  public int getYear(){
	    return this.year;
	  }
	  public String getDateString(){
		  String dayS = String.valueOf(day);
		  String monthS = String.valueOf(month);
		  String yearS = String.valueOf(year);
		    return dayS+"."+monthS+"."+yearS;
		  }
	  
}
