package com.lizhi.miaosha.util;

import com.lizhi.miaosha.dto.VerifyCodeDTO;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 生成验证码工具类
 *
 * @author xulizhi-lenovo
 * @date 2019/6/6
 */
public class VerifyCodeUtil {

    /**
     * 操作符数组
     */
    private static char[] operators = new char[] {'+', '-', '*'};

    public static VerifyCodeDTO createVerifyCode(){

        VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO();

        int width = 80;
        int height = 32;
        //create the image
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        // set the background color
        graphics.setColor(new Color(0xDCDCDC));
        graphics.fillRect(0, 0, width, height);
        // draw the border
        graphics.setColor(Color.black);
        graphics.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random random = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            graphics.drawOval(x, y, 0, 0);
        }
        // 生成计算公式
        String formula = generateFormula(random);
        graphics.setColor(new Color(0, 100, 0));
        graphics.setFont(new Font("Candara", Font.BOLD, 24));
        graphics.drawString(formula, 8, 24);
        graphics.dispose();
        verifyCodeDTO.setBufferedImage(bufferedImage);
        verifyCodeDTO.setVerifyCodeResult(calculateResult(formula));
        return verifyCodeDTO;
    }

    /**
     * 生成字符串格式的计算公式
     * @param random
     * @return
     */
    private static String generateFormula(Random random) {
        int firstNumber = random.nextInt(10);
        int secondNumber = random.nextInt(10);
        int thirdNumber  = random.nextInt(10);
        char firstOperator = operators[random.nextInt(3)];
        char secondOperator = operators[random.nextInt(3)];
        String formula = ""+ firstNumber + firstOperator + secondNumber + secondOperator + thirdNumber;
        return formula;
    }

    /**
     * 计算验证码上的公式并返回结果
     * @param formula
     * @return
     */
    private static int calculateResult(String formula) {
        try {
            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
            return (Integer)scriptEngine.eval(formula);
        }catch(ScriptException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
