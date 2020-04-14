package com.apap.tu08.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tu08.model.UserRoleModel;
import com.apap.tu08.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user, String password, Model model) {
		if (password.matches("((?=.*\\d)(?=.*[a-zA-Z]).{8,})")) {
			userService.addUser(user);
			model.addAttribute("user_message", "User baru berhasil ditambahkan!");
		} else {
			model.addAttribute("user_message", "User baru gagal ditambahkan! Pastikan password telah sesuai dengan ketentuan yang ada.");
		}
		return "home";
	}
	@RequestMapping(value="/updatePassword", method=RequestMethod.POST)
    private String updatePasswordSubmit(String new_password, String conf_password, String old_password, String username, Model model) {
    	UserRoleModel user = userService.findUser(username);	
    	if (userService.validatePassword(user, old_password)) {
    		if (new_password.matches("((?=.*\\d)(?=.*[a-zA-Z]).{8,})")) {
    			if (new_password.equals(conf_password)) {
    				user.setPassword(new_password);
    				userService.addUser(user);
    				model.addAttribute("pass_message", "Password berhasil diubah!");
	    		} else {
	    				model.addAttribute("pass_message", "Silahkan coba lagi. Password baru dan konfirmasi password tidak sama");
	    		}
    		} else {
    			model.addAttribute("pass_message", "Silahkan coba lagi. Pastikan password telah sesuai dengan ketentuan yang ada.");
    		}
    	} else {
    		model.addAttribute("pass_message", "Silahkan coba lagi. Password lama salah.");
    	}
    	return "home";
    }
}
