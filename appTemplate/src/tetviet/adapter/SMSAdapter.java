package tetviet.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.sea.tetviet.R;
import com.squareup.picasso.Picasso;
import com.thefinestartist.ytpa.enums.Quality;
import com.thefinestartist.ytpa.utils.YouTubeThumbnail;
import com.thefinestartist.ytpa.utils.YouTubeUrlParser;








import java.util.List;

import tetviet.utils.MainItem;
import tetviet.utils.SMSItem;

public class SMSAdapter extends BaseAdapter {
	Context mContext;
	List<SMSItem> mAppList;

	public SMSAdapter(Context context, List<SMSItem> mApps) {
		this.mContext = context;
		this.mAppList = mApps;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		View gridView = convertView;
		final SMSItem itemData = mAppList.get(position);
		
		if (itemData != null) {
			ItemGridHolder holder = null;
			if (gridView == null) {
				LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
				gridView = inflater.inflate(R.layout.item_sms_ver, parent, false);

				holder = new ItemGridHolder();
//				holder.imgThumb = (ImageView) gridView.findViewById(R.id.imgThumb);
                holder.tv_SMSContent = (TextView) gridView.findViewById(R.id.tv_SMSContent);
                holder.buttonShare = (Button) gridView.findViewById(R.id.buttonShare);
                holder.buttonSendSMS = (Button) gridView.findViewById(R.id.buttonSendSMS);
                holder.buttonSendMail = (Button) gridView.findViewById(R.id.buttonSendMail);
				gridView.setTag(holder);
			} else {
				holder = (ItemGridHolder) gridView.getTag();
			}

            holder.tv_SMSContent.setText(itemData.Message);
//            holder.imgThumb.setImageResource(itemData.MenuIcon);
            holder.buttonShare.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					sendText(itemData.Message);
				}
			});
            holder.buttonSendSMS.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					sendSMS(itemData.Message);
				}
			});
            holder.buttonSendMail.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					sendMail(itemData.Message);
				}
			});
            
		}

		return gridView;
	}

	public int getCount() {
		return mAppList.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}
	
	public void sendSMS(String text) {
//		MyLog.d("LoveSMS", text);
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.putExtra("sms_body", text);
		smsIntent.setType("vnd.android-dir/mms-sms");
		mContext.startActivity(smsIntent);
	}

	public void sendMail(String content) {
		Intent i = new Intent(Intent.ACTION_SENDTO);
		i.setType("message/rfc822");
		i.setData(Uri.parse("mailto:default@recipient.com"));
		i.putExtra(Intent.EXTRA_SUBJECT, " ");
		i.putExtra(Intent.EXTRA_TEXT, content);
		try {
			mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
//			Log.e("", ex.toString());
		}
	}

	public void sendText(String type) {

		// 1: sms. 2: email. 3: social
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, type);
		sendIntent.setType("text/plain");
		mContext.startActivity(sendIntent);
	}

	static class ItemGridHolder {
        TextView tv_SMSContent;
		ImageView imgThumb;
		Button buttonShare;
		Button buttonSendSMS;
		Button buttonSendMail;
	}
}
