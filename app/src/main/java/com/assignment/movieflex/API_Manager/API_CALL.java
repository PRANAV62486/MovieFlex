package com.assignment.movieflex.API_Manager;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*This is a general class which can be used to make http request using volley library
* Required parameters are : context and url
* Return value : response received from api call(interface is used to handle return value)*/

public class API_CALL {
    Context context;

    public API_CALL(Context context) {
        this.context = context;
    }

    //Defining call back for asynchronous task
    public interface callback_result {
        void onResponse(List<Model> list);

        void onError(String error);
    }

    public void getData(String url, callback_result callback) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Model> list = new ArrayList<>();

                    JSONArray array = response.getJSONArray("results");
                    JSONObject obj;

                    for (int i = 0; i < array.length(); i++) {
                        Model model = new Model();
                        obj = (JSONObject) array.get(i);

                        model.setBackdrop_path(obj.getString("backdrop_path"));
                        model.setOverview(obj.getString("overview"));
                        model.setPoster_path(obj.getString("poster_path"));
                        model.setRelease_date(obj.getString("release_date"));
                        model.setTitle(obj.getString("title"));
                        model.setVote_average(obj.getString("vote_average"));

                        list.add(model);
                    }

                    //this line is required to handle the callback of async method
                    callback.onResponse(list);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> callback.onError("Error " + error));

        MySingleton.getInstance(context).addToRequestQueue(request);

    }

}
