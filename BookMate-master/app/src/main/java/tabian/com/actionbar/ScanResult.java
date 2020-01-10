package tabian.com.actionbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class ScanResult extends AppCompatActivity {

    private TextView book_title;
    private TextView book_author;
    private String cover_art;
    private ImageView book_cover;
    private RequestQueue mQueue;

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        book_title = findViewById(R.id.book_title);
        book_author = findViewById(R.id.author);
        book_cover = findViewById(R.id.book_Cover);

        mQueue = Volley.newRequestQueue(this);

        jsonParse();



    }

    public void jsonParse() {

        String url = "https://www.googleapis.com/books/v1/volumes?q=9780545035170&key=AIzaSyDG6IF6-n0EDhvSfac4OYNqtU520xQUqzQ";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("items");


                            /*for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);

                                String firstName = employee.getString("firstname");
                                int age = employee.getInt("age");
                                String mail = employee.getString("mail");

                                textViewResult.append(firstName + ", " + String.valueOf(age) + ", " + mail + "\n\n");
                            }*/

                            book_title.setText(jsonArray.getJSONObject(0).getJSONObject("volumeInfo").getString("title"));
                            book_author.setText(jsonArray.getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("authors").getString(0));
                            cover_art = jsonArray.getJSONObject(0).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");

                            Glide.with(book_cover).load(cover_art).into(book_cover);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }


}




