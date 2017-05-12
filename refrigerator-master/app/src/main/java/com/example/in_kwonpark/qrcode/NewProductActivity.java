package com.example.in_kwonpark.qrcode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class NewProductActivity extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;



    JSONParser jsonParser = new JSONParser();
    EditText sellername;
    EditText phonenumber;
    EditText buyprice;
    EditText sellprice;
  //  EditText inputEmail;
    Button btnCreateProduct;

    // url to create new product
    private static String url_create_product = "http://218.150.181.173/re/create_product.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        // Edit Text
        sellername = (EditText) findViewById(R.id.sellername); // 판매자
        phonenumber = (EditText) findViewById(R.id.phonenumber); // 폰번호
        buyprice = (EditText) findViewById(R.id.buyprice);      // 구매가
        sellprice = (EditText) findViewById(R.id.sellprice); // 판매가
        //   inputTell = (EditText) findViewById(R.id.inputTell);
        //   inputEmail = (EditText) findViewById(R.id.inputEmail);


        // Create button
        btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = sellername.getText().toString();
                String price = phonenumber.getText().toString();
                String description = buyprice.getText().toString();
                String price2 = sellprice.getText().toString();
                //       String email = inputEmail.getText().toString();
                // creating new product in background thread
                //       new CreateNewProduct().execute(name, price, description, tell, email);

                new CreateNewProduct().execute(name, price, description, price2);

                // new EditProductActivity.SaveProductDetails().execute(name, price, description, tell, email);

                /*String name = inputName.getText().toString();
                String price = inputPrice.getText().toString();
                String description = inputDesc.getText().toString();
                String tell = inputTell.getText().toString();
                String email = inputEmail.getText().toString();
                new EditProductActivity.SaveProductDetails().execute(name, price, description, tell, email);*/
            }
        });
    }

    /**
     * Background Async Task to Create new product
     * */
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewProductActivity.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String sellername = args[0],
                    phonenumber = args[1],
                    buyprice = args[2],
                    sellprice = args[3];
            //        email = args[4];


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sellername", sellername));
            params.add(new BasicNameValuePair("phonenumber", phonenumber));
            params.add(new BasicNameValuePair("buyprice", buyprice));
            params.add(new BasicNameValuePair("sellprice", sellprice));
            //  params.add(new BasicNameValuePair("email", email));
            //   params.add(new BasicNameValuePair("email", email));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product, "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}