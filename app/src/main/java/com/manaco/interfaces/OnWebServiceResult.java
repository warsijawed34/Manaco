package com.manaco.interfaces;


import com.manaco.utils.Constants;

/**
 * Created by vinove on 16/8/16.
 */
public interface OnWebServiceResult {

    public void onWebServiceResult(String result, Constants.SERVICE_TYPE type,int responseCode);
}
