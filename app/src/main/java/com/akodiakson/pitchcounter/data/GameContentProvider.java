package com.akodiakson.pitchcounter.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

public class GameContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.akodiakson.pitchcounter.provider";
    private static final String BASE_PATH = "games";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    private static final String TAG = "GameProvider";

    private GameDbHelper gameDbHelper;
    public GameContentProvider() {
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long id = gameDbHelper.getWritableDatabase().insert(GameContract.TABLE_NAME, null, values);
        Context context = getContext();
        if(context != null){
            ContentResolver contentResolver = context.getContentResolver();
            contentResolver.notifyChange(uri, null);
        }
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public boolean onCreate() {
        gameDbHelper = new GameDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return gameDbHelper.getReadableDatabase().query(
                GameContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return gameDbHelper.getWritableDatabase().update(
                GameContract.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}
