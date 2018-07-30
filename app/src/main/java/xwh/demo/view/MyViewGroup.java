package xwh.demo.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by xwh on 2018/7/5.
 */
public class MyViewGroup extends LinearLayout {

	private TextView[] mItems;
	private int mColorResNormal = Color.parseColor("#999999");
	private int mColorResSelect = Color.parseColor("#cccccc");
	private int mCurrentIndex = -1;
	private int mBorderWidth = 6;
	private int mItemWidth = 200;
	private int mItemHeight = 120;

	public MyViewGroup(Context context) {
		this(context, null);
	}

	public MyViewGroup(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);


		OnClickListener onClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				int index = (Integer) v.getTag();
				selectItem(index);
				if (mItemOnClickListener != null) {
					mItemOnClickListener.onItemClick(index);
				}
			}
		};

		String titles = "低|中|高";
		if (!TextUtils.isEmpty(titles)) {
			String[] mTitles = titles.split("\\|");

			mItems = new TextView[mTitles.length];
			for (int i = 0; i < mTitles.length; i++) {
				TextView textView = new TextView(getContext());
				textView.setText(mTitles[i]);

				textView.setBackgroundColor(mColorResNormal);
				textView.setTextSize(18);
				textView.setGravity(Gravity.CENTER);

				textView.setTag(i);
				textView.setOnClickListener(onClickListener);
				mItems[i] = textView;

				ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mItemWidth, mItemHeight);
				this.addView(textView, params);
			}

		}

	}

	public void selectItem(int index) {
		if (mItems == null || index < 0 || index > mItems.length) {
			return;
		}

		if (mCurrentIndex != -1) {
			mItems[mCurrentIndex].setBackgroundColor(mColorResNormal);
		}

		TextView item = mItems[index];
		item.setBackgroundColor(mColorResSelect);

		item.bringToFront();
		mCurrentIndex = index;
	}

	private ItemOnClickListener mItemOnClickListener;

	public void setItemOnClickListener(ItemOnClickListener onClickListener) {
		this.mItemOnClickListener = onClickListener;
	}

	public interface ItemOnClickListener {
		void onItemClick(int index);
	}


	private int totalWidth = 0;
	private int totalHeight = 0;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// 测量子View
		/*if (!isMeasured) {      // 没有动态添加的情况，这儿只测量一次
			isMeasured = true;
			measureChildren(widthMeasureSpec, heightMeasureSpec);

			for (int i = 0; i < mItems.length; i++) {
				View childView = mItems[i];

				totalWidth += childView.getMeasuredWidth();

				int childHeight = childView.getMeasuredHeight();
				if (childHeight > totalHeight) {
					totalHeight = childHeight;
				}
			}
		}

		measureChildren(widthMeasureSpec, heightMeasureSpec);
*/
		totalWidth = mItemWidth * getChildCount() - mBorderWidth * (getChildCount() - 1);
		totalHeight = mItemHeight;
		//setMeasuredDimension(totalWidth, totalHeight);

		super.onMeasure(totalWidth, totalHeight);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int totalLeft = 0;
		for (int i = 0; i < mItems.length; i++) {   // bringToFront会改变childView的位置，这儿固定初始的位置
			TextView child = mItems[i];
			if (i > 0) {
				totalLeft -= mBorderWidth;
			}
			child.layout(totalLeft, 0, totalLeft + mItemWidth, mItemHeight);
			totalLeft += mItemWidth;
		}

		//super.onLayout(changed, l, t, r, b);
	}

}

