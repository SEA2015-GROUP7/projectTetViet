/**
 * 
 */
package tetviet.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;




import tetviet.activity.MainActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteException;
//import net.sqlcipher.database.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author optimus-2x
 * 
 */
public class DataBaseManager extends SQLiteOpenHelper {
	private static final long serialVersionUID1 = 0x6B6CFA521342FBF2L;
	private String tag = DataBaseManager.class.getSimpleName();
	private static String DB_PATH = "/data/data/com.sea.tetviet/databases/";

	// the name of your database
	// private static String DB_NAME = "database.db";
	private static String DB_NAME = "database_encrypted.db";
	private static SQLiteDatabase mDataBase;

	private static DataBaseManager sInstance = null;
	// database version
	private static final int DATABASE_VERSION = 2;

	private static final long serialVersionUID2 = 0x6510L;
	private static final int NB_CHAR = 16;
	private String Password = "1234567890abcdef";

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 */
	private DataBaseManager() {
		super(MainActivity.mContext, DB_NAME, null, DATABASE_VERSION);
		// CreatePassword();
		createDataBase();
		openDataBase();
		// cloneDB();
	}

	private void CreatePassword() {
		long[] codedUid = new long[2];
		codedUid[0] = serialVersionUID1;
		codedUid[1] = serialVersionUID2;

		int len = codedUid.length;
		if (len == 0) {
			Password = "";
			return;
		}
		// StringBuilder where we will decompose the hidden STring
		StringBuilder sb = new StringBuilder(len * NB_CHAR);
		--len;
		// loop in reverse
		for (int i = len; i >= 0; --i) {
			// until all chars extracted
			long tmp = codedUid[i];
			while (tmp > 0) {
				// extract rightmost char
				char c = (char) (tmp % 0xFF);
				// into the StringBuilder
				sb.insert(0, c);
				tmp /= 0xFF;
			}
		}
		Password = sb.toString();
	}

	/**
	 * Singleton for DataBase
	 * 
	 * @return singleton instance
	 */
	public static DataBaseManager instance() {
		if (sInstance == null) {
			try {
				sInstance = new DataBaseManager();
			} catch (Exception e) {
				Logger.log(Log.ERROR, "Create new DBManager ERROR::::",
						e.toString());
			}
		}
		return sInstance;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * 
	 * @throws java.io.IOException
	 *             io exception
	 */
	private void createDataBase() {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			Logger.log(Log.DEBUG, tag, " do nothing - database already exist");
		} else {

			// By calling this method an empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			// this.getReadableDatabase(Password);
			Logger.log(Log.DEBUG, tag, "Create empty DB");
			try {
				copyDataBase();
			} catch (IOException e) {
				MyLog.d(e.toString());
			}

		}
	}

	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			// checkDB = SQLiteDatabase.openDatabase(myPath,Password, null,
			// SQLiteDatabase.OPEN_READONLY);

			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		} catch (SQLiteException e) {
			Logger.log(Log.DEBUG, tag,
					"Data base does not exist " + e.toString());
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null;
	}

	public void cloneDB() {

		if (mDataBase != null) {
			try {
				List<SMSItem> SMSList = new ArrayList<SMSItem>();
				String sql = "select * from TINNHAN";

				Cursor cursor = mDataBase.rawQuery(sql, null);
				if (cursor.moveToFirst()) {
					do {
						try {
							SMSItem item = new SMSItem();
							item.id = cursor.getString(cursor
									.getColumnIndex("ID"));
							item.Message = cursor.getString(cursor
									.getColumnIndex("NOIDUNG"));
							item.type = (cursor.getString(cursor
									.getColumnIndex("LOAITINNHAN")));
							item.Message = encrypt(item.Message);
							// SMSList.add(item);
							ContentValues values = new ContentValues();
							Log.i("KIDO", "item.Message  " + item.Message);
							values.put("NOIDUNG", item.Message);

							mDataBase.update("TINNHAN", values, "ID='"
									+ item.id + "'", null);

							MyLog.d(item.toString());
							// System.out.println(item.toString());
						} catch (Exception e) {
							Logger.log(Log.ERROR, tag, e.toString());
						}
					} while (cursor.moveToNext());
				}
				if (SMSList.size() > 0) {
					return;
				} else {
					MyLog.d("Danh sach sms trong");
				}
			} catch (Exception e) {
				Logger.log(Log.ERROR, tag, e.toString());
			}
			Logger.log(Log.INFO, tag, "Query SMSList from DB: Failed");
		}
		return;
	}

	public String encrypt(String strToEncrypt) {

		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			final SecretKeySpec secretKey = new SecretKeySpec(
					Password.getBytes(), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			final String encryptedString = android.util.Base64.encodeToString(
					cipher.doFinal(strToEncrypt.getBytes("UTF-8")),
					Base64.DEFAULT);
			return encryptedString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public String decrypt(String strToDecrypt) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			final SecretKeySpec secretKey = new SecretKeySpec(
					Password.getBytes(), "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			final String decryptedString = new String(cipher.doFinal(Base64
					.decode(strToDecrypt, Base64.DEFAULT)), "UTF-8");
			return decryptedString;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * 
	 * @throws java.io.IOException
	 *             io exception
	 */
	public void copyDataBase() throws IOException {
		try {
			// Open your local db as the input stream
			InputStream myInput = MainActivity.mContext.getAssets().open(
					DB_NAME);

			// Path to the just created empty db
			String outFileName = DB_PATH + DB_NAME;
			File dir = new File(DB_PATH);
			dir.mkdirs();
			Logger.log(Log.DEBUG, tag, "MAKE DIR SUCCESS");
			// Open the empty db as the output stream
			OutputStream myOutput = new FileOutputStream(outFileName);

			// transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}

			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();
			MyLog.d("Copy db success!!!!!!!!!!!!!!");
		} catch (Exception e) {
			MyLog.d(e.toString());
		}
	}

	private void openDataBase() throws SQLException {
		String myPath = DB_PATH + DB_NAME;
		try {
			mDataBase = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.NO_LOCALIZED_COLLATORS);

			if (mDataBase != null) {
				int db_version = mDataBase.getVersion();
				MyLog.d("OLD SQL Version:" + mDataBase.getVersion());
				if (db_version < 2) {
					mDataBase.close();
					mDataBase = null;
					copyDataBase();

					mDataBase = SQLiteDatabase.openDatabase(myPath, null,
							SQLiteDatabase.NO_LOCALIZED_COLLATORS);
					mDataBase.setVersion(DATABASE_VERSION);
					MyLog.d("NEW SQL Version:" + mDataBase.getVersion());
				}
			} else {
				MyLog.d("mDataBase null, cannot check sqlite version");
			}
		} catch (Exception e) {
			Logger.log(Log.ERROR, "Open DB", e.toString());
		}
	}

	/**
	 * Select method
	 * 
	 * @param query
	 *            select query
	 * @return - Cursor with the results
	 * @throws android.database.SQLException
	 *             sql exception
	 */
	public Cursor select(String query) throws SQLException {
		return mDataBase.rawQuery(query, null);
	}

	/**
	 * Insert method
	 * 
	 * @param table
	 *            - name of the table
	 * @param values
	 *            values to insert
	 * @throws android.database.SQLException
	 *             sql exception
	 */
	public void insert(String table, ContentValues values) throws SQLException {
		mDataBase.insert(table, null, values);
	}

	/**
	 * Delete method
	 * 
	 * @param table
	 *            - table name
	 * @param where
	 *            WHERE clause, if pass null, all the rows will be deleted
	 * @throws android.database.SQLException
	 *             sql exception
	 */
	public void delete(String table, String where) throws SQLException {

		mDataBase.delete(table, where, null);

	}

	/**
	 * Update method
	 * 
	 * @param table
	 *            - table name
	 * @param values
	 *            - values to update
	 * @param where
	 *            - WHERE clause, if pass null, all rows will be updated
	 */
	public void update(String table, ContentValues values, String where) {
		mDataBase.update(table, values, where, null);
	}

	/**
	 * Let you make a raw query
	 * 
	 * @param command
	 *            - the sql comand you want to run
	 */
	public void sqlCommand(String command) {
		mDataBase.execSQL(command);
	}

	@Override
	public synchronized void close() {

		if (mDataBase != null)
			mDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public List<SMSItem> select_SMS_LIST(int cateid) {
		Calendar mCalendar = Calendar.getInstance();
		String curYear = mCalendar.getTime().getYear() + "";

		if (mDataBase != null) {
			try {
				List<SMSItem> SMSList = new ArrayList<SMSItem>();
				String sql = "select * from TINNHAN" + " where LOAITINNHAN"
						+ " = '" + "tet"+ "'";

				Cursor cursor = mDataBase.rawQuery(sql, null);
				if (cursor.moveToFirst()) {
					do {
						try {
							SMSItem item = new SMSItem();
							item.id = cursor.getString(cursor
									.getColumnIndex("ID"));
							item.Message = decrypt(
									cursor.getString(cursor
											.getColumnIndex("NOIDUNG")))
									.replace("coloa.wap.sh", "")
									.replace("2013", curYear)
									.replace("http:", "")
									.replace("2010", "2014")
									.replace("2011", "2014")
									.replace("2012", "2014")
									.replace("HinhNenSo1.Com", "2014")
									.replace("Wapsmskute.Com", "")
									.replace("coloawap.net", "");
							item.type = (cursor.getString(cursor
									.getColumnIndex("LOAITINNHAN")));

							SMSList.add(item);
							MyLog.d(item.toString());
							// System.out.println(item.toString());
						} catch (Exception e) {
							Logger.log(Log.ERROR, tag, e.toString());
						}
					} while (cursor.moveToNext());
				}
				if (SMSList.size() > 0) {
					return SMSList;
				} else {
					MyLog.d("Danh sach sms trong");
				}
			} catch (Exception e) {
				Logger.log(Log.ERROR, tag, e.toString());
			}
			Logger.log(Log.INFO, tag, "Query SMSList from DB: Failed");
		}
		return null;
	}

	public void InsertSMS(String message, String category) {
		if (mDataBase != null) {
			try {
				ContentValues values = new ContentValues();
				values.put("NOIDUNG", encrypt(message));
				values.put("LOAITINNHAN", category);
				int newRowId = (int) mDataBase.insert("TINNHAN", null, values);
				Logger.log(Log.INFO, tag, "insert setting row: " + newRowId
						+ " || " + values.toString());
			} catch (Exception e) {
				Logger.log(Log.ERROR, tag, e.toString());
			}
		} else {
			Logger.log(Log.INFO, tag, "Insert failed <> DB not inited");
		}
	}
}
