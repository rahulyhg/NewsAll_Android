package sourabh.newsall.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import sourabh.newsall.R;
import sourabh.newsall.app.AppConfig;
import sourabh.newsall.app.CustomRequest;
import sourabh.newsall.data.LanguageData;
import sourabh.newsall.data.SourceData;
import sourabh.newsall.data.SourcesData;
import sourabh.newsall.helper.CommonUtilities;
import sourabh.newsall.helper.JsonSeparator;
import sourabh.newsall.helper.SessionManager;

import static android.provider.Contacts.SettingsColumns.KEY;
import static sourabh.newsall.app.AppConfig.KEY_LANGUAGES;

public class SplashScreenActivity extends BaseActivity {

    SessionManager sessionManager;
    Context context;

//    int themeColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        context = this;
        sessionManager = new SessionManager(context);
//        themeColor = sessionManager.getThemeColor();


        getSupportActionBar().hide();
        if(!sessionManager.checkFirstRun() || !sessionManager.getIsLanguageSelcted())
        {
            getLanguages();
        }else{
            getDashBoard();
        }
    }

    public void getLanguages()
    {
        Volley.newRequestQueue(this).add(new CustomRequest(this,this,
                false, Request.Method.GET,
                AppConfig.URL_GET_LANUGAGES,
                CommonUtilities.buildBlankParams(), CommonUtilities.buildGuestHeaders(),


                new com.android.volley.Response.Listener() {

                    @Override
                    public void onResponse(Object response) {
                        JSONObject jsonObject = (JSONObject) response;
                        JsonSeparator js= new JsonSeparator(context,jsonObject);

                        try {
                            if(js.isError()){

                                Toast.makeText(context,js.getMessage().toString(),Toast.LENGTH_LONG).show();
                            }else{

                                //JSONArray categories = js.getData().getJSONArray(Const.KEY_CATEGORIES);
                                parseLangauageJson(js.getData());

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                }, new com.android.volley.Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();

            }
        }));
    }


    public void getDashBoard()
    {

//        List<Integer>selectedLanguageIDS = new ArrayList<>();
//
//        for (LanguageData languageData: sessionManager.getSelectedLanguages()) {
//
//                selectedLanguageIDS.add(languageData.getId_language());
//        }

        String selectedLanguageIDs = new Gson().toJson(sessionManager.getSelectedLanguages());

        HashMap<String, String> params = new HashMap<String, String>();
        params.put(KEY_LANGUAGES,selectedLanguageIDs);

        Volley.newRequestQueue(this).add(new CustomRequest(this,this,
                true, Request.Method.POST,
                AppConfig.URL_GET_DASHBOARD,
                params, CommonUtilities.buildGuestHeaders(),


                new com.android.volley.Response.Listener() {

                    @Override
                    public void onResponse(Object response) {
                        JSONObject jsonObject = (JSONObject) response;
                        JsonSeparator js= new JsonSeparator(context,jsonObject);

                        try {
                            if(js.isError()){

                                Toast.makeText(context,js.getMessage().toString(),Toast.LENGTH_LONG).show();
                            }else{

                                //JSONArray categories = js.getData().getJSONArray(Const.KEY_CATEGORIES);
                                parseDashBoardJson(js.getData());

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                }, new com.android.volley.Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();

            }
        }));
    }

    public void parseDashBoardJson(JSONObject jsonObject){

        gotoHome(CommonUtilities.getObjectFromJson(jsonObject, SourcesData.class));

    }



    public void parseLangauageJson(JSONObject jsonObject){


        sessionManager.setFirstRun();
        sessionManager.setLangauges(jsonObject);
        showChooseLanguageDalog();

    }

    void showChooseLanguageDalog()
    {

        final List<LanguageData> languageDataList = (sessionManager.getLangauges());

//        new MaterialDialog.Builder(this)
//                .title("Select Languages")
//                .cancelable(false)
//                .items(CommonUtilities.convertLangaugeListToLanguageArray
//                        (languageDataList))
//                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
//                    @Override
//                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
//
//                        sessionManager.setSelectedLanguageId(languageDataList.get(which).getId_language());
//
////                        gotoHome(null);
//                        return true;
//                    }
//                })
//                .positiveText("Done")
//                .show();


        new MaterialDialog.Builder(this)
                .title("Select Languages")
                .items(CommonUtilities.convertLangaugeListToLanguageArray
                        (languageDataList))
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        /**
                         * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected check box to actually be selected
                         * (or the newly unselected check box to be unchecked).
                         * See the limited multi choice dialog example in the sample project for details.
                         **/
                        List<LanguageData> languagesSelected = new ArrayList<>();

                        for (int index: which) {
                            languagesSelected.add(languageDataList.get(index));
                        }

                        sessionManager.setSelectedLanguages(languagesSelected);

                        return true;
                    }
                })
                .positiveText("Done")
                .show();


    }


    void gotoHome(SourcesData sourceData)
    {

        if(sourceData == null){
            startActivity(new Intent(SplashScreenActivity.this,HomeActivity.class));
            finish();
        }else{
            startActivity(new Intent(SplashScreenActivity.this,HomeActivity.class)
            .putExtra(AppConfig.ARG_PARAM_SOURCE_DATA,sourceData));
            finish();
        }

    }



}
