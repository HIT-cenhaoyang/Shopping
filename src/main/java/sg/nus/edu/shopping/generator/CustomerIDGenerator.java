package sg.nus.edu.shopping.generator;

import jakarta.annotation.PostConstruct;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sg.nus.edu.shopping.service.CustomerImplementation;

import java.io.Serializable;

@Component
//Author: Cen Haoyang
public class CustomerIDGenerator implements IdentifierGenerator {
    private static int counter = 1;

    @Autowired
    CustomerImplementation customerImp;

    @PostConstruct  // This method is called after the bean is instantiated
    public void init() {
        String maxId = customerImp.findMaxCustomerId();
        if (maxId != null) {
            counter = Integer.parseInt(maxId.substring(1)) + 1;  // Extract the number part and increment it
        }
    }

    @Override  // This method is called to generate a new identifier
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        int id = counter++;
        return String.format("C%03d", id);
    }
}
