package org.rajawali3d.vr.example;

import android.app.ListActivity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dileep on 12/30/2015.
 */
public class ListViewActivity extends ListActivity {

    private static final String TAG = "ListViewActivity";

    // private Item[] fileList;
    private File path = new File(Environment.getExternalStorageDirectory() + "");
  List<String> str = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    /**
     * This method will be called when an item in the list is selected.
     * Subclasses should override. Subclasses can call
     * getListView().getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param l        The ListView where the click happened
     * @param v        The view that was clicked within the ListView
     * @param position The position of the view in the list
     * @param id       The row id of the item that was clicked
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
      Log.d(TAG, str.get(position));
       str.clear();
        Log.d(TAG, str.toString());
        path=new File(Environment.getExternalStorageDirectory()+"/"+str.get(position));
        Log.d(TAG,path.toString());
        adapter.clear();
        adapter=null;
        setListAdapter(null);
        if(path.exists()){
             if(path.isDirectory()){
                 loadFileList();
                 adapter = new ArrayAdapter<String>(this,
                         android.R.layout.simple_list_item_1,str);
                 setListAdapter(adapter);
             }
        }
    }

    /**
     * Called when the activity is starting.  This is where most initialization
     * should go: calling {@link #setContentView(int)} to inflate the
     * activity's UI, using {@link #findViewById} to programmatically interact
     * with widgets in the UI, calling
     * {@link #managedQuery(Uri, String[], String, String[], String)} to retrieve
     * cursors for data being displayed, etc.
     * <p/>
     * <p>You can call {@link #finish} from within this function, in
     * which case onDestroy() will be immediately called without any of the rest
     * of the activity lifecycle ({@link #onStart}, {@link #onResume},
     * {@link #onPause}, etc) executing.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     * @see #onStart
     * @see #onSaveInstanceState
     * @see #onRestoreInstanceState
     * @see #onPostCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFileList();
       adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,str);
        setListAdapter(adapter);

    }
    private void loadFileList(){
        try {
            path.mkdirs();
        } catch (SecurityException e) {
            Log.e(TAG, "unable to write on the sd card ");
        }

        if (path.exists()) {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    // Filters based on whether the file is hidden or not
                    return (sel.isFile() || sel.isDirectory())
                            && !sel.isHidden();

                }
            };

            String[] fList = path.list(filter);
            for (int i = 0; i < fList.length; i++) {
                str.add(fList[i]);
            }
        }
    }
}
