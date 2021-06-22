/**
 * Copyright 2021 Maxime "M4x1m3" FRIESS
 *
 * This file is part of CoffeeLeaf.
 *
 * CoffeeLeaf is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CoffeeLeaf is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CoffeeLeaf.  If not, see <https://www.gnu.org/licenses/>.
 */
package io.github.m4x1m3.coffeeleaf.model.pri;

import io.github.m4x1m3.coffeeleaf.model.cls.UMLAccessLevel;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLClass;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLClassType;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLConstructor;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLField;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLMethod;
import io.github.m4x1m3.coffeeleaf.model.pkg.UMLPackage;

/**
 * Represents a java primitive (int, double, ...)
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public final class UMLPrimitive extends UMLClass {
	UMLPrimitive(String name) {
		super(name, UMLAccessLevel.PUBLIC, UMLClassType.CLASS, true);
	}
	
	/**
	 * Get the full name if the class
	 * 
	 * The full name of the class is set to be packagename.classname
	 * 
	 * @return The full name of the class
	 */
	public String getFullName() {
		return this.getName();
	}

	/**
	 * Add a constructor to the class
	 * 
	 * @param c Constructor to add
	 */
	public void addConstructor(UMLConstructor c) {
		throw new RuntimeException("Can't add a constructor to a primitive type!");
	}

	/**
	 * Add a method to the class
	 * 
	 * @param meth Method to add
	 */
	public void addMethod(UMLMethod meth) {
		throw new RuntimeException("Can't add a method to a primitive type!");
	}

	/**
	 * Add a field to the class
	 * 
	 * @param field Field to add
	 */
	public void addField(UMLField field) {
		throw new RuntimeException("Can't add a field to a primitive type!");
	}

	/**
	 * Set the name of the class
	 * 
	 * @param name New name
	 */
	public void setName(String name) {
		throw new RuntimeException("Can't set the name of a primitive type!");
	}

	/**
	 * Set the parent package of the class
	 * 
	 * @param parent New parent
	 */
	public void setParent(UMLPackage parent) {
		throw new RuntimeException("Can't set parent of a primitive type!");
	}

	/**
	 * Set the access level of the class
	 * 
	 * @param accessLevel New access level
	 */
	public void setAccessLevel(UMLAccessLevel accessLevel) {
		throw new RuntimeException("Can't set access level a primitive type!");
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash * 31 + this.getName().hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof UMLPrimitive) {
			UMLPrimitive o = (UMLPrimitive) other;
			return o.getName().equals(this.getName());
		} else {
			return false;
		}
	}
}
