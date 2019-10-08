package tejsempire.com.androideatit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import tejsempire.com.androideatit.Common.Common;
import tejsempire.com.androideatit.Model.User;

public class SignIN extends AppCompatActivity {

    EditText edtPhone,edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword=(MaterialEditText)findViewById(R.id.edtPassword);
        edtPhone=(MaterialEditText)findViewById(R.id.edtPhone);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);

        //initilize fire
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog=new ProgressDialog(SignIN.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //cheak if User exist in database
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            //get User information
                            mDialog.dismiss();

                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            user.setPhone(edtPhone.getText().toString()); //set phone
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                {
                                    Intent homeIntent=new Intent(SignIN.this,Home.class);
                                    Common.currentUser=user;
                                    startActivity(homeIntent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(SignIN.this, "Wrong Password !!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            mDialog.dismiss();
                            Toast.makeText(SignIN.this,"User not Exist in Database",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
