package testservice;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "shop")
public class Shop {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id; // id

	@Column(name = "name")
	private String name; // 名称

	@Column(name = "contract_name")
	private String contractName; // 联系方式

	@Column(name = "tel")
	private String tel; // 电话

	@Column(name = "address")
	private String address; // 地址

	@Column(name = "creater")
	private String creater; // 创建人

	@Column(name = "creater_id")
	private int createrId; // 创建人

	@Column(name = "create_time")
	private Date createTime; // 时间

	@Column(name = "act_nums")
	private int actNums; // 下单次数

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getActNums() {
		return actNums;
	}

	public void setActNums(int actNums) {
		this.actNums = actNums;
	}

}
