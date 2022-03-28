G1和CMS的并发标记阶段

难点：在标记过程中，对象引用关系正在发生改变。标记的过程中，既有已被标记过为垃圾的再被重新引用变为非垃圾，也会产生新垃圾。

三色标记：

1. 黑色：

2. 灰色

3. 白色：

   理解三色的根本，什么情况下会漏标：黑色对象指向了白色对象，与此同时，（灰色）指向白色对象的引用没了。

   漏标：本来是live object，但是因为没有被标记到，被当成garbage回收掉了。

   这时候如果不对黑色重新扫描，则会把白色对象当成辣鸡而被回收。

> 解决方案：
>
> 1. CMS使用incremental update，增量更新，关注引用的增加，把黑色重新标记为灰色，下次重新标记 重新扫描属性。
>
> 2. SATB: 关注引用的删除，当B->D删除时，要把这个 引用 推到GC的堆栈，保证D在重新标记时还能被扫描到。
>
> 原因：

```
http POST :8080/admin/gateway/routes/apiaddreqhead uri=http://httpbin.org:80
	 * predicates:='["Host=**.apiaddrequestheader.org", "Path=/headers"]'
	 * filters:='["AddRequestHeader=X-Request-ApiFoo, ApiBar"]'
```