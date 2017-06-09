package sourabh.newsall.activity;

import android.content.Context;

import android.net.Uri;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sourabh.newsall.R;
import sourabh.newsall.adapter.SourceAdapter;
import sourabh.newsall.app.AppConfig;
import sourabh.newsall.data.LanguageData;
import sourabh.newsall.data.SourceData;
import sourabh.newsall.data.SourcesData;
import sourabh.newsall.helper.SessionManager;

import static sourabh.newsall.R.id.toolbar;
import static sourabh.newsall.app.AppConfig.ARG_PARAM_SOURCE_DATA;

public class HomeActivity extends AppCompatActivity implements SourceFragment.OnFragmentInteractionListener{



    private List<SourceData> albumList;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpagertab)
    SmartTabLayout viewPagerTab;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    SourcesData sourceDataList;

    SessionManager sessionManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);



        context = getApplicationContext();



        sessionManager = new SessionManager(context);

        sourceDataList = (SourcesData) getIntent().getSerializableExtra(ARG_PARAM_SOURCE_DATA);


//        setupDrawer(savedInstanceState);
        setFragments(AppConfig.KEY_TITLE_LATEST,AppConfig.KEY_TITLE_TOP50);





    }


    void   setFragments(String first_fragment_title, String second_fragment_title){



        List<LanguageData> selectedLanguages = sessionManager.getSelectedLanguages();
        FragmentPagerItems.Creator viewPagers = FragmentPagerItems.with(this);


        for(int i = 0; i<selectedLanguages.size();i++){

            Bundle argSources = new Bundle();
            List<SourceData> argSourceList = new ArrayList<>();

            for (SourceData sourceData: sourceDataList.getSources())
            {

                if(selectedLanguages.get(i).getId_language().equals(sourceData.getId_language())){
                    argSourceList.add(sourceData);
                }
            }
            argSources.putSerializable(ARG_PARAM_SOURCE_DATA, (Serializable) argSourceList);
            viewPagers.add(selectedLanguages.get(i).getLanguage_title(),SourceFragment.class,argSources);
        }


//        FragmentPagerItems viewPagers = FragmentPagerItems.with(this)
//                                            .add(selectedLanguages.get(0).getLanguage_title(), SourceFragment.class)
//                                            .create();


//        argsLatest.putSerializable(AppConfig.ARG_PARAM_SOURCE_DATA,sourceDataList);



        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), viewPagers.create());

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
