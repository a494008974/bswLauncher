package com.mylove.tvlauncher.mvp.model.entity;

import java.util.List;

import me.jessyan.armscomponent.commonservice.dao.ClsBean;
import me.jessyan.armscomponent.commonservice.dao.InfoBean;

/**
 * Created by zhou on 2018/11/29.
 */

public class HomeResponse {

    /**
     * status : 1
     * tip : 获取数据成功
     * info : {"is_shade":"0","is_fly":"0","img_url":"","data":{"cls":[{"title":"推荐","screen":"1","display":"1"},{"title":"影视","screen":"2","display":"1"},{"title":"教育","screen":"3","display":"1"},{"title":"应用","screen":"4","display":"1"}],"info":[{"title":"爱奇艺","way":"0","way_val":"cibntv_yingshi://homei_v5?tabld=355","img_url":"2017-11/09/9bd8301618373d0d41cbf64ba2212035.png","effect_url":"","tag":"101","is_lock":"1","is_hold":"1","update_time":"1530929224","url":"2017-09/29/0eb1538df661253be1e19dab2918d1c3","ico":"2017-09/29/fc61debc87aae82f638473235ac55b6f.png","pkg":"com.gitvdemobsw.video","act":"com.qiyi.video.home.HomeActivity","is_link":"0","link":"","screen":"1"},{"title":"电视二","way":"0","way_val":"","img_url":"2017-12/09/c127e70367a65cd0be9edfa33bf7ea12.png","effect_url":"","tag":"107","is_lock":"1","is_hold":"1","update_time":"1523616374","url":"2017-12/06/7fc1be269b4b68e78cc7afe3243302ee.apk","ico":"2017-12/06/76eb4fe523b20f10e2ebc999818916f6.png","pkg":"com.linkin.tv","act":"com.linkin.tv.IndexActivity","is_link":"0","link":"","screen":"1"}]}}
     */

    private int status;
    private String tip;
    private InfoBeanX info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public InfoBeanX getInfo() {
        return info;
    }

    public void setInfo(InfoBeanX info) {
        this.info = info;
    }

    public static class InfoBeanX {
        /**
         * is_shade : 0
         * is_fly : 0
         * img_url :
         * data : {"cls":[{"title":"推荐","screen":"1","display":"1"},{"title":"影视","screen":"2","display":"1"},{"title":"教育","screen":"3","display":"1"},{"title":"应用","screen":"4","display":"1"}],"info":[{"title":"爱奇艺","way":"0","way_val":"cibntv_yingshi://homei_v5?tabld=355","img_url":"2017-11/09/9bd8301618373d0d41cbf64ba2212035.png","effect_url":"","tag":"101","is_lock":"1","is_hold":"1","update_time":"1530929224","url":"2017-09/29/0eb1538df661253be1e19dab2918d1c3","ico":"2017-09/29/fc61debc87aae82f638473235ac55b6f.png","pkg":"com.gitvdemobsw.video","act":"com.qiyi.video.home.HomeActivity","is_link":"0","link":"","screen":"1"},{"title":"电视二","way":"0","way_val":"","img_url":"2017-12/09/c127e70367a65cd0be9edfa33bf7ea12.png","effect_url":"","tag":"107","is_lock":"1","is_hold":"1","update_time":"1523616374","url":"2017-12/06/7fc1be269b4b68e78cc7afe3243302ee.apk","ico":"2017-12/06/76eb4fe523b20f10e2ebc999818916f6.png","pkg":"com.linkin.tv","act":"com.linkin.tv.IndexActivity","is_link":"0","link":"","screen":"1"}]}
         */

        private String is_shade;
        private String is_fly;
        private String img_url;
        private DataBean data;

        public String getIs_shade() {
            return is_shade;
        }

        public void setIs_shade(String is_shade) {
            this.is_shade = is_shade;
        }

        public String getIs_fly() {
            return is_fly;
        }

        public void setIs_fly(String is_fly) {
            this.is_fly = is_fly;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            private List<ClsBean> cls;
            private List<InfoBean> info;

            public List<ClsBean> getCls() {
                return cls;
            }

            public void setCls(List<ClsBean> cls) {
                this.cls = cls;
            }

            public List<InfoBean> getInfo() {
                return info;
            }

            public void setInfo(List<InfoBean> info) {
                this.info = info;
            }

        }
    }
}
