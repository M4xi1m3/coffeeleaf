package io.github.m4x1m3.coffeeleaf.model;

import java.util.ArrayDeque;

public class UMLModel {
	private String name;
	
	private UMLRootPackage rootpkg;
	
	public UMLModel(String name) {
		this.name = name;
		this.rootpkg = new UMLRootPackage();
	}
	
	public void addClass(Class<? extends Object> clazz) {
		String name = clazz.getCanonicalName();

		ArrayDeque<String> names = new ArrayDeque<String>();
		for(String s : name.split("\\.")) {
			names.add(s);
		}
		
		UMLPackage current = rootpkg;
		
		while(names.size() > 1) {
			current = current.findOrCreatePackage(names.pop());
		}
		
		new UMLClass(names.pop(), current);
	}
	
	public void debug() {
		System.out.println(name);
		rootpkg.debug(0);
	}
}
