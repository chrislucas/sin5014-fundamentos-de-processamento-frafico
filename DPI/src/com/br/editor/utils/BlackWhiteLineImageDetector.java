package com.br.editor.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by C.Lucas on 14/05/2017.
 */
public class BlackWhiteLineImageDetector {


    enum PixelColorLine {
        BLACK(Color.BLACK), WHITE(Color.WHITE);
        private Color color;
        PixelColorLine(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }

    private BufferedImage image;
    private PixelColorLine pixelColorLine;
    private int width, height;
    private Map<Pixel, List<Pixel>> graph;
    private FiltersToRGB filtersToRGB;
    private boolean [][] pixelVisited;

    public BlackWhiteLineImageDetector(BufferedImage image, PixelColorLine pixelColorLine) {
        this.image  = image;
        this.width  = image.getWidth();
        this.height = image.getHeight();
        this.pixelColorLine = pixelColorLine;
        this.graph          = new LinkedHashMap<>()/*new TreeMap<>()*/;
        this.filtersToRGB   = new FiltersToRGB(image);
        this.pixelVisited   = new boolean[width][height];
    }

    private static class Pixel /* implements Comparable<Pixel> */{
        int h, w;
        Pixel ancestor;
        public Pixel(int h, int w, Pixel ancestor) {
            this.h = h;
            this.w = w;
            this.ancestor = ancestor;
        }
/*
        @Override
        public int compareTo(Pixel that) {
            return (this.h < that.h) && (this.w < that.w) ? 0 : 1;
        }
        */
    }

    public List<Pixel> neighbors(Pixel current, Pixel parent, PixelColorLine colorline) {
        List<Pixel> pixels = new ArrayList<>();
        int hs [] = {1,-1,0,0,-1,1,-1,1};
        int ws [] = {0,0,1,-1,-1,-1,1,1};
        int limitW = image.getWidth(), limitH = image.getHeight();
        int cw = current.w, ch = current.h;
        for(int i=0; i<Math.min(ws.length, hs.length); i++) {
            // limite da imagem
            if(ch + hs[i] < 0 || ch + hs[i] > limitH || cw + ws[i] < 0 || cw + ws[i] > limitW)
                continue;
            // se estiver dentro do limite da imamge. pegue a cor do pixel
            Color colorPixelNeighbor = new Color(image.getRGB(cw + ws[i], ch + hs[i]));
            // se o pixel for da cor que eu quero (No caso do exercicio PRETO) adiciona na lista de vizinhos
            if(colorline.getColor().equals(colorPixelNeighbor)) {
                Pixel pixel = new Pixel(ch + hs[i], cw + ws[i], parent);
                pixels.add(pixel);
            }
        }
        return pixels;
    }

    /**
     * Algoritmo recursivo para navegar pela imagem e pegar os pixels de interesse
     * */
    public void buildTree(int initH, int initW, Pixel parent) {
        for(int h=initH; h<height; h++) {
            for(int w=initW; w<width; w++) {
                int c = this.image.getRGB(w, h);
                Color pixelColor = new Color(c);
                // se o pixel for da cor que me interessa e eu nao tiver visitado-o ainda
                // passe por ele
                if(pixelColorLine.getColor().equals(pixelColor) && pixelVisited[w][h] == false) {
                    pixelVisited[w][h] = true;      // marcar como visitado
                    //System.out.printf("(%d %d)\n", w, h);
                    if(parent == null) {
                        parent = new Pixel(h, w, null);
                    }
                    Pixel current = new Pixel(h, w, parent);
                    if(graph.size() > 0 && graph.containsKey(parent)) {
                        List<Pixel> pixels = graph.get(parent);
                        pixels.add(current);
                    }
                    else {
                        graph.put(parent, new ArrayList<>());
                    }
                    // find 8 neighbors
                    List<Pixel> nbs = neighbors(current, parent, pixelColorLine);
                    for(Pixel pixel : nbs) {
                        buildTree(0/*pixel.h*/, 0/*pixel.w*/, parent);
                    }
                }
            }
        }
    }

    public void navigationInImage() {
        for(int h=0; h<height; h++) {
            for(int w=0; w<width; w++) {
                int c = this.image.getRGB(h, w);
                Color pixelColor = new Color(c);
                if(pixelColorLine.getColor().equals(pixelColor)) {
                    System.out.printf("(%d %d)\n", h, w);
                }
            }
        }
    }

    public void navigationInImage(BufferedImage bufferedImage) {
        for(int h=0; h<height; h++) {
            for(int w=0; w<width; w++) {
                int c = this.image.getRGB(h, w);
                Color pixelColor = new Color(c);
                if(pixelColorLine.getColor().equals(pixelColor)) {
                    System.out.printf("(%d %d)\n", h, w);
                }
            }
        }
    }

    public BufferedImage applyHorizontalLineDetection() {
        BufferedImage bufferedImage = this.filtersToRGB.applyMask(MaskFilterDefault.MaskLineDetection.maskHorinzontal, "FiltroDeteccaoLinhaHorizontal", "jpg");
        return bufferedImage;
    }


    public BufferedImage applyVerticalLineDetection() {
        BufferedImage bufferedImage = this.filtersToRGB.applyMask(MaskFilterDefault.MaskLineDetection.maskVertical, "FiltroDeteccaoLinhaVertical", "jpg");
        return bufferedImage;
    }

    private double slope(Pixel a, Pixel b) {
        double diffX = (b.w - a.w);
        double diffY = (b.h - a.h);
        if(diffX == 0.0 || diffY == 0)
            return 0.0;
        return  diffY/diffX;
    }

    public void printLines() {
        for(Map.Entry<Pixel, List<Pixel>> pair : graph.entrySet()) {
            Pixel parent =  pair.getKey();
            System.out.printf("Ponto (%d, %d)\n", parent.h, parent.w);
            List<Pixel> pixels  = pair.getValue();
            Pixel lastPixel     = pixels.get(pixels.size() - 1);
            System.out.printf("Ponto (%d, %d)\n", lastPixel.h, lastPixel.w);
            /*
            for(int i=0; i<pixels.size(); i++) {
                Pixel pixel = pixels.get(i);
                System.out.printf("Ponto (%d, %d) ", pixel.h, pixel.w);
            }
            */
            double ans = slope(parent, lastPixel);
            System.out.printf("Coeficiente angular: %f\n", ans);
            if(ans == 0.0) {
                int diffX = (lastPixel.w - parent.w);
                int diffY = (lastPixel.h - parent.h);
                if(diffX == 0) {
                    System.out.println("Vertical");
                }
                else if (diffY == 0) {
                    System.out.println("Horizontal");
                }
            }

            else {
                System.out.println("Diagonal");
            }
        }
    }

    public static void main(String[] args) {
        try {
            String  path [] = {
                 "images/exercise8/"
                ,"images/exercise8/meusexemplos/"
            };
            String ext [] = {"bmp", "png"};
            BufferedImage bufferedImage = ImageIO.read(new File(String.format("%s%s%s", path[1], "inclinada.", ext[1])));
            BlackWhiteLineImageDetector bw = new BlackWhiteLineImageDetector(bufferedImage, PixelColorLine.BLACK);
            //bw.navigationInImage();
            System.out.println("Executando");
            bw.buildTree(0, 0, null);
            bw.printLines();
            System.out.println("Fim");
            //bw.navigationInImage(bw.applyHorizontalLineDetection());
            //bw.navigationInImage(bw.applyVerticalLineDetection());
        } catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
    }

}
