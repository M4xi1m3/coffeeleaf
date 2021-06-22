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
 * Represents a Field in a class
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class UMLField {
	/**
	 * Access level of the fiels
	 */
	private UMLAccessLevel accessLevel;

	/**
	 * Whether or not the field is static
	 */
	private boolean isStatic;

	/**
	 * Whether or not the field is final
	 */
	private boolean isFinal;

	/**
	 * Class in which the field is
	 */
	private UMLClass clazz;

	/**
	 * Name of the field
	 */
	private String name;

	/**
	 * Type of the field
	 */
	private UMLClass type;

	/**
	 * Create a field
	 * 
	 * @param name        Name of the field
	 * @param type        Type of the field
	 * @param accessLevel Access level of the field
	 * @param isFinal     Whether or not the field is final
	 * @param isStatic    Whether or not the fiels is static
	 */
	public UMLField(String name, UMLClass type, UMLAccessLevel accessLevel, boolean isFinal, boolean isStatic) {
		this.name = name;
		this.type = type;
		this.accessLevel = accessLevel;
		this.isFinal = isFinal;
		this.isStatic = isStatic;
		this.clazz = null;
	}

	/**
	 * Creates uninitialized field
	 */
	public UMLField() {

	}

	/**
	 * Get the name of the field
	 * 
	 * @return Name of the field
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the type of the field
	 * 
	 * @return Type of the field
	 */
	public UMLClass getType() {
		return type;
	}

	/**
	 * Get the access level of the field
	 * 
	 * @return Access level of the field
	 */
	public UMLAccessLevel getAccessLevel() {
		return accessLevel;
	}

	/**
	 * Is the field static ?
	 * 
	 * @return True if the field is static, false otherwise
	 */
	public boolean isStatic() {
		return isStatic;
	}

	/**
	 * Is the field final ?
	 * 
	 * @return True if the field is final, false otherwise
	 */
	public boolean isFinal() {
		return isFinal;
	}

	/**
	 * Get the class of the field
	 * 
	 * @return
	 */
	public UMLClass getClazz() {
		return clazz;
	}

	/**
	 * Set the access level of the field
	 * 
	 * @param accessLevel New access level
	 */
	public void setAccessLevel(UMLAccessLevel accessLevel) {
		this.accessLevel = accessLevel;
	}

	/**
	 * Set the field static
	 * 
	 * @param isAbstract True if the field is static, false otherwise
	 */
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	/**
	 * Set the field final
	 * 
	 * @param isAbstract True if the field is final, false otherwise
	 */
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	/**
	 * Set the class of the field
	 * 
	 * @param clazz New class
	 */
	public void setClazz(UMLClass clazz) {
		this.clazz = clazz;
	}

	/**
	 * Set the name of the field
	 * 
	 * @param name New name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set the type of the field
	 * 
	 * @param type New type
	 */
	public void setType(UMLClass type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash * 31 + this.name.hashCode();
		hash = hash * 31 + this.clazz.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof UMLField) {
			UMLField o = (UMLField) other;
			return o.name.equals(this.name) && o.clazz.equals(this.clazz);
		} else {
			return false;
		}
	}
}
