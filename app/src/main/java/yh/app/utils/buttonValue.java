package yh.app.utils;

/**
 * 
 * 包 名:yh.app.utils 类 名:buttonValue.java 功 能:按钮信息设置
 * 
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class buttonValue {

	private String	button_name;
	private int 	FunctionID;
	private String 	cls;
	private String 	pkg;
	private int    	Type;

	public void setButtonName(String button_name) {
		this.button_name = button_name;
	}
	public String getButtonName() {
		return button_name;
	}

	public void setButtonId(int FunctionID) {
		this.FunctionID = FunctionID;
	}
	public int getButtonId() {
		return FunctionID;
	}

	public void setTag(String cls) {
		this.cls = cls;
	}
	public String getTag() {
		return cls;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	public String getPkg() {
		return pkg;
	}
	
	public void setType(int Type){
		this.Type = Type;
	}
	public int getType(){
		return this.Type;
	}
	
	public void setAllButtonValues(int FunctionID,String button_name,String pkg,String cls,int Type){
		this.button_name 	= button_name;
		this.FunctionID 	= FunctionID;
		this.cls 			= cls;
		this.pkg			= pkg;
		this.Type			= Type;
	}
}