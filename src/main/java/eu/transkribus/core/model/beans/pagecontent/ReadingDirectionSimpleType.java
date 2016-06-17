//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.16 at 03:51:41 PM CEST 
//


package eu.transkribus.core.model.beans.pagecontent;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReadingDirectionSimpleType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReadingDirectionSimpleType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="left-to-right"/>
 *     &lt;enumeration value="right-to-left"/>
 *     &lt;enumeration value="top-to-bottom"/>
 *     &lt;enumeration value="bottom-to-top"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReadingDirectionSimpleType")
@XmlEnum
public enum ReadingDirectionSimpleType {

    @XmlEnumValue("left-to-right")
    LEFT_TO_RIGHT("left-to-right"),
    @XmlEnumValue("right-to-left")
    RIGHT_TO_LEFT("right-to-left"),
    @XmlEnumValue("top-to-bottom")
    TOP_TO_BOTTOM("top-to-bottom"),
    @XmlEnumValue("bottom-to-top")
    BOTTOM_TO_TOP("bottom-to-top");
    private final String value;

    ReadingDirectionSimpleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReadingDirectionSimpleType fromValue(String v) {
        for (ReadingDirectionSimpleType c: ReadingDirectionSimpleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
