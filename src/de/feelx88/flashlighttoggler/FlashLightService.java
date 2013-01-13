package de.feelx88.flashlighttoggler;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Build;
import android.os.IBinder;


public class FlashLightService extends Service {

	private Camera mCamera;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		mCamera = Camera.open();
		
		Camera.Parameters params = mCamera.getParameters();
    	params.setFlashMode( Camera.Parameters.FLASH_MODE_TORCH );
        mCamera.setParameters( params );
        
        createNotification();
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void createNotification()
	{
		PendingIntent intent = PendingIntent.getActivity( this, 0,
        		new Intent( this, MainActivity.class ), PendingIntent.FLAG_ONE_SHOT );
		
		new Notification.Builder( this )
	        .setContentTitle( "Flash light activated" )
	        .setContentText( "Click to disable" )
	        .setSmallIcon( R.drawable.ic_flashlight_on )
	        .setOngoing( true )
	        .setPriority( Notification.PRIORITY_HIGH )
	        .setContentIntent( intent )
	        .build();
	}
	
	private void createNotification()
	{
		PendingIntent intent = PendingIntent.getActivity( this, 0,
        		new Intent( this, MainActivity.class ), PendingIntent.FLAG_ONE_SHOT );
		
		new Notification.Builder( this )
	        .setContentTitle( "Flash light activated" )
	        .setContentText( "Click to disable" )
	        .setSmallIcon( R.drawable.ic_flashlight_on )
	        .setOngoing( true )
	        .setPriority( Notification.PRIORITY_HIGH )
	        .setContentIntent( intent )
	        .build();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mCamera.release();
	}

}
