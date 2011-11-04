/**
 * 
 */
package com.studiodojo.qwikinvoice.utils.pdf.v2011;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.studiodojo.qwikinvoice.data.AlbumOrderBean;
import com.studiodojo.qwikinvoice.data.BoxOrderBean;
import com.studiodojo.qwikinvoice.data.OrderBean;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.studiodojo.qwikinvoice.utils.pdf.PdfDocument;

/**
 * @author mwho
 *
 */
public class FFOrderPdfWriter extends PdfDocument {

	private	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

	
	final static float PAGE_TOP = 820f;
	final static float PAGE_BOTTOM = 40f;
	final static float PAGE_LEFT_MARGIN = 52f;
	final static float PAGE_WIDTH = 530f;
	/**Defines the TOP Y-POSITION of the body area*/
	final static float BODY_TOP_Y_POS = 530f;
	/**Defines the BOTTOM Y-POSITION of the body area (padded from the footer)*/
	final static float BODY_BOTTOM_Y_POS = PAGE_BOTTOM;
	
	private final static String DEFAULT_FONT = "Verdana";
	/** maintains the font size*/
	private final static float H1_FONT_SIZE = 24, H2_FONT_SIZE = 18, NORMAL_FONT_SIZE = 12,SMALL_FONT_SIZE = 10;
	
	//private final static String CONFIG_PDF_MWEB_LOGO = "com.mweb.rica.logo.path";
	//private String logoPath = "C:\\workspace\\mweb\\TAPPSWeb\\WebRoot\\shared\\images\\MWebBusinessLogo.png";
	
	//private String EMAIL_FROM_ADDRESS = "StudioDojo Admin <mitch@studiodojo.com>";
	//private String EMAIL_REPLY_ADDRESS = "StudioDojo Admin <no-reply@studiodojo.com>";
	
	private String templatePath = "resources/FuriFineart-Order-2011a.pdf";
	
	/**Maintain a font used in the document*/
	final static Font fontH1,fontH2, fontNormal, fontNormalBold, fontSmallBold;

	
	private final static float DATE_DAY_XPOS = PAGE_LEFT_MARGIN + 40f;
	private final static float DATE_DAY_YPOS = PAGE_BOTTOM + 612f;
	
	private final static float DATE_MONTH_XPOS = DATE_DAY_XPOS + 40f;
	private final static float DATE_MONTH_YPOS = PAGE_BOTTOM + 612f;
	
	private final static float DATE_YEAR_XPOS = DATE_MONTH_XPOS + 45f;
	private final static float DATE_YEAR_YPOS = PAGE_BOTTOM + 612f;
	
	private final static float BOOK_TITLE_XPOS = PAGE_LEFT_MARGIN + 155f;
	private final static float BOOK_TITLE_YPOS = PAGE_BOTTOM + 755f;
	
	private final static float CONTACTPERSON_NAME_XPOS = PAGE_LEFT_MARGIN + 155f;
	private final static float CONTACTPERSON_NAME_YPOS = BOOK_TITLE_YPOS - 25f;
	
	private final static float COMPANY_NAME_XPOS = PAGE_LEFT_MARGIN + 155f;
	private final static float COMPANY_NAME_YPOS = CONTACTPERSON_NAME_YPOS - 25f;
	
	private final static float ADDRESS_XPOS = PAGE_LEFT_MARGIN + 155f;
	private final static float ADDRESS_YPOS = COMPANY_NAME_YPOS - 25f;
	
	private final static float PHONE_XPOS = PAGE_LEFT_MARGIN + 155f;
	private final static float PHONE_YPOS = PAGE_BOTTOM + 535f;
	
	private final static float MOBILE_XPOS = PAGE_LEFT_MARGIN + 155f;
	private final static float MOBILE_YPOS = PHONE_YPOS - 25f;
	
	private final static float EMAIL_XPOS = PAGE_LEFT_MARGIN + 155f;
	private final static float EMAIL_YPOS = MOBILE_YPOS - 25f;

	private final static float WEBSITE_XPOS = PAGE_LEFT_MARGIN + 155f;
	private final static float WEBSITE_YPOS = EMAIL_YPOS - 25f;

	private final static float EXCLUSIVEBOOK_CHECK_XPOS = PAGE_LEFT_MARGIN + 150f;
	private final static float EXCLUSIVEBOOK_CHECK_YPOS = PAGE_BOTTOM + 402f;
	private final static float CLASSICBOOK_CHECK_XPOS = PAGE_LEFT_MARGIN + 150f;
	private final static float CLASSICBOOK_CHECK_YPOS = PAGE_BOTTOM + 376f;
	
	private final static float ALBUMSIZE_1_XPOS = PAGE_LEFT_MARGIN + 95f;
	private final static float ALBUMSIZE_1_YPOS = PAGE_BOTTOM + 312f;
	
	private final static float ALBUMCOUNT_1_XPOS = PAGE_LEFT_MARGIN + 345f;
	
	
	private final static float COVER_WIBALIN_XPOS = PAGE_LEFT_MARGIN + 15f;
	private final static float COVER_WIBALIN_YPOS = PAGE_BOTTOM + 173f;
	private final static float COVER_NOVALITE_XPOS = COVER_WIBALIN_XPOS + 73f;
	private final static float COVER_NOVALITE_YPOS = PAGE_BOTTOM + 173f;
	private final static float COVER_PRINTED_XPOS = COVER_NOVALITE_XPOS + 72f;
	private final static float COVER_PRINTED_YPOS = PAGE_BOTTOM + 173f;
	private final static float COVER_CANVAS_XPOS = COVER_PRINTED_XPOS + 71f;
	private final static float COVER_CANVAS_YPOS = PAGE_BOTTOM + 173f;
	private final static float COVER_SKIVERTEX_XPOS = COVER_CANVAS_XPOS + 81f;
	private final static float COVER_SKIVERTEX_YPOS = PAGE_BOTTOM + 173f;
	private final static float COVER_AGED_XPOS = COVER_SKIVERTEX_XPOS + 80f;
	private final static float COVER_AGED_YPOS = PAGE_BOTTOM + 173f;

	private final static float LEATHER_BLACK_XPOS = PAGE_LEFT_MARGIN + 35f;
	private final static float LEATHER_BLACK_YPOS = PAGE_BOTTOM + 110f;
	private final static float LEATHER_BROWN_XPOS = LEATHER_BLACK_XPOS + 107f;
	private final static float LEATHER_BROWN_YPOS = PAGE_BOTTOM + 110f;
	private final static float LEATHER_BLACK_BUFFALO_XPOS = LEATHER_BROWN_XPOS + 107f;
	private final static float LEATHER_BLACK_BUFFALO_YPOS = PAGE_BOTTOM + 110f;
	private final static float LEATHER_BROWN_BUFFALO_XPOS = LEATHER_BLACK_BUFFALO_XPOS + 109f;
	private final static float LEATHER_BROWN_BUFFALO_YPOS = PAGE_BOTTOM + 110f;
	
	private final static float FOIL_GOLD_XPOS = PAGE_LEFT_MARGIN + 8f;
	private final static float FOIL_GOLD_YPOS = PAGE_BOTTOM + 50f;
	private final static float FOIL_SILVER_XPOS = FOIL_GOLD_XPOS + 107f;
	private final static float FOIL_SILVER_YPOS = PAGE_BOTTOM + 50f;

	private final static float FOIL_TEXT_1_XPOS = PAGE_LEFT_MARGIN + 8f;
	private final static float FOIL_TEXT_1_YPOS = PAGE_BOTTOM + 17f;
	
	private final static float FINISH_FINEART_XPOS = PAGE_LEFT_MARGIN + 70f;
	private final static float FINISH_FINEART_YPOS = PAGE_BOTTOM + 693f;
	private final static float FINISH_GLOSS_XPOS = FINISH_FINEART_XPOS + 180f;
	private final static float FINISH_GLOSS_YPOS = PAGE_BOTTOM + 693f;
	private final static float FINISH_MATT_XPOS = FINISH_GLOSS_XPOS + 134f;
	private final static float FINISH_MATT_YPOS = PAGE_BOTTOM + 693f;
	
	private final static float BOXSIZE_1_XPOS = PAGE_LEFT_MARGIN + 95f;
	private final static float BOXSIZE_1_YPOS = PAGE_BOTTOM + 628;
	
	private final static float BOXCOUNT_1_XPOS = PAGE_LEFT_MARGIN + 345f;
	private final static float BOXCOUNT_1_YPOS = PAGE_BOTTOM + 628f;

	private final static float SHIPPING_YES_XPOS = PAGE_LEFT_MARGIN + 80f;
	private final static float SHIPPING_YES_YPOS = PAGE_BOTTOM + 555f;
	private final static float SHIPPING_NO_XPOS = PAGE_LEFT_MARGIN + 150f;
	private final static float SHIPPING_NO_YPOS = PAGE_BOTTOM + 555f;

	private final static float NOTES_XPOS = PAGE_LEFT_MARGIN + 50f;
	private final static float NOTES_YPOS = PAGE_BOTTOM + 526f;
	/**
	 * Static constructor
	 */
	static {
		fontH1 = FontFactory.getFont(DEFAULT_FONT, H1_FONT_SIZE, Font.BOLD);
		fontH2 = FontFactory.getFont(DEFAULT_FONT, H2_FONT_SIZE, Font.BOLD);
		fontNormal = FontFactory.getFont(DEFAULT_FONT, NORMAL_FONT_SIZE, Font.NORMAL);
		fontNormalBold = FontFactory.getFont(DEFAULT_FONT, NORMAL_FONT_SIZE, Font.BOLD);
		fontSmallBold = FontFactory.getFont(DEFAULT_FONT, SMALL_FONT_SIZE, Font.BOLD);
	}
	/**
	 * @throws Exception upon any error
	 */
	public FFOrderPdfWriter() throws Exception {
		super();
	}
	
	/**
	 * Initialise the document for writing
	 * @throws Exception
	 */
	public void init() throws Exception {
		super.createFonts();
		//super.encrypt();
		super.open();
		// load Order Form as a template
		//PdfContentByte cb = super.getFWriter().getDirectContentUnder();
		//File output = new File(outputPath);
		//if (output.exists()) output.delete();
        
	}

	/**
	 * @throws Exception upon any error
	 */
	public void writePdf(SessionBean theSession) throws Exception {
		PdfContentByte cb = super.getFWriter().getDirectContentUnder();
		OrderBean order = theSession.getTheOrder();
		List<AlbumOrderBean> albumOrders = theSession.getAlbums();
		List<BoxOrderBean> boxOrders = theSession.getBoxes();
		// Load Order page 1 as page 1
		File templateFile = new File(templatePath);
        PdfReader reader = new PdfReader(new FileInputStream(templateFile));
        PdfImportedPage page1 = super.getFWriter().getImportedPage(reader, 1);
        cb.addTemplate(page1, 0, 0);
		//set Order date
        Date orderDate = order.getOrderDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(orderDate);
		this.writeText(Integer.toString(100 + cal.get(Calendar.DAY_OF_MONTH)).substring(1), DATE_DAY_XPOS, DATE_DAY_YPOS, cb);
		this.writeText(Integer.toString(101 + cal.get(Calendar.MONTH)).substring(1), DATE_MONTH_XPOS, DATE_MONTH_YPOS, cb);
		this.writeText(Integer.toString(cal.get(Calendar.YEAR)).substring(2), DATE_YEAR_XPOS, DATE_YEAR_YPOS, cb);
		// ------------------------------------------------------------------------
		// Load Order page 2 as page 2
		super.getPdfDocument().newPage();
		PdfImportedPage page2 = super.getFWriter().getImportedPage(reader, 2);
		cb.addTemplate(page2, 0, 0);
		
		this.writeText(order.getAlbumName(), BOOK_TITLE_XPOS, BOOK_TITLE_YPOS, cb);
		this.writeText(order.getContactPerson(), CONTACTPERSON_NAME_XPOS, CONTACTPERSON_NAME_YPOS, cb);
		this.writeText(order.getCompanyName(), COMPANY_NAME_XPOS, COMPANY_NAME_YPOS, cb);
		
		StringTokenizer st = new StringTokenizer(order.getShippingAddress(), "\n\r");
		for (int i = 0; st.hasMoreTokens(); i++) {
			this.writeText(st.nextToken(), ADDRESS_XPOS, ADDRESS_YPOS - (i*13f), cb);
			if (i == 2) break; //truncate at 3 lines
		}
		this.writeText(order.getCity(), ADDRESS_XPOS, ADDRESS_YPOS - 40f, cb);
		this.writeText(order.getStateProvince(), ADDRESS_XPOS, ADDRESS_YPOS - 67f, cb);
		this.writeText(order.getCountry(), ADDRESS_XPOS, ADDRESS_YPOS - 94f, cb);
		this.writeText(order.getAreacode(), ADDRESS_XPOS, ADDRESS_YPOS - 121f, cb);
		
		
		this.writeText(order.getPhoneNumber(), PHONE_XPOS, PHONE_YPOS, cb);
		this.writeText(order.getMobileNumber(), MOBILE_XPOS, MOBILE_YPOS, cb);
		this.writeText(order.getEmail(), EMAIL_XPOS, EMAIL_YPOS, cb);
		this.writeText(order.getWebsiteAddress(), WEBSITE_XPOS, WEBSITE_YPOS, cb);

		if (order.getAlbumType() == OrderBean.AlbumTypes.EXCLUSIVE.ordinal())
			this.writeText("Y", EXCLUSIVEBOOK_CHECK_XPOS, EXCLUSIVEBOOK_CHECK_YPOS, cb);
		else
			this.writeText("Y", CLASSICBOOK_CHECK_XPOS, CLASSICBOOK_CHECK_YPOS, cb);

		if (!albumOrders.isEmpty()) {
			for (int i=0, y=0; i < albumOrders.size() ; i++) {
				if (albumOrders.get(i).getOrderQuantity() <= 0) continue; //ignore '0' quantity orders
				this.writeText(OrderBean.AlbumSizes.values()[albumOrders.get(i).getAlbumSize()].name(), ALBUMSIZE_1_XPOS, ALBUMSIZE_1_YPOS - (y * 15.0f), cb);
				this.writeText(Integer.toString(albumOrders.get(i).getOrderQuantity()), ALBUMCOUNT_1_XPOS, ALBUMSIZE_1_YPOS - (y * 15.0f), cb);
				y++;
			}
		}

		if (order.getCoverFinish() == OrderBean.CoverTypes.WIBALIN.ordinal())
			this.writeText("Y", COVER_WIBALIN_XPOS, COVER_WIBALIN_YPOS, cb);
		else if (order.getCoverFinish() == OrderBean.CoverTypes.NOVALITE.ordinal())
			this.writeText("Y", COVER_NOVALITE_XPOS, COVER_NOVALITE_YPOS, cb);
		else if (order.getCoverFinish() == OrderBean.CoverTypes.PRINTED.ordinal())
			this.writeText("Y", COVER_PRINTED_XPOS, COVER_PRINTED_YPOS, cb);
		else if (order.getCoverFinish() == OrderBean.CoverTypes.CANVAS.ordinal())
			this.writeText("Y", COVER_CANVAS_XPOS, COVER_CANVAS_YPOS, cb);
		else if (order.getCoverFinish() == OrderBean.CoverTypes.SKIVETEX.ordinal())
			this.writeText("Y", COVER_SKIVERTEX_XPOS, COVER_SKIVERTEX_YPOS, cb);
		else if (order.getCoverFinish() == OrderBean.CoverTypes.LEATHER.ordinal())
			this.writeText("Y", COVER_AGED_XPOS, COVER_AGED_YPOS, cb);

		if (order.getLeatherType() == OrderBean.LeatherTypes.BLACK.ordinal())
			this.writeText("Y", LEATHER_BLACK_XPOS, LEATHER_BLACK_YPOS, cb);
		else if (order.getLeatherType() == OrderBean.LeatherTypes.BROWN.ordinal())
			this.writeText("Y", LEATHER_BROWN_XPOS, LEATHER_BROWN_YPOS, cb);
		else if (order.getLeatherType() == OrderBean.LeatherTypes.BLACK_BUFFALO.ordinal())
			this.writeText("Y", LEATHER_BLACK_BUFFALO_XPOS, LEATHER_BLACK_BUFFALO_YPOS, cb);
		else if (order.getLeatherType() == OrderBean.LeatherTypes.BROWN_BUFFALO.ordinal())
			this.writeText("Y", LEATHER_BROWN_BUFFALO_XPOS, LEATHER_BROWN_BUFFALO_YPOS, cb);
		
		if (order.getFoilColour() == OrderBean.FoilType.GOLD.ordinal())
			this.writeText("Y", FOIL_GOLD_XPOS, FOIL_GOLD_YPOS, cb);
		else if (order.getFoilColour() == OrderBean.FoilType.SILVER.ordinal())
			this.writeText("Y", FOIL_SILVER_XPOS, FOIL_SILVER_YPOS, cb);
		
		if (order.getFoilingText() != null && order.getFoilingText().length() > 0) {
			StringTokenizer stt = new StringTokenizer(order.getFoilingText(), "\n\r");
			for (int i = 0; stt.hasMoreTokens(); i++) {
				this.writeText(stt.nextToken(), FOIL_TEXT_1_XPOS, FOIL_TEXT_1_YPOS - (i*13f), cb);
			}
		}
		// ------------------------------------------------------------------------
		// Load Order page 3 as page 3
		super.getPdfDocument().newPage();
		PdfImportedPage page3 = super.getFWriter().getImportedPage(reader, 3);
		cb.addTemplate(page3, 0, 0);
		
		if (order.getExclusivePageFinish() == OrderBean.ExclusivePageType.FINEART.ordinal())
			this.writeText("Y", FINISH_FINEART_XPOS, FINISH_FINEART_YPOS, cb);
		else if (order.getExclusivePageFinish() == OrderBean.ExclusivePageType.GLOSS.ordinal())
			this.writeText("Y", FINISH_GLOSS_XPOS, FINISH_GLOSS_YPOS, cb);
		else if (order.getExclusivePageFinish() == OrderBean.ExclusivePageType.MATT.ordinal())
			this.writeText("Y", FINISH_MATT_XPOS, FINISH_MATT_YPOS, cb);
		
		if (!boxOrders.isEmpty()) {
			for (int i=0, y=0; i < boxOrders.size() ; i++) {
				if (boxOrders.get(i).getOrderQuantity() <= 0) continue; //ignore '0' quantity orders
				this.writeText(OrderBean.AlbumSizes.values()[boxOrders.get(i).getBoxSize()].name(), BOXSIZE_1_XPOS, BOXSIZE_1_YPOS - (y * 15.0f), cb);
				this.writeText(Integer.toString(boxOrders.get(i).getOrderQuantity()), BOXCOUNT_1_XPOS, BOXCOUNT_1_YPOS - (y * 15.0f), cb);
				y++;
			}
		}

		if (order.getShippedOrCollected() == 1)
			this.writeText("Y", SHIPPING_YES_XPOS, SHIPPING_YES_YPOS, cb);
		else if (order.getShippedOrCollected() == 0)
			this.writeText("N", SHIPPING_NO_XPOS, SHIPPING_NO_YPOS, cb);

		if (order.getNote() != null && order.getNote().length() > 0) {
			StringTokenizer sttt = new StringTokenizer(order.getNote(), "\n\r");
			for (int i = 0; sttt.hasMoreTokens(); i++) {
				this.writeText(sttt.nextToken(), NOTES_XPOS, NOTES_YPOS - (i*13f), cb);
			}
		}
		super.generate();
		// write pdf
		//this.emailPdf(theSession);
	}
	
	/**
	 * Writes text to the specific coordinates
	 * @param text
	 * @param x
	 * @param y
	 * @param cb
	 * @throws Exception
	 */
	private void writeText(String text, float x, float y, PdfContentByte cb) throws Exception {
		BaseFont bf = BaseFont.createFont();
		cb.beginText();
        cb.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL);
		cb.setLineWidth(0.5f);
        cb.setRGBColorStroke(0x00, 0x00, 0x00);
        cb.setRGBColorFill(0x00, 0x00, 0x00);
        cb.setFontAndSize(bf, NORMAL_FONT_SIZE);
        cb.setTextMatrix(x, y);
		cb.showText(text);
		cb.endText();
	}
	
	/**
	 * Emails the document to the specified recipient
	 * @param toAddress
	 * @throws Exception
	 */
	/*
	private void emailPdf(SessionBean theSession) throws Exception {
		MailService ms = MailServiceFactory.getMailService();
		MailService.Message msg = new MailService.Message(EMAIL_FROM_ADDRESS, theSession.getLogin(), "Your Furi Fineart Album Order - "+theSession.getTheOrder().getAlbumName() + '('+dateFormatter.format(theSession.getTheOrder().getOrderDate())+')', "");
		msg.setReplyTo(EMAIL_REPLY_ADDRESS);
		msg.setTextBody("Hi "+theSession.getTheOrder().getContactPerson()+",\n\rThank you for using this *beta* service.  Your generated order is attached as a PDF.\n\r\n\r Regards,\nTeam StudioDojo\n\r\n\r** Disclaimer: This service is provided as is. E&O accepted. StudioDojo will not be held responsible for any damages or losses **\n\r");
		String filename = "FuriFineartOrder-"+(theSession.getTheOrder().getCompanyName().replace(' ', '_'))+'-'+(new SimpleDateFormat("ddMMyyyy").format(theSession.getTheOrder().getOrderDate()))+".pdf";
		MailService.Attachment att = new MailService.Attachment(filename, pdfOutputStream.toByteArray());
		msg.setAttachments(att);
		ms.send(msg);
	}
	*/
	/**
	 * Returns the document as a byte array.
	 * @param theSession identifies the session bean
	 * @return the document as a byte array.
	 * @throws Exception up on any error
	 */
	public byte[] pdfToArray(SessionBean theSession) throws Exception {
		this.writePdf(theSession);
		return super.pdfOutputStream.toByteArray();
	}
}
