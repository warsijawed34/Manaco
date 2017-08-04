package com.manaco.utils;

import android.content.Context;

import com.manaco.R;


/**
 * Created by vinove on 16/8/16.
 */
public class WebServiceApis {
    Context mContext;

    public WebServiceApis(Context context) {
        mContext = context;
    }

    public String callApi(int type) {
        if (type == 1)
            return "https://monacoapp.eu.auth0.com/dbconnections/signup";
        else
            return "http://104.131.179.118/monacoapp/public/api/signup";
    }

    public String gendersApi() {
        return mContext.getString(R.string.api)+"genders";
    }

    public String ageApi() {
        return mContext.getString(R.string.api)+"agegroups";
    }

    public String languageApi() {
        return mContext.getString(R.string.api)+"languages";
    }

    public String loginApi()
    {
        return "https://monacoapp.eu.auth0.com/oauth/ro";
    }

    public String resetPasswordApi()
    {
        return "https://monacoapp.eu.auth0.com/dbconnections/change_password";
    }

    public String homeStandsApi() {
        return mContext.getString(R.string.api) + "stands";
    }

    public String exhibitorDetailsApi(String id) {
        return mContext.getString(R.string.api) + "stands/"+id;
    }

    public String tokenInfoApi()
    {
        return "https://monacoapp.eu.auth0.com/tokeninfo";
    }

    public String getUserProfileDetailApi(String userId)
    {
        return "http://104.131.179.118/monacoapp/public/api/users/auth0/"+userId;

    }


    public String standInformationApi(String standId)
    {
        return mContext.getString(R.string.api) + "stands/"+standId+"/infos";


    }

    public String standEventsApi(String standId)
    {
        return mContext.getString(R.string.api) + "stands/"+standId+"/events";

    }

    public String eventUrlApi() {
        return mContext.getString(R.string.api) + "events";
    }

    public String eventViewApi(String eventId) {
        return mContext.getString(R.string.api) + "events/"+eventId;
    }

    public String exhibithorsDetailsFollowApi(String userId,String standsId)
    {
        return "http://104.131.179.118/monacoapp/public/api/users/"+userId+"/stands/"+standsId+"/interested";
    }

    public String exhibithorsDetailsUnFollowApi(String userId,String standsId)
    {
        return "http://104.131.179.118/monacoapp/public/api/users/"+userId+"/stands/"+standsId+"/notinterested";
    }

    public String servicesApi() {
        return mContext.getString(R.string.api) + "services";
    }
    public String serviceViewApi(String serviceTypeId) {
        return mContext.getString(R.string.api) + "services/"+serviceTypeId;
    }

    public String serviceTypeViewApi() {
        return mContext.getString(R.string.api) + "servicetypes";
    }

    public String standFavInterested(String userId)
    {
        return mContext.getString(R.string.api)+"users/"+userId+"/stands/interested";


    }
    public String eventsFavInterested(String userId)
    {
        return mContext.getString(R.string.api)+"users/"+userId+"/events/interested";


    }

    public String checkFollowUnfollow(String userId, String standsId)
    {
        return mContext.getString(R.string.api)+"stands/"+standsId+"/users/"+userId;


    }

}
