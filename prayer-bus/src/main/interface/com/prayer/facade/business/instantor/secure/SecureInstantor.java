package com.prayer.facade.business.instantor.secure;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.model.record.Record;
import com.prayer.fantasm.exception.AbstractException;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.DIRECT)
public interface SecureInstantor {
    /**
     * 使用用户名验证用户信息
     * 
     * @param realm
     * @param username
     * @param password
     * @return
     * @throws AbstractException
     */
    @VertexApi(Api.READ)
    Record identByName(String realm, String username, String password) throws AbstractException;

    /**
     * 使用用户Email验证用户信息
     * 
     * @param realm
     * @param email
     * @param password
     * @return
     * @throws AbstractException
     */
    @VertexApi(Api.READ)
    Record identByEmail(String realm, String email, String password) throws AbstractException;

    /**
     * 使用用户Mobile验证用户信息
     * 
     * @param realm
     * @param mobile
     * @param password
     * @return
     * @throws AbstractException
     */
    @VertexApi(Api.READ)
    Record identByMobile(String realm, String mobile, String password) throws AbstractException;
}
