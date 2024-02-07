import java.util.Arrays;

public class Reservation {
	private int[][] resTable;
	private int resCustomerID;
	private int resRoomID;
	private Date dateArrival;
	private Date dateDeparture;
	private boolean resStatus;
	private int resCounter;
	public Reservation() {
		this.resTable=new int[30][366];
		this.resStatus=true;
		
	}
	public void addReservation(int customerID,int roomID, Date startDate, Date endDate ) {
		this.resCustomerID = customerID;
		this.resRoomID = roomID;
		this.dateArrival = startDate;
		this.dateDeparture = endDate;
		int sd=dateToInteger(startDate);
		int ed=dateToInteger(endDate);
		for(int i=sd; i<ed;i++)
		{
			if(resTable[roomID-1][i-1]!=0)
				this.resStatus=false;
		}
			
		if(resStatus)
		{
			for(int i=sd; i<ed;i++) 
				this.resTable[roomID-1][i-1]=customerID;
			if(sd>1)
			{
				if(resTable[roomID-1][sd-2]!=customerID)
					this.resCounter++;
			}else
				this.resCounter++;
				
		}
			
		else {
			System.out.println("invalid reservation!");
			this.resStatus=true;
		}
			
	}
	public int[][] getReservation() {
		int[][] resInt = new int[4][resCounter];
		int sd=0;
		int resCount = 0;
		for(int i=0;i<30;i++) {
			for(int j=0 ; j<365;j++) {
				if(resTable[i][j]>0) {
					if(sd==0)
						sd=j+1;
					if(resTable[i][j]!=resTable[i][j+1])
					{
						resInt[0][resCount]=resTable[i][j];
						resInt[1][resCount]=i+1;
						resInt[2][resCount]=sd;
						resInt[3][resCount]=j+2;
						resCount++;
						sd=0;
					}
						
				}
			}
		}
			
		return resInt;
	}
	public int getResCount() {
		return resCounter;
		
	}
	public int getResRoomID() {
		return resRoomID;
	}
	public int[][] getResTable(){
		return resTable;
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
	public int[] getAvailableRoom(Date sDateSearch, Date eDateSearch) {
		int[] availableRoom = new int [30];
		Arrays.fill(availableRoom, 1);
		int sDate = dateToInteger(sDateSearch);
		int eDate= dateToInteger(eDateSearch);
		for(int i=0; i<30; i++) {
			for(int j=sDate;j<eDate;j++)
			{
					if(resTable[i][j]>0)
						availableRoom[i]=0;
			}
		}
		return availableRoom;
	}
	int[] getBestCustomer() {
		int[] days=new int[HotelManagement.getCustomerIndex()+1];
		Arrays.fill(days, 0);
		for(int i=0;i<getResCount();i++)
			days[getReservation()[0][i]-1]+=(getReservation()[3][i]-getReservation()[2][i]);
		int max[] = {0,0};

		for (int i = 0; i < HotelManagement.getCustomerIndex()+1; i++) {
		    max[0] = days[i] > days[max[0]] ? i : max[0];
		}
		max[1]=days[max[0]];
		return max;
	}

	int getMostResRoomID() {
		int[] days=new int[HotelManagement.getRoomIndex()+1];
		Arrays.fill(days, 0);
		for(int i=0;i<getResCount();i++)
			days[getReservation()[1][i]-1]+=(getReservation()[3][i]-getReservation()[2][i]);
		int maxAt = 0;

		for (int i = 0; i < HotelManagement.getRoomIndex()+1; i++) {
			maxAt = days[i] > days[maxAt] ? i : maxAt;
		}
		return maxAt+1;
	}
}
