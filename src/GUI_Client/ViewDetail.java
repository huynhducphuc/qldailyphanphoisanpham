package GUI_Client;

import bean.Supermarket;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewDetail {

    final Browser browser = new Browser();
    BrowserView browserView = new BrowserView(browser);
    private Supermarket supermarket = null;
    private String district_name = "";
    
    public ViewDetail(Supermarket supermarket, String district_name) {
        this.supermarket = supermarket;
        this.district_name = district_name;
        
        JPanel pn_district = new JPanel();
        JPanel pn_name = new JPanel();
        JPanel pn_address = new JPanel();
        JPanel pn_phone = new JPanel();
        JPanel pn_email = new JPanel();
        JPanel pn_website = new JPanel();
        JPanel pn_btnCancel = new JPanel();

        JLabel lb_district = new JLabel("District: ");
        JTextField txt_district = new JTextField(15);
        pn_district.add(lb_district);
        pn_district.add(txt_district);

        JLabel lb_name = new JLabel("Supermarket: ");
        JTextField txt_name = new JTextField(15);
        pn_name.add(lb_name);
        pn_name.add(txt_name);

        JLabel lb_address = new JLabel("Address: ");
        JTextField txt_address = new JTextField(15);
        pn_address.add(lb_address);
        pn_address.add(txt_address);

        JLabel lb_phone = new JLabel("Phone: ");
        JTextField txt_phone = new JTextField(15);
        pn_phone.add(lb_phone);
        pn_phone.add(txt_phone);

        JLabel lb_email = new JLabel("Email: ");
        JTextField txt_email = new JTextField(15);
        pn_email.add(lb_email);
        pn_email.add(txt_email);

        JLabel lb_web = new JLabel("Website: ");
        JTextField txt_web = new JTextField(15);
        pn_website.add(lb_web);
        pn_website.add(txt_web);

        JButton btn_Cancel = new JButton("Cancel");
        pn_btnCancel.add(btn_Cancel);

        JPanel toolBar = new JPanel(new GridLayout(7, 1, 10, 20));
        toolBar.add(pn_district);
        toolBar.add(pn_name);
        toolBar.add(pn_address);
        toolBar.add(pn_phone);
        toolBar.add(pn_email);
        toolBar.add(pn_website);
        toolBar.add(pn_btnCancel);

        
        JFrame frame = new JFrame("Trang chi tiết");

        frame.add(toolBar, BorderLayout.EAST);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        browser.loadURL(ViewDetail.class.getResource("map.html")+"");

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent arg0) {
                // TODO Auto-generated method stub
                setPlace(supermarket);
            }
        });

        btn_Cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        txt_district.setText(district_name);
        txt_name.setText(supermarket.getName());
        txt_address.setText(supermarket.getAddress());
        txt_phone.setText(supermarket.getPhone());
        txt_email.setText(supermarket.getEmail());
        txt_web.setText(supermarket.getWebsite());
    }

    void setPlace(Supermarket s) {
        String javaScript = "var myLatlng = new google.maps.LatLng(" + s.getLagtitude() + "," + s.getLongitude() + ");\n"
                + "var marker = new google.maps.Marker({\n" + "    position: myLatlng,\n" + "    map: map,\n"
                + "    title: '" + s.getName() + "'\n" + "});";
        browser.executeJavaScript(javaScript);
    }

}
