package rfid;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.text.NumberFormat;

import DTO.Bill;
import DTO.Billdetails;
import DTO.Customer;
import DTO.Staff;
import DTO.Productline;
import DTO.Productinstancerfid;
import DTO.Pdf;
import BUS.PdfBUS;
import BUS.BillBUS;
import BUS.BilldetailsBUS;
import BUS.CustomerBUS;
import BUS.StaffBUS;
import BUS.ProductlineBUS;
import BUS.ProductinstancerfidBUS;
import java.text.DecimalFormat;
import java.util.ArrayList;
import static rfid.RemoveVN.removeAccent;


public class PdfRFID {
	public static String PDF_PATH = "C:\\Users\\LacKhaiMinh\\Desktop\\Bill\\Bill.pdf";
        
        NumberFormat nf = NumberFormat.getNumberInstance();
        BillBUS BillBUS = new BillBUS();
        ProductlineBUS ProductlineBUS = new ProductlineBUS();
        StaffBUS StaffBUS = new StaffBUS();
        CustomerBUS CustomerBUS = new CustomerBUS();
        BilldetailsBUS BilldetailsBUS = new BilldetailsBUS();
        ProductinstancerfidBUS ProductinstancerfidBUS = new ProductinstancerfidBUS();
        PdfBUS PdfBUS = new PdfBUS();
        DecimalFormat dcf = new DecimalFormat("#");
        
	public void export(String billId,String staffId,String customerId) {
                dcf.setMaximumFractionDigits(340);
                ArrayList <Billdetails> billdetail = BilldetailsBUS.getList();
                ArrayList <Productline> productline = ProductlineBUS.getList();
                ArrayList<Bill> bill = BillBUS.getList();
                ArrayList<Staff> staff = StaffBUS.getList();
                ArrayList<Customer> customer = CustomerBUS.getList();
                ArrayList <Productinstancerfid> productinstancerfid = ProductinstancerfidBUS.getList();
                Bill billGet = BillBUS.get(billId);
                ArrayList<Pdf> pdfl = PdfBUS.getList(billId);
		Document document = new Document();
		try {
			// title
                        
			Font font = new Font(FontFamily.HELVETICA, 24.0f, Font.NORMAL, BaseColor.BLACK);
			Chunk titleChunk = new Chunk("Invoice", font);
			Paragraph titlePara = new Paragraph();
			titlePara.add(titleChunk);
			titlePara.setAlignment(Element.ALIGN_CENTER);

			// address
			Font font1 = new Font(FontFamily.HELVETICA, 10.0f);
			Chunk addressChunk = new Chunk("273 An Duong Vuong Street, Ward 3, District 5, Ho Chi Minh City", font1);
			Paragraph addressPara = new Paragraph();
			addressPara.add(addressChunk);
			addressPara.setAlignment(Element.ALIGN_CENTER);

			// table: left: date, staff, right: invoice id
			PdfPTable headerTable = new PdfPTable(1);
			headerTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

			headerTable.setWidthPercentage(80);

			Font font2 = new Font(FontFamily.HELVETICA, 12.0f);

			PdfPCell headerCell;
                        headerCell = new PdfPCell(new Phrase("", font2));
			headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			headerCell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(headerCell);
                        
			headerCell = new PdfPCell(new Phrase("Date : "+billGet.getDate(), font2));
			headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			headerCell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(headerCell);

			headerCell = new PdfPCell(new Phrase("Bill ID : "+billGet.getBill_id(), font2));
			headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			headerCell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(headerCell);
                        
                        String staffName = "";
                        for(Staff s: staff){
                            if(s.getStaff_id().equals(staffId)){
                                staffName = s.getName();
                            }
                            
                        }
                        
                        String customerName = "";
                        for(Customer c: customer){
                            if(c.getCustomer_id().equals(customerId)){
                                customerName = c.getName();
                            }
                            
                        }
			
			
			headerCell = new PdfPCell(new Phrase("Staff Name : "+removeAccent(staffName), font2));
			headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			headerCell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(headerCell);

			headerCell = new PdfPCell(new Phrase("Customer Name : "+removeAccent(customerName), font2));
			headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
			headerCell.setBorder(Rectangle.NO_BORDER);
			headerTable.addCell(headerCell);

			// line
			LineSeparator line = new LineSeparator();
			line.setPercentage(80);
			line.setLineColor(new BaseColor(179, 179, 179));
			Chunk linebreak = new Chunk(line);

			// table: product detail
			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(80);
			table.setWidths(new int[] { 2, 2, 2, 2 });

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA, 12.0f);

			PdfPCell hcell;

			hcell = new PdfPCell(new Phrase("Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell.setBorder(Rectangle.NO_BORDER);
			hcell.setMinimumHeight(24.0f);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Quantity", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell.setBorder(Rectangle.NO_BORDER);
			hcell.setMinimumHeight(24.0f);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Price", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell.setBorder(Rectangle.NO_BORDER);
			hcell.setMinimumHeight(24.0f);
			table.addCell(hcell);


			hcell = new PdfPCell(new Phrase("Total", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			hcell.setBorder(Rectangle.NO_BORDER);
			hcell.setMinimumHeight(24.0f);
			table.addCell(hcell);

			Font elementFont = FontFactory.getFont(FontFactory.HELVETICA, 10.0f);
			
			
   
                    
                    for (Pdf p : pdfl) {
                        
                            PdfPCell cell;
                            
                            cell = new PdfPCell(new Phrase(removeAccent(p.getName()),elementFont));
                            cell.setVerticalAlignment(Element.ALIGN_LEFT);
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell.setBorder(Rectangle.NO_BORDER);
                            cell.setMinimumHeight(16.0f);
                            table.addCell(cell);
                            
                            cell = new PdfPCell(new Phrase(String.valueOf(p.getCount()),elementFont));
                            cell.setVerticalAlignment(Element.ALIGN_LEFT);
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell.setBorder(Rectangle.NO_BORDER);
                            cell.setMinimumHeight(16.0f);
                            table.addCell(cell);

                            cell = new PdfPCell(new Phrase(nf.format(p.getPrice())+" VND",elementFont));
                            cell.setVerticalAlignment(Element.ALIGN_LEFT);
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell.setPaddingRight(5);
                            cell.setBorder(Rectangle.NO_BORDER);
                            cell.setMinimumHeight(16.0f);
                            table.addCell(cell);

                            cell = new PdfPCell(new Phrase(p.getCount()*Integer.parseInt(dcf.format(p.getPrice()))+" VND",elementFont));
                            cell.setVerticalAlignment(Element.ALIGN_LEFT);
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell.setBorder(Rectangle.NO_BORDER);
                            cell.setMinimumHeight(16.0f);
                            cell.setPaddingRight(5);
                            table.addCell(cell);
                    }   
			// table: left: label (subtotal, discount, total) right: price
			PdfPTable totalTable = new PdfPTable(2);
			totalTable.setWidthPercentage(80);
			totalTable.setWidths(new int[] { 6, 2 });

			PdfPCell totalCell;


			totalCell = new PdfPCell(new Phrase("Total : ", new Font(FontFamily.HELVETICA, 14.0f, Font.BOLD)));
			totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			totalCell.setBorder(Rectangle.NO_BORDER);
			totalCell.setMinimumHeight(24.0f);
			totalTable.addCell(totalCell);
                        
			totalCell = new PdfPCell(new Phrase(nf.format(Double.parseDouble(billGet.getTotal()))+" VND", headFont));
			totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			totalCell.setBorder(Rectangle.NO_BORDER);
			totalCell.setMinimumHeight(24.0f);
			totalTable.addCell(totalCell);

			PdfWriter.getInstance(document, new FileOutputStream(PDF_PATH));
			document.open();

			document.add(titlePara);
			document.add(addressPara);
			document.add(headerTable);
			document.add(linebreak);
			document.add(table);
			document.add(linebreak);
			document.add(totalTable);

			document.close();

		} catch (DocumentException ex) {

		} catch (FileNotFoundException e) {
		}

	}

}