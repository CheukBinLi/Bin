package Controller.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "ALL_USER_ROLE")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int userId;
	private int roleId;

	public int getId() {
		return id;
	}

	public UserRole setId(int id) {
		this.id = id;
		return this;
	}

	public int getUserId() {
		return userId;
	}

	public UserRole setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public int getRoleId() {
		return roleId;
	}

	public UserRole setRoleId(int roleId) {
		this.roleId = roleId;
		return this;
	}

}
