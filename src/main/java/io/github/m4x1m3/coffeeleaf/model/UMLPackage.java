package io.github.m4x1m3.coffeeleaf.model;

import java.util.HashMap;

public class UMLPackage {
	private String name;
	private UMLPackage parent;
	
	private HashMap<String, UMLPackage> subPackages;
	private HashMap<String, UMLClass> subClasses;
	
	public UMLPackage(String name, UMLPackage parent) {
		this.name = name;
		this.parent = parent;
		this.subPackages = new HashMap<String, UMLPackage>();
		this.subClasses = new HashMap<String, UMLClass>();
	}
	
	public UMLPackage findOrCreatePackage(String name) {
		if (subPackages.containsKey(name)) {
			return subPackages.get(name);
		} else {
			UMLPackage pkg = new UMLPackage(name, this);
			subPackages.put(name, pkg);
			return pkg;
		}
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + name.hashCode();
		return hash;
	}

	public void addClass(UMLClass umlClass) {
		this.subClasses.put(umlClass.getName(), umlClass);
	}
	
	public void debug(int depth) {
		System.out.println(" ".repeat(depth * 4) + name);
		
		subPackages.forEach((n, p) -> {
			p.debug(depth + 1);
		});
		
		subClasses.forEach((n, c) -> {
			c.debug(depth + 1);
		});
	}
}
