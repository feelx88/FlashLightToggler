/**
Copyright 2013 Felix M�ller.

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

import java.util.List;

import android.annotation.TargetApi;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class FLTSettings extends PreferenceActivity {
	
	protected static ComponentName mDeviceAdmin;
	protected static DevicePolicyManager mPolicyManager;
	
	private static final int RESULT_CODE_DEVICE_ADMIN = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mDeviceAdmin = new ComponentName( this, FLTDeviceAdminReceiver.class );
		mPolicyManager = (DevicePolicyManager)getSystemService( DEVICE_POLICY_SERVICE );
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource( R.xml.preference_headers, target );
		super.onBuildHeaders(target);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class GlobalPrefs extends PreferenceFragment
	{		
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource( R.xml.global_prefs );
			
			findPreference( "deviceAdmin" ).setOnPreferenceClickListener( new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					
					if( preference.getSharedPreferences().getBoolean( "deviceAdmin", false ) )
					{
						Intent intent = new Intent( DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN );
						intent.putExtra( DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdmin );
						intent.putExtra( DevicePolicyManager.EXTRA_ADD_EXPLANATION,
								getString( R.string.deviceAdminSummary ) );
						startActivityForResult( intent, RESULT_CODE_DEVICE_ADMIN );
						return false;
					}
					else
					{
						mPolicyManager.removeActiveAdmin( mDeviceAdmin );
						return true;
					}
				}
				
			} );
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			
			if( requestCode == RESULT_CODE_DEVICE_ADMIN )
			{
				if( mPolicyManager.isAdminActive( mDeviceAdmin ) )
					findPreference( "deviceAdmin" ).setEnabled( true );
			}
			
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
}
