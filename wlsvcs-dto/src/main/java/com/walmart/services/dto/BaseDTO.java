package com.walmart.services.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.walmart.wls.util.JsonUtil;
@JsonIgnoreProperties
@XmlRootElement
@JsonSerialize(include = Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7681835340041736605L;
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}

}
