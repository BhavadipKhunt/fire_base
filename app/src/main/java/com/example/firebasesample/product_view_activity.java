package com.example.firebasesample;

import static com.example.firebasesample.splash_screen_activity.editor;
import static com.example.firebasesample.splash_screen_activity.preferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class product_view_activity extends AppCompatActivity {
int i;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView hname,hemail;
    EditText productname,productprice,productdec;
    Button addbutton,updatebutton;
    ImageView himageView,productimageview;
    FirebaseDatabase database;
    DatabaseReference myRef;

String name,email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        mAuth = FirebaseAuth.getInstance();
        i=getIntent().getIntExtra("i",0);
   if (i==1)
   {
       name=preferences.getString("name","12");
       email=preferences.getString("email","adv@gmail.com");
   } else if (i==2) {
       name=preferences.getString("name","12");
       email=preferences.getString("number","adv@gmail.com");
   } else {
       name=preferences.getString("name","12");
       email=preferences.getString("email","adv@gmail.com");
   }
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigation);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View view=navigationView.getHeaderView(0);
        hname=view.findViewById(R.id.hader_name);
        hemail=view.findViewById(R.id.hader_email);
        himageView=view.findViewById(R.id.hader_image);
        hname.setText(""+name);
        hemail.setText(""+email);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.home)
                {

                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                if (item.getItemId()==R.id.userproduct)
                {

                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
                if (item.getItemId()==R.id.add)
                {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    Dialog dialog=new Dialog(product_view_activity.this);
                    dialog.setContentView(R.layout.add_products);
                    productimageview=dialog.findViewById(R.id.product_image);
                    productdec=dialog.findViewById(R.id.product_dec);
                    productname=dialog.findViewById(R.id.product_name);
                    productprice=dialog.findViewById(R.id.product_price);
                    addbutton=dialog.findViewById(R.id.add_button);

                    productimageview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CropImage.activity()
                                    .setGuidelines(CropImageView.Guidelines.ON)
                                    .start(product_view_activity.this);
                        }
                    });
                    addbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String name,price,dec,image;
                            if (productname.getText().toString().equals(""))
                            {
                                productname.setError("Field can't be empty",getResources().getDrawable(R.drawable.baseline_error_24));
                            } else if (productprice.getText().toString().equals("")) {
                                productprice.setError("Field can't be empty",getResources().getDrawable(R.drawable.baseline_error_24));
                            }
                            else if(productdec.getText().toString().equals(""))
                            {
                                productdec.setError("Field can't be empty",getResources().getDrawable(R.drawable.baseline_error_24));

                            }
                            else {
                                name = productname.getText().toString();
                                price = productprice.getText().toString();
                                dec = productdec.getText().toString();
                                Bitmap bitmap= ((BitmapDrawable)productimageview.getDrawable()).getBitmap();
                                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG,30,bos);
                                byte[] byteArray = bos.toByteArray();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                     image = Base64.getEncoder().encodeToString(byteArray);
                                }
                                database = FirebaseDatabase.getInstance();
                                myRef = database.getReference("ProductData").push();
                                String id = myRef.getKey();
                                product_data product_data=new product_data(id,name,price,dec);
                                myRef.setValue(product_data);
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog.show();
                }
                if (item.getItemId()==R.id.logout)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(product_view_activity.this);
                    builder.setTitle("Alert...!");
                    builder.setMessage("Are you sure?"+"\n" +
                            "you want to logout");
                    builder.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editor.putBoolean("login",false);
                            editor.commit();
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(product_view_activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
                return true;
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                    productimageview.setImageURI(resultUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}