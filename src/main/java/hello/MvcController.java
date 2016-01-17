package hello;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
	public User login(@RequestParam("username") String username, @RequestParam("password") String password) {
		User user = new User(username, password);
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user, password));
		SecurityContextHolder.getContext().setAuthentication(auth);
		System.out.println("auth:" + auth);
		return user;
	}

	@RequestMapping(path = "auth/logout", method = RequestMethod.GET)
	public User logout(HttpServletRequest req, HttpServletResponse response) {
		try {
			req.logout();
		} catch (ServletException e) {
			System.out.println("auth:" + e);
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
