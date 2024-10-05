package sg.nus.edu.shopping.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.nus.edu.shopping.interfacemethods.AdminInterface;
import sg.nus.edu.shopping.model.Admin;
import sg.nus.edu.shopping.repository.AdminRepository;

import java.util.ArrayList;

@Service
@Transactional
public class AdminImplementation implements AdminInterface {

    @Autowired
    AdminRepository adminRepository;

    @Override
    @Transactional
    public ArrayList<Admin> findAdminByuserName(String userName) {
        return null;
    }
}
