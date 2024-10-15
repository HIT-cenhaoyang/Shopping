package sg.nus.edu.shopping.controller;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import sg.nus.edu.shopping.interfacemethods.*;
import sg.nus.edu.shopping.model.*;

@Controller
public class PurchaseRecordController {
    @Autowired
    private CustomerInterface cService;
    @Autowired
    public void setCustomerService(CustomerInterface cServiceImpl){
        this.cService = cServiceImpl;
    }
    @Autowired
    private OrderDetailInterface oService;
    @Autowired
    public void setOrderService(OrderDetailInterface oServiceImpl) {
        this.oService = oServiceImpl;
    }
    @Autowired
    private PurchaseRecordInterface purchaseRecordService;
    @Autowired
    public void setPurchaseRecordService(PurchaseRecordInterface purchaseRecordServiceImpl) {
        this.purchaseRecordService = purchaseRecordServiceImpl;
    }
 @Autowired
 private ProductInterface pService;
    @Autowired
    public void setProductService(ProductInterface pServiceImpl) {
        this.pService = pServiceImpl;
    }
    @Autowired
    private ReviewInterface rService;
    @Autowired
    public void setReviewService(ReviewInterface rServiceImpl) {
        this.rService = rServiceImpl;
    }
    
	//Author:zhao yiran
	@GetMapping("/7haven/purchase/history")
	public String getCustomer(Model model, HttpSession obj, @RequestParam(defaultValue = "0") int page) {
		String customerId = (String) obj.getAttribute("customerId");
		Customer cust = cService.findById(customerId);
		List<PurchaseRecord> orderList = cust.getPurchaseRecords();
		
		model.addAttribute("orders", orderList);

        //pageable
        Pageable pageable = PageRequest.of(page, 5);
        Page<PurchaseRecord> purchaseRecordsPage = purchaseRecordService.getAllPurchaseRecords(pageable);

        model.addAttribute("purchaseRecordsPage", purchaseRecordsPage);
		
		return "PurchaseRecord";
	}

    @PostMapping("/7haven/product/review")
    public String review(@Param("productId")int productId,Model model){
        Product product = pService.findByProductId(productId).orElse(null);
        model.addAttribute("product",product);
        return "review";
    }
    
    @PostMapping("/7haven/product/review/submit")
    public String reviewSubmit(@RequestParam("starValue") int starValue,@RequestParam("reviewContent")String content,@Param("productId")int productId, HttpSession sessionObj, Model model){
        Product product = pService.findByProductId(productId).orElse(null);
        model.addAttribute("product",product);
        String customerName = (String) sessionObj.getAttribute("username");
        Review review=new Review();
        review.setCustomer(cService.findByUserName(customerName).orElse(null));
        review.setProduct(pService.findByProductId(productId).orElse(null));
        review.setComment(content);
        review.setStar(starValue);
        rService.saveReview(review);
        return "redirect:/7haven/purchase/history";


    }

}
