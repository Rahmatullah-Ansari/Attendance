package com.android.attendance.Activities;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.attendance.Holder.Helper;
import com.android.attendance.R;
import com.android.attendance.Security;
import com.android.attendance.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Login extends AppCompatActivity {
ActivityLoginBinding binding;
ImageView teacher,student;
FirebaseAuth auth;
RadioGroup radioGroup;
RadioButton button;
String email,password,name,type,category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        teacher=binding.imageView;
        student= binding.imageView1;
        teacher.setOnClickListener(view -> showDialog("teacher"));
        student.setOnClickListener(view -> showDialog("student"));
        radioGroup=binding.radioGroup;
        binding.next.setOnClickListener(v->signUp());
    }

    private void signUp() {
        int id=radioGroup.getCheckedRadioButtonId();
        button=findViewById(id);
        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("Sign up");
        dialog.setMessage("Signing under progress.....");
        dialog.show();
        if (button != null){
            if (button.isChecked()){
                category=button.getText().toString();
                if (email != null && password != null && name != null) {
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(Login.this, "sign in successful", Toast.LENGTH_SHORT).show();
                            try {
                                UpdateData();
                            } catch (Exception e) {
                                Log.e("error:",e.getLocalizedMessage());
                            }
                        }else{
                            dialog.dismiss();
                            Toast.makeText(Login.this, "Login:sign in failed:"+ Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> {
                        dialog.dismiss();
                        Toast.makeText(Login.this, "Login:sign in failed:"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();});
                }else{
                    dialog.dismiss();
                }
                }else{
                dialog.dismiss();
            }
            }else{
            dialog.dismiss();
            Toast.makeText(Login.this, "Login:please select category", Toast.LENGTH_SHORT).show();
            }

    }

    private void UpdateData() throws NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String id,p,n,i;
        id=Objects.requireNonNull(auth.getCurrentUser()).getUid();
        //i= Security.Encrypt(id);
        //p= Security.Encrypt(password);
        //n= Security.Encrypt(name);
        Helper helper=new Helper(name,id,password);
        helper.setCategory(category);
        helper.setType(type);
        database.getReference("Users").child(id).setValue(helper)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(Login.this, "Data Updated!", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(() -> {
                            Intent intent=new Intent(Login.this,HomeScreen.class);
                            intent.putExtra("category", button.getText());
                            startActivity(intent);
                            finish();
                        }, 800);
                    }else{
                        Toast.makeText(Login.this, "Login:Failed due to :"+ Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(Login.this, "Login:Failed due to :"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
    }

    private void showDialog(String text) {
        type=text;
        @SuppressLint ("InflateParams") View view= LayoutInflater.from(this).inflate(R.layout.inputlayout,null);
        AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(view)
                .setTitle("Credential Input").setPositiveButton("Done", (dialogInterface, i) -> {
                    EditText editText, editText1,editText2;
                    editText=view.findViewById(R.id.email);
                    editText1=view.findViewById(R.id.password);
                    editText2=view.findViewById(R.id.name);
                    email=editText.getText().toString();
                    password=editText1.getText().toString();
                    name=editText2.getText().toString();
                    if (password.length() > 0 && email.length() > 0 && name.length() >0){
                        if (FirebaseAuth.getInstance().getCurrentUser() != null){
                            signUp();
                        }else{
                            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    Toast.makeText(Login.this, "User created successfully", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(Login.this, "Login:failed to create user:"+ Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(e -> Toast.makeText(Login.this, "Login:failed to load due to:"+ e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
                        }
                    }else {
                        Toast.makeText(Login.this,"Login:All field are required!", Toast.LENGTH_SHORT).show();
                    }
                    dialogInterface.dismiss();
                }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();
    }

}