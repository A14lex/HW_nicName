import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    /*
    сгенерированное слово является палиндромом, т. е. читается одинаково как слева направо, так и справа налево, например, abba;
    сгенерированное слово состоит из одной и той же буквы, например, aaa;
    буквы в слове идут по возрастанию: сначала все a (при наличии), затем все b (при наличии), затем все c и т. д. Например, aaccc.
     */
    public static AtomicInteger nic3 = new AtomicInteger(0);
    public static AtomicInteger nic4 = new AtomicInteger(0);
    public static AtomicInteger nic5 = new AtomicInteger(0);


    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];//по условию должно быть 100000
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Runnable palindrom = () -> {

            for (String s : texts) {
                boolean b = true;
                String toBingo = s;

                while (b) {
                    if (s.charAt(0) == s.charAt(s.length() - 1)) {
                        s = s.substring(1, s.length() - 1);

                        if (s.length() == 0 || s.length() == 1) {
                            break;
                        }
                    } else {
                        b = false;//как только условие не соблюдается
                    }

                }
                if (b) {
                    bingo(toBingo);//вызываем метод, который и увеличивает соответствующий счетчик
                }


            }


        };
        Runnable oneSymbol = () -> {
            for (String s : texts) {

                String toBingo = s;
                boolean b = true;
                while (b) {
                    if (s.length() == 1) {
                        break;
                    }
                    if (s.charAt(0) != s.charAt(1)) {
                        b = false;
                    } else {
                        s = s.substring(1);

                    }

                }
                if (b) {
                    bingo(s);
                }


            }


        };
        Runnable escalation = () -> {
            for (String s : texts) {
                String toBingo = s;

                boolean b = true;
                while (b) {
                    if (s.length() == 1) {
                        break;
                    }
                    if (s.charAt(0) > s.charAt(1)) {
                        b = false;
                    } else {
                        s = s.substring(1);

                    }


                }
                if (b) {
                    bingo(toBingo);
                }

            }


        };
        Thread thread1 = new Thread(escalation);
        thread1.start();
        Thread thread2 = new Thread(escalation);
        thread2.start();
        Thread thread3 = new Thread(escalation);
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        System.out.println("Красивых слов длиной 3 есть: " + nic3);
        System.out.println("Красивых слов длиной 4 есть: " + nic4);
        System.out.println("Красивых слов длиной 5 есть: " + nic5);


    }

    private static void bingo(String s) {
        if (s.length() == 3) {

            //увеличение nic3 на 1
            nic3.addAndGet(1);
        }
        if (s.length() == 4) {
            //увеличение nic4 на 1
            nic4.addAndGet(1);
        }
        if (s.length() == 5) {
            //увеличение nic5 на 1
            nic5.addAndGet(1);
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
