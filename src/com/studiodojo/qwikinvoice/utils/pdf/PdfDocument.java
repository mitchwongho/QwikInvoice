/**
 * 
 */
package com.studiodojo.qwikinvoice.utils.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.studiodojo.qwikinvoice.data.SessionBean;

/**
 * PDFDocument.java Class Description
 */
public class PdfDocument
{
	protected ByteArrayOutputStream pdfOutputStream = null;
	protected Document pdfDocument = null;
	protected PdfWriter fWriter = null;
	
	protected static final int DEFAULT_TEXT_HEADING_FONT_SIZE = 10;
	protected static final int DEFAULT_TEXT_FONT_SIZE = 8;
	protected static final int DEFAULT_TEXT_FONT_SIZE_SMALL = 7;
	protected static final String DEFAULT_TEXT_FONT = "Verdana";
	
	protected Font pdfBodyFont = null;
	protected Font pdfBoldBodyFont = null;
	protected Font pdfSmallBodyFont = null;
	protected Font pdfHeadingFontWhite = null;
	protected Font pdfSubHeadingFont = null;
	
	/**
	 * Constructor that creates a new encrypted PDF document
	 * @throws Exception
	 */
	public PdfDocument() throws Exception
	{
		pdfDocument = new Document(PageSize.A4);
		pdfOutputStream = new ByteArrayOutputStream();
		fWriter = PdfWriter.getInstance(pdfDocument, pdfOutputStream);
	}
	
	/**
	 * Constructor that creates a new encrypted PDF document
	 * @throws Exception
	 */
	public PdfDocument(Rectangle pageSize) throws Exception
	{
		pdfDocument = new Document(pageSize);
		pdfOutputStream = new ByteArrayOutputStream();
		fWriter = PdfWriter.getInstance(pdfDocument, pdfOutputStream);
	}
	/**
	 * Writes the contents of the pdf (currently stored in the pdfOutputStream) to the specified file 
	 * @param fileName
	 * @return File containing pdf content with the specified fileName
	 * @throws Exception
	 */
	public File writeToFile(String fileName) throws Exception
	{
		File pdfFile = new File(fileName);
		/*
		// Close the PDF Document
		// Set up the file to write to
		FileOutputStream theFile = new FileOutputStream(pdfFile);
		
		byte bytes[] = pdfOutputStream.toByteArray();
		// Read the bytes (the pdf contents) from the already populated outputstream and write to the file
		theFile.write(bytes);
		theFile.close();
		*/
		return pdfFile;  
	}
	
	/**
	 * Sets up the fonts to be used by this PDF document
	 */
	protected void createFonts()
	{
		pdfBodyFont = FontFactory.getFont(DEFAULT_TEXT_FONT, DEFAULT_TEXT_FONT_SIZE);
		pdfHeadingFontWhite = FontFactory.getFont(DEFAULT_TEXT_FONT, DEFAULT_TEXT_HEADING_FONT_SIZE, Font.BOLD, new BaseColor(0xFF, 0xFF, 0xFF));
		pdfSubHeadingFont = FontFactory.getFont(DEFAULT_TEXT_FONT, DEFAULT_TEXT_FONT_SIZE, Font.BOLD, new BaseColor(0x00, 0x00, 0x00));
		pdfSmallBodyFont = FontFactory.getFont(DEFAULT_TEXT_FONT, DEFAULT_TEXT_FONT_SIZE_SMALL);
		pdfBoldBodyFont = FontFactory.getFont(DEFAULT_TEXT_FONT, DEFAULT_TEXT_FONT_SIZE, Font.BOLD);
	}
	
	/**
	 * Set the encryption options for this document:
	 * Strength: true - for 128 bit key length
	 * UserPassword: null - no user password is set
	 * OwnerPassword: null - no owner password is set
	 * Permissions: AllowCopy and AllowPrinting - to allow the document to be copied and printed
	 * @throws DocumentException
	 */
	@SuppressWarnings("deprecation")
	protected void encrypt() throws DocumentException
	{
		fWriter.setEncryption(true, null, null, PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
	}
	
	/**
	 * Opens the PDF document for writing
	 * @throws Exception
	 */
	protected void open() throws Exception
	{
		pdfDocument.open();
	}
	
	/**
	 * Closes off the PDF document
	 * @throws Exception
	 */
	protected void close() throws Exception
	{
		pdfDocument.close();
	}
	
	/**
	 * Method to complete the pdf doc creation
	 * @throws Exception
	 */
	public void generate() throws Exception
	{
		close();
	}

	/**
	 * @return
	 */
	public PdfWriter getFWriter()
	{
		return fWriter;
	}

	/**
	 * @return
	 */
	public Document getPdfDocument()
	{
		return pdfDocument;
	}

	/**
	 * @return
	 */
	public ByteArrayOutputStream getPdfOutputStream()
	{
		return pdfOutputStream;
	}
}
