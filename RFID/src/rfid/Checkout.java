/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rfid;
import BUS.BillBUS;
import BUS.BilldetailsBUS;
import BUS.CustomerBUS;
import BUS.ProductinstancerfidBUS;
import BUS.ProductlineBUS;
import BUS.StaffBUS;
import BUS.TagreadBUS;
import DTO.Bill;
import DTO.Billdetails;
import DTO.Customer;
import DTO.Productinstancerfid;
import DTO.Productline;
import DTO.Staff;
import DTO.Tagread;
import com.caen.RFIDLibrary.CAENRFIDException;
import com.caen.RFIDLibrary.CAENRFIDLogicalSource;
import com.caen.RFIDLibrary.CAENRFIDLogicalSourceConstants;
import com.caen.RFIDLibrary.CAENRFIDPort;
import com.caen.RFIDLibrary.CAENRFIDReader;
import com.caen.RFIDLibrary.CAENRFIDReaderInfo;
import com.caen.RFIDLibrary.CAENRFIDTag;
import java.awt.Color;
import java.awt.Font;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rfid.Read;
import static rfid.Read.hex;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import static jdk.nashorn.internal.objects.NativeString.toUpperCase;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author LacKhaiMinh
 */
public class Checkout extends javax.swing.JFrame {
    public ProductinstancerfidBUS ProductinstancerfidBUS = new ProductinstancerfidBUS();
    public ProductlineBUS ProductlineBUS = new ProductlineBUS();
    public TagreadBUS TagreadBUS = new TagreadBUS();
    public BillBUS BillBUS = new BillBUS();
    public CustomerBUS CustomerBUS = new CustomerBUS();
    public StaffBUS StaffBUS = new StaffBUS();
    public BilldetailsBUS BilldetailsBUS = new BilldetailsBUS();
    public DefaultTableModel model;
    ArrayList<Productinstancerfid> product = new ArrayList<Productinstancerfid>();
    byte[] b;
    DecimalFormat df = new DecimalFormat("#");
    NumberFormat nf = NumberFormat.getNumberInstance();
    float tongTien;
    String billId = "";
    ArrayList<Productline> productline = ProductlineBUS.getList();
    ArrayList<Bill> bill = BillBUS.getList();
    ArrayList<Customer> customer = CustomerBUS.getList();
    ArrayList<Staff> staff = StaffBUS.getList();
    ArrayList<Billdetails> billdetails = BilldetailsBUS.getList();
    ArrayList<Productinstancerfid> productinstancerfid = ProductinstancerfidBUS.getList();
    private String username;
    ManegementCustomer manegementCustomer = new ManegementCustomer();
//    NewJFrame newj = new NewJFrame();
    String StaffName = "";
    String Staffid = "";
        

    
    public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
            // upper case
            // result.append(String.format("%02X", aByte));
        }
        return result.toString().toUpperCase();
    }
    /**
     * Creates new form Test
     */
    Read rd = new Read();
    
    public Checkout(String username) {
        initComponents();
        this.username = username;

        thanhToanTbl.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD, 12));
        thanhToanTbl.getTableHeader().setOpaque(false);
        thanhToanTbl.getTableHeader().setBackground(Color.orange);
        
        hoaDonTbl.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD, 12));
        hoaDonTbl.getTableHeader().setOpaque(false);
        hoaDonTbl.getTableHeader().setBackground(Color.orange);
        hoaDonTbl.getColumnModel().getColumn(0).setPreferredWidth(2);
        hoaDonTbl.getColumnModel().getColumn(1).setPreferredWidth(50);
        hoaDonTbl.getColumnModel().getColumn(2).setPreferredWidth(50);
        hoaDonTbl.getColumnModel().getColumn(3).setPreferredWidth(150);
        hoaDonTbl.getColumnModel().getColumn(4).setPreferredWidth(5);
        
        chiTietHoaDonTbl.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD, 12));
        chiTietHoaDonTbl.getTableHeader().setOpaque(false);
        chiTietHoaDonTbl.getTableHeader().setBackground(Color.orange);
        chiTietHoaDonTbl.getColumnModel().getColumn(0).setPreferredWidth(2);
        chiTietHoaDonTbl.getColumnModel().getColumn(1).setPreferredWidth(100);
        chiTietHoaDonTbl.getColumnModel().getColumn(2).setPreferredWidth(100);
        chiTietHoaDonTbl.getColumnModel().getColumn(3).setPreferredWidth(2);
        
        nf.setGroupingUsed(true);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        
        if(BillBUS.getList()== null)BillBUS.listBill();
        outModelHoaDon(model,bill,staff,customer);
        
        AutoCompleteDecorator.decorate(customerJComboBox);
        setValueJcombo(customer);
        customerJComboBox.setSelectedItem("Khách Hàng");
        for(Staff s: staff){
            if(s.getUsername().equals(username)){
                StaffName = s.getName();
                Staffid = s.getStaff_id();
            }
        }
        tenNhanVienjbl.setText(StaffName);
    }
    
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    private Checkout() {
       
    }
    
    public void setValueJcombo(ArrayList<Customer> customer){
        for(Customer c: customer){
            customerJComboBox.addItem(c.getCustomer_id());
        }
       
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false; 
        }
        return pattern.matcher(strNum).matches();
    }
    
    public void listTagread(String tagId) // Chép ArrayList lên table
    {
        if(TagreadBUS.getList()== null)TagreadBUS.listTagread();
        ArrayList<Tagread> tagread = TagreadBUS.getList();
        
        if(ProductinstancerfidBUS.getList()== null)ProductinstancerfidBUS.listProductinstancerfid();
            
        if(ProductlineBUS.getList()== null)ProductlineBUS.listProductline();

        String productinstance = TagreadBUS.get(tagId).getProduct_instance_id();
        if(ProductinstancerfidBUS.get(productinstance).getIs_purchased()==0){
            product.add(ProductinstancerfidBUS.get(productinstance));
        }
        
        
        outModelThanhToan(model,productline,product);
        
    }
    public void clearTable()
{
    tongTienLabel.revalidate();
    product.clear();
    DefaultTableModel dm = (DefaultTableModel) thanhToanTbl.getModel();
    dm.getDataVector().removeAllElements();
    revalidate();
}
    
    public void tinhTongTien(ArrayList<Productline> productline, ArrayList<Productinstancerfid> product){
        tongTien = 0;
        for(Productinstancerfid pi: product)
        {
            if(pi.getIs_purchased() == 0)
            {
            
            for(Productline p: productline)
            {
                if(pi.getProduct_line_id().equals(p.getProduct_line_id())){
                      tongTien += (p.getPrice());
                }
            }
            }
        }
    }
    
    public void outModelThanhToan(DefaultTableModel model , ArrayList<Productline> productline, ArrayList<Productinstancerfid> product) // Xuất ra Table từ ArrayList
    {   
        model = (DefaultTableModel) thanhToanTbl.getModel();
        Vector data;
        model.setRowCount(0);
        tongTien = 0;
        for(Productinstancerfid pi: product)
        {
            if(pi.getIs_purchased() == 0)
            {
            data = new Vector();
            data.add(pi.getProduct_instance_id());
            for(Productline p: productline)
            {
                if(pi.getProduct_line_id().equals(p.getProduct_line_id())){
                      data.add(p.getName());
                      data.add(nf.format(p.getPrice())+"VNĐ");
                      data.add("1");
                      tongTien += p.getPrice();
                }
            }
            model.addRow(data);
            }
        }
        
        
        thanhToanTbl.setModel(model);
        model.fireTableDataChanged();
        tongTienLabel.setText(nf.format(tongTien)+"VNĐ");
        
        
    }
    
    public void outModelHoaDon(DefaultTableModel model , ArrayList<Bill> bill,ArrayList<Staff> staff,ArrayList<Customer> customer) // Xuất ra Table từ ArrayList
    {
        float doanhthu = 0;
        model = (DefaultTableModel) hoaDonTbl.getModel();
        Vector data;
        model.setRowCount(0);
        for(Bill b: bill)
        {
            
            data = new Vector();
            data.add(b.getBill_id());
            for(Staff s : staff){
                if(s.getStaff_id().equals(b.getStaff_id())){
                    data.add(s.getName());
                }
            }
            data.add(b.getCustomer_id());
            data.add(b.getDate());
            data.add(b.getTotal());
            doanhthu += Double.parseDouble(b.getTotal());
            model.addRow(data);
            
        }

        hoaDonTbl.setModel(model);
        model.fireTableDataChanged();
        doanhThuJlb.setText(nf.format(doanhthu) + "VNĐ");
    }
    
    public void outModelChiTietHoaDon(DefaultTableModel model, String id , ArrayList<Billdetails> billdetails) // Xuất ra Table từ ArrayList
    {
        model = (DefaultTableModel) chiTietHoaDonTbl.getModel();
        Vector data;
        model.setRowCount(0);
        int i = 0;
        for(Billdetails bd: billdetails)
        {   
            data = new Vector();
            if(bd.getBill_id().equals(id)){      
                for(Productinstancerfid p : productinstancerfid){
                    if(p.getProduct_instance_id().equals(bd.getProduct_instance_id())){
                        for(Productline pl : productline){
                            if(pl.getProduct_line_id().equals(p.getProduct_line_id())){
                               data.add(p.getProduct_instance_id());
                               data.add(pl.getName());
                               data.add(df.format(pl.getPrice()));
                               data.add("1");
                               
                               model.addRow(data);
                            }
                        }
                    }
                }
            }
            
        }

        chiTietHoaDonTbl.setModel(model);
        model.fireTableDataChanged();
    }

    public void UpdateProduct(String id, ArrayList<Productline> productline, ArrayList<Productinstancerfid> product) // Xuất ra Table từ ArrayList
    {
        for(Productinstancerfid pi: product)
        {   if(pi.getProduct_instance_id().equals(id)){
            
        
            for(Productline p: productline)
            {
                if(pi.getProduct_line_id().equals(p.getProduct_line_id())){
                    Productinstancerfid pupdate = new Productinstancerfid(pi.getProduct_instance_id(),p.getProduct_line_id(),1);
                      ProductinstancerfidBUS.setProductinstancerfid(pupdate);
                }
            }
            
        }

        
    }
        
    }
    
    public void UpdateProductLine(String id, ArrayList<Productline> productline) // Xuất ra Table từ ArrayList
    {
        for(Productinstancerfid pi: product)
        {   if(pi.getProduct_instance_id().equals(id)){
            for(Productline pl: productline)
            {
                if(pi.getProduct_line_id().equals(pl.getProduct_line_id())){
                    Productinstancerfid pupdate = new Productinstancerfid(pi.getProduct_instance_id(),pl.getProduct_line_id(),1);
                      ProductinstancerfidBUS.setProductinstancerfid(pupdate);
                      Productline plupdate = new Productline(pl.getProduct_line_id(),pl.getName(),pl.getPrice(),pl.getStock() - 1);
                      ProductlineBUS.setProductline(plupdate);
                }
            }
            
        }

        
    }
        
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        thanhToanPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        xoaBtn = new javax.swing.JButton();
        scanBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        thanhToanTbl = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        tongTienLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        khachDuaTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tenNhanVienjbl = new javax.swing.JLabel();
        thanhToanBtn = new javax.swing.JButton();
        tinhTienBtn = new javax.swing.JButton();
        customerJComboBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tienThoiLbl = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        tenKhachHangjbl = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        hoaDonTbl = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        chiTietHoaDonTbl = new javax.swing.JTable();
        Jlabel6 = new javax.swing.JLabel();
        doanhThuJlb = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        billIdSearchJbl = new javax.swing.JTextField();
        totalSearchJbl = new javax.swing.JTextField();
        dateSearchJbl = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        sdtKhachHangSearchJbl = new javax.swing.JTextField();
        sdtNhanVienSearchJbl = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        countJbll = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        thanhToanPane.setForeground(new java.awt.Color(51, 51, 255));
        thanhToanPane.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        xoaBtn.setBackground(new java.awt.Color(255, 51, 51));
        xoaBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        xoaBtn.setText("Xóa");
        xoaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaBtnActionPerformed(evt);
            }
        });
        jPanel1.add(xoaBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 0, 160, 110));

        scanBtn.setBackground(new java.awt.Color(102, 255, 0));
        scanBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        scanBtn.setText("SCAN");
        scanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scanBtnActionPerformed(evt);
            }
        });
        jPanel1.add(scanBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, 260, 110));

        thanhToanTbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        thanhToanTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên Sản Phẩm", "Giá", "Số Lượng"
            }
        )
        {
            public boolean isCellEditable(int row,int column){
                return false;
            }
        }
    );
    thanhToanTbl.setFocusable(false);
    thanhToanTbl.setGridColor(new java.awt.Color(0, 0, 0));
    thanhToanTbl.setRowHeight(50);
    thanhToanTbl.setSelectionBackground(new java.awt.Color(153, 255, 153));
    thanhToanTbl.getTableHeader().setReorderingAllowed(false);
    thanhToanTbl.setUpdateSelectionOnSort(false);
    thanhToanTbl.setVerifyInputWhenFocusTarget(false);
    thanhToanTbl.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            thanhToanTblMouseClicked(evt);
        }
    });
    thanhToanTbl.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            thanhToanTblKeyPressed(evt);
        }
    });
    jScrollPane1.setViewportView(thanhToanTbl);

    jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 430));

    jLabel1.setBackground(new java.awt.Color(255, 51, 0));
    jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    jLabel1.setForeground(new java.awt.Color(255, 153, 51));
    jLabel1.setText("Tổng Tiền:");
    jLabel1.setToolTipText("");
    jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 130, 70));

    tongTienLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    tongTienLabel.setForeground(new java.awt.Color(255, 153, 51));
    jPanel1.add(tongTienLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 440, 660, 70));

    jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel2.setForeground(new java.awt.Color(255, 153, 51));
    jLabel2.setText("Thối Khách:");
    jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 290, 120, 60));

    khachDuaTxt.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    khachDuaTxt.setForeground(new java.awt.Color(255, 153, 51));
    khachDuaTxt.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            khachDuaTxtKeyPressed(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            khachDuaTxtKeyTyped(evt);
        }
    });
    jPanel1.add(khachDuaTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 210, 320, 50));

    jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel3.setForeground(new java.awt.Color(255, 153, 51));
    jLabel3.setText("Khách Hàng:");
    jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 120, 120, 60));

    tenNhanVienjbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    tenNhanVienjbl.setForeground(new java.awt.Color(255, 153, 51));
    jPanel1.add(tenNhanVienjbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 360, 170, 60));

    thanhToanBtn.setBackground(new java.awt.Color(102, 255, 0));
    thanhToanBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    thanhToanBtn.setText("Thanh Toán");
    thanhToanBtn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            thanhToanBtnActionPerformed(evt);
        }
    });
    jPanel1.add(thanhToanBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 440, 380, 120));

    tinhTienBtn.setBackground(new java.awt.Color(102, 255, 0));
    tinhTienBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    tinhTienBtn.setText("Tính Tiền Thừa");
    tinhTienBtn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            tinhTienBtnActionPerformed(evt);
        }
    });
    jPanel1.add(tinhTienBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 440, 350, 120));

    customerJComboBox.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    customerJComboBox.setForeground(new java.awt.Color(255, 153, 51));
    customerJComboBox.setToolTipText("");
    customerJComboBox.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            customerJComboBoxFocusGained(evt);
        }
    });
    customerJComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            customerJComboBoxMouseClicked(evt);
        }
    });
    customerJComboBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            customerJComboBoxActionPerformed(evt);
        }
    });
    jPanel1.add(customerJComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 130, 320, 50));

    jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel7.setForeground(new java.awt.Color(255, 153, 51));
    jLabel7.setText("Khách Hàng:");
    jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 360, 120, 60));

    jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jButton1.setText("...");
    jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jButton1MouseClicked(evt);
        }
    });
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });
    jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1340, 130, 70, 50));

    jLabel8.setBackground(new java.awt.Color(255, 255, 255));
    jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    jLabel8.setForeground(new java.awt.Color(255, 153, 51));
    jLabel8.setText(",000 VNĐ");
    jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 210, 130, 50));

    jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel9.setForeground(new java.awt.Color(255, 153, 51));
    jLabel9.setText("Khách Đưa:");
    jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 210, 120, 60));

    tienThoiLbl.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    tienThoiLbl.setForeground(new java.awt.Color(255, 153, 51));
    jPanel1.add(tienThoiLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 290, 340, 50));

    jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_available_updates_30px.png"))); // NOI18N
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });
    jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1420, 130, 60, 50));

    jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel17.setForeground(new java.awt.Color(255, 153, 51));
    jLabel17.setText("Nhân Viên:");
    jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 360, 120, 60));

    tenKhachHangjbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    tenKhachHangjbl.setForeground(new java.awt.Color(255, 153, 51));
    jPanel1.add(tenKhachHangjbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 360, 250, 60));

    jButton3.setBackground(new java.awt.Color(255, 51, 51));
    jButton3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
    jButton3.setText("Xóa hết");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
        }
    });
    jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 0, 310, 110));

    thanhToanPane.addTab("Thanh Toán", jPanel1);

    jPanel2.setBackground(new java.awt.Color(255, 255, 255));

    jLabel4.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
    jLabel4.setForeground(new java.awt.Color(255, 153, 51));
    jLabel4.setText("Chi Tiết Hóa Đơn");
    jLabel4.setToolTipText("");
    jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

    jLabel5.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
    jLabel5.setForeground(new java.awt.Color(255, 153, 51));
    jLabel5.setText("Hóa Đơn");
    jLabel5.setToolTipText("");
    jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

    hoaDonTbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    hoaDonTbl.setForeground(new java.awt.Color(255, 153, 51));
    hoaDonTbl.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Mã hóa đơn","Nhân viên","Khách Hàng", "Ngày lập hóa đơn","Tổng Tiền"
        }
    )
    {
        public boolean isCellEditable(int row,int column){
            return false;
        }
    }

    );
    hoaDonTbl.setFocusable(false);
    hoaDonTbl.setRowHeight(50);
    hoaDonTbl.setSelectionBackground(new java.awt.Color(153, 255, 153));
    hoaDonTbl.getTableHeader().setReorderingAllowed(false);
    hoaDonTbl.setUpdateSelectionOnSort(false);
    hoaDonTbl.setVerifyInputWhenFocusTarget(false);
    hoaDonTbl.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            hoaDonTblMouseClicked(evt);
        }
    });
    hoaDonTbl.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            hoaDonTblKeyPressed(evt);
        }
    });
    jScrollPane5.setViewportView(hoaDonTbl);

    chiTietHoaDonTbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    chiTietHoaDonTbl.setForeground(new java.awt.Color(255, 153, 51));
    chiTietHoaDonTbl.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Mã Sản Phẩm", "Tên Sản Phẩm", "Giá","Số lượng"
        }
    )
    {
        public boolean isCellEditable(int row,int column){
            return false;
        }
    }

    );
    chiTietHoaDonTbl.setFocusable(false);
    chiTietHoaDonTbl.setRowHeight(50);
    chiTietHoaDonTbl.setSelectionBackground(new java.awt.Color(153, 255, 153));
    chiTietHoaDonTbl.getTableHeader().setReorderingAllowed(false);
    chiTietHoaDonTbl.setUpdateSelectionOnSort(false);
    chiTietHoaDonTbl.setVerifyInputWhenFocusTarget(false);
    chiTietHoaDonTbl.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            chiTietHoaDonTblMouseClicked(evt);
        }
    });
    chiTietHoaDonTbl.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            chiTietHoaDonTblKeyPressed(evt);
        }
    });
    jScrollPane6.setViewportView(chiTietHoaDonTbl);

    Jlabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    Jlabel6.setForeground(new java.awt.Color(255, 153, 51));
    Jlabel6.setText("Tổng Doanh Thu:");

    doanhThuJlb.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    doanhThuJlb.setForeground(new java.awt.Color(255, 153, 51));

    jLabel6.setBackground(new java.awt.Color(255, 255, 255));
    jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel6.setForeground(new java.awt.Color(255, 153, 51));
    jLabel6.setText("Tìm Kiếm Theo:");

    billIdSearchJbl.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            billIdSearchJblActionPerformed(evt);
        }
    });
    billIdSearchJbl.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            billIdSearchJblKeyReleased(evt);
        }
    });

    totalSearchJbl.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            totalSearchJblKeyReleased(evt);
        }
    });

    dateSearchJbl.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            dateSearchJblActionPerformed(evt);
        }
    });
    dateSearchJbl.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            dateSearchJblKeyReleased(evt);
        }
    });

    jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel10.setForeground(new java.awt.Color(255, 153, 51));
    jLabel10.setText("Nhân Viên");

    jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel12.setForeground(new java.awt.Color(255, 153, 51));
    jLabel12.setText("SĐT Khách Hàng");

    jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel13.setForeground(new java.awt.Color(255, 153, 51));
    jLabel13.setText("Ngày");

    jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel15.setForeground(new java.awt.Color(255, 153, 51));
    jLabel15.setText("Tổng Tiền");

    sdtKhachHangSearchJbl.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            sdtKhachHangSearchJblKeyReleased(evt);
        }
    });

    sdtNhanVienSearchJbl.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            sdtNhanVienSearchJblKeyReleased(evt);
        }
    });

    jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8_search_50px.png"))); // NOI18N

    jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel14.setForeground(new java.awt.Color(255, 153, 51));
    jLabel14.setText("Tổng Số Lượng :");

    countJbll.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    countJbll.setForeground(new java.awt.Color(255, 153, 51));

    jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    jLabel18.setForeground(new java.awt.Color(255, 153, 51));
    jLabel18.setText("ID Hóa Đơn");

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane5)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(388, 388, 388)
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4)
            .addGap(149, 149, 149))
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(billIdSearchJbl, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(89, 89, 89)
            .addComponent(sdtNhanVienSearchJbl, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE)
            .addComponent(sdtKhachHangSearchJbl, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(113, 113, 113)
            .addComponent(dateSearchJbl, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(99, 99, 99)
            .addComponent(totalSearchJbl, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(18, 18, 18)
            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(countJbll, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(62, 62, 62)
            .addComponent(jLabel18)
            .addGap(201, 201, 201)
            .addComponent(jLabel10)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(221, 221, 221)
            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(212, 212, 212)
            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(60, 60, 60))
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(13, 13, 13)
            .addComponent(Jlabel6)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(doanhThuJlb, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(76, 76, 76)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(sdtKhachHangSearchJbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(billIdSearchJbl, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sdtNhanVienSearchJbl, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(countJbll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(totalSearchJbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dateSearchJbl, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(Jlabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(doanhThuJlb, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    thanhToanPane.addTab("Hóa Đơn", jPanel2);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(thanhToanPane)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(thanhToanPane)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void xoaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaBtnActionPerformed
        // TODO add your handling code here:
        // TODO Auto-generated method stub
        
//        System.out.println(thanhToanTbl.getSelectedRow());
         if(thanhToanTbl.getSelectedRow() != -1) {
               // remove selected row from the model
               product.remove(thanhToanTbl.getSelectedRow());
               ((DefaultTableModel)thanhToanTbl.getModel()).removeRow(thanhToanTbl.getSelectedRow());
               tinhTongTien(productline,product);
               tongTienLabel.setText(nf.format(tongTien)+"VNĐ");
               JOptionPane.showMessageDialog(null, "Xóa Sản Phẩm thành Công");
            }else{
                JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng để xóa");
            }
		
                   
                
    }//GEN-LAST:event_xoaBtnActionPerformed

    private void thanhToanTblKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_thanhToanTblKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_thanhToanTblKeyPressed

    private void thanhToanTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thanhToanTblMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_thanhToanTblMouseClicked

    private void scanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scanBtnActionPerformed
        // TODO add your handling code here:
        clearTable();
        for(int i = 0;i < product.size();i++){
            product.remove(i);
        }

//        CAENRFIDReader MyReader = new CAENRFIDReader();
//		try {
//			MyReader.Connect(CAENRFIDPort.CAENRFID_TCP, "192.168.1.2"); 
//	        CAENRFIDLogicalSource MySource = MyReader.GetSource("Source_0");
//	        
//	      //get Reader Infor
//	      CAENRFIDReaderInfo Info = MyReader.GetReaderInfo();
//	      String Model = Info.GetModel();
//	      String SerialNumber = Info.GetSerialNumber();
//	      String FWRelease = MyReader.GetFirmwareRelease();
//	      int power = MyReader.GetPower();
//
//	      System.out.println("Model: "+Model);
//	      System.out.println("SerialNumber: "+SerialNumber);
//	      System.out.println("FWRelease: "+FWRelease);
//	      System.out.println("power: "+power);
//	      
//	      System.out.println("");
//	      	MySource.SetSession_EPC_C1G2(CAENRFIDLogicalSourceConstants.EPC_C1G2_SESSION_S1);
//
//	        CAENRFIDTag[] MyTags = MySource.InventoryTag();
//	       
//	        if (MyTags.length > 0)
//	        {
//	            for (int i = 0; i < MyTags.length; i++)
//	            {
//	                System.out.println("EPC: "+ hex(MyTags[i].GetId())  + " | Antenna : " +MyTags[i].GetAntenna() +" | TID:"+ (MyTags[i].GetTID()) +" | RSSI : "+Integer.valueOf(MyTags[i].GetRSSI()));
//                        listTagread( hex(MyTags[i].GetId()));
//                    }
//	        }
//	        
//	    
//	        MyReader.Disconnect();
//		}catch(Exception ex) {
//			if(MyReader != null)try {
//                            MyReader.Disconnect();
//                        } catch (CAENRFIDException ex1) {
//                            Logger.getLogger(Checkout.class.getName()).log(Level.SEVERE, null, ex1);
//                        }
//		}
        
//        

        listTagread("300F4F573AD001C08369A230");
        listTagread("300F4F573AD001C08369A241");
        listTagread("300F4F573AD001C08369A245");
        listTagread("300F4F573AD001C08369A249");
        listTagread("300F4F573AD001C08369A28F");

//        listTagread("001");
//        listTagread("002");
//        listTagread("003");
//        listTagread("004");
//        listTagread("005");
//        listTagread("006");
//        listTagread("007");
//        listTagread("008");
//        listTagread("009");
//        listTagread("0011");
//        listTagread("0012");
//        listTagread("0014");
//        listTagread("0015");
//        listTagread("0016");
//        listTagread("0017");
//        listTagread("0020");
//        listTagread("0022");
        
//        
      
    }//GEN-LAST:event_scanBtnActionPerformed

    private void hoaDonTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hoaDonTblMouseClicked
        // TODO add your handling code here:
        String id = hoaDonTbl.getModel().getValueAt(hoaDonTbl.getSelectedRow(), 0).toString();
        outModelChiTietHoaDon(model,id,billdetails);
        
        DefaultTableModel dmcthd = (DefaultTableModel) chiTietHoaDonTbl.getModel();
            int rowCount = dmcthd.getRowCount();
            int count = 0;
            //Remove rows one by one from the end of the table
            for (int i = rowCount - 1; i >= 0; i--) {
                count += Integer.parseInt((String) dmcthd.getValueAt(i, 3));
            }
            countJbll.setText(String.valueOf(count));
    }//GEN-LAST:event_hoaDonTblMouseClicked

    private void hoaDonTblKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hoaDonTblKeyPressed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_hoaDonTblKeyPressed

    private void chiTietHoaDonTblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chiTietHoaDonTblMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_chiTietHoaDonTblMouseClicked

    private void chiTietHoaDonTblKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chiTietHoaDonTblKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_chiTietHoaDonTblKeyPressed

    private void thanhToanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thanhToanBtnActionPerformed
        // TODO add your handling code here:
        
        if (thanhToanTbl.getRowCount()!=0){
            
        bill = BillBUS.getList();
        if(bill.size() == 0){
            billId = "HD1";
        }else{
            String HD = "HD";
            billId = HD +  Integer.toString(bill.size()+1);
        }
        
        Date date = new Date();
        SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        

        String customerGetted = (String) customerJComboBox.getSelectedItem();
        String customerId = "";
        String staffName = "";
        if(customerGetted.equals("Khách Hàng")){
            customerId = "Khách Hàng";
        }else{
            for(Customer c: customer){
                if(customerGetted.equals(c.getCustomer_id())){
                    customerId = c.getCustomer_id();
                }
            }
        }
        
        for(Staff s: staff){
                if(Staffid.equals(s.getStaff_id())){
                    staffName = s.getName();
                }
            }
        
        Bill b = new Bill(billId,Staffid,staffName,customerId,formatterDate.format(date),String.valueOf(tongTien));
        BillBUS.addBill(b);
        
        for (int count = 0; count < thanhToanTbl.getRowCount(); count++){
            Billdetails bd = new Billdetails(billId,thanhToanTbl.getModel().getValueAt(count, 0).toString());
            BilldetailsBUS.addBilldetails(bd);
            
            UpdateProduct(thanhToanTbl.getModel().getValueAt(count, 0).toString(),productline,product);
            productline = ProductlineBUS.getList();
            UpdateProductLine(thanhToanTbl.getModel().getValueAt(count, 0).toString(),productline);
        }
        
        JOptionPane.showMessageDialog(null, "Thanh Toán Thành Công");
            khachDuaTxt.setText("");
            tienThoiLbl.setText("");
            tongTienLabel.setText("");
            thanhToanTbl.removeAll();
            customerJComboBox.setSelectedItem("Khách Hàng");
            
            DefaultTableModel dm = (DefaultTableModel) thanhToanTbl.getModel();
            int rowCount = dm.getRowCount();
            //Remove rows one by one from the end of the table
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
        ArrayList<Bill> outbill = BillBUS.getList();
        outModelHoaDon(model,outbill,staff,customer);
        PdfRFID PdfRFID = new PdfRFID();
        PdfRFID.export(billId, Staffid, customerId);
        }else{
            JOptionPane.showMessageDialog(null, "Không có sản phẩm nào trong giỏ hàng");
        }
        
//        
    }//GEN-LAST:event_thanhToanBtnActionPerformed

    private void tinhTienBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tinhTienBtnActionPerformed
        // TODO add your handling code here:
        if(khachDuaTxt.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Bạn chưa nhập số tiền");
        }else{
            if(isNumeric(khachDuaTxt.getText())){
            String khachDua = khachDuaTxt.getText()+"000";

                if(Float.parseFloat(khachDua) >= tongTien){
                    tienThoiLbl.setText(nf.format(Float.parseFloat(khachDua) - tongTien )+"VNĐ");
                }else{
                    JOptionPane.showMessageDialog(null, "Số tiền không hợp lệ");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Xin chỉ nhập số");
            }
        }
        
        
    }//GEN-LAST:event_tinhTienBtnActionPerformed

    private void khachDuaTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_khachDuaTxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_khachDuaTxtKeyTyped

    private void khachDuaTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_khachDuaTxtKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_khachDuaTxtKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//         addCustomer.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
       manegementCustomer.setVisible(true);
    }//GEN-LAST:event_jButton1MouseClicked

    private void customerJComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_customerJComboBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_customerJComboBoxMouseClicked

    private void customerJComboBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_customerJComboBoxFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_customerJComboBoxFocusGained

    private void billIdSearchJblKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billIdSearchJblKeyReleased
        // TODO add your handling code here:
        
        String BillIdSearch = toUpperCase(billIdSearchJbl.getText());
        String NhanVienSearch = toUpperCase(sdtNhanVienSearchJbl.getText());
        String KhachHangSearch = toUpperCase(sdtKhachHangSearchJbl.getText());
        String DateSearch = toUpperCase(dateSearchJbl.getText());
        String TotalSearch = toUpperCase(totalSearchJbl.getText());
        
        outModelHoaDon(model,BillBUS.search(BillIdSearch, NhanVienSearch, KhachHangSearch, DateSearch, TotalSearch),staff,customer);
    }//GEN-LAST:event_billIdSearchJblKeyReleased

    private void sdtKhachHangSearchJblKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sdtKhachHangSearchJblKeyReleased

        
        String BillIdSearch = toUpperCase(billIdSearchJbl.getText());
        String NhanVienSearch = toUpperCase(sdtNhanVienSearchJbl.getText());
        String KhachHangSearch = toUpperCase(sdtKhachHangSearchJbl.getText());
        String DateSearch = toUpperCase(dateSearchJbl.getText());
        String TotalSearch = toUpperCase(totalSearchJbl.getText());
        
        outModelHoaDon(model,BillBUS.search(BillIdSearch, NhanVienSearch, KhachHangSearch, DateSearch, TotalSearch),staff,customer);
    }//GEN-LAST:event_sdtKhachHangSearchJblKeyReleased

    private void sdtNhanVienSearchJblKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sdtNhanVienSearchJblKeyReleased
        // TODO add your handling code here:
        
        String BillIdSearch = toUpperCase(billIdSearchJbl.getText());
        String NhanVienSearch = toUpperCase(sdtNhanVienSearchJbl.getText());
        String KhachHangSearch = toUpperCase(sdtKhachHangSearchJbl.getText());
        String DateSearch = toUpperCase(dateSearchJbl.getText());
        String TotalSearch = toUpperCase(totalSearchJbl.getText());
        
        outModelHoaDon(model,BillBUS.search(BillIdSearch, NhanVienSearch, KhachHangSearch, DateSearch, TotalSearch),staff,customer);
    }//GEN-LAST:event_sdtNhanVienSearchJblKeyReleased

    private void dateSearchJblKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateSearchJblKeyReleased
        // TODO add your handling code here:
        
        String BillIdSearch = toUpperCase(billIdSearchJbl.getText());
        String NhanVienSearch = toUpperCase(sdtNhanVienSearchJbl.getText());
        String KhachHangSearch = toUpperCase(sdtKhachHangSearchJbl.getText());
        String DateSearch = toUpperCase(dateSearchJbl.getText());
        String TotalSearch = toUpperCase(totalSearchJbl.getText());
        
        outModelHoaDon(model,BillBUS.search(BillIdSearch, NhanVienSearch, KhachHangSearch, DateSearch, TotalSearch),staff,customer);
    }//GEN-LAST:event_dateSearchJblKeyReleased

    private void totalSearchJblKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_totalSearchJblKeyReleased
        // TODO add your handling code here:
        
        String BillIdSearch = toUpperCase(billIdSearchJbl.getText());
        String NhanVienSearch = toUpperCase(sdtNhanVienSearchJbl.getText());
        String KhachHangSearch = toUpperCase(sdtKhachHangSearchJbl.getText());
        String DateSearch = toUpperCase(dateSearchJbl.getText());
        String TotalSearch = toUpperCase(totalSearchJbl.getText());
        
        outModelHoaDon(model,BillBUS.search(BillIdSearch, NhanVienSearch, KhachHangSearch, DateSearch, TotalSearch),staff,customer);
    }//GEN-LAST:event_totalSearchJblKeyReleased

    private void customerJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customerJComboBoxActionPerformed
        // TODO add your handling code here:
        String customerId = (String) customerJComboBox.getSelectedItem();
        String customerName ="";
        if(customerId != null){
            for(Customer c : customer){
                if(customerId.equals(c.getCustomer_id())){
                    customerName = c.getName();
                }
            }
        }
        tenKhachHangjbl.setText(customerName);
    }//GEN-LAST:event_customerJComboBoxActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        customer = CustomerBUS.getList();
        customerJComboBox.removeAllItems();
        setValueJcombo(customer);
        customerJComboBox.setSelectedItem("Khách Hàng");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void billIdSearchJblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billIdSearchJblActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_billIdSearchJblActionPerformed

    private void dateSearchJblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateSearchJblActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateSearchJblActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel dm = (DefaultTableModel) thanhToanTbl.getModel();
            int rowCount = dm.getRowCount();
            //Remove rows one by one from the end of the table
            if(rowCount == 0){
                JOptionPane.showMessageDialog(null, "Không có sản phẩm để xóa");
            }else{
                for (int i = rowCount - 1; i >= 0; i--) {
                    dm.removeRow(i);
                    product.remove(i);
                }
                JOptionPane.showMessageDialog(null, "Xóa Sản Phẩm thành Công");
            }

    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows Vista".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Checkout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Checkout().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Jlabel6;
    private javax.swing.JTextField billIdSearchJbl;
    private javax.swing.JTable chiTietHoaDonTbl;
    private javax.swing.JLabel countJbll;
    private javax.swing.JComboBox<String> customerJComboBox;
    private javax.swing.JTextField dateSearchJbl;
    private javax.swing.JLabel doanhThuJlb;
    private javax.swing.JTable hoaDonTbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField khachDuaTxt;
    private javax.swing.JButton scanBtn;
    private javax.swing.JTextField sdtKhachHangSearchJbl;
    private javax.swing.JTextField sdtNhanVienSearchJbl;
    private javax.swing.JLabel tenKhachHangjbl;
    private javax.swing.JLabel tenNhanVienjbl;
    private javax.swing.JButton thanhToanBtn;
    private javax.swing.JTabbedPane thanhToanPane;
    private javax.swing.JTable thanhToanTbl;
    private javax.swing.JLabel tienThoiLbl;
    private javax.swing.JButton tinhTienBtn;
    private javax.swing.JLabel tongTienLabel;
    private javax.swing.JTextField totalSearchJbl;
    private javax.swing.JButton xoaBtn;
    // End of variables declaration//GEN-END:variables
}
