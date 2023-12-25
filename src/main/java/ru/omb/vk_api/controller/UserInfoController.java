package ru.omb.vk_api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.omb.vk_api.entity.UserInfo;
import ru.omb.vk_api.services.UserInfoService;
import ru.omb.vk_api.services.excel.ExcelService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final ExcelService excelService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService, ExcelService excelService) {
        this.userInfoService = userInfoService;
        this.excelService = excelService;
    }

    @GetMapping("/main")
    public ModelAndView creatMainForm(){
        ModelAndView modelAndView = new ModelAndView("main");
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView findAll()  {
        ModelAndView modelAndView = new ModelAndView("user-list");
        CompletableFuture<List<UserInfo>> users = userInfoService.findAllUser();
        try {
            modelAndView.addObject("users", users.get());
        } catch (ExecutionException | InterruptedException e) {
            e.getMessage();
        }
        return modelAndView;
    }

    @GetMapping("user-delete/{id}")
    public ModelAndView deleteClient(@PathVariable("id") String userId){
        userInfoService.delete(Integer.valueOf(userId));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }

    @GetMapping("/fill-data")
    public ModelAndView fillDataUsers() {
        ModelAndView modelAndView = new ModelAndView("user-list");
        List<UserInfo> users = userInfoService.getUserInfoFromVkApi();
        excelService.fillDataExcelFile(users);
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/user-create")
    public ModelAndView createUserForm(UserInfo user){
        ModelAndView modelAndView = new ModelAndView("user-create.html");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/user-create")
    public ModelAndView createClient(UserInfo user){
        userInfoService.create(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }
}
