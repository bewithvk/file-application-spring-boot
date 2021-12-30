package com.ikea.filehandling.payload;


import java.util.HashMap;
import java.util.Map;

public class Response {
    private Map<String, Integer> res;

    public Response(Map<String, Integer> wordList) {
        this.res = wordList;
    }

    public Map<String, Integer> getRes() {
        return res;
    }

    public void setRes(Map<String, Integer> res) {
        this.res = res;
    }



}
