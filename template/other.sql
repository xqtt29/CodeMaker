<#list TSQL_busiinfo as maps>
INSERT INTO TB_BUS_BUSIPARAM(BUSI_CODE,BUSI_NAME,PARAM_CODE,PARAM_NAME,PARAM_VALUE,STATE) VALUES ('ҵ�����','ҵ������','${maps["PARAM_CODE"]}','${maps["PARAM_NAME"]}','${maps["PARAM_VALUE"]}','${maps["STATE"]}');
</#list>
COMMIT;


${otherSQL}
