package com.base;

/**
 * @desciption 公共参数类
 */
public class Global {
	private Global(){}
	/**
	 * @desciption 数据库url
	 */
	public static String AUTHOR="author";

	/**
	 * @desciption 数据库url
	 */
	public static String URL="url";

	/**
	 * @desciption 数据库driver
	 */
	public static String DRIVER="driver";

	/**
	 * @desciption 数据库账号
	 */
	public static String DBNAME="dbname";

	/**
	 * @desciption 数据库密码
	 */
	public static String DBPASS="dbpass";

	/**
	 * @desciption 生成的文件输出路径
	 */
	public static String FILE_PATH="file_path";

	/**
	 * @desciption 业务编码开关(0是1否)
	 */
	public static String BUSI_FLAG="busi_flag";

	/**
	 * @desciption 业务编码(多行参数以','分隔)
	 */
	public static String BUSI_CODE="busi_code";

	/**
	 * @desciption 要生成的DAO类的项目文件路径
	 */
	public static String CLASS_PATH="class_path";

	/**
	 * @desciption 要生成的DAO类的中文名称
	 */
	public static String CLASS_NOTE="class_note";

	/**
	 * @desciption 要生成的DAO类对应的数据库表名
	 */
	public static String TABLE_NAME="table_name";

	/**
	 * @desciption 是否生成JAVABEAN开关(0生成1不生成)
	 */
	public static String BEAN_FLAG="bean_flag";

	/**
	 * @desciption 交易编码(单行参数)
	 */
	public static String TRANS_CODE="trans_code";

	/**
	 * @desciption 是否输出交易部分的配置(0是1否)
	 */
	public static String TRANS_FLAG="trans_flag";

	/**
	 * @desciption 外围接口的编码
	 */
	public static String INT_CODE="int_code";

	/**
	 * @desciption 外围ECB接口的编码
	 */
	public static String INT_ECB_CODE="int_ecb_code";

	/**
	 * @desciption 外围接口的名称
	 */
	public static String INT_NAME="int_name";

	/**
	 * @desciption 外围ECB接口的名称
	 */
	public static String INT_ECB_NAME="int_ecb_name";

	/**
	 * @desciption 是否输出外围接口的配置(0是1否)
	 */
	public static String INT_FLAG="int_flag";
	/**
	 * @desciption 是否生成圈子平台的业务配置(0生成1办理2推荐)
	 */
	public static String QZYX_FLAG="qzyx_flag";
	/**
	 * @desciption 圈子平台业务编码
	 */
	public static String QZYX_CODE="qzyx_code";
	/**
	 * @desciption 圈子平台业务名称
	 */
	public static String QZYX_NAME="qzyx_name";
	/**
	 * @desciption 圈子平台业务对应的ecb查询编码
	 */
	public static String QZYX_ECB_QUERY="qzyx_ecb_query";
	/**
	 * @desciption 圈子平台业务对应的ecb办理编码
	 */
	public static String QZYX_ECB_OPER="qzyx_ecb_oper";
	/**
	 * @desciption 自处理类
	 */
	public static String AUTOCLASSFLAG="auto_class_flag";
	/**
	 * @desciption 自处理类路径
	 */
	public static String CLASSPATH="classPath";
	/**
	 * @desciption 自处理类描述
	 */
	public static String CLASSNOTE="classNote";
	/**
	 * @desciption 自处理类名
	 */
	public static String CLASSNAME="className";
	/**
	 * @desciption 自处理类方法
	 */
	public static String METHODNAME="methodName";
	/**
	 * @desciption 自处理方法描述
	 */
	public static String METHODNOTE="methodNote";
	/**
	 * @desciption WebWin标识(0生成1不生成)
	 */
	public static String WW_FLAG="ww_flag";
	/**
	 * @desciption WebWin表名
	 */
	public static String WW_TABLENAME="ww_tablename";
	/**
	 * @desciption WebWin包名
	 */
	public static String WW_PACKAGENAME="ww_packagename";
	/**
	 * @desciption WebWin包路径名
	 */
	public static String WW_PACKAGEPATH="ww_packagepath";
	/**
	 * @desciption WebWin类编码
	 */
	public static String WW_CLASSCODE="ww_classcode";
	/**
	 * @desciption WebWin类名
	 */
	public static String WW_CLASSNAME="ww_classname";
	/**
	 * @desciption WebWin类描述
	 */
	public static String WW_CLASSINFO="ww_classinfo";
	/**
	 * @desciption 版本打包标识(0为生成1为不生成)
	 */
	public static String VERSION_FLAG="version_flag";
	/**
	 * @desciption 版本名称
	 */
	public static String VERSION_NAME="version_name";
	/**
	 * @desciption 版本打包路径
	 */
	public static String VERSION_PATH="version_path";
	/**
	 * @desciption 版本源路径
	 */
	public static String VERSION_SRCPATH="version_srcpath";
	/**
	 * @desciption 能力平台生成标志
	 */
	public static String OPEN_FLAG="open_flag";
	/**
	 * @desciption 能力平台源代码路径
	 */
	public static String OPEN_PATH="open_path";
	/**
	 * @desciption 能力平台业务编码
	 */
	public static String OPEN_NAME="open_name";
	/**
	 * @desciption 能力平台方法编码
	 */
	public static String OPEN_METHOD="open_method";
	/**
	 * @desciption 能力平台方法名称
	 */
	public static String OPEN_METHODNAME="open_methodname";
	/**
	 * @desciption 能力平台ECB单行出参编码
	 */
	public static String OPEN_ECBSINGLEOUT="open_ecbsingleout";
	/**
	 * @desciption 能力平台单行出参编码
	 */
	public static String OPEN_SINGLEOUT="open_singleout";
	/**
	 * @desciption 能力平台ECB多行出参编码
	 */
	public static String OPEN_ECBMUTILOUT="open_ecbmutilout";
	/**
	 * @desciption 能力平台多行出参编码
	 */
	public static String OPEN_MUTILOUT="open_mutilout";
	/**
	 * @desciption 能力平台单行输出名称
	 */
	public static String OPEN_SINGLENOTE="open_singlenote";
	/**
	 * @desciption 能力平台多行输出名称
	 */
	public static String OPEN_MUTILNOTE="open_mutilnote";
	/**
	 * @desciption 能力平台ECB业务名称
	 */
	public static String OPEN_ECBCODE="open_ecbcode";
	/**
	 * @desciption 其他功能业务
	 */
	public static String OTHER_FLAG="other_flag";
}
