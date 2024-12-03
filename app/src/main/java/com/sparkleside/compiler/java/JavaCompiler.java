package com.sparkleside.compiler.java;

import com.sparkleside.App;
import com.sparkleside.utils.Decompress;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.internal.compiler.batch.Main;

public final class JavaCompiler {

  private static final List<String> logs = new ArrayList<>();
  private static final BinaryExecutor executor = new BinaryExecutor();

  public static void compile(CompileItem compileItem) {
    logs.clear();
    if (!compileItem.getJavaFile().exists()
        || !compileItem.getJavaFile().getName().endsWith(".java")) {
      newLog("Invalid file: " + compileItem.getJavaFile().getAbsolutePath());
      return;
    }

    var errorWriter = new StringWriter();
    var errWriter = new PrintWriter(errorWriter);

    var outputWriter = new StringWriter();
    var outWriter = new PrintWriter(outputWriter);

    var outputDir = new File(compileItem.getOutputDir(), "classes/bin/");
    if (!outputDir.exists() && !outputDir.mkdirs()) {
      newLog("failed create output directory: " + outputDir.getAbsolutePath());
      return;
    }

    var args = new ArrayList<String>();
    args.add("-1.8");
    args.add("-proc:none");
    args.add("-nowarn");
    args.add("-d");
    args.add(outputDir.getAbsolutePath());
    args.add("-sourcepath");
    args.add(" ");
    args.add(compileItem.getJavaFile().getAbsolutePath());
    args.add("-cp");
    args.add(getLibs());

    var compiler = new Main(outWriter, errWriter, false, null, null);
    var success = compiler.compile(args.toArray(new String[0]));

    if (!success) {
      newLog("failed to compile:\n" + errorWriter);
      return;
    }

    newLog("compiled with successful:\n" + outputWriter);
    run(outputDir);
  }

  private static void run(File outputDir) {
    var className = "Main";
    executor.setCommands(List.of("java", "-cp", outputDir.getAbsolutePath(), className));
    var runResult = executor.execute();
    newLog("Result:\n" + runResult);
  }

  public static final void newLog(final String log) {
    logs.add(log);
  }

  public static final List<String> getLogs() {
    return logs;
  }

  private static final String getLibs() {
    var libs = new StringBuilder();
    libs.append(getAndroidJarFile().getAbsolutePath());
    libs.append(":");
    libs.append(getLambdaFactoryFile().getAbsolutePath());
    return libs.toString();
  }

  private static final File getAndroidJarFile() {
    var ctx = App.getContext();
    var androidJar = new File(ctx.getFilesDir() + "/temp/android.jar");

    if (androidJar.exists()) {
      return androidJar;
    }

    Decompress.unzipFromAssets(
        ctx, "android.jar.zip", androidJar.getParentFile().getAbsolutePath());

    return androidJar;
  }

  private static final File getLambdaFactoryFile() {
    var ctx = App.getContext();
    var lambdaFactory = new File(ctx.getFilesDir() + "/temp/core-lambda-stubs.jar");

    if (lambdaFactory.exists()) {
      return lambdaFactory;
    }

    Decompress.unzipFromAssets(
        ctx, "core-lambda-stubs.zip", lambdaFactory.getParentFile().getAbsolutePath());

    return lambdaFactory;
  }

  public static final class CompileItem {
    private final File javaFile;
    private final File outputDir;

    public CompileItem(final File javaFile, final File outputDir) {
      this.javaFile = javaFile;
      this.outputDir = outputDir;
    }

    public File getJavaFile() {
      return javaFile;
    }

    public File getOutputDir() {
      return outputDir;
    }
  }
}
