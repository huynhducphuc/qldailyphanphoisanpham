package bean;

import java.io.Serializable;

public class Position implements Serializable {
	public static String latitude = "1";
	public static String longitude = "2";
	public static void getPosition(String lat, String longt){
		latitude = lat;
		longitude = longt;
		
		System.out.println("latitude :"+ latitude +" , "+ longitude);
	}
	public static String getLatitude(){
		return latitude;
	}
	public static String getLongitude(){
		return longitude;
	}
}
