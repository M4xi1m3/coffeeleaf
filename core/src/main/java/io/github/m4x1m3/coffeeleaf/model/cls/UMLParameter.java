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

/**
 * Represents a parameter in a method or in a constructor.
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class UMLParameter {
	/**
	 * Type of the parameter
	 */
	private UMLClass type;

	/**
	 * Name of the parameter
	 */
	private String name;

	/**
	 * Is it a variable arg ?
	 */
	private boolean variable;

	/**
	 * Method to which the parameter belongs to. Null if belongs to constructor.
	 */
	private UMLMethod method;

	/**
	 * Constructor to which the parameter belongs to. Null if belongs to method.
	 */
	private UMLConstructor constructor;

	public UMLParameter(UMLClass type, String name, boolean variable, UMLMethod method) {
		this.type = type;
		this.name = name;
		this.variable = variable;
		this.method = method;
		this.constructor = null;
	}

	public UMLParameter(UMLClass type, String name, boolean variable, UMLConstructor constructor) {
		this.type = type;
		this.name = name;
		this.variable = variable;
		this.constructor = constructor;
		this.method = null;
	}

	/**
	 * Get the type
	 * 
	 * @return The type
	 */
	public UMLClass getType() {
		return type;
	}

	/**
	 * Get the name
	 * 
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Is it variable ?
	 * 
	 * @return True if it's a variable argument, false otherwise
	 */
	public boolean isVariable() {
		return variable;
	}

	/**
	 * Get the method
	 * 
	 * @return The method
	 */
	public UMLMethod getMethod() {
		return method;
	}

	/**
	 * Get the constructor
	 * 
	 * @return The constructor
	 */
	public UMLConstructor getConstructor() {
		return constructor;
	}

	/**
	 * Set the type
	 *
	 * @param type The new type
	 */
	public void setType(UMLClass type) {
		this.type = type;
	}

	/**
	 * Set the name
	 *
	 * @param name The new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Mark the parameter as a variable argument
	 *
	 * @param variable True if the parameter is a variable argument, false otherwise
	 */
	public void setVariable(boolean variable) {
		this.variable = variable;
	}

	/**
	 * Set the method
	 *
	 * @param method The new method
	 */
	public void setMethod(UMLMethod method) {
		this.method = method;
		this.constructor = null;
	}

	/**
	 * Set the constructor
	 *
	 * @param constructor The new constructor
	 */
	public void setConstructor(UMLConstructor constructor) {
		this.constructor = constructor;
		this.method = null;
	}

}
