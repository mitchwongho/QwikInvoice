package com.studiodojo.qwikinvoice.utils.pdf.v2011;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.studiodojo.qwikinvoice.data.InvoiceBean;
import com.studiodojo.qwikinvoice.data.InvoiceItemBean;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.studiodojo.qwikinvoice.utils.pdf.PdfDocument;

/**
 * @author mwho
 *
 */
public class PdfWriter extends PdfDocument {

	private	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private NumberFormat currFormat = DecimalFormat.getNumberInstance();
	
	final static float PAGE_TOP = 760f;
	final static float PAGE_BOTTOM = 40f;
	final static float PAGE_LEFT_MARGIN = 72f;
	final static float PAGE_WIDTH = 530f;
	/**Defines the TOP Y-POSITION of the body area*/
	final static float BODY_TOP_Y_POS = 530f;
	/**Defines the BOTTOM Y-POSITION of the body area (padded from the footer)*/
	final static float BODY_BOTTOM_Y_POS = PAGE_BOTTOM;
	
	private final static String DEFAULT_FONT = "Verdana";
	
	private final static float DEFAULT_LINE_SPACE = 14f;
	/** maintains the font size*/
	private final static float H1_FONT_SIZE = 24, H2_FONT_SIZE = 18, NORMAL_FONT_SIZE = 12,SMALL_FONT_SIZE = 10;
	
	private final static float COMPANY_NAME_XPOS = PAGE_LEFT_MARGIN;
	private final static float COMPANY_NAME_YPOS = 723f;
	private final static float COMPANY_PERSON_NAME_XPOS = PAGE_LEFT_MARGIN;
	private final static float COMPANY_PERSON_NAME_YPOS = COMPANY_NAME_YPOS - DEFAULT_LINE_SPACE;
	private final static float COMPANY_DETAILS_XPOS = PAGE_LEFT_MARGIN;
	private final static float COMPANY_DETAILS_YPOS = COMPANY_PERSON_NAME_YPOS - DEFAULT_LINE_SPACE;
	
	private final static float INVOICE_DATE_XPOS = 390f;
	private final static float INVOICE_DATE_YPOS = 760f;
	
	private final static float REFERENCE_XPOS = 420f;
	private final static float REFERENCE_YPOS = 745f;

	private final static float ORDER_NUM_ID_XPOS = 390f;
	private final static float ORDER_NUM_YPOS = 732f;
	
	private final static float COMPANY_PHONE_XPOS = 345f;
	private final static float COMPANY_PHONE_YPOS = 705f;
	private final static float COMPANY_FAX_XPOS = 345f;
	private final static float COMPANY_FAX_YPOS = COMPANY_PHONE_YPOS - DEFAULT_LINE_SPACE;
	private final static float COMPANY_EMAIL_XPOS = 345f;
	private final static float COMPANY_EMAIL_YPOS = COMPANY_FAX_YPOS - DEFAULT_LINE_SPACE;
	private final static float COMPANY_TAX_XPOS = 345f;
	private final static float COMPANY_TAX_YPOS = COMPANY_EMAIL_YPOS - DEFAULT_LINE_SPACE;
	
	
	private final static float CUSTOMER_NAME_XPOS = PAGE_LEFT_MARGIN;
	private final static float CUSTOMER_NAME_YPOS = 610f;
	private final static float CUSTOMER_PERSON_XPOS = PAGE_LEFT_MARGIN;
	private final static float CUSTOMER_PERSON_YPOS = CUSTOMER_NAME_YPOS - DEFAULT_LINE_SPACE;
	private final static float CUSTOMER_DETAILS_XPOS = PAGE_LEFT_MARGIN;
	private final static float CUSTOMER_DETAILS_YPOS = CUSTOMER_PERSON_YPOS - DEFAULT_LINE_SPACE;
	
	private final static float CUSTOMER_SHIP_NAME_XPOS = 350f;
	private final static float CUSTOMER_SHIP__NAME_YPOS = 610f;
	private final static float CUSTOMER_SHIP_PERSON_NAME_XPOS = 350f;
	private final static float CUSTOMER_SHIP_PERSON__NAME_YPOS = CUSTOMER_SHIP__NAME_YPOS - DEFAULT_LINE_SPACE;
	private final static float CUSTOMER_SHIP__DETAILS_XPOS = 350f;
	private final static float CUSTOMER_SHIP__DETAILS_YPOS = CUSTOMER_SHIP_PERSON__NAME_YPOS - DEFAULT_LINE_SPACE;
	
	private final static float CUSTOMER_PHONE_XPOS = PAGE_LEFT_MARGIN;
	private final static float CUSTOMER_PHONE_YPOS = CUSTOMER_SHIP__DETAILS_YPOS - (DEFAULT_LINE_SPACE * 5);
	private final static float CUSTOMER_FAX_XPOS = 215f;
	private final static float CUSTOMER_FAX_YPOS = CUSTOMER_PHONE_YPOS;
	private final static float CUSTOMER_EMAIL_XPOS = 350f;
	private final static float CUSTOMER_EMAIL_YPOS = CUSTOMER_PHONE_YPOS;
	
	private final static float ITEM_CODE_XPOS = PAGE_LEFT_MARGIN;
	private final static float ITEM_CODE_YPOS = 470f;
	private final static float ITEM_DESC_XPOS = PAGE_LEFT_MARGIN + 50f;
	private final static float ITEM_DESC_YPOS = 470f;
	private final static float ITEM_QUANTITY_XPOS = 360f;
	private final static float ITEM_QUANTITY_YPOS = 470f;
	private final static float ITEM_UNITPRICE_XPOS = ITEM_QUANTITY_XPOS + 50f;
	private final static float ITEM_UNITPRICE_YPOS = 470f;
	private final static float ITEM_TOTALPRICE_XPOS = ITEM_UNITPRICE_XPOS + 50f;
	private final static float ITEM_TOTALPRICE_YPOS = 470f;

	private final static float BANK_DETAILS_XPOS = PAGE_LEFT_MARGIN;
	private final static float BANK_DETAILS_YPOS = 122f;
	
	private final static float TAX_RATE_XPOS = 403f;
	private final static float TAX_RATE_YPOS = 107F;
	
	private final static float SUBTOTAL_XPOS = 470f;
	private final static float SUBTOTAL_YPOS = 133F;
	private final static float DISCOUNT_XPOS = 470f;
	private final static float DISCOUNT_YPOS = SUBTOTAL_YPOS - DEFAULT_LINE_SPACE;
	private final static float TAXABLE_XPOS = 470f;
	private final static float TAXABLE_YPOS = DISCOUNT_YPOS - DEFAULT_LINE_SPACE;
	private final static float TOTAL_XPOS = 470f;
	private final static float TOTAL_YPOS = TAXABLE_YPOS - DEFAULT_LINE_SPACE;
	
	
	private String EMAIL_FROM_ADDRESS = "StudioDojo Admin <mitch@studiodojo.com>";
	private String EMAIL_REPLY_ADDRESS = "StudioDojo Admin <no-reply@studiodojo.com>";
	
	private String templatePath = "resources/QwikInvoice-InvoiceTemplate.pdf";
	
	/**Maintain a font used in the document*/
	final static Font fontH1,fontH2, fontNormal, fontNormalBold, fontSmallBold;

	
	
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
	public PdfWriter() throws Exception {
		super();
	}
	
	/**
	 * Initialise the document for writing
	 * @throws Exception
	 */
	public void init() throws Exception {
		currFormat.setMinimumFractionDigits(2);
		super.createFonts();
		//super.encrypt();
		super.open();
	}

	/**
	 * @param theSession
	 * @throws Exception
	 */
	public void emailPdf(SessionBean theSession) throws Exception {
		this.writePdf(theSession);
		this.email(theSession);
	}
	
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
	/**
	 * @throws Exception upon any error
	 */
	private void writePdf(SessionBean theSession) throws Exception {
		PdfContentByte cb = super.getFWriter().getDirectContentUnder();
		InvoiceBean invoiceBean = theSession.getInvoiceBean();
		//List<AlbumOrderBean> albumOrders = theSession.getAlbums();
		//List<BoxOrderBean> boxOrders = theSession.getBoxes();
		// Load Order page 1 as page 1
		File templateFile = new File(templatePath);
        PdfReader reader = new PdfReader(new FileInputStream(templateFile));
        PdfImportedPage page1 = super.getFWriter().getImportedPage(reader, 1);
        cb.addTemplate(page1, 0, 0);
        
        this.writeText(invoiceBean.getCompanyName(), COMPANY_NAME_XPOS, COMPANY_NAME_YPOS, cb);
        this.writeText(invoiceBean.getCompanyPersonName(), COMPANY_PERSON_NAME_XPOS, COMPANY_PERSON_NAME_YPOS, cb);
        StringTokenizer st = new StringTokenizer(invoiceBean.getCompanyAddress(), "\n\r");
		for (int i = 0; st.hasMoreTokens(); i++) {
			this.writeText(st.nextToken(), COMPANY_DETAILS_XPOS, COMPANY_DETAILS_YPOS - (i*13f), cb);
			if (i == 4) break; //max 5 lines
		}
		//set Order date
		Date invDate = invoiceBean.getDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(invDate);
		this.writeText(Integer.toString(cal.get(Calendar.YEAR)) + '/' + Integer.toString(101 + cal.get(Calendar.MONTH)).substring(1) + '/' + Integer.toString(100 + cal.get(Calendar.DAY_OF_MONTH)).substring(1), INVOICE_DATE_XPOS, INVOICE_DATE_YPOS, cb);
        
		this.writeText(invoiceBean.getReference(), REFERENCE_XPOS, REFERENCE_YPOS, cb);
		this.writeText("N/A", ORDER_NUM_ID_XPOS, ORDER_NUM_YPOS, cb);
		// COMAPNY CONTACT DETAILS
		this.writeText("Tel: "+invoiceBean.getCompanyPhoneNumber(), COMPANY_PHONE_XPOS, COMPANY_PHONE_YPOS, cb);
		this.writeText("Fax: "+ invoiceBean.getCompanyFaxNumber(), COMPANY_FAX_XPOS, COMPANY_FAX_YPOS, cb);
		this.writeText("Email: "+invoiceBean.getCompanyEmailAddress(), COMPANY_EMAIL_XPOS, COMPANY_EMAIL_YPOS, cb);
		this.writeText("Tax Num: "+invoiceBean.getCompanyTaxNumber(), COMPANY_TAX_XPOS, COMPANY_TAX_YPOS, cb);
		// BILLING ADDRESS
		this.writeText(invoiceBean.getCustomerName(), CUSTOMER_NAME_XPOS, CUSTOMER_NAME_YPOS, cb);
		this.writeText(invoiceBean.getCustomerPersonName(), CUSTOMER_PERSON_XPOS, CUSTOMER_PERSON_YPOS, cb);
        StringTokenizer stt = new StringTokenizer(invoiceBean.getCustomerAddress(), "\n\r");
		for (int i = 0; stt.hasMoreTokens(); i++) {
			this.writeText(stt.nextToken(), CUSTOMER_DETAILS_XPOS, CUSTOMER_DETAILS_YPOS - (i*DEFAULT_LINE_SPACE), cb);
			if (i == 4) break; //max 5 lines
		}
		// SHIPPING ADDRESS
		this.writeText(invoiceBean.getCustomerName(), CUSTOMER_SHIP_NAME_XPOS, CUSTOMER_SHIP__NAME_YPOS, cb);
		this.writeText(invoiceBean.getCustomerPersonName(), CUSTOMER_SHIP_PERSON_NAME_XPOS, CUSTOMER_SHIP_PERSON__NAME_YPOS, cb);
		StringTokenizer sttt = new StringTokenizer(invoiceBean.getCustomerAddress(), "\n\r");
		for (int i = 0; sttt.hasMoreTokens(); i++) {
			this.writeText(sttt.nextToken(), CUSTOMER_SHIP__DETAILS_XPOS, CUSTOMER_SHIP__DETAILS_YPOS - (i*DEFAULT_LINE_SPACE), cb);
			if (i == 4) break; //max 5 lines
		}
		// CUSTOMER CONTACT DETAILS
		this.writeText("Tel: "+invoiceBean.getCustomerPhoneNumber(), CUSTOMER_PHONE_XPOS, CUSTOMER_PHONE_YPOS, cb);
		this.writeText("Fax: "+ invoiceBean.getCustomerFaxNumber(), CUSTOMER_FAX_XPOS, CUSTOMER_FAX_YPOS, cb);
		this.writeText("Email: "+invoiceBean.getCustomerEmailAddress(), CUSTOMER_EMAIL_XPOS, CUSTOMER_EMAIL_YPOS, cb);
		
		double subtotal = 0.0D;
		if (!theSession.getItems().isEmpty()) {
			List<InvoiceItemBean> items = theSession.getItems();
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getItemDescription().isEmpty()) continue;
				this.writeText(items.get(i).getItemCode(), ITEM_CODE_XPOS, ITEM_CODE_YPOS - (i*DEFAULT_LINE_SPACE), cb);
				this.writeText(items.get(i).getItemDescription(), ITEM_DESC_XPOS, ITEM_DESC_YPOS - (i*DEFAULT_LINE_SPACE), cb);
				this.writeText(Integer.toString(items.get(i).getItemQuantity()), ITEM_QUANTITY_XPOS, ITEM_QUANTITY_YPOS - (i*DEFAULT_LINE_SPACE), cb);
				this.writeText(currFormat.format(items.get(i).getItemUnitPrice()), ITEM_UNITPRICE_XPOS, ITEM_UNITPRICE_YPOS - (i*DEFAULT_LINE_SPACE), cb);
				this.writeText(currFormat.format(items.get(i).getItemUnitPrice() * items.get(i).getItemQuantity()), ITEM_TOTALPRICE_XPOS, ITEM_TOTALPRICE_YPOS - (i*DEFAULT_LINE_SPACE), cb);
				subtotal += items.get(i).getItemUnitPrice() * items.get(i).getItemQuantity();
			}
		}
		
		// SUBTOTAL
		this.writeText(currFormat.format(subtotal), SUBTOTAL_XPOS, SUBTOTAL_YPOS, cb);
		// DISCOUNT
		this.writeText(currFormat.format(invoiceBean.getDiscountAmount()), DISCOUNT_XPOS, DISCOUNT_YPOS, cb);
		// TAX RATE
		this.writeText(currFormat.format(invoiceBean.getTaxRatePercentage()), TAX_RATE_XPOS, TAX_RATE_YPOS, cb);
		// TAXABLE
		double taxable = (subtotal - invoiceBean.getDiscountAmount()) * invoiceBean.getTaxRatePercentage() / 100;
		this.writeText(currFormat.format(taxable), TAXABLE_XPOS, TAXABLE_YPOS, cb);
		// TOTAL
		double total = (subtotal - invoiceBean.getDiscountAmount()) + taxable;
		this.writeText(currFormat.format(total), TOTAL_XPOS, TOTAL_YPOS, cb);
		// BANKING DETAILS
		StringTokenizer stttt = new StringTokenizer(invoiceBean.getBankingDetails(), "\n\r");
		for (int i = 0; stttt.hasMoreTokens(); i++) {
			this.writeText(stttt.nextToken(), BANK_DETAILS_XPOS, BANK_DETAILS_YPOS - (i*DEFAULT_LINE_SPACE), cb);
			if (i == 4) break; //max 5 lines
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
	private void email(SessionBean theSession) throws Exception {
		MailService ms = MailServiceFactory.getMailService();
		MailService.Message msg = new MailService.Message(EMAIL_FROM_ADDRESS, theSession.getLogin(), "Your QwikInvoice Invoice - "+theSession.getInvoiceBean().getReference() + '('+dateFormatter.format(theSession.getInvoiceBean().getDate())+')', "");
		msg.setReplyTo(EMAIL_REPLY_ADDRESS);
		msg.setTextBody("Hi "+theSession.getInvoiceBean().getCompanyPersonName()+",\n\rThank you for using this *beta* service.  Your generated invoice is attached as a PDF.\n\r\n\r Regards,\nTeam StudioDojo\n\r\n\r** Disclaimer: This service is provided as is. E&O accepted. StudioDojo will not be held responsible for any damages or losses **\n\r");
		String filename = PdfWriter.getFilename(theSession);
		MailService.Attachment att = new MailService.Attachment(filename, pdfOutputStream.toByteArray());
		msg.setAttachments(att);
		ms.send(msg);
	}
	
	/**
	 * returns the filename
	 * @param theSession
	 * @return
	 */
	public static String getFilename(SessionBean theSession) {
		return "Invoice-"+(theSession.getInvoiceBean().getReference())+'-'+(new SimpleDateFormat("ddMMyyyy").format(theSession.getInvoiceBean().getDate()))+".pdf";
	}
}
