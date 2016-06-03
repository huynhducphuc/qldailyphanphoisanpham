/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import bean.Position;
import javax.swing.JFrame;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.FailLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FrameLoadEvent;
import com.teamdev.jxbrowser.chromium.events.LoadEvent;
import com.teamdev.jxbrowser.chromium.events.LoadListener;
import com.teamdev.jxbrowser.chromium.events.ProvisionalLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.StartLoadingEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author oOosu
 */
public class SetPosition extends JFrame implements ActionListener, LoadListener {

    AddNewSupermarket parent = null;
    ViewInfoSupermarket parent2 = null;
    JButton btn_Save, btn_Cancel, btn_Setup;
    public static final int MIN_ZOOM = 0;
    public static final int MAX_ZOOM = 21;
    private static int zoomValue = 6;
    private static Position position = new Position();
    final Browser browser = new Browser();
    BrowserView browserView = new BrowserView(browser);
    String lati = "", longi = "", supmName = "";

    public SetPosition(String latitude, String longitude,AddNewSupermarket parent) {
        this.parent = parent;
        this.lati = latitude;
        this.longi = longitude;
        initComponents();
        this.setVisible(true);
        this.setTitle("Set Position");
    }

    public SetPosition(String latitude, String longitude, String supmName, ViewInfoSupermarket parent2) {
        this.parent2 = parent2;
        this.lati = latitude;
        this.longi = longitude;
        this.supmName = supmName;
        initComponents();
        this.setVisible(true);
        this.setTitle("Set Position");

    }

    void setPlace() {
        String javaScript = "var myLatlng = new google.maps.LatLng(" + lati + "," + longi + ");\n"
                + "var marker = new google.maps.Marker({\n" + "    position: myLatlng,\n" + "    map: map,\n"
                + "    title: '" + supmName + "'\n" + "});";
        browser.executeJavaScript(javaScript);
    }
    
    void replacePlace(){
        String javaScript = "marker.setMap(null);\n";
        browser.executeJavaScript(javaScript);
        javaScript = "var myLatlng = new google.maps.LatLng(" + position.getLatitude() + "," + position.getLongitude() + ");\n"
                + "var marker = new google.maps.Marker({\n" + "    position: myLatlng,\n" + "    map: map,\n"
                + "    title: '" + "New Place" + "'\n" + "});";
        browser.executeJavaScript(javaScript);
    }

    void initComponents() {
        browser.addLoadListener(this);
        btn_Setup = new JButton("Set");
        btn_Save = new JButton("Save");
        btn_Cancel = new JButton("Cancel");
        btn_Setup.addActionListener(this);
        btn_Save.addActionListener(this);
        btn_Cancel.addActionListener(this);
        JPanel toolBar = new JPanel();
        toolBar.add(btn_Setup);
        toolBar.add(btn_Save);
        toolBar.add(btn_Cancel);

        browser.loadURL(SetPosition.class.getResource("map_addnew.html") + "");
        add(browserView, BorderLayout.CENTER);
        add(toolBar, BorderLayout.SOUTH);
        setSize(650, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == btn_Setup) {
            replacePlace();
        } else if (e.getSource() == btn_Save) {
            if ("".equals(position.getLatitude())) {
                JOptionPane.showMessageDialog(this, "Chooise position!");
            } else if (parent != null) {
                parent.setPosition(position.getLatitude(), position.getLongitude());
                this.dispose();
            } else {
                parent2.setPosition(position.getLatitude(), position.getLongitude());
                this.dispose();
            }
        } else if (e.getSource() == btn_Cancel) {
            this.dispose();
        }
    }

    @Override
    public void onFinishLoadingFrame(FinishLoadingEvent arg0) {
        // TODO Auto-generated method stub
        Browser browser = arg0.getBrowser();
        JSValue value = browser.executeJavaScriptAndReturnValue("window");
        value.asObject().setProperty("Position", position);
        setPlace();
    }

    @Override
    public void onStartLoadingFrame(StartLoadingEvent sle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onProvisionalLoadingFrame(ProvisionalLoadingEvent ple) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onFailLoadingFrame(FailLoadingEvent fle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDocumentLoadedInFrame(FrameLoadEvent fle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDocumentLoadedInMainFrame(LoadEvent le) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
