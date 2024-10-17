package sg.nus.edu.shopping;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

@SpringBootTest
class ShoppingApplicationTests {

    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    
	@Test
	void contextLoads() {
        Connection conn;

        try {
            conn = DriverManager.getConnection(url, username, password);

            ClassPathResource resource = new ClassPathResource("7Haven_data.xlsx");
            FileInputStream fis = new FileInputStream(resource.getFile());
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet("SQL"); 

            // Create the SQL INSERT statement
            PreparedStatement preparedStatement;
            
            
            // Iterate through the rows in the Excel sheet
            for (Row row : sheet) {
                if (row.getCell(0) == null || row.getCell(0).equals("")) {
                    continue; 
                }

                String sql = row.getCell(0).getStringCellValue();
                preparedStatement = conn.prepareStatement(sql);
                // Execute the INSERT statement
                preparedStatement.executeUpdate();
            }

            // Close resources
            workbook.close();
            fis.close();
            conn.close();

            System.out.println("Data inserted successfully!");


        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
