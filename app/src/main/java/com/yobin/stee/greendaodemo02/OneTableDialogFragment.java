package com.yobin.stee.greendaodemo02;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * 用户添加与修改
 * Created by yobin_he on 2017/1/22.
 */


public class OneTableDialogFragment extends DialogFragment {
    private EditText edit_onetable_name;
    private EditText edit_onetable_sex;
    private EditText edit_onetable_tel;
    private EditText edit_onetable_age;

    private String uName;                                      //用户姓名
    private String uSex;                                       //用户性别
    private String uAge;                                       //用户年纪
    private String uTel;                                       //用户电话

    private int flag;                                          //flag 标识 0:添加 1:修改
    private long uId;                                          //用户id,添加时为0,修改时为正确的id

    public OneTableDialogFragment(long uId, String uName, String uSex, String uAge, String uTel, int flag) {
        this.uName = uName;
        this.uSex = uSex;
        this.uAge = uAge;
        this.uTel = uTel;
        this.flag = flag;
        this.uId = uId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_onetable_dialog, null);
        edit_onetable_name = (EditText) view.findViewById(R.id.edit_onetable_name);
        edit_onetable_sex = (EditText) view.findViewById(R.id.edit_onetable_sex);
        edit_onetable_tel = (EditText) view.findViewById(R.id.edit_onetable_tel);
        edit_onetable_age = (EditText) view.findViewById(R.id.edit_onetable_age);


        edit_onetable_name.setText(uName);
        edit_onetable_age.setText(uAge);
        edit_onetable_sex.setText(uSex);
        edit_onetable_tel.setText(uTel);

        builder.setView(view)
                //add action buttons
                .setTitle(R.string.add_user)
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddUseronClickListener listener = (AddUseronClickListener) getActivity();
                        listener.onAddUserOnClick(uId,edit_onetable_name.getText().toString(),
                                edit_onetable_sex.getText().toString(),
                                edit_onetable_age.getText().toString(),
                                edit_onetable_tel.getText().toString(),
                                flag);
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edit_onetable_name.setText("");
                edit_onetable_sex.setText("");
                edit_onetable_age.setText("");
                edit_onetable_tel.setText("");
            }
        });

        return builder.create();
    }

    public interface AddUseronClickListener{
        void onAddUserOnClick(long id,String uName, String uSex,String uAge,String uTel,int flag);
    }
}
