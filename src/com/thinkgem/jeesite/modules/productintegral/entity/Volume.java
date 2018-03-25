package com.thinkgem.jeesite.modules.productintegral.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * @author ChenXb
 * 容量 
 * 2017年10月30日
 */
public class Volume extends DataEntity<Volume>{
	private static final long serialVersionUID = 7566952663898319362L;
	private String volumeId;
	private String volume;
	
	/**
	 * @return the volumeId
	 */
	public String getVolumeId() {
		return volumeId;
	}
	/**
	 * @param volumeId the volumeId to set
	 */
	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}
	/**
	 * @return the volume
	 */
	public String getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}
	
}
