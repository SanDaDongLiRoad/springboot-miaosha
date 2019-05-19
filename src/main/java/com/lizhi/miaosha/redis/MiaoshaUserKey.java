package com.lizhi.miaosha.redis;

/**
 * 秒杀用户key
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
public class MiaoshaUserKey extends BasePrefix{

	public static final int TOKEN_EXPIRE = 3600*24 * 2;
	private String prefix;
	private MiaoshaUserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
		this.prefix = prefix;
	}

	public MiaoshaUserKey withExpire(int seconds) {
		return new MiaoshaUserKey(seconds, prefix);
	}

	public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "tk");

	public static MiaoshaUserKey getById = new MiaoshaUserKey(0, "id");
}
