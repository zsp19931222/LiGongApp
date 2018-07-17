package yh.app.tool;
/**
 * 
 * 包	名:yh.app.tool
 * 类	名:KeyValue.java
 * 功	能:键值对操作
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 * @param <K>
 * @param <V>
 */
public class KeyValue <K,V>
{   
	private K key;  
	private V value; 
	public 	KeyValue(){
		
	};
	//初始化key和value 
	public 	KeyValue(K k2,V v2){
		key=k2;
		value=v2;
	} 
	public 	K getKey(){
		return key;
	}  
	public 	V getValue(){
		return value;
	} 
	public void setKey(K key2){
		this.key=key2;
	}  
	public void setValue(V value2){
		this.value=value2;
	}
} 