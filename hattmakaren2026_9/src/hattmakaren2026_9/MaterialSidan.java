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
        fyllOrderLista();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnMaterialBehov = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstOrdrar = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnMaterialBehov.setText("MaterialBehov");
        btnMaterialBehov.addActionListener(this::btnMaterialBehovActionPerformed);

        jScrollPane1.setViewportView(lstOrdrar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMaterialBehov)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMaterialBehov)
                .addContainerGap(136, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMaterialBehovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaterialBehovActionPerformed
try { 
            java.util.List<String> valdaRader = lstOrdrar.getSelectedValuesList();
            
            if (valdaRader.isEmpty() || valdaRader.get(0).equals("Inga aktuella ordrar hittades.")) {
                javax.swing.JOptionPane.showMessageDialog(this, "Vänligen markera minst en order i listan först!");
                return;
            }
            StringBuilder orderIdsBuilder = new StringBuilder();
            
            for (int i = 0; i < valdaRader.size(); i++) {

                String endastSiffran = valdaRader.get(i).split(" ")[0]; 
                
                orderIdsBuilder.append(endastSiffran);
                
                if (i < valdaRader.size() - 1) {
                    orderIdsBuilder.append(", ");
                }
            }
            String valdOrderIds = orderIdsBuilder.toString();
            
String sqlFraga = 
    "SELECT m.Namn, m.LagerSaldo, o.OrderID, " +
    "SUM(r.Antal * hm.Antal) AS TotaltBehov " +
    "FROM Ordrar o " +
    "JOIN Orderrader r ON o.OrderID = r.OrderID " +
    "JOIN Hatt_Material hm ON r.ModellID = hm.ModellID " +
    "JOIN Material m ON hm.MaterialID = m.MaterialID " +
    "WHERE o.OrderID IN (" + valdOrderIds + ") " +
    "GROUP BY m.MaterialID, m.Namn, m.LagerSaldo, o.OrderID;";

            java.util.ArrayList<java.util.HashMap<String, String>> resultatLista = idb.fetchRows(sqlFraga);
            
StringBuilder textTillRutan = new StringBuilder();
textTillRutan.append("--- INKÖPSLISTA FÖR ORDER ").append(valdOrderIds).append(" ---\n\n");
            
            if(resultatLista != null && !resultatLista.isEmpty()) {
                for (java.util.HashMap<String, String> rad : resultatLista) {

    String orderID = rad.get("OrderID");
    String materialNamn = rad.get("Namn");
    String behov = rad.get("TotaltBehov");
    String saldo = rad.get("LagerSaldo");

    textTillRutan.append("Order: ").append(orderID).append("\n")
            .append("Material: ").append(materialNamn).append("\n")
            .append("Krävs för tillverkning: ").append(behov).append("\n")
            .append("(Finns i lager: ").append(saldo).append(")\n")
            .append("----------------------------\n");
}

            } else { 
                System.out.println("Inget material behövs, eller så hittades inte ordrarna.");
            }
            javax.swing.JOptionPane.showMessageDialog(this, textTillRutan.toString(), "Materialbehov", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
        } catch (InfException e){ 
javax.swing.JOptionPane.showMessageDialog(this, "Ett databasfel uppstod: " + e.getMessage(), "Ett fel uppstod", javax.swing.JOptionPane.ERROR_MESSAGE);        }
    }//GEN-LAST:event_btnMaterialBehovActionPerformed
private void fyllOrderLista() {
        try {
            String sql = "SELECT OrderID, Status FROM Ordrar WHERE Status IN ('Registrerad', 'Tillverkning')";
            java.util.ArrayList<java.util.HashMap<String, String>> ordrar = idb.fetchRows(sql);

            javax.swing.DefaultListModel<String> listModell = new javax.swing.DefaultListModel<>();

            if (ordrar != null) {
                for (java.util.HashMap<String, String> order : ordrar) {
                    String rad = order.get("OrderID") + " - " + order.get("Status");
                    listModell.addElement(rad);
                }
            } else {
                listModell.addElement("Inga aktuella ordrar hittades.");
            }

            lstOrdrar.setModel(listModell);

        } catch (InfException e) {
            System.out.println("Kunde inte ladda ordrar: " + e.getMessage());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMaterialBehov;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lstOrdrar;
    // End of variables declaration//GEN-END:variables
}