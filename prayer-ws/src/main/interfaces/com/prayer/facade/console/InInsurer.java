package com.prayer.facade.console;

import org.apache.commons.cli.Options;

import net.sf.oval.guard.Guarded;

/**
 * 
 * @author Lang
 *
 */
@Guarded
public interface InInsurer {
    /**
     * 
     * @param file
     * @param args
     * @return
     */
    Options satisfy(String file, String[] args);
}
