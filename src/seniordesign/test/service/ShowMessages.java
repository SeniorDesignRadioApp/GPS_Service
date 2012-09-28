package seniordesign.test.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;
/**
 * This service will start up and stop a thread to continuously find the current GPS location.
 */
public class ShowMessages extends Service implements LocationListener {
	//constants for requestLocationUpdates
	private static final int MIN_TIME_MILLISECONDS = 0;
	private static final int MIN_DIST_METERS = 0;
	//constants for Thread (in milliseconds)
	private final int THREAD_TIMER = 30*1000;
	
	private LocationManager lm;
	private int display_count = 0;
	//variables for thread
	private boolean thread_started = false;
	private Thread mThread = new Thread(new RepeatingThread());
	private final Handler mHandler = new Handler();
	//variables for averaging gps location
	private double latitude_temp = 0;
	private double longitude_temp = 0;
	private int lat_long_count_temp = 0;

	/**
	 * On "startMessages" start LocationManager (gps service)
	 *   setup requestLocationUpdates (by time and distance)
	 *   change textView1 to "Service Started"
	 *   setup thread
	 */
	public void onCreate() {
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_MILLISECONDS, 
				MIN_DIST_METERS, this);
		Toast.makeText(getApplicationContext(), "Location display is on", 
				Toast.LENGTH_SHORT).show();
		Control.changeText("Service Started");
		
		//only start thread if one isn't already running
		if(!thread_started){
			thread_started = true;
			mThread.start();
		}
	}
	
	/**
	 * Thread that is repeated every 30 seconds till "onDestroy" is called
	 */
	public class RepeatingThread implements Runnable {
	    public RepeatingThread() {}
	    /*
	     * If the longitude, latitude or counter are still at 0 do nothing
	     * Else find the average and display to screen
	     */
	    public void run() {
	    	if(longitude_temp != 0 && latitude_temp != 0 && lat_long_count_temp != 0) {
		    	double average_longitude = longitude_temp / lat_long_count_temp;
		    	double averate_latitude = latitude_temp / lat_long_count_temp;
		    	Control.changeText("Timer Started " + display_count + "\n" + 
		    			"Latitude = " + averate_latitude + "\n" + 
		    			"Longitude = " + average_longitude);
		    	display_count++;
		    	longitude_temp = 0;
		    	latitude_temp = 0;
		    	lat_long_count_temp = 0;
	    	}
	        mHandler.postDelayed(mThread, THREAD_TIMER);       
	    }
	}

	/**
	 * Called when "stopMessage" is run
	 * Stops Threads and resets all variables
	 */
	public void onDestroy() {
		lm.removeUpdates(this);
		Toast.makeText(getApplicationContext(), "Location display is off", 
				Toast.LENGTH_SHORT).show();
		longitude_temp = 0;
    	latitude_temp = 0;
    	lat_long_count_temp = 0;
    	//only stop thread if it's already running
		if(thread_started){
			mHandler.removeCallbacks(mThread);
			if(mThread != null){mThread.interrupt();}
			thread_started = false;
			display_count = 0;
		}
	}

	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onStart() {
	}

	/**
	 * Called when location is changed
	 * If the thread is going then update location
	 * Else reset location
	 */
	public void onLocationChanged(Location loc) {
		if(thread_started){
			latitude_temp += loc.getLatitude();
			longitude_temp += loc.getLongitude();
			lat_long_count_temp++;
		} else {
			latitude_temp = 0;
			longitude_temp = 0;
			lat_long_count_temp = 0;
		}
	}

	public void onProviderDisabled(String provider) {
		Toast.makeText(getApplicationContext(), "GPS disabled", Toast.LENGTH_SHORT).show();	
	}

	public void onProviderEnabled(String provider) {
		Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_SHORT).show();	
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
