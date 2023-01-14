package com.example.mynfc;

public class Student {
    private String name;  //姓名
    private String id;   //学号
    private String height;  //身高
    private String weight;  //体重
    private String vitalCapacity;  //肺活量
    private String bodyFlexibility;   //坐位体前屈
    private String longJump;  //立定跳远
    private String run50;  //50米跑步
    private String run800;  //800米跑步
    private String sitUp;   //仰卧起坐

    public Student(){
//        name = "Coco           ";
//        id = "8202200000      ";
        name = "8202201417     ";
        id = "1234            ";
        height = "165             ";
        weight = "50              ";
        vitalCapacity = "2908            ";
        bodyFlexibility = "19.8            ";
        longJump = "183             ";
        run50 = "8.8             ";
        run800 = "217             ";
        sitUp = "46              ";
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setVitalCapacity(String vitalCapacity) {
        this.vitalCapacity = vitalCapacity;
    }

    public void setBodyFlexibility(String bodyFlexibility) {
        this.bodyFlexibility = bodyFlexibility;
    }

    public void setLongJump(String longJump) {
        this.longJump = longJump;
    }

    public void setRun50(String run50) {
        this.run50 = run50;
    }

    public void setRun800(String run800) {
        this.run800 = run800;
    }

    public void setSitUp(String sitUp) {
        this.sitUp = sitUp;
    }


    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getVitalCapacity() {
        return vitalCapacity;
    }

    public String getBodyFlexibility() {
        return bodyFlexibility;
    }

    public String getLongJump() {
        return longJump;
    }

    public String getRun50() {
        return run50;
    }

    public String getRun800() {
        return run800;
    }

    public String getSitUp() {
        return sitUp;
    }
}
