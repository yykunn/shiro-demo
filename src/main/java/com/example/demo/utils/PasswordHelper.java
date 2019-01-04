package com.example.demo.utils;

import com.example.demo.pojo.SysUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {
	private static String algorithmName = "md5";
    private static int hashIterations = 2;
 
    public static void encryptPassword(SysUser user) {
    	String newPassword = new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), hashIterations).toString();
        user.setPassword(newPassword);
 
    }
    public static void main(String[] args) {
        PasswordHelper passwordHelper = new PasswordHelper();
        SysUser user = new SysUser();
        user.setPassword("zxx456654");
        user.setSalt("123456");
        passwordHelper.encryptPassword(user);
        System.out.println(user.getPassword());
    }
}
