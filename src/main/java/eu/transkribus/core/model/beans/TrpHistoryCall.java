package eu.transkribus.core.model.beans;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="HISTORY")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TrpHistoryCall {
	@Id
	@Column(name="HISTORY_ID")
	private int id;
	
	@Column
	private int session_history_id;
	
	@Column
	private Timestamp time;
	
	@Column
	private String call;
	
	@Column
	private String parameter;
	
	@Column
	private String custom;
	
	@Column
	private Integer collId;
	
	@Column
	private Integer docId;
	
	@Column
	private Integer pageNr;
	
	public TrpHistoryCall() {}

	public TrpHistoryCall(int id, int session_history_id, Timestamp time, String call, String parameter, String custom) {
		super();
		this.id = id;
		this.session_history_id = session_history_id;
		this.time = time;
		this.call = call;
		this.parameter = parameter;
		this.custom = custom;
	}

	public TrpHistoryCall(TrpHistoryCall other) {
		super();
		this.id = other.id;
		this.session_history_id = other.session_history_id;
		this.time = other.time;
		this.call = other.call;
		this.parameter = other.parameter;
		this.custom = other.custom;
	}

	// bean getters & setters:
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSession_history_id() {
		return session_history_id;
	}

	public void setSession_history_id(int session_history_id) {
		this.session_history_id = session_history_id;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public Integer getCollId() {
		return collId;
	}

	public void setCollId(Integer collId) {
		this.collId = collId;
	}

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public Integer getPageNr() {
		return pageNr;
	}

	public void setPageNr(Integer pageNr) {
		this.pageNr = pageNr;
	}

}
