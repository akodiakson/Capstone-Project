package com.akodiakson.pitchcounter.data;

import android.appwidget.AppWidgetManager;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;

import com.akodiakson.pitchcounter.widget.GameEntryWidget;

import java.lang.ref.WeakReference;

/**
 * Created by ace0808 on 4/20/2016.
 */
public class UpdateStatQueryHandler extends AsyncQueryHandler {

    private WeakReference<UpdateStatQueryListener> updateStatQueryListenerWeakReference;
    private WeakReference<Context> contextWeakReference;
    private int widgetId = -1;

    public interface UpdateStatQueryListener{
        void onUpdateComplete(int token, Object cookie, int result);
    }
    public UpdateStatQueryHandler(ContentResolver cr, WeakReference<UpdateStatQueryListener> updateStatQueryListenerWeakReference) {
        super(cr);
        this.updateStatQueryListenerWeakReference = updateStatQueryListenerWeakReference;
    }

    public UpdateStatQueryHandler(ContentResolver cr,  WeakReference<Context> contextWeakReference, int widgetId) {
        super(cr);
        this.contextWeakReference = contextWeakReference;
        this.widgetId = widgetId;
    }

    @Override
    protected void onUpdateComplete(int token, Object cookie, int result) {
        if(42 != Integer.valueOf(cookie.toString())){
            if(updateStatQueryListenerWeakReference == null || updateStatQueryListenerWeakReference.get() == null){
                return;
            }
            UpdateStatQueryListener updateStatQueryListener = updateStatQueryListenerWeakReference.get();
            updateStatQueryListener.onUpdateComplete(token, cookie, result);
        }

        if(contextWeakReference == null || contextWeakReference.get() == null){
            return;
        }

        Context context = contextWeakReference.get();
        Intent updateIntent = new Intent(context, GameEntryWidget.class);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
        // since it seems the onUpdate() is only fired on that:
        int[] ids = {widgetId};
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        context.sendBroadcast(updateIntent);
    }

}
