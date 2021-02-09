package msl.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String CATEGORY_ID = "CATEGORY_ID";
    private static final String CATEGORY_MASTER = "CATEGORY_MASTER";
    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final String COST = "COST";
    private static final String CREATE_TABLE_CATEGORY_MASTER = "CREATE TABLE CATEGORY_MASTER(CATEGORY_ID INTEGER PRIMARY KEY,CATEGORY_NAME TEXT,SEQUENCE INTEGER,TOTAL_ITEMS TEXT)";
    private static final String CREATE_TABLE_STICKERS_INFO = "CREATE TABLE STICKERS_INFO(STICKER_ID INTEGER PRIMARY KEY,STICKER_NAME TEXT,MAIN_CATEGORY TEXT,SUB_CATEGORY TEXT,IS_HOT TEXT,COST TEXT,THUMB_PATH TEXT,IMAGE_PATH TEXT,THUMB_SERVER_PATH TEXT,IMAGE_SERVER_PATH TEXT,IS_DOWNLOADED TEXT,SEQUENCE INTEGER,IS_UPDATED TEXT)";
    private static final String DATABASE_NAME = "STICKERS_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String IMAGE_PATH = "IMAGE_PATH";
    private static final String IMAGE_SERVER_PATH = "IMAGE_SERVER_PATH";
    private static final String IS_DOWNLOADED = "IS_DOWNLOADED";
    private static final String IS_HOT = "IS_HOT";
    private static final String IS_UPDATED = "IS_UPDATED";
    private static final String MAIN_CATEGORY = "MAIN_CATEGORY";
    private static final String SEQUENCE = "SEQUENCE";
    private static final String STICKERS_INFO = "STICKERS_INFO";
    private static final String STICKER_ID = "STICKER_ID";
    private static final String STICKER_NAME = "STICKER_NAME";
    private static final String SUB_CATEGORY = "SUB_CATEGORY";
    private static final String THUMB_PATH = "THUMB_PATH";
    private static final String THUMB_SERVER_PATH = "THUMB_SERVER_PATH";
    private static final String TOTAL_ITEMS = "TOTAL_ITEMS";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static DatabaseHandler getDbHandler(Context context) {
        return new DatabaseHandler(context);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_TABLE_CATEGORY_MASTER);
        sQLiteDatabase.execSQL(CREATE_TABLE_STICKERS_INFO);
        Log.i("testing", "Database Created");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS CATEGORY_MASTER");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS STICKERS_INFO");
        onCreate(sQLiteDatabase);
    }

    public void insertCategoryMasterRow(CategoryRowInfo categoryRowInfo) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_NAME, categoryRowInfo.getCATEGORY_NAME());
        contentValues.put(SEQUENCE, Integer.valueOf(categoryRowInfo.getSEQUENCE()));
        contentValues.put(TOTAL_ITEMS, Integer.valueOf(categoryRowInfo.getTOTAL_ITEMS()));
        writableDatabase.insert(CATEGORY_MASTER, null, contentValues);
        writableDatabase.close();
    }

    public long insertStickerInfoRow(StickerInfo stickerInfo) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STICKER_NAME, stickerInfo.getSTICKER_NAME());
        contentValues.put(MAIN_CATEGORY, stickerInfo.getMAIN_CATEGORY());
        contentValues.put(SUB_CATEGORY, stickerInfo.getSUB_CATEGORY());
        contentValues.put(IS_HOT, stickerInfo.IS_HOT());
        contentValues.put(COST, Integer.valueOf(stickerInfo.getCOST()));
        contentValues.put(THUMB_PATH, stickerInfo.getTHUMB_PATH());
        contentValues.put(IMAGE_PATH, stickerInfo.getIMAGE_PATH());
        contentValues.put(THUMB_SERVER_PATH, stickerInfo.getTHUMB_SERVER_PATH());
        contentValues.put(IMAGE_SERVER_PATH, stickerInfo.getIMAGE_SERVER_PATH());
        contentValues.put(IS_DOWNLOADED, stickerInfo.IS_DOWNLOADED());
        contentValues.put(SEQUENCE, Integer.valueOf(stickerInfo.getSEQUENCE()));
        contentValues.put(IS_UPDATED, stickerInfo.getIS_UPDATED());
        long insert = writableDatabase.insert(STICKERS_INFO, null, contentValues);
        writableDatabase.close();
        return insert;
    }

    public ArrayList<CategoryRowInfo> getCategoriesList() {
        ArrayList<CategoryRowInfo> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT  * FROM CATEGORY_MASTER ORDER BY SEQUENCE ASC;", null);
        if (rawQuery == null || rawQuery.getCount() <= 0 || !rawQuery.moveToFirst()) {
            readableDatabase.close();
        } else {
            do {
                arrayList.add(new CategoryRowInfo(rawQuery.getString(1), rawQuery.getInt(2), rawQuery.getInt(3)));
            } while (rawQuery.moveToNext());
            readableDatabase.close();
        }
        return arrayList;
    }

    public ArrayList<StickerInfo> getStickerInfoList(String str) {
        ArrayList<StickerInfo> arrayList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM STICKERS_INFO WHERE MAIN_CATEGORY='");
        sb.append(str);
        sb.append("' AND ");
        sb.append(IS_DOWNLOADED);
        sb.append(" = '");
        sb.append(String.valueOf(true));
        sb.append("' ORDER BY ");
        sb.append(SEQUENCE);
        sb.append(" ASC");
        String sb2 = sb.toString();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery(sb2, null);
        if (rawQuery == null || rawQuery.getCount() <= 0 || !rawQuery.moveToFirst()) {
            readableDatabase.close();
        } else {
            do {
                StickerInfo stickerInfo = new StickerInfo();
                stickerInfo.setSTICKER_ID(rawQuery.getLong(0));
                stickerInfo.setSTICKER_NAME(rawQuery.getString(1));
                stickerInfo.setMAIN_CATEGORY(rawQuery.getString(2));
                stickerInfo.setSUB_CATEGORY(rawQuery.getString(3));
                stickerInfo.setIS_HOT(rawQuery.getString(4));
                stickerInfo.setCOST(rawQuery.getInt(5));
                stickerInfo.setTHUMB_PATH(rawQuery.getString(6));
                stickerInfo.setIMAGE_PATH(rawQuery.getString(7));
                stickerInfo.setTHUMB_SERVER_PATH(rawQuery.getString(8));
                stickerInfo.setIMAGE_SERVER_PATH(rawQuery.getString(9));
                stickerInfo.setIS_DOWNLOADED(rawQuery.getString(10));
                stickerInfo.setSEQUENCE(rawQuery.getInt(11));
                stickerInfo.setIS_UPDATED(rawQuery.getString(12));
                arrayList.add(stickerInfo);
            } while (rawQuery.moveToNext());
            readableDatabase.close();
        }
        return arrayList;
    }

    public ArrayList<StickerInfo> getStickerInfoList1(String str, int i) {
        ArrayList<StickerInfo> arrayList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM STICKERS_INFO WHERE IS_UPDATED='");
        sb.append(String.valueOf(true));
        sb.append("' AND ");
        sb.append(MAIN_CATEGORY);
        sb.append("='");
        sb.append(str);
        sb.append("' AND ");
        sb.append(SEQUENCE);
        sb.append(" > '");
        sb.append(String.valueOf(i));
        sb.append("' ORDER BY ");
        sb.append(SEQUENCE);
        sb.append(" ASC;");
        String sb2 = sb.toString();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery(sb2, null);
        if (rawQuery == null || rawQuery.getCount() <= 0 || !rawQuery.moveToFirst()) {
            readableDatabase.close();
        } else {
            do {
                StickerInfo stickerInfo = new StickerInfo();
                stickerInfo.setSTICKER_ID(rawQuery.getLong(0));
                stickerInfo.setSTICKER_NAME(rawQuery.getString(1));
                stickerInfo.setMAIN_CATEGORY(rawQuery.getString(2));
                stickerInfo.setSUB_CATEGORY(rawQuery.getString(3));
                stickerInfo.setIS_HOT(rawQuery.getString(4));
                stickerInfo.setCOST(rawQuery.getInt(5));
                stickerInfo.setTHUMB_PATH(rawQuery.getString(6));
                stickerInfo.setIMAGE_PATH(rawQuery.getString(7));
                stickerInfo.setTHUMB_SERVER_PATH(rawQuery.getString(8));
                stickerInfo.setIMAGE_SERVER_PATH(rawQuery.getString(9));
                stickerInfo.setIS_DOWNLOADED(rawQuery.getString(10));
                stickerInfo.setSEQUENCE(rawQuery.getInt(11));
                stickerInfo.setIS_UPDATED(rawQuery.getString(12));
                arrayList.add(stickerInfo);
            } while (rawQuery.moveToNext());
            readableDatabase.close();
        }
        return arrayList;
    }

    public ArrayList<StickerInfo> getStickerInfoByName(String str) {
        ArrayList<StickerInfo> arrayList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM STICKERS_INFO WHERE STICKER_NAME='");
        sb.append(str);
        sb.append("'");
        String sb2 = sb.toString();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery(sb2, null);
        if (rawQuery == null || rawQuery.getCount() <= 0 || !rawQuery.moveToFirst()) {
            readableDatabase.close();
        } else {
            do {
                StickerInfo stickerInfo = new StickerInfo();
                stickerInfo.setSTICKER_ID(rawQuery.getLong(0));
                stickerInfo.setSTICKER_NAME(rawQuery.getString(1));
                stickerInfo.setMAIN_CATEGORY(rawQuery.getString(2));
                stickerInfo.setSUB_CATEGORY(rawQuery.getString(3));
                stickerInfo.setIS_HOT(rawQuery.getString(4));
                stickerInfo.setCOST(rawQuery.getInt(5));
                stickerInfo.setTHUMB_PATH(rawQuery.getString(6));
                stickerInfo.setIMAGE_PATH(rawQuery.getString(7));
                stickerInfo.setTHUMB_SERVER_PATH(rawQuery.getString(8));
                stickerInfo.setIMAGE_SERVER_PATH(rawQuery.getString(9));
                stickerInfo.setIS_DOWNLOADED(rawQuery.getString(10));
                stickerInfo.setSEQUENCE(rawQuery.getInt(11));
                stickerInfo.setIS_UPDATED(rawQuery.getString(12));
                arrayList.add(stickerInfo);
            } while (rawQuery.moveToNext());
            readableDatabase.close();
        }
        return arrayList;
    }

    public void updateCategoryRow(String str, int i, int i2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SEQUENCE, Integer.valueOf(i));
        contentValues.put(TOTAL_ITEMS, Integer.valueOf(i2));
        writableDatabase.update(CATEGORY_MASTER, contentValues, "CATEGORY_NAME= ?", new String[]{str});
        writableDatabase.close();
    }

    public void updateStickerInfoRow(long j, int i) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SEQUENCE, Integer.valueOf(i));
        contentValues.put(IS_UPDATED, String.valueOf(true));
        writableDatabase.update(STICKERS_INFO, contentValues, "STICKER_ID= ?", new String[]{String.valueOf(j)});
        writableDatabase.close();
    }

    public void disableAllRow() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IS_UPDATED, String.valueOf(false));
        writableDatabase.update(STICKERS_INFO, contentValues, "SEQUENCE > ?", new String[]{"0"});
        writableDatabase.close();
    }

    public void updateStickerImagePath(long j, String str, boolean z) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE_PATH, str);
        contentValues.put(IS_DOWNLOADED, String.valueOf(z));
        writableDatabase.update(STICKERS_INFO, contentValues, "STICKER_ID= ?", new String[]{String.valueOf(j)});
        writableDatabase.close();
    }

    public void updateStickerThumbPath(long j, String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(THUMB_PATH, str);
        writableDatabase.update(STICKERS_INFO, contentValues, "STICKER_ID= ?", new String[]{String.valueOf(j)});
        writableDatabase.close();
    }

    public boolean deleteCategoryMasterRow(String str) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM CATEGORY_MASTER WHERE CATEGORY_NAME='");
            sb.append(str);
            sb.append("'");
            writableDatabase.execSQL(sb.toString());
            writableDatabase.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStickerInfoRow(int i) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM STICKERS_INFO WHERE STICKER_ID='");
            sb.append(i);
            sb.append("'");
            writableDatabase.execSQL(sb.toString());
            writableDatabase.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
