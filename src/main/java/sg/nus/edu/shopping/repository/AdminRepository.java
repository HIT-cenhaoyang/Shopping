package sg.nus.edu.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.nus.edu.shopping.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
	Optional<Admin> findAdminByUserName(String userName);

	@Query("SELECT MAX(a.id) FROM Admin a")
	String findMaxAdminId();
}
