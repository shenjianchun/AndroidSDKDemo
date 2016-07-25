package com.example.android.telephony;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.widget.SimpleCursorAdapter;

/**
 * Sms demo
 * Created by 14110105 on 2016-05-10.
 */
public class SmsListActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    // This is the Adapter being used to display the list's data.
    SimpleCursorAdapter mAdapter;

    static final String[] PROJECTION = new String[]{Telephony.Sms._ID, Telephony.Sms.ADDRESS , Telephony.Sms.BODY};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                null,
                new String[]{Telephony.Sms.ADDRESS, Telephony.Sms.BODY},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);

        setListAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri baseUri = Telephony.Sms.CONTENT_URI;


        return new CursorLoader(this, baseUri, PROJECTION, null, null, Telephony.Sms.DEFAULT_SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
