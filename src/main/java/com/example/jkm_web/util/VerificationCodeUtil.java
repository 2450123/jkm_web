package com.example.jkm_web.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerificationCodeUtil {
    /**
     * 生成验证码
     *
     * @param length 验证码的长度
     * @return 验证码字符串
     */
    public static String getVerificationCode(int length) {
        char[] codes = { //除去容易混淆的0,o,1,l,I
                '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
                'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z'};
        //一共57个
        //a、b、c、d、e、f、g、h、i、j、k、l、m、n、o、p、q、r、s、t、u、v、w、x、y、z
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            //math.random()函数是取从0至1之间的随机数,即取0至codes.length-1之间的随机数
            int r = (int) (Math.random() * codes.length);
            result[i] = codes[r];
        }
        return String.valueOf(result);
    }

    //验证码长宽
    private static final int CAPTCHA_HEIGHT = 35;
    private static final int CAPTCHA_WIDTH = 100;
    private static final Random r = new Random();
    private static final String[] fontNames = {"宋体", "楷体", "隶书"};

    public static BufferedImage createImage(String verificationCode) {
        BufferedImage image = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(new Color(255, 255, 255));//背景颜色
        g2.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);//填充背景色的矩形

        for (int i = 0; i < verificationCode.length(); i++) {
            String s = verificationCode.charAt(i) + "";
            float x = i * 1.0F * CAPTCHA_WIDTH / 4 + 7F;
            g2.setFont(randomFont());
            g2.setColor(randomColor());
            g2.drawString(s, x, CAPTCHA_HEIGHT - 7);
        }
        drawLine(image);
        return image;
    }

    private static Color randomColor() {
        int red = r.nextInt(150);
        int green = r.nextInt(150);
        int blue = r.nextInt(150);
        return new Color(red, green, blue);
    }

    private static Font randomFont() {
        int index = r.nextInt(fontNames.length);
        String fontName = fontNames[index];
        int style = r.nextInt(4);
        int size = r.nextInt(5) + 24;
        return new Font(fontName, style, size);
    }

    private static void drawLine(BufferedImage image) {
        int num = 5;
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {
            int x1 = r.nextInt(CAPTCHA_WIDTH);
            int y1 = r.nextInt(CAPTCHA_HEIGHT);
            int x2 = r.nextInt(CAPTCHA_WIDTH);
            int y2 = r.nextInt(CAPTCHA_HEIGHT);
            g2.setStroke(new BasicStroke(1.5F));
            g2.setColor(randomColor());
            g2.drawLine(x1, y1, x2, y2);
        }
    }
}
