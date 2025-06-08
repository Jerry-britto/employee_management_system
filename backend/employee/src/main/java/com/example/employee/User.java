package com.example.employee;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Document(collation = "users")
public class User {

	@Id
	private String _id;
	
    @NotEmpty(message = "User name should not empty")
    
	private String username;
    
    @NotEmpty(message = "Password should not empty")
    private String password;
    
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	} 
    
    
}
