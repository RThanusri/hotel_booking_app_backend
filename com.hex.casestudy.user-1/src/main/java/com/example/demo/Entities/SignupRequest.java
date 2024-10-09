package com.example.demo.Entities;

import com.example.demo.Enum.Role;

public class SignupRequest {
		private String email;
	    private String name;
	    private String password;
	    
	    private Role role;
        public SignupRequest()
        {
        	
        }
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}

		@Override
		public String toString() {
			return "SignupRequest [email=" + email + ", name=" + name + ", password=" + password + ", role=" + role
					+ "]";
		}

		public SignupRequest(String email, String name, String password, Role role) {
			super();
			this.email = email;
			this.name = name;
			this.password = password;
			this.role = role;
		}
}