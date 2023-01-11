package com.example.llz.cloud1.Collection;

import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * 1.什么是流
 * 流是从支持数据处理操作的源生成的元素序列，源可以是数组、文件、集合、函数。流不是集合元素，它不是数据结构并不保存数据，它的主要目的在于计算。
 * 2.如何生成流
 * 
 * 
 * 
 */
public class StreamTest {
	
	//===>1.如何生成流
	/**
	 * 通过集合生成流
	 */
	private Stream<T> generalByCollection(List<T> list){
		return list.stream();
	}

	/**
	 * 通过数组生成流
	 */
	private Stream<T> generalByArray(T[] arr){
		//如果是基本类型数组，会有对应的stream，避免计算过程中拆箱装箱，提高性能
		int[] intArr = {1,2,3};
		IntStream intStream = Arrays.stream(intArr);
		double[] doubleArr = {1.0, 12.2};
		DoubleStream stream = Arrays.stream(doubleArr);
		return Arrays.stream(arr);
	}

	/**
	 * 通过值生成流
	 */
	private Stream<Integer> generalByValues(){
		return Stream.of(1, 2, 3);
	}

	/**
	 * 通过文件生成流
	 */
	private Stream<String> generalByFile() throws IOException {
		return Files.lines(Paths.get("data.txt"), Charset.defaultCharset());
	}

	/**
	 * 通过函数生成流
	 */
	private Stream generalByFunc(int type){
		//iterator
		if (type == 1) {
			return Stream.iterate(1, n -> n + 1).limit(5);
		}
		//
		return Stream.generate(Math::random).limit(5);
	}

	//===>2.流的操作类型（中间操作、终端操作）
	//======>一个流可以后面跟随零活多个中间操作。其主要目的是打开流，做出某种程度的数据映射、过滤，然后返回一个新的流
	// 		，交给下一个操作使用。这类操作都是惰性的，仅仅调用到这类方法，并没有真正开始流的遍历，真正的遍历需等到终端操作
	//======>一个流有且只有一个终端操作，当这个操作执行后，流就关闭并无法使用了，因此流只能遍历一次。终端操作执行时，才真正开始流的遍历

	//中间操作集合
	/**
	 * 过滤
	 */
	private Stream<Integer> filter(){
		List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6);
		return integerList.stream().filter(i -> i > 3);

	}

	/**
	 * 去重
	 */
	private Stream<Integer> distinct(){
		List<Integer> integerList = Arrays.asList(1, 1, 2, 3, 4, 5);
		return integerList.stream().distinct();
	}
	/**
	 * 返回指定流个数
	 */
	private Stream<Integer> limit(){
		List<Integer> integerList = Arrays.asList(1, 1, 2, 3, 4, 5);
		return integerList.stream().limit(3);
	}
	/**
	 * 填过流中元素
	 */
	private Stream<Integer> skip(){
		List<Integer> integerList = Arrays.asList(1, 1, 2, 3, 4, 5);
		return integerList.stream().skip(2);
	}
	/**
	 * map流映射，将流原来的流元素映射成另外的元素
	 */
	private List<Integer> map(){
		List<String> stringList = Arrays.asList("Java 8", "Lambdas", "In", "Action");
		return stringList.stream()
				.map(String::length)
				.collect(toList());
	}
	/**
	 * flatMap 流转换 将一个流中的每个值都转换为另一个流.
	 */
	private List<String> flatMap(){
		List<String> wordList = Arrays.asList("Java 8", "Lambdas", "In", "Action");
		return wordList.stream()
				.map(w -> w.split(" "))
				.flatMap(Arrays::stream)
				.distinct()
				.collect(toList());

	}
	/**
	 * allMatch 匹配所有元素
	 * anyMatch 匹配其中一个
	 */
	private void match(){
		List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);

		if (integerList.stream().allMatch(i -> i > 3)) {
			System.out.println("所有元素值都大于3");
		} else {
			System.out.println("并非所有元素值都大于3");
		}

		if (integerList.stream().anyMatch(i -> i > 3)) {
			System.out.println("存在值大于3的元素");
		} else {
			System.out.println("不存在值大于3的元素");
		}
		
		if (integerList.stream().noneMatch(i -> i > 3)) {
			System.out.println("值都小于3的元素");
		} else {
			System.out.println("值不都小于3的元素");
		}

	}
	
	//终端操作集合

	/**
	 * count 统计流中元素个数
	 */
	private void count(){
		List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
		System.out.println(integerList.stream().count());
	}
	
	/**
	 * findFirst 查找第一个
	 */
	private void findFirst(){
		List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
		Optional<Integer> result = integerList.stream().filter(i -> i > 3).findFirst();
		System.out.println(result.orElse(-1));
	}	
	
	/**
	 * findFirst 随机查找一个
	 */
	private void findAny(){
		List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
		Optional<Integer> result = integerList.stream().filter(i -> i > 3).findAny();
		System.out.println(result.orElse(-1));
	}

	/**
	 * reduce 将流中的元素组合
	 */
	private void reduce() {
		//用于求和：
		List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
		int sum = integerList.stream().reduce(0, Integer::sum);
		System.out.println(sum);

		List<String> stringList = Arrays.asList("Java 8", "Lambdas", "In", "Action");
		Optional<Integer> min = stringList.stream()
				.map(String::length)
				.reduce(Integer::min);
		System.out.println("min==>" + min);
		Optional<Integer> max = stringList.stream()
				.map(String::length)
				.reduce(Integer::max);
		System.out.println("max=>" + max);
	}

	/**
	 * min/max 获取最小最大值
	 */
	private void minMax(){
		List<String> stringList = Arrays.asList("Java 8", "Lambdas", "In", "Action");

		Optional<Integer> min = stringList.stream()
				.map(String::length)
				.min(Integer::compareTo);

		Optional<Integer> max = stringList.stream()
				.map(String::length)
				.max(Integer::compareTo);

		OptionalInt
				minInt = stringList.stream()
				.mapToInt(String::length)
				.min();

		OptionalInt
				maxInt = stringList.stream()
				.mapToInt(String::length)
				.max();
	}

	/**
	 * 求和
	 */
	private void sum() {
		List<String> stringList = Arrays.asList("Java 8", "Lambdas", "In", "Action");
		//方式1：sum（推荐）
		int sum = stringList.stream()
				.mapToInt(String::length)
				.sum();
		System.out.println("sum:" + sum);
		//方式2：summingInt
		//如果数据类型为double、long，则通过summingDouble、summingLong方法进行求和。
		//你看，idea都推荐转成int之后再求和
		int summingInt = stringList.stream()
				.collect(summingInt(String::length));
		System.out.println("summingInt:" + summingInt);
		//方式3：reduce
		int reduce = stringList.stream()
				.map(String::length)
				.reduce(0, Integer::sum);
		System.out.println("reduce:" + reduce);
	}
	//在上面求和、求最大值、最小值的时候，对于相同操作有不同的方法可以选择执行。
	// 可以选择collect、reduce、min/max/sum方法，推荐使用min、max、sum方法。
	// 因为它最简洁易读，同时通过mapToInt将对象流转换为数值流，避免了装箱和拆箱操作

	/**
	 * summarizingxxx 同时求总和、平均值、最大值、最小值
	 */
	private void summarizingxxx(){
		List<String> stringList = Arrays.asList("Java 8", "Lambdas", "In", "Action");

		IntSummaryStatistics intSummaryStatistics = stringList.stream()
				.collect(summarizingInt(String::length));

		double average = intSummaryStatistics.getAverage(); // 获取平均值
		int min = intSummaryStatistics.getMin();            // 获取最小值
		int max = intSummaryStatistics.getMax();            // 获取最大值
		long sum = intSummaryStatistics.getSum();           // 获取总和

		System.out.println("average:" + average);
		System.out.println("min:" + min);
		System.out.println("max:" + max);
		System.out.println("sum:" + sum); 
	}

	/**
	 * collect 返回集合
	 */
	private void collect(){
		List<String> stringList = Arrays.asList("Java 8", "Lambdas", "In", "Action");
		List<Integer> intList = stringList.stream()
				.map(String::length)
				.collect(toList());
		Set<Integer> intSet = stringList.stream()
				.map(String::length)
				.collect(toSet());
	}

	/**
	 * joining 拼接流中的元素
	 */
	private void joining(){
		List<String> stringList = Arrays.asList("Java 8", "Lambdas", "In", "Action");

		String result = stringList.stream()
				.map(String::toLowerCase)
				.collect(Collectors.joining("-"));

	}

}
