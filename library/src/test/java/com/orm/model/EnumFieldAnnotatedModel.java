package com.orm.model;

import com.orm.annotation.Table;

@Table
public class EnumFieldAnnotatedModel {
	public enum DefaultEnum {
		ONE, TWO
	}

	public enum OverrideEnum {
		ONE, TWO;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	private OverrideEnum overrideEnum;
	private DefaultEnum defaultEnum;
	private Long id;

	public EnumFieldAnnotatedModel() {

	}

	public EnumFieldAnnotatedModel(OverrideEnum e1, DefaultEnum d1) {
		overrideEnum = e1;
		defaultEnum = d1;
	}

	public DefaultEnum getDefaultEnum() {
		return defaultEnum;
	}

	public void setDefaultEnum(DefaultEnum defaultEnum) {
		this.defaultEnum = defaultEnum;
	}

	public void setOverrideEnum(OverrideEnum overrideEnum) {
		this.overrideEnum = overrideEnum;
	}

	public OverrideEnum getOverrideEnum() {
		return overrideEnum;
	}

	public Long getId() {
		return id;
	}
}
