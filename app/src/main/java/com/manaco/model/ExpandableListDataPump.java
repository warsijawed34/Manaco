package com.manaco.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vinove on 17/11/16.
 */

public class ExpandableListDataPump {

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("BMW");
        cricket.add("MERCEDES BENZ");
        cricket.add("MERCEDES BENZ");
        cricket.add("MERCEDES BENZ");
        cricket.add("MERCEDES BENZ");
        cricket.add("MERCEDES BENZ");

        List<String> football = new ArrayList<String>();
        football.add("MERCEDES BENZ");
        football.add("MERCEDES BENZ");
        football.add("MERCEDES BENZ");
        football.add("MERCEDES BENZ");
        football.add("MERCEDES BENZ");

        List<String> basketball = new ArrayList<String>();
        basketball.add("MERCEDES BENZ");
        basketball.add("MERCEDES BENZ");
        basketball.add("MERCEDES BENZ");
        basketball.add("MERCEDES BENZ");
        basketball.add("MERCEDES BENZ");

        expandableListDetail.put("CONSTRUCTOR", cricket);
        expandableListDetail.put("MEDIAS", football);
        expandableListDetail.put("ENERGY", basketball);
        return expandableListDetail;
    }
}
