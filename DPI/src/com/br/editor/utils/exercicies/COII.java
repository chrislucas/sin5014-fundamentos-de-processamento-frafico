package com.br.editor.utils.exercicies;

import com.br.editor.utils.FiltersToRGB;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by C.Lucas on 28/05/2017.
 */
public class COII {


    private int h, w;
    private int [][] binaryImage;
    private int [][] components;

    public static class Pixel {
        int h, w;
        public Pixel(int h, int w) {
            this.h = h;
            this.w = w;
        }
    }

    public void dfs(int nh, int nw, int counter) {
        components[nh][nw] = counter;
        ArrayList<Pixel> pixels = neighbors(new Pixel(nh, nw), 0);
        for(Pixel pixel : pixels) {
            nh = pixel.h;
            nw = pixel.w;
            dfs(nh, nw, counter);
        }
    }

    public int bfs(int nh, int nw) {
        int counter = 0;
        Stack<Pixel> pixels = new Stack<>();
        for (int i = nh; i <h ; i++) {
            for (int j = nw; j <w ; j++) {
                if(components[i][j] == 0 && binaryImage[i][j] == 0) {
                    for(Pixel pixel : neighbors(new Pixel(i, j), 0)) {
                        nh = pixel.h;
                        nw = pixel.w;
                        components[nh][nw] = counter;
                        pixels.add(pixel);
                    }
                    while(!pixels.isEmpty()) {
                        Pixel neighbor = pixels.pop();
                        for(Pixel pixel : neighbors(neighbor, 0)) {
                            nh = pixel.h;
                            nw = pixel.w;
                            components[nh][nw] = counter;
                            pixels.add(pixel);
                        }
                    }
                }
            }
            counter++;
        }
        return counter;
    }

    public int run(int nh, int nw, int counter) {
        for (int i = nh; i <h ; i++) {
            for (int j = nw; j <w ; j++) {
                if(components[i][j] == 0 && binaryImage[i][j] == 0) {
                    dfs(i, j, ++counter);
                }
            }
        }
        return counter;
    }

    public ArrayList<Pixel> neighbors(Pixel current, int targetColor) {
        ArrayList<Pixel> pixels = new ArrayList<>();
        int hs [] = {1,-1,0,0,-1,1,-1,1};
        int ws [] = {0,0,1,-1,-1,-1,1,1};
        int limitW = w, limitH = h;
        int cw = current.w, ch = current.h;
        for(int i=0; i<Math.min(ws.length, hs.length); i++) {
            // limite da imagem
            int newH = ch + hs[i], newW = cw + ws[i];
            if(newH < 0 || newH > limitH || newW < 0 || newW > limitW)
                continue;
            //lugar ja visitado
            else if(components[newH][newW] > 0)
                continue;
            // se estiver dentro do limite da imamge. pegue a cor do pixel
            int c = binaryImage[newH][newW];
            // se o pixel for da cor que eu quero (No caso do exercicio PRETO) adiciona na lista de vizinhos
            if(c == targetColor) {
                Pixel pixel = new Pixel(newH, newW);
                pixels.add(pixel);
            }
        }
        return pixels;
    }

    public BufferedImage toBinary(BufferedImage bufferedImage) {
        int w = bufferedImage.getWidth(), h = bufferedImage.getHeight();
        BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        binaryImage = new int[h][w];
        components  = new int[h][w];
        for(int i=0; i<h; i++) {
            for (int j = 0; j <w ; j++) {
                Color c = new Color(bufferedImage.getRGB(j, i));
                int r = c.getRed(), g = c.getGreen(), b = c.getBlue();
                int mid = (r+g+b)/3;
                mid = mid < 0 ? 0 : mid > 255 ? 255 : mid;
                Color newColor = mid < 200 ? new Color(0,0,0) : new Color(255, 255, 255);
                binaryImage[i][j] = mid < 230 ? 0 : 1;
                newImage.setRGB(j, i, newColor.getRGB());
            }
        }
        return newImage;
    }

    private void open() {
        try {
            BufferedImage image = ImageIO.read(new File("images/exercise8/objetos/4objetos.bmp"));
            System.out.println("Executando");
            BufferedImage newImage = toBinary(image);
            FiltersToRGB.createImage(newImage, "images/exercise8/resultado/4objetosbinarizado.jpg", "jpg");
            h = newImage.getHeight();
            w = newImage.getWidth();
            components = new int[h][w];
            int counter = run(14, 47, 0);
            System.out.printf("%d componentes", counter);
        }
        catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
    }

    public static void main(String[] args) {
        new COII().open();
    }

}
