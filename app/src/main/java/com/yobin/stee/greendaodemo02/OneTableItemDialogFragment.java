package com.yobin.stee.greendaodemo02;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by yobin_he on 2017/1/23.
 */

public class OneTableItemDialogFragment extends DialogFragment {
    private long id;     //用户id
    private int position;     //list中的编号
    private TextView txt_onetable_update;
    private TextView txt_onetable_delete;

    public OneTableItemDialogFragment(long id, int position) {
        this.id = id;
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_onetable_itemdialog,container);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_onetable_update = (TextView)view.findViewById(R.id.txt_onetable_update);
        txt_onetable_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行接口回调
                EditUserOnClickListener listener = (EditUserOnClickListener) getActivity();
                listener.onEditUserOnClick(id,position,1);
            }
        });
        txt_onetable_delete = (TextView)view.findViewById(R.id.txt_onetable_delete);

        txt_onetable_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUserOnClickListener listener = (EditUserOnClickListener) getActivity();
                listener.onEditUserOnClick(id,position,0);
            }
        });
    }

    public interface EditUserOnClickListener{
        //flag标示，0标示删除，1表示修改
        void onEditUserOnClick(long id,int position,int flag);
    }




}
