/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internalPage;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SCC-COMLAB
 */
public class dashBoardPages extends javax.swing.JInternalFrame {
  private Connection con;
  DefaultTableModel model;
    /**
     * Creates new form dashBoardPages
     */
    public dashBoardPages() {
        initComponents();
        total();
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
        
    }
    Color navcolor= new Color(0,102,102);
    Color headcolor= new Color(0,204,204);
    Color bodycolor = new Color(0,153,153);
    
    
    public void total(){
     PreparedStatement st = null;
     ResultSet rs = null;
     
     long l = System.currentTimeMillis();
     Date todaydate = new Date(l);

      try {
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/milktea_db", "root", "");
           st = con.prepareStatement("SELECT COUNT(*) FROM tbl_residentrecords");
           rs = st.executeQuery();
          while (rs.next()){
              int count = rs.getInt(1);
             
          total.setText(String.valueOf(count));  
          }
                          
      } catch (Exception e) {
      e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        total = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setAutoscrolls(true);
        jPanel1.setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconsfolder/logo (1).png"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(460, 110, 210, 210);

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MILKTEA TRACKER");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 250, 30));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("M and M Tandem");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 150, 30));

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 740, 70);

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setAutoscrolls(true);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("TOTAL SALES");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 150, 30));

        total.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 60, 40));

        jPanel1.add(jPanel3);
        jPanel3.setBounds(70, 160, 260, 110);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 760, 430));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables
}
