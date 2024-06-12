package model.dao;

import entity.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDaoImpl implements StudentDao{
    @Override
    public int addStudent(Student student) {
        String sql = """
                INSERT INTO student (student_fullname, student_gender)
                VALUE(?,?);
                """;

        try(
                Connection connection = DriverManager.getConnection(
                        "jdbc:mariadb://localhost:3309/parking_system_db",
                        "root",
                        "20030830"
                );
                PreparedStatement preparedStatement = connection.prepareStatement(sql)

                ){
            preparedStatement.setString(1,student.getName());
            preparedStatement.setString(2,student.getGender());
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Successfully added student");
            }

        }catch (SQLException e){
            System.out.printf(e.getMessage());
        }
        return 0;
    }
}
