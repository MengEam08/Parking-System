package main;

import entity.Parking;
import entity.Student;
import entity.Vehicle;
import model.dao.ParkingDao;
import model.dao.ParkingDaoImpl;
import model.dao.StudentDao;
import model.dao.StudentDaoImpl;
import model.dao.VehicleDao;
import model.dao.VehicleDaoImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ParkingSystem {
    private JTextField tfRecordId;
    private JTextField tfEntryTime;
    private JTextField tfStudentId;
    private JTextField tfVehicleId;
    private JComboBox<String> cbVehicleType;
    private JButton btnCommit;
    private JPanel mainPanel;
    private JTextField tfStuName;
    private JTextField tfStuGen;
    private JList<String> lst1;
    private JButton btnCancel;
    private JButton btnAddNew;
    private JButton btnClear;

    private DefaultListModel<String> listModel;

    public ParkingSystem() {
        listModel = new DefaultListModel<>();
        lst1.setModel(listModel);

        btnCommit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Validate and parse input fields
                    if (tfRecordId.getText().isEmpty() || tfEntryTime.getText().isEmpty() ||
                            tfStudentId.getText().isEmpty() || tfVehicleId.getText().isEmpty() ||
                            tfStuName.getText().isEmpty() || tfStuGen.getText().isEmpty() ||
                            cbVehicleType.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(null, "Please fill all fields.");
                        return;
                    }

                    int recordId = Integer.parseInt(tfRecordId.getText().trim());
                    LocalTime entryTime = parseTime(tfEntryTime.getText().trim());
                    LocalDateTime entryDateTime = LocalDateTime.of(LocalDate.now(), entryTime); // Combine current date with the provided time
                    int studentId = Integer.parseInt(tfStudentId.getText().trim());
                    int vehicleId = Integer.parseInt(tfVehicleId.getText().trim());
                    String vehicleType = cbVehicleType.getSelectedItem().toString().trim();

                    String studentName = tfStuName.getText().trim();
                    String studentGender = tfStuGen.getText().trim();

                    // Create Student and Vehicle objects
                    Student student = Student.builder().id(studentId).name(studentName).gender(studentGender).build();
                    Vehicle vehicle = Vehicle.builder().id(vehicleId).vehicleType(Vehicle.VehicleType.valueOf(vehicleType)).build();

                    // Create Parking object
                    Parking parking = Parking.builder()
                            .recordId(recordId)
                            .entryTime(entryDateTime)
                            .exitTime(null) // Assuming exit time is not provided initially
                            .student(student)
                            .vehicle(vehicle)
                            .build();

                    // Debugging output
                    System.out.println("Adding parking record:");
                    System.out.println("Record ID: " + parking.getRecordId());
                    System.out.println("Entry Time: " + parking.getEntryTime());
                    System.out.println("Student ID: " + parking.getStudent().getId());
                    System.out.println("Vehicle ID: " + parking.getVehicle().getId());

                    // Use DAO to insert data into the database
                    StudentDao studentDao = new StudentDaoImpl();
                    studentDao.addStudent(student);

                    VehicleDao vehicleDao = new VehicleDaoImpl();
                    vehicleDao.addVehicle(vehicle);

                    ParkingDao parkingDao = new ParkingDaoImpl();
                    parkingDao.addParking(parking);

                    // Add record to list model with student name
                    listModel.addElement("Record ID: " + recordId + ", Entry Time: " + entryDateTime + ", Student: " + studentName + ", Vehicle ID: " + vehicleId);

                    JOptionPane.showMessageDialog(null, "Successfully added parking record!");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Please enter valid numbers for IDs.");
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Please enter valid time in the format HH:mm.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        btnAddNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfRecordId.setText("");
                tfEntryTime.setText("");
                tfStudentId.setText("");
                tfVehicleId.setText("");
                tfStuName.setText("");
                tfStuGen.setText("");
                cbVehicleType.setSelectedItem(null);
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfRecordId.setText("");
                tfEntryTime.setText("");
                tfStudentId.setText("");
                tfVehicleId.setText("");
                tfStuName.setText("");
                tfStuGen.setText("");
                cbVehicleType.setSelectedItem(null);
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the application
                System.exit(0);
            }
        });

        lst1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // Add mouse click event logic for the list if needed
            }
        });
    }

    private LocalTime parseTime(String timeStr) {
        if (timeStr.isEmpty()) {
            throw new DateTimeParseException("Time string is empty", timeStr, 0);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(timeStr, formatter);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ParkingApp");
        frame.setContentPane(new ParkingSystem().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
