/**
Copyright 2013 Felix Müller.

This file is part of FlashLightToggler.

FlashLightToggler is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

FlashLightToggler is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with FlashLightToggler.  If not, see <http://www.gnu.org/licenses/>.
**/

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
        
        mPolicyManager = (DevicePolicyManager)getSystemService( DEVICE_POLICY_SERVICE );
        mAdminReceiver = new ComponentName( this, FLTDeviceAdminReceiver.class );
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( this );
        
        boolean lock = prefs.getBoolean( "lockOnDeActivate", false );
        
        if( !stopService( new Intent( this, FlashLightService.class ) ) )
        {
    		startService( new Intent( this, FlashLightService.class ) );
    		lock = prefs.getBoolean( "lockOnActivate", false );
        }
        
        if( mPolicyManager.isAdminActive( mAdminReceiver ) && lock )
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
    
}
