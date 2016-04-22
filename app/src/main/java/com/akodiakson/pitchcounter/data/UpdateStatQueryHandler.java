package com.akodiakson.pitchcounter.data;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.net.Uri;

import java.lang.ref.WeakReference;

/**
 * Created by ace0808 on 4/20/2016.
 */
public class UpdateStatQueryHandler extends AsyncQueryHandler {

    private WeakReference<UpdateStatQueryListener> updateStatQueryListenerWeakReference;

    public interface UpdateStatQueryListener{
        void onUpdateComplete(int token, Object cookie, int result);
    }
    public UpdateStatQueryHandler(ContentResolver cr, WeakReference<UpdateStatQueryListener> updateStatQueryListenerWeakReference) {
        super(cr);
        this.updateStatQueryListenerWeakReference = updateStatQueryListenerWeakReference;
    }

    @Override
    protected void onUpdateComplete(int token, Object cookie, int result) {
        if(updateStatQueryListenerWeakReference == null || updateStatQueryListenerWeakReference.get() == null){
            return;
        }
        updateStatQueryListenerWeakReference.get().onUpdateComplete(token, cookie, result);
    }

}
