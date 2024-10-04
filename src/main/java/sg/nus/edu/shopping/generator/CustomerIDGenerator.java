package sg.nus.edu.shopping.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import java.io.Serializable;


public class CustomerIDGenerator implements IdentifierGenerator {
    private static int counter = 1;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        int id = counter++;
        return String.format("C%03d", id);
    }
}
