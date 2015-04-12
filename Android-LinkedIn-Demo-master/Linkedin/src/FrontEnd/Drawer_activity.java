
package frontEnd;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linkedin.R;
import com.mukesh.linkedin.MainActivity;

import beans.MyProfile;
import beans.NetworkUpdate;
import linkedinAPI.MyProfileAPI;
import linkedinAPI.NetworkUpdateAPI;
import oauth.signpost.OAuthConsumer;

/**
 * Created by lanzhang_mini on 15/4/9.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Drawer_activity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;

    static OAuthConsumer consumer = null;

    static final String[] m={"google","oracle","LinkedIn","amazon","baidu","microsoft","twitter","facebook"};


    public Drawer_activity() {
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);

        Intent intent = this.getIntent();
        consumer = (OAuthConsumer) intent.getSerializableExtra("consumer");

        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.menu_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.menu_settings:
                // create intent to perform web search for this planet
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
                // catch event that there's no activity to handle intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }


    private void selectItem(int position) {
        // update the main content by replacing fragments

        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new FragmentMyprofile();
                break;
            case 1:
                fragment = new FragmentSearch();
                break;
            default:
                return;
        }
                new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.planets_array)[i];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                    "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
            return rootView;
        }
    }

    public static class FragmentMyprofile extends Fragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

//            color = this.getArguments().getString(CAT_COLOR);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return initView(inflater, container);
        }

        private View initView(LayoutInflater inflater, ViewGroup container) {
            View view = inflater.inflate(R.layout.myprofile_main, container, false);

            MyProfileAPI myProfileAPI = new MyProfileAPI();
            MyProfile myProfile = myProfileAPI.testAPI(consumer);
            TextView firstName_text = (TextView) view.findViewById(R.id.uname_TextView);
            TextView lastName_text = (TextView) view.findViewById(R.id.uage_TextView);
            TextView headline_text = (TextView) view.findViewById(R.id.uaddress_TextView);
            TextView myLink_text = (TextView) view.findViewById(R.id.uemail_TextView);
            firstName_text.setText(myProfile.getFirstName());
            lastName_text.setText(myProfile.getLastName());
            headline_text.setText(myProfile.getHeadline());
            myLink_text.setText(myProfile.getUrl());

//            TextView txtCat = (TextView) view.findViewById(R.id.txtCat);
//            String colorCat = color + " " + txtCat.getText().toString();
//            txtCat.setText(colorCat);

            return view;
        }

    }

    public static class FragmentSearch extends Fragment {

        String selected_Company;
        TextView count_text;
        TextView keyword_text;


        Fragment frag = new Fragment();


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return initView(inflater, container);
        }

        private View initView(LayoutInflater inflater, ViewGroup container) {
            View view = inflater.inflate(R.layout.search_main, container, false);
            final String[] m = {"Google","Oracle","LinkedIn","Amazon","Baidu","Microsoft","Twitter","Facebook"};
            final TextView spinnerview;
            Spinner spinner;
            ArrayAdapter<String> adapter;

            count_text = (TextView) view.findViewById(R.id.count_editText);
            keyword_text = (TextView) view.findViewById(R.id.keyword_editText);

            spinnerview = (TextView)view.findViewById(R.id.company_spinnerTest);
            spinner = (Spinner) view.findViewById(R.id.spinner);

            adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,m);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    spinnerview.setText(m[arg2]);
                    selected_Company = m[arg2];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spinner.setVisibility(View.VISIBLE);

            Button search = (Button) view.findViewById(R.id.search_Button);

            search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                NetworkUpdateAPI networkUpdateAPI = new NetworkUpdateAPI();
                String count_str = String.valueOf(count_text.getText());
                String keyword_str = String.valueOf(keyword_text.getText());
                ArrayList<NetworkUpdate> networkUpdates = networkUpdateAPI.networkSearchAPI(consumer, selected_Company, count_str, keyword_str);
                intent.putExtra("networkUpdates", networkUpdates);
                startActivity(intent);
            }
        });


            return view;
        }



    }
}