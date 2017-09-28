package com.sinopec.opc.change;

import com.sinopec.opc.bean.AllPoints;
import com.sinopec.opc.bean.Point;
import com.sinopec.opc.tools.DBtool;
import com.sinopec.opc.tools.JaxbReadXml;
import javafish.clients.opc.JOpc;
import javafish.clients.opc.component.OpcGroup;
import javafish.clients.opc.component.OpcItem;
import javafish.clients.opc.exception.CoInitializeException;
import javafish.clients.opc.variant.Variant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

public class delivery {
    private String configPath;
    private int interval;
    private String opcName;
    private String opcIP;

    public static void main(String[] args) {
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

    public delivery(String configPath) {
        try {
            this.configPath = configPath;
            File file = new File(configPath + "/config.txt");
            String lineTxt = null;
            InputStreamReader read = null;
            BufferedReader bufferedReader = null;
            String encoding = "UTF-8";

            //数据库相关
            String dbIP = "";
            String dbPort = "1433";
            String dbUser = "";
            String dbPwd = "";
            String dbName = "";

            //OPCServer相关
            String opcIP = "";
            String opcName = "";
            int interval = 30;

            //判断配置文件是否存在
            if (file.isFile() && file.exists()) {
                read = new InputStreamReader(new FileInputStream(file), encoding);
                bufferedReader = new BufferedReader(read);
                lineTxt = bufferedReader.readLine();
            } else {
                System.out.println("配置文件不存在");
                return;
            }

            lineTxt = bufferedReader.readLine();
            if (lineTxt != null) {
                dbIP = lineTxt;
                DBtool.ip = dbIP;
                System.out.println("dbIP:" + dbIP);
            } else {
                return;
            }
            lineTxt = bufferedReader.readLine();
            if (lineTxt != null) {
                dbPort = lineTxt;
                DBtool.port = dbPort;
                System.out.println("dbPort:" + dbPort);
            } else {
                return;
            }
            lineTxt = bufferedReader.readLine();
            if (lineTxt != null) {
                dbUser = lineTxt;
                DBtool.user = dbUser;
                System.out.println("dbUser:" + dbUser);
            } else {
                return;
            }
            lineTxt = bufferedReader.readLine();
            if (lineTxt != null) {
                dbPwd = lineTxt;
                DBtool.pwd = dbPwd;
                System.out.println("dbPwd:" + dbPwd);
            } else {
                return;
            }
            lineTxt = bufferedReader.readLine();
            if (lineTxt != null) {
                dbName = lineTxt;
                DBtool.db = dbName;
                System.out.println("dbName:" + dbName);
            } else {
                return;
            }
            lineTxt = bufferedReader.readLine();
            lineTxt = bufferedReader.readLine();
            lineTxt = bufferedReader.readLine();
            if (lineTxt != null) {
                opcIP = lineTxt;
                this.opcIP = opcIP;
                System.out.println("opcIP:" + opcIP);
            } else {
                return;
            }
            lineTxt = bufferedReader.readLine();
            if (lineTxt != null) {
                opcName = lineTxt;
                this.opcName = opcName;
                System.out.println("opcName:" + opcName);
            } else {
                return;
            }
            lineTxt = bufferedReader.readLine();
            if (lineTxt != null) {
                interval = Integer.parseInt(lineTxt);
                this.interval = interval;
                System.out.println("interval:" + interval);
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            List<String> array = new ArrayList<String>();
            AllPoints allPoints = JaxbReadXml.readString(AllPoints.class, this.configPath + "/points.xml");
            for (Point o : allPoints) {
                array.add(o.getPointName());
            }

            JOpc jopc = new JOpc(this.opcIP, this.opcName, "JOPC1");
            try {
                JOpc.coInitialize();
            } catch (CoInitializeException e1) {
                e1.printStackTrace();
            }
            OpcGroup group = new OpcGroup("sia", true, 0, 0.0f);
            OpcItem item = null;

            for (int i = 0; i < array.size(); i++) {
                item = new OpcItem(array.get(i), true, "");
                group.addItem(item);
            }

            jopc.addGroup(group);
            jopc.connect();
            jopc.registerGroups();

            List<OpcItem> itemsArray = group.getItems();

            DBtool db = new DBtool();
            ResultSet rs = null;
            String sql = "";
            Map<String, Double> tagValue = new HashMap<String, Double>();

            String pointName = "";
            String tableName = "";
            String idColumn = "";
            String id = "";
            String valueColumn = "";
            double pointValue = 0;

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while (true) {
                System.out.println(sdf2.format(new Date()));
                for (Point o : allPoints) {
                    pointName = o.getPointName();
                    tableName = o.getTableName();
                    idColumn = o.getIdColumn();
                    id = o.getId();
                    valueColumn = o.getValueColumn();

                    sql = "select * from " + tableName + " where " + idColumn + " = '" + id + "'";
                    System.out.println(sql);
                    rs = db.executeQuery(sql);
                    rs.next();
                    tagValue.put(pointName, rs.getDouble(valueColumn));
                }

                for (int i = 0; i < itemsArray.size(); i++) {
                    item = itemsArray.get(i);
                    pointName = item.getItemName();
                    pointValue = tagValue.get(pointName);
                    System.out.println("name:" + pointName + ", value:" + pointValue);
                    item.setValue(new Variant(pointValue));
                    jopc.synchWriteItem(group, item);
                    Thread.sleep(100);
                }

                System.out.println("sleep for " + this.interval + " seconds");
                Thread.sleep(this.interval * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
