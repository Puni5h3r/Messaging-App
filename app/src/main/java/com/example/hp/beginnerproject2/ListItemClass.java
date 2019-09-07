package com.example.hp.beginnerproject2;

public class ListItemClass {

    private String mssg_from;
    private String mssg_body;
    private String mssg_time;
    private boolean status = false;
    private int send_or_receive;


    public ListItemClass(){


    }

    public ListItemClass(String mssg_from, String mssg_body, String mssg_time){

        this.mssg_body = mssg_body;
        this.mssg_from = mssg_from;
        this.mssg_time = mssg_time;
    }


    public void setMssg_from(String mssg_from){

        this.mssg_from=mssg_from;
    }
    public void setMssg_body(String a){

        this.mssg_body=a;
    }
 public void setMssg_time(String a){

        this.mssg_time=a;
    }

    public String getMssg_from() {
        return mssg_from;

    }

    public String getMssg_body() {
        return mssg_body;

    }


    public void setSend_or_receive(int k){
        this.send_or_receive=k;

    }
    public int getSend_or_receive() {
        return send_or_receive;

    }

    public String getMssg_time() {
        return mssg_time;
    }
    public void setStatus(int k){
        if (k==0){
            this.status=false;

        }else{
        this.status=true;

    }

    }
    public Boolean getStatus(){
        return status;
    }
}

