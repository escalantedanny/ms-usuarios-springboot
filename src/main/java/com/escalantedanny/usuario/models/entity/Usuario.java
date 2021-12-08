package com.escalantedanny.usuario.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "usuarios")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = -1773007946664981382L;
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    
    @OneToMany(cascade = {CascadeType.ALL})
    public List<Phone> phones;
    private String created;
    private String modified;    
    @Column(name = "last_login")
    private String  lastLogin;
    @Column(name = "is_active")
    private Boolean isActive;
    private String token;
    
	public Usuario() {
		super();
		DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm:ss");
		   LocalDateTime now = LocalDateTime.now();
		   created = dtfDate.format(now);
		   modified = dtfDate.format(now);
		   lastLogin = dtfTime.format(now);
	}
	
	public Usuario(UUID id, String name, String email, String password, List<Phone> phones, String created,
			String modified, String lastLogin, Boolean isActive, String token) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phones = phones;
		this.created = created;
		this.modified = modified;
		this.lastLogin = lastLogin;
		this.isActive = isActive;
		this.token = token;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Phone> getPhones() {
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public UUID getId() {
		return id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	
    
}
