/*
 * Copyright 2018 Yan Zhenjie.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mylove.module_hotel_launcher.webservice;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yanzhenjie.andserver.annotation.Controller;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.PathVariable;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.framework.body.FileBody;
import com.yanzhenjie.andserver.framework.body.JsonBody;
import com.yanzhenjie.andserver.framework.body.StringBody;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.http.multipart.MultipartFile;
import com.yanzhenjie.andserver.util.StatusCode;

import java.io.File;
import java.io.IOException;
import java.util.List;

import me.jessyan.armscomponent.commonsdk.utils.FileUtils;
import me.jessyan.armscomponent.commonservice.CommonApp;
import me.jessyan.armscomponent.commonservice.dao.DaoHelper;
import me.jessyan.armscomponent.commonservice.dao.HotelEntity;

@Controller
public class WebController {
    private static final String TAG = "WebController";
    @GetMapping(path = "/")
    public String index() {
        return "forward:/index.html";
    }

    @GetMapping(path = "/fileupload")
    public String fileupload() {
        return "forward:/fileupload.html";
    }

    @PostMapping(path = "/upload")
    void upload(HttpResponse response,@RequestParam(name = "file") MultipartFile file) throws IOException {
        File localFile = FileUtils.createRandomFile(CommonApp.getInstance().getRootDir(),
                file.getContentType().toString(),
                file.getFilename());
        Log.e(TAG,localFile.getPath());
        file.transferTo(localFile);
    }

    @GetMapping(path = "/fetch/{filename}")
    void fetch(HttpRequest request, HttpResponse response, @PathVariable(name = "filename") String filename) {
        File file = new File(CommonApp.getInstance().getRootDir(),filename);
        if(FileUtils.isFileExists(file)){
            response.setStatus(StatusCode.SC_PARTIAL_CONTENT);
            response.setBody(new FileBody(file));
        }else{
            response.setStatus(StatusCode.SC_MOVED_PERMANENTLY);
            response.setBody(new StringBody("资源不存在!"));
        }
    }

    @GetMapping(path = "/delete/{filename}")
    void del(HttpRequest request, HttpResponse response, @PathVariable(name = "filename") String filename) {
        File file = new File(CommonApp.getInstance().getRootDir(),filename);
        if(FileUtils.isFileExists(file)){
            FileUtils.deleteFile(file);
            response.setStatus(StatusCode.SC_OK);
            response.setBody(new StringBody("删除成功!"));
        }else{
            response.setStatus(StatusCode.SC_MOVED_PERMANENTLY);
            response.setBody(new StringBody("资源不存在!"));
        }

    }

    @GetMapping(path = "/login/{account}/{password}")
    void login(HttpRequest request, HttpResponse response, @PathVariable(name = "account") String account,
                 @PathVariable(name = "password") String password) {
        response.setStatus(StatusCode.SC_OK);
        response.setBody(new StringBody("zhou login => account = "+account+" password = "+password ));
    }

    @GetMapping(path = "/hotels")
    void banners(HttpRequest request, HttpResponse response) {
        try{
            List<HotelEntity> hotelEntities = DaoHelper.fetchHotelEntity();
            if (hotelEntities != null){
                Gson gson = new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create();
                String con = gson.toJson(hotelEntities);
                response.setStatus(StatusCode.SC_OK);
                response.setBody(new JsonBody(con));
            }
        }catch (Exception e){
        }
    }

}