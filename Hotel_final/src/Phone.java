
public class Phone{

   private String countrycode;
   private String citycode;
   private String number;


  public Phone(String inputCountrycode,String inputCitycode,String number){
	  countrycode = inputCountrycode;
	  citycode =inputCitycode;
	  this.number = number;
  }


public String getCountrycode() {
	return countrycode;
}


public void setCountrycode(String countrycode) {
	this.countrycode = countrycode;
}


public String getCitycode() {
	return citycode;
}


public void setCitycode(String citycode) {
	this.citycode = citycode;
}


public String getNumber() {
	return number;
}


public void setNumber(String number) {
	this.number = number;
}

  
}
