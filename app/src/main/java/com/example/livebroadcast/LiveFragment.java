package com.example.livebroadcast;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;


/**
 * Created by Malth on 2015/11/5.
 */
public class LiveFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.live,null);
    }

    @Override
    public void onResume(){
        super.onResume();
        MainActivity activity = (MainActivity)getActivity();
        if (activity.mPullRefreshListView != null){
            //activity.mPullRefreshListView.invalidate();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("LiveBroadcast","live fragment onPause");
    }



}
