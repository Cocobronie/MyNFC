# MyNFC
基于Android的RFID读写器 **(详细介绍请移步我的博客：https://www.cocobronie.cn/)**

### 实验要求
基于任意一种13.56MHz通信技术，使用包括(但不限于)手机的任意一种RFID读写器，使用老师派发（但不限于）的任意一张RFID卡片，针对任意应用场景，构建一个RFID应用系统，实现对卡片数据的读写。

## 一、功能描述

为了便于中南大学的老师们体测成绩登记更加快捷方便，基于13.56MHz通信技术和手机的NFC功能，使用老师派发的RFID卡片和NFC标签，构建了一个RFID应用系统，实现对Ndef和MifareClassic两种卡片数据的读写。



## 二、设计简要描述

### 1、UI界面设计

![image-20230528141821319](https://github.com/Cocobronie/MyNFC/assets/98938169/2e6f8911-1824-4ca0-81c0-67cb7a8d0369)


### 2、程序设计

![image-20230528141851384](https://github.com/Cocobronie/MyNFC/assets/98938169/f8e5d714-eedd-4ea6-855b-a8029036defb)

首先判断是否支持NFC，再判断NFC是否打开，若没有打开则跳转到手机的设置界面。如果打开此时可以看到按钮状态，只有ReadBtn是可点击的，点击ReadBtn，弹出ReadDialog寻找标签，当标签靠近时关闭ReadDialog，判断标签数据类型，读取标签数据同时更改主界面文本框中的值。此时WriteBtn变为可点击，点击WriteBtn，根据ReadBtn判断的标签数据类型弹出相应的InputDialog，最后弹出ReadDialog寻找标签并将数据写入，最后将更改完毕之后的数据呈现到主界面的文本框中。

## 三、学习笔记

### 1、标签可以分为两大类：

1、`NDEF TAG`：常见的NFC

2、`非NDEF TAG`：RFID 卡片

### 2、**Android支持的数据格式**

![Untitled](https://github.com/Cocobronie/MyNFC/assets/98938169/5bae637a-d604-41d4-80a5-2a6494da38e7)

- **实验中的mifare卡片的数据格式为MifareClassic**
- **实验中的NFC标签的数据格式为Ndef**

### 3、byte转string

### 4、NdefMessage 、NdefRecord（适用于NDEF TAG）

`NdefMessage`：主要是描述NDEF格式的信息

`NdefRecord`：这个是NDEF信息的一个信息段

`NdefMessage`中包含许多`NdefRecord`
