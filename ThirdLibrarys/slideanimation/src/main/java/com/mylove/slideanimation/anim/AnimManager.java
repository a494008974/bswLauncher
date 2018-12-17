package com.mylove.slideanimation.anim;

import java.util.Random;

/**
 * Created by zhou on 2018/12/15.
 */

public class AnimManager {
    public static Class[] anims = {
            AnimBaiYeChuang.class,
            AnimCaChu.class,
            AnimHeZhuang.class,
            AnimJieTi.class,
            AnimLingXing.class,
            AnimLunZi.class,
            AnimPiLie.class,
            AnimQieRu.class,
            AnimQiPan.class,
            AnimShanXingZhanKai.class,
            AnimShiZiXingKuoZhan.class,
            AnimSuiJiXianTiao.class,
            AnimXiangNeiRongJie.class,
            AnimYuanXingKuoZhan.class
    };

    public static int index = 0;

    public static Class getNext(){
        index ++;
        index = index % anims.length;
        return anims[index];
    }

    public static Class getRandom(){
        Random random = new Random();
        index = random.nextInt(anims.length);
        return anims[index];
    }

}
