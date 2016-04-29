package com.prayer.facade.resource;

import com.prayer.constant.SystemEnum.Interface;
import com.prayer.facade.annotation.VertexPoint;

/**
 * 常量接口，保存了所有需要使用的配置Key
 * 
 * @author Lang
 *
 */
@VertexPoint(Interface.CONSTANT)
public interface Point {

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Uri {
        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Route {
            /** **/
            String PARENT = "route.parent";
            /** **/
            String HANDLER = "route.request.handler";
            /** **/
            String METHOD = "route.method";
            /** **/
            String MIMES_CONSUMER = "route.mimes.consumer";
            /** **/
            String MIMES_PRODUCER = "route.mimes.producer";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Address {
            /** **/
            String QUEUE = "addr.queue";
            /** **/
            String CONSUMER = "addr.consumer";
        }

        /** **/
        String ADDRESS = "uri.address";
        /** **/
        String SENDER = "uri.sender";
        /** **/
        String MIMES_CONTENT = "uri.mimes.content";
        /** **/
        String MIMES_ACCEPT = "uri.mimes.accept";
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Security {
        /** **/
        String PROVIDER = "secure.provider";
        /** **/
        String HANDLER = "secure.handler";
        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Shared {
            /** **/
            String MODE = "secure.mode";
            /** **/
            String REALM = "secure.realm";
            /** Schema Identifier **/
            String IDENTIFIER = "secure.identifier";
            /** **/
            @VertexPoint(Interface.CONSTANT)
            interface User{
                /** **/
                String USER_ID = "secure.user.id";
                /** **/
                String EMAIL = "secure.email";
                /** **/
                String MOBILE = "secure.mobile";
            }

            /** **/
            @VertexPoint(Interface.CONSTANT)
            interface Tp {
                /** **/
                String ALIPAY = "secure.alipay";
                /** **/
                String TAOBAO = "secure.taobao";
                /** **/
                String QQ = "secure.qq";
                /** **/
                String WEIBO = "secure.weibo";
                /** **/
                String WEBCHAT = "secure.webchat";
            }
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Cors {
            /** **/
            String ORIGIN = "cors.origin";
            /** **/
            String METHODS = "cors.methods";
            /** **/
            String HEADERS = "cors.headers";
            /** **/
            String CREDENTIALS = "cors.credentials";
        }
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Server {
        /** Host **/
        String HOST = "server.host";
        /** Accept Backlog **/
        String ACCEPT_BACKLOG = "opt.accept.backlog";

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Http {
            /** Compression Support **/
            String COMPRESSION_SUPPORT = "opt.compression.support";
            /** Max Chunk Size **/
            String MAX_CHUNK_SIZE = "opt.chunk.size";
            /** Web Socket Frame Size **/
            String MAX_WEBSOCKET_FSIZE = "opt.websocket.frame.size";
            /** Max Initial Line Length **/
            String MAX_INITIAL_LINE_LENGTH = "opt.initial.line.length";
            /** Max Header Size **/
            String MAX_HEADER_SIZE = "opt.header.size";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Network {
            /** Receive Buffer Size **/
            String REC_BUFFER_SIZE = "opt.receive.buffer.size";
            /** Send Buffer Size **/
            String SEND_BUFFER_SIZE = "opt.send.buffer.size";
            /** Reuse Address **/
            String REUSE_ADDR = "opt.reuse.address";
            /** Traffic Class **/
            String TRAFFIC_CLASS = "opt.traffic.class";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface TcpSSL {
            /** Idle Time out **/
            String IDLE_TIMEOUT = "opt.idle.timeout";
            /** So Linger **/
            String SO_LINGER = "opt.so.linger";
            /** Tcp No Delay **/
            String TCP_NO_DELAY = "opt.tcp.no.delay";
            /** Tcp Keep Alive **/
            String TCP_KEEP_ALIVE = "opt.tcp.keep.alive";
            /** Use Pooled Buffers **/
            String USE_POOLED_BUF = "opt.use.pooled.buffer";
        }
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Vertx {
        /** 激活的Vertx实例 **/
        String ACTIVE = "vertx.active";

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Pool {
            /** Pool Size Configuration **/
            String EVTLOOP_SIZE = "vertx.{0}.pool.size.event.loop";
            /** Worker Size **/
            String WORKER_SIZE = "vertx.{0}.pool.size.worker";
            /** Internal Blocking **/
            String INTERNAL_BLOCKING = "vertx.{0}.pool.size.internal.blocking";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Cluster {
            /** Enabled **/
            String ENABLED = "vertx.{0}.cluster.enabled";
            /** Host **/
            String HOST = "vertx.{0}.cluster.host";
            /** Port **/
            String PORT = "vertx.{0}.cluster.port";
            /** Host **/
            String PUB_HOST = "vertx.{0}.cluster.pub.host";
            /** Port **/
            String PUB_PORT = "vertx.{0}.cluster.pub.port";

            /** Ping Interval **/
            String PING_IV = "vertx.{0}.cluster.ping.interval";
            /** Ping Interval Reply **/
            String PING_IV_REPLY = "vertx.{0}.cluster.ping.interval.reply";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Blocked {
            /** Blocked thread check internal **/
            String THREAD_CHK_IV = "vertx.{0}.blocked.thread.check.internal";
            /** Execute Time Max **/
            String TIME_MAX_EVTLOOP = "vertx.{0}.execute.time.max.event.loop";
            /** Max Worker **/
            String TIME_MAX_WORKER = "vertx.{0}.execute.time.max.worker";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Ha {
            /** Enabled **/
            String ENABLED = "vertx.{0}.ha.enabled";
            /** Ha Group **/
            String GROUP = "vertx.{0}.ha.group";
            /** Quorum Size **/
            String QUORUM_SIZE = "vertx.{0}.quorum.size";
            /** Warning Exception Time **/
            String WARNING_EXPTIME = "vertx.{0}.warning.exception.time";
        }
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Console {
        /** Console 配置专用 **/
        String SLF4J_CONFIG = "console.slf4j.config";
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface MetaServer {
        /** Meta Server对应的配置 **/
        String CONFIG = "meta.server.config";
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Injection {
        /** 使用的Cache **/
        String CATCH = "cache.manager";

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Web {
            /** Pattern的Hooker钩子 **/
            String URI_HOOKER = "web.uri.hooker";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Service {
            /** Script Engine **/
            String SCRIPT_ENGINE = "script.engine";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Data {
            /** 数据库连接池 **/
            String POOL = "database.data.pool";
            /** 数据库中验证器 **/
            String VALIDATOR = "database.data.validator";
            /** Meta 数据库构造器 **/
            String BUILDER = "database.meta.builder";
            /** Meta：数据库中的元数据访问 **/
            String META_DALOR = "database.meta.dalor";
            /** Data **/
            String DATA_DALOR = "database.data.dalor";
            /** Transducer **/
            String TRANSDUCER = "database.data.transducer";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Meta {
            /** 元数据使用JDBC时的连接类 **/
            String CONNECTION = "meta.connection";
            /** Meta：数据库中的Accessor **/
            String ACCESSOR = "meta.accessor";
            /** Meta：Server启动类 **/
            String LAUNCHER = "meta.server.launcher";
        }
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface System {
        /** 编码方式 **/
        String ENCODING = "system.encoding";
        /** 日志目录 **/
        String LOGS_DEBUG = "system.logs.debug";
        /** 错误日志目录 **/
        String LOGS_ERROR = "system.logs.error";
        /** 消息日志目录 **/
        String LOGS_INFO = "system.logs.info";
        /** 验证Folder **/
        String VALIDATION_RULE = "system.validation.rules";
        /** Script文件夹 **/
        String SCRIPT_FOLDER = "system.script.folders";
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Deploy {
        /** 默认发布的元数据文件 **/
        String INIT_FILE = "meta.init.file";
        /** 默认发布的元数据目录 **/
        String META_FOLDER = "meta.folder";
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface RMI {
        /** Meta Server **/
        String META_SERVER = "rmi.meta.server";
        /** Vertx实例 **/
        String VERTX = "rmi.vertx";
        /** RMI Port **/
        String RMI_PORT = "rmi.port";
        /** RMI Host **/
        String RMI_HOST = "rmi.host";
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Error {
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Schema {
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Resolver {
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Jdbc {
        // Fix引用必须，Database和Jdbc是相互独立的，并不在同一个文件，不可使用Database作为Key值
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Web {
        /** ORDERS **/
        String HANDLER_ORDERS = "orders.file.config";

        /** Login相关配置 **/
        @VertexPoint(Interface.CONSTANT)
        interface Login {
            /** Logout Action **/
            String ACTION_LOGOUT = "action.logout";
            /** Login Page **/
            String ACTION_INDEX = "action.login.index";
        }

        /** Dynamic动态脚本配置 **/
        @VertexPoint(Interface.CONSTANT)
        interface Dynamic {
            /** 基本路由 **/
            String BASIC_ROUTE = "dynamic.basic.route";
            /** 管理员路由 **/
            String ADMIN_ROUTE = "dynamic.admin.route";
        }

        /** Static配置 **/
        @VertexPoint(Interface.CONSTANT)
        interface Static {
            /** Favicon专用 **/
            String FAVICON = "static.favicon";
            /** Static路由专用 **/
            String RESOURCE = "static.resource";
            /** 模板引擎名称 **/
            String TEMPLATE_MODE = "static.template.mode";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Api {
            /** 公开Api **/
            String PUBLIC = "api.public";
            /** 安全Api **/
            String SECURE = "api.secure";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Orders {

            /** Static Order **/
            String STATIC = "handler.static";
            /** Favicon **/
            String FAVICON = "handler.favicon";
            /** Template Mode **/
            String TPLMODE = "handler.tpl";

            /** Dynamic **/
            String DYNAMIC = "handler.dynamic";
            /** Dynamic Admin **/
            String DYNAMIC_ADMIN = "handler.dynamic.admin";

            /** CORS **/
            String CORS = "handler.cors";
            /** Cookie **/
            String COOKIE = "handler.cookie";
            /** Body **/
            String BODY = "handler.body";

            /** **/
            @VertexPoint(Interface.CONSTANT)
            interface Api {
                /** 0.Secure **/
                String AUTH = "handler.secure";
                /** 1.URI **/
                String URI = "handler.strainer.uri";
                /** 2.Sinkor **/
                String SINKER = "handler.sinker";
                /** 3.Acceptor **/
                String ACCEPTOR = "handler.acceptor";
                /** 4.Inspector **/
                String INSPECTOR = "handler.inspector";
                /** 5.Data Strainer **/
                String DATA = "handler.strainer.data";
                /** 6.Stdn **/
                String STDN = "handler.stdn";

                /** 6.Failure **/
                String FAILURE = "handler.failure";
                /** 7.Service Sender **/
                String SERVICE = "handler.service";
            }
        }
    }

    /** 第三方 **/
    @VertexPoint(Interface.CONSTANT)
    interface TP {
        /** MyBatis **/
        @VertexPoint(Interface.CONSTANT)
        interface MyBatis {
            /** 配置文件路径 **/
            String CFG_FILE = "mybatis.config.file";
            /** 环境名 **/
            String ENV = "mybatis.environment";
        }
    }

    /** **/
    @VertexPoint(Interface.CONSTANT)
    interface Database {
        /** 开启底层AOP验证 **/
        String VALIDATION = "aop.validation.enabled";
        /** Dao的mapping文件 **/
        String DALOR = "dalor.mapping";

        /** Jdbc特有配置 **/
        @VertexPoint(Interface.CONSTANT)
        interface Jdbc {
            /** JDBC配置文件 **/
            String JDBC = "jdbc.config.file";
            /** Mapping文件位置 **/
            String MAPPING = "jdbc.type.mapping";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Data {
            /** 数据库模式 **/
            String MODE = "database.mode";
            /** 数据库种类 **/
            String CATEGORY = "database.category";
        }

        /** **/
        @VertexPoint(Interface.CONSTANT)
        interface Meta {
            /** 元数据库模式 **/
            String MODE = "metadata.mode";
            /** 元数据库种类 **/
            String CATEGORY = "metadata.category";
        }

    }
}
