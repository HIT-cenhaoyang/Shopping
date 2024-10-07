package sg.nus.edu.shopping.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.nus.edu.shopping.interfacemethods.AdminInterface;
import sg.nus.edu.shopping.model.Admin;
import sg.nus.edu.shopping.repository.AdminRepository;
import sg.nus.edu.shopping.repository.CategoryRepository;
import sg.nus.edu.shopping.repository.ProductImageRepository;
import sg.nus.edu.shopping.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminImplementation implements AdminInterface {

	@Autowired
	AdminRepository adminRepo;

	@Override
	public Admin searchUserByUserName(String userName) {
		// TODO Auto-generated method stub
		if (userName != null) {
			ArrayList<Admin> adminList = adminRepo.searchUserByUserName(userName);
			if (adminList.size() > 0) {
				return adminList.get(0);
			}
		}

		return null;
	}






}
