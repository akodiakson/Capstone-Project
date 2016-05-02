package com.akodiakson.pitchcounter.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import com.akodiakson.pitchcounter.R;
import com.akodiakson.pitchcounter.data.GameContentProvider;
import com.akodiakson.pitchcounter.data.GameContract;
import com.akodiakson.pitchcounter.data.GameCursorUtil;
import com.akodiakson.pitchcounter.data.StatType;
import com.akodiakson.pitchcounter.data.UpdateStatQueryHandler;
import com.akodiakson.pitchcounter.model.Game;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class GameEntryWidget extends AppWidgetProvider {

    private static final String ACTION_BALL = "ACTION_BALL";
    private static final String ACTION_STRIKE = "ACTION_STRIKE";
    private static final String WIDGET_ID = "WIDGET_ID";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        System.out.println("GameEntryWidget.updateAppWidget");

        Game game = getGameData(context);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.game_entry_widget);
        views.setOnClickPendingIntent(R.id.game_entry_ball_button, getPitchClickedPendingIntent(context, ACTION_BALL, appWidgetId));
        views.setOnClickPendingIntent(R.id.game_entry_strike_button, getPitchClickedPendingIntent(context, ACTION_STRIKE, appWidgetId));
        views.setTextViewText(R.id.total_pitch_count, String.valueOf(game.getPitches()));
        views.setTextViewText(R.id.game_entry_ball_button_value, String.valueOf(game.getBalls()));
        views.setTextViewText(R.id.game_entry_strike_button_value, String.valueOf(game.getStrikes()));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static Game getGameData(Context context) {
        System.out.println("GameEntryWidget.getGameData");
        String gameDate = getTodaysDate();

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(
                GameContentProvider.CONTENT_URI,
                GameContract.PROJECTION_ALL_COLUMNS,
                GameContract.DATE + " = ?", //selection
                new String[]{gameDate}, null);

        Game game;
        if(cursor != null){
            cursor.moveToFirst();
            game = GameCursorUtil.buildGame(cursor);
            cursor.close();
        } else {
            game = new Game();
        }
        return game;
    }

    private static String getTodaysDate() {
        Date today = new Date();
        return new SimpleDateFormat("yyyyMMdd").format(today);
    }

    private static PendingIntent getPitchClickedPendingIntent(Context context, String action, int widgetId) {
        Intent intent = new Intent(context, GameEntryWidget.class);
        intent.setAction(action);
        intent.putExtra(WIDGET_ID, widgetId);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        System.out.println("GameEntryWidget.onUpdate");
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        System.out.println("GameEntryWidget.onReceive " + intent.getAction());

        int widgetId = intent.getIntExtra(WIDGET_ID, 0);

        Game game = getGameData(context);

        ContentValues contentValues = new ContentValues();
        contentValues.put(StatType.TOTAL_PITCHES.getAssociatedStatColumn(), (game.getPitches()+1));

        if(ACTION_BALL.equals(intent.getAction())){
            contentValues.put(StatType.BALL.getAssociatedStatColumn(), (game.getBalls()+1));
            updateStatValue(context, contentValues, StatType.BALL, widgetId);
        } else if(ACTION_STRIKE.equals(intent.getAction())){
            contentValues.put(StatType.STRIKE.getAssociatedStatColumn(), (game.getStrikes()+1));
            updateStatValue(context, contentValues, StatType.STRIKE, widgetId);
        }
    }

    private void updateStatValue(Context context, ContentValues contentValues, StatType statType, int widgetId) {
        String where = GameContract.DATE + " = ?";
        String[] selectionArgs = new String[]{getTodaysDate()};
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.update(GameContentProvider.CONTENT_URI, contentValues, where, selectionArgs);
        UpdateStatQueryHandler handler = new UpdateStatQueryHandler(context.getContentResolver(), new WeakReference<>(context), widgetId);
        handler.startUpdate(statType.ordinal(), 42, GameContentProvider.CONTENT_URI, contentValues, where, selectionArgs);
    }
}

