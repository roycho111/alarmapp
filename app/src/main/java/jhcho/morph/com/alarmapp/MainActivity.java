package jhcho.morph.com.alarmapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    public static class PageFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private ListView mListView;

        public static PageFragment newInstance(int sectionNumber) {

            PageFragment fragment = new PageFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView;
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1)
            {
                rootView = inflater.inflate(R.layout.alarm_list_page, container, false);

                // set alarm list
                mListView = (ListView) rootView.findViewById(R.id.listView);
                CustomAdaptor customAdaptor = new CustomAdaptor();
                mListView.setAdapter(customAdaptor);

                // set fab
                FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
                fab.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "To be added", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        GotoAlarmPage(view);
                    }
                });
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2)
            {
                rootView = inflater.inflate(R.layout.history_page, container, false);
            }
            else
            {
                rootView = inflater.inflate(R.layout.setting_page, container, false);
            }

            return rootView;
        }

        class CustomAdaptor extends BaseAdapter{

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {

                View _view = getLayoutInflater().inflate(R.layout.alarm_item_layout, null);
                TextView mTimeView = _view.findViewById(R.id.time_label);
                TextView mDayView = _view.findViewById(R.id.day_label);
                TextView mNameView = _view.findViewById(R.id.name_label);

                mTimeView.setText("08:00");
                mDayView.setText("월화수목금토일");
                mNameView.setText("알람0" + i);

                mTimeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("zzz", "ASDSAD");
                        GotoAlarmPage(view);
                    }
                });

                return _view;
            }
        }

        public void GotoAlarmPage(View view)
        {
            startActivity(new Intent(view.getContext(), AlarmActivity.class));
        }

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("aaa", "" + position);
            return PageFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
