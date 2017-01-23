package com.yobin.stee.greendaodemo02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import greendao.Users;

public class MainActivity extends AppCompatActivity implements OneTableDialogFragment.AddUseronClickListener,OneTableItemDialogFragment.EditUserOnClickListener{
    private Toolbar toolbar;                                                   //定义toolbar
    private ListView lv_oneTable;

    private List<Users> list_user;
    private Onetable_adapter oAdapter;

    private DbService db;

    private OneTableItemDialogFragment oneItemDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_table);
        toolbar = (Toolbar)this.findViewById(R.id.toolbar);
        toolbar.setTitle("单表操作");                     // 标题的文字需在setSupportActionBar之前，不然会无效
        setSupportActionBar(toolbar);

        db = DbService.getInstance(this);
        initControls();
        initData();
    }

    //初始化控件
    private void initControls() {
        lv_oneTable = (ListView) findViewById(R.id.lv_oneTable);
        lv_oneTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,list_user.get(position).getUName() + "--" +
                        list_user.get(position).getId(),Toast.LENGTH_SHORT).show();

                oneItemDialog = new OneTableItemDialogFragment(list_user.get(position).getId(),position);
                oneItemDialog.show(getFragmentManager(),getString(R.string.edit));
            }
        });

    }

    //初始化数据，刚进入页面时加载
    private void initData() {
        list_user = new ArrayList<>();
        list_user = db.loadAllNoteByOrder();
        oAdapter = new Onetable_adapter(this,list_user);
        lv_oneTable.setAdapter(oAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_one_table,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.menu_onetable_add){
            OneTableDialogFragment oneDialog = new OneTableDialogFragment(0,"","","","",0);
            oneDialog.show(getFragmentManager(),getString(R.string.add_user));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onAddUserOnClick(long id, String uName, String uSex, String uAge, String uTel, int flag) {
        Users users = new Users();
        if(flag==1){
            users.setId(id);
        }
        users.setUSex(uSex);
        users.setUTelphone(uTel);
        users.setUAge(uAge);
        users.setUName(uName);

        //flag为0时代表添加
        if(flag==0){
            if(db.saveNote(users) > 0){
                list_user.add(0,users);
                oAdapter.notifyDataSetChanged();
            }
        }else {
            if(db.saveNote(users) > 0){
                int num = 0;
                for (Users u:list_user) {
                    if(u.getId() == id){
                        list_user.remove(num);
                        list_user.add(num,users);
                        break;
                    }
                  num ++;
                }
                oAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onEditUserOnClick(long id, int position, int flag) {
        if(flag==0){
            db.deleteNote(id);
            list_user.remove(position);
            oAdapter.notifyDataSetChanged();
            oneItemDialog.dismiss();
        }else {
            Users updateUser = db.loadNote(id);
            OneTableDialogFragment oDialog = new OneTableDialogFragment(updateUser.getId(),updateUser.getUName(),updateUser.getUSex(),
                    updateUser.getUAge(), updateUser.getUTelphone(),1);
            oneItemDialog.dismiss();
            oDialog.show(getFragmentManager(),getString(R.string.change));
        }
    }
}
