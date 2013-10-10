package com.kspichale.kundera.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kspichale.kundera.entity.TweetEntity;
import com.kspichale.kundera.service.TweetService;

/**
 * This class encapsulates the controllers for the blog web application. It
 * delegates all interaction with MongoDB to three Data Access Objects (DAOs).
 * <p/>
 * It is also the entry point into the web application.
 */
@Controller
public class BlogController {

	@Autowired
	private TweetService tweetService;

	public BlogController() {
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(HttpServletRequest request, @ModelAttribute("model") ModelMap model) {
		model.addAttribute("error_message", "");
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String createAccount(HttpSession session, HttpServletResponse response, @ModelAttribute("user") User user) {
		// TODO save new user
		putUsernameIntoSession(session, user.getName());
		return "redirect:welcome.html";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String createTweet(HttpSession session, HttpServletRequest request) {
		removeUsernameFromSession(session);
		removeSearchnameFromSession(session);
		return "redirect:index.html";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, HttpServletResponse response, @ModelAttribute("user") User user) {
		putUsernameIntoSession(session, user.getName());
		return "redirect:welcome.html";
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(HttpServletRequest request, @ModelAttribute("model") ModelMap model) {

		String username = getUsernameFromSession(request.getSession());
		model.addAttribute("username", username);

		List<Tweet> timeline = new ArrayList<Tweet>();

		List<TweetEntity> tweetEntities = tweetService.getTweetsForUser(username);
		for (TweetEntity tweetEntity : tweetEntities) {
			Tweet tweet = new Tweet();
			tweet.setMessage(tweetEntity.getMessage());
			tweet.setAuthor(tweetEntity.getAuthor());
			timeline.add(tweet);
		}

		model.addAttribute("timeline", timeline);

		return "welcome";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String login(@ModelAttribute("model") ModelMap model) {
		model.addAttribute("username", "");
		model.addAttribute("error_message", "");
		return "index";
	}

	@RequestMapping(value = "/tweet", method = RequestMethod.GET)
	public String createTweet(HttpServletRequest request, @ModelAttribute("model") ModelMap model) {
		String username = getUsernameFromSession(request.getSession());
		model.addAttribute("username", username);
		return "tweet";
	}

	@RequestMapping(value = "/postTweet", method = RequestMethod.POST)
	public String add(HttpServletResponse response, @ModelAttribute("tweet") Tweet tweet) {
		String message = tweet.getMessage();
		String author = tweet.getAuthor();
		this.tweetService.addTweet(author, message);
		return "redirect:welcome.html";
	}

	@RequestMapping(value = "/following", method = RequestMethod.GET)
	public String following(HttpServletRequest request, @ModelAttribute("model") ModelMap model) {
		String username = getUsernameFromSession(request.getSession());
		model.addAttribute("username", username);

		// Set<String> followers = userService.getFollowerNames(username);
		Set<String> followers = new HashSet<String>();
		followers.add("user1");
		model.addAttribute("following", followers);

		return "following";
	}

	@RequestMapping(value = "/following", method = RequestMethod.POST)
	public String following(HttpSession session, HttpServletResponse response,
			@ModelAttribute("following") User followedUser) {
		String username = getUsernameFromSession(session);
		String followedUsername = followedUser.getName();
		// userService.removeFollower(username, followedUsername);
		return "redirect:following.html";
	}

	@RequestMapping(value = "/addFollower", method = RequestMethod.GET)
	public String addFollower(HttpServletRequest request, @ModelAttribute("model") ModelMap model) {

		String username = getUsernameFromSession(request.getSession());
		model.addAttribute("username", username);

		HttpSession session = request.getSession(true);
		String searchname = (String) session.getAttribute("searchname");
		if (searchname != null) {
			model.addAttribute("searchText", searchname);
			List<User> users = new ArrayList<User>();

			User user1 = new User();
			user1.setName(searchname);
			users.add(user1);

			model.addAttribute("users", users);
			session.removeAttribute("searchname");

		} else {
			model.addAttribute("users", new ArrayList<User>());
			model.addAttribute("searchText", "");
		}

		return "addFollower";
	}

	@RequestMapping(value = "/searchFollower", method = RequestMethod.POST)
	public String searchFollower(HttpSession session, HttpServletResponse response, @ModelAttribute("user") User user) {
		session.setAttribute("searchname", user.getName());
		return "redirect:addFollower.html";
	}

	@RequestMapping(value = "/addNewFollower", method = RequestMethod.POST)
	public String addNewFollower(HttpServletResponse response, @ModelAttribute("following") User addedUser) {
		// if (addedUser != null && addedUser.getName() != null) {
		// User user = new User();
		// user.setName(addedUser.getName());
		// this.following.add(user);
		// }
		return "redirect:following.html";
	}

	private void putUsernameIntoSession(HttpSession session, String username) {
		session.setAttribute("username", username);
	}

	private String getUsernameFromSession(HttpSession session) {
		String username = (String) session.getAttribute("username");
		return username;
	}

	private void removeUsernameFromSession(HttpSession session) {
		session.removeAttribute("username");
	}

	private void removeSearchnameFromSession(HttpSession session) {
		session.removeAttribute("searchname");
	}
}
