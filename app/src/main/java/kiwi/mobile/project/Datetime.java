package kiwi.mobile.project;

// standard library imports
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Datetime {
	public int year;
	public int day;
	public long milli;

	public Datetime( int year, int day, long milli){
		this.year = year;
		this.day = day;
		this.milli = milli;}

	public static final Datetime nowUtc(){
		TimeZone utc = TimeZone.getTimeZone( "UTC");
		Calendar calendar = new GregorianCalendar( utc);
		int year = calendar.get( Calendar.YEAR);
		int day = calendar.get( Calendar.DAY_OF_YEAR);
		long hour = calendar.get( Calendar.HOUR_OF_DAY);
		long minute = 60*hour + calendar.get( Calendar.MINUTE);
		long second = 60*minute + calendar.get( Calendar.SECOND);
		long milli = 1000*second + calendar.get( Calendar.MILLISECOND);
		return new Datetime( year, day, milli);}

	public String toString(){
		return String.format(
			"%03x.%03x.%07x",
			this.year, this.day, this.milli);}
}