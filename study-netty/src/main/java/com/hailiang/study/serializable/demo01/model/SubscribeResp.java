package com.hailiang.study.serializable.demo01.model;

import java.io.Serializable;

/**
 * 订购应答消息
 * 
 * @classname com.hailiang.study.serializable.demo01.SubscribeResp
 * @author hailiang.jiang
 * @date 2015年12月10日 上午11:24:56
 * @version: v1.0.0
 */
public class SubscribeResp implements Serializable {
	/** 默认的序列号ID **/
	private static final long serialVersionUID = 1L;
	
	/**订购编号**/
	private int subReqId;
	
	/**订购结果：0表示成功**/
	private int respCode;
	
	/**可选的详细描述信息**/
	private String desc;

	public int getSubReqId() {
		return subReqId;
	}

	public void setSubReqId(int subReqId) {
		this.subReqId = subReqId;
	}

	public int getRespCode() {
		return respCode;
	}

	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "SubscribeResp [subReqId=" + subReqId + ", respCode=" + respCode
				+ ", desc=" + desc + "]";
	}

}
