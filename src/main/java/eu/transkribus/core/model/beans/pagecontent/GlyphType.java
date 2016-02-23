//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.02.23 at 05:32:05 PM MEZ 
//


package eu.transkribus.core.model.beans.pagecontent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for GlyphType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GlyphType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Coords" type="{http://schema.primaresearch.org/PAGE/gts/pagecontent/2013-07-15}CoordsType"/>
 *         &lt;element name="TextEquiv" type="{http://schema.primaresearch.org/PAGE/gts/pagecontent/2013-07-15}TextEquivType" minOccurs="0"/>
 *         &lt;element name="TextStyle" type="{http://schema.primaresearch.org/PAGE/gts/pagecontent/2013-07-15}TextStyleType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="ligature" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="symbol" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="production" type="{http://schema.primaresearch.org/PAGE/gts/pagecontent/2013-07-15}ProductionSimpleType" />
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
@XmlType(name = "GlyphType", propOrder = {
    "coords",
    "textEquiv",
    "textStyle"
})
public class GlyphType {

    @XmlElement(name = "Coords", required = true)
    protected CoordsType coords;
    @XmlElement(name = "TextEquiv")
    protected TextEquivType textEquiv;
    @XmlElement(name = "TextStyle")
    protected TextStyleType textStyle;
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute
    protected Boolean ligature;
    @XmlAttribute
    protected Boolean symbol;
    @XmlAttribute
    protected ProductionSimpleType production;
    @XmlAttribute
    protected String custom;
    @XmlAttribute
    protected String comments;

    /**
     * Gets the value of the coords property.
     * 
     * @return
     *     possible object is
     *     {@link CoordsType }
     *     
     */
    public CoordsType getCoords() {
        return coords;
    }

    /**
     * Sets the value of the coords property.
     * 
     * @param value
     *     allowed object is
     *     {@link CoordsType }
     *     
     */
    public void setCoords(CoordsType value) {
        this.coords = value;
    }

    /**
     * Gets the value of the textEquiv property.
     * 
     * @return
     *     possible object is
     *     {@link TextEquivType }
     *     
     */
    public TextEquivType getTextEquiv() {
        return textEquiv;
    }

    /**
     * Sets the value of the textEquiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextEquivType }
     *     
     */
    public void setTextEquiv(TextEquivType value) {
        this.textEquiv = value;
    }

    /**
     * Gets the value of the textStyle property.
     * 
     * @return
     *     possible object is
     *     {@link TextStyleType }
     *     
     */
    public TextStyleType getTextStyle() {
        return textStyle;
    }

    /**
     * Sets the value of the textStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextStyleType }
     *     
     */
    public void setTextStyle(TextStyleType value) {
        this.textStyle = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the ligature property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLigature() {
        return ligature;
    }

    /**
     * Sets the value of the ligature property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLigature(Boolean value) {
        this.ligature = value;
    }

    /**
     * Gets the value of the symbol property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSymbol() {
        return symbol;
    }

    /**
     * Sets the value of the symbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSymbol(Boolean value) {
        this.symbol = value;
    }

    /**
     * Gets the value of the production property.
     * 
     * @return
     *     possible object is
     *     {@link ProductionSimpleType }
     *     
     */
    public ProductionSimpleType getProduction() {
        return production;
    }

    /**
     * Sets the value of the production property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductionSimpleType }
     *     
     */
    public void setProduction(ProductionSimpleType value) {
        this.production = value;
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
