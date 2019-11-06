package com.ahnsafety.ex90exoplayerjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.res.AssetManager;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    VideoAdapter adapter;
    ArrayList<VideoItem> videoItems= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= findViewById(R.id.recycler);
        adapter= new VideoAdapter(this, videoItems);
        recyclerView.setAdapter(adapter);

        //대량의 비디오정보들을 로딩해오기!!!
        loadData();
    }

    void loadData(){

        new Thread(){
            @Override
            public void run() {

                //assets폴더에 있는 파일을 읽어오기 위해 매니져(창고관리자) 소환
                AssetManager assetManager= getAssets();

                //assets/jsons/videoUrl.json파일 일기위한 InputStream
                try {
                    InputStream is= assetManager.open("jsons/videoUrl.json");
                    InputStreamReader isr= new InputStreamReader(is);
                    BufferedReader reader= new BufferedReader(isr);

                    StringBuffer buffer= new StringBuffer();
                    String line= reader.readLine();
                    while (line!=null){
                        buffer.append(line+"\n");
                        line= reader.readLine();
                    }

                    final String jsonData= buffer.toString();

                    //JSON parsing..
                    JSONObject jsonObject= new JSONObject(jsonData);
                    JSONArray categoriesArray= jsonObject.getJSONArray("categories");
                    JSONObject object= categoriesArray.getJSONObject(0);
                    JSONArray videosArray= object.getJSONArray("videos");

                    for(int i=0; i<videosArray.length(); i++){
                        JSONObject videoData= videosArray.getJSONObject(i);

                        final String title= videoData.getString("title");
                        final String subTitle= videoData.getString("subtitle");
                        final String desc= videoData.getString("description");
                        String sources= videoData.getString("sources");
                        sources= sources.replace("[\"", "");
                        sources= sources.replace("\"]", "");
                        sources= sources.replace("\\/", "/");
                        final String videoUrl= sources;
                        final String thumb= videoData.getString("thumb");

                        //리사이클러에 보여줄 대량의 데이터(videoItems)에 추가하기!!
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                videoItems.add(new VideoItem(title, subTitle, desc, videoUrl, thumb));
                                adapter.notifyItemInserted(videoItems.size()-1);
                            }
                        });

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
