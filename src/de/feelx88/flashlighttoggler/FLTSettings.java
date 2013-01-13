package de.feelx88.flashlighttoggler;

import java.util.List;

import android.annotation.TargetApi;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class FLTSettings extends PreferenceActivity {
	
	protected static ComponentName mDeviceAdmin;
	protected static DevicePolicyManager mPolicyManager;
	
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
						intent.putExtra( DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Add Device Admin" );
						startActivityForResult( intent, 1 );
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
			
			if( requestCode == 1 )
			{
				if( mPolicyManager.isAdminActive( mDeviceAdmin ) )
					findPreference( "deviceAdmin" ).setEnabled( true );
			}
			
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
}
