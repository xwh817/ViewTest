package xwh.demo.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class EventActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parent parent = new Parent(this, "parent");
		parent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		setContentView(parent);

		Parent child1 = new Parent(this, "child_1");
		child1.setLayoutParams(new ViewGroup.LayoutParams(800, 800));
		child1.setBackgroundResource(R.color.colorPrimary);

		Child child2 = new Child(this, "child_2");
		child2.setLayoutParams(new ViewGroup.LayoutParams(400, 400));
		child2.setBackgroundColor(Color.RED);
		child2.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;        // 把时间拦下来了
			}
		});

		child1.addView(child2);
		parent.addView(child1);

	}
	class Parent extends LinearLayout {
		String name;
		public Parent(Context context, String name) {
			super(context);
			this.name = name;
		}
		private void log(String log) {
			Log.d("EventTest", this.name + " : " + log);
		}

		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			log("dispatchTouchEvent");
			return super.dispatchTouchEvent(ev);
		}

		@Override
		public boolean onInterceptTouchEvent(MotionEvent ev) {
			log("onInterceptTouchEvent");
			return super.onInterceptTouchEvent(ev);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			log("onTouchEvent");
			return super.onTouchEvent(event);
		}
	}

	class Child extends View {
		String name;
		public Child(Context context, String name) {
			super(context);
			this.name = name;
		}
		private void log(String log) {
			Log.d("EventTest", this.name + " : " + log);
		}

		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			log("dispatchTouchEvent");
			return super.dispatchTouchEvent(ev);
		}

		/*@Override
		public boolean onInterceptTouchEvent(MotionEvent ev) {
			log("onInterceptTouchEvent");
			return super.onInterceptTouchEvent(ev);
		}
*/
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			log("onTouchEvent");
			return super.onTouchEvent(event);
		}
	}

}
