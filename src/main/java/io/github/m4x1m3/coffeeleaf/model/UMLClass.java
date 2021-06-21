/**
 * Copyright 2021 Maxime "M4x1m3" FRIESS
 * 
 * This file is part of CoffeeLeaf.
 *
 * CoffeeLeaf is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CoffeeLeaf is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with CoffeeLeaf.  If not, see <https://www.gnu.org/licenses/>.
 */
package io.github.m4x1m3.coffeeleaf.model;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import io.github.m4x1m3.coffeeleaf.utils.ReflectUtil;

/**
 * Represents a class in the UML
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class UMLClass {
	private String name;
	private UMLPackage parent;
	private UMLAccessLevel accessLevel;
	private UMLClassType classType;
	private boolean isFinal;

	private List<UMLMethod> methods;
	private List<UMLConstructor> constructors;
	private List<UMLField> fields;

	public UMLClass(Class<?> c, UMLPackage parent) {
		this(c.getSimpleName(), parent, ReflectUtil.getAccessLevel(c), ReflectUtil.getClassType(c),
				(c.getModifiers() & Modifier.FINAL) != 0);
	}

	public UMLClass(String name, UMLPackage parent, UMLAccessLevel accessLevel, UMLClassType classType,
			boolean isFinal) {
		this.name = name;
		this.parent = parent;
		this.accessLevel = accessLevel;
		this.classType = classType;
		this.isFinal = isFinal;
		this.methods = new ArrayList<UMLMethod>();
		this.constructors = new ArrayList<UMLConstructor>();
		this.fields = new ArrayList<UMLField>();
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + name.hashCode();
		hash = 31 * hash + parent.hashCode();
		return hash;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		String pname = parent.getFullName();
		return (pname.length() == 0 ? "" : pname + ".") + this.getName();
	}

	public UMLPackage getParent() {
		return parent;
	}

	public void debug(int depth) {
		System.out.println(" ".repeat(depth * 4) + "[CLS] " + this.getFullName());
	}

	public UMLAccessLevel getAccessLevel() {
		return accessLevel;
	}

	public UMLClassType getClassType() {
		return classType;
	}

	public List<UMLField> getFields() {
		return new ArrayList<UMLField>(fields);
	}

	public List<UMLMethod> getMethods() {
		return new ArrayList<UMLMethod>(methods);
	}

	public List<UMLConstructor> getConstructors() {
		return new ArrayList<UMLConstructor>(constructors);
	}

	public void addConstructor(UMLConstructor c) {
		this.constructors.add(c);
	}

	public void addMethod(UMLMethod meth) {
		this.methods.add(meth);
	}

	public void addField(UMLField field) {
		this.fields.add(field);
	}
}
