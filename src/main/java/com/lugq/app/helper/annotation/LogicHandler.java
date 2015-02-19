package com.lugq.app.helper.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LogicHandler {

	public int id();
	public String desc();

}
