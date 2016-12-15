package kiwi.mobile.project;

public interface DoorStatusListener {
	public void door_status( String status);
	public void door_err( String err);
	public void door_exp( Exception exception);
}