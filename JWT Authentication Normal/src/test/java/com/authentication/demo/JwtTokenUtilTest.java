package com.authentication.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.authentication.demo.config.JwtTokenUtil;

public class JwtTokenUtilTest {

    @Test
    void testGenerateAndValidateToken() throws Exception {
        JwtTokenUtil util = new JwtTokenUtil();

        Field secretField = JwtTokenUtil.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(util, "test_secret");

        UserDetails userDetails = new User("dummy", "password", new ArrayList<>());
        String token = util.generateToken(userDetails);

        assertEquals("dummy", util.getUsernameFromToken(token));
        assertTrue(util.validateToken(token, userDetails));
    }
}
