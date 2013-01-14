package de.feelx88.flashlighttoggler;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;


public class FlashLightService extends Service {

	private Camera mCamera;
	private Notification mNotification;
	
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
	
	@SuppressLint("NewApi")
	private void createNotification()
	{
		PendingIntent intent = PendingIntent.getActivity( this, 0,
        		new Intent( this, MainActivity.class ), PendingIntent.FLAG_ONE_SHOT );
		
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
		{
		
			mNotification = new Notification.Builder( this )
		        .setContentTitle( "Flash light activated" )
		        .setContentText( "Click to disable" )
		        .setSmallIcon( R.drawable.ic_flashlight_on )
		        .setOngoing( true )
		        .setPriority( Notification.PRIORITY_HIGH )
		        .setContentIntent( intent )
		        .build();
		}
		else if(  Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB )
		{
			mNotification = new Notification.Builder( this )
		        .setContentTitle( "Flash light activated" )
		        .setContentText( "Click to disable" )
		        .setSmallIcon( R.drawable.ic_flashlight_on )
		        .setOngoing( true )
		        .setContentIntent( intent )
		        .build();
		}
		else
			Toast.makeText( this, "Started flash light", Toast.LENGTH_SHORT ).show();
		
		if( mNotification != null )
			( (NotificationManager)getSystemService( NOTIFICATION_SERVICE ) ).notify( 0, mNotification );
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mCamera.release();
		( (NotificationManager)getSystemService( NOTIFICATION_SERVICE ) ).cancel( 0 );
	}

}
