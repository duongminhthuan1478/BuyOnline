package com.example.dell.buyonline.ultil;

/**
 * Created by Dell on 07-Mar-18.
 * Chứa link
 */

// 192.168.1.103
    //192.168.43.135
public class Server {

    public static String MY_WEBSITE = "https://thuanduong.000webhostapp.com";
    public static String LOCAL_HOST = "192.168.1.102";
//    public static String LINK_TYPE_PRODUCT = "http://" + LOCAL_HOST + "/server/getloaisanpham.php";
//    public static String LINK_ITEM_NEW = "http://" + LOCAL_HOST + "/server/getsanphammoinhat.php";
//    public static String LINK_DIEN_THOAI= "http://" + LOCAL_HOST + "/server/getsanpham.php?page=";

    public static String LINK_TYPE_PRODUCT = MY_WEBSITE + "/server/getloaisanpham.php";
    public static String LINK_ITEM_NEW = MY_WEBSITE + "/server/getsanphammoinhat.php";
    public static String LINK_DIEN_THOAI= MY_WEBSITE + "/server/getsanpham.php?page=";
}
