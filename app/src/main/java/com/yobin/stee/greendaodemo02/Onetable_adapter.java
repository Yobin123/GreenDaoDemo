package com.yobin.stee.greendaodemo02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import greendao.Users;

/**
 * 用户显示adapter
 * Created by yobin_he on 2017/1/22.
 */

public class Onetable_adapter extends BaseAdapter{
    private LayoutInflater inflater;
    private List<Users> list_user;

    public Onetable_adapter(Context context, List<Users> list_user) {
        this.inflater = LayoutInflater.from(context);
        this.list_user = list_user;
    }

    @Override
    public int getCount() {
        return list_user.size();
    }

    @Override
    public Object getItem(int position) {
        return list_user.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserInfo userInfo;
        if(convertView==null){
            userInfo = new UserInfo();
            convertView = inflater.inflate(R.layout.activity_ontable_lv_item,null);

            userInfo.uAge = (TextView)convertView.findViewById(R.id.txt_onetable_age);
            userInfo.uName = (TextView)convertView.findViewById(R.id.txt_onetable_uName);
            userInfo.uSex = (TextView)convertView.findViewById(R.id.txt_onetable_uSex);
            userInfo.uTel = (TextView)convertView.findViewById(R.id.txt_onetable_tel);

            convertView.setTag(userInfo);
        }else {
            userInfo = (UserInfo) convertView.getTag();
        }
        userInfo.uSex.setText(list_user.get(position).getUSex());
        userInfo.uTel.setText(list_user.get(position).getUTelphone());
        userInfo.uName.setText(list_user.get(position).getUName());
        userInfo.uAge.setText(list_user.get(position).getUAge());
        return convertView;
    }
     private class UserInfo{
        TextView uName;
        TextView uSex;
        TextView uAge;
        TextView uTel;
    }
}
