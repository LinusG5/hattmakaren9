/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hattmakaren2026_9;
import oru.inf.InfDB;
import oru.inf.InfException;
/**
 *
 * @author malms
 */
public class MaterialSidan extends javax.swing.JFrame {
    private InfDB idb;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MaterialSidan.class.getName());

    /**
     * Creates new form MaterialSidan
     */
    public MaterialSidan(InfDB idb) throws InfException  {
        initComponents();
        this.idb = idb;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnMaterialBehov = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnMaterialBehov.setText("MaterialBehov");
        btnMaterialBehov.addActionListener(this::btnMaterialBehovActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(btnMaterialBehov)
                .addContainerGap(250, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(btnMaterialBehov)
                .addContainerGap(196, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMaterialBehovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaterialBehovActionPerformed
try { 
            String valdOrderIds = "1, 2"; 
            
            String sqlFraga = "SELECT m.Namn, m.LagerSaldo, " +
                    "SUM(r.Antal * hm.Antal) AS TotaltBehov " + 
                    "FROM Ordrar o " + 
                    "JOIN Orderrader r ON o.OrderID = r.OrderID " + 
                    "JOIN Hatt_Material hm ON r.ModellID = hm.ModellID " +
                    "JOIN Material m ON hm.MaterialID = m.MaterialID " + 
                    "WHERE o.OrderID IN (" + valdOrderIds + ") " +
                    "GROUP BY m.MaterialID, m.Namn, m.LagerSaldo;";

            java.util.ArrayList<java.util.HashMap<String, String>> resultatLista = idb.fetchRows(sqlFraga);
            
            System.out.println("--- INKÖPSLISTA FÖR ORDER " + valdOrderIds + " ---");

            if(resultatLista != null && !resultatLista.isEmpty()) {
                for (java.util.HashMap<String, String> rad : resultatLista) {
                    String materialNamn = rad.get("Namn"); 
                    String behov = rad.get("TotaltBehov");
                    String saldo = rad.get("LagerSaldo");
                    
                    System.out.println("Material: " + materialNamn + " | Att tillverka kräver: " + behov + " | (I lager: " + saldo + ")");
                }
            } else { 
                System.out.println("Inget material behövs, eller så hittades inte ordrarna.");
            }
            
        } catch (InfException e){ 
            System.out.println("Ett databasfel uppstod: " + e.getMessage());
        }
    }//GEN-LAST:event_btnMaterialBehovActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMaterialBehov;
    // End of variables declaration//GEN-END:variables

}