package me.jessyan.armscomponent.commonres.controller;

import cn.hotapk.fhttpserver.NanoHTTPD;
import cn.hotapk.fhttpserver.annotation.RequestMapping;
import cn.hotapk.fhttpserver.annotation.RequestParam;

public class UserController {

    @RequestMapping("userls")
    public NanoHTTPD.Response getUserLs() {
        return setResponse("user列表");
    }

    @RequestMapping("gethtml")
    public String getHtml() {
        return "index2.html";
    }

    @RequestMapping("edituser")
    public NanoHTTPD.Response editUser(@RequestParam("userName") String userName, @RequestParam("id") int id) {
        return setResponse("修改成功");
    }

    public static NanoHTTPD.Response setResponse(String res) {
        return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "application/octet-stream", res);
    }

}
