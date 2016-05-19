<#list TSQL_busiinfo as maps>
INSERT INTO TB_BUS_BUSIPARAM(BUSI_CODE,BUSI_NAME,PARAM_CODE,PARAM_NAME,PARAM_VALUE,STATE) VALUES ('业务编码','业务名称','${maps["PARAM_CODE"]}','${maps["PARAM_NAME"]}','${maps["PARAM_VALUE"]}','${maps["STATE"]}');
</#list>
COMMIT;


${otherSQL}
