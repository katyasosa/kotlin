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

package org.jetbrains.jet.compiler.android;

import com.google.common.io.Files;
import com.intellij.openapi.util.io.FileUtil;
import junit.framework.TestSuite;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jet.compiler.CodegenTestsOnAndroidRunner;
import org.jetbrains.jet.compiler.PathManager;

import java.io.File;


public class AndroidRunner extends TestSuite {

    private static PathManager pathManager;

    @NotNull
    public static PathManager getPathManager() {
        if (pathManager == null) {
            File tmpFolder = Files.createTempDir();
            System.out.println("Created temporary folder for running android tests: " + tmpFolder.getAbsolutePath());
            File rootFolder = new File("");
            pathManager = new PathManager(rootFolder.getAbsolutePath(), tmpFolder.getAbsolutePath());
        }
        return pathManager;
    }

    public static TestSuite suite() throws Throwable {
        PathManager pathManager = getPathManager();

        FileUtil.copyDir(new File(pathManager.getAndroidModuleRoot()), new File(pathManager.getTmpFolder()));
        
        CodegenTestsOnAndroidGenerator.generate(pathManager);

        System.out.println("Run tests on android...");
        TestSuite suite = CodegenTestsOnAndroidRunner.getTestSuite(pathManager);
        suite.addTest(new AndroidJpsBuildTestCase());
        return suite;
    }

    public void tearDown() throws Exception {
        // Clear tmp folder where we run android tests
        FileUtil.delete(new File(pathManager.getTmpFolder()));
    }
}
