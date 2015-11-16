package com.example.livebroadcast;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.test.malth.mylibrary.mylibclass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends FragmentActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener,RadioGroup.OnCheckedChangeListener {

	public LiveFragment live;
	public VideoFragment video;
	public DataFragment data;
	public NewsFragment news;
	public DiscoverFragment discover;

	public RadioGroup radioGroup;

    private SlideMenu slideMenu;


	private LinkedList<String> mListItems;
	PullToRefreshListView mPullRefreshListView;
	private ArrayAdapter<String> mAdapter;

	FragmentTransaction fragmentTransaction;

	//数据源
	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler" };

    //Live fragment
    private ViewPager pager;
    private LivePageAdapter livePageAdapter;
    private List<Fragment> fragments;
    private RadioGroup liveGroup;
    private RadioButton liveRadioButtonFocus, liveRadioButtonAll, liveRadioButtonFinished,liveRadioButtonImportant;

    LiveFocusFragment liveFocusFragment;
    LiveImportantFragment liveImportantFragment;
    LiveAllFragment liveAllFragment;
    LiveFinishedFragment liveFinishedFragment;

    //
    Intent intentMenuAccountActivity;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        int sum = 0;
        sum = mylibclass.sum(20,10);

        ActionBar actionBar = getActionBar();
		actionBar.hide();

		live = new LiveFragment();
		video = new VideoFragment();
		data = new DataFragment();
		news = new NewsFragment();
		discover = new DiscoverFragment();

		fragmentTransaction = getSupportFragmentManager().beginTransaction();


        fragmentTransaction.add(R.id.main_content, live);
        fragmentTransaction.add(R.id.main_content, video);
		fragmentTransaction.add(R.id.main_content, data);
		fragmentTransaction.add(R.id.main_content, news);
		fragmentTransaction.add(R.id.main_content, discover);

		fragmentTransaction.hide(news);
		fragmentTransaction.hide(video);
		fragmentTransaction.hide(data);
		fragmentTransaction.hide(discover);
		fragmentTransaction.show(live);
		fragmentTransaction.commit();

        liveFocusFragment = new LiveFocusFragment();
        liveImportantFragment = new LiveImportantFragment();
        liveAllFragment = new LiveAllFragment();
        liveFinishedFragment = new LiveFinishedFragment();


		radioGroup = (RadioGroup)findViewById(R.id.rg_navigate_menue);
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(video);
                fragmentTransaction.hide(news);
                fragmentTransaction.hide(data);
                fragmentTransaction.hide(discover);
                fragmentTransaction.hide(live);

                switch (checkedId) {
                    case R.id.rd_navigate_live:
                        fragmentTransaction.show(live);
                        break;
                    case R.id.rd_navigate_video:
                        fragmentTransaction.show(video);
                        break;
                    case R.id.rd_navigate_news:
                        fragmentTransaction.show(news);
                        break;
                    case R.id.rd_navigate_data:
                        fragmentTransaction.show(data);
                        break;
                    case R.id.rd_navigate_discover:
                        fragmentTransaction.show(discover);
                        break;
                    default:
                        break;
                }
                fragmentTransaction.commit();
            }
        });


		FrameLayout fl = (FrameLayout)findViewById(R.id.main_content);
		fl.post(new Runnable() {
			@Override
			public void run() {

                //live fragment settings
                fragments = new ArrayList<Fragment>();
                fragments.add(liveFocusFragment);
                fragments.add(liveImportantFragment);
                fragments.add(liveAllFragment);
                fragments.add(liveFinishedFragment);


                pager = (ViewPager) findViewById(R.id.pager);
                livePageAdapter = new LivePageAdapter(getSupportFragmentManager(), fragments);
                pager.setAdapter(livePageAdapter);
                pager.setOffscreenPageLimit(fragments.size() - 1);// 缓存页面,显示第一个缓存最后一个
                pager.setOnPageChangeListener(MainActivity.this);


                liveGroup = (RadioGroup) findViewById(R.id.rg_live);
                liveRadioButtonFocus = (RadioButton) findViewById(R.id.bt_live_focus);
                liveRadioButtonImportant = (RadioButton) findViewById(R.id.bt_live_important);
                liveRadioButtonAll = (RadioButton) findViewById(R.id.bt_live_all);
                liveRadioButtonFinished = (RadioButton) findViewById(R.id.bt_live_finished);
                liveGroup.setOnCheckedChangeListener(MainActivity.this);

                liveRadioButtonFocus.setChecked(true);


				mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.pull_refresh_list);
				//mPullRefreshListView.setVerticalScrollBarEnabled(false);
				mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

					@Override
					public void onPullDownToRefresh(PullToRefreshBase refreshView) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "onPullDownToRefresh", Toast.LENGTH_SHORT).show();
						new GetDataTask().execute();
					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase refreshView) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, "onPullUpToRefresh", Toast.LENGTH_SHORT).show();
						new GetDataTask().execute();
					}
				});

				ListView actualListView = mPullRefreshListView.getRefreshableView();

				// Need to use the Actual ListView when registering for Context Menu
				registerForContextMenu(actualListView);

				mListItems = new LinkedList<String>();
				mListItems.addAll(Arrays.asList(mStrings));

				mAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, mListItems);

				//
				List<Map<String,Object>> items = new ArrayList<Map<String, Object>>();
				Map<String,Object> item = new HashMap<String, Object>();
				item.put("timer","09:12");
				items.add(item);
				SimpleAdapter adapter = new SimpleAdapter(
						MainActivity.this,
						items,
						R.layout.simple_list_item,
						new String[]{"timer"},
						new int[]{R.id.simple_list_timer});
				actualListView.setAdapter(adapter);
				//actualListView.setAdapter(mAdapter);

			}
		});
        intentMenuAccountActivity = new Intent(MainActivity.this,MenuAccountActivity.class);


        slideMenu = (SlideMenu) findViewById(R.id.slide_menu);
        slideMenu.mEnableScroll = false;

        ImageView menuImg = (ImageView) findViewById(R.id.title_bar_menu_btn);
        menuImg.setOnClickListener(this);
	}

    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return false;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.title_bar_menu_btn:
                if (slideMenu.isMainScreenShowing()) {
                    slideMenu.openMenu();
                } else {
                    slideMenu.closeMenu();
                }
                break;
            case R.id.slide_menu_account:       //my account
                startActivity(intentMenuAccountActivity);
                //setContentView(R.layout.activity_menu_account);
                break;
            case R.id.slide_menu_focus:

                break;
            case R.id.slide_menu_download:

                break;
            case R.id.slide_menu_favorite:

                break;
            case R.id.slide_menu_prompt:

                break;
            case R.id.slide_menu_history:

                break;
            case R.id.slide_menu_plugin:

                break;
            case R.id.slide_menu_feedback:

                break;
            case R.id.slide_menu_setting:

                break;
            case R.id.bt_live_finished_test:
                TextView tv = (TextView)findViewById(R.id.tv_live_finished_test);
                tv.setText("tv_live_finished_test");
                Toast.makeText(MainActivity.this, "finished", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        getTabState(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void getTabState(int index) {
        // TODO Auto-generated method stub
        //pager.setCurrentItem(index);

        liveRadioButtonFocus.setChecked(false);
        liveRadioButtonImportant.setChecked(false);
        liveRadioButtonAll.setChecked(false);
        liveRadioButtonFinished.setChecked(false);

        switch (index) {
            case 0:
                liveRadioButtonFocus.setChecked(true);
                break;
            case 1:
                liveRadioButtonImportant.setChecked(true);
                break;
            case 2:
                liveRadioButtonAll.setChecked(true);
                break;
            case 3:
                liveRadioButtonFinished.setChecked(true);
                break;
            default:
                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        slideMenu.mEnableScroll = false;
        switch (checkedId) {
            case R.id.bt_live_focus:
                pager.setCurrentItem(0);
                slideMenu.mEnableScroll = true;
                break;
            case R.id.bt_live_important:
                pager.setCurrentItem(1);
                break;
            case R.id.bt_live_all:
                pager.setCurrentItem(2);
                break;
            case R.id.bt_live_finished:
                pager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }

    //模拟网络加载数据的   异步请求类
	//
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		//子线程请求数据
		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			return mStrings;
		}

		//主线程更新UI
		@Override
		protected void onPostExecute(String[] result) {

			//向RefreshListView Item 添加一行数据  并刷新ListView
			//mListItems.addLast("Added after refresh...");
			mListItems.addFirst("Added after refresh...");
			mAdapter.notifyDataSetChanged();

			//通知RefreshListView 我们已经更新完成
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
