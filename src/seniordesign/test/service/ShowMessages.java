package seniordesign.test.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class ShowMessages extends Service implements LocationListener {
	LocationManager lm;

	public void onCreate() {
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		Toast.makeText(getApplicationContext(), "Location display is on", Toast.LENGTH_SHORT).show();
	}
	
	public void onDestroy() {
		lm.removeUpdates(this);
		Toast.makeText(getApplicationContext(), "Location display is off", Toast.LENGTH_SHORT).show();
	}

	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onStart() {
	}

	public void onLocationChanged(Location loc) {
		String msg = "Location changed:  lat=" + loc.getLatitude() + "  long=" + loc.getLongitude();
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
