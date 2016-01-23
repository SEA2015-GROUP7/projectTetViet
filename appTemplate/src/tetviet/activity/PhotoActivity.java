package tetviet.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.sea.tetviet.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import tetviet.adapter.PhotosAdapter;
import tetviet.*;
import tetviet.utils.LoggerFactory;

public class PhotoActivity extends Activity
{
    Context mContext;
    public static String EventTable = "Photos";
    List<ParseObject> eventList = new LinkedList<ParseObject>();
    PhotosAdapter eventAdapter;
    GridView gridView;
    String albumId = "1";
    File root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_photo);
        mContext = this;
        root = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "folder_name" + File.separator);
		root.mkdirs();

         loadParseData(albumId);

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				showPhoto(eventList.get(arg2).getString("PhotoUrl"));
			}
		});
        initTopBar();
    }

    private void initTopBar(){
        findViewById(R.id.btnBarBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }



    private void loadParseData(String Id){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(EventTable);
        query.whereEqualTo("PhotoAlbumId", Id);
        query.setLimit(50);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> productParseList, com.parse.ParseException e) {
                if (e == null) {
                    if(productParseList.size() > 0){
                        eventList.addAll(productParseList);
                        eventAdapter = new PhotosAdapter(mContext, eventList);
                        gridView.setAdapter(eventAdapter);
                    }else{
                        Toast.makeText(mContext,"Danh sách trống", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    e.printStackTrace();
                    LoggerFactory.e("loadParseData", "Error: " + e.getMessage());
                }
            }
        });
    }
    
    private void showPhoto(final String photoUrl){
    	final Dialog dialog = new Dialog(mContext);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_photo);

		final ImageView imgThumb = (ImageView) dialog.findViewById(R.id.imgThumb);
		Target saveFileTarget = new Target() {
		    @Override
		    public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
		        new Thread(new Runnable() {
		            @Override
		            public void run() {
		            	File file = new File(root, "myPicName.jpg");
//		                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + FILEPATH);
		                try {
		                    file.createNewFile();
		                    FileOutputStream ostream = new FileOutputStream(file);
		                    bitmap.compress(CompressFormat.JPEG, 100, ostream);
		                    shareImage = Uri.fromFile(file);
		                    ostream.close();
		                } catch (Exception e) {
		                    e.printStackTrace();
		                }
		            }
		        }).start();
		    }

			@Override
			public void onBitmapFailed(Drawable arg0) {
			}

			@Override
			public void onPrepareLoad(Drawable arg0) {
			}
		};
		Picasso.with(mContext).load(photoUrl).networkPolicy(NetworkPolicy.OFFLINE).into(imgThumb, new Callback() {
		    @Override
		    public void onSuccess() {

		    }

		    @Override
		    public void onError() {
		        //Try again online if cache failed
		        Picasso.with(mContext)
		                .load(photoUrl)
		                .into(imgThumb, new Callback() {
		            @Override
		            public void onSuccess() {

		            }

		            @Override
		            public void onError() {
		                LoggerFactory.d("Picasso","Could not fetch image");
		            }
		        });
		    }
		});
		Picasso.with(mContext).load(photoUrl).networkPolicy(NetworkPolicy.OFFLINE).into(saveFileTarget);
//		shareImage = saveBitmap(imgThumb);
		dialog.findViewById(R.id.buttonWallpaper)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						setWallpaper(shareImage);
					}
				});
		dialog.findViewById(R.id.buttonShare)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = createShareIntent(shareImage);
				mContext.startActivity(intent);
			}
		});
		
		dialog.findViewById(R.id.buttonClose)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
    }
    Uri shareImage;
    
	private void setWallpaper(Uri imageUri) {
//		Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
//		intent.setType("image/*");
//		intent.setData(imageUri);
//
//		mContext.startActivity(Intent.createChooser(intent, "Set as"));
//		
		
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		Bitmap bitmap = BitmapFactory.decodeFile(shareImage.getPath(),bmOptions);
//		bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);

		 WallpaperManager myWallpaperManager 
	        = WallpaperManager.getInstance(getApplicationContext());
	        try {
	            myWallpaperManager.setBitmap(bitmap);
	            Toast.makeText(mContext, "Cai anh nen thanh cong", Toast.LENGTH_LONG).show();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}

	private Intent createShareIntent(Uri imageUri) {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/*");
		shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
		return shareIntent;
	}
}