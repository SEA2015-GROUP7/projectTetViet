package tetviet.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.sea.tetviet.R;
import com.squareup.picasso.Picasso;
import com.thefinestartist.ytpa.enums.Quality;
import com.thefinestartist.ytpa.utils.YouTubeThumbnail;
import com.thefinestartist.ytpa.utils.YouTubeUrlParser;


import java.util.List;

public class VideoAdapter extends BaseAdapter {
	Context mContext;
	List<ParseObject> mAppList;

	public VideoAdapter(Context context, List<ParseObject> mApps) {
		this.mContext = context;
		this.mAppList = mApps;
	}
	// update content to view
	public View getView(final int position, View convertView, ViewGroup parent) {
		View gridView = convertView;
		final ParseObject itemData = mAppList.get(position);
		
		if (itemData != null) {
			ItemGridHolder holder = null;
			if (gridView == null) {
				LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
				gridView = inflater.inflate(R.layout.item_video, parent, false);

				holder = new ItemGridHolder();
				holder.imgThumb = (ImageView) gridView.findViewById(R.id.imgThumb);
                holder.tv1 = (TextView) gridView.findViewById(R.id.tv1);
                holder.tv2 = (TextView) gridView.findViewById(R.id.tv2);
                holder.tv3 = (TextView) gridView.findViewById(R.id.tv3);
                holder.tv4 = (TextView) gridView.findViewById(R.id.tv4);
				gridView.setTag(holder);
			} else {
				holder = (ItemGridHolder) gridView.getTag();
			}

            holder.tv1.setText(itemData.getString("VideoName"));
            holder.tv2.setText("View: " +itemData.getString("VideoViews"));
            holder.tv3.setText("Like: " + itemData.getString("VideoLike"));

            String videoId = YouTubeUrlParser.getVideoId(itemData.getString("VideoUrl"));

            String videoThumbUrl = YouTubeThumbnail.getUrlFromVideoId(videoId, Quality.HIGH);
            Picasso.with(mContext).load(videoThumbUrl).into(holder.imgThumb);
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

	static class ItemGridHolder {
//		TextView tv5;
		TextView tv4;
        TextView tv3;
        TextView tv2;
        TextView tv1;
		ImageView imgThumb;

	}
}
