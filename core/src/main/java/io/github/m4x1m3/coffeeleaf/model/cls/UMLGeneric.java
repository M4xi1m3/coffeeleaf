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
public class UMLGeneric extends UMLClass {

	private UMLClass parentClass;

	private UMLMethod parentMethod;

	private UMLClass extendsType;
	private UMLClass superType;

	public UMLGeneric(String name) {
		super(name, UMLAccessLevel.PRIVATE, UMLClassType.CLASS, true);
	}

	/**
	 * Get the parentClass
	 *
	 * @return The parentClass
	 */
	public UMLClass getParentClass() {
		return parentClass;
	}

	/**
	 * Set the parentClass
	 *
	 * @param parentClass The new parentClass
	 */
	public void setParentClass(UMLClass parentClass) {
		this.parentMethod = null;
		this.parentClass = parentClass;
	}

	/**
	 * Get the parentMethod
	 *
	 * @return The parentMethod
	 */
	public UMLMethod getParentMethod() {
		return parentMethod;
	}

	/**
	 * Set the parentMethod
	 *
	 * @param parentMethod The new parentMethod
	 */
	public void setParentMethod(UMLMethod parentMethod) {
		this.parentClass = null;
		this.parentMethod = parentMethod;
	}

	/**
	 * Get the extendsType
	 *
	 * @return The extendsType
	 */
	public UMLClass getExtendsType() {
		return extendsType;
	}

	/**
	 * Set the extendsType
	 *
	 * @param extendsType The new extendsType
	 */
	public void setExtendsType(UMLClass extendsType) {
		this.superType = null;
		this.extendsType = extendsType;
	}

	/**
	 * Get the superType
	 *
	 * @return The superType
	 */
	public UMLClass getSuperType() {
		return superType;
	}

	/**
	 * Set the superType
	 *
	 * @param superType The new superType
	 */
	public void setSuperType(UMLClass superType) {
		this.extendsType = null;
		this.superType = superType;
	}

	/**
	 * Set the parent package of the class
	 * 
	 * @param parent New parent
	 */
	public void setParent(UMLPackage parent) {
		throw new RuntimeException("Can't set parent package of generic type!");
	}

	/**
	 * Set the access level of the class
	 * 
	 * @param accessLevel New access level
	 */
	public void setAccessLevel(UMLAccessLevel accessLevel) {
		throw new RuntimeException("Can't set access level of generic type!");
	}

	/**
	 * Set the class type
	 * 
	 * @param classType New class type
	 */
	public void setClassType(UMLClassType classType) {
		throw new RuntimeException("Can't set class type of generic type!");
	}

	/**
	 * Set whether or not the class if final
	 * 
	 * @param isFinal True if the class is final, false otherwise
	 */
	public void setFinal(boolean isFinal) {
		throw new RuntimeException("Can't set finalness of generic type!");
	}

	@Override
	public String getFullName() {
		return this.getName();
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash * 31 + this.getName().hashCode();
		hash = hash * 31 + (this.parentClass == null ? 0 : this.parentClass.hashCode());
		hash = hash * 31 + (this.parentMethod == null ? 0 : this.parentMethod.hashCode());
		hash = hash * 31 + (this.extendsType == null ? 0 : this.extendsType.hashCode());
		hash = hash * 31 + (this.superType == null ? 0 : this.superType.hashCode());
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof UMLGeneric) {
			UMLGeneric o = (UMLGeneric) other;
			return o.getName().equals(this.getName())
					&& (this.parentClass == null ? o.parentClass == null : this.parentClass.equals(o.parentClass))
					&& (this.parentMethod == null ? o.parentMethod == null : this.parentMethod.equals(o.parentMethod))
					&& (this.extendsType == null ? o.extendsType == null : this.extendsType.equals(o.extendsType))
					&& (this.superType == null ? o.superType == null : this.superType.equals(o.superType));
		} else {
			return false;
		}
	}

}
