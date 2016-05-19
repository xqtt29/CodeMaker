package ${wwPackagePath}.client.${wwPackageName}.gui;

import com.chinacreator.ecb.client.base.gui.EcbEditPanel;
import com.chinacreator.ecb.client.base.gui.EcbWindow;
import com.chinacreator.ecb.client.core.util.CanvasUtils;
import ${wwPackagePath}.client.${wwPackageName}.datasource.${wwClassName}DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * ${wwClassInfo}编辑区
 * <p>
 * Copyright: Chinacreator (c) 2014
 * </p>
 * <p>
 * Company: 湖南科创信息技术股份有限公司
 * </p>
 * @author ${author}  time ${sysdate}
 * 
 */
 public class ${wwClassName}EditPanel extends EcbEditPanel { 
	//记录编辑布局
	private VLayout editlayout;
	//待编辑的记录,如果为null则表示新增
	private Record editRecord = null;
	//新增树形结构数据时的父Id
	//private String parentId = null;
	
	/**
	 * 
	 * @param initEditRecord 待编辑记录,如果为null则表示新增
	 * @param parentId	新增树形结构数据时的父Id
	 */
	public ${wwClassName}EditPanel(Record initEditRecord , String parentId){
		super(null);
		editRecord = initEditRecord;
		initializeInterface();
	}
	public ${wwClassName}EditPanel(EcbWindow parentWindow){
		super(parentWindow);
		initializeInterface();
	}
		
	public void initializeInterface( ){ 
		
//		setWidth100();//窗体宽度500
//		setHeight100();//窗体高度 200

		setShowCustomScrollbars(false);
//		setShowHeader(false); //不显示标题
	    setEdgeImage("[SKIN]/../../../standard/images/blank.gif");//边界图片
	    setEdgeBackgroundColor("#ffffff");
	    setEdgeSize(1);
//		setShowMinimizeButton(false);
		this.setBorder("1px solid gray");
//		setShowMinimizeButton(false);
//		setIsModal(true); //设置为模式窗体
//		setShowModalMask(true); //设置背景屏蔽后面面板
//		centerInPage(); //在页面中间显示
		if(editRecord == null){
			setTitle("新增${wwClassInfo}");
		}else{
			setTitle("编辑${wwClassInfo}");
		}
		
		//初始化表单
		editForm = getEditForm();
		editForm.setDataSource(${wwClassName}DataSource.getInstance());
		editForm.setPadding(5);
		editForm.setNumCols(4);
		editForm.setCellPadding(5); 

		editForm.setFields(
				<#list wwTable.columns as c>
				get${c.columnNameVecFistUpper}Item()<#if c_has_next>,</#if>
				</#list>
		);
 
		//编辑记录布局面板
		editlayout = new VLayout();
		editlayout.addMember(editForm);
		//editlayout.addMember(operatePanel);
		
		addMember(editlayout);
		//初始化表单数据
		if(editRecord == null){
			newRecord();
			//get${wwTable.columns[0].columnNameVecFistUpper}Item().setAttribute("readOnly",false);
		}else{
			editRecord(editRecord);
			//get${wwTable.columns[0].columnNameVecFistUpper}Item().setAttribute("readOnly",true);
		}
	}
	
	@Override
	public boolean editRecordBefore(Record record) {
		CanvasUtils.setReadOnly(get${wwTable.columns[0].columnNameVecFistUpper}Item(), true);
		return true;
	}

	@Override
	public boolean newRecordBefore( ) {
		<#list wwTable.columns as c>
		CanvasUtils.setReadOnly(get${c.columnNameVecFistUpper}Item(), false);
		</#list>
		return true;
	}
	
	<#list wwTable.columns as c>
	private TextItem ${c.columnNameVec} = null;
	private TextItem get${c.columnNameVecFistUpper}Item() {
		if (${c.columnNameVec}==null) {
			${c.columnNameVec} = new TextItem("${c.columnNameLower}", "${c.columnNote}");
			${c.columnNameVec}.setRequired(true);
			if (editRecord!=null) {
				//CanvasUtils.setReadOnly(${c.columnNameVec}, true);
			}
//		${c.columnNameVec}.setLength(100);
			${c.columnNameVec}.setWidth( "*" );
		}
		return ${c.columnNameVec};
	}
	</#list>
}