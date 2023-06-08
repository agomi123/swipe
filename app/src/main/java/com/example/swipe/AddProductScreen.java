package com.example.swipe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.swipe.Models.Product;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddProductScreen extends AppCompatActivity {
   AutoCompleteTextView type;
   EditText name,price,tax;
   MaterialButton add;
   ProgressBar progressBar;
    String[] languages = { "a","b","c","d","e","aa","aaa","bbb","ccc","dd","eee"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_screen);
        type=findViewById(R.id.producttype);
        name=findViewById(R.id.productname);
        price=findViewById(R.id.productprice);
        tax=findViewById(R.id.productrate);
        add=findViewById(R.id.addproduct);
        progressBar=findViewById(R.id.idLoadingPB);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, languages);
        type.setThreshold(1);
        type.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a1=type.getText().toString();
                String a2=name.getText().toString();
                String a3=price.getText().toString();
                String a4=tax.getText().toString();
                if(a1.isEmpty() || a2.isEmpty() || a3.isEmpty() || a4.isEmpty()){
                    Toast.makeText(AddProductScreen.this,"All field required",Toast.LENGTH_SHORT).show();
                    return;
                }
                addProduct(a1,a2,a4,a3);

            }
        });
    }
    public void addProduct(String type,String name,String tax,String price){
            progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://app.getswipe.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api retrofitAPI = retrofit.create(Api.class);
        Product modal = new Product(Double.parseDouble(price),"",name,type,Double.parseDouble(tax));
        Call<Product> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
              Toast.makeText(AddProductScreen.this, "Data added to API", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                finish();
//                jobEdt.setText("");
//                nameEdt.setText("");
//                DataModal responseFromAPI = response.body();
//                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Job : " + responseFromAPI.getJob();
//                responseTV.setText(responseString);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(AddProductScreen.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                //esponseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}
