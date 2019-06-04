package com.lizhi.miaosha.redis;

/**
 * 秒杀相关
 *
 * @author xulizhi-lenovo
 * @date 2019/6/4
 */
public class MiaoshaKey extends BasePrefix{

	private MiaoshaKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	public static MiaoshaKey isGoodsOver = new MiaoshaKey(0, "go");
	public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60, "mp");
	public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(300, "vc");
}
