package com.farmershao.stock.trade.util;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;



/**
 * Created by kongkp on 16-8-26.
 */
public final class FileUtils {

    public static String readText(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        return StreamUtils.readText(is);
    }

    public static InputStream load(String filePath) throws IOException {
        InputStream is = null;

        if (filePath.startsWith("classpath:")) {
            String path = filePath.substring(10);
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            while (cl != null) {
                is = cl.getResourceAsStream(path);
                if (is == null) {
                    cl = cl.getParent();
                } else {
                    break;
                }
            }
        } else {
            is = new FileInputStream(filePath);
        }

        return is;
    }

    public static File touch(File file) {
        if (!file.exists()) {
            try {
                ensureDir(file.getParentFile());
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        } else {
            file.setLastModified(System.currentTimeMillis());
        }
        return file;
    }

    public static File touch(String filePath) {
        return touch(new File(filePath));
    }

    public static boolean ensureDir(File dir) {
        assert dir != null;
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                return true;
            } else {
                System.err.println("创建文件失败");
            }
        }
        return dir.isDirectory();
    }

    public static boolean ensureDir(String dir) {
        assert dir != null;
        return ensureDir(new File(dir));
    }

    public static boolean overwrite(String filePath, String text) {
        return overwrite(new File(filePath), text);
    }

    public static boolean overwrite(File file, String text) {
        return overwrite(file, text.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean overwrite(File file, byte[] bytes) {
        InputStream is = new ByteArrayInputStream(bytes);
        return overwrite(file, is);
    }

    public static boolean overwrite(File file, InputStream is) {
        OutputStream os = null;
        try {
            touch(file);
            os = new FileOutputStream(file);
            StreamUtils.fromTo(is, os, true, true);
            return true;
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return false;
    }

    public static boolean copy(File src, File dst) {
        try {
            InputStream is = new FileInputStream(src);
            OutputStream os = new FileOutputStream(dst);
            StreamUtils.fromTo(is, os, true, true);
            return true;
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return false;
    }

    public static boolean move(File src, File dst) {
        if (ensureDir(dst.getParentFile())) {
            if (src.renameTo(dst)) {
                return true;
            }
        }
        return false;
    }

    public static boolean move(String src, String dst) {
        return move(new File(src), new File(dst));
    }


    public static boolean nativeDelete(String file) {
        try {
            Runtime runtime = Runtime.getRuntime();
            String[] args = null;
            if (OsInfo.isWindows()) {
                args = new String[] {"cmd.exe", "/c", "rd " + file + " /q /s"};
            } else if (OsInfo.isLinux()) {
                args = new String[] {"sh", "-c", "rm -f -r " + file};
            }
            if (args != null) {
                Process proc = runtime.exec(args);
                proc.waitFor();
                proc.destroy();
                return proc.exitValue() == 0;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return false;
    }

    public static class OsInfo {
        private static final String osName;

        static {
            String osn = System.getProperty("os.name");
            osName = osn == null ? "" : osn.toLowerCase();
        }

        public static boolean isLinux() {
            return osName.indexOf("linux") >= 0;
        }

        public static boolean isMacOS() {
            return osName.indexOf("mac") >= 0 && osName.indexOf("os") > 0 && osName.indexOf("x") < 0;
        }

        public static boolean isMacOSX() {
            return osName.indexOf("mac") >= 0 && osName.indexOf("os") > 0 && osName.indexOf("x") > 0;
        }

        public static boolean isWindows() {
            return osName.indexOf("windows") >= 0;
        }

        public static boolean isOS2() {
            return osName.indexOf("os/2") >= 0;
        }

        public static boolean isSolaris() {
            return osName.indexOf("solaris") >= 0;
        }

        public static boolean isSunOS() {
            return osName.indexOf("sunos") >= 0;
        }

        public static boolean isMPEiX() {
            return osName.indexOf("mpe/ix") >= 0;
        }

        public static boolean isHPUX() {
            return osName.indexOf("hp-ux") >= 0;
        }

        public static boolean isAix() {
            return osName.indexOf("aix") >= 0;
        }

        public static boolean isOS390() {
            return osName.indexOf("os/390") >= 0;
        }

        public static boolean isFreeBSD() {
            return osName.indexOf("freebsd") >= 0;
        }

        public static boolean isIrix() {
            return osName.indexOf("irix") >= 0;
        }

        public static boolean isDigitalUnix() {
            return osName.indexOf("digital") >= 0 && osName.indexOf("unix") > 0;
        }

        public static boolean isNetWare() {
            return osName.indexOf("netware") >= 0;
        }

        public static boolean isOSF1() {
            return osName.indexOf("osf1") >= 0;
        }

        public static boolean isOpenVMS() {
            return osName.indexOf("openvms") >= 0;
        }
    }

}
