package sg.nus.edu.shopping.generator;

import jakarta.annotation.PostConstruct;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sg.nus.edu.shopping.service.AdminImplementation;

import java.io.Serializable;

@Component
//Author: Cen Haoyang
public class AdminIDGenerator implements IdentifierGenerator {
    private static int counter = 1;

    @Autowired
    AdminImplementation adminImp;

    @PostConstruct  // This method is called after the bean is instantiated
    public void init() {
        String maxId = adminImp.findMaxAdminId();
        if (maxId != null) {
            counter = Integer.parseInt(maxId.substring(1)) + 1;  // Extract the number part and increment it
        }
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        int id = counter++;
        return String.format("A%03d", id);
    }
}
