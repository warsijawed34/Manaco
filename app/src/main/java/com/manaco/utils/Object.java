package com.manaco.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinove on 15/11/16.
 */

public class Object {

    public String title; // use getters and setters instead
    public List<Object> children; // same as above

    public Object() {
        children = new ArrayList<Object>();
    }
}
