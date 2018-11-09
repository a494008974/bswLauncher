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

import com.yanzhenjie.andserver.annotation.Controller;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.PathVariable;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.framework.body.StringBody;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.http.multipart.MultipartFile;
import com.yanzhenjie.andserver.util.MediaType;
import com.yanzhenjie.andserver.util.StatusCode;

import java.io.File;
import java.io.IOException;

import me.jessyan.armscomponent.commonsdk.utils.FileUtils;
import me.jessyan.armscomponent.commonservice.CommonApp;

@Controller
public class WebController {

    @GetMapping(path = "/")
    public String index() {
        return "forward:/index.html";
    }

    @GetMapping(path = "/show")
    public String show() {
        return "forward:/show.html";
    }

    @PostMapping(path = "/upload")
    String upload(HttpResponse response,@RequestParam(name = "file") MultipartFile file) throws IOException {

        File localFile = FileUtils.createRandomFile(CommonApp.getInstance().getRootDir(),
                file.getContentType().toString(),
                file.getFilename());

        file.transferTo(localFile);
//        response.setStatus(StatusCode.SC_OK);
//        response.setBody(new StringBody("upload => success !"));
        return "forward:/index.html";
    }

    @GetMapping(path = "/login/{account}/{password}")
    void login(HttpRequest request, HttpResponse response, @PathVariable(name = "account") String account,
                 @PathVariable(name = "password") String password) {
        response.setStatus(StatusCode.SC_OK);
        response.setBody(new StringBody("zhou login => account = "+account+" password = "+password ));
    }
}