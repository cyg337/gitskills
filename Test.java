package com;

import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请选择功能：");
        System.out.println("1. 加密密码");
        System.out.println("2. 解密密码");
        System.out.println("3. 判断密码强度");
        System.out.println("4. 生成密码");
        int choice = scanner.nextInt();//23445555556
        scanner.nextLine(); // 消耗掉换行符,防止直接跳过了下面的输入环节
        //因为nextLine读取的是 nextInt 后面的换行，nextInt读取到了空白字符就停止了，导致nextline读取的是上一行数字后面的enter。

        switch (choice) {
            case 1:
                System.out.println("请输入要加密的密码：");
                String plainPassword = scanner.nextLine();
                String encryptedPassword = encryptPassword(plainPassword);
                System.out.println("加密后的密码：" + encryptedPassword);
                break;
            case 2:
                System.out.println("请输入要解密的密码：");
                String encryptedPasswordInput = scanner.nextLine();
                String decryptedPassword = decryptPassword(encryptedPasswordInput);
                System.out.println("解密后的密码：" + decryptedPassword);
                break;
            case 3:
                System.out.println("请输入要判断强度的密码：");
                String passwordToCheck = scanner.next();
                String passwordStrength = checkPasswordStrength(passwordToCheck);
                System.out.println("密码强度为：" + passwordStrength);
                break;
            case 4:
            	String password = generatePassword(10);
                System.out.println("生成的密码：" + password);
                break;
            default:
                System.out.println("无效的选择！");
                break;
        }
        scanner.close();
    }
    //加密方法（主类中只可以定义静态方法）
    public static String encryptPassword(String plainPassword) {
        StringBuilder encryptedPassword = new StringBuilder();//可变的字符序列类（动态字符串），这里创建了一个空的动态字符串
        int length = plainPassword.length();//获取原密码的长度以便于遍历加密

        for (int i = 0; i < length; i++) {
            char c = plainPassword.charAt(i);//用于返回给定索引的字符值，数组索引从0开始
            int ascii = (int) c;//字符转ASCII
            ascii = ascii + i + 1 + 3; // ASCII码加上字符串中的位置和偏移值3
            encryptedPassword.append((char) ascii);//在动态字符串后面加上ascii
        }

        // 调换第一位和最后一位
        if (length > 1) {
            char firstChar = encryptedPassword.charAt(0);//用于返回给定索引的字符值，数组索引从0开始
            char lastChar = encryptedPassword.charAt(length - 1);
            encryptedPassword.setCharAt(0, lastChar);
            encryptedPassword.setCharAt(length - 1, firstChar);
        }

        // 反转字符串
        encryptedPassword.reverse();// StringBuilder自带反转字符串的方法

        return encryptedPassword.toString();
    }
    //解密方法
    public static String decryptPassword(String encryptedPassword) {
        StringBuilder decryptedPassword = new StringBuilder();
        int length = encryptedPassword.length();

        // 反转字符串
        encryptedPassword = new StringBuilder(encryptedPassword).reverse().toString();

        // 调换第一位和最后一位
        if (length > 1) {
            char firstChar = encryptedPassword.charAt(0);
            char lastChar = encryptedPassword.charAt(length - 1);
            //substring截取了中间部分（包括第1个，不包括第length-1个。）
            encryptedPassword = encryptedPassword.substring(1, length - 1);
            decryptedPassword.append(lastChar).append(encryptedPassword).append(firstChar);
        } else {
            decryptedPassword.append(encryptedPassword);
        }

        length = decryptedPassword.length();

        for (int i = 0; i < length; i++) {
            char c = decryptedPassword.charAt(i);
            int ascii = (int) c;
            ascii = ascii - i - 1 - 3; // 减去位置和偏移值得到原始ASCII码
            decryptedPassword.setCharAt(i, (char) ascii);
        }

        return decryptedPassword.toString();
    }
    //判断密码强度
    public static String checkPasswordStrength(String password) {
        int length = password.length();
        int uppercaseCount = 0;
        int lowercaseCount = 0;
        int digitCount = 0;
        //判断输入的密码中包含的元素数目以便于下面判断密码强度
        for (int i = 0; i < length; i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                uppercaseCount++;
            } else if (Character.isLowerCase(c)) {
                lowercaseCount++;
            } else if (Character.isDigit(c)) {
                digitCount++;
            }
        }
        //判断强度
        if (length < 8) {
            return "Weak";
        } else if (length >=8 && (uppercaseCount > 0 || lowercaseCount > 0) && digitCount > 0) {
            return "Medium";
        } else if (length >=8 && uppercaseCount > 0 && lowercaseCount > 0 && digitCount > 0) {
            return "Strong";
        } else {
            return "Medium";
        }
    }
    //生成密码，密码中的元素从以下字符串中取得
        private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
        private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final String NUMERIC_CHARACTERS = "0123456789";
        private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_-+=<>?";

        public static String generatePassword(int length) {
            StringBuilder password = new StringBuilder();
            Random random = new SecureRandom();

            // 添加至少一个小写字母
            password.append(LOWERCASE_CHARACTERS.charAt(random.nextInt(LOWERCASE_CHARACTERS.length())));

            // 添加至少一个大写字母
            password.append(UPPERCASE_CHARACTERS.charAt(random.nextInt(UPPERCASE_CHARACTERS.length())));

            // 添加至少一个数字
            password.append(NUMERIC_CHARACTERS.charAt(random.nextInt(NUMERIC_CHARACTERS.length())));

            // 添加至少一个特殊字符
            password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

            // 添加剩余的字符
            int remainingLength = length - password.length();
            for (int i = 0; i < remainingLength; i++) {
                String characters = LOWERCASE_CHARACTERS + UPPERCASE_CHARACTERS + NUMERIC_CHARACTERS + SPECIAL_CHARACTERS;
                password.append(characters.charAt(random.nextInt(characters.length())));
            }

            // 打乱密码中的字符顺序
            for (int i = password.length() - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                char temp = password.charAt(i);
                password.setCharAt(i, password.charAt(j));
                password.setCharAt(j, temp);
            }

            return password.toString();
        }
}
