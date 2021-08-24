package com.example.bank;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

@Controller
public class AppController {
    private final ArrayList<PaymentData> savedPaymentData = new ArrayList<>();

    @RequestMapping("/welcome")
    public String savePaymentData(Model model) {
        model.addAttribute("newPaymentData", new PaymentData());
        model.addAttribute("savedPaymentData", savedPaymentData);

        return "index";
    }

    @PostMapping("/addPaymentData")
    public String savePaymentData(@ModelAttribute PaymentData paymentData, Model model) {
        model.addAttribute("newPaymentData", paymentData);

        paymentData.generateCardInfoDetails();

        if (paymentData.isValid()) {
            savedPaymentData.add(paymentData);
            Collections.sort(savedPaymentData);
        }

        model.addAttribute("newPaymentData", new PaymentData());
        model.addAttribute("savedPaymentData", savedPaymentData);

        return "redirect:/welcome";
    }

    @PostMapping("/uploadPaymentData")
    public String savePaymentData(@ModelAttribute PaymentData paymentData, Model model, @RequestParam("fileSelect") MultipartFile file) {
        model.addAttribute("newPaymentData", paymentData);

        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
            CSVReader csvReader = new CSVReader(fileReader);

            for (String[] item : csvReader.readAll()) {
                try {
                    PaymentData newPaymentData = new PaymentData();

                    newPaymentData.setBank(item[0]);
                    newPaymentData.setCardNo0(item[1]);
                    newPaymentData.setCardNo1(item[2]);
                    newPaymentData.setCardNo2(item[3]);
                    newPaymentData.setCardNo3(item[4]);
                    newPaymentData.setExpirationDate(item[5]);

                    newPaymentData.generateCardInfoDetails();

                    if (newPaymentData.isValid())
                        savedPaymentData.add(newPaymentData);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(savedPaymentData);

        model.addAttribute("newPaymentData", new PaymentData());
        model.addAttribute("savedPaymentData", savedPaymentData);

        return "redirect:/welcome";
    }
}
