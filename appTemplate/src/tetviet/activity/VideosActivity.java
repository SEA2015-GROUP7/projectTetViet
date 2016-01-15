package tetviet.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.sea.tetviet.R;
import com.thefinestartist.ytpa.YouTubePlayerActivity;
import com.thefinestartist.ytpa.enums.Orientation;
import com.thefinestartist.ytpa.utils.YouTubeApp;
import com.thefinestartist.ytpa.utils.YouTubeUrlParser;

import java.util.LinkedList;
import java.util.List;

import tetviet.adapter.VideoAdapter;
import tetviet.utils.LoggerFactory;

public class VideosActivity extends Activity
{
    Context mContext;
    public static String tableName = "Videos";
    List<ParseObject> eventList = new LinkedList<ParseObject>();
    VideoAdapter eventAdapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_video);
        mContext = this;

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openYoutubeWebview(eventList.get(i).getString("VideoUrl"));
            }
        });

        initTopBar();

        loadParseData();
    }

    private void initTopBar(){
        findViewById(R.id.btnBarBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void openYoutubePlayer(String youUrl){
        Intent intent = new Intent(VideosActivity.this, YouTubePlayerActivity.class);

// Youtube video ID (Required, You can use YouTubeUrlParser to parse Video Id from url)
        intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID, "iS1g8G_njx8");

// Youtube player style (DEFAULT as default)
        intent.putExtra(YouTubePlayerActivity.EXTRA_PLAYER_STYLE, YouTubePlayer.PlayerStyle.DEFAULT);

// Screen Orientation Setting (AUTO for default)
// AUTO, AUTO_START_WITH_LANDSCAPE, ONLY_LANDSCAPE, ONLY_PORTRAIT
        intent.putExtra(YouTubePlayerActivity.EXTRA_ORIENTATION, Orientation.AUTO);

// Show audio interface when user adjust volume (true for default)
        intent.putExtra(YouTubePlayerActivity.EXTRA_SHOW_AUDIO_UI, true);

// If the video is not playable, use Youtube app or Internet Browser to play it
// (true for default)
        intent.putExtra(YouTubePlayerActivity.EXTRA_HANDLE_ERROR, true);

// Animation when closing youtubeplayeractivity (none for default)
//        intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_ENTER, R.anim.fade_in);
//        intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_EXIT, R.anim.fade_out);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void openYoutubeWebview(String youUrl){
        String videoId = YouTubeUrlParser.getVideoId(youUrl);
        YouTubeApp.startVideo(mContext, videoId);
    }


    private void loadParseData(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				// TODO Auto-generated method stub
				if (arg1 == null) {
                    if(arg0.size() > 0){
                        eventList.addAll(arg0);
//                        if(eventList != null && eventList.size() > 0){
                        	eventAdapter = new VideoAdapter(mContext, eventList);
                        	gridView.setAdapter(eventAdapter);
//                        }else{
//                        	
//                        }
                    }else{
                        Toast.makeText(mContext,"Danh sách trống", Toast.LENGTH_SHORT).show();
                    }
                } else {
                	arg1.printStackTrace();
                    LoggerFactory.e("loadParseData", "Error: " + arg1.getMessage());
                }
			}
		});
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> productParseList, com.parse.ParseException e) {
//                if (e == null) {
//                    if(productParseList.size() > 0){
//                        eventList.addAll(productParseList);
////                        if(eventList != null && eventList.size() > 0){
//                        	eventAdapter = new VideoAdapter(mContext, eventList);
//                        	gridView.setAdapter(eventAdapter);
////                        }else{
////                        	
////                        }
//                    }else{
//                        Toast.makeText(mContext,"Danh sách trống", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    e.printStackTrace();
//                    LoggerFactory.e("loadParseData", "Error: " + e.getMessage());
//                }
//            }
//        });

    }
}