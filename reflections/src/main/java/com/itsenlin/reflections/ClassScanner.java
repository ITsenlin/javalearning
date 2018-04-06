package com.itsenlin.reflections;

import java.io.File;
import java.io.FileFilter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ClassScanner {
  private static final String CLASS_SUFFIX = ".class";
  private static final ClassLoader loader = ClassScanner.class.getClassLoader();

  /**
   * 通过类全名，获取相应的Class对象。
   * @param classFullName，例如java.lang.String
   * @return 获取classFullName相应的Class对象
   */
  public Class<?> getClassFromName(String classFullName) {
    Class<?> clazz = null;
    try {
      clazz = Class.forName(classFullName);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return clazz;
  }

  /**
   *
   * 获取clazz同路径下的所有类的Class对象
   * @param clazz, classpath下存在的类的Class对象
   * @return 获取clazz所在路径下所有class文件的Class对象，以Map形式返回
   */
  public Map<String, Class<?>> getClasses(Class<?> clazz) {
    return getClasses(clazz, false);
  }

  /**
   *
   * @param clazz classpath下存在的类的Class对象
   * @param recursion 是否要递归扫描，即clazz所在路径的子目录
   * @return 所有满足要求的clazz集合
   */
  public Map<String, Class<?>> getClasses(Class<?> clazz, final boolean recursion) {
    final String pkgName = clazz.getPackage().getName();
    Map<String, Class<?>> classes = new HashMap<String, Class<?>>();

    getClasses(pkgName, recursion, classes);

    return classes;
  }


  private void getClasses(String pkg, final boolean recursion, Map<String, Class<?>> map) {

    URI uri = null;
    try {
      uri = loader.getResource(pkg.replace(".", "/")).toURI();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    File dir = new File(uri);
    File[] files = dir.listFiles();
    for (File file : files) {
      if (file.isDirectory() && recursion) {
        getClasses(pkg + "." + file.getName(), true, map);
      } else if (file.isFile() && file.getName().endsWith(CLASS_SUFFIX)) {
        String name = pkg + "." + file.getName().replace(CLASS_SUFFIX, "");
        Class<?> clazz = null;
        try {
          clazz = Class.forName(name);
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
        map.put(file.getAbsolutePath(), clazz);
      }
    }
  }

}
