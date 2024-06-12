package model.dao;

import entity.Vehicle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VehicleDaoImpl implements VehicleDao {
    @Override
    public int addVehicle(Vehicle vehicle) {
        String sql = """
                INSERT INTO vehicle (vehicle_id, vehicle_type)
                VALUES (?, ?);
                """;

        try (
                Connection connection = DriverManager.getConnection(
                        "jdbc:mariadb://localhost:3309/parking_system_db",
                        "root",
                        "20030830"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, vehicle.getId());
            preparedStatement.setString(2,vehicle.getVehicleType().name());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully added vehicle");
            }

        } catch (SQLException e) {
            System.out.printf(e.getMessage());
        }
        return 0;
    }
}
