package Controller.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "ALL_DICT")
public class Dict implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int parentId;
	@Column(name = "DICT_KEY")
	private String key;
	@Column(name = "DICT_VALUE")
	private String value;
	private String remark;

	public int getId() {
		return id;
	}

	public Dict setId(int id) {
		this.id = id;
		return this;
	}

	public int getParentId() {
		return parentId;
	}

	public Dict setParentId(int parentId) {
		this.parentId = parentId;
		return this;
	}

	public String getKey() {
		return key;
	}

	public Dict setKey(String key) {
		this.key = key;
		return this;
	}

	public String getValue() {
		return value;
	}

	public Dict setValue(String value) {
		this.value = value;
		return this;
	}

	public String getRemark() {
		return remark;
	}

	public Dict setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	public Dict() {
		super();
	}

}
