package tetviet.activity;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import tetviet.adapter.PhotosAdapter;
import tetviet.utils.DataBaseManager;
import tetviet.utils.MSharedPreferences;
import tetviet.utils.MainItem;

import org.json.JSONObject;

import com.sea.tetviet.R;

public final class MainActivity extends Activity {
    private MSharedPreferences mSharedPreferences;
    public static Context mContext;
    private List<MainItem> listMenu;
    PhotosAdapter eventAdapter;
    GridView gridView;
    public static DataBaseManager mDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mSharedPreferences = MSharedPreferences.getInstance(mContext);
        mDB = DataBaseManager.instance();
        initView();
        

        setStatusBarColor();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    private void initView(){
    	gridView = (GridView) findViewById(R.id.grid);
    }

    private void setStatusBarColor(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(getResources().getColor(R.color.blue_network_bar_bg));
//        }
    }
    public static int MENU_MUSIC = 1;
    public static int MENU_PHOTO = 2;
    public static int MENU_VIDEO = 3;
    public static int MENU_SMS = 4;
    public static int MENU_SHARE = 5;
    private void initMenu(){
    	if(listMenu == null){
    		listMenu = new LinkedList<MainItem>();
    	}
    	
    	listMenu.add(new MainItem(0, MENU_MUSIC, "Nhạc tết", R.drawable.icon_music));
    }
}
