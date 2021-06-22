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
 * Represents a method in a class
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class UMLMethod {
	/**
	 * Access level of the method
	 */
	private UMLAccessLevel accessLevel;

	/**
	 * Whether or not the method is abstract
	 */
	private boolean isAbstract;

	/**
	 * Whether or not the method is static
	 */
	private boolean isStatic;

	/**
	 * Whether or not the method is final
	 */
	private boolean isFinal;

	/**
	 * Class in which the method is
	 */
	private UMLClass clazz;

	/**
	 * Name of the method
	 */
	private String name;

	/**
	 * Parameters of the method
	 */
	private List<UMLParameter> params;

	/**
	 * Return type of the method
	 */
	private UMLClass returnType;

	/**
	 * Create a new method
	 * 
	 * @param name        Name of the method
	 * @param returnType  Return type of the method
	 * @param accessLevel Access level of the method
	 * @param isAbstract  Is the method abstract?
	 * @param isStatic    Is the method static?
	 * @param isFinal     Is the method final?
	 */
	public UMLMethod(String name, UMLClass returnType, UMLAccessLevel accessLevel, boolean isAbstract, boolean isStatic,
			boolean isFinal) {
		this.name = name;
		this.returnType = returnType;
		this.params = new ArrayList<UMLParameter>();
		this.accessLevel = accessLevel;
		this.isAbstract = isAbstract;
		this.isStatic = isStatic;
		this.isFinal = isFinal;
		this.clazz = null;
	}

	/**
	 * Created uninitialized method
	 */
	public UMLMethod() {

	}

	/**
	 * Get the name of the method
	 * 
	 * @return The name of the method
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the parameters of the method
	 * 
	 * @return Parameters of the method
	 */
	public List<UMLParameter> getParams() {
		return params;
	}

	/**
	 * Get the return type of the method
	 * 
	 * @return The return type of the method
	 */
	public UMLClass getReturnType() {
		return returnType;
	}

	/**
	 * Get the access level of the method
	 * 
	 * @return The access evel of the method
	 */
	public UMLAccessLevel getAccessLevel() {
		return accessLevel;
	}

	/**
	 * Is the method abstract?
	 * 
	 * @return True if the method is abstract, false otherwise
	 */
	public boolean isAbstract() {
		return isAbstract;
	}

	/**
	 * Is the method static?
	 * 
	 * @return True if the method is static, false otherwise
	 */
	public boolean isStatic() {
		return isStatic;
	}

	/**
	 * Is the method final?
	 * 
	 * @return True if the method is final, false otherwise
	 */
	public boolean isFinal() {
		return isFinal;
	}

	/**
	 * Get the class of the method
	 * 
	 * @return The class of the method
	 */
	public UMLClass getClazz() {
		return clazz;
	}

	/**
	 * Add a parameter
	 * 
	 * @param param Name of the parameter
	 */
	public void addParam(UMLParameter param) {
		this.params.add(param);
	}

	/**
	 * Set the class of the method
	 * 
	 * @param clazz New class
	 */
	public void setClazz(UMLClass clazz) {
		this.clazz = clazz;
	}

	/**
	 * Set the access level of the method
	 * 
	 * @param accessLevel New access level
	 */
	public void setAccessLevel(UMLAccessLevel accessLevel) {
		this.accessLevel = accessLevel;
	}

	/**
	 * Set the method abstract
	 * 
	 * @param isAbstract True if the method is abstract, false otherwise
	 */
	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	/**
	 * Set the method static
	 * 
	 * @param isAbstract True if the method is static, false otherwise
	 */
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	/**
	 * Set the method final
	 * 
	 * @param isAbstract True if the method is final, false otherwise
	 */
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	/**
	 * Set the name of the method
	 * 
	 * @param name New name of the method
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set the return type of the method
	 * 
	 * @param returnType New return type
	 */
	public void setReturnType(UMLClass returnType) {
		this.returnType = returnType;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash * 31 + this.name.hashCode();
		hash = hash * 31 + this.clazz.hashCode();
		hash = hash * 31 + this.params.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof UMLMethod) {
			UMLMethod o = (UMLMethod) other;
			return o.name.equals(this.name) && o.params.equals(this.params) && o.clazz.equals(this.clazz);
		} else {
			return false;
		}
	}
}
