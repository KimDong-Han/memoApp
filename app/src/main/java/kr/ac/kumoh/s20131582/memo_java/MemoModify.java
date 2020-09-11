package kr.ac.kumoh.s20131582.memo_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;

import static kr.ac.kumoh.s20131582.memo_java.MainActivity.activity;

public class MemoModify extends AppCompatActivity {
    String titles, dates, contents,first_con,first_title;
    String last_con, last_title;
    int itemnums;
    TextView date;
    EditText mtitle, mcontent;

    Realm realm;
    public static Activity avt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_modify);

        Intent intent = getIntent();
        titles = intent.getStringExtra("title");
        dates = intent.getStringExtra("date");
        contents = intent.getStringExtra("content");
        itemnums = intent.getIntExtra("itemnum",1);

        date = findViewById(R.id.date);
        mtitle = findViewById(R.id.title);
        mcontent = findViewById(R.id.content);

        date.setText(dates);
        mtitle.setText(titles);
        mcontent.setText(contents);

        first_con = mcontent.getText().toString();
        first_title = mtitle.getText().toString();



    }

    @Override
    public void onBackPressed() {

        last_con = mcontent.getText().toString();
        last_title = mtitle.getText().toString();
        if(mcontent.getText().toString().equals(first_con)&&mtitle.getText().toString().equals(first_title))
        {
           finish();
        }else{
            AlertDialog.Builder dlog = new AlertDialog.Builder(this);

            dlog.setMessage("변경된 내용을 저장하시겠습니까?");

            dlog.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    modi();

                }
            });

            dlog.setNegativeButton("저장안함", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dlog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memo_su,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id ==R.id.action_modify){
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    final Memo result =
                            realm.where(Memo.class).equalTo("title",titles).findFirst();
                    result.setTitle(mtitle.getText().toString());
                    result.setDate(date.getText().toString());
                    result.setContent(mcontent.getText().toString());



                }
            });
            realm.close();
            //((MainActivity)MainActivity.mcontext).up();
           delact();
           Intent mod = new Intent(MemoModify.this,MainActivity.class);
            startActivityForResult(mod,1);
            finish();


            //overridePendingTransition(R.anim.anim_slide_out_bottom,R.anim.anim_slide_in_top);
        }else if(id ==R.id.action_Del)
        {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    final Memo results =
                            realm.where(Memo.class).equalTo("title",titles).findFirst();
                    if(results.isValid()){
                        results.deleteFromRealm();
                    }
                    //Toast.makeText(getApplicationContext(),"삭제 완료",Toast.LENGTH_SHORT).show();
                }
            });
            realm.close();
            delact();
            Intent mod = new Intent(MemoModify.this,MainActivity.class);
            startActivityForResult(mod,1);
            finish();
            // overridePendingTransition(R.anim.anim_slide_out_bottom,R.anim.anim_slide_in_bottom);
        }


        return super.onOptionsItemSelected(item);
    }
    public void delact(){
        if(activity!=null){//중복호출방지//메인엑티비티에 선언후 사용해야함
            activity = (MainActivity) activity;
            activity.finish();


        }
    }

    public void modi(){

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final Memo result =
                        realm.where(Memo.class).equalTo("title",titles).findFirst();
                result.setTitle(mtitle.getText().toString());
                result.setDate(date.getText().toString());
                result.setContent(mcontent.getText().toString());
                //Toast.makeText(getApplicationContext(), " 수정완료", Toast.LENGTH_SHORT).show();


            }
        });
        realm.close();

        delact();
        Intent mod = new Intent(MemoModify.this,MainActivity.class);
        startActivityForResult(mod,1);
        finish();
    }

}