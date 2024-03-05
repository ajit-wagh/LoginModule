package com.loginModule.User.Service;

import java.util.Optional; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;

import com.loginModule.User.dto.User;
import com.loginModule.User.repository.UserRepository;

// @Service
// public class UserService {
// 	private List<User> users = new ArrayList<>();
// 	public UserService() {
// 		users.add(new User(UUID.randomUUID().toString(),"Ajit","waghas0404@gmail.com","Waghas@09"));
// 		users.add(new User(UUID.randomUUID().toString(),"Mayur","waghms0303@gmail.com","Waghas@09"));
// 		users.add(new User(UUID.randomUUID().toString(),"Swapnil","waghss0202@gmail.com","Waghas@09"));
// 		users.add(new User(UUID.randomUUID().toString(),"Gaurav","waghgs0101@gmail.com","Waghas@09"));
// 		System.out.println(users);
// 	}
// 	public List<User> getUsers(){
// 		return this.users;		
// 	}	
// }



@Service
public class UserInfoService implements UserDetailsService { 

	@Autowired
	private UserRepository repository; 

	@Autowired
	private PasswordEncoder encoder; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 

		Optional<User> userDetail = repository.findByName(username); 

		// Converting userDetail to UserDetails 
		return userDetail.map(UserInfoDetails::new) 
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
	} 

	public String addUser(User userInfo) { 
		userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
		repository.save(userInfo); 
		return "User Added Successfully"; 
	} 


} 
