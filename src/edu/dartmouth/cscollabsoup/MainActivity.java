package edu.dartmouth.cscollabsoup;



import edu.dartmouth.cscollabsoup.R;
import android.app.Fragment;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.ActionBar.Tab;


public class MainActivity extends Activity {
	private static final String TAB_INDEX_KEY = "tab_index";  
	// string for keep tracking last tab 
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		ActionBar mainActionBar = getActionBar();
		mainActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		// IF WE want to change to different style, we should modify this part 
		
//		Tab mProfileTab = mainActionBar.newTab().setText(getString(R.string.ui_profile_tab_start_title)).setTabListener(
//				new TabListener <ProfileTabFragment>(this, getString(R.string.ui_profile_tab_start_title), ProfileTabFragment.class) );
//		mainActionBar.addTab(mProfileTab);
//		
//		Tab mCourseTab = mainActionBar.newTab().setText(getString(R.string.ui_course_tab_title)).setTabListener(
//				new TabListener <CourseTabFragment>(this, getString(R.string.ui_course_tab_title), CourseTabFragment.class) );
//		mainActionBar.addTab(mCourseTab);
		
		//Restore Navigation part 
		if (savedInstanceState != null) {
			mainActionBar.setSelectedNavigationItem(savedInstanceState.getInt(
			TAB_INDEX_KEY, 0));
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
        outState.putInt(TAB_INDEX_KEY, getActionBar()
                                .getSelectedNavigationIndex());
	}

	// Pretty standard stuff for TabListener.
	public static class TabListener<T extends Fragment> implements
				ActionBar.TabListener {
		private Fragment mFragment;
		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;

		public TabListener(Activity activity, String tag, Class<T> clz) {
			mActivity = activity;
			mTag = tag;
			mClass = clz;
				
			mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
			if(mFragment != null && !mFragment.isDetached()){
				FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
				ft.detach(mFragment);
				ft.commit();
			}
				
		}
		/* The following are each of the ActionBar.TabListener callbacks */
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			//mFragment = (Fragment) Fragment.instantiate(mActivity, fname)
			if (mFragment == null){
				mFragment = Fragment.instantiate(mActivity, mClass.getName(), null);
				ft.add(android.R.id.content, mFragment, mTag);
			}
			else {
				ft.attach(mFragment);
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (mFragment != null){
				ft.detach(mFragment);
			}
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// User selected the already selected tab. Usually do nothing.
			Toast.makeText(mActivity, "Clicked SameTab again", Toast.LENGTH_SHORT).show();
			}
	}
	
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
//			Bundle savedInstanceState){
//		// inflate layout here and return the View !!
//		return inflater.inflate(R.layout.activity_profile, container, false);
//	}
}