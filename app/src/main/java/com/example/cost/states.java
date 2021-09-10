package com.example.cost;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import android.os.Bundle;
import android.widget.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@RequiresApi(api = Build.VERSION_CODES.O)
public class states extends AppCompatActivity{
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    Map<String,String> st = new HashMap<String,String>();
    String text;
    String Relstate;
    String RelTotal;
    String ctext;
    Spinner stSpinner;
    String relDel;

    String ConfirmedTillNow;
    String RecoveredTillNow;
    String DeathTillNow;
    String TestedTillNow;

    TextView TotConfirm;
    TextView TotTest;
    TextView TotRecov;
    TextView TotDeath;
    TextView RecovRate;
    TextView PosRate;
    TextView DeathRate;
    TextView Deltaa;

    private static final String URL="https://api.covid19india.org/v3/data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();

        setContentView(R.layout.activity_states);

        st.put("Andhra Pradesh","AP");
        st.put("Arunachal Pradesh","AR");
        st.put("Assam","AS");
        st.put("Bihar","BR");
        st.put("Chhattisgarh","CH");
        st.put("Gujarat","GJ");
        st.put("Goa","GA");
        st.put("Haryana","HR");
        st.put("Himachal Pradesh","HP");
        st.put("Jharkhand","JH");
        st.put("Karnataka","KA");
        st.put("Kerala","KL");
        st.put("Madhya Pradesh","MP");
        st.put("Maharashtra","MH");
        st.put("Manipur","MN");
        st.put("Meghalaya","ML");
        st.put("Mizoram","MZ");
        st.put("Nagaland","NL");
        st.put("Odisha","OR");
        st.put("Punjab","PB");
        st.put("Rajasthan","RJ");
        st.put("Sikkim","SK");
        st.put("Tamil Nadu","TN");
        st.put("Telangana","TG");
        st.put("Tripura","TR");
        st.put("Uttar Pradesh","UP");
        st.put("Uttarakhand","UT");
        st.put("West Bengal","WB");
        st.put("Andaman and Nicobar Islands","AN");
        st.put("Delhi","DL");
        st.put("Jammu and Kashmir","JK");
        st.put("Ladakh","LA");
        st.put("Lakshadweep","LD");
        st.put("Puducherry","PY");

        stSpinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<>();
        list.add("Andhra Pradesh");
        list.add("Karnataka");
        list.add("Telangana");
        list.add("Uttarakhand");
        list.add("Tamil Nadu");
        list.add("Maharashtra");
        list.add("Rajasthan");
        list.add("Delhi");
        list.add("Kerala");
        list.add("Uttar Pradesh");
        list.add("Bihar");
        list.add("West Bengal");
        list.add("Jammu and Kashmir");
        list.add("Punjab");
        list.add("Madhya Pradesh");
        list.add("Odisha");
        list.add("Gujarat");
        list.add("Haryana");



        TotConfirm = findViewById(R.id.TotConfirm);
        TotRecov = findViewById(R.id.TotRecovered);
        TotTest = findViewById(R.id.TotTested);
        TotDeath = findViewById(R.id.TotDeath);
        RecovRate = findViewById(R.id.RecovRate);
        PosRate = findViewById(R.id.PosRate);
        DeathRate = findViewById(R.id.DeathRate);
        Deltaa = findViewById(R.id.delta);


        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stSpinner.setAdapter(arrayAdapter);
        stSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                text= adapterView.getItemAtPosition(i).toString();
                ctext= st.get(text);
                GetRel(ctext);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void GetRel(final String StateCode){



        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Relstate = jsonObject.getString(StateCode);
                    JSONObject st = new JSONObject(Relstate);
                    RelTotal = st.getString("total");
                    JSONObject confirmed = new JSONObject(RelTotal);
                    ConfirmedTillNow = confirmed.getString("confirmed");
                    RecoveredTillNow = confirmed.getString("recovered");
                    DeathTillNow = confirmed.getString("deceased");
                    TestedTillNow = confirmed.getString("tested");

                    double Conf = Integer.parseInt(ConfirmedTillNow);
                    double recov = Integer.parseInt(RecoveredTillNow);
                    double deat = Integer.parseInt(DeathTillNow);
                    double test = Integer.parseInt(TestedTillNow);

                    double recovRate = (double) (recov/Conf)*100;
                    double deatRate = (double)(deat/Conf)*100;
                    double posRate = (double)(Conf/test)*100;


                    Log.i("state",Relstate);
                    Log.i("Total",RelTotal);

                    TotConfirm.setText(ConfirmedTillNow);
                    TotRecov.setText(RecoveredTillNow);
                    TotTest.setText(TestedTillNow);
                    TotDeath.setText(DeathTillNow);

                    RecovRate.setText(String.valueOf(df2.format(recovRate))+"%");
                    DeathRate.setText(String.valueOf(df2.format(deatRate))+"%");
                    PosRate.setText(String.valueOf(df2.format(posRate))+"%");
                    TotDeath.setText(DeathTillNow);


                } catch (JSONException e) {
                    e.printStackTrace();
                    RelTotal="Error";
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("err","errorr");

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }
}
