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
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.resolution.declarations.ResolvedFieldDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedParameterDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.types.ResolvedPrimitiveType;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import io.github.m4x1m3.coffeeleaf.loader.ILoader;
import io.github.m4x1m3.coffeeleaf.loader.Loader;
import io.github.m4x1m3.coffeeleaf.model.UMLModel;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLAccessLevel;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLArray;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLClass;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLClassType;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLConstructor;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLField;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLGeneric;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLMethod;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLParameter;
import io.github.m4x1m3.coffeeleaf.model.cls.UMLTemplateClass;
import io.github.m4x1m3.coffeeleaf.model.pkg.UMLPackage;
import io.github.m4x1m3.coffeeleaf.model.pri.Primitives;
import io.github.m4x1m3.coffeeleaf.model.rel.UMLRelation;
import io.github.m4x1m3.coffeeleaf.model.rel.UMLRelationDirection;
import io.github.m4x1m3.coffeeleaf.model.rel.UMLRelationType;
import net.md_5.bungee.config.Configuration;

/**
 * Load from Java sourcecode
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
@Loader("java")
public class JavaLoader implements ILoader {

	boolean loadUse = false;

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
		if (type.isPrimitive()) {
			return getPrimitiveType(type.asPrimitive());
		} else if (type.isArray()) {
			return new UMLArray(this.getType(type.asArrayType().getComponentType(), model));
		} else if (type.isVoid()) {
			return Primitives.VOID;
		} else if (type.isReferenceType()) {
			if (type.asReferenceType().getTypeDeclaration().isPresent()) {
				ResolvedReferenceTypeDeclaration decl = type.asReferenceType().getTypeDeclaration().get();
				return model.findClassOrCreateTemplate(model.findPackageOrCreate(decl.getPackageName()),
						decl.getName());
			}
		}

		return Primitives.UNKNOWN;
	}

	@Override
	public Set<UMLModel> load() {

		UMLModel model = new UMLModel("defaut");

		CombinedTypeSolver cts = new CombinedTypeSolver();
		cts.add(new JavaParserTypeSolver("../core/src/main/java"));
		cts.add(new ReflectionTypeSolver(false));

		StaticJavaParser.getConfiguration().setSymbolResolver(new JavaSymbolSolver(cts));

		try {
			for (Path path : Files.walk(Paths.get("../core/src/main/java")).filter(p -> p.toString().endsWith("java"))
					.filter(Files::isRegularFile).toList()) {

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

							for (TypeParameter generic : classdec.getTypeParameters()) {
								clazz.addGeneric(new UMLGeneric(generic.getNameAsString()));
							}

							for (FieldDeclaration field : classdec.getFields()) {
								ResolvedFieldDeclaration decfield = field.resolve();
								UMLAccessLevel fieldlevel = getAccessLevel(field.getAccessSpecifier());

								UMLField umlfield = new UMLField(decfield.getName(),
										this.getType(decfield.getType(), model), fieldlevel, field.isFinal(),
										field.isStatic());

								UMLClass c = this.getType(decfield.getType(), model);

								if (loadUse) {
									if (c.getClass().equals(UMLClass.class)
											|| c.getClass().equals(UMLTemplateClass.class)) {
										model.addRelation(new UMLRelation(clazz, c, UMLRelationType.USE,
												UMLRelationDirection.UP));
									}
								}

								clazz.addField(umlfield);
							}

							for (MethodDeclaration method : classdec.getMethods()) {
								ResolvedMethodDeclaration decmethod = method.resolve();
								UMLAccessLevel methodlevel = getAccessLevel(method.getAccessSpecifier());

								UMLMethod umlmethod = new UMLMethod(decmethod.getName(),
										this.getType(decmethod.getReturnType(), model), methodlevel,
										method.isAbstract(), method.isStatic(), method.isFinal());

								for (Parameter param : method.getParameters()) {
									ResolvedParameterDeclaration decparam = param.resolve();

									UMLParameter umlparam = new UMLParameter(this.getType(decparam.getType(), model),
											param.getNameAsString(), decparam.isVariable(), umlmethod);
									umlmethod.addParam(umlparam);
								}

								clazz.addMethod(umlmethod);
							}

							for (ConstructorDeclaration constructor : classdec.getConstructors()) {
								UMLAccessLevel constructorlevel = getAccessLevel(constructor.getAccessSpecifier());

								UMLConstructor umlconstructor = new UMLConstructor(constructorlevel);

								for (Parameter param : constructor.getParameters()) {
									ResolvedParameterDeclaration decparam = param.resolve();

									UMLParameter umlparam = new UMLParameter(this.getType(decparam.getType(), model),
											param.getNameAsString(), decparam.isVariable(), umlconstructor);
									umlconstructor.addParam(umlparam);
								}

								clazz.addConstructor(umlconstructor);
							}

							for (ClassOrInterfaceType extend : classdec.getExtendedTypes()) {
								ResolvedReferenceType t = extend.resolve();

								UMLClass e = model.findClassOrCreateTemplate(t.getQualifiedName());
								model.addRelation(
										new UMLRelation(clazz, e, UMLRelationType.EXTENDS, UMLRelationDirection.UP));
							}

							for (ClassOrInterfaceType implement : classdec.getImplementedTypes()) {
								ResolvedReferenceType t = implement.resolve();

								UMLClass e = model.findClassOrCreateTemplate(t.getQualifiedName());
								model.addRelation(
										new UMLRelation(clazz, e, UMLRelationType.IMPLEMENTS, UMLRelationDirection.UP));
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

	@Override
	public void config(Configuration conf) {
		// TODO Auto-generated method stub
		
	}

}
