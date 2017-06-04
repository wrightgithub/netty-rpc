# netty-rpc 
todo
1. 完善netty-rpc使用教程
2. 后期增加zk



## 5.21 add jmx support


## 5.22 add client连接失败重试


## 5.28 service异常如何返回给client。
1. 把异常当成result来返回，在客户端动态代理的时候，判断是否是异常。
需要注意的是服务端的调用异常需要从InvocationTargetException里的getTargetException去获取真正的业务异常
2. 异常堆栈的信息其实对序列化和反序列化会带来很大的开销，可以考虑值返回一份重要的堆栈

## 5.28 客户端最好和服务端建立长链接，以免每次都要去建立连接
由于client是bean初始化的时候就已经建立了链接，所以其生命周期伴随bean的生命周期

## 5.28 连接中断的重试机制


## 5.30 利用groovy做动态配置