//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.07.29 um 03:19:48 PM CEST 
//


package eu.transkribus.core.model.beans.pagecontent;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für GraphicsTypeSimpleType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="GraphicsTypeSimpleType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="logo"/>
 *     &lt;enumeration value="letterhead"/>
 *     &lt;enumeration value="decoration"/>
 *     &lt;enumeration value="frame"/>
 *     &lt;enumeration value="handwritten-annotation"/>
 *     &lt;enumeration value="stamp"/>
 *     &lt;enumeration value="signature"/>
 *     &lt;enumeration value="barcode"/>
 *     &lt;enumeration value="paper-grow"/>
 *     &lt;enumeration value="punch-hole"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GraphicsTypeSimpleType")
@XmlEnum
public enum GraphicsTypeSimpleType {

    @XmlEnumValue("logo")
    LOGO("logo"),
    @XmlEnumValue("letterhead")
    LETTERHEAD("letterhead"),
    @XmlEnumValue("decoration")
    DECORATION("decoration"),
    @XmlEnumValue("frame")
    FRAME("frame"),
    @XmlEnumValue("handwritten-annotation")
    HANDWRITTEN_ANNOTATION("handwritten-annotation"),
    @XmlEnumValue("stamp")
    STAMP("stamp"),
    @XmlEnumValue("signature")
    SIGNATURE("signature"),
    @XmlEnumValue("barcode")
    BARCODE("barcode"),
    @XmlEnumValue("paper-grow")
    PAPER_GROW("paper-grow"),
    @XmlEnumValue("punch-hole")
    PUNCH_HOLE("punch-hole"),
    @XmlEnumValue("other")
    OTHER("other");
    private final String value;

    GraphicsTypeSimpleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GraphicsTypeSimpleType fromValue(String v) {
        for (GraphicsTypeSimpleType c: GraphicsTypeSimpleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
