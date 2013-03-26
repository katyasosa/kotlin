/*
 * Copyright 2010-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.generators.runtime;

import com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.jet.utils.ExceptionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class GenerateFunctions {
    public static final int MAX_PARAM_COUNT = 22;
    public static final File JET_SRC_DIR = new File("compiler/frontend/src/jet/");
    public static final File RUNTIME_SRC_DIR = new File("runtime/src/jet/");

    private enum FunctionKind {
        FUNCTION("Function", false),
        EXTENSION_FUNCTION("ExtensionFunction", true);

        private final String classNamePrefix;
        private final boolean hasReceiverParameter;

        private FunctionKind(String classNamePrefix, boolean hasReceiverParameter) {
            this.classNamePrefix = classNamePrefix;
            this.hasReceiverParameter = hasReceiverParameter;
        }

        public String getJetFileName() {
            return classNamePrefix + "s.jet";
        }

        public String getClassName(int i) {
            return classNamePrefix + i;
        }

        public String getImplClassName(int i) {
            return classNamePrefix + "Impl" + i;
        }
    }

    private static void generateBuiltInFunctions(PrintWriter out, int count, FunctionKind kind) {
        generated(out);
        for (int i = 0; i <= count; i++) {
            out.print("public trait " + kind.getClassName(i));
            generateTypeParameters(out, i, kind, true);
            out.println(" {");
            generateKotlinInvokeSignature(out, i, kind);
            out.println("}");
        }
    }

    private static void generateKotlinInvokeSignature(PrintWriter out, int i, FunctionKind kind) {
        out.print("    public fun " + (kind.hasReceiverParameter ? "T." : "") + "invoke(");
        for (int j = 1; j <= i; j++) {
            out.print("p" + j + ": " + "P" + j);
            if (j < i) {
                out.print(", ");
            }
        }
        out.println(") : R");
    }

    private static void generateRuntimeFunction(PrintWriter out, int i, FunctionKind kind) {
        generateRuntimeClassHeader(out);

        out.println("import org.jetbrains.jet.rt.annotation.AssertInvisibleInResolver;");
        out.println();
        out.println("@AssertInvisibleInResolver");

        out.print("public interface " + kind.getClassName(i));
        generateTypeParameters(out, i, kind, false);
        out.println(" {");
        generateJavaInvokeSignature(out, i, kind);
        out.println("}");
    }

    private static void generateJavaInvokeSignature(PrintWriter out, int i, FunctionKind kind) {
        out.print("    R invoke(");
        if (kind.hasReceiverParameter) {
            out.print("T receiver");
            if (i > 0) {
                out.print(", ");
            }
        }
        for (int j = 1; j <= i; j++) {
            out.print("P" + j + " p" + j);
            if (j < i) {
                out.print(", ");
            }
        }
        out.println(");");
    }

    private static void generateTypeParameters(PrintWriter out, int i, FunctionKind kind, boolean kotlinVariance) {
        out.print("<");
        if (kind.hasReceiverParameter) {
            if (kotlinVariance) out.print("in ");
            out.print("T, ");
        }
        for (int j = 1; j <= i; j++) {
            if (kotlinVariance) out.print("in ");
            out.print("P" + j + ", ");
        }
        if (kotlinVariance) out.print("out ");
        out.print("R>");
    }

    private static void generateRuntimeFunctionImpl(PrintWriter out, int i, FunctionKind kind) {
        generateRuntimeClassHeader(out);

        out.print("public abstract class " + kind.getImplClassName(i));
        generateTypeParameters(out, i, kind, false);
        out.print(" extends DefaultJetObject");
        out.print(" implements " + kind.getClassName(i));
        generateTypeParameters(out, i, kind, false);
        out.println(" {");
        generateToStringForFunctionImpl(out);
        out.println("}");
    }

    private static void generateToStringForFunctionImpl(PrintWriter out) {
        out.println("    @Override");
        out.println("    public String toString() {");
        out.println("        return getClass().getGenericSuperclass().toString();");
        out.println("    }");
    }

    private static void generateRuntimeClassHeader(PrintWriter out) {
        try {
            out.println(FileUtil.loadFile(new File("injector-generator/copyright.txt")));
        }
        catch (IOException e) {
            ExceptionUtils.rethrow(e);
        }
        out.println("package jet;");
        out.println();
    }

    private static void generated(PrintWriter out) {
        out.println("// Generated by " + GenerateFunctions.class.getName());
        out.println();
        out.println("package jet");
        out.println();
    }

    public static void main(String[] args) throws FileNotFoundException {
        assert JET_SRC_DIR.exists() : "jet.* src dir does not exist: " + JET_SRC_DIR.getAbsolutePath();
        assert RUNTIME_SRC_DIR.exists() : "Runtime src dir does not exist: " + RUNTIME_SRC_DIR.getAbsolutePath();

        for (FunctionKind kind : FunctionKind.values()) {
            PrintWriter functions = new PrintWriter(new File(JET_SRC_DIR, kind.getJetFileName()));
            generateBuiltInFunctions(functions, MAX_PARAM_COUNT, kind);
            functions.close();
            
            for (int i = 0; i <= MAX_PARAM_COUNT; i++) {
                PrintWriter function = new PrintWriter(new File(RUNTIME_SRC_DIR, kind.getClassName(i) + ".java"));
                generateRuntimeFunction(function, i, kind);
                function.close();

                PrintWriter functionImpl = new PrintWriter(new File(RUNTIME_SRC_DIR, kind.getImplClassName(i) + ".java"));
                generateRuntimeFunctionImpl(functionImpl, i, kind);
                functionImpl.close();
            }
        }
    }

    private GenerateFunctions() {
    }
}
