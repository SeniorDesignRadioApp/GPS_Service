package seniordesign.test.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Control extends Activity {
	Intent in;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        in = new Intent(this, ShowMessages.class);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_control, menu);
        return true;
    }
    
    public void startMessages(View view) {
    	/* tell the service to start displaying GPS updates */
    	startService(in);
    }
    
    public void stopMessages(View view) {
    	/* tell the service to stop displaying GPS updates */
    	stopService(in);
    }
}
