package HospitalManagmentSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection conn;
    private Scanner sc;
    public Patient(Connection conn,Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }
    public void addPatient(){
        sc.nextLine();
        System.out.print("Enter Patient Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Patient Age: ");
        int age = sc.nextInt();
        System.out.print("Enter Patient Gender: ");
        String gender = sc.next();
        try{
            String query = "insert into patients(name,age,gender) values(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,name);
            ps.setInt(2,age);
            ps.setString(3,gender);
            int affectedRow=ps.executeUpdate();
            if(affectedRow>0){
                System.out.println("Successfully Added Patient");
            }else{
                System.out.println("Failed to add Patient");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void viewPatients(){
        String query = "select * from patients";
        try{
           PreparedStatement ps = conn.prepareStatement(query);
           ResultSet rs = ps.executeQuery();
           System.out.println("Patient Details");
           System.out.println("+------------+--------------------------+------+--------+");
           System.out.println("| Patient ID | Name                     | Age  | Gender |");
           System.out.println("+------------+--------------------------+------+--------+");
           while(rs.next()){
               int id=rs.getInt(1);
               String name=rs.getString(2);
               int age=rs.getInt(3);
               String gender=rs.getString(4);
               System.out.printf("| %-10s | %-24s | %-4s | %-6s |\n",id,name,age,gender);
               System.out.println("+------------+--------------------------+------+--------+");
           }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean checkPatient(int id) {
        String query = "select * from patients where ID=?";
        try{
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) return true;
            else return false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;

    }
}
