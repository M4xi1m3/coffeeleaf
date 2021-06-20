package io.github.m4x1m3.coffeeleaf.model;

public class UMLClass {
	private String name;
	private UMLPackage parent;
	
	public UMLClass(String name, UMLPackage parent) {
		this.name = name;
		this.parent = parent;
		parent.addClass(this);
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + name.hashCode();
		hash = 31 * hash + parent.hashCode();
		return hash;
	}

	public String getName() {
		return name;
	}

	public UMLPackage getParent() {
		return parent;
	}
	
	public void debug(int depth) {
		System.out.println(" ".repeat(depth * 4) + name);
	}
}
