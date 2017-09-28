package com.sinopec.opc.app;

import com.alibaba.fastjson.JSON;
import com.sinopec.opc.bean.PointValue;
import com.sinopec.opc.change.delivery;
import com.sinopec.opc.read.JdomReadXml;
import com.sinopec.opc.write.JdomWriteXml;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.List;

/**
 * @Author : Pnoker
 * @E-mail : pnokers@gmail.com
 * @Date : 2017年07月16日 12:32
 * @Description : opcXml ...
 */
@Controller
@SpringBootApplication
public class Application {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("/insertOpc")
    public String insertOpc(@RequestParam String pointName, @RequestParam String tableName, @RequestParam String idColumn, @RequestParam String id, @RequestParam String valueColumn) {
        System.out.println("insertOpc-->    pointName : " + pointName + " , tableName : " + tableName + " , idColumn : " + idColumn + " , id : " + id + " , valueColumn : " + valueColumn);
        List<PointValue> points = JdomReadXml.getPoints();
        points.add(new PointValue(pointName, tableName, idColumn, id, valueColumn));
        JdomWriteXml.genPoints(points);
        return "新增成功！";
    }

    @ResponseBody
    @RequestMapping("/deleteOpc")
    public String deleteOpc(@RequestParam String pointName) {
        System.out.println("pointName : " + pointName);
        List<PointValue> points = JdomReadXml.getPoints();
        Iterator<PointValue> iterator = points.iterator();
        while (iterator.hasNext()) {
            PointValue point = iterator.next();
            if (point.getPointName().equals(pointName)) {
                iterator.remove();
            }
        }
        JdomWriteXml.genPoints(points);
        return "Opc 点位 ：" + pointName + " 删除成功 ！";
    }

    @ResponseBody
    @RequestMapping("/updateOpc")
    public String updateOpc(@RequestParam String pointName, @RequestParam String old_pointName, @RequestParam String tableName, @RequestParam String idColumn, @RequestParam String id, @RequestParam String valueColumn) {
        System.out.println("updateOpc-->    pointName : " + pointName + " , tableName : " + tableName + " , idColumn : " + idColumn + " , id : " + id + " , valueColumn : " + valueColumn);
        List<PointValue> points = JdomReadXml.getPoints();
        Iterator<PointValue> iterator = points.iterator();
        while (iterator.hasNext()) {
            PointValue point = iterator.next();
            if (point.getPointName().equals(old_pointName)) {
                point.setPointName(pointName);
                point.setTableName(tableName);
                point.setIdColumn(idColumn);
                point.setId(id);
                point.setValueColumn(valueColumn);
            }
        }
        JdomWriteXml.genPoints(points);
        return "编辑成功！";
    }

    @ResponseBody
    @RequestMapping("/selectOpc")
    public String selectOpc(@RequestParam(required = false) String text) {
        System.out.println("text : " + text);
        List<PointValue> points = JdomReadXml.getPoints();
        if (null != text) {
            Iterator<PointValue> iterator = points.iterator();
            while (iterator.hasNext()) {
                PointValue point = iterator.next();
                if (!point.getPointName().contains(text)) {
                    iterator.remove();
                }
            }
        }
        return JSON.toJSONString(points);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        String configPath = "src/main/resources/config";
        if (args.length >= 1) {
            configPath = args[0];
            System.out.println("有路径参数，设置路径为" + configPath);
        } else {
            System.out.println("没有路径参数，默认为src/main/resources/config");
        }
        delivery del = new delivery(configPath);
        del.run();
    }
}
