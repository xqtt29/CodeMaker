//map��Ϣ

class table ����ϸ
	String tableName				����
	String tableNameUpper				������д
	List<?> columns					����

class column ������ϸ
	String columnName;				//����				��:id
	String columnNameUpper;				//������д			��:ID
	String columnNameFistUpper;			//��������ĸ��д		��:Id
	String columnType;				//����������			��:VARCHAR2||DATE||NUMBER||CLOB||BLOB
	String columnNote;				//�еı�ע			��:����id
	String columnNameLower;				//����Сд			��:id
	String columnSize;				//��ֵ��С			��:8
String path ��·��
String ClassName ����
String classNote ��������
List<String> busiinfo ҵ�����������Ϣ

conf.properties�����ļ��п��Զ����ֵ����template����ʹ�ã�Ŀǰ֧���Զ������ָ�ʽ(��ע��ģ�������﷨����)��
	1����--ֵ  
	2����--����(���ֵ��&_&�ָ�,��templateģ����ʹ��listѭ�����)
	3����--SQL���(������SQL_��ͷ,���صĽ����ΪList<Map<String,String>>)
		���磺
		�����ļ��������ã�
			TSQL_busiinfo=SELECT * FROM TB_BUS_COREINFRULE	
		ģ���ļ�����ʹ��(ע��MAP�ļ�����SQL�ж�Ӧ���У�������Ϊ��д)��
			<#list TSQL_busiinfo as maps>
				${maps["BUSI_CODE"]}--->${maps["CHANNEL"]}--->${maps["RULE_ID"]}--->${maps["CREATE_MAN"]}
			</#list>
			
			
by 20131227

����
	in.txt ��Χ�ӿ����
	out.txt ��Χ�ӿڳ���
�����ļ�

�÷���
1������Χ�ӿ��ĵ�������������Ƶ�in.txt ����������Ƶ�out.txt 
2����conf.properties������int_name��Χ�ӿ����Ƽ�int_code��Χ�ӿڱ����Լ����ɿ���int_flag=0
��������ɽ��ײ��ֽű���