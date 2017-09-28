package com.sinopec.opc.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @Author : Pnoker
 * @E-mail : pnokers@gmail.com
 * @Date : 2017年07月16日 16:13
 * @Description : opcXml ...
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Point {
    @XmlElement(name = "pointName")
    private String pointName;
    @XmlElement(name = "tableName")
    private String tableName;
    @XmlElement(name = "idColumn")
    private String idColumn;
    @XmlElement(name = "id")
    private String id;
    @XmlElement(name = "valueColumn")
    private String valueColumn;

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(String idColumn) {
        this.idColumn = idColumn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValueColumn() {
        return valueColumn;
    }

    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }
}