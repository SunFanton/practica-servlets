package com.digitalers.controllers;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitalers.entities.User;
import com.digitalers.enums.MessageEnum;

@WebServlet(value = { "/users" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private User user;
	
	public UserController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		Long id = Long.valueOf(request.getParameter("id"));
		String path = "lista.jsp";
		
		if (action != null && id != null) {
			if (action.equals("update")) {
				user = getUserFromId(id);
				if(user != null) {
					// sesion objeto editar
					request.getSession().setAttribute("userEdit", user);
					path = "usuario.jsp";
				}
				
			} else if (action.equals("delete")) {
				delete(id);
			}
		}
		response.sendRedirect(path);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Long id = null;
		try{
			id = Long.valueOf(request.getParameter("id"));
		}catch(Exception e) {
			
		}
		
		String email = request.getParameter("email");
		String key1 = request.getParameter("key1");
		String key2 = request.getParameter("key2");
		Boolean active = Boolean.valueOf(request.getParameter("active"));
		String path = "lista.jsp";

		if (email != null && key1 != null && key2 != null && active != null) {
			
			if (!key1.equals(key2)) {
				request.setAttribute("messageEnum", MessageEnum.INCORRECT_KEYS);
				path = "usuario.jsp";
				
			}  else if (id != null && getUserFromId(id) != null) {
				edit(id,email,key1,active,request);
				
			} else if (validate(email)) {
				request.setAttribute("messageEnum", MessageEnum.INCORRECT_EMAIL);
				path = "usuario.jsp";
				
			} else {
				add(new User(null, LocalDateTime.now(), email, key1, active));

			}
		}
		request.getRequestDispatcher(path).forward(request, response);

	}

	private void delete(Long id) {
		for (User user : LoginController.users) {
			if (user.getId() == id) {
				LoginController.users.remove(user);
				return;
			}
		}
	}
	
	private void edit(Long id, String email, String key, boolean active, HttpServletRequest request) {
		
		for(User user : LoginController.users) {
			if(user.getId()==id) {
				user.setEmail(email);
				user.setKey(key);
				user.setActive(active);
				break;
			}
		}
		
		request.getSession().removeAttribute("userEdit");
	}

	private void add(User user) {
		Long id = LoginController.users.get(0).getId();
		for (User userAux : LoginController.users) {
			if (userAux.getId() > id) {
				id = userAux.getId();
			}
		}
		user.setId(id + 1);

		LoginController.users.add(user);
	}

	private boolean validate(String email) {
		for (User user : LoginController.users) {
			if (user.getEmail().equalsIgnoreCase(email)) {
				return true;
			}
		}
		return false;
	}
	
	private User getUserFromId(Long id) {
		for(User user : LoginController.users) {
			if(user.getId() == id)
				return user;
		}
		return null;
	}

}
