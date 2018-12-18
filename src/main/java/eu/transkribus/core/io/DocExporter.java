package eu.transkribus.core.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.dea.fimgstoreclient.IFimgStoreGetClient;
import org.dea.fimgstoreclient.beans.ImgType;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.itextpdf.text.DocumentException;

import eu.transkribus.core.misc.APassthroughObservable;
import eu.transkribus.core.model.beans.JAXBPageTranscript;
import eu.transkribus.core.model.beans.TrpDoc;
import eu.transkribus.core.model.beans.TrpDocMetadata;
import eu.transkribus.core.model.beans.TrpGroundTruthPage;
import eu.transkribus.core.model.beans.TrpPage;
import eu.transkribus.core.model.beans.TrpTranscriptMetadata;
import eu.transkribus.core.model.beans.mets.Mets;
import eu.transkribus.core.model.beans.pagecontent.MetadataType;
import eu.transkribus.core.model.beans.pagecontent.TranskribusMetadataType;
import eu.transkribus.core.model.builder.CommonExportPars;
import eu.transkribus.core.model.builder.ExportCache;
import eu.transkribus.core.model.builder.alto.AltoExporter;
import eu.transkribus.core.model.builder.docx.DocxBuilder;
import eu.transkribus.core.model.builder.mets.TrpMetsBuilder;
import eu.transkribus.core.model.builder.ms.TrpXlsxBuilder;
import eu.transkribus.core.model.builder.ms.TrpXlsxTableBuilder;
import eu.transkribus.core.model.builder.pdf.PdfExporter;
import eu.transkribus.core.model.builder.tei.TeiExportPars;
import eu.transkribus.core.model.builder.txt.TrpTxtBuilder;
import eu.transkribus.core.util.CoreUtils;
import eu.transkribus.core.util.JaxbUtils;

public class DocExporter extends APassthroughObservable {
	private static final Logger logger = LoggerFactory.getLogger(DocExporter.class);
	
	private static final String PAGE_TO_TEI_XSLT = "xslt/page2tei-0.xsl";
	
	private final ExportCache cache;
	private final AltoExporter altoEx;
	private final IFimgStoreGetClient getter;
	
	private CommonExportPars pars;
	private OutputDirStructure outputDir;
	
	public DocExporter(IFimgStoreGetClient getClient) {
		this(getClient, new ExportCache());
	}
	
	public DocExporter(IFimgStoreGetClient getClient, ExportCache cache) {
		if(getClient == null) {
			throw new IllegalArgumentException("FImagestoreClient is null!");
		}
		if(cache == null) {
			this.cache = new ExportCache();
		} else {
			this.cache = cache;
		}
        altoEx = new AltoExporter();
        getter = getClient;
	}
	
	/**
	 * Export raw document to local directory according to set parameters
	 * @param doc raw document to export
	 * @param dir target directory on local machine
	 * @param doOverwrite 
	 * @param pageIndices indices of pages to export
	 * @param exportImg if set images will be exported
	 * @param exportPage if set transcripts will be exported
	 * @param exportAlto if set alto format will be exported
	 * @param splitIntoWordsInAlto
	 * @param fileNamePattern
	 * @param imgType the image type to export for remote documents
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws URISyntaxException
	 * @throws JAXBException
	 * @throws TransformerException
	 */
	public File writeRawDoc(TrpDoc doc, final String dir, boolean doOverwrite, Set<Integer> pageIndices, 
			boolean exportImg, boolean exportPage, boolean exportAlto, boolean splitIntoWordsInAlto, 
			String fileNamePattern, ImgType imgType) throws IOException,
			IllegalArgumentException, URISyntaxException, JAXBException, TransformerException {
		CommonExportPars pars = new CommonExportPars();
		pars.setDoWriteMets(true);
		pars.setDoExportPageXml(exportPage);
		pars.setDoExportAltoXml(exportAlto);
		pars.setDoWriteImages(exportImg);
		pars.setUseOcrMasterDir(false);
		pars.setSplitIntoWordsInAltoXml(splitIntoWordsInAlto);
		pars.setPages(CoreUtils.getRangeListStrFromSet(pageIndices));
		pars.setDir(dir);
		pars.setDoOverwrite(doOverwrite);
		pars.setRemoteImgQuality(imgType);
		
		if(fileNamePattern != null) {
			pars.setFileNamePattern(fileNamePattern);
		}
		
		return exportDoc(doc, pars);
	}
	
	public void writePDF(final TrpDoc doc, final String path, Set<Integer> pageIndices, final boolean addTextPages, final boolean imagesOnly, final boolean highlightTags, final boolean wordBased, final boolean doBlackening, boolean createTitle, ExportCache cache, final String font, final ImgType pdfImgType) throws MalformedURLException, DocumentException, IOException, JAXBException, URISyntaxException, InterruptedException{
		PdfExporter pdfWriter = new PdfExporter();
		pdfWriter.export(doc, path, pageIndices, wordBased, addTextPages, imagesOnly, highlightTags, doBlackening, createTitle, cache, font, pdfImgType);
	}
	
	public void writeTEI(final TrpDoc doc, final String exportFilename, CommonExportPars commonPars, final TeiExportPars pars) throws Exception{
		/*
		 * old TEI export
		 */
//		ATeiBuilder builder = new TrpTeiStringBuilder(doc, commonPars, pars, null);
//		builder.addTranscriptsFromCache(cache);
//		builder.buildTei();
//		builder.writeTeiXml(new File(path));
		
		/*
		 * from now on we take the 'TEI base export' from Dario Kampaskar 'https://github.com/dariok/page2tei'
		 * 
		 * either read the already exported mets or temporarly export a mets with page files
		 */
		Mets mets;
		
		//create copy of object, as we alter it here while exporting
		TrpDoc doc2;
		doc2 = new TrpDoc(doc);
		
		File workDir = new File(new File(exportFilename).getParentFile().getAbsolutePath() + File.separator + "tmpDirForTeiExport_" + doc2.getId() + File.separator);
		workDir.mkdirs();	
		File pageDir = new File(workDir.getAbsolutePath() + File.separator + "page" );
		pageDir.mkdir();
		
		logger.debug("work Dir is " + workDir);
		
		TrpDocMetadata dmd = doc2.getMd();
		dmd.setLocalFolder(workDir);
		doc2.setMd(dmd);
		
		Set<Integer> pageIndices= commonPars.getPageIndices(doc2.getNPages());
//		if (commonPars.isDoWriteMets() && new File(commonPars.getDir()).exists()){
//			
//			File metsFile = new File(commonPars.getDir() + File.separator
//					+ TrpMetsBuilder.METS_FILE_NAME);
//			//File metsFile = new File(metsPath);
//			try {
//				mets = JaxbUtils.unmarshal(metsFile, Mets.class, null);
//				//transformTei(mets);
//			} catch (JAXBException e) {
//				throw new IOException("Could not unmarshal METS file!", e);
//			}
//		}
//		else{
		
		
		// do export for all defined pages
		for (int i=0; i<doc2.getNPages(); ++i) {
			if (pageIndices!=null && !pageIndices.contains(i)) {
				continue;
			}
			
			TrpPage p = doc2.getPages().get(i);
			File xmlFile = null;
							
			//if filennamepattern is empty the filename is taken as name
			final String baseFileName = ExportFilePatternUtils.buildBaseFileName("", p);
			final String xmlExt = ".xml";
			
			TrpTranscriptMetadata transcriptMd = p.getCurrentTranscript();
			JAXBPageTranscript transcript = cache.getPageTranscriptAtIndex(i);
			
			// set up transcript metadata
			if(transcript == null) {
				logger.warn("Have to unmarshall transcript in DocExporter for transcript "+transcriptMd+" - should have been built before using ExportUtils::storePageTranscripts4Export!");
				transcript = new JAXBPageTranscript(transcriptMd);
				transcript.build();
			}
			
			xmlFile = new File(FilenameUtils.normalizeNoEndSeparator(pageDir.getAbsolutePath()) + File.separator + baseFileName + xmlExt);
			logger.debug("PAGE XMl output file: "+xmlFile.getAbsolutePath());
			transcript.write(xmlFile);
			
			/*
			 * section for setting the relative path to the image instead of the (remote) filestore URL 
			 */
			File imgFile = new File(workDir.getAbsolutePath() + File.separator + p.getImgFileName());
			p.setUrl(imgFile.toURI().toURL());
			p.setKey(null);
			
			// make sure (for other exports) that the transcript that is exported is the only one set in the transcripts list of TrpPage
			p.getTranscripts().clear();
			TrpTranscriptMetadata tCopy = new TrpTranscriptMetadata(transcriptMd, p);
			tCopy.setUrl(xmlFile.toURI().toURL());
			p.getTranscripts().add(tCopy);
		}
		
		/*
		 * use temporary stored mets and page files
		 */
		TrpMetsBuilder metsBuilder = new TrpMetsBuilder();
		mets = metsBuilder.buildMets(doc2, true, false, true, pageIndices);

//		}
		
		logger.debug("workDir = " + workDir.getAbsolutePath());
		transformTei(mets, workDir.getAbsolutePath()+"/", exportFilename);
		
		for (File file : new File(pageDir.getAbsolutePath()+"/").listFiles()){
			if (!file.delete()){
				logger.warn("Failed to delete files in the temporary export folder: " + workDir);
			}
		}
		pageDir.delete();
		workDir.delete();
		
		
	}
	
	public void writeDocx(final TrpDoc doc, final String path, Set<Integer> pageIndices, final boolean highlightTags, final boolean wordBased, final boolean doBlackening, final boolean createTitle, boolean doDocxMarkUnclear, boolean doDocxExpandAbbrevs, boolean doDocxSubstituteAbbrevs, boolean doDocxPreserveLineBreaks, boolean doDocxForcePageBreaks) throws MalformedURLException, DocumentException, IOException, JAXBException, URISyntaxException, InterruptedException, Docx4JException{
		//last two params are for supplied handling, needs to be added to server esport as well. In the meanwhile args are false by default
		DocxBuilder docxBuilder = new DocxBuilder();
		docxBuilder.writeDocxForDoc(doc, wordBased, highlightTags, doBlackening, new File(path), pageIndices, null, createTitle, doDocxMarkUnclear, doDocxExpandAbbrevs, doDocxSubstituteAbbrevs, doDocxPreserveLineBreaks, doDocxForcePageBreaks, false, false, cache);
	}
	
	public void writeTxt(final TrpDoc doc, final String path, Set<Integer> pageIndices, final boolean createTitle, final boolean wordBased, boolean preserveLineBreaks) throws MalformedURLException, DocumentException, IOException, JAXBException, URISyntaxException, InterruptedException, Docx4JException{
		TrpTxtBuilder txtBuilder = new TrpTxtBuilder();
		txtBuilder.writeTxtForDoc(doc, createTitle, wordBased, preserveLineBreaks, new File(path), pageIndices, null, cache);
	}
	
	public void writeTagExcel(final TrpDoc doc, final String path, Set<Integer> pageIndices, boolean wordBased) throws Exception{
		TrpXlsxBuilder xlsxBuilder = new TrpXlsxBuilder();
		xlsxBuilder.writeXlsxForDoc(doc, wordBased, new File(path), pageIndices, null, cache);
	}
	
	public void writeTableExcel(final TrpDoc doc, final String path, Set<Integer> pageIndices) throws Exception{
		TrpXlsxTableBuilder xlsxTableBuilder = new TrpXlsxTableBuilder();
		xlsxTableBuilder.writeXlsxForTables(doc, new File(path), pageIndices, null, cache);
	}
	
	/*
	 * first shot to get the TEI export as a transformation of the page XML with a predefined XSLT
	 * test and make it available via the rest API for the server export 
	 */
	public File transformTei(Mets mets, String workDir, String exportFilename) throws JAXBException, TransformerException, IOException, SAXException, ParserConfigurationException {
		if(mets == null){
			throw new IllegalArgumentException("An argument is null!");
		}
						
		StreamSource mySrc = new StreamSource();
		mySrc.setInputStream(new ByteArrayInputStream(JaxbUtils.marshalToBytes(mets, TrpDocMetadata.class)));
		
		//necessary to use the relative paths of the mets in the xslt
		mySrc.setSystemId(workDir);
		
		InputStream is = DocExporter.class.getClassLoader().getResourceAsStream(PAGE_TO_TEI_XSLT);
		
		InputStream xslIS = new BufferedInputStream(is);

		
		DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
		dFactory.setNamespaceAware(true);
		DocumentBuilder dBuilder = dFactory.newDocumentBuilder();

        InputSource xslInputSource = new InputSource(xslIS);
        Document xslDoc = dBuilder.parse(xslInputSource);
        DOMSource xslDomSource = new DOMSource(xslDoc);
		
        TransformerFactory transFact = TransformerFactory.newInstance();
        
        //may? this is the only way to dynamically include a xsl in the xsl-source
        transFact.setURIResolver(new MyURIResolver(dBuilder));

        //would be the short way from MyURIResolver: lambda expression
//        transFact.setURIResolver((href, base) -> {
//            final InputStream s = DocExporter.class.getClassLoader().getResourceAsStream("xslt/" + href);
//            return new StreamSource(s);
//        });
                
        Transformer trans = transFact.newTransformer(xslDomSource);
				
		File teiFile = new File(exportFilename);			
		trans.transform(mySrc, new StreamResult(new FileOutputStream(teiFile)));
		
		return teiFile;
		
	}

	/**
	 * Export current document with the provided parameters.
	 * @param doc current document
	 * @param pars export settings 
	 * @return directory to which the export files were written 
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws URISyntaxException
	 * @throws JAXBException
	 * @throws TransformerException
	 */
	public File exportDoc(TrpDoc doc, CommonExportPars pars) throws IOException, IllegalArgumentException,
			URISyntaxException, JAXBException, TransformerException {		
		//create copy of object, as we alter it here while exporting
		TrpDoc doc2 = new TrpDoc(doc);
		
		this.init(pars);

		// check and write metadata
		if (doc2.getMd() != null) {
			
			/*
			 * TODO this should not write the metadata file but the doc.xml in the end.
			 * Correct save can be checked with LocalDocReader which should then pick up the doc.xml and not import the doc again.
			 */
			
			File fileOut = new File(outputDir.getRootOutputDir().getAbsolutePath() + File.separatorChar
					+ LocalDocConst.METADATA_FILENAME);
			try {
				JaxbUtils.marshalToFile(doc2.getMd(), fileOut);
			} catch (JAXBException e) {
				throw new IOException("Could not marshal metadata to file.", e);
			}
		}

		List<TrpPage> pages = doc2.getPages();
		Set<Integer> pageIndices = pars.getPageIndices(doc.getNPages());
		
		// do export for all defined pages
		for (int i=0; i<pages.size(); ++i) {
			if (pageIndices!=null && !pageIndices.contains(i)) {
				continue;
			}
			exportPage(pages.get(i));
		}
		
		if (pars.isDoWriteMets()) {
			//load the exported doc from its new location
			//FIXME this does not work for export of PAGE XMLs only!
//			final TrpDoc localDoc = LocalDocReader.load(outputDir.getAbsolutePath(), false);
			
			//set local folder or else TrpMetsBuilder will treat this as remote doc!
			doc2.getMd().setLocalFolder(outputDir.getRootOutputDir());
			//write mets with file pointers to local files
			TrpMetsBuilder metsBuilder = new TrpMetsBuilder();
			Mets mets = metsBuilder.buildMets(doc2, pars.isDoExportPageXml(), pars.isDoExportAltoXml(), pars.isDoWriteImages(), pageIndices);
			File metsFile = new File(outputDir.getRootOutputDir().getAbsolutePath() + File.separator
					+ TrpMetsBuilder.METS_FILE_NAME);
	
			try {
				JaxbUtils.marshalToFile(mets, metsFile, TrpDocMetadata.class);
			} catch (JAXBException e) {
				throw new IOException("Could not marshal METS to file!", e);
			}
		}
		
		return outputDir.getRootOutputDir();
	}
	
	/**
	 * Set output directories according to parameters and create them.
	 * 
	 * @param pars
	 * @throws IOException
	 */
	public void init(CommonExportPars pars) throws IOException {
		this.pars = pars;
		// check and create output directory
		File rootOutputDir = new File(pars.getDir());
		if (!pars.isDoOverwrite() && rootOutputDir.exists()) {
			throw new IOException("File path already exists.");
		}
		rootOutputDir.mkdir();
		
		//decide where to put the images
		final File imgOutputDir;
		if (pars.isUseOcrMasterDir()) {
			imgOutputDir = new File(rootOutputDir.getAbsolutePath(), LocalDocConst.OCR_MASTER_DIR);
			imgOutputDir.mkdir();
		} else {
			imgOutputDir = rootOutputDir;
		}
		
		File pageOutputDir = null, altoOutputDir = null;
		
		// check PAGE export settings and create output directory
		String pageDirName = pars.getPageDirName();
		if (pars.isDoExportPageXml() && !StringUtils.isEmpty(pageDirName)) {
			pageOutputDir = new File(rootOutputDir.getAbsolutePath() + File.separatorChar + pageDirName);
			if (pageOutputDir.mkdir()){
				logger.debug("pageOutputDir created successfully ");
			}
			else{
				logger.debug("pageOutputDir could not be created!");
			}
		} else {
			//if pageDirName is not set, export the PAGE XMLs to imgOutputDir
			pageOutputDir = imgOutputDir;
		}
		// check Alto export settings and create output directory
		if (pars.isDoExportAltoXml()){
			altoOutputDir = altoEx.createAltoOuputDir(rootOutputDir.getAbsolutePath());
		}
		outputDir = new OutputDirStructure(rootOutputDir, imgOutputDir, pageOutputDir, altoOutputDir);
	}

	/**
	 * Exports files for a ground truth entity, according to the parameters set on this DocExporter instance.
	 * <br><br>
	 * If the parameters include a export filename pattern, note that the document ID of ground truth is never set (i.e. "docId == -1").
	 * To avoid collisions, rather use the page ID in a pattern ({@link TrpGroundTruthPage#getOriginPageId()} will be used), 
	 * instead of a doc. ID + page nr. combination.
	 *  
	 * @param gtPage
	 * @throws IOException
	 */
	public void exportPage(TrpGroundTruthPage gtPage) throws IOException {
		exportPage(gtPage.toTrpPage());
	}
	
	public void exportPage(TrpPage page) throws IOException {
		if(pars == null || outputDir == null) {
			throw new IllegalStateException("Export parameters are not set or output directory has not been initialized!");
		}
		
		File imgFile = null, xmlFile = null, altoFile = null;
		
		URL imgUrl = page.getUrl(); 
		
		final String baseFileName;
		final String imgExt = "." + FilenameUtils.getExtension(page.getImgFileName());
		final String xmlExt = ".xml";
		
		// gather remote files and export document
		if (!page.isLocalFile()) {
			//use export filename pattern for remote files
			baseFileName = ExportFilePatternUtils.buildBaseFileName(pars.getFileNamePattern(), page);
			
			if (pars.isDoWriteImages()) {
				final String msg = "Downloading " + pars.getRemoteImgQuality().toString() + " image for page nr. " + page.getPageNr();
				logger.debug(msg);
				updateStatus(msg);
				final URI imgUri = getter.getUriBuilder().getImgUri(page.getKey(), pars.getRemoteImgQuality());
//				imgFile = getter.saveFile(imgUri, outputDir.getImgOutputDir().getAbsolutePath(), baseFileName + imgExt);
				imgFile = getter.saveImg(page.getKey(), pars.getRemoteImgQuality(), 
						outputDir.getImgOutputDir().getAbsolutePath(), baseFileName + imgExt);
				page.setUrl(imgFile.toURI().toURL());
				page.setKey(null);
			}
			if(pars.isDoExportPageXml()) {
				//old
				//TrpTranscriptMetadata t = p.getCurrentTranscript();
				/*
				 * new: to get the previously stored chosen version
				 */
				TrpTranscriptMetadata transcriptMd;
				JAXBPageTranscript transcript = cache.getPageTranscriptAtIndex(page.getPageNr()-1);
				
				// set up transcript metadata
				if(transcript == null) {
					transcriptMd = page.getCurrentTranscript();
					logger.warn("Have to unmarshall transcript in DocExporter for transcript "+transcriptMd+" - should have been built before using ExportUtils::storePageTranscripts4Export!");
					transcript = new JAXBPageTranscript(transcriptMd);
					transcript.build();
				} else {
					transcriptMd = transcript.getMd();
				}
				
				//fix the image file name attribute in the Page element in case there was another name set for the export
				transcript.getPageData().getPage().setImageFilename(baseFileName + imgExt);
				
				URL xmlUrl = transcriptMd.getUrl();
				
				if (pars.isExportTranscriptMetadata()) {
					MetadataType md = transcript.getPage().getPcGtsType().getMetadata();
					if (md == null) {
						md = new MetadataType();
						transcript.getPage().getPcGtsType().setMetadata(md);
					}
					
					String imgUrlStr = CoreUtils.urlToString(imgUrl);
					String xmlUrlStr = CoreUtils.urlToString(xmlUrl);
					String status = transcriptMd.getStatus() == null ? null : transcriptMd.getStatus().toString();

					TranskribusMetadataType tmd = new TranskribusMetadataType();
					tmd.setDocId(page.getDocId());
					tmd.setPageId(page.getPageId());
					tmd.setPageNr(page.getPageNr());
					tmd.setTsid(transcriptMd.getTsId());
					tmd.setStatus(status);
					tmd.setUserId(transcriptMd.getUserId());
					tmd.setImgUrl(imgUrlStr);
					tmd.setXmlUrl(xmlUrlStr);
					tmd.setImageId(page.getImageId());
					md.setTranskribusMetadata(tmd);
				}
				
				// write transcript to file
				xmlFile = new File(FilenameUtils.normalizeNoEndSeparator(outputDir.getPageOutputDir().getAbsolutePath()) 
							+ File.separator + baseFileName + xmlExt);
				logger.debug("PAGE XMl output file: "+xmlFile.getAbsolutePath());
				transcript.write(xmlFile);

				// old code: save file by just downloading to disk
//				xmlFile = getter.saveFile(transcriptMd.getUrl().toURI(), pageOutputDir.getAbsolutePath(), baseFileName + xmlExt);
				
				// make sure (for other exports) that the transcript that is exported is the only one set in the transcripts list of TrpPage
				page.getTranscripts().clear();
				TrpTranscriptMetadata tCopy = new TrpTranscriptMetadata(transcriptMd, page);
				tCopy.setUrl(xmlFile.toURI().toURL());
				page.getTranscripts().add(tCopy);
			}
		} else {
			updateStatus("Copying local files for page nr. " + page.getPageNr());
			//ignore export filename pattern for local files
			baseFileName = FilenameUtils.getBaseName(page.getImgFileName());
			// copy local files during export
			if (pars.isDoWriteImages()) {
				imgFile = LocalDocWriter.copyImgFile(page, page.getUrl(), outputDir.getImgOutputDir().getAbsolutePath(), baseFileName + imgExt);
			}
			if(pars.isDoExportPageXml()) {
				xmlFile = LocalDocWriter.copyTranscriptFile(page, outputDir.getPageOutputDir().getAbsolutePath(), baseFileName + xmlExt, cache);
			}
		}
		// export alto:
		if (pars.isDoExportAltoXml()) {
			altoFile = altoEx.exportAltoFile(page, baseFileName + xmlExt, outputDir.getAltoOutputDir(), pars.isSplitIntoWordsInAltoXml());
		}
		
		if (imgFile != null)
			logger.debug("Written image file " + imgFile.getAbsolutePath());
		
		if (xmlFile != null) {
			logger.debug("Written transcript xml file " + xmlFile.getAbsolutePath());
		} else {
			logger.warn("No transcript was exported for page ");
		}
		if (altoFile != null) {
			logger.debug("Written ALTO xml file " + altoFile.getAbsolutePath());
		} else {
			logger.warn("No alto was exported for page ");
		}
		
		setChanged();
		notifyObservers(Integer.valueOf(page.getPageNr()));
	}

	public ExportCache getCache() {
		return cache;
	}
		
	public static void main(String[] args){
		final String p = "${filename}_${${pageId}_${pageNr}";
		System.out.println(ExportFilePatternUtils.isFileNamePatternValid(p));
		System.out.println(ExportFilePatternUtils.buildBaseFileName(p, "test.jpg", 123, 456, "AAAAA", 7));
	}
	
	private static class OutputDirStructure {
		final File rootOutputDir, imgOutputDir, pageOutputDir, altoOutputDir;
		
		public OutputDirStructure(File rootOutputDir, File imgOutputDir, File pageOutputDir, File altoOutputDir) {
			this.rootOutputDir = rootOutputDir;
			this.imgOutputDir = imgOutputDir;
			this.pageOutputDir = pageOutputDir;
			this.altoOutputDir = altoOutputDir;
		}

		public File getRootOutputDir() {
			return rootOutputDir;
		}
		
		public File getImgOutputDir() {
			return imgOutputDir;
		}

		public File getPageOutputDir() {
			return pageOutputDir;
		}

		public File getAltoOutputDir() {
			return altoOutputDir;
		}
	}
	
	protected static class MyURIResolver implements URIResolver {
		private DocumentBuilder dBuilder;
		/**
		 * @param dBuilder a DocumentBuilder configured for the current purpose
		 */
		public MyURIResolver(DocumentBuilder dBuilder) {
			this.dBuilder = dBuilder;
		}
		@Override
		public Source resolve(String href, String base) throws TransformerException {

			//logger.debug("href " + href);
		    ClassLoader cl = this.getClass().getClassLoader();
		    java.io.InputStream in = cl.getResourceAsStream(href);
		    InputSource xslInputSource = new InputSource(in);
		    Document xslDoc;
			try {
				if (dBuilder != null && href.startsWith("xslt")){
					xslDoc = dBuilder.parse(xslInputSource);
				    DOMSource xslDomSource = new DOMSource(xslDoc);
				    xslDomSource.setSystemId(href);
				    return xslDomSource;
				}
			} catch (SAXException | IOException e) {
				logger.error("Failed to load XSLT!", e);
			}

		    return null;
		 
		}
	}
}
