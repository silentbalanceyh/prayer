package com.prayer.facade.deployment.acus;

import com.prayer.facade.fun.deploy.AcusPoster;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 发布用接口
 * @author Lang
 *
 */
public interface DeployAcus {
    /**
     * 发布数据专用接口
     * @param folder
     * @return
     * @throws AbstractException
     */
    boolean deploy(String folder, AcusPoster fun) throws AbstractException;
}
