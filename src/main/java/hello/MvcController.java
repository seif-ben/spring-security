package hello;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MvcController {

	@Autowired
	AuthenticationManager authManager;

	@RequestMapping(path = "auth/login", method = RequestMethod.POST)
	public User login(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpServletRequest req, HttpServletResponse response) {
		Authentication auth = null;
		try {
			auth = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(new User(username, password), password));
		} catch (AuthenticationException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return new User();
		}
		if (auth == null) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return null;
		}

		SecurityContextHolder.getContext().setAuthentication(auth);
		return (User) auth.getPrincipal();
	}

	@RequestMapping(path = "auth/logout", method = RequestMethod.GET)
	public User logout(HttpServletRequest req, HttpServletResponse response) {
		try {
			req.logout();
		} catch (ServletException e) {
			System.out.println("auth:" + e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return new User();
	}

	@RequestMapping(path = "rest/current", method = RequestMethod.GET)
	public User current(HttpServletRequest req, HttpServletResponse response) {
		UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) req.getUserPrincipal();
		if (principal.getPrincipal() instanceof User) {
			return (User) principal.getPrincipal();
		}
		return null;
	}

}
