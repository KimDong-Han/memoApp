package kr.ac.kumoh.s20131582.memo_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemoActivity extends AppCompatActivity {
    private Activity MemoActivity=this;
    private TextView tvDate;
    private FloatingActionButton fbt2;
    private EditText title;
    private EditText content;
    private String titles,contents,dates,dates2;
    private String dates32;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        tvDate = findViewById(R.id.date);
        title =(EditText)findViewById(R.id.title);
        content= (EditText)findViewById(R.id.content);

        long now1 = System.currentTimeMillis();

        Date d = new Date(now1);
        SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        tvDate.setText(""+adf.format(d));
        dates32 = adf.format(d);
        fbt2 = (FloatingActionButton) findViewById(R.id.fbt2);
        fbt2.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {



                    titles = title.getText().toString();
                    dates = tvDate.getText().toString();
                    contents = content.getText().toString();
                if(titles.getBytes().length==0&&contents.getBytes().length==0){
                    AlertDialog.Builder dig = new AlertDialog.Builder(MemoActivity);
                        dig.setMessage("메모를 입력하세요");
                    
                        dig.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                }else{
                    Intent add = new Intent(MemoActivity.this, MainActivity.class);
                    add.putExtra("title", titles);
                    add.putExtra("date", dates);
                    add.putExtra("content", contents);
                    setResult(RESULT_OK, add);

                    finish();

                }


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memo_add,menu);
        getMenuInflater().inflate(R.menu.memo_cam,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_save) {
            //Toast.makeText(getApplicationContext(), "SAVE Memo", Toast.LENGTH_SHORT).show();
            titles = title.getText().toString();
            dates = tvDate.getText().toString();
            contents = content.getText().toString();

            Intent add = new Intent(MemoActivity.this, MainActivity.class);
            add.putExtra("title", titles);
            add.putExtra("date", dates);
            add.putExtra("content", contents);
            setResult(RESULT_OK, add);
            Log.d("con", contents);

            finish();
        }else if(id == R.id.action_cam){

            Intent camm = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
            startActivity(camm);



        }
        return super.onOptionsItemSelected(item);
    }
}