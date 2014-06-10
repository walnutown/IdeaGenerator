package com.taohu.ideagenerator.views;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ViewFlipper;

public class SlotViewFlipper extends ViewFlipper implements OnGestureListener {
	String[] values;
	private int mDuration;
	private int mDelt;
	private int mCount;
	private boolean isRolling;
	private Runnable rollUp;
	private Runnable rollDown;
	private Context context;
	private GestureDetector mDetector;

	public SlotViewFlipper(Context context) {
		super(context);
		this.context = context;
	}

	public SlotViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void init(String[] values) {
		mDuration = 0;
		mDelt = 10;
		mCount = 10;
		isRolling = false;
		mDetector = new GestureDetector(this);
		this.values = values;
		for (String value : values)
			addView(new SlotView(context, value));
		rollUp = new Runnable() {
			@Override
			public void run() {
				up();
				if (mCount < 1) {
					isRolling = false;
					return;
				}
				new Handler().postDelayed(rollUp, mDuration);
			}
		};
		rollDown = new Runnable() {
			@Override
			public void run() {
				down();
				Log.d("Touch", "count: " + mCount);
				if (mCount < 1) {
					isRolling = false;
					return;
				}
				new Handler().postDelayed(rollDown, mDuration);
			}
		};
	}

	@Override
	public boolean onTouchEvent(MotionEvent me) {
		return mDetector.onTouchEvent(me);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (isRolling)
			return true;
		isRolling = true;
		Log.d("Touch", "onFling()");
		float maxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity() / 2;
		float normalizedVelocityY = velocityY / maxVelocity;
		Log.d("Touch", "velocityY " + velocityY + "maxValocity " + maxVelocity + "normalizedVelocity "
				+ normalizedVelocityY);
		mDuration = 0;
		mCount = (int) Math.abs(normalizedVelocityY * 10);
		if (mCount==0)
			mCount=1;
		mDelt = 600 / mCount;
		if (velocityY > 0) {
			rollDown.run();
		} else {
			rollUp.run();
		}
		return true;
	}

	private void up() {
		Log.d("Touch", "up() " + getDisplayedChild());
		mCount--;
		mDuration += mDelt;
		Animation inFromBottom = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT,
				0.0f);
		inFromBottom.setInterpolator(new AccelerateInterpolator());
		inFromBottom.setDuration(mDuration);
		Animation outToTop = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
				0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f);
		outToTop.setInterpolator(new AccelerateInterpolator());
		outToTop.setDuration(mDuration);
		clearAnimation();
		setInAnimation(inFromBottom);
		setOutAnimation(outToTop);
		if (getDisplayedChild() == 0)
			setDisplayedChild(values.length - 1);
		else
			showPrevious();
	}

	private void down() {
		Log.d("Touch", "down() " + getDisplayedChild());
		mCount--;
		mDuration += mDelt;
		Animation outToBottom = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
				1.0f);
		outToBottom.setInterpolator(new AccelerateInterpolator());
		outToBottom.setDuration(mDuration);
		Animation inFromTop = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
				0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromTop.setInterpolator(new AccelerateInterpolator());
		inFromTop.setDuration(mDuration);
		clearAnimation();
		setInAnimation(inFromTop);
		setOutAnimation(outToBottom);
		if (getDisplayedChild() == values.length - 1)
			setDisplayedChild(0);
		else
			showNext();
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Log.d("Touch", "onSingleTabUp()");
		Log.d("Touch", "isRolling " + isRolling);
		if (isRolling){
			if (isFlipping()){
				stopFlipping();
				isRolling = false;
				return true;
			}
			return true;
		}
		isRolling = true;
		clearAnimation();
		setInAnimation(null);
		setOutAnimation(null);
		setFlipInterval(100);
		startFlipping();
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.d("Touch", "onDown()");
		return true;
	}

}
