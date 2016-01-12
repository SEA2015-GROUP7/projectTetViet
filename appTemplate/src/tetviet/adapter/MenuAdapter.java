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

import tetviet.utils.MainItem;

public class MenuAdapter extends BaseAdapter {
	Context mContext;
	List<MainItem> mAppList;

	public MenuAdapter(Context context, List<MainItem> mApps) {
		this.mContext = context;
		this.mAppList = mApps;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		View gridView = convertView;
		final MainItem itemData = mAppList.get(position);
		
		if (itemData != null) {
			ItemGridHolder holder = null;
			if (gridView == null) {
				LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
				gridView = inflater.inflate(R.layout.item_menu, parent, false);

				holder = new ItemGridHolder();
				holder.imgThumb = (ImageView) gridView.findViewById(R.id.imgThumb);
                holder.tv1 = (TextView) gridView.findViewById(R.id.tv1);
				gridView.setTag(holder);
			} else {
				holder = (ItemGridHolder) gridView.getTag();
			}

            holder.tv1.setText(itemData.MenuName);
            holder.imgThumb.setImageResource(itemData.MenuIcon);
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
        TextView tv1;
		ImageView imgThumb;

	}
}
