package com.decSports.measureme;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MeasureCalcFragment extends Fragment {
	public static final String EXTRA_PHOTO_FILENAME =
			"com.decsports.measureme.photo_filename";
	public static final float dotMoveBy = 2;
	public static final String TAG = "MeasureCalcFragment";
	
	private String mFilename;
	private ImageView mPhotoView;
	private DotDrawView mDotDrawView;
	
	private Button mLeftButton;
	private Button mRightButton;
	private Button mUpButton;
	private Button mDownButton;
	private Button mDoneButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_measure_calc, parent, false);
		mPhotoView = (ImageView)v.findViewById(R.id.measure_calc_imageView);
		Intent i = getActivity().getIntent();
		mFilename = (String)i.getSerializableExtra(EXTRA_PHOTO_FILENAME);
		mDotDrawView = (DotDrawView)v.findViewById(R.id.measure_calc_dotDrawView);
		
		mLeftButton = (Button)v.findViewById(R.id.left_button);
		mLeftButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				PointF curPoint = mDotDrawView.getCurrentPoint();
				if (curPoint != null) {
					mDotDrawView.setCurrentPoint(new PointF(curPoint.x - dotMoveBy, curPoint.y));
				}
			}
		});
		
		
		mRightButton = (Button)v.findViewById(R.id.right_button);
		mRightButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				PointF curPoint = mDotDrawView.getCurrentPoint();
				if (curPoint != null) {
					mDotDrawView.setCurrentPoint(new PointF(curPoint.x + dotMoveBy, curPoint.y));
				}
			}
		});
		
		mUpButton = (Button)v.findViewById(R.id.up_button);
		mUpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				PointF curPoint = mDotDrawView.getCurrentPoint();
				if (curPoint != null) {
					mDotDrawView.setCurrentPoint(new PointF(curPoint.x, curPoint.y - dotMoveBy));
				}
			}
		});
		
		mDownButton = (Button)v.findViewById(R.id.down_button);
		mDownButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				PointF curPoint = mDotDrawView.getCurrentPoint();
				if (curPoint != null) {
					mDotDrawView.setCurrentPoint(new PointF(curPoint.x, curPoint.y + dotMoveBy));
				}
			}
		});
		
		mDoneButton = (Button)v.findViewById(R.id.done_button);
		mDoneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PointF curPoint = mDotDrawView.getCurrentPoint();
				ArrayList<PointF> storedPoints = mDotDrawView.getPoints();
				if (curPoint != null) {
					if (storedPoints.size() < 3) {
						storedPoints.add(curPoint);
						mDotDrawView.setPoints(storedPoints);
						mDotDrawView.setCurrentPoint(null);
					} else {
						storedPoints.add(curPoint);
						Float result = calculateHeight(storedPoints);
						double meters = (double)Math.round(result * 12 * 2.5) / 100; // TODO Not Correct Yet
						Integer feet = Math.round(result - (result % 1));
						Integer inches = Math.round((result - feet) * 12);
						String message = feet.toString() + "' " + inches.toString() + "\" -- " + meters + "m";
						Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
					}
				}
			}
		});
		
		return v;
		
	}
	
	private void showPhoto() {
		Photo p = new Photo(mFilename);
		BitmapDrawable b = null;
		if (p != null) {
			String path = getActivity()
					.getFileStreamPath(p.getFilename()).getAbsolutePath();
			b = PictureUtils.getScaledDrawable(getActivity(), path);
		}
		mPhotoView.setImageDrawable(b);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		showPhoto();
	}
	
	private float calculateHeight(ArrayList<PointF> arr) {
		float dist1 = distanceFrom(arr.get(0), arr.get(1));
		float dist2 = distanceFrom(arr.get(2), arr.get(3));
		float oneFoot = dist1;
		float raisedBy = dist2 / oneFoot;
		return 12 + (5/48) + raisedBy;
	}
	
	private float distanceFrom(PointF one, PointF two) {
		float xDist = two.x - one.x;
		float yDist = two.y - one.y;
		Double cSqr = Math.pow(xDist, 2) + Math.pow(yDist, 2);
		return (float)Math.sqrt(cSqr);
	}
	
}
