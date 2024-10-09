package sg.nus.edu.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sg.nus.edu.shopping.repository.OrderDetailRepository;

@Controller
public class OrderDetailController {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @GetMapping("/order_detail")
    public String getOrder(Model model) {
        model.addAttribute("order", orderDetailRepository.findAll());
        return"PurchaseRecord";
    }
}
