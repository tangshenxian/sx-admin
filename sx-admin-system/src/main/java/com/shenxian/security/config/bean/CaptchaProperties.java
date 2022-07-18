package com.shenxian.security.config.bean;

import com.shenxian.exception.BadConfigurationException;
import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.Objects;

/**
 * @author: shenxian
 * @date: 2022/7/16 16:09
 */
@Data
public class CaptchaProperties {

    /**
     * 验证码配置
     */
    private CaptchaTypeEnum codeType;
    /**
     * 验证码有效期 分钟
     */
    private Long expiration = 2L;
    /**
     * 验证码内容长度
     */
    private int length = 2;
    /**
     * 验证码宽度
     */
    private int width = 111;
    /**
     * 验证码高度
     */
    private int height = 36;
    /**
     * 验证码字体
     */
    private String fontName;
    /**
     * 字体大小
     */
    private int fontSize = 25;

    /**
     * 获取验证码生产类
     *
     * @return /
     */
    public Captcha getCaptcha() {
        if (Objects.isNull(codeType)) {
            this.setCodeType(CaptchaTypeEnum.ARITHMETIC);
        }
        return switchCaptcha(this);
    }

    private Captcha switchCaptcha(CaptchaProperties properties) {
        Captcha captcha;
        switch (properties.getCodeType()) {
            case ARITHMETIC:
                captcha = new FixedArithmeticCaptcha(properties.getWidth(), properties.getHeight());
                // 几位数运算，默认两位
                captcha.setLen(properties.getLength());
                break;
            case CHINESE:
                captcha = new ChineseCaptcha(properties.getWidth(), properties.getHeight());
                captcha.setLen(properties.getLength());
                break;
            case CHINESE_GIF:
                captcha = new ChineseGifCaptcha(properties.getWidth(), properties.getHeight());
                captcha.setLen(properties.getLength());
                break;
            case GIF:
                captcha = new GifCaptcha(properties.getWidth(), properties.getHeight());
                captcha.setLen(properties.getLength());
                break;
            case SPEC:
                captcha = new SpecCaptcha(properties.getWidth(), properties.getHeight());
                captcha.setLen(properties.getLength());
                break;
            default:
                throw new BadConfigurationException("验证码配置信息错误！请查看正确配置 CaptchaTypeEnum");
        }
        if (StringUtils.isNotBlank(properties.getFontName())) {
            captcha.setFont(new Font(properties.getFontName(), Font.PLAIN, properties.getFontSize()));
        }
        return captcha;
    }

    static class FixedArithmeticCaptcha extends ArithmeticCaptcha {

        public FixedArithmeticCaptcha(int width, int height) {
            super(width, height);
        }

        @Override
        protected char[] alphas() {
            // 生成随机数字和运算符
            int n1 = num(1, 10), n2 = num(1, 10);
            int opt = num(3);

            // 计算结果
            int res = new int[]{n1 + n2, n1 - n2, n1 * n2}[opt];
            // 转换为字符运算符
            char optChar = "+-x".charAt(opt);

            this.setArithmeticString(String.format("%s%c%s=?", n1, optChar, n2));
            this.chars = String.valueOf(res);

            return chars.toCharArray();
        }
    }

}
