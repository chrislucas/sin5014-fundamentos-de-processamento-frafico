package com.br.editor.utils.exercicies;

import com.br.editor.utils.FiltersToRGB;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by C.Lucas on 27/05/2017.
 */
public class COII2 {

    private BufferedImage image, newImage;
    private int[][] components;
    private int w, h;


    public static class Pixel {
        int h, w;
        Pixel ancestor;

        public Pixel(int h, int w) {
            this.h = h;
            this.w = w;
        }

        public Pixel(int h, int w, Pixel ancestor) {
            this.h = h;
            this.w = w;
            this.ancestor = ancestor;
        }
    }



    public ArrayList<Pixel> neighbors(Pixel current, Color pixelColor) {
        ArrayList<Pixel> pixels = new ArrayList<>();
        int hs [] = {1,-1,0,0,-1,1,-1,1};
        int ws [] = {0,0,1,-1,-1,-1,1,1};
        int limitW = newImage.getWidth(), limitH = newImage.getHeight();
        int ch = current.h, cw = current.w;
        for(int i=0; i<Math.min(ws.length, hs.length); i++) {
            // limite da imagem
            int newH = ch + hs[i], newW = cw + ws[i];
            // dentro do limite da matriz e ainda nao visitado
            if( (newH < 0 || newH > limitH || newW < 0 || newW > limitW))
                continue;
            else if( components[newH][newW] > 0 )
                continue;
            // se estiver dentro do limite da imamge. pegue a cor do pixel
            int c = newImage.getRGB(newW, newH);
            Color colorPixelNeighbor = new Color(c);
            //int r = colorPixelNeighbor.getRed(), g = colorPixelNeighbor.getGreen(), b= colorPixelNeighbor.getBlue();
            // se o pixel for da cor que eu quero (No caso do exercicio PRETO) adiciona na lista de vizinhos
            if(pixelColor.equals(colorPixelNeighbor)) {
                Pixel pixel = new Pixel(newH, newW);
                pixels.add(pixel);
            }
        }
        return pixels;
    }


    public void dfs(int h, int w, int component) {
        components[h][w] = component;
        ArrayList<Pixel> pixels = neighbors(new Pixel(h, w), Color.BLACK);
        for(Pixel pixel : pixels) {
            int nh = pixel.h, nw = pixel.w;
            dfs(nh, nw, component);
        }
    }


    public int run(int i, int j) {
        int counter = 1;
        for (; i<h ; i++) {
            for (; j<w ; j++) {
                Color target = new Color(newImage.getRGB(j, i));
                if(components[i][j] == 0 && target.equals(Color.BLACK)) {
                    dfs(i, j, counter);
                    counter++;
                }
            }
        }
        return counter;
    }


    public BufferedImage toBinary(BufferedImage bufferedImage) {
        int w = bufferedImage.getWidth(), h = bufferedImage.getHeight();
        BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for(int i=0; i<h; i++) {
            for (int j = 0; j <w ; j++) {
                Color c = new Color(bufferedImage.getRGB(j, i));
                int r = c.getRed(), g = c.getGreen(), b = c.getBlue();
                int mid = (r+g+b)/3;
                mid = mid < 0 ? 0 : mid > 255 ? 255 : mid;
                //System.out.printf("%d ", mid < 0 ? 0 : mid);
                // binarizar
                Color newColor = mid < 200 ? new Color(0,0,0) : new Color(255, 255, 255);
                //System.out.printf("%d",  mid < 200 ? 0 : 1);
                newImage.setRGB(j, i, newColor.getRGB());
            }
            //System.out.println("");
        }
        return newImage;
    }

    private void open() {
        try {
            image = ImageIO.read(new File("images/exercise8/objetos/4objetos.bmp"));
            System.out.println("Executando");
            newImage = toBinary(image);
            FiltersToRGB.createImage(newImage, "images/exercise8/resultado/imgbin4obj.jpg", "jpg");
            h = newImage.getHeight();
            w = newImage.getWidth();
            components = new int[h][w];
            int counter = run(15, 47);
            System.out.printf("%d componentes", counter);
        }
        catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
    }

    public static void main(String[] args) {
        new COII2().open();
    }

}
