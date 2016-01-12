package tetviet.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.sea.tetviet.R;

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
    String albumId = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_photo);
        mContext = this;

        Intent passData = getIntent();
        if(passData != null){
            albumId = passData.getStringExtra("ID");
            loadParseData(albumId);
        }

        gridView = (GridView) findViewById(R.id.gridView);

        initTopBar();


    }

    private void initTopBar(){
        findViewById(R.id.btnBarBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        TextView title = (TextView)findViewById(R.id.tvTitle);
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
}