package tetviet.activity;

import java.util.List;

import tetviet.adapter.SMSAdapter;
import tetviet.utils.DataBaseManager;
import tetviet.utils.SMSItem;

import com.sea.tetviet.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.GridView;

public class VerticalListSms extends Activity {
	List<SMSItem> listSMS;
	DataBaseManager dbm;
	SMSAdapter adapter;
	Context mContext;
	GridView grid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vertical_list_sms);
		mContext = this;
		dbm = DataBaseManager.instance();
		
		listSMS = dbm.select_SMS_LIST(1);
		adapter = new SMSAdapter(mContext, listSMS);
		grid = (GridView) findViewById(R.id.grid);
		grid.setAdapter(adapter);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_vertical_list_sms, menu);
		return true;
	}

}
