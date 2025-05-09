package com.example.sanction.pdfgeneration;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

@Service
public class PdfGenerationService 
{
	public byte[] generateSanctionLetter(LoanSanctionPdf request) throws MalformedURLException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
	    PdfWriter writer = new PdfWriter(baos);
	    PdfDocument pdfDoc = new PdfDocument(writer);
	    Document document = new Document(pdfDoc);

	    // Colors
	    com.itextpdf.kernel.colors.Color headerColor = new DeviceRgb(25, 118, 210); // Blue
	    com.itextpdf.kernel.colors.Color tableHeaderColor = new DeviceRgb(56, 142, 60); // Green
	    com.itextpdf.kernel.colors.Color grayBackground = new DeviceRgb(240, 240, 240);

	    // Logo
//	    String logoPath = "src/main/resources/static/unified-home-loan-logo.png"; // Change to your logo path
//	    ImageData data = ImageDataFactory.create(logoPath);
//	    Image img = new Image(data).scaleToFit(80, 80);
//	    img.setHorizontalAlignment(HorizontalAlignment.CENTER);
//	    document.add(img);

	    // Title
	    Paragraph title = new Paragraph("Unified Home Loan Pvt Ltd")
	            .setBold().setFontSize(18).setFontColor(headerColor).setTextAlignment(TextAlignment.CENTER);
	    document.add(title);

	    document.add(new Paragraph("Home Loan Eligibility Letter")
	            .setBold().setFontSize(15).setTextAlignment(TextAlignment.CENTER).setUnderline());

	    // Date
	    Paragraph dateParagraph = new Paragraph("Date: " + LocalDate.now());
	                    dateParagraph.setMarginTop(10);
	                    dateParagraph.setTextAlignment(TextAlignment.RIGHT);

	    document.add(dateParagraph);

	    // Subject
	    document.add(new Paragraph("\nSubject: Home Loan Eligibility Based on Your Salary").setUnderline());

	    document.add(new Paragraph("\nDear " + request.getApplicantName() + ","));
	    document.add(new Paragraph(
	            "Thank you for considering Unified Home Loan Pvt Ltd. Based on your monthly salary, we are pleased to inform you of your home loan eligibility as detailed below:"
	    ));

	    // Loan Details Table
	    float[] columnWidths = {300f, 400f};
	    Table table = new Table(columnWidths);
	    table.setMarginTop(15).setWidth(500).setBorder(new SolidBorder(ColorConstants.BLACK, 1));

	    table.addCell(new Cell().add(new Paragraph("Monthly Salary").setBold().setFontColor(tableHeaderColor))
	            .setBackgroundColor(grayBackground).setBorder(new SolidBorder(ColorConstants.BLACK, 1)));
	    table.addCell(new Cell().add(new Paragraph("₹" + request.getNetMonthlyIncome()))
	            .setBorder(new SolidBorder(ColorConstants.BLACK, 1)));

	    table.addCell(new Cell().add(new Paragraph("Eligible Loan Amount").setBold().setFontColor(tableHeaderColor))
	            .setBackgroundColor(grayBackground).setBorder(new SolidBorder(ColorConstants.BLACK, 1)));
	    table.addCell(new Cell().add(new Paragraph("₹" + request.getLoanSanctionedAmount()))
	            .setBorder(new SolidBorder(ColorConstants.BLACK, 1)));

	    table.addCell(new Cell().add(new Paragraph("Tenure Considered").setBold().setFontColor(tableHeaderColor))
	            .setBackgroundColor(grayBackground).setBorder(new SolidBorder(ColorConstants.BLACK, 1)));
	    table.addCell(new Cell().add(new Paragraph(request.getLoanTenureInMonth() + " Months"))
	            .setBorder(new SolidBorder(ColorConstants.BLACK, 1)));

	    table.addCell(new Cell().add(new Paragraph("Interest Rate").setBold().setFontColor(tableHeaderColor))
	            .setBackgroundColor(grayBackground).setBorder(new SolidBorder(ColorConstants.BLACK, 1)));
	    table.addCell(new Cell().add(new Paragraph(request.getRateOfInterest() + "% p.a."))
	            .setBorder(new SolidBorder(ColorConstants.BLACK, 1)));

	    table.addCell(new Cell().add(new Paragraph("EMI (Approx.)").setBold().setFontColor(tableHeaderColor))
	            .setBackgroundColor(grayBackground).setBorder(new SolidBorder(ColorConstants.BLACK, 1)));
	    table.addCell(new Cell().add(new Paragraph("₹" + request.getMonthlyEmiAmount()))
	            .setBorder(new SolidBorder(ColorConstants.BLACK, 1)));

	    table.addCell(new Cell().add(new Paragraph("Processing Fee").setBold().setFontColor(tableHeaderColor))
	            .setBackgroundColor(grayBackground).setBorder(new SolidBorder(ColorConstants.BLACK, 1)));
	    table.addCell(new Cell().add(new Paragraph("₹" + request.getProcessingFees()+ " + GST"))
	            .setBorder(new SolidBorder(ColorConstants.BLACK, 1)));

	    document.add(table);

	    // Footer Notes
	    document.add(new Paragraph("\nPlease note that this is a provisional eligibility and subject to verification of documents, credit appraisal, and other due diligence.")
	            .setFontColor(ColorConstants.DARK_GRAY));

	    document.add(new Paragraph("\nFor any queries or to proceed with the loan application, feel free to contact us."));

	    document.add(new Paragraph("\n\nWarm regards,"));
	    document.add(new Paragraph("Unified Home Loan Pvt Ltd").setBold());

	    document.close();
	    return baos.toByteArray();
	}
}
