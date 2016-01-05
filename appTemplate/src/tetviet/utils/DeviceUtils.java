package tetviet.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.Random;

/**
 * Created by thanh on 5/1/2016.
 */
public class DeviceUtils {
    /**
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     * @param mContext
     * @return phone number sim1
     */
    public static String getPhoneNumber(Context mContext){
        TelephonyManager tMgr = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        if(mPhoneNumber.startsWith("+84")) {
            mPhoneNumber = mPhoneNumber.substring(3);
            mPhoneNumber = "0"+mPhoneNumber;
        }

        return mPhoneNumber;
    }

    /**
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     * @param mContext
     * @return phone number sim1
     */
    public static String getImei(Context mContext){
        TelephonyManager tMgr = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tMgr.getDeviceId();
        if(imei == null || imei.length() == 0) {
            imei = new Random().nextDouble() + "";
        }

        return imei;
    }

    /**
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     * @param mContext
     * @return phone number sim1
     */
    public static String getDeviceUIID(Context mContext){
        String imei = getUniqueDeviceId.getUniqueDeviceID(mContext);

        LoggerFactory.d("DeviceUIID", imei);
        return imei;
    }
}
