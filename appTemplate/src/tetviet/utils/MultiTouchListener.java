package tetviet.utils;

/**
 * Created by thanh on 08/12/2015.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.RelativeLayout;

public class MultiTouchListener implements OnTouchListener
{

    private float mPrevX;
    private float mPrevY;

    public Context mainActivity;
    public MultiTouchListener(Context mainActivity1) {
        mainActivity = mainActivity1;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float currX,currY;
        int action = event.getAction();
        switch (action ) {
            case MotionEvent.ACTION_DOWN: {

                mPrevX = event.getX();
                mPrevY = event.getY();
                break;
            }

            case MotionEvent.ACTION_MOVE:
            {

                currX = event.getRawX();
                currY = event.getRawY();


                MarginLayoutParams marginParams = new MarginLayoutParams(view.getLayoutParams());
                marginParams.setMargins((int)(currX - mPrevX), (int)(currY - mPrevY),0, 0);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
                view.setLayoutParams(layoutParams);


                break;
            }



            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_UP:
                LoggerFactory.d("MotionEvent.ACTION_UP  = " + event.getRawX() + "/" + event.getRawY());

                if(Math.abs(event.getRawX() - mPrevX) < 10 && Math.abs(event.getRawY() - mPrevY) < 10){
                    LoggerFactory.d("Click oke");
                }
                break;
        }

        return true;
    }

}