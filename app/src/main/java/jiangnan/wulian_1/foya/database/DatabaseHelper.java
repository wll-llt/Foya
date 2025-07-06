package jiangnan.wulian_1.foya.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FoyaDB";
    private static final int DATABASE_VERSION = 1;

    // 用户表
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_CREATE_TIME = "create_time";

    // 创建用户表的SQL语句
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT UNIQUE NOT NULL,"
            + COLUMN_PASSWORD + " TEXT NOT NULL,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PHONE + " TEXT,"
            + COLUMN_CREATE_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        
        // 插入默认用户
        insertDefaultUser(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private void insertDefaultUser(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, "admin");
        values.put(COLUMN_PASSWORD, "123456");
        values.put(COLUMN_EMAIL, "admin@foya.com");
        values.put(COLUMN_PHONE, "13800138000");
        db.insert(TABLE_USERS, null, values);
    }

    // 注册新用户
    public boolean registerUser(String username, String password, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // 验证用户登录
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }

    // 检查用户名是否已存在
    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }

    // 获取用户信息
    public User getUserInfo(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_PHONE, COLUMN_CREATE_TIME};
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME);
            int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
            int createTimeIndex = cursor.getColumnIndex(COLUMN_CREATE_TIME);
            
            if (idIndex >= 0) user.setId(cursor.getInt(idIndex));
            if (usernameIndex >= 0) user.setUsername(cursor.getString(usernameIndex));
            if (emailIndex >= 0) user.setEmail(cursor.getString(emailIndex));
            if (phoneIndex >= 0) user.setPhone(cursor.getString(phoneIndex));
            if (createTimeIndex >= 0) user.setCreateTime(cursor.getString(createTimeIndex));
        }
        cursor.close();
        
        return user;
    }
} 