package seniordesign.test.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class Control extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_control, menu);
        return true;
    }
    
    public void startMessages(View view) {
    	Toast.makeText(getApplicationContext(), "you pressed the Start button", Toast.LENGTH_SHORT).show();
    	// tell the service that we want it to do something (aka display the coordinates whenever they change)
    	Intent start = new Intent(this, ShowMessages.class);
    	startService(start);
    }
    
    public void stopMessages(View view) {
    	/*
    	Intent stop = new Intent(this, ShowMessages.class);
    	stopService(stop);
    	*/
    	Toast.makeText(getApplicationContext(), "you pressed the Stop button", Toast.LENGTH_SHORT).show();
    }
}
