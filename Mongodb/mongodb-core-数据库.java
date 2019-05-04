-------------------------
数据库					 |
-------------------------
	# 一个mongodb中可以建立多个数据库

	# MongoDB的默认数据库为"db",该数据库存储在data目录中

	# MongoDB的单个实例可以容纳多个独立的数据库,每一个都有自己的集合和权限,不同的数据库也放置在不同的文件中

	# 数据库名称的约束(UTF-8字符串)
		*  不能是空字符串（"")
		* 不得含有' '（空格)、.、$、/、\和\0 (空字符)
		* 应全部小写
		* 最多64字节
	
	# 有一些数据库名是保留的,可以直接访问这些有特殊作用的数据库
		admin
			* 从权限的角度来看,这是"root"数据库
			* 要是将一个用户添加到这个数据库,这个用户自动继承所有数据库的权限
			* 一些特定的服务器端命令也只能从这个数据库运行,比如列出所有的数据库或者关闭服务器

		local
			* 这个数据永远不会被复制,可以用来存储限于本地单台服务器的任意集合

		config
			* 当Mongo用于分片设置时,config数据库在内部使用,用于保存分片的相关信息
	
	#  默认的数据库为 test,如果没有创建新的数据库,集合将存放在 test 数据库中

-------------------------
命令					 |
-------------------------
	show dbs
		* 显示所有数据的列表
		* 如果数据库没有数据(空的),不会显示
	
	db
		* 显示当前数据库对象或集合
	
	use
		* 切换到指定的数据库
		* 如果数据库不存在,则创建数据库
	
	db.dropDatabase()
		* 删除当前数据库
	


	
