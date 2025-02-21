package com.college.notice.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Document(collection = "AuthUser")
@Data

public class AuthUser {

    @Id
    private String id;

    private String email;

    private String name;

    private String semester;

    private String erp;

    private String hashedPassword;

    private String role; //'USER', 'FACULTY', 'CLUBLEAD', 'ADMIN' create enum in front end

    public AuthUser(String id, String email, String name,String semester,String erp, String hashedPassword){
        this.id = id;
        this.email = email;
        this.name = name;
        this.semester = semester;
        this.erp = erp;
        this.hashedPassword = hashedPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getErp() {
        return erp;
    }

    public void setErp(String erp) {
        this.erp = erp;
    }
}
