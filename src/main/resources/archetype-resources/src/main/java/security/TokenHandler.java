/**
 * File $Id$
 * Created on Sep 19, 2017
 *
 * Copyright (C) 2000-2017, Romac Fuels nv, All rights reserved.
 * 
 * License to use this software is restricted to companies
 * that have signed a non-disclosure agreement with Romac Fuels.
 * 
 * CONFIDENTIAL
 *
 * @author     Delsael Kenji <kenji@romacfuels.com>, Original Author
 * @copyright  Romac Fuels nv, 2000-2017
 * @license    CONFIDENTIAL
 * @package    com.mattheeuws.security.security
 * @version    $Id LastChangedRevision$
 */
package com.mattheeuws.security.security;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
@Service
public class TokenHandler {

  private CustomUserDetailsService userDetailsService;

  private static final String SECRET = "ThisIsASecret";

  @Autowired
  public TokenHandler(CustomUserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  public String generateToken(UserDetails user) {
    Date now = new Date();
    Date expiration = new Date(now.getTime() + TimeUnit.HOURS.toMillis(1L));
    return Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuedAt(now)
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
  }

  public CustomUserDetails parseToken(String token) {
    String userName = Jwts.parser()
        .setSigningKey(SECRET)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();

    return userDetailsService.loadUserByUsername(userName);
  }

}
