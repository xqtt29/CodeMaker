package ${wwPackagePath}.client.${wwPackageName}.gui;

import com.chinacreator.common.client.util.reflection.annotations.ClassForNameAble;
import com.chinacreator.ecb.client.base.gui.EcbWindow;

/**
 * ${wwClassInfo}界面入口
 * <p>
 * Copyright: Chinacreator (c) 2014
 * </p>
 * <p>
 * Company: 湖南科创信息技术股份有限公司
 * </p>
 * @author ${author}  time ${sysdate}
 * 
 */
@ClassForNameAble
public class ${wwClassName}Window extends EcbWindow {

	/**
	 * 默认构造函数
	 */
	public ${wwClassName}Window() {
		super();
		
		initializeInterface();
	}

	/**
	 * 
	 * <p>初始界面的方法，分离可被多个构造函数调用。</p>
	 * @author ${author} on ${sysdate}
	 */
	private void initializeInterface() {
		setModuleId("${wwClassCode}");
		setModuleName("${wwClassInfo}");   //用于前台提示信息
		
		//setHeight(520);
		addItem(get${wwClassName}EditPanel());	
		addItem(createHLayoutSpacer());		
		addItem(getToolStrip());
		addItem(createHLayoutSpacer());		
		addItem(get${wwClassName}ListPanel());	

//		setEditPanel(get${wwClassName}EditPanel());
//		setListPanel(get${wwClassName}ListPanel());
	}

	private ${wwClassName}ListPanel ${wwPackageName}ListPanel = null;
	private ${wwClassName}ListPanel get${wwClassName}ListPanel() {
		if (${wwPackageName}ListPanel==null) {
			${wwPackageName}ListPanel = new ${wwClassName}ListPanel(this);
			setListPanel(${wwPackageName}ListPanel);
		}
		return ${wwPackageName}ListPanel;
	}

	private ${wwClassName}EditPanel ${wwPackageName}EditPanel = null;
	private ${wwClassName}EditPanel get${wwClassName}EditPanel() {
		if (${wwPackageName}EditPanel==null) {
			${wwPackageName}EditPanel = new ${wwClassName}EditPanel(this);
			setEditPanel(${wwPackageName}EditPanel);
		}
		return ${wwPackageName}EditPanel;
	}
}
