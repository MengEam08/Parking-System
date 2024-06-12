package model.dao;

import entity.Parking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ParkingDaoImpl implements ParkingDao {
    @Override
    public int addParking(Parking parking) {
        String sql = """
                INSERT INTO parking_record (record_id, entry_time, exit_time, student_id, vehicle_id)
                VALUES (?, ?, ?, ?, ?);
                """;

        try (
                Connection connection = DriverManager.getConnection(
                        "jdbc:mariadb://localhost:3309/parking_system_db",
                        "root",
                        "20030830"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, parking.getRecordId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(parking.getEntryTime()));
            // If exitTime is null, set the corresponding value to null in the prepared statement
            if (parking.getExitTime() == null) {
                preparedStatement.setNull(3, java.sql.Types.TIMESTAMP);
            } else {
                preparedStatement.setTimestamp(3, Timestamp.valueOf(parking.getExitTime()));
            }
            preparedStatement.setInt(4, parking.getStudent().getId());
            preparedStatement.setInt(5, parking.getVehicle().getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully added parking record");
            }

        } catch (SQLException e) {
            System.out.printf(e.getMessage());
        }
        return 0;
    }
}
