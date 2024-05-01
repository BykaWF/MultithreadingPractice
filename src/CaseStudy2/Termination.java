package CaseStudy2;

import java.math.BigInteger;

public class Termination {
    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("200000"), new BigInteger("1000000")));
        thread.start();
        thread.interrupt();
    }
    private static class BlockingThread extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Exiting blocking thread");
                return;
            }
            System.out.println("Exiting normally");
        }
    }

    private static class   LongComputationTask implements Runnable{
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger power, BigInteger base) {
            this.power = power;
            this.base = base;
        }

        private BigInteger pow(BigInteger base, BigInteger power){
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)){
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("Prematurely interrupted computation");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }
        @Override
        public void run() {
            System.out.printf("%s^%s = %s%n", base, power, pow(base, power));
        }
    }
}


