package kiwi.mobile.project;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.*;
import java.net.*;
import java.util.List;


public class DoorStatusTask extends AsyncTask<Void,Void,String> {
	private static final String url_form = "http://%s/status";
	private DoorStatusListener listener = null;
	private URL url = null;
	private Exception exception = null;

	public DoorStatusTask( DoorStatusListener listener, String host){
		this.listener = listener;
		try{ 
			this.url = new URL( String.format( url_form, host));}
		catch( Exception ex){}}	

	protected String doInBackground( Void... v){
		String response = null;
		if( url == null){
			this.exception = new Exception( "Error: Malformed host!");
			return response;}
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if( conn.getResponseCode() == HttpURLConnection.HTTP_OK){
				BufferedReader reader = new BufferedReader(
					new InputStreamReader( conn.getInputStream()));
				response = reader.readLine();}}
		catch( Exception ex){
			this.exception = ex;}
		return response;}

	protected void onPostExecute( String response){
		if( exception != null)
			this.listener.door_exp( exception);
		else if( response.startsWith( "err:"))
			this.listener.door_err( response);
		else
			this.listener.door_status( response);}
}
