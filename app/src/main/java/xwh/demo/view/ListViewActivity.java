package xwh.demo.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewActivity extends Activity {

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);

		mListView = (ListView) this.findViewById(R.id.listView);

		final ArrayList<String> strings = new ArrayList<String>();
		for(int i=0; i<30; i++) {
			strings.add("" +(i+1));
		}

		mListView.setAdapter(new BaseAdapter() {
			@Override
			public int getCount() {
				return strings.size();
			}

			@Override
			public Object getItem(int position) {
				return strings.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = ListViewActivity.this.getLayoutInflater().inflate(R.layout.item_list, null);
				}

				final Data data = new Data(strings.get(position));

				TextView textView = (TextView) convertView.findViewById(R.id.text);
				textView.setText(data.str);

				convertView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(ListViewActivity.this, data.str, Toast.LENGTH_SHORT).show();
					}
				});

				return convertView;
			}
		});
	}


	class Data {
		String str;

		public Data(String str) {
			this.str = str;
		}
	}

}
