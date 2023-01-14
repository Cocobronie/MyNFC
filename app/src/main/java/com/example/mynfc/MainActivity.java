package com.example.mynfc;



import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity implements InputDialog.ClickCallBack,InputDialog2.ClickCallBack2{

    //NFC相关参数
    private NfcAdapter mNfcAdapter=null;
    private PendingIntent mPendingIntent = null;
    private Student mStudent;
    private String mName;  //姓名
    private String mId;   //学号
    private String mGrade;  //成绩

    //组件
    private TextView mInfoText;
    private Button mReadBtn;
    private Button mWriteBtn;
    private InputDialog mInputDialog;
    private InputDialog2 mInputDialog2;
    private ReadDialog mReadDialog;
    private Tag mTag;
    private String TAG = "MainActivity";
    private boolean isWrite = false;
    private boolean isRead = false;
    private boolean isNdef = false;
    private boolean isMifareClassic = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInfoText = findViewById(R.id.student_info);
        mReadBtn = findViewById(R.id.readNFC);
        mWriteBtn = findViewById(R.id.writeNFC);
        mStudent = new Student();

        //点击read按钮：读取卡片中的信息，并显示到infoText中
        mReadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出ReadDialog
                mReadDialog = new ReadDialog();
                mReadDialog.showNow(getSupportFragmentManager(),"ReadDialog");
                mReadDialog.getDialog().setCancelable(false);
                isRead=true;
            }});

        //点击write按钮：弹出writeDialog并完成写入功能
        mWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNdef){
                    mInputDialog2 = new InputDialog2();    // 弹出输入对话框
                    mInputDialog2.showNow(getSupportFragmentManager(),"InputDialog2");
                    mInputDialog2.getDialog().setCancelable(false);
                    isWrite=true;
                }
                if(isMifareClassic){
                    mInputDialog = new InputDialog();    // 弹出输入对话框
                    mInputDialog.showNow(getSupportFragmentManager(),"InputDialog");
                    mInputDialog.getDialog().setCancelable(false);
                    isWrite=true;
                }

            }
        });
        //检测NFC是否打开
        NfcCheck();
        //初始化参数
        mPendingIntent = PendingIntent.getActivity(this,0,new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0);
    }

    /*
    点击InputDialog的取消按钮
    */
    @Override
    public void clickCancel() {
        mInputDialog.dismiss(); //关闭弹窗
    }

    /*
    点击InputDialog的确认按钮
     */
    @Override
    public void clickConfirm(Student student) {
        mStudent = student;
        mInputDialog.dismiss();//关闭弹窗
        //弹出ReadDialog
        mReadDialog = new ReadDialog();
        mReadDialog.showNow(getSupportFragmentManager(),"ReadDialog");
        mReadDialog.getDialog().setCancelable(false);
        isWrite=true;
    }

    @Override
    public void clickCancel2() { mInputDialog2.dismiss(); }

    @Override
    public void clickConfirm2(String name, String id, String grade) {
        mName = name;
        mId = id;
        mGrade = grade;
        mInputDialog2.dismiss();//关闭弹窗
        //弹出ReadDialog
        mReadDialog = new ReadDialog();
        mReadDialog.showNow(getSupportFragmentManager(),"ReadDialog");
        mReadDialog.getDialog().setCancelable(false);
        isWrite=true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mNfcAdapter!=null)
            mNfcAdapter.enableForegroundDispatch(this,mPendingIntent,null,null);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mNfcAdapter!=null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //判断类型
        String Type = NfcUtil.supportedTechs(mTag.getTechList());
        //正在读
        if(isRead){
            mInfoText.setText("数据类型为："+Type+"\n");
            //Ndef数据读
            if(Type.equals("Ndef")){  NdefRead();   }
            //MifareClassic数据读
            if(Type.equals("MifareClassic")){   MifareRead();   }
            //设置Write按钮可点击
            mWriteBtn.setEnabled(true);
        }

        //正在写
        if (isWrite){
            if(Type.equals("Ndef")){     NdefWrite();  }
            if(Type.equals("MifareClassic")){   MifareWrite();   }
            mWriteBtn.setEnabled(false);
        }
    }

    private void NdefWrite(){
        isNdef = true;
        Ndef ndef = Ndef.get(mTag);
        if (ndef != null) {
            try {
                ndef.connect();
                NdefRecord mimeRecord1 = NdefRecord.createMime("CSU", mName.getBytes(Charset.forName("UTF-8")));
                NdefRecord mimeRecord2 = NdefRecord.createMime("CSU", mId.getBytes(Charset.forName("UTF-8")));
                NdefRecord mimeRecord3 = NdefRecord.createMime("CSU", mGrade.getBytes(Charset.forName("UTF-8")));
                mInfoText.setText("姓名："+mName+"\n");
                mInfoText.append("学号："+mId+"\n");
                mInfoText.append("总成绩"+mGrade+"\n");
                ndef.writeNdefMessage(new NdefMessage(mimeRecord1,mimeRecord2,mimeRecord3));
                ndef.close();
            } catch ( Exception e) {
                e.printStackTrace();
            }
        }
        isWrite=false;
        isNdef=false;
        mReadDialog.dismiss();  //关闭弹窗
    }

    private void MifareWrite(){
        isMifareClassic = true;
        if(isWrite){    //正在写
            NfcUtil.write(mTag,mStudent);
            setInfoText(mStudent);
            isWrite=false;
            isMifareClassic=false;
            mReadDialog.dismiss();  //关闭弹窗
        }
    }

    private void MifareRead(){
        isMifareClassic = true;
        String[] info = NfcUtil.read(mTag); //读取Nfc
        for(int i=1;i<11;i++){
            setInfoText(i,info[i]);
        }
        isRead=false;
        mReadDialog.dismiss();  //关闭弹窗
    }

    private void NdefRead(){
        isNdef = true;
        Ndef ndef = Ndef.get(mTag);
        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();
            if (ndefMessage.getRecords().length == 3) {
                String messageId = new String(ndefMessage.getRecords()[0].getPayload(),"UTF-8");
                String messageOwner = new String(ndefMessage.getRecords()[1].getPayload(),"UTF-8");
                String messageOwnerPhone = new String(ndefMessage.getRecords()[2].getPayload(),"UTF-8");
                mInfoText.append("姓名："+messageId+"\n");
                mInfoText.append("学号："+messageOwner+"\n");
                mInfoText.append("总成绩"+messageOwnerPhone+"\n");
            }
            ndef.close();
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isRead=false;
        mReadDialog.dismiss();  //关闭弹窗
    }

    /*
    设置mInfoText
     */
    private void setInfoText(int i,String info){
        switch (i){
            case 1:
                mInfoText.append("姓名："+info+"\n");
                break;
            case 2:
                mInfoText.append("学号："+info+"\n");
                break;
            case 3:
                mInfoText.append("身高："+info+"\n");
                break;
            case 4:
                mInfoText.append("体重："+info+"\n");
                break;
            case 5:
                mInfoText.append("肺活量："+info+"\n");
                break;
            case 6:
                mInfoText.append("坐位体前屈："+info+"\n");
                break;
            case 7:
                mInfoText.append("立定跳远："+info+"\n");
                break;
            case 8:
                mInfoText.append("50米跑步："+info+"\n");
                break;
            case 9:
                mInfoText.append("800米跑步："+info+"\n");
                break;
            case 10:
                mInfoText.append("仰卧起坐："+info+"\n");
                break;
        }
    }
    private void setInfoText(Student student){
        for(int i =0;i<11;i++){
            switch (i){
                case 1:
                    mInfoText.setText("姓名："+student.getName()+"\n");
                    break;
                case 2:
                    mInfoText.append("学号："+student.getId()+"\n");
                    break;
                case 3:
                    mInfoText.append("身高："+student.getHeight()+"\n");
                    break;
                case 4:
                    mInfoText.append("体重："+student.getWeight()+"\n");
                    break;
                case 5:
                    mInfoText.append("肺活量："+student.getVitalCapacity()+"\n");
                    break;
                case 6:
                    mInfoText.append("坐位体前屈："+student.getBodyFlexibility()+"\n");
                    break;
                case 7:
                    mInfoText.append("立定跳远："+student.getLongJump()+"\n");
                    break;
                case 8:
                    mInfoText.append("50米跑步："+student.getRun50()+"\n");
                    break;
                case 9:
                    mInfoText.append("800米跑步："+student.getRun800()+"\n");
                    break;
                case 10:
                    mInfoText.append("仰卧起坐："+student.getSitUp()+"\n");
                    break;
            }
        }
    }
    /*
    判断NFC功能是否可用
    */
    private void NfcCheck(){
        //初始化mNfcAdapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(mNfcAdapter==null){//不支持NFC
            Toast.makeText(this,"不支持NFC",Toast.LENGTH_SHORT).show();
        }else{//判断NFC是否打开
            if(!mNfcAdapter.isEnabled()){    //如果没有打开，则跳转到设置界面
                Intent setNfc = new Intent(Settings.ACTION_NFC_SETTINGS);
                startActivity(setNfc);
            }else{
                Toast.makeText(this,"NFC已打开",Toast.LENGTH_SHORT).show();
            }

        }
    }


}

//写操作
//    private void writeNfc(Tag tag){
//        if(supportedTechs(tag.getTechList())){
//            MifareClassic mifareClassic = MifareClassic.get(tag);
//            boolean isAuth = false;
//            if(mifareClassic!=null){
//                try {
//                    mifareClassic.connect();
////                    if(mifareClassic.authenticateSectorWithKeyA(1,MifareClassic.KEY_DEFAULT)){
////                        if(mifareClassic.authenticateSectorWithKeyB(1,MifareClassic.KEY_DEFAULT)){
////                            mifareClassic.writeBlock(1,"0123456789000000".getBytes());
////                        }
////                    }
//                    if(mifareClassic.authenticateSectorWithKeyA(0,MifareClassic.KEY_DEFAULT)){
//                        mifareClassic.writeBlock(1,"student id:12345".getBytes(StandardCharsets.UTF_8));
//                        Log.e("写入成功","");
//                    }
//                    mifareClassic.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }

//    //读操作
//    private void readNfc(Tag tag){
//        if(supportedTechs(tag.getTechList())){
//            MifareClassic mifareClassic = MifareClassic.get(tag);
//            boolean isAuth = false;
//            if(mifareClassic!=null){
//                try {
//                    mifareClassic.connect();
//                    int SecCount = mifareClassic.getSectorCount();
//                    Log.e("SecCount", String.valueOf(SecCount));
//                    if(mifareClassic.authenticateSectorWithKeyA(0,MifareClassic.KEY_DEFAULT)){
//                        isAuth=true;
//                        Log.e("Sector",String.valueOf(0));
//                    }
//                    if(isAuth){
//                        byte data[] = mifareClassic.readBlock(1);
//                        Log.e("Block",String.valueOf(1));
//                        mInfoText.setText("Discover a TAG:"+tag+"\n"
//                                + new String(data,"UTF-8"));
//
//                    }
//
////                    for(int i=0;i<SecCount;i++){    //遍历每一个扇区
////                        if(mifareClassic.authenticateSectorWithKeyA(i,MifareClassic.KEY_DEFAULT)){
////                            isAuth=true;
////                            Log.e("Sector",String.valueOf(i));
////                        }
////                        if(isAuth){
////                            int BlockCount = mifareClassic.getBlockCountInSector(i);
////                            for(int j =0;j<BlockCount;j++){     //遍历扇区中的每一块
////                                byte data[] = mifareClassic.readBlock(j);
////                                Log.e("Block",String.valueOf(j));
////                                mInfoText.setText("Discover a TAG:"+tag+"\n"
////                                        + bytesToHexString(data));
////                            }
////                        }
////                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }else{
//                Log.e("mifareClassic:","为空！！！");
//            }
//        }
//    }