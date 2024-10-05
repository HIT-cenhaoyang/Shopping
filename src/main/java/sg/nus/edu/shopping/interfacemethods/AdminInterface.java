package sg.nus.edu.shopping.interfacemethods;

import sg.nus.edu.shopping.model.Admin;

import java.util.ArrayList;

public interface AdminInterface {

    public ArrayList<Admin> findAdminByuserName(String userName);
}
