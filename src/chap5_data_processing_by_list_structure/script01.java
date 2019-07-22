package chap5_data_processing_by_list_structure;

public class script01 {
    /**
     * 选择数据结构，其实就是时间和空间权衡的问题
     * 函数式的数据结构，的特点就是 『不可变和持久化』
     * 不可变和持久化并不总是比其它版本慢，它们甚至会更快，但是不管在什么场合，它们总是更加安全的
     *
     * 查看本章例子 List.java 就能体现上面提到的 『不可变』的特性，具体体现式在 Cons 私有类中的 list(A ...as)
     * 中，for 循环中，当创建新的元素的时候，是新的list 和新的元素，一起重新生成一个新的 list
     *
     * 而对于数据共享的事情，那么应该是这么理解，就是说在创建新的list 的时候，比如说增加一个 header，那么你是还可以访问到旧的 list 的，这样才能形成新的 list
     *
     * 第二个带来的就是性能的提升：
     *  如找第一个元素，只需要调用 head() 方法
     *  同时如果要删除第一个元素，那么也调用 tail() 就可以了，返回除第一个元素之后的list
     *
     *
     *        public static Integer sum(List<Integer> ls){
     *             return ls.isEmpty()
     *                     ? 0
     *                     : ls.head() + sum(ls.tails());
     *         }
     *
     *         public static Double product(List<Double> ls){
     *             return ls.isEmpty()
     *                     ? 1.0
     *                     : ls.head() * product(ls.tails());
     *         }
     *
     *  如上的代码是可以有很多的相似性，只有最后乘法和加法的区别
     *  所以有了这种的观察，相似和区别
     *  那么接下来做的就是抽象画，将相似的东西能抽象出来，不相似的东西能够当成是参数传递进去
     *  上面的两个方法，只需要提供变量信息，Type， Operation，Identity，operator 来实现操作
     *
     *  public static <A, B> B foldRight(List<A> list, B identity, Function<A, Function<B, B>> f){
     *             return list.isEmpty()
     *                     ? identity
     *                     : f.apply(list.head()).apply(foldRight(list.tails(), identity, f));
     *         }
     *
     *
     *
     *
     *
     *
     */
}
