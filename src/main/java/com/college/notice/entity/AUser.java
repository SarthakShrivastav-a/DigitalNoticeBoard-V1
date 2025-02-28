package com.college.notice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Document(collection = "AUsers")
public class AUser {

    @Id
    private String erp;
    private String email;
    private String hashedPassword;
    private String role; //'USER', 'FACULTY', 'CLUBLEAD', 'ADMIN' create enum in front end


    public AUser(String erp, String email,String hashedPassword){
        this.erp = erp;
        this.email = email;
        this.erp = erp;
        this.hashedPassword = hashedPassword;
    }

    public String getErp() {
        return erp;
    }

    public void setErp(String erp) {
        this.erp = erp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public GrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(role); //for role
    }
}
