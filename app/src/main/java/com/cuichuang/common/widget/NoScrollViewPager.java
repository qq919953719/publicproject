package com.cuichuang.common.widget;

import android.content.Context;
import android.graphics.PointF;

import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class NoScrollViewPager extends ViewPager {
    private boolean noScroll = false;
    private PointF mDownP = new PointF();

    private PointF mCurrentP = new PointF();

    OnItemTouchListener mOnItemTouchListener;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if(noScroll){
            return false;
        }
        mCurrentP.x = arg0.getX();
        mCurrentP.y = arg0.getY();
        if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
            mDownP.x = arg0.getX();
            mDownP.y = arg0.getY();
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if (arg0.getAction() == MotionEvent.ACTION_MOVE) {
            float aMoveX = mDownP.x - mCurrentP.x;
            float aMoveY = mDownP.y - mCurrentP.y;
            if (Math.abs(aMoveX) > Math.abs(aMoveY * 2)) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }else {
                getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        if (arg0.getAction() == MotionEvent.ACTION_UP) {
            if (Math.abs(mDownP.x - mCurrentP.x) < 5 && Math.abs(mDownP.y - mCurrentP.y) < 5) {
                onItemTouch();
                return true;
            }
        }
        return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

    public void onItemTouch() {
        if (mOnItemTouchListener != null) {
            mOnItemTouchListener.onItemTouch(getCurrentItem());
        }
    }
    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    public interface OnItemTouchListener {
        public void onItemTouch(int position);
    }


    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListener = onItemTouchListener;
    }

}
