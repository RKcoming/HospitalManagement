package HospitalManagmentSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection conn;
    public Doctor(Connection conn) {
        this.conn = conn;
    }
    public void viewDoctor(){
        String query = "select * from doctors";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("Doctor Details");
            System.out.println("+-----------+--------------------------+------------------+");
            System.out.println("| Doctor ID | Name                     | Specialization   |");
            System.out.println("+-----------+--------------------------+------------------+");
            while(rs.next()){
                int id=rs.getInt(1);
                String name=rs.getString(2);
                String specialization=rs.getString(3);
                System.out.printf("| %-9s | %-24s | %-16s |\n",id,name,specialization);
                System.out.println("+-----------+--------------------------+------------------+");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean checkDoctor(int id) {
        String query = "select * from doctors where ID=?";
        try{
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) return true;
            else return false;
        }catch (SQLException e){
             e.printStackTrace();
        }
        return  false;

    }
}
