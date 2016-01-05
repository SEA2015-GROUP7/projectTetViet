package tetviet.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class getUniqueDeviceId {

    /**
     * get IMSI number, defined in E.212
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     *
     * @param context
     * @return
     */
    public static String getIMSI(Context context) throws Exception {
//        String myIMSI = android.os.SystemProperties.get(android.telephony.TelephonyProperties.PROPERTY_IMSI);
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imsi = telephonyManager.getSubscriberId();

            return imsi;
        } catch (Exception e) {

            LoggerFactory.e("XMPhone", "android.permission.READ_PHONE_STATE permision required");
            throw e;
        }

    }

    /**
     * Get IMEI on real device
     * or unique string if not a device
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        String identifier = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null)
            identifier = tm.getDeviceId();
        if (identifier == null || identifier.length() == 0)
            identifier = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        return identifier;
    }

    /**
     * Try to generate unique string for a device
     *
     * @return
     */
    public static String getDeviceVirtualID() {
        String m_szDevIDShort = "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 digits
        return m_szDevIDShort;
    }

    /**
     * get bluetootch mac addresss
     *
     * @param c
     * @return
     */

    public static String getBluetoothMAC(Context c) {
        try {

            BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
            m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            String m_szBTMAC = m_BluetoothAdapter.getAddress();
            return m_szBTMAC;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * return MAC address of wifi card
     * requires permission:  android.permission.ACCESS_WIFI_STATE
     *
     * @param c
     * @return
     */
    public static String getWifiMac(Context c) {
        WifiManager wm = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();

        if (m_szWLANMAC == null) {
            LoggerFactory.e("XMPhone", " android.permission.ACCESS_WIFI_STATE permission required");
        }
        return m_szWLANMAC;
    }

    /**
     * get Android id, sometime it may return null,
     * No permission required.
     *
     * @param c
     * @return
     */
    public static String getAnroidID(Context c) {

        return Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * return MD5 hashed hexa for a device. can be used as device-id
     *
     * @param c
     * @return
     */
    public static String getUniqueDeviceID(Context c) {
        String imei = getImei(c);
        String wifiMAC = getWifiMac(c);
//        String bluetooth = getBluetoothMAC(c);
        String android_id = getAnroidID(c);
        String devShortId = getDeviceVirtualID();
        String imsi = "";
        try {
            imsi = getIMSI(c);
        }catch (Exception e){
            e.printStackTrace();
        }

        //MD5 hash
//        String m_szLongID = imei + wifiMAC + bluetooth + android_id + devShortId + imsi;
        String m_szLongID = imei + wifiMAC + android_id + devShortId + imsi;
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        byte p_md5Data[] = m.digest();

        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            // if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF) m_szUniqueID += "0";
            // add number to string
            m_szUniqueID += Integer.toHexString(b);
        }
        m_szUniqueID = m_szUniqueID.toUpperCase();

        //return MD5 hash
        return m_szUniqueID;
    }
}