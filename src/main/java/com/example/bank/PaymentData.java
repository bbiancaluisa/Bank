package com.example.bank;

import java.util.Scanner;

public class PaymentData implements Comparable<PaymentData> {
    private String bank;
    private String cardNo0;
    private String cardNo1;
    private String cardNo2;
    private String cardNo3;
    private String expirationDate;
    private String cardInfoDetails;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCardNo0() {
        return cardNo0;
    }

    public void setCardNo0(String cardNo0) {
        this.cardNo0 = cardNo0;
    }

    public String getCardNo1() {
        return cardNo1;
    }

    public void setCardNo1(String cardNo1) {
        this.cardNo1 = cardNo1;
    }

    public String getCardNo2() {
        return cardNo2;
    }

    public void setCardNo2(String cardNo2) {
        this.cardNo2 = cardNo2;
    }

    public String getCardNo3() {
        return cardNo3;
    }

    public void setCardNo3(String cardNo3) {
        this.cardNo3 = cardNo3;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardInfoDetails() {
        return cardInfoDetails;
    }

    public void setCardInfoDetails(String cardInfoDetails) {
        this.cardInfoDetails = cardInfoDetails;
    }

    public void generateCardInfoDetails() {
        this.cardInfoDetails = "XXXX-XXXX-XXXX-" + cardNo3;
    }

    public boolean isValid() {
        if (!(Character.getNumericValue(cardNo0.charAt(0)) == 4 ||
                Character.getNumericValue(cardNo0.charAt(0)) == 5 ||
                Character.getNumericValue(cardNo0.charAt(0)) == 6 ||
                (Character.getNumericValue(cardNo0.charAt(0)) == 3 && Character.getNumericValue(cardNo0.charAt(1)) == 7))) {
            return false;
        }

        int evenSum = 0;
        int oddSum = 0;

        evenSum += evenSumHelper(cardNo0);
        evenSum += evenSumHelper(cardNo1);
        evenSum += evenSumHelper(cardNo2);
        evenSum += evenSumHelper(cardNo3);

        oddSum += Character.getNumericValue(cardNo0.charAt(1));
        oddSum += Character.getNumericValue(cardNo0.charAt(3));
        oddSum += Character.getNumericValue(cardNo1.charAt(1));
        oddSum += Character.getNumericValue(cardNo1.charAt(3));
        oddSum += Character.getNumericValue(cardNo2.charAt(1));
        oddSum += Character.getNumericValue(cardNo2.charAt(3));
        oddSum += Character.getNumericValue(cardNo3.charAt(1));
        oddSum += Character.getNumericValue(cardNo3.charAt(3));

        return (evenSum + oddSum) % 10 == 0;
    }

    private int evenSumHelper(String number) {
        int evenSum = 0;

        if (Character.getNumericValue(number.charAt(0)) * 2 < 10) {
            evenSum += Character.getNumericValue(number.charAt(0)) * 2;
        } else {
            int partialSum = Character.getNumericValue(number.charAt(0)) * 2;

            evenSum += partialSum % 10;
            evenSum += partialSum / 10;
        }

        if (Character.getNumericValue(number.charAt(2)) * 2 < 10) {
            evenSum += Character.getNumericValue(number.charAt(2)) * 2;
        } else {
            int partialSum = Character.getNumericValue(number.charAt(2)) * 2;

            evenSum += partialSum % 10;
            evenSum += partialSum / 10;
        }

        return evenSum;
    }

    @Override
    public String toString() {
        return "PaymentData{" +
                "bank='" + bank + '\'' +
                ", cardNo0=" + cardNo0 +
                ", cardNo1=" + cardNo1 +
                ", cardNo2=" + cardNo2 +
                ", cardNo3=" + cardNo3 +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }

    @Override
    public int compareTo(PaymentData o) {
        Scanner parser = new Scanner(expirationDate).useDelimiter("-");

        int year = parser.nextInt();
        int month = parser.nextInt();

        parser = new Scanner(o.getExpirationDate()).useDelimiter("-");

        int year_o = parser.nextInt();
        int month_o = parser.nextInt();

        if (year == year_o) {
            return month_o - month;
        } else return year_o - year;
    }
}
