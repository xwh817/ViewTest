package xwh.demo.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by xwh on 2018/7/5.
 */
public class MyViewGroup extends ViewGroup {

	private TextView[] mItems;
	private int mColorResNormal = Color.parseColor("#999999");
	private int mColorResSelect = Color.GREEN;
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
		initView();
	}

	private void initView() {

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
				TextView textView = getItemView(mTitles[i]);
				textView.setTag(i);
				textView.setOnClickListener(onClickListener);
				mItems[i] = textView;

				ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mItemWidth, mItemHeight);
				this.addView(textView, params);
			}

		}

	}

	private TextView getItemView(String title) {
		TextView textView = new TextView(getContext());
		textView.setText(title);
		textView.setTextSize(18);
		textView.setTextColor(Color.WHITE);
		textView.setBackgroundColor(mColorResNormal);
		textView.setGravity(Gravity.CENTER);
		return textView;
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


	//private int totalWidth = 0;
	//private int totalHeight = 0;

	/**
	 * 用来计算自己的宽高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

		// 计算出所有的childView的宽和高
		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int totalWidth = 0;
		int totalHeight = 0;
		// 根据子View计算出ViewGroup的宽高
		for (int i = 0; i < mItems.length; i++) {
			View childView = mItems[i];

			totalWidth += childView.getMeasuredWidth();

			int childHeight = childView.getMeasuredHeight();
			if (childHeight > totalHeight) {    // 取最高的为父容器的高度
				totalHeight = childHeight;
			}
		}


		int mWidth;
		int mHeight;

		if (widthMode == MeasureSpec.EXACTLY) { // 当在布局中指定了宽度为match_parent或是固定值时，不用计算了，直接使用指定的值。
			mWidth = sizeWidth;
		} else {
			mWidth = totalWidth + (getChildCount() + 1) * mBorderWidth;
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			mHeight = sizeHeight;
		} else {
			mHeight = totalHeight + mBorderWidth * 2;
		}

		// 设置自己的大小
		setMeasuredDimension(mWidth, mHeight);

	}

	/**
	 * 指定子View的位置
	 * 参数是当前View在其父控件中的位置（由父控件指派的）
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childLeft = 0;
		int childTop = mBorderWidth;
		for (int i = 0; i < mItems.length; i++) {
			TextView child = mItems[i];
			childLeft += mBorderWidth;
			int childWidth = child.getMeasuredWidth();
			int childRight = childLeft + childWidth;
			int childBottom = childTop + child.getMeasuredHeight();
			child.layout(childLeft, childTop, childRight, childBottom); // 指定子View位置
			childLeft += childWidth;
		}
	}

}

