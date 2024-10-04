package sg.nus.edu.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.edu.shopping.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {

}
