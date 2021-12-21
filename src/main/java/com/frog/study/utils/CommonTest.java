package com.frog.study.utils;

import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.collections4.map.LinkedMap;
import org.junit.Test;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/06/13
 */
public class CommonTest {
    //1.Commons BeanUtils对象拷贝

    @Test
    public void commonCollections(){
        //举个简单的例子
        OrderedMap map = new LinkedMap();
        map.put("FIVE", "5");
        map.put("SIX", "6");
        map.put("SEVEN", "7");
        map.firstKey(); // returns "FIVE"
        map.nextKey("FIVE"); // returns "SIX"
        map.nextKey("SIX"); // returns "SEVEN"
    }

    @Test
    public void configTest(){
        /*这个工具是用来帮助处理配置文件的，支持很多种存储方式
        1. Properties files
        2. XML documents
        3. Property list files (.plist)
        4. JNDI
        5. JDBC Datasource
        6. System properties
        7. Applet parameters
        8. Servlet parameters
        使用示例：举一个Properties的简单例子*/

        /*# usergui.properties, definining the GUI,
        colors.background = #FFFFFF
        colors.foreground = #000080
        window.width = 500
        window.height = 300*/
//
//        PropertiesConfiguration config = new PropertiesConfiguration("usergui.properties");
//        config.setProperty("colors.background", "#000000);
//                           config.save();
//
//        config.save("usergui.backup.properties);//save a copy
//                    Integer integer = config.getInteger("window.width");

        //shc TODO : 2021/6/13 说明：Database Connection pool, Tomcat就是用的这个
    }
}
