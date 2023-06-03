/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalPage;

import config.db_configuration;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author SCC-COMLAB
 */
public class cashierss extends javax.swing.JFrame {

    /**
     * Creates new form cashierss
     */
    public cashierss() {
        initComponents();
        
        displayData();
    }
        public void reset(){
        pid.setText("");
        firstname.setText("");
        lastname.setText("");
        gender.setText("");
        status.setText("");
        date.setText("");
        
    }
        public byte[] imageBytes;
    String path;
    String action;
    String filename=null;
    String imgPath = null;
    byte[] person_image = null; 
    DefaultTableModel model;
    private Connection con;
        
    public  ImageIcon ResizeImage(String ImagePath, byte[] pic) {
    ImageIcon MyImage = null;
        if(ImagePath !=null){
            MyImage = new ImageIcon(ImagePath);
        }else{
            MyImage = new ImageIcon(pic);
        }
    Image img = MyImage.getImage();
    Image newImg = img.getScaledInstance(picv.getWidth(), picv.getHeight(), Image.SCALE_SMOOTH);
    ImageIcon image = new ImageIcon(newImg);
    return image;
}    
    public boolean validation(){
        String fname= firstname.getText();
        String lname= lastname.getText();
        String gend= gender.getText();
        String stat= status.getText();
        String trans= date.getText();

 if (firstname.equals("")){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER YOUR FIRSTNAME");
 return false;
 }
 if(lastname.equals("")){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER YOUR LASTNAME");
 return false;
 }
if(gender.equals("")){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER YOUR GENDER");
 return false;
 }     
 if(status.equals("")){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER YOUR STATUS");
 return false;
 }if(date.equals("")){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER THE TRANSACTION DATE");
 return false;
 }
  
    if(picv.getIcon()==null){
 JOptionPane.showMessageDialog(this, "PLEASE ENTER PHOTO");
 return false;
 }
   return true;  
 }
    public void add(){
      try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/milktea_db","root","");
            String sql = "INSERT INTO tbl_cashier ( c_firstname, c_lastname, c_gender , c_status, c_transactdate, c_image) VALUES (?,?,?,?,?,?)"; 
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, firstname.getText());
            pst.setString(2, lastname.getText());
            pst.setString(3, gender.getText());
            pst.setString(4, status.getText()); 
            pst.setString(5, date.getText());
            pst.setBytes(6, pic);
            pst.executeUpdate();
           
            displayData();
               reset(); 
        JOptionPane.showMessageDialog(this, "SUCCESSFULLY SAVE");
            }catch(SQLException e){
                System.err.println("Cannot connect to database: " + e.getMessage());
            }
     
     }
        public void update(){
         try {
         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/milktea_db","root","");
         int row =tablecashier.getSelectedRow();
         String value = (tablecashier.getModel().getValueAt(row, 0).toString());
         String sql = "UPDATE tbl_cashier SET c_firstname=?, c_lastname=?, c_gender=?, c_status=?, c_transactdate=?, c_image=? where c_id="+value;
            PreparedStatement pst = con.prepareStatement(sql);
           
            pst.setString(1, firstname.getText());
            pst.setString(2, lastname.getText());
            pst.setString(3, gender.getText());
            pst.setString(4, status.getText());
            pst.setString(5, date.getText());
            pst.setBytes(6, pic);
            pst.executeUpdate();
           if(row == 0){
            JOptionPane.showMessageDialog(null, "Updated FAILED!");
        }else{
           JOptionPane.showMessageDialog(null, "Updated Successfully!");
           displayData();
           reset();
        }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
        public void upload(){
     JFileChooser chose = new JFileChooser();
     chose.showOpenDialog(null);
     File f = chose.getSelectedFile();
         filename = f.getAbsolutePath();
         ImageIcon ii = new ImageIcon(filename);
         Image img = ii.getImage().getScaledInstance(picv.getWidth(), picv.getHeight(), Image.SCALE_SMOOTH);
     picv.setIcon(new ImageIcon(img));
         try {
             File ig = new File(filename);
             FileInputStream is = new FileInputStream(ig);
             ByteArrayOutputStream bos =  new ByteArrayOutputStream();
             byte[] buf = new byte [1024];
             for (int rnum; (rnum = is.read(buf))!=-1;){
             bos.write(buf, 0, rnum);
             }
             pic =bos.toByteArray();
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e);
         }
          
    }
        public void table(){
            int row = tablecashier.getSelectedRow();
            int cc = tablecashier.getSelectedColumn();
            String tc = tablecashier.getModel().getValueAt(row, 0).toString();
             try{
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/milktea_db","root","");
             String sql = "select * from tbl_cashier where c_id="+tc+"";
             PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
            int id=rs.getInt("c_id");
            String fname=rs.getString("c_firstname");
            String lname=rs.getString("c_lastname");
            String gend=rs.getString("c_gender");
            String stat=rs.getString("c_status");
            String dat=rs.getString("c_transactdate");
        
            byte[] img = rs.getBytes("c_image");
            format = new ImageIcon(img);
            Image im =format.getImage().getScaledInstance(picv.getWidth(), picv.getHeight(), Image.SCALE_SMOOTH);
            picv.setIcon(new ImageIcon(im));
            
            
                pid.setText(""+id);
                firstname.setText(fname);
                lastname.setText(lname);
                gender.setText(gend);
                status.setText(stat);
                date.setText(dat);
           
         
            }
             pst.close();
             rs.close();
         } catch (Exception e) {
         JOptionPane.showMessageDialog(null, e);
         }
        }
    
    
        public void displayData(){
       
        try{
       
            db_configuration dbc = new db_configuration();
            ResultSet rs = dbc.getData("SELECT * FROM tbl_cashier");
            tablecashier.setModel(DbUtils.resultSetToTableModel(rs));
       
        }catch(SQLException ex){
            System.out.println("Error Message: "+ex);
       
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

        jPanel2 = new javax.swing.JPanel();
        date = new javax.swing.JTextField();
        pid = new javax.swing.JTextField();
        lastname = new javax.swing.JTextField();
        status = new javax.swing.JTextField();
        delete = new javax.swing.JButton();
        insert = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablecashier = new javax.swing.JTable();
        update = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        firstname = new javax.swing.JTextField();
        gender = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        display1 = new javax.swing.JButton();
        back = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        picv = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        browse1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setLayout(null);

        date.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateActionPerformed(evt);
            }
        });
        jPanel2.add(date);
        date.setBounds(160, 300, 270, 30);

        pid.setEditable(false);
        pid.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        pid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pidActionPerformed(evt);
            }
        });
        jPanel2.add(pid);
        pid.setBounds(160, 100, 270, 30);

        lastname.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lastname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastnameActionPerformed(evt);
            }
        });
        jPanel2.add(lastname);
        lastname.setBounds(160, 180, 270, 30);

        status.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });
        jPanel2.add(status);
        status.setBounds(160, 260, 270, 30);

        delete.setBackground(new java.awt.Color(0, 204, 204));
        delete.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        delete.setText("DELETE");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        jPanel2.add(delete);
        delete.setBounds(400, 60, 90, 30);

        insert.setBackground(new java.awt.Color(0, 204, 204));
        insert.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        insert.setText("ADD");
        insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertActionPerformed(evt);
            }
        });
        jPanel2.add(insert);
        insert.setBounds(600, 60, 90, 30);

        clear.setBackground(new java.awt.Color(0, 204, 204));
        clear.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        clear.setText("CLEAR");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });
        jPanel2.add(clear);
        clear.setBounds(500, 60, 90, 30);

        tablecashier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablecashierMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablecashier);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(470, 100, 420, 270);

        update.setBackground(new java.awt.Color(0, 204, 204));
        update.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        update.setText("UPDATE");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        jPanel2.add(update);
        update.setBounds(700, 60, 90, 30);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ID:");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(40, 100, 120, 30);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("First Name:");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(40, 140, 120, 30);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Last Name:");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(40, 180, 120, 30);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Gender:");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(40, 220, 120, 30);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Status:");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(40, 260, 120, 30);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Upload Image:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(40, 340, 120, 30);

        firstname.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        firstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstnameActionPerformed(evt);
            }
        });
        jPanel2.add(firstname);
        firstname.setBounds(160, 140, 270, 30);

        gender.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jPanel2.add(gender);
        gender.setBounds(160, 220, 270, 30);

        jPanel3.setBackground(new java.awt.Color(0, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("CASHIER");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 40));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("X");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 0, 40, 40));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("__");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, 40, 40));

        jPanel2.add(jPanel3);
        jPanel3.setBounds(0, 0, 920, 40);

        display1.setBackground(new java.awt.Color(0, 204, 204));
        display1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        display1.setText("DISPLAY");
        display1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                display1ActionPerformed(evt);
            }
        });
        jPanel2.add(display1);
        display1.setBounds(800, 60, 90, 30);

        back.setBackground(new java.awt.Color(0, 204, 204));
        back.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        back.setText("BACK");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        jPanel2.add(back);
        back.setBounds(790, 440, 90, 30);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(picv, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 130));

        jPanel2.add(jPanel1);
        jPanel1.setBounds(160, 340, 140, 130);

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Transaction Date:");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(40, 300, 120, 30);

        browse1.setBackground(new java.awt.Color(0, 204, 204));
        browse1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        browse1.setText("BROWSE");
        browse1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browse1ActionPerformed(evt);
            }
        });
        jPanel2.add(browse1);
        browse1.setBounds(310, 440, 90, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateActionPerformed

    private void pidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pidActionPerformed

    private void lastnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastnameActionPerformed

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusActionPerformed

    private void firstnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstnameActionPerformed

    private void tablecashierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablecashierMouseClicked
        table();
    }//GEN-LAST:event_tablecashierMouseClicked

    private void insertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertActionPerformed
        if (validation () == true){
         add();
     }
    }//GEN-LAST:event_insertActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        if(validation()== true){
     update();
        }
    }//GEN-LAST:event_updateActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        int rowIndex = tablecashier.getSelectedRow();
       if(rowIndex < 0){
           JOptionPane.showMessageDialog(null, "Please select a data first");
       }else{
            TableModel model = tablecashier.getModel();
            Object value = model.getValueAt(rowIndex, 0);
            String id = value.toString();
             int a=JOptionPane.showConfirmDialog(null,"Are you sure?");  
                    if(a==JOptionPane.YES_OPTION){  
                            db_configuration dbc = new db_configuration();
                            dbc.DeleteData(Integer.parseInt(id));
                            displayData();
                            reset();
                    }    
       }       
    }//GEN-LAST:event_deleteActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        reset();
    }//GEN-LAST:event_clearActionPerformed

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        setState(ICONIFIED);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        int a=JOptionPane.showConfirmDialog(null, "Confirm Exit?");
        if(a==JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_jLabel10MouseClicked

    private void display1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_display1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_display1ActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        dashBoard db = new dashBoard();
        db.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backActionPerformed

    private void browse1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browse1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_browse1ActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(cashierss.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cashierss.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cashierss.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cashierss.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new cashierss().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JButton browse1;
    private javax.swing.JButton clear;
    private javax.swing.JTextField date;
    private javax.swing.JButton delete;
    private javax.swing.JButton display1;
    private javax.swing.JTextField firstname;
    private javax.swing.JTextField gender;
    private javax.swing.JButton insert;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lastname;
    private javax.swing.JLabel picv;
    private javax.swing.JTextField pid;
    private javax.swing.JTextField status;
    private javax.swing.JTable tablecashier;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
String filen= null;
byte[] pic = null; 
private ImageIcon format = null;

}

