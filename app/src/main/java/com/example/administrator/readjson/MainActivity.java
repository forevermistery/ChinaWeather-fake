package com.example.administrator.readjson;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    TextView tv_test;
    Button btn_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_test = (TextView)findViewById(R.id.tv_test);
        btn_test = (Button)findViewById(R.id.btn_test);

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r = readWeatherinf();
                try {
                    JSONObject jobj = new JSONObject(r);
                    JSONObject weather = jobj.getJSONObject("data");
                    StringBuffer wbf = new StringBuffer();
                    wbf.append("所选城市："+weather.getString("city")+"\n");
                    wbf.append("温度："+weather.getString("wendu")+"\n");
                    wbf.append("天气提示："+weather.getString("ganmao")+"\n");

                    JSONArray jary = weather.getJSONArray("forecast");
                    for(int i=0;i<jary.length();i++){
                        JSONObject pobj = (JSONObject)jary.opt(i);
                        wbf.append("日期："+pobj.getString("date")+"\n");
                        wbf.append("高温："+pobj.getString("high")+"\n");
                        wbf.append("低温："+pobj.getString("low")+"\n");
                        wbf.append("风向："+pobj.getString("fengxiang")+"\n");
                        wbf.append("风力："+pobj.getString("fengli")+"\n");
                        wbf.append("天气："+pobj.getString("type")+"\n");

                    }
                    tv_test.setText(wbf.toString());
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });
    }
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 123:
                    showData((String)msg.obj);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private  void showData(String jData){
        tv_test.setText(jData);
        //这里我直接显示json数据，没解析。解析的方法，请参考教材或网上相应的代码

    }
    private  String readWeatherinf(){
        String winf = "";
        InputStream ins = getResources().openRawResource(R.raw.ww);
        InputStreamReader insr = new InputStreamReader(ins);
        BufferedReader br = new BufferedReader(insr);
        StringBuffer buff = new StringBuffer();
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                 buff.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        winf = buff.toString();
        return  winf;
    }
}
