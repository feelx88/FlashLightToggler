package de.feelx88.flashlighttoggler;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class MainActivity extends Activity {
	
	DevicePolicyManager mPolicyManager;
	ComponentName mAdminReceiver;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if( !stopService( new Intent( this, FlashLightService.class ) ) )
    		startService( new Intent( this, FlashLightService.class ) );
        
        mPolicyManager = (DevicePolicyManager)getSystemService( DEVICE_POLICY_SERVICE );
        mAdminReceiver = new ComponentName( this, FLTDeviceAdminReceiver.class );
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( this );
        
        if( mPolicyManager.isAdminActive( mAdminReceiver ) && prefs.getBoolean( "lockOnActivate", false ) )
        {
        	mPolicyManager.lockNow();
        	new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                	mPolicyManager.lockNow();
                }
            }, 500);
        	new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                	mPolicyManager.lockNow();
                }
            }, 1000);
        }
        
        finish();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    }
    
    @Override
    protected void onStart() {
    	super.onStart();    	
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
