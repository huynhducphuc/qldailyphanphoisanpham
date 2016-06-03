/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI_Server;

import bean.City;
import bean.District;
import bean.Supermarket;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import service.CityService;
import service.DistrictService;
import service.SupermarketService;

/**
 *
 * @author Noname
 */
public class HandleClient implements Runnable {

    private Socket socket;
    private String clientMessage = "";

    private DataInputStream inFromClient = null;
    private ObjectOutputStream outToClient = null;

    private SupermarketService supermarketService = null;
    private CityService cityService = null;
    private DistrictService districtService = null;
    private ArrayList<City> cities = null;
    private ArrayList<District> districts = null;
    private ArrayList<Supermarket> supermarkets = null;

    public HandleClient(Socket sk) {
        this.socket = sk;
    }

    @Override
    public void run() {
        try {
            inFromClient = new DataInputStream(socket.getInputStream());
            outToClient = new ObjectOutputStream(socket.getOutputStream());

            supermarketService = new SupermarketService();
            cityService = new CityService();
            districtService = new DistrictService();

            while (true) {
                // read message from client
                clientMessage = inFromClient.readUTF();
                System.out.println("Message from client: " + clientMessage);

                String[] tokens = clientMessage.split(";");
                supermarkets = supermarketService.getSupermarkets(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));

                // send result to client
                if (supermarkets == null) {
                    supermarkets = new ArrayList<Supermarket>();
                }
                cities = cityService.getCities();
                districts = districtService.getDistricts();
                
                outToClient.writeObject(cities);
                outToClient.writeObject(districts);
                outToClient.writeObject(supermarkets);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ket thuc chuong trinh !");
        } finally {
            try {
                inFromClient.close();
                outToClient.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                inFromClient = null;
                outToClient = null;
                socket = null;
            }
        }
    }

}
