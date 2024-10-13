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
import java.util.Optional;

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
        shoppingCartRepo.delete(existingCart);
    }

    public ShoppingCart addProduct(String customerId, int productId, int quantity) {
        Optional<Customer> optCustomer = customerRepo.findCustomerById(customerId);
        Customer customer = optCustomer.orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Optional<Product> optProduct = productRepo.findByProductId(productId);
        Product product = optProduct.orElseThrow(() -> new IllegalArgumentException("Product not found!"));
        ShoppingCart newCart = new ShoppingCart(product, customer, quantity);
        customer.addProductToCart(newCart);

        customerRepo.save(customer);
        return shoppingCartRepo.save(newCart);
    }

    public void clearCartByCustomer(Customer customer) {
        shoppingCartRepo.deleteByCustomer(customer);
    }

	@Override
	public ShoppingCart getCartByCustomerIdProductId(String customerId, int productId) {
		// TODO Auto-generated method stub
		List<ShoppingCart> list = shoppingCartRepo.getCartByCustomerIdProductId(customerId, productId);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
