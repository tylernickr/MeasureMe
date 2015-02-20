package com.decSports.measureme;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DotDrawView extends View {
	public static final String TAG = "DotDrawView";
	public static final float dotRadius = 5;
	
	private PointF mCurrentPoint;
	private ArrayList<PointF> mPoints = new ArrayList<PointF>();
	private Paint mPointPaint;
	
	
	public DotDrawView(Context context) {
		this(context, null);
	}
	
	public DotDrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mPointPaint = new Paint();
		mPointPaint.setColor(0xffff6347);
	}
	
	public PointF getCurrentPoint() {
		return mCurrentPoint;
	}
	
	public void setCurrentPoint(PointF newPoint) {
		mCurrentPoint = newPoint;
		invalidate();
	}
	
	public void setPoints(ArrayList<PointF> points) {
		mPoints = points;
	}
	
	public ArrayList<PointF> getPoints() {
		return mPoints;
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		PointF curr = new PointF(event.getX(), event.getY());
		
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.i(TAG, "Touched Screen");
				mCurrentPoint = curr;
				break;
			
			case MotionEvent.ACTION_MOVE:
				if (mCurrentPoint != null) {
					mCurrentPoint = curr;
					invalidate();
				}
				break;
				
			case MotionEvent.ACTION_UP:
				break;
				
			case MotionEvent.ACTION_CANCEL:
				break;
		}
		
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (mCurrentPoint != null) {
			float canvasHeight = canvas.getHeight();
			float canvasWidth = canvas.getWidth();
			float xPos = mCurrentPoint.x;
			float yPos = mCurrentPoint.y;
			canvas.drawLine(xPos, 0, xPos, canvasHeight, mPointPaint);
			canvas.drawLine(0, yPos, canvasWidth, yPos, mPointPaint);
			
			
			for (PointF point : mPoints) {
				xPos = point.x;
				yPos = point.y;
				canvas.drawCircle(xPos, yPos, dotRadius, mPointPaint);
			}
		}
	}

}
