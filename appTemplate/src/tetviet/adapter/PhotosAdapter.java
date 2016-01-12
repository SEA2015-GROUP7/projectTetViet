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

import java.util.List;

import tetviet.utils.LoggerFactory;

public class PhotosAdapter extends BaseAdapter {
	Context mContext;
	List<ParseObject> mAppList;

	public PhotosAdapter(Context context, List<ParseObject> mApps) {
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
				gridView = inflater.inflate(R.layout.item_photo, parent, false);

				holder = new ItemGridHolder();
				holder.imgThumb = (ImageView) gridView.findViewById(R.id.imgThumb);
                holder.tv1 = (TextView) gridView.findViewById(R.id.tv1);
                holder.tv2 = (TextView) gridView.findViewById(R.id.tv2);
				gridView.setTag(holder);
			} else {
				holder = (ItemGridHolder) gridView.getTag();
			}

            holder.tv1.setText(itemData.getString("PhotoDescription"));
            holder.tv2.setText(itemData.getString("PhotoDate"));


            Picasso.with(mContext).load(itemData.getString("PhotoUrl")).into(holder.imgThumb);
            LoggerFactory.d("load photo" + itemData.getString("PhotoUrl"));
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
        TextView tv2;
        TextView tv1;
		ImageView imgThumb;

	}
}
