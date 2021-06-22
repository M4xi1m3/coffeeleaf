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
package io.github.m4x1m3.coffeeleaf.model.cls;

import io.github.m4x1m3.coffeeleaf.model.pkg.UMLPackage;

/**
 * @author Maxime "M4x1m3" FRIESS
 *
 */
public class UMLArray extends UMLClass {

	private UMLClass type;

	public UMLArray(UMLClass type) {
		this.type = type;
	}

	/**
	 * Get the name of the class
	 * 
	 * @return The name of the class
	 */
	public String getName() {
		return this.type.getName() + "[]";
	}

	/**
	 * Add a constructor to the class
	 * 
	 * @param c Constructor to add
	 */
	public void addConstructor(UMLConstructor c) {
		throw new RuntimeException("Can't add a constructor to an array type!");
	}

	/**
	 * Add a method to the class
	 * 
	 * @param meth Method to add
	 */
	public void addMethod(UMLMethod meth) {
		throw new RuntimeException("Can't add a method to an array type!");
	}

	/**
	 * Add a field to the class
	 * 
	 * @param field Field to add
	 */
	public void addField(UMLField field) {
		throw new RuntimeException("Can't add a field to an array type!");
	}

	/**
	 * Set the name of the class
	 * 
	 * @param name New name
	 */
	public void setName(String name) {
		throw new RuntimeException("Can't set the name of an array type!");
	}

	/**
	 * Set the parent package of the class
	 * 
	 * @param parent New parent
	 */
	public void setParent(UMLPackage parent) {
		throw new RuntimeException("Can't set parent of an array type!");
	}

	/**
	 * Set the access level of the class
	 * 
	 * @param accessLevel New access level
	 */
	public void setAccessLevel(UMLAccessLevel accessLevel) {
		throw new RuntimeException("Can't set access level an array type!");
	}

	/**
	 * Get the type in the array
	 * 
	 * @return Type in the array
	 */
	public UMLClass getContainedType() {
		return type;
	}

	/**
	 * Set the type in the array
	 * 
	 * @param type New type in the array
	 */
	public void setContainedType(UMLClass type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash * 31 + this.getContainedType().hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof UMLArray) {
			UMLArray o = (UMLArray) other;
			return o.getContainedType().equals(this.getContainedType());
		} else {
			return false;
		}
	}

}
