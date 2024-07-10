package HospitalManagmentSystem;

import javax.print.Doc;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagmentSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String user="root";
    private static final String password="1234rk";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn= DriverManager.getConnection(url,user,password);
            Scanner sc=new Scanner(System.in);
            Patient patient=new Patient(conn,sc);
            Doctor doctor=new Doctor(conn);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice=sc.nextInt();
                switch(choice){
                    case 1: patient.addPatient();
                        System.out.println();
                    break;
                    case 2:patient.viewPatients();
                    System.out.println();
                    break;
                    case 3:doctor.viewDoctor();
                    System.out.println();
                    break;
                    case 4:bookAppointment(patient,doctor,conn,sc);
                        System.out.println();
                    break;
                    case 5:return;
                    default:
                        System.out.println("Invalid choice");


                }

            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private static void bookAppointment(Patient patient,Doctor doctor,Connection conn,Scanner sc){
        System.out.print("Enter patient ID: ");
        int patientID=sc.nextInt();
        System.out.print("Enter doctor ID: ");
        int doctorID=sc.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        sc.nextLine();
        String appointmentDate=sc.next();
        if(patient.checkPatient(patientID) && doctor.checkDoctor(doctorID)){
            if(checkDoctorAvailable(conn,doctorID,appointmentDate)){
                String appointmentQuery="INSERT INTO appointments(patient_id,doctor_id,date) VALUES(?,?,?)";
                try{
                    PreparedStatement ps=conn.prepareStatement(appointmentQuery);
                    ps.setInt(1,patientID);
                    ps.setInt(2,doctorID);
                    ps.setString(3,appointmentDate);
                    int result=ps.executeUpdate();
                    if(result>0){
                        System.out.println("Appointment Booked Successfully");
                    }else{
                        System.out.println("Appointment Booking Failed");
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor not available on this date");
            }
        }else{
            System.out.println("Patient or Doctor not found");
        }

    }

    private static boolean checkDoctorAvailable(Connection conn,int doctorID, String appointmentDate) {
        String query="SELECT COUNT(*) FROM appointments WHERE id=? AND date=?";
        try{
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1,doctorID);
            ps.setString(2,appointmentDate);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                int count=rs.getInt(1);
                if(count==0) return true;
                else return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
