new Vue({
      el: '#app',
      data: {
        hotels: ''
      },
      created: function () {
        this.$http.get('/hotels').then(response => {
            this.$data.hotels = response.body;
          }, response => {
          });
      },
      method:{

      }
 })


var uploadUrl = 'https://xxxxxxx';
//文件选择完毕时
function FileChangeFn(event) {
    $('.opst_txt').text('重新选择文件');
    $('.send_btn').show();
    var event = event || window.event,
        dom = '',
        ofile = $("#oFile").get(0).files[0],
        otype = ofile.type || '获取失败',
        osize = ofile.size / 1054000,
        ourl = window.URL.createObjectURL(ofile); //文件临时地址
    $('#file_type').text("选择上传文件类型：" + otype);
    $('#file_size').text("选择上传文件大小，共" + osize.toFixed(2) + "MB。");

    console.log("文件类型：" + otype); //文件类型
    console.log("文件大小：" + osize); //文件大小

    if ('video/mp4' == otype || 'video/avi' == otype || 'video/x-msvideo' == otype) {
        dom = '<video id="video" width="100%" height="100%" controls="controls" autoplay="autoplay" src=' + ourl + '></video>';
    }
    if ('audio/mp3' == otype || 'audio/wav' == otype  || 'audio/x-m4a' == otype) {
        dom = '<audio id="audio" width="100%" height="100%" controls="controls" autoplay="autoplay" loop="loop" src=' + ourl + ' ></audio>';
    }
    if ('image/jpeg' == otype || 'image/png' == otype || 'image/gif' == otype) {
        dom = '<img id="photo" width="100%" height="100%" alt="我是image图片文件" src=' + ourl + ' title="" />';
    }
    $('#file_box').html(dom);
};

//侦查附件上传情况 ,这个方法大概0.05-0.1秒执行一次
function OnProgRess(event) {
    var event = event || window.event;
    //console.log(event);  //事件对象
    console.log("已经上传：" + event.loaded); //已经上传大小情况(已上传大小，上传完毕后就 等于 附件总大小)
    //console.log(event.total);  //附件总大小(固定不变)
    var loaded = Math.floor(100 * (event.loaded / event.total)); //已经上传的百分比
    $("#speed").html(loaded + "%").css("width", loaded + "%");
};

//开始上传文件
function UploadFileFn() {

    $('.speed_box').show();
    var oFile = $("#oFile").get(0).files[0], //input file标签
        formData = new FormData(); //创建FormData对象
    xhr = $.ajaxSettings.xhr(); //创建并返回XMLHttpRequest对象的回调函数(jQuery中$.ajax中的方法)
    formData.append("UploadForm[image]", oFile); //将上传name属性名(注意：一定要和 file元素中的name名相同)，和file元素追加到FormData对象中去

    $.ajax({
        type: "POST",
        url: uploadUrl, // 后端服务器上传地址
        data: formData, // formData数据
        cache: false, // 是否缓存
        async: true, // 是否异步执行
        processData: false, // 是否处理发送的数据  (必须false才会避开jQuery对 formdata 的默认处理)
        contentType: false, // 是否设置Content-Type请求头
        xhr: function() {
            if (OnProgRess && xhr.upload) {
                xhr.upload.addEventListener("progress", OnProgRess, false);
                return xhr;
            }
        },
        success: function(returndata) {
            $("#speed").html("上传成功");
            //alert(returndata);
        },
        error: function(returndata) {
            $("#speed").html("上传失败");
            console.log(returndata)
            alert('请正确配置后台服务！');
        }
    });
};