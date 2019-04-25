package com.lvshihao.entity;

import java.util.HashMap;
import java.util.Map;

public class Msg {
	//״̬�� 100-�ɹ� 200-ʧ��
	private int code;
	//��ʾ��Ϣ
	private String msg;
	//�û�Ҫ���ظ������������
	private Map<String, Object> eMap=new HashMap<String, Object>();
	
	public static Msg succes() {
		Msg result=new Msg();
		result.setCode(100);
		result.setMsg("����ɹ�");
		return result;
	}
	public static Msg fail() {
		Msg result=new Msg();
		result.setCode(200);
		result.setMsg("����ʧ��");
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
