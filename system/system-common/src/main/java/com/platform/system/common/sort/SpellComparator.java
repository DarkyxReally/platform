package com.platform.system.common.sort;

import java.text.Collator;
import java.util.Comparator;


/**
 * 拼音排序比较器()
 * @version: 1.0
 */
public class SpellComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2){
        Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);  
        return com.compare(o1, o2);  
    }
}
