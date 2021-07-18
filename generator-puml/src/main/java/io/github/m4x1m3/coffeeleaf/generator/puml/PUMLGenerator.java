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
package io.github.m4x1m3.coffeeleaf.generator.puml;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import io.github.m4x1m3.coffeeleaf.generator.Generator;
import io.github.m4x1m3.coffeeleaf.generator.IGenerator;
import io.github.m4x1m3.coffeeleaf.model.UMLModel;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLAccessLevel;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLClass;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLClassType;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLConstructor;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLField;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLGeneric;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLMethod;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLParameter;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLTemplateClass;
import io.github.m4x1m3.coffeeleaf.model.pkg.UMLPackage;
import io.github.m4x1m3.coffeeleaf.model.pkg.UMLRootPackage;
import io.github.m4x1m3.coffeeleaf.model.pri.Primitives;
import io.github.m4x1m3.coffeeleaf.model.rel.UMLRelation;
import io.github.m4x1m3.coffeeleaf.model.rel.UMLRelationDirection;
import io.github.m4x1m3.coffeeleaf.model.rel.UMLRelationType;
import net.md_5.bungee.config.Configuration;

/**
 * @author Maxime "M4x1m3" FRIESS
 *
 */
@Generator("puml")
public class PUMLGenerator implements IGenerator {
	private PrintStream out;
	private boolean accessLevelOnClass = false;
	private boolean repeatType = false;

	public PUMLGenerator() {
		this.out = System.out;
	}

	public void generate(UMLModel model) {
		UMLRootPackage root = model.getRootPackage();

		root.getSubPackages().forEach(p -> generatePackage(p));
		root.getSubClasses().forEach(c -> generateClasses(c));
		model.getRelations().forEach(r -> generateRelation(r));
	}

	private void generateRelation(UMLRelation r) {
		out.print(r.getFrom().getName());
		out.print(arrowType(r.getType(), r.getDirection()));
		out.print(r.getTo().getName());
		out.println();
	}

	private void generatePackage(UMLPackage pkg) {
		if (pkg.hasClasses()) {
			out.println("package " + pkg.getFullName() + " {");
			pkg.getSubPackages().forEach(p -> generatePackage(p));
			pkg.getSubClasses().forEach(c -> generateClasses(c));
			out.println("}");
		} else {
			pkg.getSubPackages().forEach(p -> generatePackage(p));
		}
	}

	private String arrowDirection(UMLRelationDirection direction) {
		switch (direction) {
		case DOWN:
			return "d";
		case LEFT:
			return "l";
		case RIGHT:
			return "r";
		default:
			return "u";
		}
	}

	private String arrowType(UMLRelationType type, UMLRelationDirection direction) {
		String d = arrowDirection(direction);

		switch (type) {
		case EXTENDS:
			return " -" + d + "-|> ";
		case IMPLEMENTS:
			return " ." + d + ".|> ";
		default:
			return " -" + d + "-> ";
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

	private void generateConstructor(UMLConstructor cons) {
		out.print(this.accessLevelChar(cons.getAccessLevel()));

		out.print(cons.getClazz().getName() + "(");

		ArrayList<String> params = new ArrayList<String>();

		for (UMLParameter par : cons.getParams()) {
			params.add(par.getName() + ":" + par.getType().getName() + (par.isVariable() ? "..." : ""));
		}

		out.print(String.join(", ", params));

		out.println(")");
	}

	private void generateMethod(UMLMethod met) {
		out.print(this.accessLevelChar(met.getAccessLevel()));

		if (met.isAbstract() && met.getClazz().getClassType() != UMLClassType.INTERFACE)
			out.print("{abstract} ");
		if (met.isStatic())
			out.print("{static} ");

		out.print(met.getName() + "(");

		ArrayList<String> params = new ArrayList<String>();

		for (UMLParameter par : met.getParams()) {
			params.add(par.getName() + ":" + par.getType().getName() + (par.isVariable() ? "..." : ""));
		}

		out.print(String.join(", ", params));

		out.print(")");

		if (!met.getReturnType().equals(Primitives.VOID)) {
			out.print(": " + met.getReturnType().getName());
		}

		if (met.isFinal())
			out.print(" <<final>>");
		out.println();
	}

	private void generateFields(UMLField field) {
		out.print(this.accessLevelChar(field.getAccessLevel()));

		if (field.isStatic())
			out.print("{static} ");

		out.print(field.getName());
		out.print(": " + field.getType().getName());

		if (field.isFinal())
			out.print(" <<final>>");
		out.println();
	}

	private void generateGenerics(List<UMLGeneric> generics) {
		if (generics.size() == 0)
			return;

		List<String> str = new ArrayList<String>();

		generics.forEach(g -> str.add(g.getName()));

		out.print("<" + String.join(", ", str) + ">");
	}

	private void generateClasses(UMLClass cls) {

		if (cls instanceof UMLTemplateClass) {
			out.println("entity " + cls.getName() + " {}");
			return;
		}

		if (accessLevelOnClass) {
			out.print(accessLevelChar(cls.getAccessLevel()));
		}

		out.print(typeName(cls.getClassType()) + " " + cls.getName());

		generateGenerics(cls.getGenerics());

		if (cls.getClassType() != UMLClassType.CLASS && repeatType) {
			out.print(" <<" + typeName(cls.getClassType()) + ">>");
		}
		out.println(" {");

		out.println("' Constructors");
		for (UMLConstructor cons : cls.getConstructors()) {
			generateConstructor(cons);
		}

		out.println("' Fields");
		for (UMLField field : cls.getFields()) {
			generateFields(field);
		}

		out.println("' Methods");
		for (UMLMethod met : cls.getMethods()) {
			generateMethod(met);
		}

		out.println("}");
		out.println();
	}

	@Override
	public void config(Configuration conf) {
		// TODO Auto-generated method stub
		
	}
}
