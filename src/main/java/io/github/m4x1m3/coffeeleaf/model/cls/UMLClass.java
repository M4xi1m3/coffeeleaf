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

import java.util.ArrayList;
import java.util.List;

import io.github.m4x1m3.coffeeleaf.model.pkg.UMLPackage;

/**
 * Represents a class in the UML
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class UMLClass {
	/**
	 * Name of the class
	 */
	private String name;

	/**
	 * Package in which the class is
	 */
	private UMLPackage parent;

	/**
	 * Access level of the class
	 */
	private UMLAccessLevel accessLevel;

	/**
	 * Type of the class
	 */
	private UMLClassType classType;

	/**
	 * Whether or not the class is private
	 */
	private boolean isFinal;

	/**
	 * List of methods in the class
	 */
	private List<UMLMethod> methods;

	/**
	 * List of constructors in the class
	 */
	private List<UMLConstructor> constructors;

	/**
	 * List of fields in the class
	 */
	private List<UMLField> fields;

	/**
	 * Create an uninitialized class
	 */
	public UMLClass() {

	}

	/**
	 * Constructor
	 * 
	 * @param name        Name of the class
	 * @param accessLevel Access level of the class
	 * @param classType   Type of the class
	 * @param isFinal     Whether or not the class is final
	 */
	public UMLClass(String name, UMLAccessLevel accessLevel, UMLClassType classType,
			boolean isFinal) {
		this.name = name;
		this.parent = null;
		this.accessLevel = accessLevel;
		this.classType = classType;
		this.isFinal = isFinal;
		this.methods = new ArrayList<UMLMethod>();
		this.constructors = new ArrayList<UMLConstructor>();
		this.fields = new ArrayList<UMLField>();
	}

	/**
	 * Is the class final ?
	 * 
	 * @return True if the class is final, false otherwise
	 */
	public boolean isFinal() {
		return isFinal;
	}

	/**
	 * Get the name of the class
	 * 
	 * @return The name of the class
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the full name if the class
	 * 
	 * The full name of the class is set to be packagename.classname
	 * 
	 * @return The full name of the class
	 */
	public String getFullName() {
		String pname = parent.getFullName();
		return (pname.length() == 0 ? "" : pname + ".") + this.getName();
	}

	/**
	 * Get the package containing the class
	 * 
	 * @return The package containing the class
	 */
	public UMLPackage getParent() {
		return parent;
	}

	/**
	 * Get the access level of the class
	 * 
	 * @return The access level of the class
	 */
	public UMLAccessLevel getAccessLevel() {
		return accessLevel;
	}

	/**
	 * Get the type of the class
	 * 
	 * @return The type of the class
	 */
	public UMLClassType getClassType() {
		return classType;
	}

	/**
	 * Get the fields of the class
	 * 
	 * @return The fields list
	 */
	public List<UMLField> getFields() {
		return fields;
	}

	/**
	 * Get the methods of the class
	 * 
	 * @return The methods list
	 */
	public List<UMLMethod> getMethods() {
		return methods;
	}

	/**
	 * Get the constructors of the class
	 * 
	 * @return The constructors list
	 */
	public List<UMLConstructor> getConstructors() {
		return constructors;
	}

	/**
	 * Add a constructor to the class
	 * 
	 * @param c Constructor to add
	 */
	public void addConstructor(UMLConstructor c) {
		c.setClazz(this);
		this.constructors.add(c);
	}

	/**
	 * Add a method to the class
	 * 
	 * @param meth Method to add
	 */
	public void addMethod(UMLMethod meth) {
		meth.setClazz(this);
		this.methods.add(meth);
	}

	/**
	 * Add a field to the class
	 * 
	 * @param field Field to add
	 */
	public void addField(UMLField field) {
		field.setClazz(this);
		this.fields.add(field);
	}

	/**
	 * Set the name of the class
	 * 
	 * @param name New name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set the parent package of the class
	 * 
	 * @param parent New parent
	 */
	public void setParent(UMLPackage parent) {
		this.parent = parent;
	}

	/**
	 * Set the access level of the class
	 * 
	 * @param accessLevel New access level
	 */
	public void setAccessLevel(UMLAccessLevel accessLevel) {
		this.accessLevel = accessLevel;
	}

	/**
	 * Set the class type
	 * 
	 * @param classType New class type
	 */
	public void setClassType(UMLClassType classType) {
		this.classType = classType;
	}

	/**
	 * Set whether or not the class if final
	 * 
	 * @param isFinal True if the class is final, false otherwise
	 */
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash * 31 + this.name.hashCode();
		hash = hash * 31 + this.parent.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof UMLClass) {
			UMLClass o = (UMLClass) other;
			return o.name.equals(this.name) && o.parent.equals(this.parent);
		} else {
			return false;
		}
	}
}
