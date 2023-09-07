package com.spring.pms.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.pms.Entity.RefreshToken;
import com.spring.pms.Exceptions.DetailsNotFoundException;
import com.spring.pms.Repository.RefreshTokenRepository;
import com.spring.pms.Repository.UserRepo;

@Service
public class RefreshTokenService {
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private UserRepo userRepo;
	public RefreshToken createRefreshToken(String username)
	{
		
		RefreshToken refreshToken=new RefreshToken();
		refreshToken.setUser(userRepo.findByName(username).get());
		refreshToken.setExpiryDate(Instant.now().plusMillis(6000000*24));
		refreshToken.setToken(UUID.randomUUID().toString());
		return refreshTokenRepository.save(refreshToken);
		
		
	}
	 public Optional<RefreshToken> findByToken(String token) {
		
	        return refreshTokenRepository.findByToken(token);
	    }
	 
	 
	 public RefreshToken verifyExpiration(RefreshToken token) {
	        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
	            refreshTokenRepository.delete(token);
	            throw new RuntimeException(token.getToken() + " Refresh_token_was_expired_Please_make_a_new_signin_request");
	        }
	        return token;
	    }


}
