package com.example.mynfc;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class InputDialog2 extends DialogFragment {
    private InputDialog2.ClickCallBack2 clickCallBack;
    private Button cancelBtn;
    private Button confirmBtn;
    private EditText mNameText;
    private EditText mIdText;
    private EditText mGradetText;
    private String name;  //姓名
    private String id;   //学号
    private String grade;  //成绩
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_input_dialog2, container, false);
        confirmBtn = view.findViewById(R.id.yes);
        cancelBtn = view.findViewById(R.id.No);

        mNameText = (EditText) view.findViewById(R.id.Mstu_name);
        mIdText = view.findViewById(R.id.Mstu_id);
        mGradetText = (EditText) view.findViewById(R.id.stu_grade);

        initView();
        return view;
    }

    //在fragment附加给activity时调用

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        clickCallBack = (InputDialog2.ClickCallBack2) activity;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable( new ColorDrawable(Color.WHITE));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );

        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.CENTER;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        //params.width =  ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = 1000;
        win.setAttributes(params);
    }

    //设置监听
    private void initView() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.clickCancel2();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.clickConfirm2(name,id,grade);
            }
        });

        //给student赋值
        mNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mIdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                id = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mGradetText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                grade = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    interface ClickCallBack2 {
        void clickCancel2();
        void clickConfirm2(String name,String id, String grade);
    }
}

