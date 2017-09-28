
package com.sinopec.opc.read;

import com.sinopec.opc.bean.PointValue;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JdomReadXml {
    public static List<PointValue> getPoints() {
        List<PointValue> points = new ArrayList<>();
        SAXBuilder sax = new SAXBuilder();
        Document doc;
        try {
            doc = sax.build(new File("src/main/resources/config/points.xml"));
            Element root = doc.getRootElement();
            List actions = root.getChildren();
            for (Iterator i = actions.iterator(); i.hasNext(); ) {
                Element action = (Element) i.next();
                points.add(new PointValue(action.getChildText("pointName"),
                        action.getChildText("tableName"),
                        action.getChildText("idColumn"),
                        action.getChildText("id"),
                        action.getChildText("valueColumn")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return points;
    }
}