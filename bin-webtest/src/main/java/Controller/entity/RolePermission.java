package Controller.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "ALL_ROLE_PERMISSION")
public class RolePermission implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int roleId;
	private int permissionId;

	public int getId() {
		return id;
	}

	public RolePermission setId(int id) {
		this.id = id;
		return this;
	}

	public int getRoleId() {
		return roleId;
	}

	public RolePermission setRoleId(int roleId) {
		this.roleId = roleId;
		return this;
	}

	public int getPermissionId() {
		return permissionId;
	}

	public RolePermission setPermissionId(int permissionId) {
		this.permissionId = permissionId;
		return this;
	}

}
