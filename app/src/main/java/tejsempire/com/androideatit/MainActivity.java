package tejsempire.com.androideatit;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn,btnSignUp;
    TextView txtSlogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        btnSignUp=(Button)findViewById(R.id.btnSignUp);
        txtSlogan=(TextView)findViewById(R.id.textSlogan);

        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        txtSlogan.setTypeface(face);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin=new Intent(MainActivity.this,SignUp.class);
                startActivity(signin);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin=new Intent(MainActivity.this,SignIN.class);
                startActivity(signin);
            }
        });

    }
}
