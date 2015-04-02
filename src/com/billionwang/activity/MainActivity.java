package com.billionwang.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.billionwang.fragment.LineSearchFragment;
import com.billionwang.fragment.LineRouteFragment;
import com.billionwang.fragment.StationSearchFragment;
import com.example.baidubus.R;

public class MainActivity extends FragmentActivity {
	
	private ViewPager viewPager;
	private ImageView imgView1;
	private ImageView imgView2;
	private ImageView imgView3;
	private ImageView tabline;
	private List<Fragment> list;// 声明一个list集合存放Fragment（数据源）
	private int tabLineLength;// 1/3屏幕宽
	private int currentPage = 0;// 初始化当前页为0（第一页）
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        initTabLine();
        //初始化界面
        initView();
        
    }
    
    private void initTabLine(){
    	//获取显示屏信息
    	Display display = getWindow().getWindowManager().getDefaultDisplay();
    	
    	//得到显示屏宽度
    	DisplayMetrics metrics = new DisplayMetrics();
    	display.getMetrics(metrics);
    	
    	// 1/3屏幕宽度
    	tabLineLength = metrics.widthPixels/3;
    	
    	//获取控件实例
    	tabline = (ImageView) findViewById(R.id.tabline);
    	
    	//控件参数
    	LayoutParams  lp = (LayoutParams) tabline.getLayoutParams();
    	lp.width = tabLineLength;
    	tabline.setLayoutParams(lp);
    	
    }

    private void initView(){
    	imgView1 = (ImageView) findViewById(R.id.imageView1);
    	imgView2 = (ImageView) findViewById(R.id.imageView2);
    	imgView3 = (ImageView) findViewById(R.id.imageView3);
    	imgView1.setOnClickListener(new ImageViewClick());
    	imgView2.setOnClickListener(new ImageViewClick());
    	imgView3.setOnClickListener(new ImageViewClick());
    	
    	viewPager = (ViewPager) findViewById(R.id.viewpager);
    	list = new ArrayList<Fragment>();
    	
    	//设置数据源
    	LineRouteFragment lineRouteFragment = new LineRouteFragment();
    	StationSearchFragment stationInfoFragment = new StationSearchFragment();
    	LineSearchFragment carInfoFragment = new LineSearchFragment();
    	
    	list.add(lineRouteFragment);
    	list.add(stationInfoFragment);
    	list.add(carInfoFragment);
    	
    	//设置适配器
    	
    	FragmentPagerAdapter adapter = new FragmentPagerAdapter(
    			getSupportFragmentManager()){

					@Override
					public android.support.v4.app.Fragment getItem(int arg0) {
						return list.get(arg0);
					}

					@Override
					public int getCount() {
						return list.size();
					}
    		
    	};
    	
    	//绑定适配器
    	viewPager.setAdapter(adapter);
    	
    	viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				currentPage = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
				//取得该控件的实例
				LinearLayout.LayoutParams ll = (android.widget.LinearLayout.LayoutParams) tabline.getLayoutParams();
				if (currentPage == 0 && arg0 == 0) { // 0->1移动(第一页到第二页)
					                    ll.leftMargin = (int) (currentPage * tabLineLength + arg1
					                            * tabLineLength);
					                } else if (currentPage == 1 && arg0 == 1) { // 1->2移动（第二页到第三页）
					                    ll.leftMargin = (int) (currentPage * tabLineLength + arg1
					                            * tabLineLength);
					                } else if (currentPage == 1 && arg0 == 0) { // 1->0移动（第二页到第一页）
					                    ll.leftMargin = (int) (currentPage * tabLineLength - ((1 - arg1) * tabLineLength));
					                } else if (currentPage == 2 && arg0 == 1) { // 2->1移动（第三页到第二页）
					                    ll.leftMargin = (int) (currentPage * tabLineLength - (1 - arg1)
					                            * tabLineLength);
					                }
					
					                 tabline.setLayoutParams(ll);
						
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
    	
    }
    
    class ImageViewClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imageView1:
				viewPager.setCurrentItem(0,true);
				break;
			case R.id.imageView2:
				viewPager.setCurrentItem(1,true);
				break;
			case R.id.imageView3:
				viewPager.setCurrentItem(2,true);
				break;
			default:
				break;
			}
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
        if (id == R.id.action_more) {
        	Intent intent = new Intent(MainActivity.this,SettingActivity.class);
        	startActivity(intent);
        	
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /*@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		String returnPosition;
		Log.d("onActivityResult", "OK");
		if(resultCode ==android.app.Activity.RESULT_OK ){
			Log.d("onActivityResult", "OK");
			switch (requestCode) {
			case 1:
					returnPosition = data.getStringExtra("data_return");
					Log.d("returnPosition", returnPosition);
					//editStart.setText(returnPosition);
				
				break;
			case 2:
				 returnPosition = data.getStringExtra("data_return");
				//editEnd.setText(returnPosition);
			default:
				break;
			}
		}
	}*/
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	Log.d("MainActivityStart", "onStart");
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	Log.d("MainActivityDestory", "onDestory");
    }

    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	Log.d("MainActivityonResume", "onResume");
    }
}
