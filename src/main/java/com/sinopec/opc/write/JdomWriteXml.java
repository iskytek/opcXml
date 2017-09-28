package com.sinopec.opc.write;

import com.sinopec.opc.bean.PointValue;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class JdomWriteXml {

    public static void genPoints(List<PointValue> points) {
        Element root = new Element("points");
        Document doc = new Document(root);

        for (int i = 0; i < points.size(); i++) {
            Element elements = new Element("point");
            elements.addContent(new Element("pointName").setText(points.get(i).getPointName()));
            elements.addContent(new Element("tableName").setText(points.get(i).getTableName()));
            elements.addContent(new Element("idColumn").setText(points.get(i).getIdColumn()));
            elements.addContent(new Element("id").setText(points.get(i).getId()));
            elements.addContent(new Element("valueColumn").setText(points.get(i).getValueColumn()));
            root.addContent(elements);
        }
        Format format = Format.getPrettyFormat();
        XMLOutputter XMLOut = new XMLOutputter(format);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/config/points.xml");
            XMLOut.output(doc, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}