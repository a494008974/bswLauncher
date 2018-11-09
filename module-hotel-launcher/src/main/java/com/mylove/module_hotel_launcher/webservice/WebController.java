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
import com.yanzhenjie.andserver.http.cookie.Cookie;
import com.yanzhenjie.andserver.http.multipart.MultipartFile;
import com.yanzhenjie.andserver.http.session.Session;
import com.yanzhenjie.andserver.util.MediaType;
import com.yanzhenjie.andserver.util.StatusCode;

import java.io.IOException;

@Controller
public class WebController {

    @GetMapping(path = "/")
    public String index() {
        // Equivalent to [return "/index"].
        return "forward:/index.html";
    }

    @GetMapping(path = "/show")
    public String show() {
        // Equivalent to [return "/index"].
        return "forward:/show.html";
    }

    @PostMapping(path = "/upload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String upload(@RequestParam(name = "header") MultipartFile file) throws IOException {
//        File localFile = FileUtils.createRandomFile(file);
//        file.transferTo(localFile);
//        return localFile.getAbsolutePath();
        return null;
    }

    @GetMapping(path = "/consume", consumes = {"application/json", "!application/xml"})
    String consume() {
        return "Consume is successful";
    }

    @GetMapping(path = "/produce", produces = {"application/json; charset=utf-8"})
    String produce() {
        return "Produce is successful";
    }

    @GetMapping(path = "/user/{username}")
    String user(@PathVariable("username") String username) {
        return "username : "+username;
    }

    @GetMapping(path = "/login/{account}/{password}")
    void login(HttpRequest request, HttpResponse response, @PathVariable(name = "account") String account,
                 @PathVariable(name = "password") String password) {
        response.setStatus(StatusCode.SC_OK);
        response.setBody(new StringBody("zhou login => account = "+account+" password = "+password ));
    }
}