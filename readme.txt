//map信息

class table 表明细
	String tableName				表名
	String tableNameUpper				表名大写
	List<?> columns					表列

class column 表列明细
	String columnName;				//列名				例:id
	String columnNameUpper;				//列名大写			例:ID
	String columnNameFistUpper;			//列名首字母大写		例:Id
	String columnType;				//列数据类型			例:VARCHAR2||DATE||NUMBER||CLOB||BLOB
	String columnNote;				//列的备注			例:主键id
	String columnNameLower;				//列名小写			例:id
	String columnSize;				//列值大小			例:8
String path 类路径
String ClassName 类名
String classNote 类中文名
List<String> busiinfo 业务编码配置信息

conf.properties配置文件中可自定义键值并在template自由使用，目前支持自定义三种格式(请注意模版语言语法规则)：
	1、键--值  
	2、键--数组(多个值以&_&分隔,在template模版中使用list循环输出)
	3、键--SQL语句(键名以SQL_开头,返回的结果集为List<Map<String,String>>)
		例如：
		配置文件这样配置：
			TSQL_busiinfo=SELECT * FROM TB_BUS_COREINFRULE	
		模块文件这样使用(注意MAP的键就是SQL中对应的列，列名均为大写)：
			<#list TSQL_busiinfo as maps>
				${maps["BUSI_CODE"]}--->${maps["CHANNEL"]}--->${maps["RULE_ID"]}--->${maps["CREATE_MAN"]}
			</#list>
			
			
by 20131227

新增
	in.txt 外围接口入参
	out.txt 外围接口出参
两个文件

用法：
1、将外围接口文档的输入参数复制到in.txt 输出参数复制到out.txt 
2、在conf.properties中配置int_name外围接口名称及int_code外围接口编码以及生成开关int_flag=0
则可以生成交易部分脚本。