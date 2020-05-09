package com.lizhi.miaosha.redis;

/**
 * 商品缓存相关key
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
public class GoodKey extends BasePrefix{

	private GoodKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	public static GoodKey getGoodList = new GoodKey(60, "gl");
	public static GoodKey getGoodDetail = new GoodKey(60, "gd");
	public static GoodKey getMiaoshaGoodStock= new GoodKey(0, "gs");
}
