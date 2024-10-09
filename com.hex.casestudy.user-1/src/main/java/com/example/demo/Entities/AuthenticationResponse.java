package com.example.demo.Entities;

import com.example.demo.Enum.Role;

public class AuthenticationResponse {
		private String jwt;
	    private Role role;
	    private Long userId;
		public String getJwt() {
			return jwt;
		}
		public void setJwt(String jwt) {
			this.jwt = jwt;
		}
		public Role getRole() {
			return role;
		}
		public void setRole(Role role) {
			this.role = role;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		@Override
		public String toString() {
			return "AuthenticationResponse [jwt=" + jwt + ", userRole=" + role + ", userId=" + userId + "]";
		}
	}
