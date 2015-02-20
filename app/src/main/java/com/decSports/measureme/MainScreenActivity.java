package com.decSports.measureme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainScreenActivity extends Activity {
	private static final String TAG = "MainScreenActivity";
	
	private static final int REQUEST_PHOTO = 1;
	
	private Button mStartButton;
	private Button mHelpButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainscreen);
		
		mStartButton = (Button)findViewById(R.id.start_button);
		mStartButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(MainScreenActivity.this, MeasureCameraActivity.class);
				startActivity(i);
			}
		});
		
		mHelpButton = (Button)findViewById(R.id.help_button);
		mHelpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(MainScreenActivity.this, R.string.not_yet, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preview, menu);
		return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) return;
		
		if (requestCode == REQUEST_PHOTO) {
			String filename = data.getStringExtra(MeasureCameraFragment.EXTRA_PHOTO_FILENAME);
			if (filename != null) {
				Log.i(TAG, "filename: " + filename);
			}
		}
	}

	static final int REQUEST_IMAGE_CAPTURE = 1;
	static boolean IMAGE_TAKEN = false;

}
