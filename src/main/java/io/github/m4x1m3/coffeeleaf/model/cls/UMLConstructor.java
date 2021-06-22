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
package io.github.m4x1m3.coffeeleaf.model.cls;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a constructor of a class
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class UMLConstructor {
	/**
	 * The access level of the constructor
	 */
	private UMLAccessLevel accessLevel;

	/**
	 * The class the constructors belongs to
	 */
	private UMLClass clazz;

	/**
	 * List of parameters of the constructor
	 */
	private List<UMLParameter> params;

	/**
	 * Create a new constructor
	 * 
	 * @param accessLevel Access level of the constructor
	 */
	public UMLConstructor(UMLAccessLevel accessLevel) {
		this.accessLevel = accessLevel;
		this.clazz = null;
		this.params = new ArrayList<UMLParameter>();
	}

	/**
	 * Create uninitialized constructor
	 */
	public UMLConstructor() {

	}

	/**
	 * Set the access level of the constructor
	 * 
	 * @param accessLevel New access level
	 */
	public void setAccessLevel(UMLAccessLevel accessLevel) {
		this.accessLevel = accessLevel;
	}

	/**
	 * Set the class of the constructor
	 * 
	 * @param clazz Class of the constructor
	 */
	public void setClazz(UMLClass clazz) {
		this.clazz = clazz;
	}

	/**
	 * Get the access level of the constructor
	 * 
	 * @return Access level of the constructor
	 */
	public UMLAccessLevel getAccessLevel() {
		return accessLevel;
	}

	/**
	 * Get the class of the constructor
	 * 
	 * @return The class of the constructor
	 */
	public UMLClass getClazz() {
		return clazz;
	}

	/**
	 * Get the parameters of the constructor
	 * 
	 * @return List of parameters
	 */
	public List<UMLParameter> getParams() {
		return params;
	}

	/**
	 * Add a parameter to the constructor
	 * 
	 * @param umlParameter Parameter to add
	 */
	public void addParam(UMLParameter umlParameter) {
		this.params.add(umlParameter);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash * 31 + this.params.hashCode();
		hash = hash * 31 + this.clazz.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof UMLConstructor) {
			UMLConstructor o = (UMLConstructor) other;
			return o.params.equals(this.params) && o.clazz.equals(this.clazz);
		} else {
			return false;
		}
	}
}
