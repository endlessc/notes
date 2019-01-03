-------------------------
acl权限管理				 |
-------------------------
	# 参考
		https://www.jianshu.com/p/147ca2533aff

	# zookeeper支持的权限有5种分别是
		CREATE: 可以创建子节点
		READ: 可以获取节点数据以及当前节点的子节点列表
		WRITE: 可以为节点设置数据
		DELETE: 可以删除子节点
		ADMIN: 以为节点设置权限

	# Zookeeper的ACL,可以从三个维度来理解,一是 scheme; 二是 user; 三是 permission
		* scheme 表示权限的控制类型
		* user 表示授权的对象
		* permission 表示访问权限

	# 通常表示为:scheme:id:permissions

	# scheme
		* scheme对应于采用哪种方案来进行权限管理，zookeeper实现了一个pluggable的ACL方案
		* 可以通过扩展scheme,来扩展ACL的机制
		* zookeeper-3.4.4缺省支持下面几种scheme

		world
			* 它下面只有一个id, 叫 anyone, world:anyone 代表任何人
			* zookeeper中对所有人有权限的结点就是属于world:anyone的

		auth:
			* 它不需要id, 只要是通过authentication的user都有权限(zookeeper支持通过kerberos来进行authencation, 也支持username/password形式的authentication)
			* 不过这里应该用expression来表示,即(scheme:expression:permissions)

		digest:
			* 它对应的id为username:BASE64(SHA1(password)),它需要先通过username:password形式的authentication
			*  digest:kevin:BASE64(SHA1('123456'))
		
		host 
			* 使用用户主机名作为访问控制列表的id
			* 但是这里需要注意的是表达式用的是主机名的后缀即可,只要是符合后缀的多级域名都可以

		ip
			* 它对应的id为客户机的IP地址,设置的时候可以设置一个ip段
			* 比如 ip:192.168.1.0/16, 表示匹配前16个bit的IP段
			
		super:
			* 在这种scheme情况下,对应的id拥有超级权限,可以做任何事情(cdrwa)

-------------------------
acl权限管理	- auth		 |
-------------------------
	# auth不使用任何 id ,代表任何已确认用户
	# 在执行授权之前,必须先添加用户
		addauth digest zookeeper:zookeeper

		* zookeeper:zookeeper 用户名和密码
	
	#  设置auth授权
		setAcl path auth:::rwadc

		* path 节点
		* auth 授权方式
		* rwadc 表示权限:read,write,admin,delete,create

		* 该授权表示,授权的节点,允许被系统添加的用户操作
	
	# 查看acl授权
		getAcl path
		'digest,'zookeeper:4lvlzsipXVaEhXMd+2qMrLc0at8=
		: cdrwa
		'digest,'root:qiTlqPLK7XM2ht3HMn02qRpkKIE=
		: cdrwa

	# 先添加用户到系统,系统帮你进行sha1 和 base64编码
	# 授权节点,不需要指定用户名和密码

-------------------------
acl权限管理	- digest	 |
-------------------------
	# 跟 auth 差不多
	# 在设置授权的时候执行设置

		setAcl path digest:user:pass:rwadc

		* path 节点
		* user:pass 用户名和密码,注意,密码是先经过 sha1运算,然后base64编码的结果
		* auth 授权方式
		* rwadc 表示权限:read,write,admin,delete,create
	
	# 添加到zk
		addauth digest user:pass
		* user:pass 用户名和密码

	#  查看acl授权
		'digest,'zookeeper:4lvlzsipXVaEhXMd+2qMrLc0at8=
		: cdrwa
		'digest,'root:qiTlqPLK7XM2ht3HMn02qRpkKIE=
		: cdrwa
	
	# 先执行授权,设置的密码需要自己去经过 sha1 和 base64编码
	# 再把用户添加到系统
