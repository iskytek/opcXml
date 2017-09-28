package com.sinopec.opc.bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name = "points")
@XmlAccessorType(XmlAccessType.FIELD)
public class AllPoints extends ArrayList<Point> { // 泛化, 聚合

    @XmlElement(name = "point")
    public List<Point> getPoints() {
        return this;
    }
}