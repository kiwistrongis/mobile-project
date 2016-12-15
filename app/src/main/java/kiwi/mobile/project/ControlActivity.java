package kiwi.mobile.project;

// standard library imports
import java.util.Vector;

// android imports
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// android support imports
import android.support.v4.content.ContextCompat;
import android.support.v7.app.*;
import android.support.v7.widget.Toolbar;

public class ControlActivity extends AppCompatActivity 
		implements DoorStatusListener {
	private final static int ADD_TOKEN_REQ = 1;

	// android ui components
	private Button connect_button;
	private Button relay_button;
	private Button delete_button;
	private TextView view_door;
	private TextView view_status;
	private TextView log_view;

	// variables
	private String door_host = null;

	@Override
	protected void onCreate( Bundle bundle){
		super.onCreate( bundle);
		this.setContentView( R.layout.activity_control);

		// set up text views
		this.view_door = (TextView) findViewById( R.id.view_door);
		this.view_status = (TextView) findViewById( R.id.view_status);

		// set up log
		this.log_view = (TextView) findViewById( R.id.log_view);
		this.log_view.setMovementMethod( new ScrollingMovementMethod());
		this.log_view.setTypeface( Typeface.MONOSPACE);

		// set up buttons
		this.connect_button = (Button) findViewById( R.id.connect_button);
		this.relay_button = (Button) findViewById( R.id.relay_button);
		this.connect_button.setOnClickListener( new View.OnClickListener(){
			public void onClick( View view){
				ControlActivity.this.get_host();
			}});
		this.relay_button.setOnClickListener( new View.OnClickListener(){
			public void onClick( View view){
				ControlActivity.this.start_relay();
			}});
		this.connect_button.setEnabled( true);
		this.relay_button.setEnabled( true);}

	@Override
	protected void onDestroy(){
		super.onDestroy();}

	/*@Override
	public boolean onCreateOptionsMenu( Menu menu){
		super.onCreateOptionsMenu( menu);
		MenuInflater inflater = this.getMenuInflater();
		inflater.inflate( R.menu.main, menu);
		return true;}*/

	@Override
	public boolean onOptionsItemSelected( MenuItem item){
		int id = item.getItemId();
		if( R.id.action_add_token == id){
			//launchAddProductActivity();
			return true;}
		return super.onOptionsItemSelected( item);}

	/*public void launchAddProductActivity(){
		Util.log( "launching add product activity...");
		Intent intent = new Intent( this, AddProductActivity.class);
		startActivityForResult( intent, ADD_TOKEN_REQ);
		Util.log( "add product activity launched");}*/

	public void get_host(){
		final EditText host_text = new EditText( this);
		if( door_host != null){
			host_text.setText( door_host);}
		new AlertDialog.Builder(this)
			.setTitle("Connect to Door Server")
			.setMessage("Format: 'hostname:port'")
			.setView( host_text)
			.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					String host = host_text.getText().toString();
					ControlActivity.this.set_host( host);}})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {}})
			.show();}
	public void set_host( String host){
		this.door_host = host;
		this.view_door.setText( host);
		this.check_status();}
	public void start_relay(){}

	public void check_status(){
		this.view_status.setText( "Connecting...");
		new DoorStatusTask( this, this.door_host).execute();}


	/*@Override
	protected void onActivityResult( int req, int result, Intent data) {
		Util.log( "activity result: %d, %d", req, result);
		if( req == ADD_TOKEN_REQ && result == RESULT_OK){
			Util.log( "returned!");}}*/

	@Override
	public void door_status( String status){
		this.view_status.setTextColor(
			ContextCompat.getColor( this,
				status.equals( "locked") ?
					R.color.color_bad : R.color.color_good));
		this.view_status.setText( status);}
	@Override
	public void door_err( String err){
		this.log( err);
		this.view_status.setText( err);}
	@Override
	public void door_exp( Exception exception){
		this.view_status.setTextColor(
			ContextCompat.getColor( this, R.color.color_err));
		this.view_status.setText( "Error...");
		this.log(
			"Connection error - Did you enter the host correctly?: %s",
			exception);}

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
