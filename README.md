# west

https://github.com/northandwest/West_Shard_ORM

##多个数据源动态指派DataSource

##一个服务
# 最新版本
		<west.version>0.6.6-RELEASE</west.version>

		<dependency>
			<groupId>com.bucuoa.west</groupId>
			<artifactId>west-shard-orm</artifactId>
			<version>${west.version}</version>
		</dependency>
# 使用步骤
1. 配置数据源

2. spring注入
```
	<bean id="sessionFactory" class="com.bucuoa.west.orm.core.SessionFactory">
		<property name="dataSource" ref="robotdataSource" />
	</bean>

	<bean id="excetueManager" class="com.bucuoa.west.orm.core.ExecuteManager">
		<property name="sessionFactory" ref="sessionFactory" />
	</bean>
```
3. 生成实体类
```
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bucuoa.west.orm.app.extend.BaseShardEntity;
import com.bucuoa.west.orm.shard.annonation.ShardKey;
import com.bucuoa.west.orm.shard.annonation.ShardTable;


@Entity
@Table(name = "activity")
@ShardTable(policy="hash",nums=256,shards=4)
public class Act  extends BaseShardEntity implements Serializable{

	@Transient
	private static final long serialVersionUID = -4439103201517663695L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // id

	@Column(name = "title")
	private String title; // 名称

	@Column(name = "num_limit")
	private Integer numLimit; // 人数限制

	@Column(name = "start_time")
	private Date startTime; // 开始时间

	@Temporal(TemporalType.DATE)
	@Column(name = "create_time")
	private Date createTime; // 创建

	@Column(name = "place_name")
	private String placeName; // 活动场所
	
	@ShardKey
	@Column(name = "place_id")
	private Integer placeId; // 场所

	@Column(name = "address")
	private String address; // 地址

	@Column(name = "contract_name")
	private String contractName; // 联系人

	@Column(name = "status")
	private Integer status; // 状态

	@Column(name = "creater_id")
	private Integer createrId; // 创建人

	@Column(name = "creater")
	private String creater; // 创建人

	@Column(name = "memo")
	private String memo; // 备注

	@Column(name = "uuid")
	private String uuid; // uuid
```
4. 生成dao类
```
import org.springframework.stereotype.Repository;

import com.bucuoa.bank.weixin.entity.ApplyDeposit;
import com.bucuoa.west.orm.app.extend.ISingleBaseDao;
import com.bucuoa.west.orm.app.extend.spring.SpringSingleBaseDao;
@Repository
public class ApplyDepositDao  extends SpringSingleBaseDao<ApplyDeposit, Long> implements ISingleBaseDao<ApplyDeposit, Long>{


}


```
4.1如何支持多数据源
>spring管理连接池, 注入ExecuteManager，业务Dao类继承MutiliDataSourceSpringSingleBaseDao并注入ExecuteManager对象

5. 生成Service类
```
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bucuoa.bank.weixin.entity.ApplyDeposit;
import com.bucuoa.bank.weixin.repository.ApplyDepositDao;
import com.bucuoa.west.orm.app.common.WPage;
import com.bucuoa.west.orm.app.extend.SingleBaseService;

@Service
public class ApplyDepositService  extends SingleBaseService<ApplyDeposit, Long> {
	@Autowired
	private ApplyDepositDao dao;

	public ApplyDepositDao getDao() {
		return dao;
	}
	}
```