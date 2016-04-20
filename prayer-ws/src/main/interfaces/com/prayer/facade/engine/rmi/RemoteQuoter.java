package com.prayer.facade.engine.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.prayer.constant.SystemEnum.Api;
import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexApi;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 生命服务端提供的服务
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface RemoteQuoter<T> extends Remote {
    /** **/
    @VertexApi(Api.TOOL)
    T service(T reference) throws RemoteException;
}
