package dev.trindadedev.compiler.java;

import android.content.Context;
import com.android.tools.r8.D8;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.internal.compiler.batch.Main;
import dalvik.system.DexClassLoader;

public final class JavaCompiler {

  private final Context context;
  private final List<String> logs = new ArrayList<>();

  public JavaCompiler(Context context) {
    this.context = context;
  }

  public final void compile(final CompileItem compileItem) {
    var executor = new BinaryExecutor();
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
    
    try {
      var inputPath = outputDir.getAbsolutePath();
      var outputPath = outputDir.getAbsolutePath() + "/classes.jar";
      var jarPackager = new JarCreator(inputPath, outputPath);
      jarPackager.create();
      
      try {
        var d8Args = new ArrayList<String>();
        d8Args.add("--output");
        d8Args.add(outputDir.getAbsolutePath());
        d8Args.add("--lib");
        d8Args.add(getAndroidJarFile().getAbsolutePath());
        d8Args.add(outputDir.getAbsolutePath() + "/classes.jar";);
        D8.main(d8Args.toArray(new String[0]));
      } catch (Exception e) {
        newLog(e.toString());
      }
    } catch (IOException e) {
      newLog(e.toString());
    }
    run(outputDir);
  }

  public final void run(final File outputDir) {
    var className = "Main";
    var optimizedDir = context.getDir("odex", Context.MODE_PRIVATE).getAbsolutePath();
    
    var dexLoader =
      new DexClassLoader(
        outputDir.getAbsolutePath() + "/classes.dex",
        optimizedDir,
        null,
        context.getClassLoader()
      );
    
    Class<?> calledClass = dexLoader.loadClass(className);
    var method = calledClass.getDeclaredMethod("main", String[].class);
    String[] param = {};
  }

  public final void newLog(final String log) {
    logs.add(log);
  }

  public final List<String> getLogs() {
    return logs;
  }

  public final String getJavaVersion() {
    var executor = new BinaryExecutor();
    executor.setCommands(List.of("java", "--version"));
    return executor.execute();
  }

  private final String getLibs() {
    var libs = new StringBuilder();
    libs.append(getAndroidJarFile().getAbsolutePath());
    libs.append(":");
    libs.append(getLambdaFactoryFile().getAbsolutePath());
    return libs.toString();
  }

  private final File getAndroidJarFile() {
    var androidJar = new File(context.getFilesDir() + "/temp/android.jar");

    if (androidJar.exists()) return androidJar;

    Decompress.unzipFromAssets(
        context, "android.jar.zip", androidJar.getParentFile().getAbsolutePath());

    return androidJar;
  }

  private final File getLambdaFactoryFile() {
    var lambdaFactory = new File(context.getFilesDir() + "/temp/core-lambda-stubs.jar");

    if (lambdaFactory.exists()) return lambdaFactory;

    Decompress.unzipFromAssets(
        context, "core-lambda-stubs.zip", lambdaFactory.getParentFile().getAbsolutePath());

    return lambdaFactory;
  }

  public static final class CompileItem {
    private final File javaFile;
    private final File outputDir;

    public CompileItem(final File javaFile, final File outputDir) {
      this.javaFile = javaFile;
      this.outputDir = outputDir;
    }

    public final File getJavaFile() {
      return javaFile;
    }

    public final File getOutputDir() {
      return outputDir;
    }
  }
}
