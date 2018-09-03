package com.mylove.launcher.app.utils;


import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonservice.launcher.bean.Element;


/**
 * Created by Administrator on 2018/4/12.
 */

public class BeanUtils {

    public static List<Element> initSize(int size){
        List<Element> elements = new ArrayList<Element>();
        for (int i =0; i<size; i++){
            Element element = new Element();
//            element.setTag("10"+size);
//            element.setColSpan(3);
//            element.setRowSpan(3);
            switch (i){
                case 0:
//                    element.setColSpan(4);
//                    element.setRowSpan(8);
                    break;
                case 1:
                case 4:
                case 5:
                case 6:
//                    element.setColSpan(4);
//                    element.setRowSpan(4);
                    break;
                case 2:
                case 3:
//                    element.setColSpan(3);
//                    element.setRowSpan(5);
                    break;
                case 7:
//                    element.setColSpan(6);
//                    element.setRowSpan(3);
                    break;
            }

            elements.add(element);
        }
        return elements;
    }

}
