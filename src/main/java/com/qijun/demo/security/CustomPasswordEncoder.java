package com.qijun.demo.security;

import com.qijun.demo.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 密码处理解析
 *
 * @author Qijun
 * @version 1.0
 * @date 12/25/18 8:26 AM
 */
@Component
public class CustomPasswordEncoder implements PasswordEncoder {
    /**
     * Encode the raw password. Generally, a good encoding algorithm applies a SHA-1 or
     * greater hash combined with an 8-byte or greater randomly generated salt.
     *
     * @param rawPassword
     */
    @Override
    public String encode(CharSequence rawPassword) {

        if (!StringUtils.isEmpty(rawPassword)){
             return SecurityUtil.encryptAES(rawPassword.toString().getBytes());
        }

        return null;
    }

    /**
     * Verify the encoded password obtained from storage matches the submitted raw
     * password after it too is encoded. Returns true if the passwords match, false if
     * they do not. The stored password itself is never decoded.
     *
     * @param rawPassword     the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return true if the raw password, after encoding, matches the encoded password from
     * storage
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        String encrypted = encode(rawPassword);
        if (!StringUtils.isEmpty(encodedPassword) && encodedPassword.equals(encrypted)){
            return true;
        }
        return false;
    }

    /**
     * Returns true if the encoded password should be encoded again for better security,
     * else false. The default implementation always returns false.
     *
     * @param encodedPassword the encoded password to check
     * @return true if the encoded password should be encoded again for better security,
     * else false.
     */
    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}
