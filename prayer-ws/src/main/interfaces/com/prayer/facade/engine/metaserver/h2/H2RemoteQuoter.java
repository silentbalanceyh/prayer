package com.prayer.facade.engine.metaserver.h2;

import org.h2.tools.Server;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;
import com.prayer.facade.engine.rmi.RemoteQuoter;

/**
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.INTERNAL)
public interface H2RemoteQuoter extends RemoteQuoter<Server> {
    
    
}
