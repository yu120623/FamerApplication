package com.baseandroid.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baseandroid.R;

public class BottomButton extends RelativeLayout {
	private Drawable imgDrawable;
	private ColorStateList textgDrawable;
	private View buttonLayout;
	private ImageView buttonImg;
	private TextView buttonTextView;
	private String text;
	public BottomButton(Context context) {
		super(context);
		init(null);
	}
	
	public BottomButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs);
	}
	
	public BottomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}
	
	public void init(AttributeSet attrs){
		buttonLayout = inflate(getContext(), R.layout.bottom_button_item, null);
		buttonImg = (ImageView) buttonLayout.findViewById(R.id.button_img);
		buttonTextView = (TextView) buttonLayout.findViewById(R.id.button_text);
		if (attrs != null) {
			TypedArray styled = getContext().obtainStyledAttributes(attrs, R.styleable.BottomButton);
			imgDrawable = styled.getDrawable(R.styleable.BottomButton_buttonImg);
			textgDrawable = styled.getColorStateList(R.styleable.BottomButton_buttonTextColor);
			text = styled.getString(R.styleable.BottomButton_buttonText);
			styled.recycle();
		}
		buttonImg.setImageDrawable(imgDrawable);		
		buttonTextView.setTextColor(textgDrawable);
		buttonTextView.setText(text);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		addView(buttonLayout, params);
	}
	
	public void setChecked(boolean flag){
		buttonImg.setSelected(flag);
		buttonTextView.setSelected(flag);
	}
	
	
}
