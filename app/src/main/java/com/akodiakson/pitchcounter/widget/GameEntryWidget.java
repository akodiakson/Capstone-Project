package com.akodiakson.pitchcounter.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.widget.RemoteViews;

import com.akodiakson.pitchcounter.R;
import com.akodiakson.pitchcounter.data.GameContentProvider;
import com.akodiakson.pitchcounter.data.GameContract;
import com.akodiakson.pitchcounter.data.GameCursorUtil;
import com.akodiakson.pitchcounter.model.Game;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class GameEntryWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Date today = new Date();
        String gameDate = new SimpleDateFormat("yyyyMMdd").format(today);

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
        } else {
            game = new Game();
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.game_entry_widget);
        views.setOnClickPendingIntent(R.id.game_entry_ball_button, null);
        views.setOnClickPendingIntent(R.id.game_entry_strike_button, null);
        views.setTextViewText(R.id.total_pitch_count, String.valueOf(game.getPitches()));
        views.setTextViewText(R.id.game_entry_ball_button_value, String.valueOf(game.getBalls()));
        views.setTextViewText(R.id.game_entry_strike_button_value, String.valueOf(game.getStrikes()));


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
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
}

