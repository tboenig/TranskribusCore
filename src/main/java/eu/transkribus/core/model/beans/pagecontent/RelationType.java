//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.16 at 03:51:41 PM CEST 
//


package eu.transkribus.core.model.beans.pagecontent;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *     			One-to-one relation between to layout object. Use 'link'
 *     			for loose relations and 'join' for strong relations
 *     			(where something is fragmented for instance).
 * 
 *     			Examples for 'link': caption - image floating -
 *     			paragraph paragraph - paragraph (when a pragraph is
 *     			split across columns and the last word of the first
 *     			paragraph DOES NOT continue in the second paragraph)
 *     			drop-cap - paragraph (when the drop-cap is a whole word)
 * 
 *     			Examples for 'join': word - word (separated word at the
 *     			end of a line) drop-cap - paragraph (when the drop-cap
 *     			is not a whole word) paragraph - paragraph (when a
 *     			pragraph is split across columns and the last word of
 *     			the first paragraph DOES continue in the second
 *     			paragraph)
 *     		
 * 
 * <p>Java class for RelationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="2" minOccurs="2">
 *         &lt;element name="RegionRef" type="{http://schema.primaresearch.org/PAGE/gts/pagecontent/2013-07-15}RegionRefType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="link"/>
 *             &lt;enumeration value="join"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="custom" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="comments" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelationType", propOrder = {
    "regionRef"
})
public class RelationType {

    @XmlElement(name = "RegionRef", required = true)
    protected List<RegionRefType> regionRef;
    @XmlAttribute(name = "type", required = true)
    protected String type;
    @XmlAttribute(name = "custom")
    protected String custom;
    @XmlAttribute(name = "comments")
    protected String comments;

    /**
     * Gets the value of the regionRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the regionRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegionRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RegionRefType }
     * 
     * 
     */
    public List<RegionRefType> getRegionRef() {
        if (regionRef == null) {
            regionRef = new ArrayList<RegionRefType>();
        }
        return this.regionRef;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the custom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustom() {
        return custom;
    }

    /**
     * Sets the value of the custom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustom(String value) {
        this.custom = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

}
