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
package io.github.m4x1m3.coffeeleaf.loader.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedFieldDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.types.ResolvedPrimitiveType;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import io.github.m4x1m3.coffeeleaf.loader.ILoader;
import io.github.m4x1m3.coffeeleaf.model.UMLModel;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLAccessLevel;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLArray;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLClass;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLClassType;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLField;
import io.github.m4x1m3.coffeeleaf.model.pkg.UMLPackage;
import io.github.m4x1m3.coffeeleaf.model.pri.Primitives;

/**
 * Load from Java sourcecode
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
public class JavaLoader implements ILoader {

	private UMLAccessLevel getAccessLevel(AccessSpecifier a) {
		switch (a) {
		case PACKAGE_PRIVATE:
			return UMLAccessLevel.PACKAGE;
		case PRIVATE:
			return UMLAccessLevel.PRIVATE;
		case PROTECTED:
			return UMLAccessLevel.PROTECTED;
		default:
			return UMLAccessLevel.PUBLIC;
		}
	}

	private UMLClassType getClassType(ClassOrInterfaceDeclaration d) {
		if (d.isAnnotationDeclaration())
			return UMLClassType.ANNOTATION;
		if (d.isInterface())
			return UMLClassType.INTERFACE;
		if (d.isAbstract())
			return UMLClassType.ABSTRACT;
		if (d.isEnumDeclaration())
			return UMLClassType.ENUM;
		return UMLClassType.CLASS;
	}

	private UMLClass getPrimitiveType(ResolvedPrimitiveType t) {
		switch (t) {
		case BOOLEAN:
			return Primitives.BOOLEAN;
		case BYTE:
			return Primitives.BYTE;
		case CHAR:
			return Primitives.CHAR;
		case DOUBLE:
			return Primitives.DOUBLE;
		case FLOAT:
			return Primitives.FLOAT;
		case INT:
			return Primitives.INT;
		case LONG:
			return Primitives.LONG;
		case SHORT:
			return Primitives.SHORT;
		default:
			return Primitives.UNKNOWN;
		}
	}

	private UMLClass getType(ResolvedType type, UMLModel model) {
		System.out.println(type);

		if (type.isPrimitive()) {
			return getPrimitiveType(type.asPrimitive());
		} else if (type.isArray()) {
			return new UMLArray(this.getType(type.asArrayType().getComponentType(), model));
		} else if (type.isVoid()) {
			return Primitives.VOID;
		} else if (type.isReferenceType()) {
			if (type.asReferenceType().getTypeDeclaration().isPresent()) {
				ResolvedReferenceTypeDeclaration decl = type.asReferenceType().getTypeDeclaration().get();
				return model.findClassOrCreateTemplate(model.findPackageOrCreate(decl.getPackageName()), decl.getName());
			}
		}

		return Primitives.UNKNOWN;
	}

	@Override
	public Set<UMLModel> load() {

		UMLModel model = new UMLModel("defaut");

		CombinedTypeSolver cts = new CombinedTypeSolver();
		cts.add(new JavaParserTypeSolver("../../../eclipse/Fourmis/src/main/java"));
		cts.add(new ReflectionTypeSolver(true));

		StaticJavaParser.getConfiguration().setSymbolResolver(new JavaSymbolSolver(cts));

		try {
			for (Path path : Files.walk(Paths.get("../../../eclipse/Fourmis/src/main/java"))
					.filter(p -> p.toString().endsWith("java")).filter(Files::isRegularFile).toList()) {

				try {
					// Load the java file and parse it
					CompilationUnit unit = null;
					System.err.println("[SCANNING] " + path.toString());

					try {
						unit = StaticJavaParser.parse(path);
					} catch (ParseProblemException e2) {
						System.err.println("[WARNING] Error occured: " + e2.getMessage().split("\n")[0]);
						continue;
					}

					// Get the package name
					String packageName = "";

					if (unit.getPackageDeclaration().isPresent())
						packageName = unit.getPackageDeclaration().get().getNameAsString();

					UMLPackage pkg = model.findPackageOrCreate(packageName);

					// Get the primary type and scan it
					if (unit.getPrimaryType().isPresent()) {
						TypeDeclaration<?> type = unit.getPrimaryType().get();
						if (type.isClassOrInterfaceDeclaration()) {
							ClassOrInterfaceDeclaration classdec = type.asClassOrInterfaceDeclaration();
							UMLAccessLevel classlevel = getAccessLevel(type.getAccessSpecifier());
							UMLClassType classtype = getClassType(classdec);
							UMLClass clazz = new UMLClass(type.getNameAsString(), classlevel, classtype,
									classdec.isFinal());
							pkg.addClass(clazz);

							for (FieldDeclaration field : classdec.getFields()) {
								ResolvedFieldDeclaration decfield = field.resolve();
								UMLAccessLevel fieldlevel = getAccessLevel(field.getAccessSpecifier());

								UMLField umlfield = new UMLField(decfield.getName(),
										this.getType(decfield.getType(), model), fieldlevel, field.isFinal(),
										field.isStatic());
								clazz.addField(umlfield);
							}
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HashSet<UMLModel> models = new HashSet<UMLModel>();

		models.add(model);

		return models;
	}

}
