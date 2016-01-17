package hello;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		System.out.println("authenticate user..");
		if (auth.getPrincipal() instanceof User) {
			System.out.println("user already connected");
			return new UsernamePasswordAuthenticationToken(new User("toto", "pwd"), "pwd");
		}

		System.out.println("authenticate user..");
		System.out.println("user authticated with success");
		return new UsernamePasswordAuthenticationToken(new User("toto", "pwd"), "pwd");
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
