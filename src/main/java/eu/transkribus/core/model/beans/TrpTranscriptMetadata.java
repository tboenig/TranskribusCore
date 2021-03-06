package eu.transkribus.core.model.beans;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import eu.transkribus.core.exceptions.NullValueException;
import eu.transkribus.core.model.beans.adapters.EditStatusAdapter;
import eu.transkribus.core.model.beans.enums.EditStatus;
import eu.transkribus.core.model.beans.pagecontent.PcGtsType;
import eu.transkribus.core.util.CoreUtils;
import eu.transkribus.core.util.PageXmlUtils;

/**
 * Includes the data of a single transcript version for a page.
 * <br/><br/>
 * TODO: DbUtils retrieves only fields declared, so extending TrpTranscriptStatistics
 * and removing the respective inherited fields here does not work!
 * 
 * @author philip
 *
 */
@Entity
@Table(name = "transcripts")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TrpTranscriptMetadata extends ATranscriptStatistics implements ITrpFile, Serializable, Comparable<TrpTranscriptMetadata>  {
	private static final long serialVersionUID = 1L;
	
	static DateFormat timeFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	@XmlTransient
	TrpPage pageReferenceForLocalDocs;
	
	@Id
	@Column(name = "TSID")
	private int tsId = -1;
	
	@Column(name = "PARENT_TSID")
	private int parentTsId = -1;
	
	@Column(name = "xmlkey")
	private String key = null; //the fimagestore key for getting the XML
	
	@Column
	private int pageId = -1;
	
	@Transient
	@Column
	private int docId = -1;
	
	@Transient
	@Column
	private int pageNr = -1;
	
	@Transient
	private File localFolder = null; // != null when local document
	
	@Column
	@Transient
	private URL url;
	
	@Column
	@XmlJavaTypeAdapter(EditStatusAdapter.class)
	private EditStatus status;
	@Column(name="userId")
	private String userName;
	@Column(name="user_id")
	private int userId;
//	@XmlJavaTypeAdapter(DateAdapter.class)
//	private Date time = new Date();
	@Column
	private long timestamp = System.currentTimeMillis();
	
	@Column
	private String toolName = null;
	
	@Column 
	private Integer jobId;
	
	@Column(name="NOTE")
	private String note = "";
	
	private String md5Sum = "";

	@Column(name=N_REGIONS_COL_NAME)
	private Integer nrOfRegions = 0;

	@Column(name=N_TRANSCRIBED_REGIONS_COL_NAME)
	private Integer nrOfTranscribedRegions = 0;

	@Column(name=N_WORDS_IN_REGIONS_COL_NAME)
	private Integer nrOfWordsInRegions = 0;
	
	@Column(name=N_LINES_COL_NAME)
	private Integer nrOfLines = 0;

	@Column(name=N_TRANSCRIBED_LINES_COL_NAME)
	private Integer nrOfTranscribedLines = 0;

	@Column(name=N_WORDS_IN_LINES_COL_NAME)
	private Integer nrOfWordsInLines = 0;

	@Column(name=N_WORDS_COL_NAME)
	private Integer nrOfWords = 0;

	@Column(name=N_TRANSCRIBED_WORDS_COL_NAME)
	private Integer nrOfTranscribedWords = 0;
	
	@Column
	@Transient
	private Integer gtId = null;
	
	//TODO tags
	//TODO annotations

	public TrpTranscriptMetadata() {
	}
	
	
	
	public TrpTranscriptMetadata(final int tsId, final String key, final int pageId, 
			final long timestamp, final int userId, final String userName, final EditStatus status, final int parentId, final String note) {
		this.tsId = tsId;
		this.key = key;
		this.pageId = pageId;
		this.timestamp = timestamp;
		this.userId = userId;
		this.userName = userName;
		this.status = status;
		this.parentTsId = parentId;
		this.note = note;
	}

	public TrpTranscriptMetadata(TrpTranscriptMetadata m, TrpPage pageReferenceForLocalDocs) {
		this();
		this.pageReferenceForLocalDocs = pageReferenceForLocalDocs;
		tsId = m.getTsId();
		parentTsId = m.getParentTsId();
		key = m.getKey();
		pageId = m.getPageId();
		docId = m.getDocId();
		pageNr = m.getPageNr();
		localFolder = m.getLocalFolder();
		url = m.getUrl();
		status = m.getStatus();
		userName = m.getUserName();
		userId = m.getUserId();
		timestamp = m.getTimestamp();
		toolName = m.getToolName();
		jobId = m.getJobId();
		note = m.getNote();
		md5Sum = m.getMd5Sum();
		this.setStats(m.getStats());
	}

	public int getTsId() {
		return tsId;
	}

	public void setTsId(int tsId) {
		this.tsId = tsId;
	}

	public int getParentTsId() {
		return parentTsId;
	}

	public void setParentTsId(int parentTsId) {
		this.parentTsId = parentTsId;
	}

	public File getLocalFolder() {
		return localFolder;
	}

	public void setLocalFolder(File localFolder) {
		this.localFolder = localFolder;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String xmlKey) {
		this.key = xmlKey;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}
	
	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public int getPageNr() {
		return pageNr;
	}

	public void setPageNr(int pageNr) {
		this.pageNr = pageNr;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL xmlUrl) {
		this.url = xmlUrl;
	}

	public String getXmlFileName() {
		String name = null;
		if(this.isLocalTranscript()) {
			name = this.getFile().getName();
		}
		return name;
	}
	
	public EditStatus getStatus() {
		return status;
	}

	public void setStatus(EditStatus status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Date getTime() {
		return new Date(this.timestamp);
	}
	
	public String getTimeFormatted() {
		return timeFormatter.format(getTime());
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setTime(Date time) {
		this.timestamp = time.getTime();
	}
	
	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	
	public Integer getJobId() {
		return jobId;
	}
	
	public void setJobId(Integer jobId) {
		this.jobId= jobId;
	}
	
	public String getNote(){
		return note;
	}
	
	public void setNote(String note){
		this.note = note;
	}

	public String getMd5Sum() {
		return this.md5Sum;
	}

	public void setMd5Sum(String md5Sum) {
		this.md5Sum = md5Sum;
	}
	
	public TrpPage getPagePageReferenceForLocalDocs() {
		return pageReferenceForLocalDocs;
	}
	
	public void setPageReferenceForLocalDocs(TrpPage pageReferenceForLocalDocs) {
		this.pageReferenceForLocalDocs = pageReferenceForLocalDocs;
	}

	public Integer getNrOfRegions() {
		return nrOfRegions;
	}
	public void setNrOfRegions(Integer nrOfRegions) {
		this.nrOfRegions = nrOfRegions;
	}
	public Integer getNrOfTranscribedRegions() {
		return nrOfTranscribedRegions;
	}
	public void setNrOfTranscribedRegions(Integer nrOfTranscribedRegions) {
		this.nrOfTranscribedRegions = nrOfTranscribedRegions;
	}
	public Integer getNrOfWordsInRegions() {
		return nrOfWordsInRegions;
	}
	public void setNrOfWordsInRegions(Integer nrOfWordsInRegions) {
		this.nrOfWordsInRegions = nrOfWordsInRegions;
	}
	public Integer getNrOfLines() {
		return nrOfLines;
	}
	public void setNrOfLines(Integer nrOfLines) {
		this.nrOfLines = nrOfLines;
	}
	public Integer getNrOfTranscribedLines() {
		return nrOfTranscribedLines;
	}
	public void setNrOfTranscribedLines(Integer nrOfTranscribedLines) {
		this.nrOfTranscribedLines = nrOfTranscribedLines;
	}
	public Integer getNrOfWordsInLines() {
		return nrOfWordsInLines;
	}
	public void setNrOfWordsInLines(Integer nrOfWordsInLines) {
		this.nrOfWordsInLines = nrOfWordsInLines;
	}
	public Integer getNrOfWords() {
		return nrOfWords;
	}
	public void setNrOfWords(Integer nrOfWords) {
		this.nrOfWords = nrOfWords;
	}
	public Integer getNrOfTranscribedWords() {
		return nrOfTranscribedWords;
	}
	public void setNrOfTranscribedWords(Integer nrOfTranscribedWords) {
		this.nrOfTranscribedWords = nrOfTranscribedWords;
	}
	
	public Integer getGtId() {
		return gtId;
	}
	
	public void setGtId(Integer gtId) {
		this.gtId = gtId;
	}
	
	/**
	 * Check key and URL protocol.<br/>
	 * This method returns false, if there is a filekey set.<br/>
	 * It returns true, if there is no filekey and the URL points to an existing file.<br/>
	 * 
	 * @return
	 * @throws IllegalStateException in case there are inconsistencies:
	 * <ul>
	 * <li>key of transcript is null, but the URL protocol is not "file://"</li>
	 * <li>key of transcript is null, but the file URL does not point to an existing file</li>
	 * </ul>
	 */
	public boolean isLocalTranscript() {
		return isLocalFile();
	}

	@Override 
	public boolean equals(Object o) {
		// FIXME ?? (not tested)
		
		if (o==null || !(o instanceof TrpTranscriptMetadata))
			return false;
		
		TrpTranscriptMetadata m = (TrpTranscriptMetadata) o;
		if (pageId != m.pageId)
			return false;
		if (!CoreUtils.equalsObjects(key, m.key))
			return false;
		if (!CoreUtils.equalsObjects(localFolder, m.localFolder))
			return false;
		
		int c = compareTo(m);
		if (c != 0)
			return false;
		
		return true;
	}
	
	/**
	 * Uses the timestamp for comparison
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(TrpTranscriptMetadata md) {
		int result = 0;
		if (this.getTimestamp() > md.getTimestamp()) {
			result = 1;
		} else if (this.getTimestamp() < md.getTimestamp()) {
			result = -1;
		}
		return result;
	}

	/**
	 * This method is just for testing equivalence of documents selected via different DocManager methods
	 * @param obj
	 * @return
	 */
	public boolean testEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrpTranscriptMetadata other = (TrpTranscriptMetadata) obj;
		if (docId != other.docId)
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (localFolder == null) {
			if (other.localFolder != null)
				return false;
		} else if (!localFolder.equals(other.localFolder))
			return false;
		if (md5Sum == null) {
			if (other.md5Sum != null)
				return false;
		} else if (!md5Sum.equals(other.md5Sum))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (nrOfLines == null) {
			if (other.nrOfLines != null)
				return false;
		} else if (!nrOfLines.equals(other.nrOfLines))
			return false;
		if (nrOfRegions == null) {
			if (other.nrOfRegions != null)
				return false;
		} else if (!nrOfRegions.equals(other.nrOfRegions))
			return false;
		if (nrOfTranscribedLines == null) {
			if (other.nrOfTranscribedLines != null)
				return false;
		} else if (!nrOfTranscribedLines.equals(other.nrOfTranscribedLines))
			return false;
		if (nrOfTranscribedRegions == null) {
			if (other.nrOfTranscribedRegions != null)
				return false;
		} else if (!nrOfTranscribedRegions.equals(other.nrOfTranscribedRegions))
			return false;
		if (nrOfTranscribedWords == null) {
			if (other.nrOfTranscribedWords != null)
				return false;
		} else if (!nrOfTranscribedWords.equals(other.nrOfTranscribedWords))
			return false;
		if (nrOfWords == null) {
			if (other.nrOfWords != null)
				return false;
		} else if (!nrOfWords.equals(other.nrOfWords))
			return false;
		if (nrOfWordsInLines == null) {
			if (other.nrOfWordsInLines != null)
				return false;
		} else if (!nrOfWordsInLines.equals(other.nrOfWordsInLines))
			return false;
		if (nrOfWordsInRegions == null) {
			if (other.nrOfWordsInRegions != null)
				return false;
		} else if (!nrOfWordsInRegions.equals(other.nrOfWordsInRegions))
			return false;
		if (pageId != other.pageId)
			return false;
		if (pageNr != other.pageNr)
			return false;
		if (pageReferenceForLocalDocs == null) {
			if (other.pageReferenceForLocalDocs != null)
				return false;
		} else if (!pageReferenceForLocalDocs.equals(other.pageReferenceForLocalDocs))
			return false;
		if (parentTsId != other.parentTsId)
			return false;
		if (status != other.status)
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (toolName == null) {
			if (other.toolName != null)
				return false;
		} else if (!toolName.equals(other.toolName))
			return false;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
			return false;
		if (tsId != other.tsId)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (userId != other.userId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	public PcGtsType unmarshallTranscript() throws NullValueException, JAXBException {
		if (getUrl()==null)
			throw new NullValueException("URL of transcript is null!");
				
		return PageXmlUtils.unmarshal(getUrl());	
	}

	@Override
	public String toString() {
		return "TrpTranscriptMetadata [tsId=" + tsId
				+ ", parentTsId=" + parentTsId + ", key=" + key + ", pageId=" + pageId + ", docId=" + docId
				+ ", pageNr=" + pageNr + ", localFolder=" + localFolder + ", url=" + url + ", status=" + status
				+ ", userName=" + userName + ", userId=" + userId + ", timestamp=" + timestamp + ", toolName="
				+ toolName + ", jobId=" + jobId + ", note=" + note + ", md5Sum=" + md5Sum + ", nrOfRegions=" + nrOfRegions
				+ ", nrOfTranscribedRegions=" + nrOfTranscribedRegions + ", nrOfWordsInRegions=" + nrOfWordsInRegions
				+ ", nrOfLines=" + nrOfLines + ", nrOfTranscribedLines=" + nrOfTranscribedLines + ", nrOfWordsInLines="
				+ nrOfWordsInLines + ", nrOfWords=" + nrOfWords + ", nrOfTranscribedWords=" + nrOfTranscribedWords
				+ ", gtId=" + gtId + "]";
	}
}
