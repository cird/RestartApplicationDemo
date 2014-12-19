package restartapplicationdemo;

import java.io.File;
import javax.swing.JOptionPane;

/**
 * Demo of Java application self-restart from
 * http://cplusadd.blogspot.com.ar/2009/04/java-application-and-self-restart.html
 */
public class RestartApplicationDemo {

    public static void main(String[] args) {
        try {
               RestartApplicationDemo.restartApplication(new RestartApplicationDemo());
        } catch (IllegalArgumentException ex) {
            //exits (without restart)
        }
    }

    RestartApplicationDemo() {
        /*Show something*/
        int option = JOptionPane.showConfirmDialog(null, "This program does nothing (until you press cancel.)",
                "Doing nothing",
                JOptionPane.OK_CANCEL_OPTION);

        if (option != JOptionPane.OK_OPTION) {
            throw new IllegalArgumentException(); /*to exit*/
        }
    }

    /**
     * blog code
     */
    static public boolean restartApplication(Object classInJarFile) {

        String javaBin = System.getProperty("java.home") + "/bin/java";
        File jarFile;
        try {
            jarFile = new File(classInJarFile.getClass().getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
        } catch (Exception e) {
            return false;
        }
        /* is it a jar file? */
        if (!jarFile.getName().endsWith(".jar")) {
            return false;//no, it's a .class probably
        }
        String toExec[] = new String[]{javaBin, "-jar", jarFile.getPath()};

        try {
            Process p = Runtime.getRuntime().exec(toExec);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
        System.exit(0);
        return true;
    }

}
