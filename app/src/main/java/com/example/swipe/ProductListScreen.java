package com.example.swipe;



import androidx.appcompat.app.AppCompatActivity;
import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import com.example.swipe.Adapter.ProductListAdapter;
import com.example.swipe.Models.Product;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductListScreen extends AppCompatActivity {
    GridView productview;
    SearchView editsearch;
    ArrayList<Product> lists;
    ProductListAdapter adapter;
    ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_screen);
        productview = findViewById(R.id.idGVcourses);
        progressBar=findViewById(R.id.id6);
         lists = new ArrayList<Product>();
         readdata();
        editsearch = (SearchView) findViewById(R.id.simpleSearchView);
        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String text = s;
                adapter.filter(text);
                return false;
            }
        });
        // register the extended floating action Button
        final ExtendedFloatingActionButton extendedFloatingActionButton = findViewById(R.id.extFloatingActionButton);
        extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it =new Intent(ProductListScreen.this,AddProductScreen.class);
                startActivity(it);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Toast.makeText(ProductListScreen.this,"Server Error Try Again1",Toast.LENGTH_LONG).show();
            productview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY + 12 && extendedFloatingActionButton.isExtended()) {
                        extendedFloatingActionButton.shrink();
                    }

                    // the delay of the extension of the FAB is set for 12 items
                    if (scrollY < oldScrollY - 12 && !extendedFloatingActionButton.isExtended()) {
                        extendedFloatingActionButton.extend();
                    }

                    // if the nestedScrollView is at the first item of the list then the
                    // extended floating action should be in extended state
                    if (scrollY == 0) {
                        extendedFloatingActionButton.extend();
                    }
                }
            });
        }


    }

    private void readdata() {

        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://app.getswipe.in/")
                .addConverterFactory(ScalarsConverterFactory.create()).build();
        Api mainInerface=retrofit.create(Api.class);
        retrofit2.Call<String> call=mainInerface.STRING_CALL();
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                if(response.isSuccessful() && response.body()!=null){
                    try {
                        JSONArray jsonObject=new JSONArray(response.body());
                        Log.d(TAG, "onResponse: "+jsonObject.length());
                       for(int i=0;i<jsonObject.length();i++){
                           JSONObject js=jsonObject.getJSONObject(i);
                           //Log.d(TAG, "onResponse: "+js.getString("product_name"));
//                           JSONObject js=jsonObject.getJSONObject(i);
                           Product product=new Product(js.getDouble("price"),js.getString("image"),js.getString("product_name"),js.getString("product_type"),js.getDouble("tax"));
                           lists.add(product);
                       }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                adapter = new ProductListAdapter(ProductListScreen.this, lists);
                                productview.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            }
                        }, 3000);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(ProductListScreen.this,"Server Error Try Again1",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {
            }
        });

    }
    }