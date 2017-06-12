package app.methods;




import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import utils.BufferedImageUtils;

import java.io.File;

/**
 * Created by C_Luc on 11/06/2017.
 */
public class Tess4Java {

    public static void main(String[] args) {

        // System.setProperty("jna.debug_load", "true");
/*
        System.setProperty("jna.library.path", "32".equals(System.getProperty("sun.arch.data.model"))
                ? "lib/win32-x86" : "lib/win32-x86-64");
*/
        //File file = new File("images/texto_exemplo.tif");
        //File file = new File("images/placa2.tif");
        File file = new File("images/img2.png");
        ITesseract instance = new Tesseract();
        //instance.setLanguage("eng");

        //System.out.println(System.getProperty("jna.library.path"));

        try {
            File folder = LoadLibs.extractTessResources("tessdata");
            //System.out.println(LoadLibs.getTesseractLibName());
            //File folder = new File("tessdata");
            instance.setDatapath(folder.getPath());
            System.out.println(folder.getPath());
            //TessAPI api = TessAPI.INSTANCE;
            //System.out.println(api.TessVersion());
            //String result = instance.doOCR(file);
            //System.out.println(result);
            String response = instance.doOCR(BufferedImageUtils.readImage3Channels(file));
            System.out.println(response);
        }

        catch (TesseractException e) {
            System.out.println(e.getMessage());
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
