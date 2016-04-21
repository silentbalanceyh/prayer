package com.prayer.facade.metaserver.h2;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.engine.rmi.RemoteQuoter;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface H2Quoter extends RemoteQuoter<String> {
}