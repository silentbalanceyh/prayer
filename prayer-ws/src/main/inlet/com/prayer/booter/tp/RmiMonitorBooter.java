package com.prayer.booter.tp;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.prayer.facade.engine.rmi.StandardQuoter;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class RmiMonitorBooter {
    // ~ Static Fields =======================================
    private static Registry registry;

    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    public static void main(String args[]) throws Exception {
        registry = LocateRegistry.getRegistry("localhost", 1099);
        for (final String item : registry.list()) {
            System.out.println("[KEY] : " + item);
            final String value = lookup(item);
            if (toJson(value)) {
                final JsonObject json = new JsonObject(value);
                System.out.println("[V -> JsonObject] : " + json.encodePrettily());
            } else if (toJsonArr(value)) {
                final JsonArray array = new JsonArray(value);
                System.out.println("[V -> JsonArray] : " + array.encodePrettily());
            } else {
                System.out.println("[V -> String] : " + value);
            }
            System.out.println("--------------------------------");
        }
    }
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================

    // ~ Methods =============================================
    // ~ Private Methods =====================================
    private static boolean toJsonArr(final String value) {
        boolean flag = false;
        try {
            new JsonArray(value);
            flag = true;
        } catch (DecodeException ex) {
            flag = false;
        }
        return flag;
    }

    private static boolean toJson(final String value) {
        boolean flag = false;
        try {
            new JsonObject(value);
            flag = true;
        } catch (DecodeException ex) {
            flag = false;
        }
        return flag;
    }

    private static String lookup(final String name) throws Exception {
        final StandardQuoter quoter = (StandardQuoter) registry.lookup(name);
        String retValue = null;
        if (null != quoter) {
            retValue = quoter.getData();
        }
        return retValue;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}
