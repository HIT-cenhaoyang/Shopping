package sg.nus.edu.shopping.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.web.bind.annotation.RequestMapping;
import sg.nus.edu.shopping.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
	@Query("Select u from Admin as u where u.userName =:userName")
	public ArrayList<Admin> searchUserByUserName(@Param("userName") String userName);
}
