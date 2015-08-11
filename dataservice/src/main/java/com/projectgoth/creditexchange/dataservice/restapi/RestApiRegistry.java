package com.projectgoth.creditexchange.dataservice.restapi;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class RestApiRegistry extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> classes = new HashSet<Class<?>>();

	public RestApiRegistry() {
		// Add REST resources here
	}

	public Set<Class<?>> getClasses() {
		return this.classes;
	}

	public Set<Object> getSingletons() {
		return this.singletons;
	}
}