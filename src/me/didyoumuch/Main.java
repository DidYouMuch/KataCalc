package me.didyoumuch;

import java.util.*;

public class Main {
    static class RomanianParseError extends Exception {
        private final String input;

        public RomanianParseError(String input) {
            this.input = input;
        }

        @Override
        public void printStackTrace() {
            System.out.println("Ошибка преобразования римского числа: " + this.input);
        }
    }

    static class ExpressionError extends Exception {
        private final String expression;

        public ExpressionError(String expression) {
            this.expression = expression;
        }

        @Override
        public void printStackTrace() {
            System.out.println("Формат математической операции не удовлетворяет заданию: " + this.expression);
        }
    }

    static class NumberSystemError extends Exception {
        private final String expression;

        public NumberSystemError(String expression) {
            this.expression = expression;
        }

        @Override
        public void printStackTrace() {
            System.out.println("Используются разные системы счисления: " + this.expression);
        }
    }

    private static final ArrayList<String> operations = new ArrayList<>(Arrays.asList("+", "-", "/", "*"));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите операцию: ");
        System.out.println(calc(scanner.nextLine()));
    }


    public static String calc(String input) {
        try {
            String[] formatted = input.split(" ");
            if (formatted.length != 3 ||
                    isNotNumber(formatted[0])
                    || isNotNumber(formatted[2])
                    || !isOperation(formatted[1])) {
                throw new ExpressionError(input);
            }
            String first = formatted[0];
            String operation = formatted[1];
            String second = formatted[2];
            if (isDec(first) && isDec(second)) {
                if (Integer.parseInt(first) > 10 || Integer.parseInt(second) > 10) throw new ExpressionError(input);
                switch (operation) {
                    case "+":
                        return String.valueOf(Integer.parseInt(first) + Integer.parseInt(second));
                    case "-":
                        return String.valueOf(Integer.parseInt(first) - Integer.parseInt(second));
                    case "/":
                        return String.valueOf(Integer.parseInt(first) / Integer.parseInt(second));
                    case "*":
                        return String.valueOf(Integer.parseInt(first) * Integer.parseInt(second));
                    default:
                        break;
                }
            } else if (isRomanian(first) && isRomanian(second)) {
                int firstInDec = romToDec(first);
                int secondInDec = romToDec(second);
                switch (operation) {
                    case "+":
                        return decToRom(firstInDec + secondInDec);
                    case "-":
                        return decToRom(firstInDec - secondInDec);
                    case "/":
                        return decToRom(firstInDec / secondInDec);
                    case "*":
                        return decToRom(firstInDec * secondInDec);
                    default:
                        break;
                }
            } else {
                throw new NumberSystemError(input);
            }


        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return "";
    }

    private static int romToDec(String rom) throws RomanianParseError {
        Map<String, Integer> values = new HashMap<>();
        values.put("I", 1);
        values.put("II", 2);
        values.put("III", 3);
        values.put("IV", 4);
        values.put("V", 5);
        values.put("VI", 6);
        values.put("VII", 7);
        values.put("VIII", 8);
        values.put("IX", 9);
        values.put("X", 10);

        if (values.get(rom) == null) {
            throw new RomanianParseError(rom);
        }

        return values.get(rom);
    }

    private static String decToRom(int dec) throws RomanianParseError {
        if (dec <= 0) throw new RomanianParseError(String.valueOf(dec));
        String ret = "";
        int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] letters = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        for (int i = 0; i < values.length; i++) {
            while (dec >= values[i]) {
                dec -= values[i];
                ret += letters[i];
            }
        }
        return ret;
    }

    private static boolean isDec(String in) {
        try {
            Integer.parseInt(in);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    private static boolean isRomanian(String in) {
        try {
            romToDec(in);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    private static boolean isNotNumber(String in) {
        return !isRomanian(in) && !isDec(in);
    }

    private static boolean isOperation(String in) {
        for (String s : operations)
            if (s.equals(in)) return true;
        return false;
    }


}
