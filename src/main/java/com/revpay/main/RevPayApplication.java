package com.revpay.main;

import com.revpay.menu.MainMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RevPayApplication {

    private static final Logger logger =
            LogManager.getLogger(RevPayApplication.class);

    public static void main(String[] args) {

        System.out.println(" ");
        System.out.println("===================================");
        logger.info("      RevPay Application Started    ");
        System.out.println("===================================");

        try {
            MainMenu mainMenu = new MainMenu();
            mainMenu.show();
        } catch (Exception e) {
            logger.error("Unexpected error occurred in application", e);
        }

        logger.info("RevPay Application Stopped");
    }
}
