package com.farmershao.stock.trade.util;

import java.io.File;
import java.net.URL;

public class DllLoader {
    public static String LIB_PATH;
    public static String[] dlls = new String[]{
            //"mfc42.dll",
            //"mfc100.dll",
            //"msvcr100.dll",
            //"msvcrt.dll",
            "TradeX2-M.dll"
    };

    private static int loaded = 0;

    public static void preload() {
        if (loaded != ((1 << dlls.length) - 1)) {
            URL url = DllLoader.class.getResource("DllLoader.class");
            String path = url.toString();
            if (path.startsWith("file:/")) {
                String pkg = DllLoader.class.getCanonicalName();
                String classesPath = path.substring(6, path.length() - pkg.length() - 7);
                LIB_PATH = (new File(classesPath)).getParentFile().getAbsolutePath();
            } else if (path.startsWith("jar:file:/")) {
                String jarPath = path.substring(10, path.lastIndexOf("!"));
                LIB_PATH = (new File(jarPath)).getParentFile().getAbsolutePath();
            } else {
                throw new IllegalStateException("无法获取系统路径信息！");
            }

            for (int i = 0; i < dlls.length; i++) {
                String lib = dlls[i];
                File file1 = new File(LIB_PATH + File.separator + lib);
                File file2 = new File(LIB_PATH + File.separator + ".." + File.separator + "src" + File.separator + "main" + File.separator + "ref" + File.separator + "dll" + File.separator + lib);
                if (file1.exists()) {
                    copyLicenseToWorkingDir(file1);
                    System.load(file1.getAbsolutePath());
                    loaded |= (1 << i);
                }
                if (file2.exists()) {
                    copyLicenseToWorkingDir(file2);
                    System.load(file2.getAbsolutePath());
                    loaded |= (1 << i);
                }
            }
        }
    }

    private static void copyLicenseToWorkingDir(File dllFile) {
//        File workingDir = new File(System.getProperty("user.dir"));
//        File parent = dllFile.getParentFile();
//        String dllFileName = dllFile.getName();
//        int p = dllFileName.lastIndexOf('.');
//        if (p > 0) {
//            String licFileName = dllFileName.substring(0, dllFileName.lastIndexOf('.')) + ".lic";
//            File licFile = new File(parent, licFileName);
//            if (licFile.exists()) {
//                FileUtils.copy(licFile, new File(workingDir, licFileName));
//            }
//        }
    }
}
