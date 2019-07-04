package com.example.wireless;

public class AddContact {
    String Deependra="9424934024";
    String not="NAME NOT FOUND";
    public  String FindName(String body){
        if(body.equals("DEEPENDRA")){
            return Deependra + " is the not that you have wanted";
        }
        else {
            return not;
        }
    }
}