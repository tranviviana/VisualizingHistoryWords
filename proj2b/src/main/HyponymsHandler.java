package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

public class HyponymsHandler extends NgordnetQueryHandler {
    @Override
    public String handle(NgordnetQuery q) {
        System.out.println("Hello");
        return null;
    }
}
