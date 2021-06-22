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

/**
 * Java's primitive types
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class Primitives {
	public static final UMLPrimitive VOID = new UMLPrimitive("void");
	public static final UMLPrimitive BOOLEAN = new UMLPrimitive("boolean");
	public static final UMLPrimitive CHAR = new UMLPrimitive("char");
	public static final UMLPrimitive BYTE = new UMLPrimitive("byte");
	public static final UMLPrimitive SHORT = new UMLPrimitive("short");
	public static final UMLPrimitive INT = new UMLPrimitive("int");
	public static final UMLPrimitive LONG = new UMLPrimitive("long");
	public static final UMLPrimitive FLOAT = new UMLPrimitive("float");
	public static final UMLPrimitive DOUBLE = new UMLPrimitive("double");

	public static final UMLPrimitive UNKNOWN = new UMLPrimitive("???");
}
