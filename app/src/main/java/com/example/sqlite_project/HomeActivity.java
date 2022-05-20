package com.example.sqlite_project;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity {
    ImageView ivProfile, ivProduct, ivCategory, ivStock, ivCustomer, ivReports, ivBilling;
    TextView tvBranchNo, tvBranchAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ivProfile= findViewById(R.id.ivProfile);
        ivProduct= findViewById(R.id.ivProduct);
        ivCategory= findViewById(R.id.ivCategory);
        ivStock= findViewById(R.id.ivPurchase);
        ivCustomer= findViewById(R.id.ivCustomer);
        ivReports= findViewById(R.id.ivReports);
        ivBilling= findViewById(R.id.ivBilling);
        tvBranchNo= findViewById(R.id.tvBranchNo);
        tvBranchAddress= findViewById(R.id.tvBranchAddress);

        ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BabyActivity.class);
                startActivity(intent);
            }
        });
        ivCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "category", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, ProblemsActivity.class));
            }
        });
        ivStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });
        ivCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });
        ivBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String  shareMessage = "https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID +"\n\n";
                String sharebody = shareMessage;
                String sharesubject = "Hey"+"\n\n"+sharebody+"\n";
                intent.putExtra(Intent.EXTRA_TEXT, sharesubject);
                startActivity(Intent.createChooser(intent, "share out Application"));
            }
        });
        ivReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });
    }
}
