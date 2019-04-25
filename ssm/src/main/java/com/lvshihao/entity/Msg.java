package com.lvshihao.entity;

import java.util.HashMap;
import java.util.Map;

public class Msg {
	//状态码 100-成功 200-失败
	private int code;
	//提示信息
	private String msg;
	//用户要返回给浏览器的数据
	private Map<String, Object> eMap=new HashMap<String, Object>();
	
	public static Msg succes() {
		Msg result=new Msg();
		result.setCode(100);
		result.setMsg("处理成功");
		return result;
	}
	public static Msg fail() {
		Msg result=new Msg();
		result.setCode(200);
		result.setMsg("处理失败");
		return result;
	}
	public Msg add(String key,Object object) {
		this.eMap.put(key,object);
		return this;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the eMap
	 */
	public Map<String, Object> geteMap() {
		return eMap;
	}
	/**
	 * @param eMap the eMap to set
	 */
	public void seteMap(Map<String, Object> eMap) {
		this.eMap = eMap;
	}
}
