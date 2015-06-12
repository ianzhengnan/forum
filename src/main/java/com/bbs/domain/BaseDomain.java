package com.bbs.domain;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author I076453
 * Super class for PO class
 *
 */

public class BaseDomain implements Serializable {
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
