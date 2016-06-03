/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JTable;

/**
 *
 * @author oOosu
 */
public class CheckTable {

    public static void CheckboxTable(JTable tbl, int index) {
        for (int i = 0; i < tbl.getRowCount(); i++) {
            tbl.setValueAt(Boolean.TRUE, i, index);

        }
    }

    public static void UnCheckboxTable(JTable tbl, int index) {
        for (int i = 0; i < tbl.getRowCount(); i++) {
            tbl.setValueAt(Boolean.FALSE, i, index);

        }
    }

    public static Vector getValueTable(JTable tbl, int index){
       Vector vValue = new Vector();
        for (int i = 0; i < tbl.getRowCount(); i++) {
            if(tbl.getValueAt(i, index).equals(true)){
               vValue.add(i);
            }
        }

        return vValue;
    }

    public static void OnlickJTable(final JTable tbl, final int index) {
        tbl.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    Object obj = tbl.getValueAt(tbl.getSelectedRow(), index);
                    if ((Boolean) obj == true) {
                        tbl.setValueAt(Boolean.FALSE, tbl.getSelectedRow(), index);
                    } else {
                        tbl.setValueAt(Boolean.TRUE, tbl.getSelectedRow(), index);
                    }
                }
            }
        });
    }
}
