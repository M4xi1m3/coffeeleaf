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

	public UMLClass(String name, UMLPackage parent, UMLAccessLevel accessLevel, UMLClassType classType) {
		this.name = name;
		this.parent = parent;
		this.accessLevel = accessLevel;
		this.classType = classType;
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
}
