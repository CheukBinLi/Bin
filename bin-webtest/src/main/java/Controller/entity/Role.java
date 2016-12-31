package Controller.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "ALL_ROLE")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String roleName;
	private int status;
	private String remark;
	private transient Set<Integer> permissions;

	public int getId() {
		return id;
	}

	public Role setId(int id) {
		this.id = id;
		return this;
	}

	public String getRoleName() {
		return roleName;
	}

	public Role setRoleName(String roleName) {
		this.roleName = roleName;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public Role setStatus(int status) {
		this.status = status;
		return this;
	}

	public String getRemark() {
		return remark;
	}

	public Role setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	public Set<Integer> getPermissions() {
		return permissions;
	}

	public Role setPermissions(Set<Integer> permissions) {
		this.permissions = permissions;
		return this;
	}

}
