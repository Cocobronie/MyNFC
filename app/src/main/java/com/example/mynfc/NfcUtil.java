package com.example.mynfc;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class NfcUtil {
    private static String TAG = "NfcUtil";

    /*
    读取NFC内容
     */
    public static String[] read(Tag tag){
        String[] s_blocks = new String[20];
        MifareClassic mif = MifareClassic.get(tag);
        int ttype = mif.getType();
        Log.d(TAG, "MifareClassic tag type: " + ttype);
        int tsize = mif.getSize();
        Log.d(TAG, "tag size: " + tsize);
        int s_len = mif.getSectorCount();
        Log.d(TAG, "tag sector count: " + s_len);
        int b_len = mif.getBlockCount();
        Log.d(TAG, "tag block count: " + b_len);
        try {
            mif.connect();
            if (mif.isConnected()){
                for(int i=0; i< s_len; i++){
                    boolean isAuthenticated = false;
                    if (mif.authenticateSectorWithKeyA(i, MifareClassic.KEY_MIFARE_APPLICATION_DIRECTORY)) {
                        isAuthenticated = true;
                    } else if (mif.authenticateSectorWithKeyA(i, MifareClassic.KEY_DEFAULT)) {
                        isAuthenticated = true;
                    } else if (mif.authenticateSectorWithKeyA(i,MifareClassic.KEY_NFC_FORUM)) {
                        isAuthenticated = true;
                    } else {
                        Log.d("TAG", "Authorization denied ");
                    }

                    if(isAuthenticated) {
                        int block_index = mif.sectorToBlock(i);
                        Log.e(TAG, String.valueOf(block_index));
                        byte[] block = mif.readBlock(block_index);
                        String s_block = new String(block,"UTF-8");
                        s_blocks[i] = s_block;
                        Log.d(TAG, s_block);
                    }
                }
            }
            mif.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s_blocks;
    }

    /*
    向NFC中写入内容
     */
    public static void write(Tag tag,Student student){
        MifareClassic mif = MifareClassic.get(tag);
        int ttype = mif.getType();
        Log.d(TAG, "MifareClassic tag type: " + ttype);
        int tsize = mif.getSize();
        Log.d(TAG, "tag size: " + tsize);
        int s_len = mif.getSectorCount();
        Log.d(TAG, "tag sector count: " + s_len);
        int b_len = mif.getBlockCount();
        Log.d(TAG, "tag block count: " + b_len);
        try {
            mif.connect();
            if (mif.isConnected()){
                for(int i=0; i< 11; i++){
                    boolean isAuthenticated = false;
                    if (mif.authenticateSectorWithKeyA(i, MifareClassic.KEY_MIFARE_APPLICATION_DIRECTORY)) {
                        isAuthenticated = true;
                    } else if (mif.authenticateSectorWithKeyA(i, MifareClassic.KEY_DEFAULT)) {
                        isAuthenticated = true;
                    } else if (mif.authenticateSectorWithKeyA(i,MifareClassic.KEY_NFC_FORUM)) {
                        isAuthenticated = true;
                    } else {
                        Log.d("TAG", "Authorization denied ");
                    }

                    if(isAuthenticated) {
                        int block_index = mif.sectorToBlock(i);
                        Log.e(TAG, String.valueOf(block_index));
                        switch (i){
                            case 1:
                                mif.writeBlock(block_index,checkString(student.getName()).getBytes(StandardCharsets.UTF_8));
                                break;
                            case 2:
                                mif.writeBlock(block_index,checkString(student.getId()).getBytes(StandardCharsets.UTF_8));
                                break;
                            case 3:
                                mif.writeBlock(block_index,checkString(student.getHeight()).getBytes(StandardCharsets.UTF_8));
                                break;
                            case 4:
                                mif.writeBlock(block_index,checkString(student.getWeight()).getBytes(StandardCharsets.UTF_8));
                                break;
                            case 5:
                                mif.writeBlock(block_index,checkString(student.getVitalCapacity()).getBytes(StandardCharsets.UTF_8));
                                break;
                            case 6:
                                mif.writeBlock(block_index,checkString(student.getBodyFlexibility()).getBytes(StandardCharsets.UTF_8));
                                break;
                            case 7:
                                mif.writeBlock(block_index,checkString(student.getLongJump()).getBytes(StandardCharsets.UTF_8));
                                break;
                            case 8:
                                mif.writeBlock(block_index,checkString(student.getRun50()).getBytes(StandardCharsets.UTF_8));
                                break;
                            case 9:
                                mif.writeBlock(block_index,checkString(student.getRun800()).getBytes(StandardCharsets.UTF_8));
                                break;
                            case 10:
                                mif.writeBlock(block_index,checkString(student.getSitUp()).getBytes(StandardCharsets.UTF_8));
                                break;
                        }
                    }
                }
            }
            mif.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    判断TAG类型
    */
    public static String supportedTechs(String[] techList) {
        String supported = "false";
        for(String s:techList){
            if(s.equals("android.nfc.tech.MifareClassic")){
                Log.e("techList ",s);
                supported= "MifareClassic";
            }
            if(s.equals("android.nfc.tech.Ndef")){
                Log.e("techList ",s);
                supported= "Ndef";
            }
        }
        return  supported;
    }

    private static String checkString(String s){
        while (s.length()<16){
            s = s+" ";
        }
        return s;
    }



    //字符序列转换为16进制字符串
    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    /**
     * 读取NFC的数据
     */
//    public static String[] readNFCFromTag(Intent intent) throws UnsupportedEncodingException {
//
//    }
//    public static String[] readNFCFromTag(Intent intent) throws UnsupportedEncodingException {
//
//        Parcelable[] rawArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
//        String readResult[] = new String[10];
//        if (rawArray != null) {
//            Log.e(TAG, String.valueOf(rawArray.length));
//            NdefMessage mNdefMsg = (NdefMessage) rawArray[0];
//            Log.e(TAG, String.valueOf(mNdefMsg.getRecords().length));
//            for(int i =0;i<mNdefMsg.getRecords().length;i++) {
//                NdefRecord mNdefRecord = mNdefMsg.getRecords()[0];
//                if (mNdefRecord != null) {
//                    readResult[i] = new String(mNdefRecord.getPayload(), "UTF-8");
//                    Log.e(TAG, readResult[i]);
//                }
//            }
//        }
//        return readResult;
//    }
}





