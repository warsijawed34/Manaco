

package com.manaco.utils;
import android.content.Context;
import android.os.AsyncTask;

import com.manaco.R;
import com.manaco.interfaces.OnWebServiceResult;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

import dmax.dialog.SpotsDialog;


public class CallWebService extends AsyncTask<String, String, String> {

    String requestApi;
    Context mContext;
    boolean showProcessing;
    HttpURLConnection urlConnection;
    Constants.SERVICE_TYPE mType;
    OnWebServiceResult webServiceResult;
    SpotsDialog prog;
    boolean value = true;
    String jsonParam;
    String requsetType;
    String header="";
    int responseCode;

    /**
     * Initizalize web address calling object
     *
     * @param mContext
     * @param url
     * @param jsonParam
     * @param type
     * @param webResultInterface
     */
    public CallWebService(Context mContext, String url, String jsonParam, String requsetType, Constants.SERVICE_TYPE type,
                          OnWebServiceResult webResultInterface, String header) {
        this.mContext = mContext;
        this.requestApi = url;
        this.mType = type;
        this.webServiceResult = webResultInterface;
        this.jsonParam = jsonParam;
        this.requsetType = requsetType;
        this.header=header;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (value) {
            prog = new SpotsDialog(mContext, R.style.Custom);
//            prog.setMessage(mContext.getResources().getString(R.string.loading));
/*            prog.setMessage("Logging in. Please wait.");
            prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));*/
            prog.setCancelable(false);
            prog.show();
        }

    }

    @Override
    protected String doInBackground(String... params) {

        if (requsetType.equalsIgnoreCase("POST"))
            return postRequest(requestApi, jsonParam);
        else
            return getRequest(requestApi);

    }

    public String getRequest(String uri) {
        String result = null;
        try {
            urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));

            // optional default is GET
            urlConnection.setRequestMethod("GET");

            //add request header
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

            if (!header.isEmpty()) {
                urlConnection.addRequestProperty("Authorization", header);
                urlConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            }

            responseCode = urlConnection.getResponseCode();
            System.out.println("Response Code : " + responseCode);


            BufferedReader bufferedReader;
            if(responseCode >= 400) {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream(), "UTF-8"));
            }
            else {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();

            //print result
            result = response.toString();
            System.out.println(response.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    public String postRequest(String uri, String json) {
        String data = json;
        String result = null;
        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
//            urlConnection.setRequestProperty("Accept", "application/json");
//            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            urlConnection.setRequestProperty("Accept","*/*");
            urlConnection.setRequestMethod("POST");//RequestType
            if (!header.isEmpty()) {
                urlConnection.setRequestProperty("Authorization", header);
//                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            }

            urlConnection.connect();

            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(data);
            writer.close();
            outputStream.close();
            //Read

            responseCode = urlConnection.getResponseCode();
            BufferedReader bufferedReader;
            if(responseCode >= 400) {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream(), "UTF-8"));
            }
            else {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            }

            String line = null;
            StringBuilder response = new StringBuilder();
            System.out.println("response: " + response.toString());
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }

            bufferedReader.close();
            result = response.toString();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;

    }

    /**
     * Pass Hashtable of key value pair we need to post
     *
     * @param params
     * @return
     */
    private String getPostParamString(Hashtable<String, String> params) {
        if (params.size() == 0)
            return "";
        StringBuffer buf = new StringBuffer();
        Enumeration<String> keys = params.keys();
        while (keys.hasMoreElements()) {
            buf.append(buf.length() == 0 ? "" : "&");
            String key = keys.nextElement();
            buf.append(key).append("=").append(params.get(key));
        }
        System.out.println("aaaaa: " + buf.toString());
        return buf.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            if (value) {
                if (prog.isShowing()) {
                    prog.dismiss();
                }
            }
            if (!(result == null))
                webServiceResult.onWebServiceResult(result, mType,responseCode);
        } catch (Exception e) {
            e.printStackTrace();
            if (prog.isShowing()) {
                prog.dismiss();
            }
        }
    }
}

