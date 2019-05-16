package com.farmershao.stock.trade.utils;

import java.io.File;

public class DllLoader {

    public static String LIB_PATH;
    public static String[] dlls = new String[]{
            "TradeX-M.dll"
    };

    private static final String BIN_LIB = "";

    /**
     * 加载classpath中的dll文件类库
     * @param type 0适用于web，1反之
     */
    public static void preload(int type) {
//        URL url = DllLoader.class.getResource("DllLoader.class");
//        String path = url.toString();
//        if (path.startsWith("file:/")) {
//            String pkg = DllLoader.class.getCanonicalName();
//            String classesPath = path.substring(6, path.length() - pkg.length() - 7);
//            LIB_PATH = (new File(classesPath)).getAbsolutePath();
//        } else if (path.startsWith("jar:file:/")) {
//            String jarPath = path.substring(0, path.lastIndexOf("!"));
//            if (type == 0) {
//                LIB_PATH = (new File(jarPath)).getAbsolutePath();
//            } else if (type == 1) {
//                LIB_PATH = (new File(jarPath)).getParentFile().getAbsolutePath();
//            }
//        } else {
//            throw new IllegalStateException("无法获取系统路径信息！");
//        }
//
//        System.out.println(LIB_PATH);
//
//        for (String lib : dlls) {
//            /*int i = LIB_PATH.indexOf("lib");
//            String a = LIB_PATH.substring(0, i);
//            System.out.println(a);
//            a += File.separator + "classes" + File.separator + lib;
//            System.out.println(a);
//            File file1 = new File(a);*/
//            File file1 = new File(LIB_PATH + File.separator + lib);
//            File file2 = new File(LIB_PATH + File.separator + ".." + File.separator + "src" + File.separator +
//                    "main" + File.separator + "ref" + File.separator + "dll" + File.separator + lib);
//            if (file1.exists()) {
//                copyLicenseToWorkingDir(file1);
//                System.load(file1.getAbsolutePath());
//            }
//            if (file2.exists()) {
//                copyLicenseToWorkingDir(file2);
//                System.load(file2.getAbsolutePath());
//            }
//        }
        System.load("c:" + File.separator + "hq" + File.separator + "TradeX-M.dll");

    }

    private static void copyLicenseToWorkingDir(File dllFile) {
        File workingDir = new File(System.getProperty("user.dir"));
        File parent = dllFile.getParentFile();
        String dllFileName = dllFile.getName();
        int p = dllFileName.lastIndexOf('.');
        if (p > 0) {
            String licFileName = dllFileName.substring(0, dllFileName.lastIndexOf('.')) + ".lic";
            File licFile = new File(parent, licFileName);
            if (licFile.exists()) {
                FileUtils.copy(licFile, new File(workingDir, licFileName));
            }
        }
    }
}
