package com.egceo.app.myfarm.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;

/**
 * Created by FreeMason on 2016/2/18.
 */
public class FavouriteBtn extends ImageView implements Checkable{
    private boolean mChecked = false;
    private Drawable mButtonDrawable;
    private OnCheckedChangeListener onCheckedChangeListener;
    public FavouriteBtn(Context context) {
        super(context);
        init(context);
    }

    public FavouriteBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FavouriteBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mButtonDrawable = this.getDrawable();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    @Override
    public void setChecked(boolean b) {
        if(onCheckedChangeListener != null)
            onCheckedChangeListener.onCheckedChanged(this);
        mChecked = b;
        refreshDrawableState();
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if(mChecked)
            mergeDrawableStates(drawableState, new int[]{android.R.attr.state_checked});
        return drawableState;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mButtonDrawable != null) {
            int[] myDrawableState = getDrawableState();
            // Set the state of the Drawable
            mButtonDrawable.setState(myDrawableState);
            invalidate();
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    public interface OnCheckedChangeListener{
        void onCheckedChanged(View view);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }
}
