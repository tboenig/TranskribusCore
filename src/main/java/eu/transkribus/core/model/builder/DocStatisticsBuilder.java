package eu.transkribus.core.model.builder;

import java.net.URL;
import java.util.List;
import java.util.Observable;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.transkribus.core.model.beans.TrpDoc;
import eu.transkribus.core.model.beans.TrpDocStatistics;
import eu.transkribus.core.model.beans.TrpPage;
import eu.transkribus.core.model.beans.pagecontent.PcGtsType;
import eu.transkribus.core.model.beans.pagecontent_trp.TrpPageType;
import eu.transkribus.core.util.PageXmlUtils;

public class DocStatisticsBuilder extends Observable {
	private static final Logger logger = LoggerFactory.getLogger(DocStatisticsBuilder.class);
	public TrpDocStatistics compute(TrpDoc doc) throws JAXBException{
		if(doc == null){
			throw new IllegalArgumentException("TrpDoc is null!");
		}
		TrpDocStatistics stats = new TrpDocStatistics();
		stats.setNrOfPages(doc.getMd().getNrOfPages());
		List<TrpPage> pages = doc.getPages();
		for(TrpPage p : pages){
			final String msg = "Computing stats: page " + p.getPageNr() + "/" + pages.size();
			logger.debug(msg);
			notifyObservers(msg);
			setChanged();
			URL xmlUrl = p.getCurrentTranscript().getUrl();
			PcGtsType pc = PageXmlUtils.unmarshal(xmlUrl);
			TrpPageType page = (TrpPageType)pc.getPage();
			stats.setNrOfLines(stats.getNrOfLines() + page.countLines());
			stats.setNrOfWords(stats.getNrOfWords() + page.countWords());
			stats.setNrOfTextRegions(stats.getNrOfTextRegions() + page.countTextRegions());
		}
		
		return stats;
	}
}
