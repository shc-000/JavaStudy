package com.frog.study.design;

/**
 * 策略模式+枚举
 *
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/3/25
 */
enum NOTIFY_TYPE {    //1、定义一个包含通知实现机制的“充血”的枚举类型
    email("邮件", NotifyMechanismInterface.byEmail()),
    sms("短信", NotifyMechanismInterface.bySms()),
    wechat("微信", NotifyMechanismInterface.byWechat());

    String memo;
    NotifyMechanismInterface notifyMechanism;

    private NOTIFY_TYPE(String memo, NotifyMechanismInterface notifyMechanism) {//2、私有构造函数，用于初始化枚举值
        this.memo = memo;
        this.notifyMechanism = notifyMechanism;
    }
    //getters ...
}

interface NotifyMechanismInterface { //3、定义通知机制的接口或抽象父类
    public boolean doNotify(String msg);

    public static NotifyMechanismInterface byEmail() {//3.1 定义邮件通知机制的实现策略
        return new NotifyMechanismInterface() {
            @Override
            public boolean doNotify(String msg) {
//                  .......
                return true;
            }
        };
    }

    public static NotifyMechanismInterface bySms() {//3.2 定义短信通知机制的实现策略
        return new NotifyMechanismInterface() {
            @Override
            public boolean doNotify(String msg) {
//                  .......
                return true;
            }
        };
    }

    public static NotifyMechanismInterface byWechat() {//3.3 定义微信通知机制的实现策略
        return new NotifyMechanismInterface() {
            @Override
            public boolean doNotify(String msg) {
//                  .......
                return true;
            }
        };
    }
}

//4、使用场景
//NOTIFY_TYPE.valueof(type).getNotifyMechanism().doNotify(msg);

/*
1.充血枚举类型——Rich Enum Type模式的优势：
        1.1不难发现，这其实就是enum枚举类型和Strategy Pattern策略模式的巧妙结合运用，
        1.2当需要增加新的通知方式时，只需在枚举类NOTIFY_TYPE增加一个值，同时在策略接口NotifyMechanismInterface中增加一个by方法返回对应的策略实现；
        1.3当需要修改某个通知机制的实现细节，只需修改NotifyMechanismInterface中对应的策略实现；
        1.4无论新增还是修改通知机制，调用方完全不受影响，仍然是 NOTIFY_TYPE.valueof(type).getNotifyMechanism().doNotify(msg);
2.与传统Strategy Pattern策略模式的比较优势：常见的策略模式也能消灭if else判断，但是实现起来比较麻烦，需要开发更多的class和代码量：
        2.1每个策略实现需单独定义成一个class；
        2.2还需要一个Context类来做初始化——用Map把类型与对应的策略实现做映射；
        2.3使用时从Context获取具体的策略；
3.Rich Enum Type的进一步的充血：
        3.1上面的例子中的枚举类型包含了行为，因此已经算作充血模型了，但是还可以为其进一步充血；
        3.2例如有些场景下，只是要对枚举值做个简单的计算获得某种flag标记，那就没必要把计算逻辑抽象成NotifyMechanismInterface那样的接口，杀鸡用了牛刀；
        3.3这是就可以在枚举类型中增加static function封装简单的计算逻辑；
4.策略实现的进一步抽象：
        4.1当各个策略实现（byEmail bySms byWechat）存在共性部分、重复逻辑时，可以将其抽取成一个抽象父类；
        4.2然后就像前一章节——业务模板Pattern of NestedBusinessTemplate那样，在各个子类之间实现优雅的逻辑分离和复用。*/
