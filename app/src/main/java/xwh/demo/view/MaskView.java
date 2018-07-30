package xwh.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/11/7.
 */

public class MaskView extends View {
	public MaskView(Context context) {
		super(context);
	}

	public MaskView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MaskView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
