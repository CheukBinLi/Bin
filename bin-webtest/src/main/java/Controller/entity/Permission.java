package Controller.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "ALL_PERMISSION")
public class Permission implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String permissionName;
	private int status;
	private String remark;

	public int getId() {
		return id;
	}

	public Permission setId(int id) {
		this.id = id;
		return this;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public Permission setPermissionName(String permissionName) {
		this.permissionName = permissionName;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public Permission setStatus(int status) {
		this.status = status;
		return this;
	}

	public String getRemark() {
		return remark;
	}

	public Permission setRemark(String remark) {
		this.remark = remark;
		return this;
	}

}
