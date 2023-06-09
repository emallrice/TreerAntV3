/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Treer.dao;

import Treer.dto.Auction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Triệu
 */
public class AuctionDAO {

    public static ArrayList<Auction> getAllAuctions() throws Exception {
        ArrayList<Auction> result = new ArrayList<>();
        try {
            //b1 make connecton
            Connection cn = Treer.untils.DBUtils.makeConnection();
            //b2 viet sql and exec
            if (cn != null) {
                String sql = "select AuctionID,\n"
                        + "		CONVERT(varchar, Starttime, 105) + ' ' + CONVERT(varchar, Starttime, 108) as Starttime,\n"
                        + "			CONVERT(varchar, Endtime, 105) + ' ' + CONVERT(varchar, Endtime, 108) as Endtime,\n"
                        + "			PlantID,Starting_price,End_price,Status,bid from dbo.Auction";
                Statement st = cn.createStatement();
                ResultSet table = st.executeQuery(sql);
                //b3 xu li dap an
                if (table != null) {
                    while (table.next()) {
                        int AuctionID = table.getInt("AuctionID");
                        String Starttime = table.getString("Starttime");
                        String Endtime = table.getString("Endtime");
                        int PlantID = table.getInt("PlantID");
                        int Starting_price = table.getInt("Starting_price");
                        int End_price = table.getInt("End_price");
                        int status = table.getInt("Status");
                        int bid = table.getInt("bid");
                        Auction auc = new Auction(AuctionID, Starttime, Endtime, PlantID, Starting_price, bid, End_price, status);
                        result.add(auc);
                    }
                }
                // b4 close connection
                cn.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean setEndPrice(int price) {
        int rs = 0;
        try {
            Connection cn = Treer.untils.DBUtils.makeConnection();
            String sql = "update dbo.Auction set End_price=?";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, price);
            rs = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
//    update dbo.Auction set Status=0 where Endtime < CURRENT_TIMESTAMP;

    public static boolean EndAuctionbyEndTime() {
        try {
            Connection cn = Treer.untils.DBUtils.makeConnection();
            String sql = "update dbo.Auction set Status=0 where Endtime < CURRENT_TIMESTAMP";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean updateStatusAuction(int status, int aucID) {
        try {
            Connection cn = Treer.untils.DBUtils.makeConnection();
            String sql = "update dbo.Auction set Status=? where AuctionID=? and Endtime>CURRENT_TIMESTAMP";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, status);
            pst.setInt(2, aucID);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Auction getAuctionbyID(int aucID) throws Exception {
        Auction auc = new Auction();
        try {
            //b1 make connecton
            Connection cn = Treer.untils.DBUtils.makeConnection();
            //b2 viet sql and exec
            if (cn != null) {
                String sql = "select AuctionID,Starttime,Endtime,PlantID,Starting_price,End_price,Status,bid from dbo.Auction where AuctionID=?";
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, aucID);
                ResultSet table = pst.executeQuery();
                //b3 xu li dap an
                if (table != null) {
                    while (table.next()) {
                        int AuctionID = table.getInt("AuctionID");
                        String Starttime = table.getString("Starttime");
                        String Endtime = table.getString("Endtime");
                        int PlantID = table.getInt("PlantID");
                        int Starting_price = table.getInt("Starting_price");
                        int End_price = table.getInt("End_price");
                        int status = table.getInt("Status");
                        int bid = table.getInt("bid");
                        auc = new Auction(AuctionID, Starttime, Endtime, PlantID, Starting_price, bid, End_price, status);
                    }
                }
                // b4 close connection
                cn.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return auc;
    }
}
