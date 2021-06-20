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
package io.github.m4x1m3.coffeeleaf.generator;

import java.io.PrintStream;
import java.util.ArrayList;

import io.github.m4x1m3.coffeeleaf.model.UMLAccessLevel;
import io.github.m4x1m3.coffeeleaf.model.UMLClass;
import io.github.m4x1m3.coffeeleaf.model.UMLClassType;
import io.github.m4x1m3.coffeeleaf.model.UMLMethod;
import io.github.m4x1m3.coffeeleaf.model.UMLModel;
import io.github.m4x1m3.coffeeleaf.model.UMLPackage;
import io.github.m4x1m3.coffeeleaf.model.UMLParameter;
import io.github.m4x1m3.coffeeleaf.model.UMLRootPackage;

/**
 * @author Maxime "M4x1m3" FRIESS
 *
 */
public class PUMLGenerator {
	private PrintStream out;
	private boolean accessLevelOnClass = false;
	private boolean repeatType = false;

	public PUMLGenerator(PrintStream out) {
		this.out = out;
	}

	public void generate(UMLModel model) {
		UMLRootPackage root = model.getRootPackage();

		root.forEachPackage(p -> generatePackage(p));
		root.forEachClass(c -> generateClasses(c));
	}

	private void generatePackage(UMLPackage pkg) {
		if (pkg.hasClasses()) {
			out.println("package " + pkg.getFullName() + " {");
			pkg.forEachPackage(p -> generatePackage(p));
			pkg.forEachClass(c -> generateClasses(c));
			out.println("}");
		} else {
			pkg.forEachPackage(p -> generatePackage(p));
		}
	}

	private String typeName(UMLClassType t) {
		switch (t) {
		case ABSTRACT:
			return "abstract";
		case ANNOTATION:
			return "annotation";
		case ENUM:
			return "enum";
		case INTERFACE:
			return "interface";
		default:
			return "class";
		}
	}

	private String accessLevelChar(UMLAccessLevel l) {
		switch (l) {
		case PRIVATE:
			return "-";
		case PACKAGE:
			return "~";
		case PROTECTED:
			return "#";
		default:
			return "+";
		}
	}

	private void generateMethod(UMLMethod met) {
		out.print(this.accessLevelChar(met.getAccessLevel()));

		if (met.isAbstract())
			out.print("{abstract} ");
		if (met.isStatic())
			out.print("{static} ");
		
		out.print(met.getName() + "(");
		
		ArrayList<String> params = new ArrayList<String>();
		
		for(UMLParameter par : met.getParams()) {
			params.add(par.getName() + ":" + par.getType() + (par.isVariable() ? "..." : ""));
		}
		
		out.print(String.join(", ", params));
		
		out.print(")");
		
		if (!met.getReturnType().equals(Void.TYPE))
			out.print(": " + met.getReturnType().getSimpleName());
		
		if (met.isFinal())
			out.print(" <<final>>");
		out.println();
	}

	private void generateClasses(UMLClass cls) {

		if (accessLevelOnClass) {
			out.print(accessLevelChar(cls.getAccessLevel()));
		}

		out.print(typeName(cls.getClassType()) + " " + cls.getName());

		if (cls.getClassType() != UMLClassType.CLASS && repeatType) {
			out.print(" <<" + typeName(cls.getClassType()) + ">>");
		}
		out.println(" {");

		for(UMLMethod met : cls.getMethods()) {
			generateMethod(met);
		}
		
		out.println("}");
	}
}
