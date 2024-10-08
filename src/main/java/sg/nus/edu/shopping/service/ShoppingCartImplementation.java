package sg.nus.edu.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.nus.edu.shopping.interfacemethods.ShoppingCartInterface;
import sg.nus.edu.shopping.model.Customer;
import sg.nus.edu.shopping.model.Product;
import sg.nus.edu.shopping.model.ShoppingCart;
import sg.nus.edu.shopping.repository.CustomerRepository;
import sg.nus.edu.shopping.repository.ProductRepository;
import sg.nus.edu.shopping.repository.ShoppingCartRepository;

import java.util.List;

@Service
@Transactional
public class ShoppingCartImplementation implements ShoppingCartInterface {
    @Autowired
    private ShoppingCartRepository shoppingCartRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private ProductRepository productRepo;

    public List<ShoppingCart> getCartByCustomerId(String customerId) {
        return shoppingCartRepo.findByCustomerId(customerId);
    }

    public List<ShoppingCart> getCartByCustomerUsername(String username) {
        return shoppingCartRepo.findByCustomerUserName(username);
    }
    public ShoppingCart updateQuantity(int cartId, int quantity) {
        ShoppingCart existingCart = shoppingCartRepo.getByCartId(cartId);
        if (existingCart == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        existingCart.setProductQty(quantity);
        return shoppingCartRepo.save(existingCart);
    }

    public void removeProduct(int cartId) {
        ShoppingCart existingCart = shoppingCartRepo.getByCartId(cartId);
        if (existingCart == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        Customer customer = shoppingCartRepo.findCustomerByCartId(cartId);
        customer.removeProductFromCart(existingCart);

        customerRepo.save(customer);
        shoppingCartRepo.delete(existingCart);
    }

    public ShoppingCart addProduct(String customerId, int productId, int quantity) {
        Customer customer = customerRepo.findCustomerById(customerId);
        Product product = productRepo.findByProductId(productId);
        ShoppingCart newCart = new ShoppingCart(product, customer, quantity);
        customer.addProductToCart(newCart);

        customerRepo.save(customer);
        return shoppingCartRepo.save(newCart);
    }
}
