package me.jessyan.armscomponent.commonres.controller;

import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cn.hotapk.fhttpserver.NanoHTTPD;
import cn.hotapk.fhttpserver.annotation.RequestMapping;
import cn.hotapk.fhttpserver.annotation.RequestParam;
import cn.hotapk.fhttpserver.utils.FFileUtils;
import me.jessyan.armscomponent.commonres.service.WebService;
import me.jessyan.armscomponent.commonres.utils.FileUtils;

public class WebController {

    private static final String TAG = "WebController";
    @RequestMapping("res")
    public NanoHTTPD.Response fileController(@RequestParam("name") String name) {
        return fetchResponse(name);
    }

    @RequestMapping("upload")
    public NanoHTTPD.Response upload(NanoHTTPD.IHTTPSession session) {
        Map<String, String> files = new HashMap<>();
        try {
            session.parseBody(files);
            Map<String, String> parms = session.getParms();
            String resId = parms.get("resId");
            String fileName = java.net.URLEncoder.encode(parms.get("file"),"UTF-8");
            String res = WebService.path + File.separator + fileName;
            FileUtils.createOrExistsFile(res);
            FFileUtils.copyFileTo(files.get("file"), res);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NanoHTTPD.ResponseException e) {
            e.printStackTrace();
        }
        return setResponse("success","text/html");
    }

    private NanoHTTPD.Response fetchResponse(String name) {
        NanoHTTPD.Response response = null;
        try {
            String filePath = WebService.path + File.separator + java.net.URLEncoder.encode(name,"UTF-8");
            InputStream inputStream = fetchInputStream(filePath);

            android.util.Log.e(TAG,"filePath = " + filePath);
            String mimeType = getMimeType(filePath);
            response = NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, mimeType,inputStream); //data -- inputStream
        } catch (Exception e) {
        }
        return response;
    }

    private String getMimeType(String filePath) {
        String ext = MimeTypeMap.getFileExtensionFromUrl(filePath);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
    }

    public InputStream fetchInputStream(String name){
        try {
            InputStream inputStream = new FileInputStream(new File(WebService.path+name));
            return inputStream;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static NanoHTTPD.Response setResponse(String res,String mimeType) {
        return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, mimeType, res);
    }
}
