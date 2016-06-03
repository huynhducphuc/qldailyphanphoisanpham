package GUI_Client;

import GUI.*;
import Util.CheckTable;
import bean.City;
import bean.District;
import bean.Supermarket;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class ViewAll {

    private String select = "--Select--";
    private final int checkBox = 2;
    private ArrayList<Supermarket> listSupermarkets = null;
    private ArrayList<City> listCitys = null;
    private ArrayList<District> listDistricts = null; 
    private JTable jtable_resutl;
    private JTextField txt_search;
    private JComboBox<String> cbx_City, cbx_District;
    final Browser browser = new Browser();
    BrowserView browserView = new BrowserView(browser);
    
    private boolean isDisconnected = true;
    private Socket socket = null;
    private ObjectInputStream fromServer;
    private DataOutputStream toServer;
    private Config config = new Config();
    
    private void connectToServer() {
        // set connection flag
        isDisconnected = false;
        
        // tao thread ket noi
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(config.getHost(), config.getPort());
                    // fromServer = new DataInputStream(socket.getInputStream());
                    fromServer = new ObjectInputStream(socket.getInputStream());
                    toServer = new DataOutputStream(socket.getOutputStream());
                    
                    // thong bao ket noi thanh cong
                    JOptionPane.showMessageDialog(null, "Da ket noi den server !", "Thong bao", JOptionPane.INFORMATION_MESSAGE);
                    
                    while (true) {
                        if (isDisconnected) {
                            break;
                        }

                        ArrayList<Supermarket> data = null;
                        ArrayList<City> cities = null;
                        ArrayList<District> districts = null;
                        try {
                            cities = (ArrayList<City>) fromServer.readObject();
                            districts = (ArrayList<District>) fromServer.readObject();
                            data = (ArrayList<Supermarket>) fromServer.readObject();
                            System.out.println("View all 77: " + data.size());
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ViewAll.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        // cap nhat du lieu cho list view
                        if (data == null) {
                            data = new ArrayList<Supermarket>();
                        }
                        listCitys = cities;
                        listDistricts = districts;
                        updateCityData();
                        listSupermarkets = data;
                        showDataToTableAndCheckbox(listSupermarkets, jtable_resutl);
                        CheckTable.UnCheckboxTable(jtable_resutl, checkBox);
                        deleteAllPlace();
                        setPlace(data);
                    }

                } catch (IOException e) {
                    isDisconnected = true;
                    JOptionPane.showMessageDialog(null, "Kết nối tới Server thất bại !\nVui lòng thử lại.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    e.printStackTrace();
                } finally {
                    try {
                        toServer.close();
                        fromServer.close();
                        socket.close();
                    } catch (IOException ex) {
                        // Logger.getLogger(ViewAll.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        toServer = null;
                        fromServer = null;
                        socket = null;
                    }
                }
            }
        });

        thread.start();
    }

    public ViewAll() {
        getData(new ArrayList<City>(), new ArrayList<District>(), new ArrayList<>());
        
        JPanel pn_search = new JPanel();
        txt_search = new JTextField(15);
        JButton btn_search = new JButton("Search");
        JButton btn_reset = new JButton("Reset");
        pn_search.add(txt_search);
        pn_search.add(btn_search);
        pn_search.add(btn_reset);

        JPanel pn_city = new JPanel();
        JLabel lb_city = new JLabel("City :");
        cbx_City = new JComboBox();
        cbx_City.addItem(select);
        pn_city.add(lb_city);
        pn_city.add(cbx_City);

        JPanel pn_district = new JPanel();
        JLabel lb_district = new JLabel("District :");
        cbx_District = new JComboBox();
        cbx_District.addItem(select);
        pn_district.add(lb_district);
        pn_district.add(cbx_District);
        //table
        JScrollPane jScrollPane1 = new JScrollPane();
        jtable_resutl = new JTable();
        jtable_resutl.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String[]{
                    "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jScrollPane1.setViewportView(jtable_resutl);

        cbx_City.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    if (select.equals(cbx_City.getSelectedItem().toString())) {
                        cbx_District.removeAllItems();
                        cbx_District.addItem(select);
                    } else {
                        int cityId = 0;
                        String cityName = cbx_City.getSelectedItem().toString();
                        for (City city : listCitys) {
                            System.out.println("District"+city.toString());
                            if (cityName.equals(city.getCityName())) {
                                cityId = city.getCityId();
                            }
                        }
                        cbx_District.removeAllItems();
                        cbx_District.addItem(select);
                        ArrayList<District> listDistrict_City = new ArrayList<District>();
                        for(District d : listDistricts){
                            if(d.getCityId()==cityId)
                                listDistrict_City.add(d);
                        }
                        for (District district : listDistrict_City) {
                            System.out.println("District"+district.toString());
                            cbx_District.addItem(district.getDistrictName());
                        }
                        System.out.println("ciTY" +cityName);
                        System.out.println("ciTYID" +cityId);
                    }
                    System.out.println("selected dfgh: " + cbx_City.getSelectedItem().toString());
                }
            }
        });

        JPanel pn_btn_view = new JPanel();
        JButton btn_view = new JButton("View");
        pn_btn_view.add(btn_view);
        
        JPanel link = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btn_config = new JButton("Config");
        JButton btn_login = new JButton("Login");
        JButton btnConnect = new JButton("Connect");
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isDisconnected) {
                    connectToServer();
                } else {
                    JOptionPane.showMessageDialog(null, "Da ket noi den server !", "Thong bao", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        JButton btnDisconnect = new JButton("Disconnect");
        btnDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isDisconnected) {
                    isDisconnected = !isDisconnected;
                    JOptionPane.showMessageDialog(null, "Da ngat ket noi den server !", "Thong bao", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Da ngat ket noi den server !", "Thong bao", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        link.add(btnConnect);
        link.add(btnDisconnect);
        link.add(btn_config);
        link.add(btn_login);

        JPanel toolBar = new JPanel(new GridLayout(5, 1, 10, 20));
        //JPanel toolBar = new JPanel(new FlowLayout());
        toolBar.add(pn_search);
        
        JPanel pn = new JPanel();
        pn.add(pn_city);
        pn.add(pn_district);
        
        toolBar.add(pn);
        
        toolBar.add(jScrollPane1);
        toolBar.add(pn_btn_view);
        toolBar.add(link);

        initDataTable();

        JFrame frame = new JFrame("Trang chủ");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(toolBar, BorderLayout.EAST);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        browser.loadURL(ViewAll.class.getResource("map.html")+"");

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent arg0) {
                // TODO Auto-generated method stub
                setPlace(listSupermarkets);
            }
        });

        btn_search.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String key = txt_search.getText();
                String cityName = "", districtName = "";
                if (cbx_City.getSelectedItem() != null) {
                    cityName = cbx_City.getSelectedItem().toString();
                }
                if (cbx_District.getSelectedItem() != null) {
                    districtName = cbx_District.getSelectedItem().toString();
                }
                int cityId = 0, districtId = 0;
                for (City c : listCitys) {
                    if (cityName.equals(c.getCityName())) {
                        cityId = c.getCityId();
                    }
                }
                for (District district : listDistricts) {
                    if (districtName.equals(district.getDistrictName())) {
                        districtId = district.getDistrictId();
                    }
                }
                
                // goi du lieu len server
                try {
                    String clientMessage = key + ";" + cityId + ";" + districtId;
                    toServer.writeUTF(clientMessage);
                } catch (Exception ex) {
                    // Logger.getLogger(ViewAll.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Khong the ket noi den server!\nVui long thu lai.", "Thong bao", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
        });

        btn_reset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cbx_City.setSelectedIndex(0);
                cbx_District.setSelectedIndex(0);
                txt_search.setText("");
                initDataTable();
                deleteAllPlace();
                setPlace(listSupermarkets);
            }
        });

        btn_view.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Vector v = null;
                v = CheckTable.getValueTable(jtable_resutl, checkBox);
                if (v.size() != 1) {
                    JOptionPane.showMessageDialog(frame, "You may only choose 1.");
                    return;
                } else {
                    String id = "";
                    id = String.valueOf(jtable_resutl.getValueAt(Integer.parseInt(v.get(0).toString()), 0));
                    Supermarket supermarket = null;
                    String district_name = "";
                    for(Supermarket s : listSupermarkets){
                        if(s.getId()==Integer.parseInt(id))
                            supermarket = s;
                    }
                    for(District d : listDistricts){
                        if(d.getDistrictId()==supermarket.getDistrictId())
                            district_name = d.getDistrictName();
                    }
                    
                    new ViewDetail(supermarket,district_name);
                }
            }
        });
        btn_login.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
            }
        });
        btn_config.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ConfigForm();
            }
        });
    }

    private void showDataToTableAndCheckbox(ArrayList<Supermarket> list, JTable tbl) {
        Vector vCol = new Vector();
        Vector vRow = new Vector();
        vCol.add("id");
        vCol.add("Supermarket Name");
        vCol.add("");
        for (int i = 0; i < list.size(); i++) {
            Vector temp = new Vector();
            temp.add(list.get(i).getId());
            temp.add(list.get(i).getName());
            vRow.add(temp);
        }

        tbl.setModel(new DefaultTableModel(vRow, vCol));
        TableColumn cbacc = tbl.getColumnModel().getColumn(checkBox);
        cbacc.setCellEditor(tbl.getDefaultEditor(Boolean.class));
        cbacc.setCellRenderer(tbl.getDefaultRenderer(Boolean.class));
        cbacc.setMaxWidth(30);
    }

    public void initDataTable() {
        try {
            for (Supermarket s : listSupermarkets) {
                s.toString();
            }
            showDataToTableAndCheckbox(listSupermarkets, jtable_resutl);
            CheckTable.UnCheckboxTable(jtable_resutl, checkBox);
        } catch (Exception ex) {
            Logger.getLogger(Manage_City.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setPlace(ArrayList<Supermarket> listSupm) {
        String javaScript = "var map;\n"
                + "var markers = [];";
        browser.executeJavaScript(javaScript);
        for (Supermarket s : listSupm) {
            javaScript = "var myLatlng = new google.maps.LatLng(" + s.getLagtitude() + "," + s.getLongitude() + ");\n"
                    + "var marker = new google.maps.Marker({\n" + "    position: myLatlng,\n" + "    map: map,\n"
                    + "    title: '" + s.getName() + "'\n" + "});\n"
                    + "markers.push(marker);";
            browser.executeJavaScript(javaScript);
        }
    }

    private void deleteAllPlace() {
        String javaScript = "for (var i = 0; i < markers.length; i++) {\n"
                + "markers[i].setMap(null);\n"
                + "}"
                + "markers = [];";
        browser.executeJavaScript(javaScript);
    }

    private void getData(ArrayList<City> cities, ArrayList<District> districts, ArrayList<Supermarket> supermarkets){
        listCitys = cities;
        listDistricts = districts;
        listSupermarkets = supermarkets;
    }
    
    private void updateCityData() {
        cbx_City.removeAllItems();
        cbx_City.addItem(select);
        for (City c : listCitys) {
            cbx_City.addItem(c.getCityName());
        }
    }
    
    public static void main(String[] args) {
        new ViewAll();
    }

}
