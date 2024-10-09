package com.example.demo.Entities;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.Enum.Role;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

	@Entity
	public class User implements UserDetails {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue
		private Long userId;
		
		@NotEmpty(message="User Name cannot be Null...Provide a User Name")
		@Pattern(regexp="^[a-zA-Z0-9_-]{3,20}$",message="Username must be between 3 and 20 characters long and can include letters, numbers, underscores, and hyphens.")
		private String userName;
		
		 @NotEmpty(message = "Password cannot be null...Provide a valid Password")
		
		private String password;
		@NotEmpty(message = "Email cannot be empty.")
	    @Email(message = "Invalid email format. Please enter a valid email address.")
		private String email;
		
		
		@Enumerated(EnumType.STRING)
		
		private Role role;
	
		
		
		
		@Override
		public String toString() {
			return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", email=" + email
					 + ", role=" + role + "]";
		}
		public User()
		{
			
		}
		public User(Long userId,
				@NotEmpty(message = "User Name cannot be Null...Provide a User Name") @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "Username must be between 3 and 20 characters long and can include letters, numbers, underscores, and hyphens.") String userName,
				@NotEmpty(message = "Password cannot be null...Provide a valid Password ") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,20}$", message = "Password must be between 8 and 20 characters long, and include at least one uppercase letter, one lowercase letter, and one digit.") String password,
				@NotEmpty(message = "Email cannot be empty.") @Email(message = "Invalid email format. Please enter a valid email address.") String email,
				@NotEmpty(message = "Phone Number cannot be empty.") @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits long and contain only numeric characters.") String phoneNumber,
				Role role) {
			super();
			this.userId = userId;
			this.userName = userName;
			this.password = password;
			this.email = email;
			this.role = role;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
		
		public Role getRole() {
			return role;
		}
		public void setRole(Role role) {
			this.role = role;
		}
		
		@Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return List.of(new SimpleGrantedAuthority(role.name()));
	    }

	    @Override
	    public String getUsername() {
	        return email;
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

	}


