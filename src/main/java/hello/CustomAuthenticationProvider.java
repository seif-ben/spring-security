package hello;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication auth) {
		User pricipal = (User) auth.getPrincipal();

		if (pricipal.getUsername().equals("bad")) {
			System.out.println("Bad credential!");
			throw new BadCredentialsException("Bad credential!");
		}

		// retrieve user
		System.out.println("start authenticate user");
		User user = new User("toto", "pwd");
		user.setRoles(new HashSet<Role>(Arrays.asList(new Role("ADMIN"), new Role("MANAGER"))));

		return new UsernamePasswordAuthenticationToken(user, "pwd",
				Arrays.asList(new Role("ADMIN"), new Role("MANAGER")));
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
}
