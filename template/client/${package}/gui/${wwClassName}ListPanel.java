package ${wwPackagePath}.client.${wwPackageName}.gui;

import java.util.ArrayList;
import java.util.List;
import com.chinacreator.common.client.widgets.grid.GridPager;
import com.chinacreator.ecb.client.base.gui.EcbListPanel;
import com.chinacreator.ecb.client.base.gui.EcbWindow;
import ${wwPackagePath}.client.${wwPackageName}.datasource.${wwClassName}DataSource;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.HeaderClickEvent;
import com.smartgwt.client.widgets.grid.events.HeaderClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * ${wwClassInfo}列表展示
 * <p>
 * Copyright: Chinacreator (c) 2014
 * </p>
 * <p>
 * Company: 湖南科创信息技术股份有限公司
 * </p>
 * @author ${author}  time ${sysdate}
 * 
 */
 public class ${wwClassName}ListPanel extends EcbListPanel { 
	//分页组件
	private GridPager gridPager;  
	
	//定义树数据源
	private ${wwClassName}DataSource listDS = ${wwClassName}DataSource.getInstance();
	
	public ${wwClassName}ListPanel(EcbWindow parent){
		super(parent);
	 
		this.setHeight100();
		this.setOverflow(Overflow.AUTO);
		
		//新增按钮
		ToolStripButton newButton = new ToolStripButton("新增");
		newButton.setIcon("[SKIN]/actions/add.png");
		newButton.setTooltip("新增${wwClassInfo}");
		newButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {		
				addRecord();
			}
		});
				
		ToolStripButton editButton = new ToolStripButton("编辑");
		editButton.setIcon("[SKIN]/actions/edit.png");
		editButton.setTooltip("编辑${wwClassInfo}");
		editButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				Record record = listGrid.getSelectedRecord();
				if(record == null){
					SC.say("没有选中${wwClassInfo}，请在${wwClassInfo}列表中选择一个${wwClassInfo}再进行编辑");
					return;
				}
				editRecord(record);
			}
		});
		
		ToolStripButton removeButton = new ToolStripButton("删除");
		removeButton.setIcon("[SKIN]/actions/remove.png");
		removeButton.setTooltip("删除${wwClassInfo}");
		removeButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				Record[] records = listGrid.getSelectedRecords();
				if(records == null || records.length == 0){
					SC.say("没有选中${wwClassInfo}，请在${wwClassInfo}列表中至少选择一个${wwClassInfo}再删除");
					return;
				}				
				removeSelectedRecord();
			}
		});
		
		ToolStripButton reloadButton = new ToolStripButton("刷新");
		reloadButton.setIcon("[SKIN]/actions/refresh.png");
		reloadButton.setTooltip("刷新${wwClassInfo}列表");
		reloadButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				reloadData();
			}
		});	
		
		ToolStripButton searchButton = new ToolStripButton("查询");
		searchButton.setIcon("[SKIN]/actions/filter.png");
		searchButton.setTooltip("查询${wwClassInfo}理");
		searchButton.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				listGrid.setShowFilterEditor(listGrid.getShowFilterEditor() == null ? true : !listGrid.getShowFilterEditor());
			}
		});	
		 
	
		//---------- ${wwClassInfo}列表定义 ----------------
		listGrid = getListGrid();//new ListGrid();
		listGrid.setDataSource(listDS);
		listGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);		
		listGrid.setAutoFetchData(true);
		listGrid.setWidth100();
		//listGrid.setAutoFitFieldWidths(true);
		listGrid.setSelectionType(SelectionStyle.SIMPLE); //列表行选择方式为多行
		listGrid.setEmptyMessage("没有数据显示");
		
		//定义显示字段
		List<ListGridField> listFieldList = new ArrayList<ListGridField>();
	
		<#list wwTable.columns as c>
		ListGridField ${c.columnNameVec}Field = new ListGridField("${c.columnNameLower}", "${c.columnNote}");
		${c.columnNameVec}Field.setCanFilter(true);
		${c.columnNameVec}Field.setShowHover(true);
		${c.columnNameVec}Field.setWidth("25%");
		listFieldList.add(${c.columnNameVec}Field);
		</#list>
		
		listGrid.setFields(listFieldList.toArray(new ListGridField[0]));		
		
//		//添加双击编辑事件
//		listGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {			
//			@Override
//			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
//				editRecord(event.getRecord());
//			}
//		});
		
		
		/*
		 * 如果Grid设置
		 * setSelectionType(SelectionStyle.SIMPLE);
		 * setSelectionAppearance(SelectionAppearance.CHECKBOX);
		 * 两个属性会出现全选复选框无效问题。
		 *  原因：全选复选框会选择grid中所有记录，但是smartgwt又对选择多条记录有总数限制，
		 *  解决方案：将选择grid中所有记录替换为选择所有可见的记录。
		 *  添加一个事件，一个方法，详情见GridPager
		 */
		listGrid.addHeaderClickHandler(new HeaderClickHandler(){
			public void onHeaderClick(HeaderClickEvent event)
				{
				Boolean re = changeSelectRange(listGrid,event.getFieldNum());
				if(re != null){	//如果是CheckBoxField事件就取消事件，禁止事件继续传递给上一级			
					event.cancel();
				}			
				}
			});

		//----------- 分页组件定义 -----------
		////listGrid = new listGrid(listGrid, ListGrid_Default_PageSize );
//		PagedListGrid plistGrid = new PagedListGrid(this.getModuleId());//listGrid, ListGrid_Default_PageSize );
//		plistGrid.setDataPageSize(ListGrid_Default_PageSize);
//		addMember( getToolStrip() );
		
		addMember(listGrid);
		addMember(listGrid.getPager());
		
	}
	
	//新增记录
	public void addRecord(){
//		${wwClassName}EditWindow window = new ${wwClassName}EditWindow(null,null);
//		CanvasUtils.showChildPanel(window, (EcbWindow)parentContainer);
	}
	
//	//编辑记录
//	public void editRecord(Record record){
//		${wwClassName}EditWindow window = new ${wwClassName}EditWindow(record,null);
//		CanvasUtils.showChildPanel(window, parentWindow);
//	}
	
	//删除单条记录
	public void removeRecord(final Record record){
		SC.confirm("警告", "确认删除选择的记录吗？", new BooleanCallback() {				
			@Override
			public void execute(Boolean value) {
				if(value){
						listGrid.removeData(record, new DSCallback() {										
									public void execute(DSResponse response,Object rawData,	DSRequest request) {
										if (response.getStatus() != DSResponse.STATUS_SUCCESS) {
											SC.say("${wwClassInfo}删除失败！原因：<br/>"+((Throwable)response.getAttributeAsObject("caught")).getMessage());
										}
									}
						});
				}					
			}
		});
	}
	
	//删除多条选中记录
	public void removeSelectedRecord(){
		SC.confirm("警告", "确认删除选择的记录吗？", new BooleanCallback() {				
			@Override
			public void execute(Boolean value) {
				if(value){
						listGrid.removeSelectedData(new DSCallback() {							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() != DSResponse.STATUS_SUCCESS) {
									SC.say("${wwClassInfo}理删除失败！原因：<br/>"+((Throwable)response.getAttributeAsObject("caught")).getMessage());
								}
							}
						}, null);
				}					
			}
		});
	}	
	
	//刷新${wwClassInfo}列表
	public void reloadData(){
		listGrid.invalidateCache();
		listGrid.fetchData();
	}	
	/**
	 * javascript本地方法。 自定义checkBoxHeader选择范围为当前页面，解决默认选择所有导致出现提示"Can't select that many records at once"问题。
	 * @param grid 要更改CheckboxHeader选择的ListGrid对象
	 * @param fieldNum checkBoxField字段位置
	 * @return 如果该字段是checkBoxField则返回当前的状态，选中为true，未选为false，如果该字段不是checkBoxField，则返回null
	 */
	public native Boolean changeSelectRange(ListGrid grid ,int fieldNum)/*-{
	    var self = grid.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
	    var field = self.fields[fieldNum];
	    // check if the checkbox column header was clicked
	    if (self.isCheckboxField(field) && self.selectionType != "single" && self.canSelectAll != false) {
	        if (field._allSelected) {
	            self.deselectAllRecords();
	            //更改checkboxHeader图标为不选中
	           	var icon =  self.checkboxFieldFalseImage || self.booleanFalseImage,
			        title = self.getValueIconHTML(icon, field);			         
			    self.setFieldTitle(fieldNum, title);
	            field._allSelected=false;
	        } else {
	        	var visibleRows = self.getVisibleRows();//return visibleRows start end end point ,if no visiable return [-1,-1]
            	var dataRows = self.data.getRange(visibleRows[0], visibleRows[1]+1);	        	
	            self.selectRecords(dataRows, true);
	           	//更改checkboxHeader图标为选中
	           	var icon =  self.checkboxFieldTrueImage || self.booleanTrueImage,
			        title = self.getValueIconHTML(icon, field);			         
			    self.setFieldTitle(fieldNum, title);
	            field._allSelected=true;
	        }
	    	 return @com.smartgwt.client.util.JSOHelper::toBoolean(Z)(field._allSelected);
	    }else{
	    	//if click other header clear all selected records
	    	 self.deselectAllRecords();
	    	 field._allSelected=false;
	     	 return null;
	    }
	   
	 }-*/;
	
}