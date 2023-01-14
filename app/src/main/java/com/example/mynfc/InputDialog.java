package com.example.mynfc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class InputDialog extends DialogFragment {
    private ClickCallBack clickCallBack;
    private Button cancelBtn;
    private Button confirmBtn;
    private EditText mNameText;
    private EditText mIdText;
    private EditText mHeightText;
    private EditText mWeightText;
    private EditText mVitaText;
    private EditText mBodyText;
    private EditText mJumpText;
    private EditText m50Text;
    private EditText m800Text;
    private EditText mSitText;
    private Student mStudent;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_input_dialog, container, false);
        confirmBtn = view.findViewById(R.id.yes);
        cancelBtn = view.findViewById(R.id.No);

        mNameText = (EditText) view.findViewById(R.id.stu_name);
        mIdText = view.findViewById(R.id.stu_id);
        mHeightText = (EditText) view.findViewById(R.id.stu_height);
        mWeightText = view.findViewById(R.id.stu_weight);
        mVitaText = (EditText) view.findViewById(R.id.stu_vitalCapacity);
        mBodyText = view.findViewById(R.id.stu_bodyFlexibility);
        mJumpText = (EditText) view.findViewById(R.id.stu_longJump);
        m50Text = view.findViewById(R.id.stu_run50);
        m800Text = (EditText) view.findViewById(R.id.stu_run800);
        mSitText = view.findViewById(R.id.stu_sitUp);

        mStudent = new Student();
        initView();
        return view;
    }
    //在fragment附加给activity时调用
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        clickCallBack = (ClickCallBack) activity;
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
                clickCallBack.clickCancel();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallBack.clickConfirm(mStudent);
            }
        });

        //给student赋值
        mNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null)
                    mStudent.setName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mIdText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null)
                mStudent.setId(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mHeightText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStudent.setHeight(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mWeightText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStudent.setWeight(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mVitaText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStudent.setVitalCapacity(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mBodyText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStudent.setBodyFlexibility(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mJumpText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStudent.setLongJump(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        m50Text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStudent.setRun50(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        m800Text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStudent.setRun800(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mSitText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStudent.setSitUp(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    interface ClickCallBack {
        void clickCancel();
        void clickConfirm(Student student);
    }


}

