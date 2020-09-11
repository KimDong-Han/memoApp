package kr.ac.kumoh.s20131582.memo_java;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static androidx.core.content.ContextCompat.getSystemService;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private Memo Memoinfo;
    private FloatingActionButton fbt;
    private MemoAdapter memoAdapter;
    public List<Memo> mlist = new ArrayList<>();
    public  RecyclerView rcv;
    public static MainActivity activity =null;
    public static Context mcontext;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(0, 0);
        overridePendingTransition(0, 0);
        finish();
      startActivity(new Intent(MainActivity.this, MainActivity.class));

        overridePendingTransition(0, 0);

        if (resultCode == RESULT_OK) {
//

            String title = data.getStringExtra("title");
            String date = data.getStringExtra("date");
            String content = data.getStringExtra("content");
            String img = data.getStringExtra("img");
            realm.beginTransaction();
            Memoinfo = realm.createObject(Memo.class);
            Memoinfo.setTitle(title);
            Memoinfo.setDate(date);
            Memoinfo.setContent(content);


            realm.commitTransaction();

            mlist.add(new Memo(title, date, content));

            memoAdapter = new MemoAdapter(MainActivity.this, mlist);

            rcv.setAdapter(memoAdapter);



        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        Realm.init(this);
        mcontext=this;


        realm = Realm.getDefaultInstance();
        //realm.beginTransaction();//림 시작
        //Memoinfo = realm.createObject(Memo.class);
        //Memoinfo.setTitle("title");
        //Memoinfo.setDate("date");
        //Memoinfo.setContent("content");
        RealmResults<Memo> realmResults = realm.where(Memo.class).findAllAsync().sort("date",Sort.DESCENDING);

        //realm.commitTransaction();
        rcv = findViewById(R.id.recycler_view);


        fbt = (FloatingActionButton) findViewById(R.id.fbt);
        fbt.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Add Memo", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,MemoActivity.class);
                startActivityForResult(intent,1);
                overridePendingTransition(R.anim.anim_slide_in_left,0);
            }
        });

        for (Memo memo : realmResults) {
            mlist.add(new Memo(memo.getTitle(), memo.getDate(), memo.getContent()));
            memoAdapter = new MemoAdapter(MainActivity.this, mlist);

            rcv.setAdapter(memoAdapter);

        }



    }
    public void cpoy(String cpdata){
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text",cpdata);
        clipboardManager.setPrimaryClip(clip);
    }

public void up()
{
    memoAdapter.notifyDataSetChanged();
}

}