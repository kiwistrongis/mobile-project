package kiwi.mobile.project;

// standard library imports
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

// android imports
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// javatuples imports
//import org.javatuples.Triplet;

// json-simple imports
//import org.json.simple.*;
//import org.json.simple.parser.*;

// json-simpler imports
//import kuro.json.JSONAdapter;

public class DebugActivity extends Activity {
	// members
	private Button launch_button;
	private TextView log_view;

	@Override
	protected void onCreate( Bundle savedInstanceState){
		super.onCreate( savedInstanceState);
		this.setContentView( R.layout.activity_debug);

		// set up log
		this.log_view = (TextView) findViewById( R.id.log_view);
		this.log_view.setMovementMethod( new ScrollingMovementMethod());
		this.log_view.setTypeface( Typeface.MONOSPACE);

		// set up button
		this.launch_button = (Button) findViewById( R.id.launch_button);
		this.launch_button.setOnClickListener( new View.OnClickListener(){
			public void onClick( View view){
				DebugActivity.this.launchBrowseActivity();}
		});}

	public void launchBrowseActivity(){
		this.log( "launching browse activity...");
		Intent intent = new Intent( this, ControlActivity.class);
		startActivity( intent);
		this.log( "control activity launched");}

	@Override
	protected void onDestroy(){
		super.onDestroy();}

	/** Write a format string to the logs **/
	private void log( String fmt, Object... params){
		String content = String.format( fmt, params);
		Util.log( content);
		log_view.append( "\n");
		log_view.append( content);

		// scroll log view to bottom
		int scrollAmount = log_view.getLayout().getLineTop(
			log_view.getLineCount()) - log_view.getHeight();
		if( scrollAmount > 0)
			log_view.scrollTo( 0, scrollAmount);
		else
			log_view.scrollTo( 0, 0);}
}
