package seniordesign.test.service;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Control extends Activity {
	private Intent in;
	public static TextView textView1;

	/**
	 * On startup starts the Intent "ShowMessages" and sets the textView1 to "On Create"
	 */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        in = new Intent(this, ShowMessages.class);

        textView1 = (TextView) findViewById(R.id.textView1);
        
        changeText("On Create");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_control, menu);
        return true;
    }
    
    /**
     * Changes textView1 to string msg
     */
    public static void changeText(String msg) {
    	textView1.setText(msg);
    }
    
    /**
     * tell the service to start displaying GPS updates
     */
    public void startMessages(View view) {
    	startService(in);
    }
    
    /**
     * tell the service to stop displaying GPS updates
     */
    public void stopMessages(View view) {
    	changeText("stopped");
    	stopService(in);
    }
    
    /**
     * When the BACK key is pressed ask the user if they want to quit
     * if they do then stop the service and exit the program
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
        	@SuppressWarnings("unused")
			AlertDialog alertbox = new AlertDialog.Builder(this)
            .setMessage("Do you want to exit the application?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                // stop the service and end the program
                public void onClick(DialogInterface arg0, int arg1) {
                	stopService(in);
                    finish();
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                // return to program
                public void onClick(DialogInterface arg0, int arg1) {}
            })
            
            .show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
